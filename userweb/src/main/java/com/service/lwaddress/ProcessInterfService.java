package com.service.lwaddress;

import com.dao.entity.lwaddress.Phone;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.validation.constraints.Max;
import java.math.BigDecimal;
import java.util.List;

public interface ProcessInterfService {
    void processInterf(int start, int batchcCount, BigDecimal n, ThreadPoolTaskExecutor executor, ThreadPoolTaskExecutor executor1, List<Phone> phoneList);

    void compare(ThreadPoolTaskExecutor executor);

    void startwayPhone(ThreadPoolTaskExecutor executor, ThreadPoolTaskExecutor executor1);

    void compareSelf(ThreadPoolTaskExecutor executor);

    void function(int start, int end);
}
