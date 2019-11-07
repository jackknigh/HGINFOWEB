package com.service.antiEncode.impl;

import com.alibaba.fastjson.JSONObject;
import com.dao.db2.lwaddress.SecAddrMapper;
import com.dao.entity.lwaddress.Sec_addr;
import com.service.antiEncode.FindEncodeService;
import com.service.antiEncode.InsertEncodeService;
import com.utils.sys.DateUtil;
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

    private Map<String, Integer> keyMap = new Hashtable();

    private List<String> urlList = new ArrayList();


    @Async("asyncPromiseExecutor")
    public void insertLngLat(int start, int number) {
        log.info("==========================线程:{} 开始执行  start:{} ===========================", Thread.currentThread().getName(), start);
        //查询指定步进值的地址信息
        List<Sec_addr> date = secAddrMapper.selectAddrsec(start, number);

        for (Sec_addr b1 : date) {
            //如果不是当天了，就重置次数
            if (!isToday()) {
                log.info("开始重置key的使用次数。。。。。");
                keyMap.clear();
                String[] keys = keyValue.split(",");
                for (String s : keys) {
                    keyMap.put(s, 0);
                }
                log.info("重置完成。。。。。");
            }
            //调用高德地图查询经纬度
            Sec_addr secAddr = getSecAddr(b1);

            if (secAddr == null) {
                log.error("所有key调用次数上限了");
                return;
            }
            b1.setLatitude(secAddr.getLatitude());
            b1.setLongitude(secAddr.getLongitude());
            b1.setType(secAddr.getType());
        }

        try {
            secAddrMapper.insert2(date);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(JSONObject.toJSONString(date));
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

        for (Sec_addr secAddr : secAddrs) {
            //调用高德地图查询经纬度
            Sec_addr secAddr1 = getSecAddr(secAddr);
            if (secAddr1 == null) {
                log.error("所有key调用次数上限了");
                return;
            }
            secAddr.setLongitude(secAddr1.getLongitude());
            secAddr.setLatitude(secAddr1.getLatitude());
            secAddr.setType(secAddr1.getType());
        }

        try {
            secAddrMapper.update(secAddrs);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(JSONObject.toJSONString(secAddrs));
        }
    }

    //判断key的调用次数是否上限
    private Sec_addr getSecAddr(Sec_addr secAddr) {
        Sec_addr secAddr1;
        Random random = new Random();
        //随机的key
        Set<String> set = keyMap.keySet();
        List<String> keys = new ArrayList(set);
        int keyIndex = random.nextInt(keys.size());
        String key = keys.get(keyIndex);
        Integer count = keyMap.get(key);
        //随机的url
        int urlIndex = random.nextInt(urlList.size());
        String url = urlList.get(urlIndex);

        //如果key调用次数小于200万
        if (count <= 2000000) {
            secAddr1 = findEncodeService.getLngLatFromOneAddr(secAddr, key, url);
            keyMap.put(key, count++);
        } else {
            //如果到了一天的上限，就从集合中移除
            keyMap.remove(key);
            if (keyMap.size() <= 0) {
                log.error("所有key到达上限");
                return null;
            }
            secAddr1 = getSecAddr(secAddr);
        }
        return secAddr1;
    }

    //判断是否是第二天，如果是第二天，重置接口调用次数
    private boolean isToday() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        try {
            d = sdf.parse(dateNowStr);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("日期转换失败");
        }
        return DateUtil.isToday(d);
    }
}

