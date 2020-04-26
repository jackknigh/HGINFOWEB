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

public class CompareRunnable4 implements Runnable {

    @Autowired
    private HgApplicationProperty applicationProperty = ApplicationContextProvider.getBean(HgApplicationProperty.class);
    @Autowired
    private Base_addrMapper1 base_addrMapper1 = ApplicationContextProvider.getBean(Base_addrMapper1.class);

    private static final Logger log = LoggerFactory.getLogger(ProcessServiceImpl.class);
    private int num;
    private int str;
    //需碰撞的数据
    private List<Base_addr> baseAddrList;
    //标准数据
    private List<Base_addr> addressMessage;

    public CompareRunnable4(int num, int str,List<Base_addr> addressMessage , List<Base_addr> baseAddrList) {
        this.num = num;
        this.str = str;
        this.baseAddrList = baseAddrList;
        this.addressMessage = addressMessage;
    }

    @Override
    public void run() {
        long startTime1 = System.currentTimeMillis();
        Map<String, String[]> strMapb = new HashMap<>();
        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();
        Map<String, String[]> strMapa = new HashMap<>();
        StringPasringServiceImpl stringParsingService = new StringPasringServiceImpl();

            for (int j = 0; j < baseAddrList.size(); j++) {
            //数字字母处理
            String shortAddr = AsciiUtil.RegProcess(baseAddrList.get(j).getShortAddr());

            //去除末尾的号和室
            if(shortAddr.endsWith("号") || shortAddr.endsWith("室")){
                shortAddr = shortAddr.substring(0,shortAddr.length()-1);
            }
            //将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
            Map stringMap = stringParsingService.stringParse(shortAddr);
            strMapa.put("stra", (String[]) stringMap.get("strb1"));
            strMapb.put("stra", (String[]) stringMap.get("strb2"));

            /*用于判断的阈值*/
            BigDecimal grace = new BigDecimal(applicationProperty.getGrace());

            for (int i = 0; i < addressMessage.size(); i++) {
                boolean flag = false;
                //如果数据已经跟其他数据合并过了，就不需要再判断了
                if (!StringUtils.isBlank(addressMessage.get(i).getContrastId())) {
                    continue;
                }

                if (!StringUtils.isEmpty(addressMessage.get(i).getShortAddr())) {
                    //数字字母处理
                    String shortAddr1 = AsciiUtil.RegProcess(addressMessage.get(i).getShortAddr());
                    //去除末尾的号和室
                    if(shortAddr1.endsWith("号") || shortAddr1.endsWith("室")){
                        shortAddr1 = shortAddr1.substring(0,shortAddr1.length()-1);
                    }
                    //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
                    Map stringMap1 = stringParsingService.stringParse(shortAddr1);
                    strMapb.put("strb", (String[]) stringMap1.get("strb2"));
                    strMapa.put("strb", (String[]) stringMap1.get("strb1"));

                    //之后这里可以使用正则匹配，匹配到关键字就调高分值
                    //后续准备直接按照百分比上调分值。另外这个字典后期可能会前移到标准化那一步就做掉，提高后面效率。
                    if(shortAddr1.contains("罗东花园")){
                        flag = true;
                    }

                    //数字和字符相似度匹配
                    Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, num,false);
                    Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, str,flag);
                    BigDecimal suma = (BigDecimal) processresult1.get("sum");
                    BigDecimal sumb = (BigDecimal) processresult2.get("sum");

                    //计算相似度
                    BigDecimal sum = processGradeService.getSum(suma, sumb, (String[]) stringMap1.get("strb1"),(String[]) stringMap.get("strb1"),(String[]) stringMap1.get("strb2"),(String[]) stringMap.get("strb2"));

                    //如果相似度大于阈值
                    if (sum.compareTo(grace) > 0) {
                        //绑定数据关联关系
                        baseAddrList.get(j).setContrastScore(sum);
                        baseAddrList.get(j).setContrastId(addressMessage.get(i).getId());
                        baseAddrList.get(j).setAddrCode(addressMessage.get(i).getAddrCode());
                        //如果被比较值的街道为空，标准数据的街道不为空，就反写街道
                        if(StringUtils.isBlank(addressMessage.get(i).getStreet())&&!StringUtils.isBlank(baseAddrList.get(j).getStreet())){
                            baseAddrList.get(j).setStreet(addressMessage.get(i).getStreet());
                        }
                        continue;
                    }
                }
            }
            //每隔200条数据就打印一次输出日志
            if(j%2000==0) {
                long endTime1 = System.currentTimeMillis();
                log.info("**********碰撞进度 ： {} ---》 {}   用时 {} 毫秒**********", addressMessage.size(), j,endTime1 - startTime1);
            }
        }

        //数据切割,如果需要插入的数据的结果大于20w就切割成20W一份
        List<List<Base_addr>> lists = ListUtil.splitList(baseAddrList, 200000);
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
        log.info("**********用时{}毫秒,共 {} 个元素**********", endTime1 - startTime1, addressMessage.size());
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
