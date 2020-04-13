/* Copyright (C) 2019-2019 Hangzhou HSH Co. Ltd.
 * All right reserved.*/
package com.utils.sys.lwaddress;

import com.config.HgApplicationProperty;
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

/**
 * 相似度匹配线程
 */

public class CompareRunnable3 implements Runnable {

    @Autowired
    private HgApplicationProperty applicationProperty = ApplicationContextProvider.getBean(HgApplicationProperty.class);
    @Autowired
    private Base_addrMapper1 base_addrMapper1 = ApplicationContextProvider.getBean(Base_addrMapper1.class);

    private static final Logger log = LoggerFactory.getLogger(ProcessServiceImpl.class);
    private int num;
    private int str;
    //标准数据
    private List<Base_addr> baseAddrList;
    //需碰撞的数据
    private List<Base_addr> addressMessage;

    public CompareRunnable3(int num, int str, List<Base_addr> baseAddrList, List<Base_addr> addressMessage) {
        this.num = num;
        this.str = str;
        this.baseAddrList = baseAddrList;
        this.addressMessage = addressMessage;
    }

    @Override
    public void run() {
        Map<String, String[]> strMapb = new HashMap<>();
        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();
        Map<String, String[]> strMapa = new HashMap<>();
        StringPasringServiceImpl stringParsingService = new StringPasringServiceImpl();
        long startTime1 = System.currentTimeMillis();

            for (int j = 0; j < baseAddrList.size(); j++) {
            //数字字母处理
            String shortAddr = AsciiUtil.RegProcess(baseAddrList.get(j).getShortAddr());
            //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
            Map stringMap = stringParsingService.stringParse(shortAddr);
            strMapb.put("stra", (String[]) stringMap.get("strb2"));
            strMapa.put("stra", (String[]) stringMap.get("strb1"));

            /*用于判断的阈值*/
            BigDecimal grace = new BigDecimal(applicationProperty.getGrace());
            BigDecimal grace1 = new BigDecimal(0.93);

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
                    strMapa.put("strb", (String[]) stringMap1.get("strb1"));
                    strMapb.put("strb", (String[]) stringMap1.get("strb2"));

                    //数字和字符相似度匹配
                    Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, num);
                    Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, str);
                    BigDecimal suma = (BigDecimal) processresult1.get("sum");
                    BigDecimal sumb = (BigDecimal) processresult2.get("sum");

                    //计算相似度
                    BigDecimal sum = processGradeService.getSum(suma, sumb, (String[]) stringMap1.get("strb1"),(String[]) stringMap.get("strb1"),(String[]) stringMap1.get("strb2"),(String[]) stringMap.get("strb2"));

                    //如果标准数据末尾是号结尾，调高阈值
                    if(baseAddrList.get(j).getShortAddr().endsWith("号")){
                        //如果相似度大于阈值
                        if (sum.compareTo(grace1) > 0) {
                            //绑定数据关联关系
                            addressMessage.get(i).setContrastId(baseAddrList.get(j).getId());
                            addressMessage.get(i).setContrastScore(sum);
                            addressMessage.get(i).setAddrCode(baseAddrList.get(j).getAddrCode());
                        }
                            continue;
                    }
                    //如果相似度大于阈值
                    if (sum.compareTo(grace) > 0) {
                        //绑定数据关联关系
                        addressMessage.get(i).setContrastId(baseAddrList.get(j).getId());
                        addressMessage.get(i).setContrastScore(sum);
                        addressMessage.get(i).setAddrCode(baseAddrList.get(j).getAddrCode());
                        //如果被比较值的街道为空，标准数据的街道不为空，就反写街道
                        if(StringUtils.isBlank(addressMessage.get(i).getStreet())&&!StringUtils.isBlank(baseAddrList.get(j).getStreet())){
                            addressMessage.get(i).setStreet(baseAddrList.get(j).getStreet());
                        }
                        continue;
                    }
                }
            }
            if(j%2000==0) {
                long endTime1 = System.currentTimeMillis();
                log.info("**********碰撞进度 ： {} ---》 {}   用时 {} 毫秒**********", baseAddrList.size(), j,endTime1 - startTime1);
            }
        }

        //数据切割,如果需要插入的数据的结果大于20w就切割成20W一份
        List<List<Base_addr>> lists = ListUtil.splitList(addressMessage, 200000);
        if(TextUtils.isEmpty(lists)){
            return;
        }

        log.info("**********切割成：{} 份集合**********",lists.size());
        for (int i = 0; i < lists.size(); i++) {
            //插入合并值
            List<Base_addr> baseAddrs1 = insertCompareByMerge(lists.get(i));

            try {
                if (baseAddrs1 != null && baseAddrs1.size() > 0) {
                    //插入基准值
                    log.info("**********准备插入数据：{} 条 **********",baseAddrs1.size());
                    base_addrMapper1.insert5_2_1(baseAddrs1);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        long endTime1 = System.currentTimeMillis();
        log.info("**********用时{}毫秒,共 {} 个元素**********", endTime1 - startTime1, baseAddrList.size());
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
                baseAddrss.add(list1.get(k));
            }
        }

        try {
            if (mergeAddrs != null && mergeAddrs.size() > 0) {
                log.info("准备插入数据：{} 条 ",mergeAddrs.size());
                base_addrMapper1.insert3_2_1(mergeAddrs);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return baseAddrss;
    }
}
