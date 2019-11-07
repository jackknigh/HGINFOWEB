package com.service.system.impl;

import com.dao.entity.system.SysHomeModule;
import com.service.system.SysHomeModuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * SysHomeModule接口实现类
 * Created by Xiezx on 2019-01-12.
 */
@Transactional
@Service("sysHomeModuleService")
public class SysHomeModuleServiceImpl implements SysHomeModuleService {
    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(SysHomeModule sysHomeModule) {
        return 0;
    }

    @Override
    public SysHomeModule selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public List<SysHomeModule> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(SysHomeModule sysHomeModule) {
        return 0;
    }

    @Override
    public int save(SysHomeModule sysHomeModule) {
        return 0;
    }

    @Override
    public List<SysHomeModule> search(Map<String, Object> queryMap) {
        return null;
    }

    @Override
    public List<SysHomeModule> selectAllTopHomeModule() {
        return null;
    }

    @Override
    public List<SysHomeModule> selectAllChildren(String id) {
        return null;
    }
}
