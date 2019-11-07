package com.service.system;

import com.dao.entity.system.SysUserDepart;


/**
 * SysOperateLog 用户机构接口
 * Created by Xiezx on 2019-01-14.
 */
public interface SysUserDepartService {
    /**
     * 根据用户id删除所有角色id
     *
     * @param id
     * @return 1代表成功
     * @date 2019-01-14
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增
     *
     * @param
     * @return 1代表成功
     * @date 2019-01-14
     */
    int insert(SysUserDepart sysUserDepart);

    /**
     * 根据用户id查询出所有角色id
     *
     * @param id
     * @return 实体类
     * @date 2019-01-14
     */
    SysUserDepart selectByPrimaryKey(String id);

    /**
     * 更新
     *
     * @param
     * @return 1代表成功
     * @date 2019-01-14
     */
    int updateByPrimaryKey(SysUserDepart sysUserDepart);

    /**
     * 保存
     *
     * @param sysUserDepart
     * @return 1代表成功
     * @date 2019-01-14
     */
    int save(SysUserDepart sysUserDepart);
}
