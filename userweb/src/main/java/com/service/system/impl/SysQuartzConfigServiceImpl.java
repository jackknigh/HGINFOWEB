package com.service.system.impl;

import com.dao.db1.system.SysQuartzConfigMapper;
import com.dao.entity.system.SysQuartzConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.system.SysQuartzConfigService;
import com.utils.sys.DepCodeUtils;
import com.utils.sys.QuartzManager;
import com.utils.sys.TextUtils;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("sysQuartzConfigService")
public class SysQuartzConfigServiceImpl implements SysQuartzConfigService {
    private static final Logger log = LoggerFactory.getLogger(SysQuartzConfigServiceImpl.class);
    @Autowired
    private SysQuartzConfigMapper quartzConfigMapper;
    @Autowired
    Scheduler scheduler;

    @Override
    public List<SysQuartzConfig> listAllOk(String depCode, String userId, String taskType, Integer state) {
        return quartzConfigMapper.selectAllOk(depCode, userId, taskType, state);
    }

    @Override
    public PageInfo list(Integer currentPage, Integer pageSize, String depCode, String userId, String taskType) {
        depCode = DepCodeUtils.getAllChildDepCodeLike(depCode);
        PageHelper.startPage(currentPage, pageSize);
        List<SysQuartzConfig> sysQuartzConfigs = quartzConfigMapper.selectAll(depCode, userId, taskType);
        return new PageInfo<>(sysQuartzConfigs);
    }

    @Override
    public SysQuartzConfig detail(String id) {
        return quartzConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public int save(SysQuartzConfig sysQuartzConfig) {
        int ret = 0;
        if (sysQuartzConfig == null) return ret;

        //修改
        if (!TextUtils.isEmpty(sysQuartzConfig.getUpdateFlg())) {
            if (sysQuartzConfig.getUpdateFlg().equalsIgnoreCase("update")) {
                return quartzConfigMapper.updateByPrimaryKey(sysQuartzConfig);
            }
            //删除
            if (sysQuartzConfig.getUpdateFlg().equalsIgnoreCase("delete")) {
                return quartzConfigMapper.deleteByPrimaryKey(sysQuartzConfig.getId());
            }
            //新增
            if (sysQuartzConfig.getUpdateFlg().equalsIgnoreCase("add")) {
                sysQuartzConfig.setId(TextUtils.getUUID());
                return quartzConfigMapper.insert(sysQuartzConfig);
            }
        }

        return ret;
    }

    @Override
    public void delete(List<String> ids) {
        quartzConfigMapper.deleteByPrimaryKeys(ids);
    }

    @Override
    public void startUse(List<String> ids, String state) throws ClassNotFoundException {
        if(!TextUtils.isEmpty(state)){
            quartzConfigMapper.startUse(ids, state);
            SysQuartzConfig sysQuartzConfig;
            for(String id:ids){
                sysQuartzConfig = quartzConfigMapper.selectByPrimaryKey(id);
                if(state.equalsIgnoreCase("-1")) {
                    QuartzManager.removeJob(scheduler, sysQuartzConfig.getId());
                    log.info("移除定时任务："+sysQuartzConfig.getTaskName() +" "+ sysQuartzConfig.getId());
                }
                if(state.equalsIgnoreCase("1")){
                    Class c = Class.forName(sysQuartzConfig.getRunClass());
                    QuartzManager.addJob(scheduler, sysQuartzConfig.getId(), c, sysQuartzConfig.getTaskTimer());
                    log.info("添加定时任务:"+sysQuartzConfig.getDepCode()+" "+sysQuartzConfig.getTaskCode()+" "+sysQuartzConfig.getTaskName()+" "+sysQuartzConfig.getTaskTimer());
                }

            }
        }


    }
}
