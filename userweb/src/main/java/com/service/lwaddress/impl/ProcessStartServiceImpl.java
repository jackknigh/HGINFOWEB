package com.service.lwaddress.impl;

import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.Base_addrMapper;
import com.dao.db2.lwaddress.PhoneMapper;
import com.dao.entity.lwaddress.*;
import com.service.lwaddress.ProcessInterfService;
import com.service.lwaddress.ProcessStartService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ProcessStartServiceImpl implements ProcessStartService {

    @Autowired
    private HgApplicationProperty applicationProperty;
    @Autowired
    private ProcessInterfService processInterfService;
    @Autowired
    private PhoneMapper phoneMapper;

    private static final Logger log = LoggerFactory.getLogger(ProcessStartServiceImpl.class);

    @Value("${sysExecutor.similarityCorePoolSize}")
    private Integer similarityCorePoolSize;

    @Value("${sysExecutor.similarityMaxPoolSize}")
    private Integer similarityMaxPoolSize;

    private static ThreadPoolTaskExecutor executor;

    private static ThreadPoolTaskExecutor executor1;

    public void initExecutor(){
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(similarityCorePoolSize);//核心线程大小
        executor.setMaxPoolSize(similarityMaxPoolSize);//最大线程大小
        executor.setQueueCapacity(9999999);//队列最大容量
        executor.setKeepAliveSeconds(60);//存活时间
        executor.setThreadNamePrefix("async-match1-");//线程名称
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
    }

    public void initExecutor1(){
        executor1 = new ThreadPoolTaskExecutor();
        executor1.setCorePoolSize(similarityCorePoolSize);//核心线程大小
        executor1.setMaxPoolSize(similarityMaxPoolSize);//最大线程大小
        executor1.setQueueCapacity(9999999);//队列最大容量
        executor1.setKeepAliveSeconds(60);//存活时间
        executor1.setThreadNamePrefix("async-match2-");//线程名称
        executor1.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor1.initialize();
    }

    /**
     * 将数据扔给线程池异步处理
     * @param start 开始下标
     * @param total 处理量
     * @param batchcCount 步进值
     */
    @Override
    public void startway(int start, int total, int batchcCount) {
        initExecutor();
        initExecutor1();
        List<Phone> phoneList = new ArrayList<>();

        //获取phone表的手机号总数和手机号信息
        if("3".equals(applicationProperty.getStandardAddress())){
            total = phoneMapper.selectPhoneCount();
            phoneList = phoneMapper.selectByPhone();
        }
        log.info("共有手机号：{} 个",total);

        BigDecimal n=new BigDecimal(applicationProperty.getInsertWeight());
        for (int j = 0; j < total / batchcCount+1; j++) {
            log.info("current j:" + j);
            if (batchcCount != 1) {
                if (j == total / batchcCount ) {
                    if(total%batchcCount==0 && (total - start) / batchcCount == 0){
                        processInterfService.processInterf(start, batchcCount,n,executor,executor1,phoneList);
                        break;
                    }
                    batchcCount = total - j  * batchcCount;
                }
                if (batchcCount == 0 || start ==total) {
                    break;
                }
                processInterfService.processInterf(start, batchcCount,n,executor,executor1,phoneList);
                start = start + batchcCount;
            } else{
                if(total-start / batchcCount==0){
                    break;
                }
                processInterfService.processInterf(start, batchcCount,n,executor,executor1,phoneList);
                start = start + batchcCount;
            }
        }
    }

    @Override
    public void compare() {
        initExecutor();
        processInterfService.compare(executor);
    }

    @Override
    public void compareSelf() {
        initExecutor();
        processInterfService.compareSelf(executor);
    }

    @Override
    public void update() {
        int end = Integer.valueOf(applicationProperty.getTotalCount());
        int count = Integer.valueOf(applicationProperty.getCount());
        int start = Integer.valueOf(applicationProperty.getStartCount());

        int num = end/count+1;
        for (int i = 0; i < num; i++) {
            processInterfService.function1(start,start+count);
            start = start+count;
        }
    }
}
