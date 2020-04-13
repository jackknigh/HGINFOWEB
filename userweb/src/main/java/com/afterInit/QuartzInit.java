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
    private ProcessStartService processStartService;

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

        //基础(切割地址，算分)
        if(applicationProperty.getdelectTableIndex().compareTo("0")==0) {
            bs_startWayService.startway(Integer.valueOf(applicationProperty.getStartCount()), Integer.valueOf(applicationProperty.getTotalCount()), Integer.valueOf(applicationProperty.getCount()));
        }

        //合并(对数据进行合并操作)
        if(applicationProperty.getdelectTableIndex().compareTo("1")==0){
            processStartService.startway(Integer.valueOf(applicationProperty.getstartProcessCount()),Integer.valueOf(applicationProperty.gettotalProcessCount()) ,Integer.valueOf(applicationProperty.getprocessCount()));
        }

        //左右数据集合相似度碰撞
        if(applicationProperty.getdelectTableIndex().compareTo("2")==0){
            processStartService.compare();
        }

        //数据自碰撞
        if(applicationProperty.getdelectTableIndex().compareTo("3")==0){
            processStartService.compareSelf();
        }

        //经纬度处理
        if(applicationProperty.getdelectTableIndex().compareTo("4")==0){
        log.debug("========================经纬度处理开始==========================");
        encodeStartWayService.startway(Integer.valueOf(applicationProperty.getStartCount()), Integer.valueOf(applicationProperty.getTotalCount()), Integer.valueOf(applicationProperty.getCount()));
        log.debug("========================经纬度处理结束==========================");
        }

        //更新经纬度
        if(applicationProperty.getdelectTableIndex().compareTo("5")==0){
            processStartService.update();
        }
    }
}

