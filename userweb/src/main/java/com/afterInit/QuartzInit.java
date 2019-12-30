package com.afterInit;

import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.Base_addrMapper;
import com.dao.entity.system.SysQuartzConfig;
import com.service.antiEncode.EncodeStartWayService;
import com.service.antiEncode.InsertEncodeService;
import com.service.lwaddress.*;
import com.service.system.SysQuartzConfigService;
import com.utils.sys.QuartzManager;
import com.utils.sys.TextUtils;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by CuiL on 2019-04-04.
 */
@Component
@Order(value = 10)
public class QuartzInit implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(QuartzInit.class);

    @Autowired
    private HgApplicationProperty applicationProperty;

    @Autowired
    private EncodeStartWayService encodeStartWayService;

    @Autowired
    private SysQuartzConfigService sysQuartzConfigService;

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private Bs_startWayService bs_startWayService;
    @Autowired
    private InsertEncodeService insertEncodeService;
    @Autowired
    private ProcessStartService processStartService;
    @Autowired
    private Base_addrMapper base_addrMapper;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("执行定时任务开始............................................");
        List<SysQuartzConfig> sysQuartzConfigs = sysQuartzConfigService.listAllOk("%%","%%","%%",1);
        if (null != sysQuartzConfigs && sysQuartzConfigs.size() > 0) {
            try {
                for (SysQuartzConfig sysQuartzConfig : sysQuartzConfigs) {
                    if (TextUtils.isEmpty(sysQuartzConfig.getRunClass()) || TextUtils.isEmpty(sysQuartzConfig.getTaskTimer()))
                        continue;
                    try {
                        Class c = Class.forName(sysQuartzConfig.getRunClass());
                        QuartzManager.addJob(scheduler, sysQuartzConfig.getId(), c, sysQuartzConfig.getTaskTimer());
                        log.info("添加定时任务:"+sysQuartzConfig.getDepCode()+" "+sysQuartzConfig.getTaskCode()+" "+sysQuartzConfig.getTaskName()+" "+sysQuartzConfig.getTaskTimer());
                    } catch (Exception e) {
                        continue;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("执行定时任务结束............................................");
        }

//        //经纬度值
//        log.debug("========================测试开始==========================");
//        encodeStartWayService.startway(0, 2243099, 500);
//        log.debug("========================测试结束==========================");

/*        bs_utilService.startway( 0,30000,3000);
          processInterfService.processInterf();log.info("start way start");*/

        //合并(从clickhouse中取出数据进行合并操作)
        if(applicationProperty.getdelectTableIndex().compareTo("1")==0){
            processStartService.startway(Integer.valueOf(applicationProperty.getstartProcessCount()),Integer.valueOf(applicationProperty.gettotalProcessCount()) ,Integer.valueOf(applicationProperty.getprocessCount()));
            log.info("startway start end" );
        }

        //基础(切割地址，算分，插入数据到clickhouse)
        if(applicationProperty.getdelectTableIndex().compareTo("0")==0) {
         /*   int sum=base_addrMapper.selectSum();

            int total=Math.max(Integer.valueOf(applicationProperty.getTotalCount()),sum);*/

            bs_startWayService.startway(Integer.valueOf(applicationProperty.getStartCount()), Integer.valueOf(applicationProperty.getTotalCount()), Integer.valueOf(applicationProperty.getCount()));
        }

        //对比两个集合数据
        if(applicationProperty.getdelectTableIndex().compareTo("2")==0){
            processStartService.compare();
        }

/*
        InsertEncodeService.startway(7851501,647000,1499);
*/


    }
}

