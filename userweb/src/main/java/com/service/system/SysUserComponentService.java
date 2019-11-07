package com.service.system;

import com.dao.entity.system.SysUserComponent;

import java.util.List;

/**
 * 用户关联组件接口
 * Created by Xiezx on 2019-01-19.
 */
public interface SysUserComponentService {

    /**
     * 根据ids批量删除用户组件
     *
     * @param ids
     * @return 1代表成功
     * @date 2019-01-19
     */
    int deleteByPrimaryKey(List<String> ids);

    /**
     * 根据ids批量删除用户组件
     *
     * @param record
     * @return 1代表成功
     * @date 2019-01-19
     */
    int insert(SysUserComponent record);

    /**
     * 根据id查询用户组件
     *
     * @param id
     * @return 实体类
     * @date 2019-01-19
     */
    SysUserComponent selectByPrimaryKey(String id);

    /**
     * 查询用户组件
     *
     * @param
     * @return 组件集合
     * @date 2019-01-19
     */
    List<SysUserComponent> selectAll();

    /**
     * 更新用户组件
     *
     * @param record
     * @return 1代表成功
     * @date 2019-01-19
     */
    int updateByPrimaryKey(SysUserComponent record);

    /**
     * 保存用户组件
     *
     * @param sysUserComponent
     * @return 1成功
     * @date 2019-01-21
     */
    int save(SysUserComponent sysUserComponent);

    /**
     * 初始化用户组件
     *
     * @param userId
     * @return
     */
    int init(String userId);

    /**
     * 首页删除功能
     * @param sysUserComponent
     * @return
     */
    int updateByUserId(SysUserComponent sysUserComponent);
}
