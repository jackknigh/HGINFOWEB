package com.service.lwaddress.impl;

import com.alibaba.fastjson.JSONObject;
import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.Base_addrMapper;
import com.dao.db3.lwaddr.Base_addrMapper1;
import com.dao.entity.lwaddress.*;
import com.service.lwaddress.Base_addrService;
import com.service.lwaddress.Bs_utilService;
import com.utils.sys.lwaddress.AsciiUtil;
import com.utils.sys.lwaddress.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Bs_utilServiceImpl implements Bs_utilService {

    private static final Logger log = LoggerFactory.getLogger(Bs_utilServiceImpl.class);

    @Autowired
    private HgApplicationProperty applicationProperty;
    @Autowired
    private Base_addrMapper1 base_addrMapper1;
    @Autowired
    private Base_addrMapper base_addrMapper;
    //    @Autowired
//    private Bs_cityMapper bs_cityMapper;
//    @Autowired
//    private Bs_areaMapper bs_areaMapper;
//    @Autowired
//    private Bs_streetMapper bs_streetMapper;
//    @Autowired
//    private Bs_provinceMapper bs_provinceMapper;
    @Autowired
    private Base_addrService base_addrService;
    @Autowired
    private Base_addrMapper bs_addrMapper;

    @Override
    @Async("asyncPromiseExecutor")
    public void addressStart(int number1, int number2, BigDecimal n, String reg) {
        List<Base_addr> list;

        long startTime = System.currentTimeMillis();

        List<Base_addr> updateMessage = new ArrayList<>();

        //查询原始数据表
        list = base_addrMapper.selectAddr_sj(number1, number2);

        //将查到的数据加入集合中
        for (int i = 0; i < list.size(); i++) {
            Base_addr baseAddr = new Base_addr();
            baseAddr = getBaseAddr(list.get(i), reg);
            if (baseAddr != null) {
                updateMessage.add(baseAddr);
            }
        }

        try {
            if (updateMessage == null || updateMessage.size() == 0) {
                log.info("start:{} , batchcCount:{}", number1, number2);
                log.info("原始表查到的数据:{}", JSONObject.toJSONString(list));
                return;
            }
            //将处理完的值存进sec_addr表
            base_addrMapper1.insert1(updateMessage);
            log.info("=======当前线程名:{}", Thread.currentThread().getName());
            log.info("=======开始处理的数据编号:{} 步进值:{}", number1, number2);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(JSONObject.toJSONString(updateMessage));
            log.error(e.getMessage());
        }

        long endTime = System.currentTimeMillis();
        log.info("完成{} 条数据共用时：{} 毫秒", list.size(), endTime - startTime);
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
        List<Bs_city> cityMessage = new ArrayList<>();
        List<Bs_area> areaMessage = new ArrayList<>();
        List<Bs_street> streetMessage = new ArrayList<>();
        List<Bs_street> streetNames;

        //优先查询温州的省市区街道全程和简称

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

//        //全国的查询省市区街道的全称和简称存进各自的List集合中
//        provinceMessage.addAll(bs_provinceMapper.selectShortNameAndProvinceName());
//        cityMessage.addAll(bs_cityMapper.selectCityAllName());
//        areaMessage.addAll(bs_areaMapper.selectAreaMessage());
//        streetMessage.addAll(bs_streetMapper.selectStreetMessage());

        allMessage.put("provinceMessage", provinceMessage);
        allMessage.put("cityMessage", cityMessage);
        allMessage.put("streetMessage", streetMessage);
        allMessage.put("areaMessage", areaMessage);

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
        return allMessage;
    }

    /**
     * 地址切割短地址并且打分
     *
     * @return
     */
    public Base_addr getBaseAddr(Base_addr base_addr, String reg) {
        Map<String, Object> allMessage = getMap();
        //如果长地址不为空
        if (StringUtils.isBlank(base_addr.getAddrSj())) {
            return null;
        }

        //参数是所有标准地址集合和加减分值
        //这里需要添加街路巷、小区的算分和切割
        base_addr = base_addrService.addrSet(base_addr, allMessage);

        String shortAddr = base_addr.getShortAddr();
        shortAddr = AsciiUtil.SpecialEndHandl(shortAddr);
        //去除省市区
        base_addr.setShortAddr(shortAddr.replaceAll(reg, ""));

        if (base_addr.getEarliestTime() == null) {
            base_addr.setEarliestTime(base_addr.getCreateTime());
        }
        if (base_addr.getLatestTime() == null) {
            base_addr.setLatestTime(base_addr.getCreateTime());
        }

        base_addr.setCreateTime(DateUtil.getCurrDateTimeStr());
        return base_addr;
    }

    @Override
    @Async("asyncPromiseExecutor")
    public void increment(Base_addr baseAddr, String reg) {
        try {
            //step1地址切割
            Base_addr base_addr = getBaseAddr(baseAddr, reg);

            if (StringUtils.isBlank(base_addr.getShortAddr())) {
                //扔进垃圾表
                base_addrMapper1.insertDiscard(base_addr);
                base_addrMapper1.updateP5type(base_addr);
                return;
            }

            int blockSizeByStr = Integer.valueOf(applicationProperty.getBlockSizeByStr());
            int blockSizeByNum = Integer.valueOf(applicationProperty.getBlockSizeByNum());

            //手机号包含*,手机号反写
            if (base_addr.getPhone().contains("*")) {
                String phone = baseAddr.getPhone();
                String shortPhone = phone.substring(1, 3);
                shortPhone = shortPhone + phone.substring(phone.length() - 4);
                List<Base_addr> addressMessage = bs_addrMapper.getDate(shortPhone, baseAddr.getProvince(), baseAddr.getCity(), baseAddr.getArea(), baseAddr.getStreet());
                Base_addr baseAddr1 = getProcess(blockSizeByNum, blockSizeByStr, base_addr, addressMessage, false);
                if (baseAddr1.getPhone().contains("*")) {
                    //扔进垃圾表
                    base_addrMapper1.insertDiscard(baseAddr1);
                    base_addrMapper1.updateP5type(base_addr);
                    return;
                }
            }

            int start = 0;
//        int count = 100000;
            int count = 100000;
            //需要被比较的值,分多次取
            while (true) {
                List<Base_addr> addressMessage = bs_addrMapper.getBaseAddrs(start, count, base_addr.getPhone());
                log.info("===============================取到 {} 条与增量数据碰撞的数据的数据", addressMessage.size());
                if (addressMessage.size() < 1) {
                    break;
                }
                Base_addr baseAddr1 = getProcess(blockSizeByNum, blockSizeByStr, base_addr, addressMessage, true);
                //如果被合并了就结束
                if (baseAddr1.getP2type() == 222) {
                    base_addrMapper1.insert3(baseAddr1);
                    base_addrMapper1.updateP5type(base_addr);
                    return;
                }
                start += count;
            }
            //判断是基准值还是合并值入对应库
            base_addr.setP2type(223);
            base_addr.setMergeNum(0);
            base_addr.setP5type(1);
            base_addr.setEarliestTime(base_addr.getCreateTime());
            base_addr.setLatestTime(base_addr.getCreateTime());
            base_addrMapper1.insert5(base_addr);
            base_addrMapper1.updateP5type(base_addr);

        } catch (Exception e) {
            log.error("==========================================id为：{} 的数据发生异常", baseAddr.getId());
            baseAddr.setP5type(baseAddr.getP5type() + 1);
            //插回源新增数据表
            base_addrMapper1.updateErrDate(baseAddr);
        }
    }


    //左右相似度碰撞
    public Base_addr getProcess(int num, int str, Base_addr baseAddrBasics, List<Base_addr> addressMessage, boolean flag) {
        Map<String, String[]> strMapa = new HashMap<>();
        Map<String, String[]> strMapb = new HashMap<>();
        StringPasringServiceImpl stringParsingService = new StringPasringServiceImpl();

        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();

        //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
        Map stringMap = stringParsingService.stringParse(baseAddrBasics.getShortAddr());
        strMapa.put("strb", (String[]) stringMap.get("strb1"));
        strMapb.put("strb", (String[]) stringMap.get("strb2"));

        for (int i = 0; i < addressMessage.size(); i++) {
            BigDecimal grace = new BigDecimal(0.76);

            if (!StringUtils.isEmpty(addressMessage.get(i).getShortAddr())) {

                //如果姓相同就继续，不相同就判定为不是同一个人
                if (!StringUtils.isBlank(baseAddrBasics.getName1()) && !StringUtils.isBlank(addressMessage.get(i).getName1())) {
                    if (!baseAddrBasics.getName1().split("")[0].equals(addressMessage.get(i).getName1().split("")[0])
                            && !addressMessage.get(i).getName1().startsWith("*") && !baseAddrBasics.getName1().startsWith("*")) {
                        continue;
                    }
                }

                //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串,key=strb是合并值，key=stra是基准值
                Map stringMap1 = stringParsingService.stringParse(addressMessage.get(i).getShortAddr());
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
                Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, num);


                //理论最大值
                BigDecimal asummax = (BigDecimal) processresult1.get("asummax");
                //实际得分
                BigDecimal asum = (BigDecimal) processresult1.get("asum");

                Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, str);

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

                        //如果字符没到及格线
                        if (bsum.divide(asummax1, 4, BigDecimal.ROUND_HALF_UP).multiply(asum1).divide(strPass, 4, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(1)) <= 0) {
                            continue;
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
                        int count = 0;
                        baseAddrBasics.setContrastId(addressMessage.get(i).getId());
                        baseAddrBasics.setP2type(222);
                        addressMessage.get(i).setMergeNum(addressMessage.get(i).getMergeNum() + 1);
                        if (baseAddrBasics.getCreateTime().compareTo(addressMessage.get(i).getLatestTime()) > 0) {
                            addressMessage.get(i).setLatestTime(baseAddrBasics.getLatestTime());
                            count++;
                        }

                        if (baseAddrBasics.getCreateTime().compareTo(addressMessage.get(i).getEarliestTime()) < 0) {
                            addressMessage.get(i).setEarliestTime(baseAddrBasics.getEarliestTime());
                            count++;
                        }

                        if (count > 0) {
                            base_addrMapper1.updateBasics(addressMessage.get(i));
                            log.info("==================================修改时间");
                        }
                        return baseAddrBasics;
                    }

                    baseAddrBasics.setOldPhone(baseAddrBasics.getPhone());
                    baseAddrBasics.setOldName1(baseAddrBasics.getName1());

                    String[] phone1 = null;
                    String[] name1a = null;
                    if (!StringUtils.isBlank(addressMessage.get(i).getPhone())) {
                        phone1 = addressMessage.get(i).getPhone().split("");
                    }
                    if (!StringUtils.isBlank(addressMessage.get(i).getName1())) {
                        name1a = addressMessage.get(i).getName1().split("");
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
                }
            }
        }
        return baseAddrBasics;
    }

}
