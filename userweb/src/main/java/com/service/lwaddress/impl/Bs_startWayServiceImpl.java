package com.service.lwaddress.impl;

import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.Base_addrMapper;
import com.dao.db3.lwaddr.Base_addrMapper1;
import com.dao.entity.lwaddress.*;
import com.service.lwaddress.Bs_startWayService;
import com.service.lwaddress.Bs_utilService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class Bs_startWayServiceImpl implements Bs_startWayService {

    @Autowired
    private HgApplicationProperty applicationProperty;
    @Autowired
    private Bs_utilService bs_utilService;
    @Autowired
    private Base_addrMapper bs_addrMapper;
    @Autowired
    private Base_addrMapper1 base_addrMapper1;
    @Autowired
    private Executor asyncPromiseExecutor;

    private static final Logger log = LoggerFactory.getLogger(Bs_startWayServiceImpl.class);

    @Override
    public void startway(int start, int total, int batchcCount) {
        log.debug("start susscces");

        BigDecimal n = new BigDecimal(applicationProperty.getInsertWeight());
        String reg = getReg();
        int startCount = (total - start) / batchcCount + 1;
        int startValue = start;
        //通过总数和步进值得出循环几次操作
        for (int j = 0; j < startCount; j++) {
            log.debug("current j:" + j);
            //如果步进值不等于1
            if (batchcCount != 1) {
                //如果是最后一次操作
                if (j == startCount-1) {
                    //判断是否有余数，没有余数就直接跳出循环
                    if (total % batchcCount == 0 && total - start / batchcCount == 1) {
                        bs_utilService.addressStart(start, batchcCount, n,reg);
                        log.debug("finish susscces");
                        break;
                    }
                    //如果有余数就修改步进值为余数值
                    batchcCount = (total-startValue) - j * batchcCount;
                }
                if (batchcCount == 0 || start == total) {
                    log.debug("success");
                    break;
                }
                //余数处理
                bs_utilService.addressStart(start, batchcCount, n,reg);
                start = start + batchcCount;
                log.debug("finish susscces");
            } else {
                if (total - start / batchcCount == 0) {
                    log.debug("success");
                    break;
                }
                bs_utilService.addressStart(start, batchcCount, n,reg);
                start = start + batchcCount;
                log.debug("finish susscces");
            }
        }
        log.debug("finish susscces");
    }


    /**
     * 获取线程池实时情况
     * @return
     */
    @Override
    public Map getThreadInfo() {
        Map map =new HashMap();
        Object[] myThread = {asyncPromiseExecutor};
        for (Object thread : myThread) {
            ThreadPoolTaskExecutor threadTask = (ThreadPoolTaskExecutor) thread;
            ThreadPoolExecutor threadPoolExecutor = threadTask.getThreadPoolExecutor();
            log.info("提交任务数" + threadPoolExecutor.getTaskCount());
            log.info("完成任务数" + threadPoolExecutor.getCompletedTaskCount());
            log.info("当前有" + threadPoolExecutor.getActiveCount() + "个线程正在处理任务");
            log.info("还剩" + threadPoolExecutor.getQueue().size() + "个任务");
            log.info("当前可用队列长度-->", threadPoolExecutor.getQueue().remainingCapacity());
            System.out.println("还剩"+threadPoolExecutor.getQueue().size()+"个任务");
            map.put("提交任务数-->",threadPoolExecutor.getTaskCount());
            map.put("完成任务数-->",threadPoolExecutor.getCompletedTaskCount());
            map.put("当前有多少线程正在处理任务-->",threadPoolExecutor.getActiveCount());
            map.put("还剩多少个任务未执行-->",threadPoolExecutor.getQueue().size());
            map.put("当前可用队列长度-->",threadPoolExecutor.getQueue().remainingCapacity());
        }
        return map;
    }

    /**
     * 增量方法
     */
    @Override
    public void increment() {
        String reg = getReg();
        // 获取增量表的数据，模拟进来的数据，正常被处理后的数据p5type=7,异常数据=6
        List<Base_addr> baseAddrList = bs_addrMapper.getInsertDate();
        log.info("===========================================查到增量数据 {} 条",baseAddrList.size());

        if(baseAddrList.size()<1){
            return;
        }

        for (Base_addr base_addr : baseAddrList) {
            if (StringUtils.isBlank(base_addr.getPhone()) || base_addr.getPhone().length() < 5) {
                //插入废弃表
                base_addrMapper1.insertDiscard(base_addr);
                base_addrMapper1.updateP5type(base_addr);
            }
            //增量碰撞处理
            bs_utilService.increment(base_addr, reg);
        }
    }

    //配置的区的省市区街道关键字正则生成，并去除对应参数中包含的关键字
    public String getReg() {
        //配置的市的编号
        String cityCode = applicationProperty.getCityCode();
        StringBuilder regex = new StringBuilder();

        //查询市
        Bs_city cityName = bs_addrMapper.getCity(cityCode);
        regex.append(cityName.getCityName());

        //查询省
        Bs_province provinceName = bs_addrMapper.getProvince(cityName.getProvinceCode());
        regex.append("|").append(provinceName.getProvinceName()).append("|").append(provinceName.getShortName());

        //查询区
        List<Bs_area> areaNames = bs_addrMapper.getArea(cityCode);

        //todo 街道不需要去除
        for (Bs_area areaName : areaNames) {
            regex.append("|").append(areaName.getAreaName());
//            //查询街道
            List<Bs_street> streetNames = bs_addrMapper.getStreetName(areaName.getAreaCode());
            for (Bs_street streetName : streetNames) {
                regex.append("|").append(streetName.getStreetName()).append("|").append(streetName.getShortName());
            }
        }
        log.info(regex.toString());
        return regex.toString();
    }
}
