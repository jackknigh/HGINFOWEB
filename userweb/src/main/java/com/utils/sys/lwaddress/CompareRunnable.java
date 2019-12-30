/* Copyright (C) 2019-2019 Hangzhou HSH Co. Ltd.
 * All right reserved.*/
package com.utils.sys.lwaddress;

import com.dao.entity.lwaddress.Base_addr;
import com.service.lwaddress.impl.NameProcessServiceImpl;
import com.service.lwaddress.impl.ProcessGradeServiceImpl;
import com.service.lwaddress.impl.ProcessServiceImpl;
import com.service.lwaddress.impl.StringPasringServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 相似度匹配线程
 */

public class CompareRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ProcessServiceImpl.class);
    private int count;
    private int num;
    private int str;
    private List<Base_addr> baseAddrList;
    private List<Base_addr> addressMessage;
    private CountDownLatch countDownLatch;

    public CompareRunnable(int count, int num, int str, List<Base_addr> baseAddrList, List<Base_addr> addressMessage, CountDownLatch countDownLatch) {
        this.count = count;
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
        NameProcessServiceImpl nameProcessService = new NameProcessServiceImpl();
        long startTime1 = System.currentTimeMillis();

        for (Base_addr baseAddr : baseAddrList) {
            long startTime = System.currentTimeMillis();
            //正则匹配去除某些关键字
//            String name = RegProcess(baseAddr.getShortAddr(),baseAddr.getName1(),reg);
            //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
            Map stringMap = stringParsingService.stringParse(baseAddr.getShortAddr());
            strMapa.put("stra", (String[]) stringMap.get("strb1"));
            strMapb.put("stra", (String[]) stringMap.get("strb2"));

            //基准值
            String[] phone1 = null;
            if (!StringUtils.isBlank(baseAddr.getPhone())) {
                phone1 = baseAddr.getPhone().split("");
            }

            String[] name1a = null;
            if (!StringUtils.isBlank(baseAddr.getName1())) {
                name1a = baseAddr.getName1().split("");
            }

            /*用于判断的阈值*/
            BigDecimal grace = new BigDecimal(0.76);

            for (int i = 0; i < addressMessage.size(); i++) {
                //如果数据已经跟其他数据合并过了，就不需要再判断了
                if (!StringUtils.isBlank(addressMessage.get(i).getContrastId())) {
                    continue;
                }

                if (!StringUtils.isEmpty(addressMessage.get(i).getShortAddr())) {
                    //正则匹配去除某些关键字
//                    String name1 = RegProcess(addressMessage.get(i).getShortAddr(),addressMessage.get(i).getName1(),reg);
                    //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
                    Map stringMap1 = stringParsingService.stringParse(addressMessage.get(i).getShortAddr());
                    strMapb.put("strb", (String[]) stringMap1.get("strb2"));
                    strMapa.put("strb", (String[]) stringMap1.get("strb1"));

                    /*返回一个map出来，其中包括计算用的Double的sum,String的integerer[]*/
                    //数字和字符相似度匹配
                    Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, num);
                    BigDecimal suma = (BigDecimal) processresult1.get("sum");

                    Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, str);
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

                        //反写手机号
                        if (phone1 != null) {
                            if (addressMessage.get(i).getPhone() != null && addressMessage.get(i).getPhone().contains("*") && nameProcessService.a2nameLikedProcess(phone1, addressMessage.get(i).getPhone().split(""))) {
                                addressMessage.get(i).setPhone(baseAddr.getPhone());
                            }
                        }

                        //反写姓名
                        if (name1a != null && addressMessage.get(i).getName1() != null) {
                            //基准值反写合并值，不带别名
                            if (addressMessage.get(i).getName1().contains("*")
                                    && baseAddr.getPhone() != null
                                    && baseAddr.getPhone().equals(addressMessage.get(i).getPhone())
                                    && nameProcessService.nameLikedProcess(addressMessage.get(i).getName1().split(""), name1a)) {
                                addressMessage.get(i).setName1(baseAddr.getName1());
                            }

                            //带别名反写
                            if (baseAddr.getPhone() != null
                                    && baseAddr.getPhone().equals(addressMessage.get(i).getPhone())
                                    && nameProcessService.a3nameLikedProcess(name1a, addressMessage.get(i).getName1().split(""))
                                    && nameProcessService.isContainChinese(addressMessage.get(i).getName1())) {
                                //如果合并值带别名
                                addressMessage.get(i).setName1(baseAddr.getName1());
                            }

                            //如果基准值和合并值手机号都为null就更据姓判断反写
                            if (baseAddr.getPhone() == null && addressMessage.get(i).getPhone() == null) {
                                //基准值反写合并值，不带别名
                                if (addressMessage.get(i).getName1().contains("*")
                                        && nameProcessService.nameLikedProcess(addressMessage.get(i).getName1().split(""), name1a)) {
                                    addressMessage.get(i).setName1(baseAddr.getName1());
                                }

                                //带别名反写
                                if (nameProcessService.a3nameLikedProcess(name1a, addressMessage.get(i).getName1().split(""))) {
                                    //如果合并值带别名
                                    if (nameProcessService.isContainChinese(addressMessage.get(i).getName1())) {
                                        addressMessage.get(i).setName1(baseAddr.getName1());
                                    }
                                }
                            }
                        }
                        if (!StringUtils.isBlank(baseAddr.getName1())) {
                            name1a = baseAddr.getName1().split("");
                        }
                    }
                }
                if (i == addressMessage.size() - 1) {
                    long endTime = System.currentTimeMillis();
                    log.info("比较" + addressMessage.size() + "/" + (i+1) + "完成，用时{}毫秒", endTime - startTime);
                }
            }

            countDownLatch.countDown();
            log.info("比较第 {} 个集合组,countDownLatch剩余: {}", count, countDownLatch.getCount());
        }
        long endTime1 = System.currentTimeMillis();
        log.info("比较第 {} 个集合组完成，用时{}毫秒,共 {} 个元素", count, endTime1 - startTime1, baseAddrList.size());
    }
}
