/* Copyright (C) 2019-2019 Hangzhou HSH Co. Ltd.
 * All right reserved.*/
package com.utils.sys.lwaddress;

import com.dao.entity.lwaddress.Base_addr;
import com.service.lwaddress.ProcessService;
import com.service.lwaddress.impl.ProcessServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 相似度匹配线程
 */
public class ProcessPhoneRunnable implements Callable<List<Base_addr>> {

    @Autowired
    private ProcessService processService = ApplicationContextProvider.getBean(ProcessService.class);

    private static final Logger log = LoggerFactory.getLogger(ProcessServiceImpl.class);
    private int blockSizeByNum;
    private int blockSizeByStr;
    private ThreadPoolTaskExecutor executor;
    private List<Base_addr> baseAddrs;
    private boolean flag;

    public ProcessPhoneRunnable(int blockSizeByNum, int blockSizeByStr,ThreadPoolTaskExecutor executor, List<Base_addr> baseAddrs, boolean flag) {
        this.blockSizeByNum = blockSizeByNum;
        this.blockSizeByStr = blockSizeByStr;
        this.executor = executor;
        this.baseAddrs = baseAddrs;
        this.flag = flag;
    }

    @Override
    public List<Base_addr> call() {
//        long startTime = System.currentTimeMillis();
        List<Base_addr> match = match(blockSizeByNum, blockSizeByStr,baseAddrs,executor,flag);
//        long endTime = System.currentTimeMillis();
//        log.info("线程"+Thread.currentThread().getName()+"执行了"+(endTime-startTime)+"毫秒");
        return match;
    }

    public List<Base_addr>  match(int blockSizeByNum,int blockSizeByStr,List<Base_addr> baseAddrs,  ThreadPoolTaskExecutor executor, boolean flag) {
        List<Base_addr> list = new ArrayList<>();
        //重置P2为0，因为原先的数据分222和223
        for (int j = 0; j < baseAddrs.size(); j++) {
            baseAddrs.get(j).setP2type(0);
        }

        int s = -1;

        for (int j = 0; j < baseAddrs.size(); j++) {
            //对数据进行处理，参数是所有数据，一个空的集合，滑块值
            Map<String, List<Base_addr>> map = processService.processService(baseAddrs, list, blockSizeByStr, blockSizeByNum,executor, flag);
            if ((map.get("insertMessage")).size() != s) {
                baseAddrs = map.get("addressMessage");
                s = (map.get("insertMessage")).size();
                list = map.get("insertMessage");
            } else {
                break;
            }
        }
        return list;
    }
}
