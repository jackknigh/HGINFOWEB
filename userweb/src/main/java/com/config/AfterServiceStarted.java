package com.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AfterServiceStarted implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AfterServiceStarted.class);
    /**
     * 会在服务启动完成后立即执行
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("Successful service startup!");
    }
}
