/* Copyright (C) 2019-2019 Hangzhou HSH Co. Ltd.
 * All right reserved.*/
package com.utils.sys.lwaddress;

import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.Base_addrMapper;
import com.dao.db3.lwaddr.Base_addrMapper1;
import com.dao.entity.lwaddress.Base_addr;
import com.service.lwaddress.impl.ProcessGradeServiceImpl;
import com.service.lwaddress.impl.ProcessServiceImpl;
import com.service.lwaddress.impl.StringPasringServiceImpl;
import com.utils.sys.TextUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 相似度匹配线程
 */

public class CompareRunnable2 implements Runnable {

    @Autowired
    private HgApplicationProperty applicationProperty = ApplicationContextProvider.getBean(HgApplicationProperty.class);
    @Autowired
    private Base_addrMapper1 base_addrMapper1 = ApplicationContextProvider.getBean(Base_addrMapper1.class);
    @Autowired
    private Base_addrMapper base_addrMapper = ApplicationContextProvider.getBean(Base_addrMapper.class);

    private static final Logger log = LoggerFactory.getLogger(ProcessServiceImpl.class);
    private int num;
    private int str;
    private String areaName;
    private CountDownLatch countDownLatch;

    public CompareRunnable2(int num, int str, CountDownLatch countDownLatch, String areaName) {
        this.num = num;
        this.str = str;
        this.countDownLatch = countDownLatch;
        this.areaName = areaName;
    }

    @Override
    public void run() {
        //获取每个区域的数据
        List<Base_addr> baseAddrList = base_addrMapper.selectBaseAddrByArea(areaName);

        if(TextUtils.isEmpty(baseAddrList)){
            return;
        }

        Map<String, String[]> strMapa = new HashMap<>();
        Map<String, String[]> strMapb = new HashMap<>();
        StringPasringServiceImpl stringParsingService = new StringPasringServiceImpl();
        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();
        long startTime1 = System.currentTimeMillis();

        log.info("开始处理街道为：{} 的数据，共 {} 条",baseAddrList.get(0).getStreet(),baseAddrList.size());

        for (int i = 0; i < baseAddrList.size(); i++) {
            //如果数据已经跟其他数据合并过了，就不需要再判断了
            if (!StringUtils.isBlank(baseAddrList.get(i).getContrastId())) {
                continue;
            }
            if(i%5000==0){
                long endTime1 = System.currentTimeMillis();
                log.info("街道：{}  处理进度 {} --》 {} ,耗时 {}",baseAddrList.get(i).getStreet(),i,baseAddrList.size(),endTime1-startTime1);
            }

            //数字字母处理
            String shortAddr = AsciiUtil.RegProcess(baseAddrList.get(i).getShortAddr());
            Map stringMap = stringParsingService.stringParse(shortAddr);
            strMapa.put("stra", (String[]) stringMap.get("strb1"));
            strMapb.put("stra", (String[]) stringMap.get("strb2"));

            /*用于判断的阈值*/
            BigDecimal grace = new BigDecimal(applicationProperty.getGrace());

            for (int j = i+1; j < baseAddrList.size(); j++) {
                //如果数据已经跟其他数据合并过了，就不需要再判断了
                if (!StringUtils.isBlank(baseAddrList.get(j).getContrastId())) {
                    continue;
                }

                if (!StringUtils.isEmpty(baseAddrList.get(j).getShortAddr())) {
                    //数字字母处理
                    String shortAddr1 = AsciiUtil.RegProcess(baseAddrList.get(j).getShortAddr());
                    //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
                    Map stringMap1 = stringParsingService.stringParse(shortAddr1);
                    strMapa.put("strb", (String[]) stringMap1.get("strb1"));
                    strMapb.put("strb", (String[]) stringMap1.get("strb2"));

                    /*返回一个map出来，其中包括计算用的Double的sum,String的integerer[]*/
                    //数字和字符相似度匹配
                    Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, num,false);
                    BigDecimal suma = (BigDecimal) processresult1.get("sum");

                    Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, str,false);
                    BigDecimal sumb = (BigDecimal) processresult2.get("sum");

                    //计算相似度
                    BigDecimal sum = processGradeService.getSum(suma, sumb, (String[]) stringMap1.get("strb1"),(String[]) stringMap.get("strb1"),(String[]) stringMap1.get("strb2"),(String[]) stringMap.get("strb2"));

                    //如果相似度大于阈值
                    if (sum.compareTo(grace) > 0) {
                        //绑定数据关联关系
                        baseAddrList.get(j).setContrastId(baseAddrList.get(i).getId());
                        baseAddrList.get(j).setContrastScore(sum);
                    }
                }
            }
        }

        //数据切割,如果需要插入的数据的结果大于20w就切割成20W一份
        List<List<Base_addr>> lists = ListUtil.splitList(baseAddrList, applicationProperty.getSplitListSize());

        log.info("街道： {} 处理完成，数据： {} 条，切割成：{} 份集合",baseAddrList.get(0).getStreet(),baseAddrList.size(),lists.size());
        for (int i = 0; i < lists.size(); i++) {
            //插入合并值
            List<Base_addr> baseAddrs1 = insertCompareByMerge(lists.get(i));

            try {
                if (baseAddrs1 != null && baseAddrs1.size() > 0) {
                    //todo 插入基准值
                    log.info("准备插入数据：{} 条 ",baseAddrs1.size());
                    base_addrMapper1.insert5_2_1(baseAddrs1);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        long endTime1 = System.currentTimeMillis();
        log.info("用时{}毫秒,共 {} 个元素", endTime1 - startTime1, baseAddrList.size());
        baseAddrList = null;
        countDownLatch.countDown();
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
            if(list1.get(k) == null){
                continue;
            }

            if (!StringUtils.isBlank(list1.get(k).getContrastId())) {
                mergeAddrs.add(list1.get(k));
            }else {
                    // 两份，一份是把自身插入合并表，一份是当做基准数据使用
                    list1.get(k).setContrastId(list1.get(k).getId());
                    baseAddrss.add(list1.get(k));
            }
        }

        try {
            if (mergeAddrs != null && mergeAddrs.size() > 0) {
                //todo 插入合并值
                log.info("准备插入数据：{} 条 ",mergeAddrs.size());
                base_addrMapper1.insert3_2_1(mergeAddrs);
            }
            if (baseAddrss != null && baseAddrss.size() > 0) {
                //todo 插入标准合并值
                log.info("准备插入数据：{} 条 ",baseAddrss.size());
                base_addrMapper1.insert3_2_1(baseAddrss);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return baseAddrss;
    }
}
