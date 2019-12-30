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
    //    private CountDownLatch countDownLatch;
    private List<Base_addr> addressMessage;
    private int start;
    private int end;
    private boolean flag;

    public MatchRunnable(int num, int str, Base_addr baseAddrBasics, List<Base_addr> addressMessage, int start, int end, boolean flag) {
        this.num = num;
        this.str = str;
        this.baseAddrBasics = baseAddrBasics;
//        this.countDownLatch = countDownLatch;
        this.addressMessage = addressMessage;
        this.start = start;
        this.end = end;
        this.flag = flag;
    }

    @Override
    public HashMap<String, Object> call() {
//        long startTime = System.currentTimeMillis();
        HashMap<String, Object> match = match(num, str, baseAddrBasics, addressMessage, start, end, flag);
//        long endTime = System.currentTimeMillis();
//        log.info("线程"+Thread.currentThread().getName()+"执行了"+(endTime-startTime)+"毫秒");
        return match;
    }

    public HashMap<String, Object> match(int num, int str, Base_addr baseAddrBasics, List<Base_addr> addressMessage, int start, int end, boolean flag) {

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
        //正则匹配去除某些关键字，第一步已经做过了
//        String name = RegProcess(baseAddrBasics.getShortAddr(),baseAddrBasics.getName1(),reg);
        //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
        Map stringMap = stringParsingService.stringParse(baseAddrBasics.getShortAddr());
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

                //正则匹配去除某些关键字,第一步做过了
//                String name1 = RegProcess(addressMessage.get(i).getShortAddr(),addressMessage.get(i).getName1(),reg);
                //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串,key=strb是合并值，key=stra是基准值
                Map stringMap1 = stringParsingService.stringParse(addressMessage.get(i).getShortAddr());
                strMapa.put("strb", (String[]) stringMap1.get("strb1"));
                strMapb.put("strb", (String[]) stringMap1.get("strb2"));

                /*返回一个map出来，其中包括计算用的Double的sum,String的integerer[]*/
                //数字和字符相似度匹配
                Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, num);
                Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, str);

                BigDecimal suma = (BigDecimal) processresult1.get("sum");
                BigDecimal sumb = (BigDecimal) processresult2.get("sum");

                //计算相似度
                BigDecimal sum = processGradeService.getSum(suma, sumb, (String[]) stringMap1.get("strb1"),(String[]) stringMap.get("strb1"),(String[]) stringMap1.get("strb2"),(String[]) stringMap.get("strb2"));

                //带姓名分占比的
//                BigDecimal sum = processGradeService.getSum(suma,sumb,baseAddrBasics.getName1(),addressMessage.get(i).getName1(),(String[]) stringMap1.get("strb1"),(String[]) stringMap.get("strb1"),(String[]) stringMap1.get("strb2"),(String[]) stringMap.get("strb2"));

                //如果相似度大于阈值或者基准短地址包含比较短地址
                if (sum.compareTo(grace) > 0) {

                    //如果是分割集合操作就需要修改原先合并值和基准值的关联id
                    if (flag) {
//                        log.info("是分割操作，需要修改关联id");
                        baseAddrMapper1.updateMerge(addressMessage.get(i).getId(), baseAddrBasics.getId());
                    }
                    bs_addr.setId(addressMessage.get(i).getId());
                    bs_addr.setName1(addressMessage.get(i).getName1());
                    bs_addr.setPhone(addressMessage.get(i).getPhone());
                    bs_addr.setAddrSj(addressMessage.get(i).getAddrSj());
                    bs_addr.setContrastScore(sum);
                    bs_addr.setContrastId(baseAddrBasics.getId());
                    bs_addr.setNumberScore((String) processresult1.get("integer"));
                    bs_addr.setStrScore((String) processresult2.get("integer"));
                    bs_addr.setShortAddr(addressMessage.get(i).getShortAddr());
                    bs_addr.setOldPhone(addressMessage.get(i).getOldPhone());
                    bs_addr.setOldName1(addressMessage.get(i).getOldName1());
                    bs_addr.setShortPhone(addressMessage.get(i).getShortPhone());
                    bs_addr.setProvince(addressMessage.get(i).getProvince());
                    bs_addr.setProWeight(addressMessage.get(i).getProWeight());
                    bs_addr.setCity(addressMessage.get(i).getCity());
                    bs_addr.setCityWeight(addressMessage.get(i).getCityWeight());
                    bs_addr.setAreaWeight(addressMessage.get(i).getAreaWeight());
                    bs_addr.setStreet(addressMessage.get(i).getStreet());
                    bs_addr.setStreWeight(addressMessage.get(i).getStreWeight());
                    bs_addr.setMulWeight(addressMessage.get(i).getMulWeight());
                    bs_addr.setAddrSign1(addressMessage.get(i).getAddrSign1());
                    bs_addr.setAddrSign2(addressMessage.get(i).getAddrSign2());
                    bs_addr.setRowId(addressMessage.get(i).getRowId());
                    bs_addr.setTableName(addressMessage.get(i).getTableName());
                    bs_addr.setCountId(addressMessage.get(i).getCountId());
                    bs_addr.setCreateTime(addressMessage.get(i).getCreateTime());
                    bs_addr.setIdCard(addressMessage.get(i).getIdCard());
                    bs_addr.setP2type(222);
                    if (addressMessage.get(i).getP3type() != null) {
                        bs_addr.setP3type(223);
                    } else {
                        bs_addr.setP3type(0);
                    }
                    addressMessage.get(i).setP2type(222);


                    //如果是根据手机号清洗就需要反写
                    if ("0".equals(applicationProperty.getStandardAddress())) {
                        if (phone1 != null) {
                            /*手机反写*/
                            if (addressMessage.get(i).getPhone() != null) {
                                if (baseAddrBasics.getPhone().contains("*") && nameProcessService.phoneLikedProcess(phone1, addressMessage.get(i).getPhone().split(""))) {
                                    baseAddrBasics.setPhone(addressMessage.get(i).getPhone());
                                }
                                //*当匹配到手机后，复写回insertmessage*//*
                                /*用修改的基准值手机号反写合并值手机号*/
                                if (addressMessage.get(i).getPhone().contains("*")
                                        && nameProcessService.a2nameLikedProcess(phone1, addressMessage.get(i).getPhone().split(""))) {
                                    addressMessage.get(i).setPhone(baseAddrBasics.getPhone());
                                    bs_addr.setPhone(baseAddrBasics.getPhone());
                                }
                                phone1 = addressMessage.get(i).getPhone().split("");
                            }
                        }

                        if (name1a != null && addressMessage.get(i).getName1() != null) {
                            //合并值反写基准值，不带别名的
                            if (baseAddrBasics.getName1().contains("*")
                                    && nameProcessService.nameLikedProcess(name1a, addressMessage.get(i).getName1().split(""))) {
                                baseAddrBasics.setName1(addressMessage.get(i).getName1());
                            }

                            //基准值反写合并值，不带别名
                            if (addressMessage.get(i).getName1().contains("*")
                                    && nameProcessService.nameLikedProcess(addressMessage.get(i).getName1().split(""), name1a)) {
                                addressMessage.get(i).setName1(baseAddrBasics.getName1());
                                bs_addr.setName1(baseAddrBasics.getName1());
                            }

                            //带别名反写
                            if (addressMessage.get(i).getPhone() != null
                                    && baseAddrBasics.getPhone().equals(addressMessage.get(i).getPhone())
                                    && nameProcessService.a3nameLikedProcess(name1a, addressMessage.get(i).getName1().split(""))) {
                                //如果合并值带别名
                                if(nameProcessService.isContainChinese(addressMessage.get(i).getName1())){
                                    addressMessage.get(i).setName1(baseAddrBasics.getName1());
                                    bs_addr.setName1(baseAddrBasics.getName1());
                                }
                                //如果基准值带别名
                                if(nameProcessService.isContainChinese(baseAddrBasics.getName1())){
                                    baseAddrBasics.setName1(addressMessage.get(i).getName1());
                                }
                            }
                            name1a = baseAddrBasics.getName1().split("");
                        }
                    }
                    insertMessage.add(bs_addr);
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
