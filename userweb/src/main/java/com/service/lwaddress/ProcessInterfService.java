package com.service.lwaddress;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.math.BigDecimal;

public interface ProcessInterfService {
    void processInterf(int start, int batchcCount, BigDecimal n, ThreadPoolTaskExecutor executor, ThreadPoolTaskExecutor executor1);

    void compare(ThreadPoolTaskExecutor executor);
}
