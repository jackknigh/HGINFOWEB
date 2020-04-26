package com.service.lwaddress.impl;

import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.*;
import com.dao.entity.lwaddress.*;
import com.dto.form.AdvancedSearch;
import com.dto.form.SearchContentForm;
import com.dto.vo.*;
import com.service.lwaddress.Base_addrService;
import com.service.lwaddress.MsgSearchService;
import com.service.lwaddress.ProcessGradeService;
import com.service.lwaddress.StringParsingService;
import com.spinfosec.system.RspCode;
import com.spinfosec.system.TMCException;
import com.utils.sys.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static com.dto.constants.Constants.REGEX_IDCARD;
import static com.dto.constants.Constants.REGEX_PHONE;

@Service("msgSearchService")
public class MsgSearchServiceImpl implements MsgSearchService {

    @Autowired
    private Base_addrMapper baseAddrMapper;
    @Autowired
    private HgApplicationProperty applicationProperty;
    @Autowired
    private Base_addrService base_addrService;
    @Autowired
    private Bs_cityMapper bs_cityMapper;
    @Autowired
    private Bs_areaMapper bs_areaMapper;
    @Autowired
    private Bs_streetMapper bs_streetMapper;
    @Autowired
    private Bs_provinceMapper bs_provinceMapper;
    @Autowired
    private StringParsingService stringParsingService;
    @Autowired
    private ProcessGradeService processGradeService;

    private static final Logger log = LoggerFactory.getLogger(MsgSearchServiceImpl.class);

    @Override
    public List<BaseAddrVo> queryMsg(String name, String phone, String address) {
        List<BaseAddrVo> msgList = new ArrayList<>();
        List<BaseAddrVo> saveList = new ArrayList<>();
        List<BaseAddrVo> outList = new ArrayList<>();

        BigDecimal maxgrace = new BigDecimal(0.0);

        /*第一步处理准备动作*/
        Map<String, Object> allMessage = new HashMap<>();

        List<Bs_province> provinceMessage = bs_provinceMapper.selectShortNameAndProvinceName();
        List<Bs_city> cityMessage = bs_cityMapper.selectCityAllName();
        List<Bs_area> areaMessage = bs_areaMapper.selectAreaMessage();
        List<Bs_street> streetMessage = bs_streetMapper.selectStreetMessage();

        allMessage.put("provinceMessage", provinceMessage);
        allMessage.put("cityMessage", cityMessage);
        allMessage.put("areaMessage", areaMessage);
        allMessage.put("streetMessage", streetMessage);

        BigDecimal dec1 = new BigDecimal(-5);
        BigDecimal dec2 = new BigDecimal(-2);
        BigDecimal dec3 = new BigDecimal(-1);
        BigDecimal dec4 = new BigDecimal(0);
        BigDecimal dec5 = new BigDecimal(100);
        BigDecimal dec6 = new BigDecimal(0);
        BigDecimal dec7 = new BigDecimal(-20);
        BigDecimal dec8 = new BigDecimal(-10);

        allMessage.put("dec1", dec1);
        allMessage.put("dec2", dec2);
        allMessage.put("dec3", dec3);
        allMessage.put("dec4", dec4);
        allMessage.put("dec5", dec5);
        allMessage.put("dec6", dec6);
        allMessage.put("dec7", dec7);
        allMessage.put("dec8", dec8);

        Base_addr baseAddr = new Base_addr();
        baseAddr.setAddrSj(address);

        /*第一步处理*/
        Base_addr base_addr = base_addrService.addrSet(baseAddr, allMessage);

        /*合并处理，准备相似记录*/
        //如果姓名和或者电话不为空
        if (!StringUtils.isBlank(name) || !StringUtils.isBlank(phone)) {
            //如果姓名或者电话有带*就模糊搜
            if ((!StringUtils.isBlank(name) && name.contains("*")) || (!StringUtils.isBlank(phone) && phone.contains("*"))) {
                int len = name.length();
                name = replaceStr(name);
                phone = replaceStr(phone);
                //如果名字为空就不需要判断名字的长度了
                if (StringUtils.isBlank(name)) {
                    msgList = baseAddrMapper.selectMsg(name, phone, null);
                } else {
                    msgList = baseAddrMapper.selectMsg(name, phone, len);
                }
            } else {
                //精确搜
                msgList = baseAddrMapper.selectMsg1(name, phone, null);
            }
        }
        /*第二步相似度匹配执行        */
        Map<String, String[]> strMapa = new HashMap<>();
        Map<String, String[]> strMapb = new HashMap<>();
        Map<String, Object> processresult1 = new HashMap<>();
        Map<String, Object> processresult2 = new HashMap<>();
        BigDecimal weight1 = new BigDecimal(0);
        BigDecimal weight2 = new BigDecimal(0);
        /*将基准值放入基准值map*/
        strMapa.put("stra", (String[]) stringParsingService.stringParse(base_addr.getShortAddr()).get("strb1"));
        strMapb.put("stra", (String[]) stringParsingService.stringParse(base_addr.getShortAddr()).get("strb2"));
        /*用于判断的阈值*/
        BigDecimal grace = new BigDecimal(0.8);

        for (int i = 0; i < msgList.size(); i++) {

            strMapa.put("strb", (String[]) (stringParsingService.stringParse(msgList.get(i).getShortAddr()).get("strb1")));
            strMapb.put("strb", (String[]) (stringParsingService.stringParse(msgList.get(i).getShortAddr()).get("strb2")));
            /*返回一个map出来，其中包括计算用的Double的sum,String的integerer[]*/
            processresult1 = processGradeService.processDemo(strMapa, 3,false);
            processresult2 = processGradeService.processDemo(strMapb, 3,false);

            BigDecimal suma = (BigDecimal) processresult1.get("sum");
            BigDecimal sumb = (BigDecimal) processresult2.get("sum");

            /*判断数字位数决定权重*/
            switch (((String[]) (stringParsingService.stringParse(msgList.get(i).getShortAddr()).get("strb1"))).length) {
                case 2:
                    weight1 = new BigDecimal(applicationProperty.getweightOfNum1());
                    break;
                case 3:
                    weight1 = new BigDecimal(applicationProperty.getweightOfNum2());
                    break;
                case 4:
                    weight1 = new BigDecimal(applicationProperty.getweightOfNum3());
                    break;
                default:
                    weight1 = new BigDecimal(applicationProperty.getweightOfNum4());
                    break;
            }
            weight2 = BigDecimal.ONE.subtract(weight1);
            BigDecimal Sum = processGradeService.processliked(suma, sumb, weight1, weight2);
            if (Sum.compareTo(BigDecimal.valueOf(0.94546000)) == 0) {
                Sum = BigDecimal.ONE;
            }

            BaseAddrVo merge_base = new BaseAddrVo();
            /*计算出结果后处理*/
            merge_base.setId(msgList.get(i).getId());
            merge_base.setName1(msgList.get(i).getName1());
            merge_base.setPhone(msgList.get(i).getPhone());
            merge_base.setAddrSj(msgList.get(i).getAddrSj());
            merge_base.setContrastScore(Sum.toString());
            if (maxgrace.compareTo(Sum) <= 0) {
                maxgrace = Sum;
            }
            saveList.add(merge_base);
        }

        for (int i = 0; i < saveList.size(); i++) {
            if (maxgrace.compareTo(new BigDecimal(saveList.get(i).getContrastScore())) == 0) {
                outList.add(saveList.get(i));
            }
        }
        return outList;
    }

    /**
     * 搜索
     *
     * @param searchContentForm
     * @return
     */
    @Override
    public SearchContentVo searchContent(SearchContentForm searchContentForm) {
        //参数校验
        ValidatorUtils.validateEntity(searchContentForm);
        if(searchContentForm.getTableData()!=null){
            List<AdvancedSearch> data = searchContentForm.getTableData();
            for (AdvancedSearch datum : data) {
                if(StringUtils.isBlank(datum.getCondition())){
                    RspCode errCode = RspCode.FAILURE;
                    errCode.setDescription(errCode, "高级搜索条件值不能为空");
                    throw new TMCException(errCode);
                }
            }
        }
        if(searchContentForm.getSearchText() == null && (searchContentForm.getTableData() == null || searchContentForm.getTableData().size()<1)){
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "请输入有效参数");
            throw new TMCException(errCode);
        }
        //搜索条件
        if(searchContentForm.getSearchText() != null){
            String searchText = searchContentForm.getSearchText();
            //判断是手机号或者身份证或地址
            AdvancedSearch advancedSearch = new AdvancedSearch();
            boolean flag = regex(searchText, REGEX_PHONE);
            boolean flag1 = regex(searchText, REGEX_IDCARD);
            if (flag) {
                advancedSearch.setTelNumber(searchText);
            } else if (flag1) {
                advancedSearch.setIdNumber(searchText);
            } else {
                advancedSearch.setAddress(searchText);
            }
            advancedSearch.setCondition("and");
            List<AdvancedSearch> tableData = searchContentForm.getTableData();
            if (tableData == null) {
                tableData = new ArrayList<>(1);
            }
            tableData.add(advancedSearch);
            searchContentForm.setTableData(tableData);
        }

        //获取总条数
        long total = baseAddrMapper.getTotal(searchContentForm);

        searchContentForm.setCurrentPage((searchContentForm.getCurrentPage()-1)*searchContentForm.getPageSize());
        //如果是根据地址查询，查标准化地址基准表
        List<PersonMsgVo> list = baseAddrMapper.searchContent(searchContentForm);

        if(list == null || list.size()==0){
            SearchContentVo searchContentVo = new SearchContentVo();
            searchContentVo.setData(list);
            searchContentVo.setTotal(total);
            return searchContentVo;
        }

        //查询同地址人员详情
        List<Base_addr> peopleLists = baseAddrMapper.searchPeopleListByContrastIds(list);

        //查询标准地址详情
        List<Base_addr> basicsLists = baseAddrMapper.searchBasicsMsg(list);

        //查询同地址数量
        List<MergeNums> mergeNums = baseAddrMapper.searchMergeNums(list);

        for (PersonMsgVo personMsgVo : list) {
            List<Base_addr> baseAddrs = new ArrayList<>();

            //同地址人员详情
            for (Base_addr peopleList : peopleLists) {
                if(personMsgVo.getContrastId().equals(peopleList.getContrastId())){
                    baseAddrs.add(peopleList);
                }
            }

            //标准地址人员详情
            for (Base_addr basicsList : basicsLists) {
                if(personMsgVo.getContrastId().equals(basicsList.getId())){
                    personMsgVo.setBasiscMsg(basicsList);
                }
            }
            personMsgVo.setPeopleList(baseAddrs);

            //合并地址数量
            for (MergeNums mergeNum : mergeNums) {
                if(personMsgVo.getId().equals(mergeNum.getId())){
                    personMsgVo.setAddressNum(mergeNum.getNum());
                }
            }
        }

        //如果是根据其他条件查询，查基准表，然后再拿结果查标准化地址表
        SearchContentVo searchContentVo = new SearchContentVo();
        searchContentVo.setData(list);
        searchContentVo.setTotal(total);
        return searchContentVo;
    }

    /**
     * 同地址人数详情
     *
     * @param id
     * @return
     */
    @Override
    public List<PersonMsgVo> searchPeopleList(String id) {
        List<PersonMsgVo> personMsgVos1 = new ArrayList<>();
        //先到合并数据表找数据，如果找到了，就说明该数据被合并了，就通过该数据的关联id找到其他同地址数据
        PersonMsgVo personMsgVo = baseAddrMapper.searchContrastIdByMerge(id);
        if(personMsgVo != null){
            List<PersonMsgVo> personMsgVos = baseAddrMapper.searchPeopleListByBasic(personMsgVo.getContrastId());
            personMsgVos1.addAll(personMsgVos);
            List<PersonMsgVo> personMsgVos2 = baseAddrMapper.searchPeopleListByContrastId(personMsgVo.getContrastId());
            personMsgVos1.addAll(personMsgVos2);
            personMsgVos1 = addressRegex(personMsgVos1);
        }else {
            //如果没找到，就通过id去基准表找id，再通过关联id找同地址下的人
            List<PersonMsgVo> personMsgVos = baseAddrMapper.searchPeopleListByBasic(id);
            personMsgVos1.addAll(personMsgVos);
            List<PersonMsgVo> personMsgVos2 = baseAddrMapper.searchPeopleListByMerge(id);
            personMsgVos1.addAll(personMsgVos2);
            personMsgVos1 = addressRegex(personMsgVos1);
        }
        return personMsgVos1;
    }

    /**
     * 合并地址列表详情
     *
     * @param id
     * @return
     */
    @Override
    public AddressListMsgVo searchAddressList(String id ,Integer currentPage, Integer pageSize) {
        //获取总条数
        long total = baseAddrMapper.getTotalById(id);
        currentPage = (currentPage-1)*pageSize;
        List<PersonMsgVo> personMsgVos = baseAddrMapper.searchAddressList(id, currentPage, pageSize);
        AddressListMsgVo addressListMsgVo = new AddressListMsgVo();
        addressListMsgVo.setTotal(total);
        addressListMsgVo.setPersonMsgVos(personMsgVos);
        return addressListMsgVo;
    }

    /**
     * 合并地址列表移除
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeAddressList(String id) {
        //先新增，后添加
        Base_addr baseAddr = baseAddrMapper.searchMergeById(id);
        //如果查不到该数据
        if (baseAddr == null) {
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "请选择有效数据");
            throw new TMCException(errCode);
        }
        baseAddr.setCountId(null);
        baseAddr.setP2type(223);
        baseAddr.setStrScore("0.0000");
        baseAddr.setNumberScore("0.0000");
        int insert = baseAddrMapper.insertBasics(baseAddr);
        int delete = baseAddrMapper.deleteMergeById(id);

        if (insert < 1 || delete < 1) {
            //插入或者删除失败
            log.error("insert:{} delete:{}", insert, delete);
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "移除失败");
            throw new TMCException(errCode);
        }
    }

    /**
     * 合并地址
     *
     * @param
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sumAddress( String addressId,String keyData) {
        //查找，添加，删除
        Base_addr baseAddr = baseAddrMapper.searchBasicsById(keyData);
        //如果查不到该数据
        if (baseAddr == null) {
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "请选择有效数据");
            throw new TMCException(errCode);
        }
        baseAddr.setContrastId(addressId);
        baseAddr.setP2type(222);
        baseAddr.setContrastScore(new BigDecimal(0.0000));
        int insert = baseAddrMapper.insertMerge(baseAddr);
        int delete = baseAddrMapper.deleteBasicsById(keyData);
        //需要把标准地址也删除
        baseAddrMapper.deleteBasicsNormal(keyData);
        baseAddrMapper.deleteMergeNormal(keyData);

        if (insert < 1 || delete < 1) {
            //插入或者删除失败
            log.error("insert:{} delete:{}", insert, delete);
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "合并地址失败");
            throw new TMCException(errCode);
        }
    }

    /**
     * 人员其他地址情况
     * @param phone
     * @return
     */
    @Override
    public List<PersonMsgVo> otherAddress(String phone) {
        List<PersonMsgVo> personMsgVos = baseAddrMapper.searchBasicsByPhone(phone);
        return addressRegex(personMsgVos);
    }

    /**
     * 收快递数
     * @param id
     * @return
     */
    @Override
    public List<ChartData> acceptDelivery(String id) {
        //过去一年的时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        String year = format.format(y);

        //近一年收快递数
        List<ChartData> chartDataList = baseAddrMapper.searchMergeKdNum(id,year);
        List<ChartData> chartData = baseAddrMapper.searchBasicsKdNum(id,year);
        if(chartDataList == null){
            chartDataList = new ArrayList<>();
        }
        for (ChartData data : chartDataList) {
            if(chartData.get(0).getTime().equals(data.getTime())){
                data.setNumber(String.valueOf(Integer.valueOf(data.getNumber())+1));
                chartData = null;
                break;
            }
        }
        if(chartData != null){
            chartDataList.add(chartData.get(0));
        }
        //每个月的快递数
        for (ChartData data : chartDataList) {
            List<AcceptDeliveryPeoperVo> acceptDeliveryPeoperVos = baseAddrMapper.searchMergeAcceptDelivery(id,data.getTime(),year);
            acceptDeliveryPeoperVos.addAll(baseAddrMapper.searchBaiscAcceptDelivery(id,data.getTime(),year));
            data.setAcceptDeliveryPeoperVo(acceptDeliveryPeoperVos);
        }
        return chartDataList;
    }


    /**
     * 替换带*的为%
     *
     * @param str
     * @return
     */
    public String replaceStr(String str) {
        if (!StringUtils.isBlank(str)) {
            str = str.trim().replaceAll("(\\*)\\1{0,}", "%");
            return str;
        }
        return str;
    }

    /**
     * 正则校验方法
     *
     * @param str   需要校验的字符串
     * @param regex 正则表达式
     * @return
     */
    public boolean regex(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        boolean flag = pattern.matcher(str).matches();
        return flag;
    }


    /**
     * 同地址人收快递数
     * @param personMsgVos
     * @return
     */
    public List<PersonMsgVo> addressRegex(List<PersonMsgVo> personMsgVos) {
        //过去一年的时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        String year = format.format(y);

        for (PersonMsgVo personMsgVo : personMsgVos) {
            List<ChartData> chartDataList = baseAddrMapper.searchMergeKdNum(personMsgVo.getId(),year);
            List<ChartData> chartData = baseAddrMapper.searchBasicsKdNum(personMsgVo.getId(),year);
            if(chartDataList == null){
                chartDataList = new ArrayList<>();
            }
            for (ChartData data : chartDataList) {
                if(chartData.get(0).getTime().equals(data.getTime())){
                    data.setNumber(String.valueOf(Integer.valueOf(data.getNumber())+1));
                    chartData = null;
                    break;
                }
            }
            if(chartData != null){
                chartDataList.add(chartData.get(0));
            }
            personMsgVo.setChartData(chartDataList);
        }
        return personMsgVos;
    }
}
