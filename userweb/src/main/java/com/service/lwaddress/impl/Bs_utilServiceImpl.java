package com.service.lwaddress.impl;

import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.Base_addrMapper;
import com.dao.db3.lwaddr.Base_addrMapper1;
import com.dao.entity.lwaddress.Base_addr;
import com.service.lwaddress.Base_addrService;
import com.service.lwaddress.Bs_utilService;
import com.utils.sys.DateUtil;
import com.utils.sys.TextUtils;
import com.utils.sys.lwaddress.AsciiUtil;
import com.utils.sys.lwaddress.CompareRunnable3;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
    @Autowired
    private Base_addrService base_addrService;
    @Autowired
    private Base_addrMapper bs_addrMapper;

    /**
     * 短地址切割步骤
     * @param number1 起始值
     * @param number2 步进值
     * @param reg
     * @param allMessage
     */
    @Override
    @Async("asyncPromiseExecutor")
    public void addressStart(int number1, int number2, String reg, Map<String, Object> allMessage) {
        long startTime = System.currentTimeMillis();
        List<Base_addr> updateMessage = new ArrayList<>();

        //查询原始数据表
        List<Base_addr> list = base_addrMapper.selectAddr_sj(number1, number1 + number2);

        //将查到的数据加入集合中
        for (int i = 0; i < list.size(); i++) {
            //调用短地址切割和地址打分处理方法
            Base_addr baseAddr = getBaseAddr(list.get(i), reg, allMessage);
            if (baseAddr != null) {
                updateMessage.add(baseAddr);
            }
        }

        try {
            if (!TextUtils.isEmpty(updateMessage)) {
                //将处理完的值存进sec_addr表
                base_addrMapper1.insert1(updateMessage);
                log.info("***********开始处理的数据编号:{} 步进值:{}***********", number1, number2);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("***********开始数据编号:"+number1+" 步进值:"+number2+"  发生异常"+e.getMessage()+"***********");
        }

        long endTime = System.currentTimeMillis();
        log.info("***********完成{} 条数据共用时：{} 毫秒***********", list.size(), endTime - startTime);
    }

    /**
     * 地址切割短地址并且打分
     * @param base_addr 需要切割的数据
     * @param reg 省市区街道字符串
     * @param allMessage 省市区街道和分值占比数据
     * @return 处理完后的数据
     */
    public Base_addr getBaseAddr(Base_addr base_addr, String reg, Map<String, Object> allMessage) {
        //如果长地址为空就退出
        if (StringUtils.isBlank(base_addr.getAddrSj())) {
            return null;
        }
        //调用省市区切割操作方法
        base_addr = base_addrService.addrSet(base_addr, allMessage);

        //如果切割完后的短地址为空，就退出
        String shortAddr = base_addr.getShortAddr();
        if (StringUtils.isBlank(shortAddr)) {
            return null;
        }
        //短地址特殊情况收尾操作
        shortAddr = AsciiUtil.SpecialEndHandl(shortAddr);
        //去除短地址中冗余的省市区
        base_addr.setShortAddr(shortAddr.replaceAll(reg, ""));

        //初始化最早收件时间和最晚收件时间
        if (base_addr.getCreateTime() == null) {
            base_addr.setCreateTime(DateUtil.getCurrDateTimeStr());
        }
        if (base_addr.getEarliestTime() == null) {
            base_addr.setEarliestTime(base_addr.getCreateTime());
        }
        if (base_addr.getLatestTime() == null) {
            base_addr.setLatestTime(base_addr.getCreateTime());
        }
        return base_addr;
    }

    /**
     * 增量数据处理
     * @param baseAddr
     * @param reg
     * @param allMessage
     */
    @Override
    @Async("asyncPromiseExecutor")
    public void increment(Base_addr baseAddr, String reg, Map<String, Object> allMessage,ThreadPoolTaskExecutor executor) {
        try {
            //step1地址切割
            Base_addr base_addr = getBaseAddr(baseAddr, reg, allMessage);

            if (base_addr == null || StringUtils.isBlank(base_addr.getShortAddr())) {
                //扔进垃圾表
                bs_addrMapper.insertDiscard(baseAddr);
                bs_addrMapper.updateP5type(baseAddr);
                return;
            }

            int blockSizeByStr = Integer.valueOf(applicationProperty.getBlockSizeByStr());
            int blockSizeByNum = Integer.valueOf(applicationProperty.getBlockSizeByNum());

            String phone = baseAddr.getPhone();
            //手机号包含*,手机号反写，反写失败就扔进垃圾表
            if (!StringUtils.isBlank(phone) && phone.length() >= 7 && phone.contains("*")) {
                String startPhone = phone.substring(1, 3);
                String endPhone = phone.substring(phone.length() - 4);
                String shortPhone = startPhone + endPhone;

                List<Base_addr> addressMessage = bs_addrMapper.getDate1(base_addr.getArea(), base_addr.getStreet(),shortPhone,null);
                Base_addr baseAddr1 = getProcess(blockSizeByNum, blockSizeByStr, base_addr, addressMessage, false);
                if (baseAddr1.getPhone().contains("*")) {
                    //扔进垃圾表
                    bs_addrMapper.insertDiscard(baseAddr1);
                    bs_addrMapper.updateP5type(base_addr);
                    return;
                }
            }

            List<Base_addr> addressMessage = bs_addrMapper.getBaseAddrs(base_addr.getPhone());
            log.info("===============================取到 {} 条与增量数据手机号：{}  碰撞的数据的数据   ", addressMessage.size(),base_addr.getPhone());

            Base_addr baseAddr1 = getProcess(blockSizeByNum, blockSizeByStr, base_addr, addressMessage, true);
            //如果被合并了就结束
            if (baseAddr1.getP2type() == 222) {
                baseAddr1.setP5type(1);
                bs_addrMapper.insert3(baseAddr1);
                bs_addrMapper.updateP5type(base_addr);
                return;
            }

            //判断是基准值还是合并值入对应库
            base_addr.setP2type(223);
            base_addr.setMergeNum(0);
            base_addr.setP5type(1);
            base_addr.setEarliestTime(base_addr.getCreateTime());
            base_addr.setLatestTime(base_addr.getCreateTime());
            bs_addrMapper.insert5(base_addr);
            bs_addrMapper.updateP5type(base_addr);

            //如果插入基准表，就需要再与房屋地址碰撞
            List<Base_addr> baseAddrs = new ArrayList<>();
            baseAddrs.add(base_addr);

            //标准数据
            List<Base_addr> volList = base_addrMapper1.selectBaseAddr2(null,base_addr.getStreet());

            log.info("*********有 {} 条被比较值数据*********",volList.size());
            executor.execute(new CompareRunnable3(blockSizeByNum,blockSizeByStr,volList,baseAddrs));

        } catch (Exception e) {
            log.error("==========================================id为：{} 的数据发生异常", baseAddr.getId());
            e.printStackTrace();
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

        //数字字母处理
        String shortAddr = AsciiUtil.RegProcess(baseAddrBasics.getShortAddr());
        //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
        Map stringMap = stringParsingService.stringParse(shortAddr);
        strMapa.put("strb", (String[]) stringMap.get("strb1"));
        strMapb.put("strb", (String[]) stringMap.get("strb2"));

        if(TextUtils.isEmpty(baseAddrBasics)){
            return baseAddrBasics;
        }

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
                Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, num,false);


                //理论最大值
                BigDecimal asummax = (BigDecimal) processresult1.get("asummax");
                //实际得分
                BigDecimal asum = (BigDecimal) processresult1.get("asum");

                Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, str,false);

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
                        }
                        return baseAddrBasics;
                    }

                    baseAddrBasics.setOldPhone(baseAddrBasics.getPhone());
                    baseAddrBasics.setOldName1(baseAddrBasics.getName1());

                    //手机号姓名反写
                    if (!flag) {
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
        }
        return baseAddrBasics;
    }

}
