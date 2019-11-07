package com.service.system.impl;

import com.dao.db1.system.SysRoleFunctionResourceMapper;
import com.dao.entity.system.SysFunction;
import com.dao.entity.system.SysRoleFunctionResource;
import com.service.system.SysRoleFunctionResourceService;
import com.utils.sys.GenUtil;
import com.utils.sys.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Service("sysRoleFunctionResourceService")
public class SysRoleFunctionResourceServiceImpl implements SysRoleFunctionResourceService {
    @Autowired
    private SysRoleFunctionResourceMapper sysRoleFunctionResourceMapper;

    @Override
    public int deleteByPrimaryKey(List<String> ids) {
        return sysRoleFunctionResourceMapper.deleteByPrimaryKey(ids);
    }

    @Override
    public int insert(SysRoleFunctionResource record) {
        record.setId(GenUtil.getUUID());
        record.setCreateDate(new Date());
        return sysRoleFunctionResourceMapper.insert(record);
    }

    @Override
    public SysRoleFunctionResource selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public List<SysRoleFunctionResource> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(SysRoleFunctionResource record) {
        return 0;
    }

    @Override
    public int save(SysRoleFunctionResource sysRoleFunctionResource) {
        int ret = 0;

        if ((sysRoleFunctionResource != null) && (!TextUtils.isEmpty(sysRoleFunctionResource.getUpdateFlg()))) {
            // 修改
            if (sysRoleFunctionResource.getUpdateFlg().equalsIgnoreCase("update")) {
                sysRoleFunctionResource.setUpdateDate(new Date());
                return sysRoleFunctionResourceMapper.updateByRoleId(sysRoleFunctionResource);
            }
            // 新增
            if (sysRoleFunctionResource.getUpdateFlg().equalsIgnoreCase("add")) {
                sysRoleFunctionResource.setId(GenUtil.getUUID());
                sysRoleFunctionResource.setCreateDate(new Date());
                return sysRoleFunctionResourceMapper.insert(sysRoleFunctionResource);
            }
        }
        return ret;
    }

    /**
     * 根据角色ID列表获取所有权限
     *
     * @param ids
     * @return
     */
    @Override
    public List<SysRoleFunctionResource> listSysRoleFunctionByRoleIds(List<String> ids) {
        return sysRoleFunctionResourceMapper.selectByRoleIds(ids);
    }

    @Override
    public List<SysFunction> selectByFunctionId(String functionId) {
        return sysRoleFunctionResourceMapper.selectByFunctionId(functionId);
    }

    /**
     * 根据角色ID列表删除记录
     *
     * @param ids
     * @return
     */
    @Override
    public void delSysRoleFunctionByRoleIds(List<String> ids) {
          sysRoleFunctionResourceMapper.delByRoleIds(ids);
    }
}
