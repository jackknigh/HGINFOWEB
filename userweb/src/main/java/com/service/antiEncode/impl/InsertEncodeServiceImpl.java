package com.service.antiEncode.impl;

import com.alibaba.fastjson.JSONObject;
import com.dao.db2.lwaddress.SecAddrMapper;
import com.dao.entity.lwaddress.Sec_addr;
import com.service.antiEncode.FindEncodeService;
import com.service.antiEncode.InsertEncodeService;
import com.utils.sys.DateUtil;
import com.utils.sys.TextUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;

@Service("insertEncodeService")
public class InsertEncodeServiceImpl implements InsertEncodeService {
    private static final Logger log = LoggerFactory.getLogger(InsertEncodeServiceImpl.class);

    @Autowired
    private FindEncodeService findEncodeService;

    @Autowired
    private SecAddrMapper secAddrMapper;

    @Value("${sysEncode.key}")
    private String keyValue;

    @Value("${sysEncode.url}")
    private String urlValue;

    private volatile Map<String, Integer> keyMap = new Hashtable();

    private List<String> urlList = new ArrayList();


    @Async("asyncPromiseExecutor")
    public void insertLngLat(int start, int number, CountDownLatch countDownLatch) {
        log.info("==========================线程:{} 开始执行  start:{} ===========================", Thread.currentThread().getName(), start);
        //查询指定步进值的地址信息
        List<Sec_addr> date = secAddrMapper.selectAddrsec(start, start+number);

        List<Sec_addr> date1 = new ArrayList<>();

        for (Sec_addr b1 : date) {
            if(b1.getLongitude() != null || b1.getType() != null){
                continue;
            }
            //调用高德地图查询经纬度
            Sec_addr secAddr = getSecAddr(b1);

            b1.setLatitude(secAddr.getLatitude());
            b1.setLongitude(secAddr.getLongitude());
            b1.setType(secAddr.getType());
            date1.add(b1);
        }

        try {
            if(TextUtils.isEmpty(date1)){
                return;
            }
            secAddrMapper.insert2(date1);
            countDownLatch.countDown();
            log.info("插入  start:{} number:{} 成功", start, number);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入  start:{} number:{} 失败", start, number);
        }
    }

    @Override
    public void init() {
        log.info("开始初始化key,url配置");
        if (!StringUtils.isBlank(keyValue) && !StringUtils.isBlank(urlValue)) {
            //所有key
            String[] keys = keyValue.split(",");
            for (String s : keys) {
                keyMap.put(s, 0);
            }
            //所有url
            String[] urls = urlValue.split(",");
            for (String s : urls) {
                urlList.add(s);
            }
        }
    }

    @Override
    public void errorProcess() {
        //查出所有异常数据
        List<Sec_addr> secAddrs = secAddrMapper.searchError();
        log.error("开始异常重试机制。。。。。。。。共"+secAddrs.size()+"条异常数据");

        for (Sec_addr secAddr : secAddrs) {
            //调用高德地图查询经纬度
            Sec_addr secAddr1 = getSecAddr(secAddr);
            if (secAddr1 == null) {
                log.error("所有key调用次数上限了");
                return;
            }
            try {
                secAddrMapper.update(secAddr1);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(JSONObject.toJSONString(secAddrs));
            }
        }
    }

    //判断key的调用次数是否上限
    private Sec_addr getSecAddr(Sec_addr secAddr) {
        Sec_addr secAddr1;
        Random random = new Random();
        //通过set集合打乱元素顺序，再转为list集合，生成随机的key
        Set<String> set = keyMap.keySet();
        List<String> keys = new ArrayList(set);
        int keyIndex = random.nextInt(keys.size());
        String key = keys.get(keyIndex);
        Integer count = keyMap.get(key);
        //随机的url
        int urlIndex = random.nextInt(urlList.size());
        String url = urlList.get(urlIndex);

        //如果key调用次数小于190万1900000
        if (count <= 1900000) {
            secAddr1 = findEncodeService.getLngLatFromOneAddr(secAddr, key, url);
            count = count+1;
            keyMap.put(key,count);
        } else {
            log.error("主键为: {} 的账号到达上限",key);
            //如果到了一天的上限，就从集合中移除
            keyMap.remove(key);
            if (keyMap.size() <= 0) {
                log.error("所有账号到达日上限");
                //睡眠到第二天
                try {
                    long today = isToday();
                    log.info("线程开始睡眠 {} 毫秒",today);
                    Thread.sleep(today);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //如果不是当天了，就重置接口调用次数
                log.info("***************开始重置key的使用次数***************");
                keyMap.clear();
                String[] keys1 = keyValue.split(",");
                for (String s : keys1) {
                    keyMap.put(s, 0);
                }
                log.info("***************重置完成***************");
            }
            secAddr1 = getSecAddr(secAddr);
        }
        return secAddr1;
    }

    //当前时间到第二天凌晨的时间间隔
    private long isToday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        // 改成这样就好了
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long time = cal.getTimeInMillis() - System.currentTimeMillis() + 1800000;
        return time;
    }
}

