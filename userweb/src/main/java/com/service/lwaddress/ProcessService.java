package com.service.lwaddress;

import com.dao.entity.lwaddress.Base_addr;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProcessService {
    Map<String,List<Base_addr>> processService(List<Base_addr> addressMessage, List<Base_addr> insertMessage, int str, int num, ThreadPoolTaskExecutor executor);



}
