/* Copyright (C) 2019-2019 Hangzhou HSH Co. Ltd.
 * All right reserved.*/
package com.utils.sys.lwaddress;

import com.config.HgApplicationProperty;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 相似度匹配线程
 */

public class CompareRunnable implements Runnable {

    @Autowired
    private HgApplicationProperty applicationProperty = ApplicationContextProvider.getBean(HgApplicationProperty.class);

    private static final Logger log = LoggerFactory.getLogger(ProcessServiceImpl.class);
    private int num;
    private int str;
    //标准数据
    private List<Base_addr> baseAddrList;
    //需碰撞的数据
    private List<Base_addr> addressMessage;
    private CountDownLatch countDownLatch;

    public CompareRunnable(int num, int str, List<Base_addr> baseAddrList, List<Base_addr> addressMessage, CountDownLatch countDownLatch) {
        this.num = num;
        this.str = str;
        this.baseAddrList = baseAddrList;
        this.addressMessage = addressMessage;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        Map<String, String[]> strMapa = new HashMap<>();
        Map<String, String[]> strMapb = new HashMap<>();
        StringPasringServiceImpl stringParsingService = new StringPasringServiceImpl();
        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();
//        NameProcessServiceImpl nameProcessService = new NameProcessServiceImpl();
        long startTime1 = System.currentTimeMillis();

        for (Base_addr baseAddr : baseAddrList) {

            //数字字母处理
            String shortAddr = AsciiUtil.RegProcess(baseAddr.getShortAddr());
            //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
            Map stringMap = stringParsingService.stringParse(shortAddr);
            strMapa.put("stra", (String[]) stringMap.get("strb1"));
            strMapb.put("stra", (String[]) stringMap.get("strb2"));

//            //基准值
//            String[] phone1 = null;
//            if (!StringUtils.isBlank(baseAddr.getPhone())) {
//                phone1 = baseAddr.getPhone().split("");
//            }
//
//            String[] name1a = null;
//            if (!StringUtils.isBlank(baseAddr.getName1())) {
//                name1a = baseAddr.getName1().split("");
//            }

            /*用于判断的阈值*/
            BigDecimal grace = new BigDecimal(applicationProperty.getGrace());

            for (int i = 0; i < addressMessage.size(); i++) {
                //如果数据已经跟其他数据合并过了，就不需要再判断了
                if (!StringUtils.isBlank(addressMessage.get(i).getContrastId())) {
                    continue;
                }

                if (!StringUtils.isEmpty(addressMessage.get(i).getShortAddr())) {
                    //数字字母处理
                    String shortAddr1 = AsciiUtil.RegProcess(addressMessage.get(i).getShortAddr());
                    //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
                    Map stringMap1 = stringParsingService.stringParse(shortAddr1);
                    strMapb.put("strb", (String[]) stringMap1.get("strb2"));
                    strMapa.put("strb", (String[]) stringMap1.get("strb1"));

                    /*返回一个map出来，其中包括计算用的Double的sum,String的integerer[]*/
                    //数字和字符相似度匹配
                    Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, num,false);
                    BigDecimal suma = (BigDecimal) processresult1.get("sum");

                    Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, str,false);
                    BigDecimal sumb = (BigDecimal) processresult2.get("sum");

//                    //todo 改动
//                    System.out.println("数字得分："+suma);
//                    System.out.println("字符得分："+sumb);

                    //计算相似度
                    BigDecimal sum = processGradeService.getSum(suma, sumb, (String[]) stringMap1.get("strb1"),(String[]) stringMap.get("strb1"),(String[]) stringMap1.get("strb2"),(String[]) stringMap.get("strb2"));

//                    //todo 改动
//                    System.out.println("最终得分："+sum);

                    //如果相似度大于阈值
                    if (sum.compareTo(grace) > 0) {
                        //绑定数据关联关系
                        addressMessage.get(i).setContrastId(baseAddr.getId());
                        addressMessage.get(i).setContrastScore(sum);
                        addressMessage.get(i).setAddrCode(baseAddr.getAddrCode());
                        continue;

//                        //反写手机号
//                        if (phone1 != null) {
//                            if (addressMessage.get(i).getPhone() != null && addressMessage.get(i).getPhone().contains("*") && nameProcessService.a2nameLikedProcess(phone1, addressMessage.get(i).getPhone().split(""))) {
//                                addressMessage.get(i).setPhone(baseAddr.getPhone());
//                            }
//                        }
//
//                        //反写姓名
//                        if (name1a != null && addressMessage.get(i).getName1() != null) {
//                            //基准值反写合并值，不带别名
//                            if (addressMessage.get(i).getName1().contains("*")
//                                    && baseAddr.getPhone() != null
//                                    && baseAddr.getPhone().equals(addressMessage.get(i).getPhone())
//                                    && nameProcessService.nameLikedProcess(addressMessage.get(i).getName1().split(""), name1a)) {
//                                addressMessage.get(i).setName1(baseAddr.getName1());
//                            }
//
//                            //带别名反写
//                            if (baseAddr.getPhone() != null
//                                    && baseAddr.getPhone().equals(addressMessage.get(i).getPhone())
//                                    && nameProcessService.a3nameLikedProcess(name1a, addressMessage.get(i).getName1().split(""))
//                                    && nameProcessService.isContainChinese(addressMessage.get(i).getName1())) {
//                                //如果合并值带别名
//                                addressMessage.get(i).setName1(baseAddr.getName1());
//                            }
//
//                            //如果基准值和合并值手机号都为null就更据姓判断反写
//                            if (baseAddr.getPhone() == null && addressMessage.get(i).getPhone() == null) {
//                                //基准值反写合并值，不带别名
//                                if (addressMessage.get(i).getName1().contains("*")
//                                        && nameProcessService.nameLikedProcess(addressMessage.get(i).getName1().split(""), name1a)) {
//                                    addressMessage.get(i).setName1(baseAddr.getName1());
//                                }
//
//                                //带别名反写
//                                if (nameProcessService.a3nameLikedProcess(name1a, addressMessage.get(i).getName1().split(""))) {
//                                    //如果合并值带别名
//                                    if (nameProcessService.isContainChinese(addressMessage.get(i).getName1())) {
//                                        addressMessage.get(i).setName1(baseAddr.getName1());
//                                    }
//                                }
//                            }
//                        }
//                        if (!StringUtils.isBlank(baseAddr.getName1())) {
//                            name1a = baseAddr.getName1().split("");
//                        }
                    }
                }
            }

            countDownLatch.countDown();
            if(countDownLatch.getCount()%1000 == 0 || countDownLatch.getCount() == 0) {
                log.info("countDownLatch剩余: {}",countDownLatch.getCount());
            }
        }
        long endTime1 = System.currentTimeMillis();
        log.info("用时{}毫秒,共 {} 个元素", endTime1 - startTime1, baseAddrList.size());
    }
}
