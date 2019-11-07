package com.service.lwaddress.impl;

import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.*;
import com.dao.db3.lwaddr.Base_addrMapper1;
import com.dao.entity.lwaddress.*;
import com.dto.vo.BaseAddrVo;
import com.service.lwaddress.Base_addrService;
import com.service.lwaddress.MsgSearchService;
import com.service.lwaddress.ProcessGradeService;
import com.service.lwaddress.StringParsingService;
import com.utils.sys.lwaddress.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("msgSearchService")
public class MsgSearchServiceImpl implements MsgSearchService {

    @Autowired
    private Base_addrMapper1 baseAddrMapper1;
    @Autowired
    private Base_addrMapper baseAddrMapper;
    @Autowired
    private HgApplicationProperty applicationProperty;
    @Autowired
    Base_addrService base_addrService;

    @Autowired
    Bs_cityMapper bs_cityMapper;
    @Autowired
    Bs_areaMapper bs_areaMapper;
    @Autowired
    Bs_streetMapper bs_streetMapper;
    @Autowired
    Bs_provinceMapper bs_provinceMapper;
    @Autowired
    StringParsingService stringParsingService;
    @Autowired
    ProcessGradeService processGradeService;


    @Override
    public List<BaseAddrVo> queryMsg(String name, String phone, String address) {
        List<BaseAddrVo> msgList = new ArrayList<>();
        List<BaseAddrVo> saveList = new ArrayList<>();
        List<BaseAddrVo> outList = new ArrayList<>();
        Base_addr base_addr = new Base_addr();


        BigDecimal maxgrace = new BigDecimal(0.0);

        /*第一步处理准备动作*/
        Map<String, Object> allMessage = new HashMap<>();
        List<Bs_province> provinceMessage = new ArrayList<>();
        List<Bs_city> cityMessage = new ArrayList<>();
        List<Bs_area> areaMessage = new ArrayList<>();
        List<Bs_street> streetMessage = new ArrayList<>();

        provinceMessage = bs_provinceMapper.selectShortNameAndProvinceName();
        cityMessage = bs_cityMapper.selectCityAllName();
        areaMessage = bs_areaMapper.selectAreaMessage();
        streetMessage = bs_streetMapper.selectStreetMessage();

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


        /*第一步处理*/
        base_addr = base_addrService.addrSet(address, allMessage);

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
            processresult1 = processGradeService.processDemo(strMapa, 3);
            processresult2 = processGradeService.processDemo(strMapb, 3);

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
}
