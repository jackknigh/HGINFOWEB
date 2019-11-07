package com.service.lwaddress.impl;

import com.config.HgApplicationProperty;
import com.service.lwaddress.Bs_startWayService;
import com.service.lwaddress.Bs_utilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
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
    private Executor asyncPromiseExecutor;

    private static final Logger log = LoggerFactory.getLogger(Bs_startWayServiceImpl.class);

    /**/
    @Override
    public void startway(int start, int total, int batchcCount) {
        log.debug("start susscces");

        BigDecimal n = new BigDecimal(applicationProperty.getInsertWeight());
        int startCount = (total - start) / batchcCount + 1;
        int startValue = start;
        //通过总数和步进值得出循环几次操作
        for (int j = 0; j < startCount; j++) {
//            getThreadInfo();
           /* if (j != 0 && j % 500 == 0) {
                try {
                    Thread.sleep(5 * 60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log.debug(e + "");
                }
            }*/
            log.debug("current j:" + j);
            //如果步进值不等于1
            if (batchcCount != 1) {
                //如果是最后一次操作
                if (j == startCount-1) {
                    //判断是否有余数，没有余数就直接跳出循环
                    if (total % batchcCount == 0 && total - start / batchcCount == 1) {
                        bs_utilService.addressStart(start, batchcCount, n);
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
                bs_utilService.addressStart(start, batchcCount, n);
                start = start + batchcCount;
                log.debug("finish susscces");
            } else {
                if (total - start / batchcCount == 0) {
                    log.debug("success");
                    break;
                }
                bs_utilService.addressStart(start, batchcCount, n);
                start = start + batchcCount;
                log.debug("finish susscces");
            }
        }
        log.debug("finish susscces");
    }

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


//    public void getThreadInfo() {
//        Object[] myThread = {asyncPromiseExecutor};
//        for (Object thread : myThread) {
//            ThreadPoolTaskExecutor threadTask = (ThreadPoolTaskExecutor) thread;
//            ThreadPoolExecutor threadPoolExecutor = threadTask.getThreadPoolExecutor();
//            log.info("提交任务数" + threadPoolExecutor.getTaskCount());
//            log.info("完成任务数" + threadPoolExecutor.getCompletedTaskCount());
//            log.info("当前有" + threadPoolExecutor.getActiveCount() + "个线程正在处理任务");
//            log.info("还剩" + threadPoolExecutor.getQueue().size() + "个任务");
//            log.info("当前可用队列长度-->", threadPoolExecutor.getQueue().remainingCapacity());
//        }
//    }
}
