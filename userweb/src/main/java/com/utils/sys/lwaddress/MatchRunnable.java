/* Copyright (C) 2019-2019 Hangzhou HSH Co. Ltd.
 * All right reserved.*/
package com.utils.sys.lwaddress;

import com.dao.entity.lwaddress.Base_addr;
import com.service.lwaddress.impl.NameProcessServiceImpl;
import com.service.lwaddress.impl.ProcessGradeServiceImpl;
import com.service.lwaddress.impl.ProcessServiceImpl;
import com.service.lwaddress.impl.StringPasringServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * 执行定时任务
 *
 * @author simon
 * @since 1.2.0 2016-11-28
 */

public class MatchRunnable implements Callable<HashMap<String,Base_addr>> {

    private static final Logger log = LoggerFactory.getLogger(ProcessServiceImpl.class);
    private int num;
    private int str;
    private String reg;
    private Base_addr baseAddr;
    private Base_addr baseAddrBasics;
    private CountDownLatch countDownLatch;

    public MatchRunnable(Base_addr baseAddrBasics, int num, int str, String reg, Base_addr baseAddr, CountDownLatch countDownLatch) {
        this.num = num;
        this.str = str;
        this.reg = reg;
        this.baseAddr = baseAddr;
        this.baseAddrBasics = baseAddrBasics;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public HashMap<String,Base_addr> call(){
        long startTime = System.currentTimeMillis();

        HashMap<String,Base_addr> match = match(baseAddrBasics, reg, num, str,baseAddr);
        //计数值减一
        countDownLatch.countDown();

        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        log.info("*************************线程"+Thread.currentThread().getName()+"处理了"+time+"毫秒");
        return match;
    }


    /**
     * 计算相似度分值
     *
     * @param suma
     * @param sumb
     * @param baseAddr
     * @return
     */
    public BigDecimal getSum(BigDecimal suma, BigDecimal sumb, Base_addr baseAddr) {
        StringPasringServiceImpl stringParsingService = new StringPasringServiceImpl();
        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();
        BigDecimal weight1;
        BigDecimal weight2;

        /*判断数字位数决定权重*/
        switch (((String[]) (stringParsingService.stringParse(baseAddr.getShortAddr()).get("strb1"))).length) {
            case 2:
                weight1 = new BigDecimal(0.4);
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
        weight2 = BigDecimal.ONE.subtract(weight1);
        //计算相似度
        BigDecimal sum = processGradeService.processliked(suma, sumb, weight1, weight2);
        if (sum.compareTo(BigDecimal.valueOf(0.94546000)) == 0) {
            sum = BigDecimal.ONE;
        }
        return sum;
    }

    public HashMap<String,Base_addr> match(Base_addr baseAddrBasics, String reg,int num, int str,Base_addr baseAddr) {
        StringPasringServiceImpl stringParsingService = new StringPasringServiceImpl();
        NameProcessServiceImpl nameProcessService = new NameProcessServiceImpl();
        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();

        Map<String, String[]> strMapa = new HashMap<>();
        Map<String, String[]> strMapb = new HashMap<>();
        HashMap<String,Base_addr> map = new HashMap<>();

        String[] phone1 = baseAddrBasics.getPhone().split("");
        String[] name1a = baseAddrBasics.getName1().split("");

        //数字
        strMapa.put("stra", (String[]) stringParsingService.stringParse(baseAddrBasics.getShortAddr()).get("strb1"));
        //字符,先正则去除关键字，匹配前正则匹配去除省市区
        String name = baseAddrBasics.getShortAddr().replaceAll(reg, "");
        strMapb.put("stra", (String[]) stringParsingService.stringParse(name).get("strb2"));

        /*用于判断的阈值*/
        BigDecimal grace = new BigDecimal(0.8);
                if (baseAddr.getP2type() == 222 || baseAddr.getP2type() == 223 || baseAddr.getP2type() == 224) {
                    return null;
                }
                Base_addr bs_addr = new Base_addr();

                if (baseAddr.getShortAddr() != null && baseAddr.getShortAddr().length() > 0) {
                    //特殊字符处理
                    String shortAddr = AsciiUtil.SpecialHandl(baseAddr.getShortAddr());
                    shortAddr = shortAddr.replace(baseAddr.getName1(), "");
                    baseAddr.setShortAddr(shortAddr);
                    //将地址切割成字符串数组，strMapa是数字，strMapb是字符串
                    strMapa.put("strb", (String[]) (stringParsingService.stringParse(baseAddr.getShortAddr()).get("strb1")));
                    //字符,先正则去除关键字，匹配前正则匹配去除省市区
                    String name1 = baseAddr.getShortAddr().replaceAll(reg, "");
                    strMapb.put("strb", (String[]) (stringParsingService.stringParse(name1).get("strb2")));

                    /*返回一个map出来，其中包括计算用的Double的sum,String的integerer[]*/
                    //数字和字符相似度匹配
                    Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, num);
                    Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, str);

                    BigDecimal suma = (BigDecimal) processresult1.get("sum");
                    BigDecimal sumb = (BigDecimal) processresult2.get("sum");

                    //计算相似度
                    BigDecimal Sum = getSum(suma, sumb, baseAddr);

                    //如果相似度大于阈值
                    if (Sum.compareTo(grace) > 0) {
                        bs_addr.setId(baseAddr.getId());
                        bs_addr.setName1(baseAddr.getName1());
                        bs_addr.setPhone(baseAddr.getPhone());
                        bs_addr.setAddrSj(baseAddr.getAddrSj());
                        bs_addr.setContrastScore(Sum);
                        bs_addr.setContrastId(baseAddrBasics.getId());
                        bs_addr.setNumberScore((String) processresult1.get("integer"));
                        bs_addr.setStrScore((String) processresult2.get("integer"));
                        bs_addr.setShortAddr(baseAddr.getShortAddr());
                        bs_addr.setOldPhone(baseAddr.getOldPhone());
                        bs_addr.setOldName1(baseAddr.getOldName1());
                        bs_addr.setShortPhone(baseAddr.getShortPhone());
                        bs_addr.setProvince(baseAddr.getProvince());
                        bs_addr.setProWeight(baseAddr.getProWeight());
                        bs_addr.setCity(baseAddr.getCity());
                        bs_addr.setCityWeight(baseAddr.getCityWeight());
                        bs_addr.setAreaWeight(baseAddr.getAreaWeight());
                        bs_addr.setStreet(baseAddr.getStreet());
                        bs_addr.setStreWeight(baseAddr.getStreWeight());
                        bs_addr.setMulWeight(baseAddr.getMulWeight());
                        bs_addr.setAddrSign1(baseAddr.getAddrSign1());
                        bs_addr.setAddrSign2(baseAddr.getAddrSign2());
                        bs_addr.setRowId(baseAddr.getRowId());
                        bs_addr.setTableName(baseAddr.getTableName());
                        bs_addr.setCountId(baseAddr.getCountId());
                        bs_addr.setP2type(222);
                        if (baseAddr.getP3type() != null) {
                            bs_addr.setP3type(223);
                        } else {
                            bs_addr.setP3type(0);
                        }
                        baseAddr.setP2type(222);
                        /*手机反写*/
                        if (baseAddrBasics.getPhone().contains("*") && baseAddr.getPhone() != null) {
                            if (nameProcessService.nameLikedProcess(phone1, baseAddr.getPhone().split(""))) {
                                baseAddr.setPhone(baseAddr.getPhone());
                                baseAddrBasics.setPhone(baseAddr.getPhone());
                                phone1 = baseAddr.getPhone().split("");
                                //*当匹配到手机后，复写回insertmessage*//*
                                /*用修改的基准值手机号反写合并止手机号*/
                                    if (baseAddr.getP2type() == 222
                                            && baseAddr.getPhone() != null
                                            && baseAddr.getPhone().contains("*")
                                            && baseAddr.getContrastId() == baseAddrBasics.getId()
                                            && nameProcessService.a2nameLikedProcess(phone1, baseAddr.getPhone().split(""))) {
                                        baseAddr.setPhone(baseAddrBasics.getPhone());
                                    }
                            }
                        } else {
                            if (nameProcessService.a2nameLikedProcess(phone1, baseAddr.getPhone().split(""))) {
                                bs_addr.setPhone(baseAddrBasics.getPhone());
                            }
                        }

                        /*如果有星星去匹配全名*/
                        if (baseAddrBasics.getName1().contains("*") && baseAddr.getName1() != null) {
                            if (nameProcessService.isContainChinese(baseAddr.getName1()) == false &&
                                    nameProcessService.nameLikedProcess(name1a, baseAddr.getName1().split(""))) {
                                baseAddrBasics.setName1(baseAddr.getName1());
                                baseAddrBasics.setName1(baseAddr.getName1());
                                name1a = baseAddr.getName1().split("");
                                //*当匹配到全名后，复写回insertmessage*//*
                                    if (baseAddr.getP2type() == 222
                                            && baseAddr.getName1() != null
                                            && baseAddr.getName1().contains("*")
                                            && baseAddr.getContrastId() == baseAddrBasics.getId()
                                            && nameProcessService.a2nameLikedProcess(name1a, baseAddr.getName1().split(""))) {
                                        baseAddr.setName1(baseAddrBasics.getName1());
                                    }

                                    /*有先生小姐女士的记录，通过相同号码以及姓氏判断进行反写*/
                                    if (baseAddr.getP2type() == 222
                                            && baseAddr.getName1() != null
                                            && baseAddr.getPhone() != null
                                            && nameProcessService.a2nameLikedProcess(phone1, baseAddr.getPhone().split(""))
                                            && nameProcessService.isContainChinese(baseAddr.getName1())
                                            && baseAddr.getContrastId() == baseAddrBasics.getId()
                                            && nameProcessService.a3nameLikedProcess(name1a, baseAddr.getName1().split(""))) {
                                        baseAddr.setName1(baseAddrBasics.getName1());
                                    }
                            }
                        } else {
                            /*用合并值反写先生小姐*/
                            if (nameProcessService.isContainChinese(baseAddrBasics.getName1())) {
                                if (baseAddrBasics.getPhone().contains("*") && baseAddr.getPhone() != null) {
                                    if (nameProcessService.nameLikedProcess(phone1, baseAddr.getPhone().split(""))) {
                                        if (nameProcessService.a3nameLikedProcess(name1a, baseAddr.getName1().split(""))) {
                                            baseAddrBasics.setName1(baseAddr.getName1());
                                            baseAddrBasics.setName1(baseAddr.getName1());
                                            name1a = baseAddr.getName1().split("");
                                            //*当匹配到全名后，复写回insertmessage*//*
                                                if (baseAddr.getP2type() == 222
                                                        && baseAddr.getName1() != null
                                                        && baseAddr.getName1().contains("*")
                                                        && baseAddr.getContrastId() == baseAddrBasics.getId()
                                                        && nameProcessService.a2nameLikedProcess(name1a, baseAddr.getName1().split(""))) {
                                                    baseAddr.setName1(baseAddrBasics.getName1());
                                                }
                                        }
                                    }
                                }
                            }
                            if (baseAddr.getName1() != null && nameProcessService.a2nameLikedProcess(name1a, baseAddr.getName1().split(""))) {
                                bs_addr.setName1(baseAddrBasics.getName1());
                            }
                            if (baseAddr.getName1() != null && nameProcessService.isContainChinese(baseAddr.getName1())
                                    && baseAddr.getPhone() != null && nameProcessService.nameLikedProcess(phone1, baseAddr.getPhone().split(""))
                                    && nameProcessService.a2nameLikedProcess(name1a, baseAddr.getName1().split(""))) {
                                bs_addr.setName1(baseAddrBasics.getName1());
                            }
                        }
                    map.put("bs_addr",bs_addr);
                    }
                }
            map.put("addressMessage",baseAddr);
            return map;
    }
}
