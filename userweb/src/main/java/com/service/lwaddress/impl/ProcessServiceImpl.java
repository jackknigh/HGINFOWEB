package com.service.lwaddress.impl;

import com.dao.entity.lwaddress.Base_addr;
import com.service.lwaddress.ProcessService;
import com.service.lwaddress.StringParsingService;
import com.utils.sys.lwaddress.AsciiUtil;
import com.utils.sys.lwaddress.DateUtil;
import com.utils.sys.lwaddress.MatchRunnable;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@Service
public class ProcessServiceImpl implements ProcessService {
    @Autowired
    private StringParsingService stringParsingService;

    @Value("${sysExecutor.StepValue}")
    private Integer stepValue;

    private static final Logger log = LoggerFactory.getLogger(ProcessStartServiceImpl.class);

    @Override
    //获取每个短号组(按标准分从大到小排)的第一位置为标准值，然后相似度匹配，匹配到的置为合并值，重复循环此操作，直至短号组所有元素被标记
    public Map<String, List<Base_addr>> processService(List<Base_addr> addressMessage, List<Base_addr> insertMessage, int str, int num,ThreadPoolTaskExecutor executor,boolean flag) {

        Map<String, String[]> strMapa = new HashMap<>();
        Map<String, String[]> strMapb = new HashMap<>();

        Map<String, List<Base_addr>> map1 = new HashMap<>();

        int kk = -1;
//        log.info("*******************开始寻找基准值操作");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < addressMessage.size(); i++) {
            if (addressMessage.get(i).getP2type() == 222 || addressMessage.get(i).getP2type() == 223 || addressMessage.get(i).getP2type() == 224) {
                continue;
            } else {
                //如果短地址和姓名不为空就把这次的基准值取出
                if (!StringUtils.isBlank(addressMessage.get(i).getShortAddr())) {
                    //特殊字符处理,第一步已经处理过了
//                    String shortAddr = AsciiUtil.SpecialHandl(addressMessage.get(i).getShortAddr());
//                    if(!StringUtils.isBlank(addressMessage.get(i).getName1())) {
//                        shortAddr = shortAddr.replace(addressMessage.get(i).getName1(), "");
//                    }
//                    addressMessage.get(i).setShortAddr(shortAddr);

                    //数字
                    strMapa.put("stra", (String[]) stringParsingService.stringParse(addressMessage.get(i).getShortAddr()).get("strb1"));
                    //字符,先正则去除关键字，匹配前正则匹配去除省市区
                    strMapb.put("stra", (String[]) stringParsingService.stringParse(addressMessage.get(i).getShortAddr()).get("strb2"));

                    //标记为基准值
                    addressMessage.get(i).setP2type(223);

                    kk = i;
                    insertMessage.add(addressMessage.get(i));
                    if (i == addressMessage.size()-1) {
                        kk = -1;
                    }
                    break;
                }
            }
        }

        if (kk != -1) {
            //基准值
            Base_addr baseAddrBasics = addressMessage.get(kk);
            //总数
            int total = addressMessage.size();
            //步进值
            int count = stepValue;
//          CountDownLatch countDownLatch = new CountDownLatch(total);

            if(total/executor.getCorePoolSize() > count){
                count = total/executor.getCorePoolSize()+1;
//                log.info("修改步进值为"+count);
            }

            //起始值
            int start = 0;
            //循环次数
            int value = total / count;
            Future<HashMap<String, Object>>[] future = new Future[value+1];

            //总数是否大于步进值
            if (value != 0) {
                for (int i = 0; i < future.length; i++) {
                    //刚好凑整，没有余数
                    if (i * count == total) {
                        break;
                    }
                    //如果是最后一次
                    if (i == value) {
                        //处理最后的余数
                        int remainder = total - (value * count);
                        future[i] = executor.submit(new MatchRunnable(num, str,baseAddrBasics, addressMessage, start, remainder + start,flag));
                        continue;
                    }
                    //不是最后一次，相似度匹配
                    future[i] = executor.submit(new MatchRunnable(num, str,baseAddrBasics, addressMessage, start, count + start,flag));
                    start = start + count;
                }
            } else {
                //总数小于步进值
                future[0] = executor.submit(new MatchRunnable(num, str,baseAddrBasics,addressMessage, start, total,flag));
            }
            try {
                //获取多线程的处理结果
                for (int i = 0; i < future.length; i++) {
                    if(future[i] == null){
                        break;
                    }
                    HashMap<String, Object> stringListHashMap1 = future[i].get();
                    List<Base_addr> addrs = (List<Base_addr>)stringListHashMap1.get("addressMessage");
                    int star = (int)stringListHashMap1.get("start");
                    int end = (int)stringListHashMap1.get("end");
                    addressMessage = disposeAddressMsg(addressMessage, addrs, star, end);
                    List<Base_addr> inserts = (List<Base_addr>)stringListHashMap1.get("insertMessage");
                    if (inserts != null || inserts.size() > 0) {
                        insertMessage.addAll(inserts);
                    }
                }
            } catch (InterruptedException e) {
                log.error("错误信息：{}",e);
            } catch (ExecutionException e) {
                log.error("错误信息：{}",e);
            }

//            for (int i = 0; i < total; i++) {
//                //被比较值
//                Base_addr baseAddr = addressMessage.get(i);
//                try {
//                    Future<HashMap<String,Base_addr>> result = executor.submit(new MatchRunnable(baseAddrBasics, num, sum, reg, baseAddr, countDownLatch));
//                    HashMap<String, Base_addr> map = result.get();
//                    if(map == null){
//                        continue;
//                    }
//                    //被比较的值
//                    Base_addr addressMessage1 =  map.get("addressMessage");
//                    //需要插入的值
//                    Base_addr bsAddr =  map.get("bs_addr");
//                    if (bsAddr != null) {
//                        insertMessage.add(bsAddr);
//                    }
//                    baseAddr.setP2type(addressMessage1.getP2type());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }

//            try {
//                //所有线程是否执行完毕
//                countDownLatch.await();
//            log.info("基准值" + baseAddrBasics.getId() + "执行完成");
//            } catch (InterruptedException e) {
//                log.error(e.getMessage());
//            }

            long endTime = System.currentTimeMillis();
            long time = endTime - startTime;
            log.info("*************************处理 {} 条数据相似度匹配用了 {} 毫秒:",addressMessage.size(),time);
        }
        map1.put("addressMessage", addressMessage);
        map1.put("insertMessage", insertMessage);
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        log.info("*************************处理一次循环共用了:" + time + "毫秒");
        return map1;
    }

    /**
     * 处理每个街道组数据
     *
     * @param addressMessage 源数据
     * @param disposeAddress 线程处理后的数据
     * @param start          起始值
     * @param end            结束值
     * @return
     */
    public List<Base_addr> disposeAddressMsg(List<Base_addr> addressMessage, List<Base_addr> disposeAddress, int start, int end) {
        for (int i = start; i < end; i++) {
            addressMessage.get(i).setP2type(disposeAddress.get(i).getP2type());
        }
        return addressMessage;
    }
}