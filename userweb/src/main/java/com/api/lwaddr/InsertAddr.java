package com.api.lwaddr;

import com.service.lwaddress.Bs_startWayService;
import com.utils.sys.lwaddress.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class InsertAddr implements Job {
    @Autowired
    private Bs_startWayService bs_startWayService;

    private static final Logger log = LoggerFactory.getLogger(InsertAddr.class);


    /**
     * 增量
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("================================开始增量操作。。。。。。。。。。 {}", DateUtil.getCurrDateTimeStr());
        bs_startWayService.increment();
    }
}
