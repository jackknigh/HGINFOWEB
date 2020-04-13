/* Copyright (C) 2019-2019 Hangzhou HSH Co. Ltd.
 * All right reserved.*/
package com.utils.sys.lwaddress;

import com.config.HgApplicationProperty;
import com.dao.db3.lwaddr.Base_addrMapper1;
import com.dao.entity.lwaddress.Base_addr;
import com.service.lwaddress.impl.NameProcessServiceImpl;
import com.service.lwaddress.impl.ProcessGradeServiceImpl;
import com.service.lwaddress.impl.ProcessServiceImpl;
import com.service.lwaddress.impl.StringPasringServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 相似度匹配线程
 */

public class MatchRunnable implements Callable<HashMap<String, Object>> {

    @Autowired
    private Base_addrMapper1 baseAddrMapper1 = ApplicationContextProvider.getBean(Base_addrMapper1.class);
    @Autowired
    private HgApplicationProperty applicationProperty = ApplicationContextProvider.getBean(HgApplicationProperty.class);

    private static final Logger log = LoggerFactory.getLogger(ProcessServiceImpl.class);
    private int num;
    private int str;
    private Base_addr baseAddrBasics;
    private List<Base_addr> addressMessage;
    private int start;
    private int end;
    private boolean flag;

    public MatchRunnable(int num, int str, Base_addr baseAddrBasics, List<Base_addr> addressMessage, int start, int end, boolean flag) {
        this.num = num;
        this.str = str;
        this.baseAddrBasics = baseAddrBasics;
        this.addressMessage = addressMessage;
        this.start = start;
        this.end = end;
        this.flag = flag;
    }

    @Override
    public HashMap<String, Object> call() {
        HashMap<String, Object> match = match(num, str, baseAddrBasics, addressMessage, start, end, flag);
        return match;
    }

    public HashMap<String, Object> match(int num, int str, Base_addr baseAddrBasics, List<Base_addr> addressMessage, int start, int end, boolean flag) {

        //初始化对象
        HashMap<String, Object> map = new HashMap<>();
        List<Base_addr> insertMessage = new ArrayList<>();
        Map<String, String[]> strMapa = new HashMap<>();
        Map<String, String[]> strMapb = new HashMap<>();
        StringPasringServiceImpl stringParsingService = new StringPasringServiceImpl();
        NameProcessServiceImpl nameProcessService = new NameProcessServiceImpl();
        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();

        String[] phone1 = null;
        String[] name1a = null;
        if (!StringUtils.isBlank(baseAddrBasics.getPhone())) {
            phone1 = baseAddrBasics.getPhone().split("");
        }
        if (!StringUtils.isBlank(baseAddrBasics.getName1())) {
            name1a = baseAddrBasics.getName1().split("");
        }

        //数字字母处理
        String shortAddr = AsciiUtil.RegProcess(baseAddrBasics.getShortAddr());

        //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
        Map stringMap = stringParsingService.stringParse(shortAddr);
        strMapa.put("stra", (String[]) stringMap.get("strb1"));
        strMapb.put("stra", (String[]) stringMap.get("strb2"));

        for (int i = start; i < end; i++) {

            /*用于判断的阈值*/
            if (addressMessage.get(i).getP2type() == 222 || addressMessage.get(i).getP2type() == 223 || addressMessage.get(i).getP2type() == 224) {
                continue;
            }
            Base_addr bs_addr = new Base_addr();
            BigDecimal grace = new BigDecimal(applicationProperty.getGrace());

            if (!StringUtils.isEmpty(addressMessage.get(i).getShortAddr())) {

                //如果姓相同就继续，不相同就判定为不是同一个人
                if(!StringUtils.isBlank(baseAddrBasics.getName1()) && !StringUtils.isBlank(addressMessage.get(i).getName1())){
                    if(!baseAddrBasics.getName1().split("")[0].equals(addressMessage.get(i).getName1().split("")[0])
                            && !addressMessage.get(i).getName1().startsWith("*") && !baseAddrBasics.getName1().startsWith("*")){
                        continue;
                    }
                }

                //数字字母处理
                String shortAddr1 = AsciiUtil.RegProcess(addressMessage.get(i).getShortAddr());
                //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串,key=strb是合并值，key=stra是基准值
                Map stringMap1 = stringParsingService.stringParse(shortAddr1);
                strMapa.put("strb", (String[]) stringMap1.get("strb1"));
                strMapb.put("strb", (String[]) stringMap1.get("strb2"));

                //满分
                BigDecimal bsum = new BigDecimal(100);
                //数字及格线
                BigDecimal numPass = new BigDecimal(applicationProperty.getNumPass());
                //字符及格线
                BigDecimal strPass = new BigDecimal(applicationProperty.getStrPass());

                BigDecimal sum = new BigDecimal(0);

                //数字和字符相似度匹配
                Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, num);

                //理论最大值
                BigDecimal asummax = (BigDecimal)processresult1.get("asummax");
                //实际得分
                BigDecimal asum = (BigDecimal)processresult1.get("asum");

                Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, str);

                //理论最大值
                BigDecimal asummax1 = (BigDecimal)processresult2.get("asummax");
                //实际得分
                BigDecimal asum1 = (BigDecimal)processresult2.get("asum");

                //数字字符实际得分与最大分值占比
                BigDecimal suma = (BigDecimal) processresult1.get("sum");
                BigDecimal sumb = (BigDecimal) processresult2.get("sum");

                //如果标准短地址包含匹配数据的短地址，就直接算满分
                if(!shortAddr.contains(shortAddr1)){
                    BigDecimal divide = new BigDecimal(0);
                    if(asummax != null && asum != null){
                        divide = bsum.divide(asummax,4, BigDecimal.ROUND_HALF_UP).multiply(asum).divide(numPass,4, BigDecimal.ROUND_HALF_UP);
                    }

                    //如果数字没到及格线
                    if(divide.compareTo(new BigDecimal(1))<0){
                        continue;
                    }

                    //如果数字基本满分，那么字符得分就能低一点
                    if(divide.compareTo(new BigDecimal(1.8))>=0){
                        strPass = new BigDecimal(15);
                    }

                    //如果字符没到及格线
                    BigDecimal value = new BigDecimal(0);
                    if(asummax1 != null && asum1 != null) {
                        value = bsum.divide(asummax1, 4, BigDecimal.ROUND_HALF_UP).multiply(asum1).divide(strPass, 4, BigDecimal.ROUND_HALF_UP);
                    }

                    if (value.compareTo(new BigDecimal(1))<0) {
                        continue;
                    }

                    //计算相似度
                    sum = processGradeService.getSum(suma, sumb, (String[]) stringMap1.get("strb1"),(String[]) stringMap.get("strb1"),(String[]) stringMap1.get("strb2"),(String[]) stringMap.get("strb2"));
                    bs_addr.setNumberScore((String) processresult1.get("integer"));
                    bs_addr.setStrScore((String) processresult2.get("integer"));
                }else {
                    sum = new BigDecimal(100);
                    bs_addr.setNumberScore((String) processresult1.get("integer")+"(包含关系)");
                    bs_addr.setStrScore((String) processresult2.get("integer")+"(包含关系)");
                }

                //如果相似度大于阈值或者基准短地址包含比较短地址
                if (sum.compareTo(grace) > 0) {

                    //如果是分割集合操作就需要修改原先合并值和基准值的关联id
                    if (flag) {
                        log.info("是分割操作，需要修改关联id");
                        baseAddrMapper1.updateMerge(addressMessage.get(i).getId(), baseAddrBasics.getId());
                    }
                    bs_addr.setId(addressMessage.get(i).getId());
                    bs_addr.setName1(addressMessage.get(i).getName1());
                    bs_addr.setPhone(addressMessage.get(i).getPhone());
                    bs_addr.setAddrSj(addressMessage.get(i).getAddrSj());
                    bs_addr.setContrastScore(sum);
                    bs_addr.setContrastId(baseAddrBasics.getId());
                    bs_addr.setShortAddr(addressMessage.get(i).getShortAddr());
                    bs_addr.setOldPhone(addressMessage.get(i).getOldPhone());
                    bs_addr.setOldName1(addressMessage.get(i).getOldName1());
                    bs_addr.setShortPhone(addressMessage.get(i).getShortPhone());
                    bs_addr.setProvince(addressMessage.get(i).getProvince());
                    bs_addr.setProWeight(addressMessage.get(i).getProWeight());
                    bs_addr.setCity(addressMessage.get(i).getCity());
                    bs_addr.setCityWeight(addressMessage.get(i).getCityWeight());
                    bs_addr.setArea(addressMessage.get(i).getArea());
                    bs_addr.setAreaWeight(addressMessage.get(i).getAreaWeight());
                    bs_addr.setStreet(addressMessage.get(i).getStreet());
                    bs_addr.setStreWeight(addressMessage.get(i).getStreWeight());
                    bs_addr.setAlley(addressMessage.get(i).getAlley());
                    bs_addr.setAlleyNum(addressMessage.get(i).getAlleyNum());
                    bs_addr.setPlot(addressMessage.get(i).getPlot());
                    bs_addr.setBuildingNum(addressMessage.get(i).getBuildingNum());
                    bs_addr.setFloorNum(addressMessage.get(i).getFloorNum());
                    bs_addr.setUnitNum(addressMessage.get(i).getUnitNum());
                    bs_addr.setDoorplateNum(addressMessage.get(i).getDoorplateNum());
                    bs_addr.setMergeNum(addressMessage.get(i).getMergeNum());
                    bs_addr.setEarliestTime(addressMessage.get(i).getEarliestTime());
                    bs_addr.setLatestTime(addressMessage.get(i).getLatestTime());
                    bs_addr.setMulWeight(addressMessage.get(i).getMulWeight());
                    bs_addr.setAddrSign1(addressMessage.get(i).getAddrSign1());
                    bs_addr.setAddrSign2(addressMessage.get(i).getAddrSign2());
                    bs_addr.setRowId(addressMessage.get(i).getRowId());
                    bs_addr.setTableName(addressMessage.get(i).getTableName());
                    bs_addr.setCountId(addressMessage.get(i).getCountId());
                    bs_addr.setCreateTime(DateUtil.getCurrDateTimeStr());
                    bs_addr.setIdCard(addressMessage.get(i).getIdCard());
                    bs_addr.setLatestTime(addressMessage.get(i).getLatestTime());
                    bs_addr.setEarliestTime(addressMessage.get(i).getEarliestTime());
                    bs_addr.setP2type(222);
                    if (addressMessage.get(i).getP3type() != null) {
                        bs_addr.setP3type(223);
                    } else {
                        bs_addr.setP3type(0);
                    }
                    addressMessage.get(i).setP2type(222);

                    boolean result = false;
                    //如果是根据手机号清洗就需要反写
                    if ("0".equals(applicationProperty.getStandardAddress()) || "2".equals(applicationProperty.getStandardAddress())) {
                        if (phone1 != null) {
                            /*手机反写*/
                            if (addressMessage.get(i).getPhone() != null) {
                                if (baseAddrBasics.getPhone().contains("*") && nameProcessService.phoneLikedProcess(phone1, addressMessage.get(i).getPhone().split(""))) {
                                    baseAddrBasics.setPhone(addressMessage.get(i).getPhone());
                                    result = true;
                                }

                                //用修改的基准值手机号反写合并值手机号
                                if (addressMessage.get(i).getPhone().contains("*")
                                        && nameProcessService.a2nameLikedProcess(phone1, addressMessage.get(i).getPhone().split(""))) {
                                    addressMessage.get(i).setPhone(baseAddrBasics.getPhone());
                                    bs_addr.setPhone(baseAddrBasics.getPhone());
                                    result = true;
                                }
                                phone1 = addressMessage.get(i).getPhone().split("");
                            }
                        }

                        if (name1a != null && addressMessage.get(i).getName1() != null) {
                            //合并值反写基准值，不带别名的
                            if (baseAddrBasics.getName1().contains("*")
                                    && nameProcessService.nameLikedProcess(name1a, addressMessage.get(i).getName1().split(""))) {
                                baseAddrBasics.setName1(addressMessage.get(i).getName1());
                                result = true;
                            }

                            //基准值反写合并值，不带别名
                            if (addressMessage.get(i).getName1().contains("*")
                                    && nameProcessService.nameLikedProcess(addressMessage.get(i).getName1().split(""), name1a)) {
                                addressMessage.get(i).setName1(baseAddrBasics.getName1());
                                bs_addr.setName1(baseAddrBasics.getName1());
                                result = true;
                            }

                            //带别名反写
                            if (addressMessage.get(i).getPhone() != null
                                    && baseAddrBasics.getPhone().equals(addressMessage.get(i).getPhone())
                                    && nameProcessService.a3nameLikedProcess(name1a, addressMessage.get(i).getName1().split(""))) {
                                //如果合并值带别名
                                if(nameProcessService.isContainChinese(addressMessage.get(i).getName1())){
                                    addressMessage.get(i).setName1(baseAddrBasics.getName1());
                                    bs_addr.setName1(baseAddrBasics.getName1());
                                    result = true;
                                }
                                //如果基准值带别名
                                if(nameProcessService.isContainChinese(baseAddrBasics.getName1())){
                                    baseAddrBasics.setName1(addressMessage.get(i).getName1());
                                    result = true;
                                }
                            }
                            name1a = baseAddrBasics.getName1().split("");
                        }
                    }

                    //如果是只反写操作，只有被反写的数据才需要修改
                    if( "2".equals(applicationProperty.getStandardAddress())){
                        if(result){
                            insertMessage.add(bs_addr);
                        }
                    }else {
                        insertMessage.add(bs_addr);
                    }
                }
            }
        }
        map.put("addressMessage", addressMessage);
        map.put("insertMessage", insertMessage);
        map.put("start", start);
        map.put("end", end);
        return map;
    }
}
