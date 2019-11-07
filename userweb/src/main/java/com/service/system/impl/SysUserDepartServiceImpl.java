package com.service.system.impl;

import com.dao.db1.system.SysUserDepartMapper;
import com.dao.entity.system.SysUserDepart;
import com.service.system.SysUserDepartService;
import com.utils.sys.GenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * SysOperateLog 用户机构接口实现类
 * Created by Xiezx on 2019-01-14.
 */
@Transactional
@Service("sysUserDepartService")
public class SysUserDepartServiceImpl implements SysUserDepartService {
    @Autowired
    private SysUserDepartMapper sysUserDepartMapper;
    @Override
    public int deleteByPrimaryKey(String id) {
        return sysUserDepartMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysUserDepart sysUserDepart) {
        return sysUserDepartMapper.insert(sysUserDepart);
    }

    @Override
    public SysUserDepart selectByPrimaryKey(String id) {
        return sysUserDepartMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(SysUserDepart sysUserDepart) {
        return sysUserDepartMapper.updateByPrimaryKey(sysUserDepart);
    }

    @Override
    public int save(SysUserDepart sysUserDepart) {
        int ret=0;
        // 修改
        if (sysUserDepart.getUpdateFlg().equalsIgnoreCase("update")) {
            sysUserDepart.setUpdateDate(new Date());
            return sysUserDepartMapper.updateByPrimaryKey(sysUserDepart);
        }
        // 新增
        if (sysUserDepart.getUpdateFlg().equalsIgnoreCase("add")) {
            sysUserDepart.setId(GenUtil.getUUID());
            sysUserDepart.setCreateDate(new Date());
            return sysUserDepartMapper.insert(sysUserDepart);
        }

        return ret;
    }
}
