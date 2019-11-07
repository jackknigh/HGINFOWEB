package com.service.lwaddress.impl;

import com.dao.entity.lwaddress.Base_addr;
import com.service.lwaddress.ProcessService;
import com.service.lwaddress.StringParsingService;
import com.utils.sys.lwaddress.AsciiUtil;
import com.utils.sys.lwaddress.MatchRunnable;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private StringParsingService stringParsingService;

    private static final Logger log = LoggerFactory.getLogger(ProcessServiceImpl.class);

    private static ThreadPoolTaskExecutor executor;

    static {
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);//核心线程大小
        executor.setMaxPoolSize(100);//最大线程大小
        executor.setQueueCapacity(99999);//队列最大容量
        executor.setKeepAliveSeconds(60);//存活时间
        executor.setThreadNamePrefix("async-match-");//线程名称
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
    }

    @Override
    //获取每个短号组(按标准分从大到小排)的第一位置为标准值，然后相似度匹配，匹配到的置为合并值，重复循环此操作，直至短号组所有元素被标记
    public Map<String, List<Base_addr>> processService(List<Base_addr> addressMessage, List<Base_addr> insertMessage, int str, int num, String reg) {

        Map<String, String[]> strMapa = new HashMap<>();
        Map<String, String[]> strMapb = new HashMap<>();

        Map<String, List<Base_addr>> map1 = new HashMap<>();

        int kk = -1;
        int sum = 1;
        log.info("*******************开始寻找基准值操作");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < addressMessage.size(); i++) {
            if (addressMessage.get(i).getP2type() == 222 || addressMessage.get(i).getP2type() == 223 || addressMessage.get(i).getP2type() == 224) {
                continue;
            } else {
                //如果短地址和姓名不为空就把这次的基准值取出
                if (!StringUtils.isBlank(addressMessage.get(i).getShortAddr()) && !StringUtils.isBlank(addressMessage.get(i).getName1())) {
                    //特殊字符处理
                    String shortAddr = AsciiUtil.SpecialHandl(addressMessage.get(i).getShortAddr());
                    shortAddr = shortAddr.replace(addressMessage.get(i).getName1(), "");
                    addressMessage.get(i).setShortAddr(shortAddr);

                    //数字
                    strMapa.put("stra", (String[]) stringParsingService.stringParse(addressMessage.get(i).getShortAddr()).get("strb1"));
                    //字符,先正则去除关键字，匹配前正则匹配去除省市区
                    String name = addressMessage.get(i).getShortAddr().replaceAll(reg, "");
                    strMapb.put("stra", (String[]) stringParsingService.stringParse(name).get("strb2"));

                    //标记为基准值
                    addressMessage.get(i).setP2type(223);
                    kk = i;
                    insertMessage.add(addressMessage.get(i));
                    if (sum == addressMessage.size()) {
                        kk = -1;
                    }
                    sum++;
                    break;
                }
            }
        }

        if(kk != -1) {
            //基准值
            Base_addr baseAddrBasics = addressMessage.get(kk);
            int total = addressMessage.size();
            CountDownLatch countDownLatch = new CountDownLatch(total);
            for (int i = 0; i < total; i++) {
                //被比较值
                Base_addr baseAddr = addressMessage.get(i);
                try {
                    Future<HashMap<String,Base_addr>> result = executor.submit(new MatchRunnable(baseAddrBasics, num, sum, reg, baseAddr, countDownLatch));
                    HashMap<String, Base_addr> map = result.get();
                    if(map == null){
                        continue;
                    }
                    //被比较的值
                    Base_addr addressMessage1 =  map.get("addressMessage");
                    //需要插入的值
                    Base_addr bsAddr =  map.get("bs_addr");
                    if (bsAddr != null) {
                        insertMessage.add(bsAddr);
                    }
                    baseAddr.setP2type(addressMessage1.getP2type());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            try {
                countDownLatch.await();
                log.info("基准值"+ baseAddrBasics.getId()+"执行完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        map1.put("addressMessage",addressMessage);
        map1.put("insertMessage",insertMessage);
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        log.info("*************************处理一次循环共用了:"+time+"毫秒");
        return map1;
    }
}