package com.service.lwaddress;

import com.dao.entity.lwaddress.Base_addr;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;

public interface Bs_utilService {
    void addressStart(int number1, int number2,String reg,Map<String, Object> allMessage);

    void increment(Base_addr baseAddr, String reg, Map<String, Object> allMessage, ThreadPoolTaskExecutor executor);
}
