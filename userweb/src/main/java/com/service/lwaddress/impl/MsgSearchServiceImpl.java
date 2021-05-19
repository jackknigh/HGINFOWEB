package com.service.lwaddress.impl;

import com.alibaba.fastjson.JSON;
import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.Base_addrMapper;
import com.dao.db3.lwaddr.Base_addrMapper1;
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
import com.utils.sys.TextUtils;
import com.utils.sys.ValidatorUtils;
import com.utils.sys.lwaddress.AsciiUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
    private Base_addrMapper1 bs_addrMapper1;
    @Autowired
    private Base_addrMapper bs_addrMapper;
    @Autowired
    private StringParsingService stringParsingService;
    @Autowired
    private ProcessGradeService processGradeService;

    private static final Logger log = LoggerFactory.getLogger(MsgSearchServiceImpl.class);

    @Override
//    public List<BaseAddrVo> queryMsg(String name, String phone, String address) {
    public List<BaseAddrVo> queryMsg(String address, String shortPhone, String surname) {
        if (StringUtils.isBlank(address)) {
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "请输入地址");
            throw new TMCException(errCode);
        }
        List<BaseAddrVo> outList = new ArrayList<>();

        //用来存放所有省市区街道集合和分值对象的
        Map<String, Object> allMessage = new HashMap<>();

        List<Bs_province> provinceMessage = new ArrayList<>();
        List<Bs_city> cityMessage = new ArrayList<>();
        List<Bs_area> areaMessage = new ArrayList<>();
        List<Bs_street> streetMessage = new ArrayList<>();
        List<Bs_street> streetNames;

        //分值计算
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

        //配置的市的编号
        String cityCode = applicationProperty.getCityCode();
        //查询市
        Bs_city cityName = bs_addrMapper.getCity(cityCode);
        //查询省
        Bs_province provinceName = bs_addrMapper.getProvince(cityName.getProvinceCode());
        //查询区
        List<Bs_area> areaNames = bs_addrMapper.getArea(cityCode);
        //查询街道不需要去除
        for (Bs_area areaName : areaNames) {
            //查询街道
            streetNames = bs_addrMapper.getStreetName(areaName.getAreaCode());
            streetMessage.addAll(streetNames);
        }
        provinceMessage.add(provinceName);
        cityMessage.add(cityName);
        areaMessage.addAll(areaNames);

        allMessage.put("provinceMessage", provinceMessage);
        allMessage.put("cityMessage", cityMessage);
        allMessage.put("areaMessage", areaMessage);
        allMessage.put("streetMessage", streetMessage);


        //地址处理特殊字符串
        address = AsciiUtil.SpecialHandl(address, null);

        /*第一步处理*/
        Base_addr base_addr = base_addrService.addrSet(address, allMessage);

//        /*合并处理，准备相似记录*/
//        //如果姓名和或者电话不为空
//        if (!StringUtils.isBlank(name) || !StringUtils.isBlank(phone)) {
//            //如果姓名或者电话有带*就模糊搜
//            if ((!StringUtils.isBlank(name) && name.contains("*")) || (!StringUtils.isBlank(phone) && phone.contains("*"))) {
//                int len = name.length();
//                name = replaceStr(name);
//                phone = replaceStr(phone);
//                //如果名字为空就不需要判断名字的长度了
//                if (StringUtils.isBlank(name)) {
//                    msgList = baseAddrMapper.selectMsg(name, phone, null);
//                } else {
//                    msgList = baseAddrMapper.selectMsg(name, phone, len);
//                }
//            } else {
//                //精确搜
//                msgList = baseAddrMapper.selectMsg1(name, phone, null);
//            }
//        }

        /*第二步相似度匹配执行        */
        Map<String, String[]> strMapa = new HashMap<>();
        Map<String, String[]> strMapb = new HashMap<>();

        //处理特殊字符串
        address = AsciiUtil.RegProcess(base_addr.getShortAddr());
        /*将基准值放入基准值map*/
        Map stringMap = stringParsingService.stringParse(address);
        strMapa.put("stra", (String[]) stringMap.get("strb1"));
        strMapb.put("stra", (String[]) stringMap.get("strb2"));

        List<BaseAddrVo> msgList = baseAddrMapper.selectMsg2(base_addr.getArea(), base_addr.getStreet(), shortPhone);
        BigDecimal weight1;
        BigDecimal weight2;
        /*用于判断的阈值*/
        BigDecimal grace = new BigDecimal(0.761);

        for (int i = 0; i < msgList.size(); i++) {
            //如果姓不相同就跳过
            String name1 = msgList.get(i).getName1();
            if (!StringUtils.isBlank(name1) && !StringUtils.isBlank(surname) && !name1.startsWith(surname)) {
                continue;
            }

            Map stringMap1 = stringParsingService.stringParse(msgList.get(i).getShortAddr());
            strMapb.put("strb", (String[]) stringMap1.get("strb2"));
            strMapa.put("strb", (String[]) stringMap1.get("strb1"));
            /*返回一个map出来，其中包括计算用的Double的sum,String的integerer[]*/
            Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, Integer.valueOf(applicationProperty.getBlockSizeByNum()), false);
            Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, Integer.valueOf(applicationProperty.getBlockSizeByStr()), false);

            BigDecimal suma = (BigDecimal) processresult1.get("sum");
            BigDecimal sumb = (BigDecimal) processresult2.get("sum");

            String[] strb1s = (String[]) stringMap1.get("strb1");
            String[] strb1s1 = (String[]) stringMap.get("strb1");
            String[] strb1s2 = (String[]) stringMap1.get("strb2");
            String[] strb1s3 = (String[]) stringMap.get("strb2");
            //基准值和合并值都不带数字
            if (StringUtils.isBlank(strb1s[0]) && StringUtils.isBlank(strb1s1[0])) {
                weight1 = new BigDecimal(0);
            } else if (StringUtils.isBlank(strb1s2[0]) && org.apache.commons.lang3.StringUtils.isBlank(strb1s3[0])) {
                //基准值和合并值都不带字符
                weight1 = new BigDecimal(1);
            } else if (StringUtils.isBlank(strb1s[0]) && !StringUtils.isBlank(strb1s1[0])) {
                //基准值不带数字，合并值带数字
                weight1 = new BigDecimal(1);
            } else {
                /*判断数字位数决定权重*/
                switch (strb1s.length - 1) {
                    case 1:
                        weight1 = new BigDecimal(0.4);
                        break;
                    case 2:
                        weight1 = new BigDecimal(0.5);
                        break;
                    case 3:
                        weight1 = new BigDecimal(0.5);
                        break;
                    case 4:
                        weight1 = new BigDecimal(0.6);
                        break;
                    default:
                        weight1 = new BigDecimal(0.7);
                        break;
                }
            }

            weight2 = BigDecimal.ONE.subtract(weight1);

            //计算相似度
            BigDecimal sum = processGradeService.processliked(suma, sumb, weight1, weight2);
            if (sum.compareTo(BigDecimal.valueOf(0.94546000)) == 0) {
                sum = BigDecimal.ONE;
            }

            //相似
            if (sum.compareTo(grace) > 0) {
                BaseAddrVo merge_base = new BaseAddrVo();
                /*计算出结果后处理*/
                merge_base.setId(msgList.get(i).getId());
                merge_base.setName1(msgList.get(i).getName1());
                merge_base.setPhone(msgList.get(i).getPhone());
                StringBuilder addr = new StringBuilder();
                if (!StringUtils.isBlank(msgList.get(i).getProvince())) {
                    addr.append(msgList.get(i).getProvince());
                }
                if (!StringUtils.isBlank(msgList.get(i).getCity())) {
                    addr.append(msgList.get(i).getCity());
                }
                if (!StringUtils.isBlank(msgList.get(i).getArea())) {
                    addr.append(msgList.get(i).getArea());
                }
                if (!StringUtils.isBlank(msgList.get(i).getStreet())) {
                    addr.append(msgList.get(i).getStreet());
                }
                merge_base.setAddrSj(addr.toString() + msgList.get(i).getShortAddr());
                merge_base.setShortAddr(msgList.get(i).getShortAddr());
                merge_base.setContrastScore(sum.toString());
                outList.add(merge_base);
            }
        }

        Collections.sort(outList, new Comparator<BaseAddrVo>() {

            @Override
            public int compare(BaseAddrVo o1, BaseAddrVo o2) {
                return o2.getContrastScore().compareTo(o1.getContrastScore());
            }
        });

        return outList;
    }

    @Override
    public List<BaseAddrVo> queryMsgByCode(String code) {
        //通过房屋编号查找同地址下的人员
        List<BaseAddrVo> list = bs_addrMapper1.queryMsgByCode(code);
        return list;
    }

    @Override
    public BaseAddrVo queryMsgById(String id) {
        //通过id查找同地址下的人员
        BaseAddrVo baseAddrVo = bs_addrMapper1.queryMsgById(id);
        return baseAddrVo;
    }

    @Override
    public BaseAddrVo insertMsgProcess(BaseAddrVo bsAddrVo) {
        //初始化值数据
        Base_addr baseAddr = new Base_addr();
        baseAddr.setPhone(bsAddrVo.getPhone());
        baseAddr.setAddrSj(bsAddrVo.getAddress());
        baseAddr.setName1(bsAddrVo.getName1());
        baseAddr.setId(TextUtils.getUUID());
        baseAddr.setTableName("insert_" + bsAddrVo.getTableName());
        baseAddr.setCreateTime(bsAddrVo.getCreateTime());

        //获取省市区街道
        String reg = getReg();
        Map<String, Object> allMessage = getMap();

        //合法性校验
        if (StringUtils.isBlank(baseAddr.getPhone()) || baseAddr.getPhone().length() < 5) {
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "手机号不合法");
            throw new TMCException(errCode);
        }

        //step1地址切割
        Base_addr base_addr = getBaseAddr(baseAddr, reg, allMessage);

        if (base_addr == null || StringUtils.isBlank(base_addr.getShortAddr())) {
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "地址不合法");
            throw new TMCException(errCode);
        }

        //插入总表base_addr
        bs_addrMapper.insertBaseAddr(base_addr);

        int blockSizeByStr = Integer.valueOf(applicationProperty.getBlockSizeByStr());
        int blockSizeByNum = Integer.valueOf(applicationProperty.getBlockSizeByNum());

        String phone = base_addr.getPhone();
        String name = base_addr.getName1();
        //手机号包含*,手机号反写，反写失败就扔进垃圾表
        if (!StringUtils.isBlank(phone) && phone.length() >= 7 && phone.contains("*")) {
//            String startPhone = phone.substring(1, 3);
//            String endPhone = phone.substring(phone.length() - 4);
//            String shortPhone = startPhone + endPhone;
            String name1 = null;
            if(!StringUtils.isBlank(name) && name.length() >= 1 && !"*".equals(name.split("")[0])){
                name1 = name;
            }

            //获取比较数据
            List<Base_addr> addressMessage = bs_addrMapper.getDate1(base_addr.getArea(), base_addr.getStreet(), base_addr.getShortPhone(),name1);

            Base_addr baseAddr1 = getProcess(blockSizeByNum, blockSizeByStr, base_addr, addressMessage, false);
            if (baseAddr1 == null || baseAddr1.getPhone().contains("*")) {
                RspCode errCode = RspCode.FAILURE;
                errCode.setDescription(errCode, "手机号反写失败");
                throw new TMCException(errCode);
            }
        }

        //第二步骤相似度碰撞
        List<Base_addr> addressMessage = bs_addrMapper.getBaseAddrs(base_addr.getPhone());
        Base_addr baseAddr1 = getProcess(blockSizeByNum, blockSizeByStr, base_addr, addressMessage, true);
        //如果被合并
        if (!TextUtils.isEmpty(baseAddr1)) {
            //根据基准数据的id去碰撞后的编码数据表获取编码，返回值(相似度)
            BaseAddrVo baseAddrVo = bs_addrMapper1.queryMsgById(baseAddr1.getId());
            //如果找不到，就说明该基准数据没有与之匹配的房屋数据
            if (TextUtils.isEmpty(baseAddrVo)) {
                BaseAddrVo baseAddrVo1 = new BaseAddrVo();
                baseAddrVo1.setName1(baseAddr1.getName1());
                baseAddrVo1.setPhone(baseAddr1.getPhone());

                StringBuilder addr = new StringBuilder();
                if (!StringUtils.isBlank(baseAddr1.getProvince())) {
                    addr.append(baseAddr1.getProvince());
                }
                if (!StringUtils.isBlank(baseAddr1.getCity())) {
                    addr.append(baseAddr1.getCity());
                }
                if (!StringUtils.isBlank(baseAddr1.getArea())) {
                    addr.append(baseAddr1.getArea());
                }
                if (!StringUtils.isBlank(baseAddr1.getStreet())) {
                    addr.append(baseAddr1.getStreet());
                }

                baseAddrVo1.setAddress(addr.toString() + baseAddr1.getShortAddr());
                baseAddrVo1.setAddrCode(baseAddr1.getAddrCode());
                baseAddrVo1.setCreateTime(baseAddr1.getCreateTime());
                return baseAddrVo1;
            }
            return baseAddrVo;
        }

        //该数据是基准值数据
        base_addr.setP2type(223);
        base_addr.setMergeNum(0);
        base_addr.setP5type(1);
        base_addr.setEarliestTime(base_addr.getCreateTime());
        base_addr.setLatestTime(base_addr.getCreateTime());
        log.info("插入基准表");
        bs_addrMapper.insert5(base_addr);

        if(!"温州市".equals(base_addr.getCity())){
            return bsAddrVo;
        }
        //todo 对比常住、暂住人口表
        String data = applicationProperty.getDiscrepancy();
        List<Discrepancy> discrepancyList = JSON.parseArray(data, Discrepancy.class);

        //todo 常暂口表处理
        for (Discrepancy discrepancy : discrepancyList) {
            //根据手机号从表中获取数据
            List<Base_addr> obtainData = bs_addrMapper.getData(discrepancy.getObtain(), base_addr.getPhone());
            log.info("*********有 {} 条被比较值数据*********", obtainData.size());
            Base_addr baseAddr2 = CompareRunnable5(blockSizeByNum, blockSizeByStr, obtainData, base_addr, discrepancy);
            //如果为null，就说明撞到了
            if (TextUtils.isEmpty(baseAddr2)) {
                break;
            }
        }

        //根据街道查询标准数据(房屋数据)表
//            List<Base_addr> volList = bs_addrMapper.selectBaseAddr2(base_addr.getStreet());
        List<Base_addr> volList = bs_addrMapper1.selectBaseAddr2(base_addr.getArea(), base_addr.getStreet());

        log.info("*********有 {} 条被比较值数据*********", volList.size());
        Base_addr baseAddr2 = CompareRunnable4(blockSizeByNum, blockSizeByStr, volList, base_addr);

        BaseAddrVo baseAddrVo = new BaseAddrVo();
        baseAddrVo.setPhone(baseAddr2.getPhone());
        baseAddrVo.setName1(baseAddr2.getName1());
        baseAddrVo.setCreateTime(baseAddr2.getCreateTime());
        baseAddrVo.setContrastScore(baseAddr2.getContrastScore().toString());

        StringBuilder addr = new StringBuilder();
        if (!StringUtils.isBlank(baseAddr2.getProvince())) {
            addr.append(baseAddr2.getProvince());
        }
        if (!StringUtils.isBlank(baseAddr2.getCity())) {
            addr.append(baseAddr2.getCity());
        }
        if (!StringUtils.isBlank(baseAddr2.getArea())) {
            addr.append(baseAddr2.getArea());
        }
        if (!StringUtils.isBlank(baseAddr2.getStreet())) {
            addr.append(baseAddr2.getStreet());
        }

        baseAddrVo.setAddrCode(baseAddr2.getAddrCode());
        baseAddrVo.setAddress(addr.toString() + baseAddr2.getShortAddr());
        return baseAddrVo;
    }

    @Override
    public BaseAddrVo queryInfoById(String id) {
        if (StringUtils.isBlank(id)) {
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "查询失败");
            throw new TMCException(errCode);
        }

        BaseAddrVo baseAddrVo = bs_addrMapper.queryInfoById(id);

        StringBuilder addr = new StringBuilder();
        if (!StringUtils.isBlank(baseAddrVo.getProvince())) {
            addr.append(baseAddrVo.getProvince());
        }
        if (!StringUtils.isBlank(baseAddrVo.getCity())) {
            addr.append(baseAddrVo.getCity());
        }
        if (!StringUtils.isBlank(baseAddrVo.getArea())) {
            addr.append(baseAddrVo.getArea());
        }
        if (!StringUtils.isBlank(baseAddrVo.getStreet())) {
            addr.append(baseAddrVo.getStreet());
        }
        if (!StringUtils.isBlank(baseAddrVo.getShortAddr())) {
            addr.append(baseAddrVo.getShortAddr());
        }
        baseAddrVo.setAddress(addr.toString());

        return baseAddrVo;
    }

    @Override
    public List<BaseAddrByPhone> queryMergeInfoById(String id) {
        if (StringUtils.isBlank(id)) {
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "查询失败");
            throw new TMCException(errCode);
        }
        return bs_addrMapper.queryMergeInfoById(id);
    }

    @Override
    public List<BaseAddrByPhone> queryBasicsMsgByPhone(String phone) {

        //如果查不到该数据
        if (phone == null) {
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "请输入手机号");
            throw new TMCException(errCode);
        }

        List<BaseAddrByPhone> baseAddrs = bs_addrMapper.queryBasicsMsgByPhone(phone);

        //将快递地址转为相对标准地址
        for (BaseAddrByPhone baseAddr : baseAddrs) {
            StringBuilder addr = new StringBuilder();
            if (!StringUtils.isBlank(baseAddr.getProvince())) {
                addr.append(baseAddr.getProvince());
            }
            if (!StringUtils.isBlank(baseAddr.getCity())) {
                addr.append(baseAddr.getCity());
            }
            if (!StringUtils.isBlank(baseAddr.getArea())) {
                addr.append(baseAddr.getArea());
            }
            if (!StringUtils.isBlank(baseAddr.getStreet())) {
                addr.append(baseAddr.getStreet());
            }
            if (!StringUtils.isBlank(baseAddr.getShortAddr())) {
                addr.append(baseAddr.getShortAddr());
            }
            baseAddr.setAddress(addr.toString());
        }
        return baseAddrs;
    }

    @Override
    public Base_addr queryAddressInfo(String address) {
        //初始化值数据
        Base_addr baseAddr = new Base_addr();
        baseAddr.setAddrSj(address);

        //获取省市区街道
        String reg = getReg();
        Map<String, Object> allMessage = getMap();

        //step1地址切割
        Base_addr base_addr = getBaseAddr(baseAddr, reg, allMessage);

        if (base_addr == null || StringUtils.isBlank(base_addr.getShortAddr())) {
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "地址不合法");
            throw new TMCException(errCode);
        }
        return base_addr;
    }

    //左右相似度碰撞
    public Base_addr getProcess(int num, int str, Base_addr baseAddrBasics, List<Base_addr> addressMessage, boolean flag) {
        Map<String, String[]> strMapa = new HashMap<>();
        Map<String, String[]> strMapb = new HashMap<>();
        StringPasringServiceImpl stringParsingService = new StringPasringServiceImpl();
        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();

        //数字字母处理
        String shortAddr = AsciiUtil.RegProcess(baseAddrBasics.getShortAddr());

        //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
        Map stringMap = stringParsingService.stringParse(shortAddr);
        strMapa.put("strb", (String[]) stringMap.get("strb1"));
        strMapb.put("strb", (String[]) stringMap.get("strb2"));


        for (int i = 0; i < addressMessage.size(); i++) {
            BigDecimal grace = new BigDecimal(applicationProperty.getGrace1());

            if (!StringUtils.isEmpty(addressMessage.get(i).getShortAddr())) {

                //如果姓相同就继续，不相同就判定为不是同一个人
                if (!StringUtils.isBlank(baseAddrBasics.getName1()) && !StringUtils.isBlank(addressMessage.get(i).getName1())) {
                    if (!baseAddrBasics.getName1().split("")[0].equals(addressMessage.get(i).getName1().split("")[0])
                            && !addressMessage.get(i).getName1().startsWith("*") && !baseAddrBasics.getName1().startsWith("*")) {
                        continue;
                    }
                }

                //数字字母处理
                String shortAddr1 = AsciiUtil.RegProcess(addressMessage.get(i).getShortAddr());
                //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串,key=strb是合并值，key=stra是基准值
                Map stringMap1 = stringParsingService.stringParse(shortAddr1);
                strMapa.put("stra", (String[]) stringMap1.get("strb1"));
                strMapb.put("stra", (String[]) stringMap1.get("strb2"));

                //满分
                BigDecimal bsum = new BigDecimal(100);
                //数字及格线
                BigDecimal numPass = new BigDecimal(applicationProperty.getNumPass());
                //字符及格线
                BigDecimal strPass = new BigDecimal(applicationProperty.getStrPass());

                BigDecimal sum = new BigDecimal(0);

                /*返回一个map出来，其中包括计算用的Double的sum,String的integerer[]*/
                //数字和字符相似度匹配
                Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, num, false);


                //理论最大值
                BigDecimal asummax = (BigDecimal) processresult1.get("asummax");
                //实际得分
                BigDecimal asum = (BigDecimal) processresult1.get("asum");

                Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, str, false);

                //理论最大值
                BigDecimal asummax1 = (BigDecimal) processresult2.get("asummax");
                //实际得分
                BigDecimal asum1 = (BigDecimal) processresult2.get("asum");


                BigDecimal suma = (BigDecimal) processresult1.get("sum");
                BigDecimal sumb = (BigDecimal) processresult2.get("sum");

                if (!baseAddrBasics.getShortAddr().contains(addressMessage.get(i).getShortAddr())) {

                    if (flag) {
                        grace = new BigDecimal(applicationProperty.getGrace());
                        BigDecimal divide = bsum.divide(asummax, 4, BigDecimal.ROUND_HALF_UP).multiply(asum).divide(numPass, 4, BigDecimal.ROUND_HALF_UP);

                        //如果数字没到及格线
                        if (divide.compareTo(new BigDecimal(1)) < 0) {
                            continue;
                        }

                        //如果数字基本满分，那么字符得分就能低一点
                        if (divide.compareTo(new BigDecimal(1.8)) >= 0) {
                            strPass = new BigDecimal(15);
                        }

                        try {
                            //如果字符没到及格线
                            if (bsum.divide(asummax1, 4, BigDecimal.ROUND_HALF_UP).multiply(asum1).divide(strPass, 4, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(1)) <= 0) {
                                continue;
                            }
                        }catch (Exception e){
                            log.error("========= bsum : {}  =========",bsum);
                            log.error("========= asummax1 : {}  =========",asummax1);
                            log.error("========= asum1 : {}  =========",asum1);
                            log.error("========= strPass : {}  =========",strPass);
                        }

                    }

                    //计算相似度
                    sum = processGradeService.getSum(suma, sumb, (String[]) stringMap1.get("strb1"), (String[]) stringMap.get("strb1"), (String[]) stringMap1.get("strb2"), (String[]) stringMap.get("strb2"));
                    baseAddrBasics.setNumberScore((String) processresult1.get("integer"));
                    baseAddrBasics.setStrScore((String) processresult2.get("integer"));
                } else {
                    sum = new BigDecimal(100);
                    baseAddrBasics.setNumberScore(processresult1.get("integer") + "(包含关系)");
                    baseAddrBasics.setStrScore(processresult2.get("integer") + "(包含关系)");
                }

                //如果相似度大于阈值或者基准短地址包含比较短地址
                if (sum.compareTo(grace) > 0) {
                    if (flag) {
                        baseAddrBasics.setContrastId(addressMessage.get(i).getId());
                        baseAddrBasics.setP2type(222);
                        addressMessage.get(i).setMergeNum(addressMessage.get(i).getMergeNum() + 1);
                        if (baseAddrBasics.getCreateTime().compareTo(addressMessage.get(i).getLatestTime()) > 0) {
                            addressMessage.get(i).setLatestTime(baseAddrBasics.getLatestTime());
                        }

                        if (baseAddrBasics.getCreateTime().compareTo(addressMessage.get(i).getEarliestTime()) < 0) {
                            addressMessage.get(i).setEarliestTime(baseAddrBasics.getEarliestTime());
                        }

                        //todo 修改合并数
                        bs_addrMapper.updateBasicsES(addressMessage.get(i));

                        log.info("插入合并表");
                        bs_addrMapper.insert3ES(baseAddrBasics);
                        addressMessage.get(i).setContrastScore(sum);
                        return addressMessage.get(i);
                    }

                    baseAddrBasics.setOldPhone(baseAddrBasics.getPhone());
                    baseAddrBasics.setOldName1(baseAddrBasics.getName1());

                    //手机号姓名反写
                    if (!flag) {
                        String[] name1a = null;
                        String[] phone1 = null;
                        if (!StringUtils.isBlank(addressMessage.get(i).getName1())) {
                            name1a = addressMessage.get(i).getName1().split("");
                        }
                        if (!StringUtils.isBlank(addressMessage.get(i).getPhone())) {
                            phone1 = addressMessage.get(i).getPhone().split("");
                        }
                        NameProcessServiceImpl nameProcessService = new NameProcessServiceImpl();
                        if (phone1 != null) {
                            /*手机反写*/
                            if (baseAddrBasics.getPhone() != null) {
                                //*当匹配到手机后，复写回insertmessage*//*
                                /*用修改的基准值手机号反写合并值手机号*/
                                if (nameProcessService.a2nameLikedProcess(phone1, baseAddrBasics.getPhone().split(""))) {
                                    baseAddrBasics.setPhone(addressMessage.get(i).getPhone());
                                }
                            }
                        }

                        if (name1a != null && baseAddrBasics.getName1() != null) {
                            //基准值反写合并值，不带别名
                            if (baseAddrBasics.getName1().contains("*")
                                    && nameProcessService.nameLikedProcess(baseAddrBasics.getName1().split(""), name1a)) {
                                baseAddrBasics.setName1(addressMessage.get(i).getName1());
                            }

                            //带别名反写
                            if (baseAddrBasics.getPhone() != null
                                    && addressMessage.get(i).getPhone().equals(baseAddrBasics.getPhone())
                                    && nameProcessService.a3nameLikedProcess(name1a, baseAddrBasics.getName1().split(""))) {
                                //如果合并值带别名
                                if (nameProcessService.isContainChinese(baseAddrBasics.getName1())) {
                                    baseAddrBasics.setName1(addressMessage.get(i).getName1());
                                }
                            }
                        }
                        return baseAddrBasics;
                    }
                }
            }
        }
        return null;
    }

    public Base_addr CompareRunnable3(int num, int str, List<Base_addr> baseAddrList, Base_addr addressMessage) {
        Map<String, String[]> strMapb = new HashMap<>();
        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();
        Map<String, String[]> strMapa = new HashMap<>();
        StringPasringServiceImpl stringParsingService = new StringPasringServiceImpl();

        for (int j = 0; j < baseAddrList.size(); j++) {
            //数字字母处理
            String shortAddr = AsciiUtil.RegProcess(baseAddrList.get(j).getShortAddr());
            if (shortAddr.endsWith("号") || shortAddr.endsWith("室")) {
                shortAddr = shortAddr.substring(0, shortAddr.length() - 1);
            }
            //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
            Map stringMap = stringParsingService.stringParse(shortAddr);
            strMapb.put("stra", (String[]) stringMap.get("strb2"));
            strMapa.put("stra", (String[]) stringMap.get("strb1"));

            /*用于判断的阈值*/
            BigDecimal grace = new BigDecimal(applicationProperty.getGrace2());

            //如果数据已经跟其他数据合并过了，就不需要再判断了
            if (!StringUtils.isBlank(addressMessage.getContrastId())) {
                continue;
            }

            if (!StringUtils.isEmpty(addressMessage.getShortAddr())) {
                //数字字母处理
                String shortAddr1 = AsciiUtil.RegProcess(addressMessage.getShortAddr());
                if (shortAddr1.endsWith("号") || shortAddr1.endsWith("室")) {
                    shortAddr1 = shortAddr1.substring(0, shortAddr1.length() - 1);
                }
                //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
                Map stringMap1 = stringParsingService.stringParse(shortAddr1);
                strMapa.put("strb", (String[]) stringMap1.get("strb1"));
                strMapb.put("strb", (String[]) stringMap1.get("strb2"));

                //数字和字符相似度匹配
                Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, num, false);
                BigDecimal suma = (BigDecimal) processresult1.get("sum");
                Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, str, false);
                BigDecimal sumb = (BigDecimal) processresult2.get("sum");

                //计算相似度
                BigDecimal sum = processGradeService.getSum(suma, sumb, (String[]) stringMap1.get("strb1"), (String[]) stringMap.get("strb1"), (String[]) stringMap1.get("strb2"), (String[]) stringMap.get("strb2"));

                //如果相似度大于阈值
                if (sum.compareTo(grace) > 0) {
                    //绑定数据关联关系
                    addressMessage.setContrastId(baseAddrList.get(j).getId());
                    addressMessage.setContrastScore(sum);
                    addressMessage.setAddrCode(baseAddrList.get(j).getAddrCode());
                    //如果被比较值的街道为空，标准数据的街道不为空，就反写街道
                    if (StringUtils.isBlank(addressMessage.getStreet()) && !StringUtils.isBlank(baseAddrList.get(j).getStreet())) {
                        addressMessage.setStreet(baseAddrList.get(j).getStreet());
                    }
                    continue;
                }
            }
        }
        List<Base_addr> list = new ArrayList<>();
        list.add(addressMessage);

        //插入合并值
        List<Base_addr> baseAddrs1 = insertCompareByMerge(list);

        try {
            if (baseAddrs1 != null && baseAddrs1.size() > 0) {
                //插入基准值
                bs_addrMapper1.insert5_2_1(baseAddrs1);
                log.info("插入标准基准表");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return addressMessage;
    }


    /**
     * 增量数据处理时，基准数据与房屋数据碰撞时使用
     *
     * @param num
     * @param str
     * @param baseAddrList
     * @param addressMessage
     * @return
     */
    public Base_addr CompareRunnable4(int num, int str, List<Base_addr> baseAddrList, Base_addr addressMessage) {
        Map<String, String[]> strMapb = new HashMap<>();
        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();
        Map<String, String[]> strMapa = new HashMap<>();
        StringPasringServiceImpl stringParsingService = new StringPasringServiceImpl();

        //数字字母处理
        String shortAddr = AsciiUtil.RegProcess(addressMessage.getShortAddr());
        if (shortAddr.endsWith("号") || shortAddr.endsWith("室")) {
            shortAddr = shortAddr.substring(0, shortAddr.length() - 1);
        }
        //将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
        Map stringMap = stringParsingService.stringParse(shortAddr);
        strMapb.put("stra", (String[]) stringMap.get("strb2"));
        strMapa.put("stra", (String[]) stringMap.get("strb1"));

        /*用于判断的阈值*/
        BigDecimal grace = new BigDecimal(applicationProperty.getGrace2());

        for (int i = 0; i < baseAddrList.size(); i++) {
            boolean flag = false;
            if (!StringUtils.isEmpty(baseAddrList.get(i).getShortAddr())) {
                //数字字母处理
                String shortAddr1 = AsciiUtil.RegProcess(baseAddrList.get(i).getShortAddr());
                if (shortAddr1.endsWith("号") || shortAddr1.endsWith("室")) {
                    shortAddr1 = shortAddr1.substring(0, shortAddr1.length() - 1);
                }
                //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
                Map stringMap1 = stringParsingService.stringParse(shortAddr1);
                strMapa.put("strb", (String[]) stringMap1.get("strb1"));
                strMapb.put("strb", (String[]) stringMap1.get("strb2"));

                //之后这里可以使用正则匹配，匹配到关键字就调高分值
                //后续准备直接按照百分比上调分值。另外这个字典后期可能会前移到标准化那一步就做掉，提高后面效率。
                //如果地址包含此关键字，那么连续匹配时分值会递增加1
                if (shortAddr1.contains("罗东花园")) {
                    flag = true;
                }

                //数字和字符相似度匹配
                Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, num, false);
                Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, str, flag);
                BigDecimal suma = (BigDecimal) processresult1.get("sum");
                BigDecimal sumb = (BigDecimal) processresult2.get("sum");

                //计算相似度
                BigDecimal sum = processGradeService.getSum(suma, sumb, (String[]) stringMap1.get("strb1"), (String[]) stringMap.get("strb1"), (String[]) stringMap1.get("strb2"), (String[]) stringMap.get("strb2"));

                //如果相似度大于阈值
                if (sum.compareTo(grace) > 0) {
                    //绑定数据关联关系
                    addressMessage.setContrastScore(sum);
                    addressMessage.setContrastId(baseAddrList.get(i).getId());
                    addressMessage.setAddrCode(baseAddrList.get(i).getAddrCode());
                    //如果被比较值的街道为空，标准数据的街道不为空，就反写街道
                    if (StringUtils.isBlank(addressMessage.getStreet()) && !StringUtils.isBlank(baseAddrList.get(i).getStreet())) {
                        addressMessage.setStreet(baseAddrList.get(i).getStreet());
                    }
                    continue;
                }
            }
        }

        List<Base_addr> baseAddrss = new ArrayList<>();

        if (addressMessage == null) {
            baseAddrList = null;
            return addressMessage;
        }
        baseAddrss.add(addressMessage);
        if (!StringUtils.isBlank(addressMessage.getContrastId())) {
            bs_addrMapper1.insert3_2_1ES(baseAddrss);
            bs_addrMapper1.insert3_2_1(baseAddrss);
            baseAddrList = null;
            log.info("插入标准合并表");
        } else {
            bs_addrMapper1.insert5_2_1ES(baseAddrss);
            baseAddrList = null;
        }
        return addressMessage;
    }

    /**
     * 增量数据处理时，基准数据与房屋数据碰撞时使用
     *
     * @param num
     * @param str
     * @param baseAddrList
     * @param addressMessage
     * @return
     */
    public Base_addr CompareRunnable5(int num, int str, List<Base_addr> baseAddrList, Base_addr addressMessage, Discrepancy discrepancy) {
        Map<String, String[]> strMapb = new HashMap<>();
        Map<String, String[]> strMapa = new HashMap<>();
        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();
        StringPasringServiceImpl stringParsingService = new StringPasringServiceImpl();

        //数字字母处理
        String shortAddr = AsciiUtil.RegProcess(addressMessage.getShortAddr());
        if (shortAddr.endsWith("号") || shortAddr.endsWith("室")) {
            shortAddr = shortAddr.substring(0, shortAddr.length() - 1);
        }
        //将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
        Map stringMap = stringParsingService.stringParse(shortAddr);
        strMapa.put("stra", (String[]) stringMap.get("strb1"));
        strMapb.put("stra", (String[]) stringMap.get("strb2"));

        /*用于判断的阈值*/
        BigDecimal grace = new BigDecimal(applicationProperty.getGrace());

        for (int i = 0; i < baseAddrList.size(); i++) {

//            //如果姓相同就继续，不相同就判定为不是同一个人
//            if (!StringUtils.isBlank(addressMessage.getName1()) && !StringUtils.isBlank(baseAddrList.get(i).getName1())) {
//                if (!addressMessage.getName1().split("")[0].equals(baseAddrList.get(i).getName1().split("")[0])
//                        && !baseAddrList.get(i).getName1().startsWith("*") && !addressMessage.getName1().startsWith("*")) {
//                    continue;
//                }
//            }

            boolean flag = false;
            if (!StringUtils.isEmpty(baseAddrList.get(i).getShortAddr())) {
                //数字字母处理
                String shortAddr1 = AsciiUtil.RegProcess(baseAddrList.get(i).getShortAddr());
                if (shortAddr1.endsWith("号") || shortAddr1.endsWith("室")) {
                    shortAddr1 = shortAddr1.substring(0, shortAddr1.length() - 1);
                }
                //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
                Map stringMap1 = stringParsingService.stringParse(shortAddr1);
                strMapb.put("strb", (String[]) stringMap1.get("strb2"));
                strMapa.put("strb", (String[]) stringMap1.get("strb1"));

                //数字和字符相似度匹配
                Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, str, flag);
                Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, num, false);
                BigDecimal sumb = (BigDecimal) processresult2.get("sum");
                BigDecimal suma = (BigDecimal) processresult1.get("sum");

                if(sumb.compareTo(new BigDecimal(0))<=0 || suma.compareTo(new BigDecimal(0))<=0){
                    continue;
                }

                //计算相似度
                BigDecimal sum = processGradeService.getSum(suma, sumb, (String[]) stringMap1.get("strb1"), (String[]) stringMap.get("strb1"), (String[]) stringMap1.get("strb2"), (String[]) stringMap.get("strb2"));

                //如果相似度大于阈值
                if (sum.compareTo(grace) > 0) {
                    //绑定数据关联关系
                    addressMessage.setAddrCode(baseAddrList.get(i).getAddrCode());
                    addressMessage.setContrastScore(sum);
                    addressMessage.setContrastId(baseAddrList.get(i).getId());
                    //如果被比较值的街道为空，标准数据的街道不为空，就反写街道
                    if (StringUtils.isBlank(addressMessage.getStreet()) && !StringUtils.isBlank(baseAddrList.get(i).getStreet())) {
                        addressMessage.setStreet(baseAddrList.get(i).getStreet());
                    }
                    continue;
                }
            }
        }

        List<Base_addr> baseAddress = new ArrayList<>();
        baseAddress.add(addressMessage);
        if (!StringUtils.isBlank(addressMessage.getContrastId())) {
            bs_addrMapper.insertDepositData(baseAddress, discrepancy.getDepositMerge());
            baseAddrList = null;
            log.info("插入标准合并表");
            return null;
        }
        bs_addrMapper.insertDepositData(baseAddress, discrepancy.getDepositBasics());
        baseAddrList = null;
        return addressMessage;
    }

    /**
     * 插入合并值
     *
     * @param list1
     * @return 返回基准值
     */
    public List<Base_addr> insertCompareByMerge(List<Base_addr> list1) {
        /*插入数据库阶段*/
        List<Base_addr> mergeAddrs = new ArrayList<>();
        List<Base_addr> baseAddrss = new ArrayList<>();
        for (int k = 0; k < list1.size(); k++) {
            if (list1.get(k) == null) {
                continue;
            }
            if (!StringUtils.isBlank(list1.get(k).getContrastId())) {
                mergeAddrs.add(list1.get(k));
            } else {
                baseAddrss.add(list1.get(k));
            }
        }

        try {
            if (mergeAddrs != null && mergeAddrs.size() > 0) {
                bs_addrMapper1.insert3_2_1(mergeAddrs);
                log.info("插入标准合并表");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return baseAddrss;
    }

    /**
     * 地址切割短地址并且打分
     *
     * @param base_addr  需要切割的数据
     * @param reg        省市区街道字符串
     * @param allMessage 省市区街道和分值占比数据
     * @return 处理完后的数据
     */
    public Base_addr getBaseAddr(Base_addr base_addr, String reg, Map<String, Object> allMessage) {
        //如果长地址为空就退出
        if (StringUtils.isBlank(base_addr.getAddrSj())) {
            return null;
        }

        //省市区切割、反写、打分操作
        base_addr = base_addrService.addrSet(base_addr, allMessage);

        String shortAddr = base_addr.getShortAddr();
        //如果去掉省市区街道后短地址为空
        if (StringUtils.isBlank(shortAddr)) {
            return null;
        }
        //短地址特殊情况收尾操作
        shortAddr = AsciiUtil.SpecialEndHandl(shortAddr);
        //去除短地址中冗余的省市区
        base_addr.setShortAddr(shortAddr.replaceAll(reg, ""));

        //初始化最早收件时间和最晚收件时间
        if (base_addr.getEarliestTime() == null) {
            base_addr.setEarliestTime(base_addr.getCreateTime());
        }
        if (base_addr.getLatestTime() == null) {
            base_addr.setLatestTime(base_addr.getCreateTime());
        }

        base_addr.setCreateTime(base_addr.getCreateTime());
        return base_addr;
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
        if (searchContentForm.getTableData() != null) {
            List<AdvancedSearch> data = searchContentForm.getTableData();
            for (AdvancedSearch datum : data) {
                if (StringUtils.isBlank(datum.getCondition())) {
                    RspCode errCode = RspCode.FAILURE;
                    errCode.setDescription(errCode, "高级搜索条件值不能为空");
                    throw new TMCException(errCode);
                }
            }
        }
        if (searchContentForm.getSearchText() == null && (searchContentForm.getTableData() == null || searchContentForm.getTableData().size() < 1)) {
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "请输入有效参数");
            throw new TMCException(errCode);
        }
        //搜索条件
        if (searchContentForm.getSearchText() != null) {
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

        searchContentForm.setCurrentPage((searchContentForm.getCurrentPage() - 1) * searchContentForm.getPageSize());
        //如果是根据地址查询，查标准化地址基准表
        List<PersonMsgVo> list = baseAddrMapper.searchContent(searchContentForm);

        if (list == null || list.size() == 0) {
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
                if (personMsgVo.getContrastId().equals(peopleList.getContrastId())) {
                    baseAddrs.add(peopleList);
                }
            }

            //标准地址人员详情
            for (Base_addr basicsList : basicsLists) {
                if (personMsgVo.getContrastId().equals(basicsList.getId())) {
                    personMsgVo.setBasiscMsg(basicsList);
                }
            }
            personMsgVo.setPeopleList(baseAddrs);

            //合并地址数量
            for (MergeNums mergeNum : mergeNums) {
                if (personMsgVo.getId().equals(mergeNum.getId())) {
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
        if (personMsgVo != null) {
            List<PersonMsgVo> personMsgVos = baseAddrMapper.searchPeopleListByBasic(personMsgVo.getContrastId());
            personMsgVos1.addAll(personMsgVos);
            List<PersonMsgVo> personMsgVos2 = baseAddrMapper.searchPeopleListByContrastId(personMsgVo.getContrastId());
            personMsgVos1.addAll(personMsgVos2);
            personMsgVos1 = addressRegex(personMsgVos1);
        } else {
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
    public AddressListMsgVo searchAddressList(String id, Integer currentPage, Integer pageSize) {
        //获取总条数
        long total = baseAddrMapper.getTotalById(id);
        currentPage = (currentPage - 1) * pageSize;
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
    public void sumAddress(String addressId, String keyData) {
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
     *
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
     *
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
        List<ChartData> chartDataList = baseAddrMapper.searchMergeKdNum(id, year);
        List<ChartData> chartData = baseAddrMapper.searchBasicsKdNum(id, year);
        if (chartDataList == null) {
            chartDataList = new ArrayList<>();
        }
        for (ChartData data : chartDataList) {
            if (chartData.get(0).getTime().equals(data.getTime())) {
                data.setNumber(String.valueOf(Integer.valueOf(data.getNumber()) + 1));
                chartData = null;
                break;
            }
        }
        if (chartData != null) {
            chartDataList.add(chartData.get(0));
        }
        //每个月的快递数
        for (ChartData data : chartDataList) {
            List<AcceptDeliveryPeoperVo> acceptDeliveryPeoperVos = baseAddrMapper.searchMergeAcceptDelivery(id, data.getTime(), year);
            acceptDeliveryPeoperVos.addAll(baseAddrMapper.searchBaiscAcceptDelivery(id, data.getTime(), year));
            data.setAcceptDeliveryPeoperVo(acceptDeliveryPeoperVos);
        }
        return chartDataList;
    }

    /**
     * 根据两个地址获取相似度分值
     *
     * @param basicsAddr
     * @param matchAddr
     * @return
     */
    @Override
    public String querySimilarity(String basicsAddr, String matchAddr) {
        boolean flag = false;
        if (StringUtils.isBlank(basicsAddr)) {
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "请输入基准地址");
            throw new TMCException(errCode);
        }

        if (StringUtils.isBlank(matchAddr)) {
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "请输入对比地址");
            throw new TMCException(errCode);
        }

        //基准地址处理特殊字符串
        basicsAddr = AsciiUtil.RegProcess(basicsAddr);
        basicsAddr = AsciiUtil.SpecialEndHandl(basicsAddr);

        //对比地址处理特殊字符串
        matchAddr = AsciiUtil.RegProcess(matchAddr);
        matchAddr = AsciiUtil.SpecialEndHandl(matchAddr);
        String reg = getReg();
        //去除省市区
        basicsAddr = basicsAddr.replaceAll(reg, "");
        matchAddr = matchAddr.replaceAll(reg, "");

        Map<String, String[]> strMapa = new HashMap<>();
        Map<String, String[]> strMapb = new HashMap<>();
        StringPasringServiceImpl stringParsingService = new StringPasringServiceImpl();
        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();

        //数字字母处理
        String shortAddr = AsciiUtil.RegProcess(basicsAddr);
        //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
        Map stringMap = stringParsingService.stringParse(shortAddr);
        strMapa.put("stra", (String[]) stringMap.get("strb1"));
        strMapb.put("stra", (String[]) stringMap.get("strb2"));

        //数字字母处理
        String shortAddr1 = AsciiUtil.RegProcess(matchAddr);
        //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
        Map stringMap1 = stringParsingService.stringParse(shortAddr1);
        strMapb.put("strb", (String[]) stringMap1.get("strb2"));
        strMapa.put("strb", (String[]) stringMap1.get("strb1"));


        if (shortAddr1.contains("罗东花园")) {
            flag = true;
        }
        /*返回一个map出来，其中包括计算用的Double的sum,String的integerer[]*/
        //数字和字符相似度匹配
        Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, Integer.valueOf(applicationProperty.getBlockSizeByNum()), false);
        BigDecimal suma = (BigDecimal) processresult1.get("sum");
        log.info("数字得分： {}", suma);

        Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, Integer.valueOf(applicationProperty.getBlockSizeByStr()), flag);
        BigDecimal sumb = (BigDecimal) processresult2.get("sum");
        log.info("字符得分： {}", sumb);

        //计算相似度
        BigDecimal sum = processGradeService.getSum(suma, sumb, (String[]) stringMap1.get("strb1"), (String[]) stringMap.get("strb1"), (String[]) stringMap1.get("strb2"), (String[]) stringMap.get("strb2"));
        sum = sum.divide(new BigDecimal(1), 4, BigDecimal.ROUND_HALF_UP);
        return sum.toString();
    }

    @Override
    public List<BaseAddrByPhone> queryMsgByPhone(String phone) {

        //如果查不到该数据
        if (phone == null) {
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "请输入手机号");
            throw new TMCException(errCode);
        }

//        List<BaseAddrByPhone> baseAddrs = bs_addrMapper1.queryMsgByPhone(phone);
        List<BaseAddrByPhone> baseAddrs = bs_addrMapper.queryMsgByPhone(phone);

        //将快递地址转为相对标准地址
        for (BaseAddrByPhone baseAddr : baseAddrs) {
            StringBuilder addr = new StringBuilder();
            if (!StringUtils.isBlank(baseAddr.getProvince())) {
                addr.append(baseAddr.getProvince());
            }
            if (!StringUtils.isBlank(baseAddr.getCity())) {
                addr.append(baseAddr.getCity());
            }
            if (!StringUtils.isBlank(baseAddr.getArea())) {
                addr.append(baseAddr.getArea());
            }
            if (!StringUtils.isBlank(baseAddr.getStreet())) {
                addr.append(baseAddr.getStreet());
            }
            if (!StringUtils.isBlank(baseAddr.getShortAddr())) {
                addr.append(baseAddr.getShortAddr());
            }
            baseAddr.setAddress(addr.toString());
        }


        //查询标准数据表，填补标准数据的地址和经纬度


//        for (int i = 0; i < baseAddrs.size()-1; i++) {
//            for (int j = i+1; j < baseAddrs.size(); j++) {
//                if(baseAddrs.get(j).getP3type() == 100){
//                    continue;
//                }
//                String s = similarity(baseAddrs.get(i).getShortAddr(), baseAddrs.get(j).getShortAddr());
//                if(Double.valueOf(s)>0.76){
//                    baseAddrs.get(j).setP3type(100);
//                }
//            }
//        }
//
//        List<Base_addr> baseAddrs1 = new ArrayList<>();
//
//        for (Base_addr baseAddr : baseAddrs) {
//            if(baseAddr.getP3type() != 100){
//                baseAddrs1.add(baseAddr);
//            }
//        }
        return baseAddrs;
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
     * 根据两个地址获取相似度分值
     *
     * @param basicsAddr
     * @param matchAddr
     * @return
     */
    public String similarity(String basicsAddr, String matchAddr) {
        if (StringUtils.isBlank(basicsAddr) || StringUtils.isBlank(matchAddr)) {
            return "1";
        }
        Map<String, String[]> strMapa = new HashMap<>();
        Map<String, String[]> strMapb = new HashMap<>();
        StringPasringServiceImpl stringParsingService = new StringPasringServiceImpl();
        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();

        //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
        Map stringMap = stringParsingService.stringParse(basicsAddr);
        strMapa.put("stra", (String[]) stringMap.get("strb1"));
        strMapb.put("stra", (String[]) stringMap.get("strb2"));

        //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
        Map stringMap1 = stringParsingService.stringParse(matchAddr);
        strMapb.put("strb", (String[]) stringMap1.get("strb2"));
        strMapa.put("strb", (String[]) stringMap1.get("strb1"));

        /*返回一个map出来，其中包括计算用的Double的sum,String的integerer[]*/
        //数字和字符相似度匹配
        Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, Integer.valueOf(applicationProperty.getBlockSizeByNum()), false);
        BigDecimal suma = (BigDecimal) processresult1.get("sum");

        Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, Integer.valueOf(applicationProperty.getBlockSizeByStr()), false);
        BigDecimal sumb = (BigDecimal) processresult2.get("sum");

        //计算相似度
        BigDecimal sum = processGradeService.getSum(suma, sumb, (String[]) stringMap1.get("strb1"), (String[]) stringMap.get("strb1"), (String[]) stringMap1.get("strb2"), (String[]) stringMap.get("strb2"));
        return sum.toString();
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
     *
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
            List<ChartData> chartDataList = baseAddrMapper.searchMergeKdNum(personMsgVo.getId(), year);
            List<ChartData> chartData = baseAddrMapper.searchBasicsKdNum(personMsgVo.getId(), year);
            if (chartDataList == null) {
                chartDataList = new ArrayList<>();
            }
            for (ChartData data : chartDataList) {
                if (chartData.get(0).getTime().equals(data.getTime())) {
                    data.setNumber(String.valueOf(Integer.valueOf(data.getNumber()) + 1));
                    chartData = null;
                    break;
                }
            }
            if (chartData != null) {
                chartDataList.add(chartData.get(0));
            }
            personMsgVo.setChartData(chartDataList);
        }
        return personMsgVos;
    }

    //配置的区的省市区街道关键字正则生成，并去除对应参数中包含的关键字
    public String getReg() {
        //配置的市的编号
        String cityCode = applicationProperty.getCityCode();
        StringBuilder regex = new StringBuilder();

        //查询市
        Bs_city cityName = bs_addrMapper.getCity(cityCode);
        regex.append(cityName.getCityName());

        //查询省
        Bs_province provinceName = bs_addrMapper.getProvince(cityName.getProvinceCode());
        regex.append("|").append(provinceName.getProvinceName()).append("|").append(provinceName.getShortName());

        //查询区
        List<Bs_area> areaNames = bs_addrMapper.getArea(cityCode);

        //todo 街道不需要去除
        for (Bs_area areaName : areaNames) {
            regex.append("|").append(areaName.getAreaName());
//            //查询街道
            List<Bs_street> streetNames = bs_addrMapper.getStreetName(areaName.getAreaCode());
            for (Bs_street streetName : streetNames) {
                regex.append("|").append(streetName.getStreetName()).append("|").append(streetName.getShortName());
            }
        }
        log.info(regex.toString());
        return regex.toString();
    }

    /**
     * 获取省市区街道集合对象
     *
     * @return
     */
    public Map<String, Object> getMap() {
        //用来存放所有省市区街道集合和分值对象的
        Map<String, Object> allMessage = new HashMap<>();

        List<Bs_province> provinceMessage = new ArrayList<>();
        List<Bs_area> areaMessage = new ArrayList<>();
        List<Bs_city> cityMessage = new ArrayList<>();
        List<Bs_street> streetNames;
        List<Bs_street> streetMessage = new ArrayList<>();

        //配置的市的编号
        String cityCode = applicationProperty.getCityCode();
        //查询市
        Bs_city cityName = bs_addrMapper.getCity(cityCode);
        //查询省
        Bs_province provinceName = bs_addrMapper.getProvince(cityName.getProvinceCode());
        //查询区
        List<Bs_area> areaNames = bs_addrMapper.getArea(cityCode);
        //查询街道不需要去除
        for (Bs_area areaName : areaNames) {
            //查询街道
            streetNames = bs_addrMapper.getStreetName(areaName.getAreaCode());
            streetMessage.addAll(streetNames);
        }

        //查询除了浙江省以外的省
        List<Bs_province> allProvince = bs_addrMapper.getAllProvince();
        provinceMessage.add(provinceName);
        cityMessage.add(cityName);
        areaMessage.addAll(areaNames);

        allMessage.put("streetMessage", streetMessage);
        allMessage.put("provinceMessage", provinceMessage);
        allMessage.put("cityMessage", cityMessage);
        allMessage.put("areaMessage", areaMessage);
        allMessage.put("allProvince", allProvince);

        //分值计算
        BigDecimal dec2 = new BigDecimal(-2);
        BigDecimal dec1 = new BigDecimal(-5);
        BigDecimal dec3 = new BigDecimal(-1);
        BigDecimal dec5 = new BigDecimal(100);
        BigDecimal dec4 = new BigDecimal(0);
        BigDecimal dec6 = new BigDecimal(0);
        BigDecimal dec7 = new BigDecimal(-20);
        BigDecimal dec8 = new BigDecimal(-10);

        allMessage.put("dec2", dec2);
        allMessage.put("dec1", dec1);
        allMessage.put("dec3", dec3);
        allMessage.put("dec5", dec5);
        allMessage.put("dec4", dec4);
        allMessage.put("dec6", dec6);
        allMessage.put("dec7", dec7);
        allMessage.put("dec8", dec8);
        return allMessage;
    }

    /**
     * 增量数据处理方法
     * @param baseAddr 源数据
     * @param reg 省市区街道正则字符串
     * @param allMessage 温州所有省市区街道的集合
     */
    @Async("asyncPromiseExecutor")
    @Override
    public void insertMsgProcess1(Base_addr baseAddr,String reg, Map<String, Object> allMessage) {
        //过滤不是温州的数据，格式化市为温州市
        if(!"温州".equals(baseAddr.getCity()) && !"温州市".equals(baseAddr.getCity()) && !"577".equals(baseAddr.getCity())){
            return;
        }
        baseAddr.setCity("温州市");

        //对手机号进行合法性校验
        if (StringUtils.isBlank(baseAddr.getPhone()) || baseAddr.getPhone().length() < 5) {
            bs_addrMapper.insertDiscard(baseAddr);
            return;
        }

        //第一步：短地址切割方法
        Base_addr base_addr = getBaseAddr(baseAddr, reg, allMessage);

        if (base_addr == null || StringUtils.isBlank(base_addr.getShortAddr())|| !"温州市".equals(base_addr.getCity())) {
            bs_addrMapper.insertDiscard(baseAddr);
            return;
        }

        int blockSizeByStr = Integer.valueOf(applicationProperty.getBlockSizeByStr());
        int blockSizeByNum = Integer.valueOf(applicationProperty.getBlockSizeByNum());

        String phone = base_addr.getPhone();
        String name = base_addr.getName1();
        //手机号包含*,手机号反写，反写失败就扔进垃圾表
        if (!StringUtils.isBlank(phone) && phone.length() >= 7 && phone.contains("*")) {
            String name1 = null;
            if(!StringUtils.isBlank(name) && name.length() >= 1 && !"*".equals(name.split("")[0])){
                name1 = name;
            }

            //获取比较数据
            List<Base_addr> addressMessage = bs_addrMapper.getDate1(base_addr.getArea(), base_addr.getStreet(), base_addr.getShortPhone(),name1);
            //手机号反写方法，反写失败就是无效数据
            Base_addr baseAddr1 = getProcess(blockSizeByNum, blockSizeByStr, base_addr, addressMessage, false);
            if (baseAddr1 == null || baseAddr1.getPhone().contains("*")) {
                bs_addrMapper.insertDiscard(baseAddr);
                return;
            }
        }

        //第二步：相似度碰撞
        List<Base_addr> addressMessage = bs_addrMapper.getBaseAddrs(base_addr.getPhone());
        Base_addr baseAddr1 = getProcess(blockSizeByNum, blockSizeByStr, base_addr, addressMessage, true);
        //如果撞到了 说明是合并数据
        if (!TextUtils.isEmpty(baseAddr1)) {
            return;
        }

        //没撞到的数据是基准值数据
        base_addr.setP2type(223);
        base_addr.setMergeNum(0);
        base_addr.setP5type(1);
        base_addr.setTableName("insert_addr");
        base_addr.setEarliestTime(base_addr.getCreateTime());
        base_addr.setLatestTime(base_addr.getCreateTime());
        bs_addrMapper.insert5ES(base_addr);
        bs_addrMapper.insert5(base_addr);

        //街道为空就不处理
        if(TextUtils.isEmpty(base_addr.getStreet())){
            return;
        }
        //碰撞房屋表，对撞到的数据赋值房屋编号
        List<Base_addr> volList = bs_addrMapper1.selectBaseAddr2(base_addr.getArea(), base_addr.getStreet());
        CompareRunnable4(blockSizeByNum, blockSizeByStr, volList, base_addr);
        return;
    }
}
