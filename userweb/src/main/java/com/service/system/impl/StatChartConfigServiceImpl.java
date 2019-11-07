package com.service.system.impl;

import com.dao.db1.system.StatChartConfigMapper;
import com.dao.db1.zjstat.entity.StatChartConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.system.StatChartConfigService;
import com.utils.sys.DepCodeUtils;
import com.utils.sys.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("statChartConfigService")
public class StatChartConfigServiceImpl implements StatChartConfigService {


    @Autowired
    private StatChartConfigMapper chartConfigMapper;

    @Override
    public PageInfo list(Integer currentPage, Integer pageSize, String depCode, String userId) {
        depCode = DepCodeUtils.getAllChildDepCodeLike(depCode);
        PageHelper.startPage(currentPage, pageSize);
        List<StatChartConfig> sysQuartzConfigs = chartConfigMapper.selectAll(depCode, userId);
        return new PageInfo<>(sysQuartzConfigs);
    }

    @Override
    public StatChartConfig detail(String id) {
        return chartConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public int save(StatChartConfig sysQuartzConfig) {
        int ret = 0;
        if (sysQuartzConfig == null) return ret;

        //修改
        if (!TextUtils.isEmpty(sysQuartzConfig.getUpdateFlg())) {
            if (sysQuartzConfig.getUpdateFlg().equalsIgnoreCase("update")) {
                return chartConfigMapper.updateByPrimaryKey(sysQuartzConfig);
            }
            //删除
            if (sysQuartzConfig.getUpdateFlg().equalsIgnoreCase("delete")) {
                return chartConfigMapper.deleteByPrimaryKey(sysQuartzConfig.getId());
            }
            //新增
            if (sysQuartzConfig.getUpdateFlg().equalsIgnoreCase("add")) {
                sysQuartzConfig.setId(TextUtils.getUUID());
                return chartConfigMapper.insert(sysQuartzConfig);
            }
        }

        return ret;
    }

    @Override
    public void delete(List<String> ids) {
        chartConfigMapper.deleteByPrimaryKeys(ids);
    }
}
