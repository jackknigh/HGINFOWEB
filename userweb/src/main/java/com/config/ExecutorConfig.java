package com.config;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Jiaoziran
 * @date 2019/06/04 11:33
 * TODO:线程池的配置
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);

    @Value("${sysExecutor.corePoolSize}")
    private Integer corePoolSize;

    @Value("${sysExecutor.maxPoolSize}")
    private Integer maxPoolSize;

    /**
     * TODO: 此方法名称为asyncPromiseExecutor，即在spring中注入了一个名字为asyncPromiseExecutor的bean
     * 方法名只要在项目中唯一性，可以适当任意取（最好遵循一定的规则）
     * 使用方法：在需要加入线程池的方法上增加注解@Async("asyncPromiseExecutor")就可以加入此线程池异步执行
     *
     * @return
     * @throws
     * @author Jiaoziran
     *  * @date 2019/06/04 11:33
     * @params
     */
    @Bean
    public Executor asyncPromiseExecutor() {
        logger.info("start asyncPromiseExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(corePoolSize);
        //配置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //配置超时时间
        executor.setKeepAliveSeconds(60);
//        //配置核心线程数
//        executor.setCorePoolSize(3);
//        //配置最大线程数
//        executor.setMaxPoolSize(5);
        //配置队列大小
        executor.setQueueCapacity(9999999);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-promise-");
        /**
         * rejection-policy：当pool已经达到max size的时候，如何处理新任务
         * CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //初始化执行器
        executor.initialize();
        return executor;
    }
}
