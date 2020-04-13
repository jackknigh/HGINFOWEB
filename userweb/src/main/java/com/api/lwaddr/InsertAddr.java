package com.api.lwaddr;

import com.service.lwaddress.Bs_startWayService;
import com.utils.sys.lwaddress.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;


@Component
public class InsertAddr implements Job {
    @Autowired
    private Bs_startWayService bs_startWayService;
    @Value("${sysExecutor.similarityCorePoolSize}")
    private Integer similarityCorePoolSize;
    @Value("${sysExecutor.similarityMaxPoolSize}")
    private Integer similarityMaxPoolSize;

    private static ThreadPoolTaskExecutor executor;

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

    private static final Logger log = LoggerFactory.getLogger(InsertAddr.class);


    /**
     * 增量
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("================================开始增量操作。。。。。。。。。。 {}", DateUtil.getCurrDateTimeStr());
        initExecutor();
        bs_startWayService.increment(executor);
    }
}
