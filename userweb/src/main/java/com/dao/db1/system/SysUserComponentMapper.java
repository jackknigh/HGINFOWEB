package com.dao.db1.system;

import com.dao.entity.system.SysUserComponent;

import java.util.List;

public interface SysUserComponentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_component
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_component
     *
     * @mbg.generated
     */
    int insert(SysUserComponent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_component
     *
     * @mbg.generated
     */
    SysUserComponent selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_component
     *
     * @mbg.generated
     */
    List<SysUserComponent> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_component
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysUserComponent record);
    //******************************************************自定义开始*****************************************************

    /**
     * 保存用户组件
     * @param sysUserComponent
     * @return 1成功
     * @date 2019-01-21
     */
    int save(SysUserComponent sysUserComponent);

    /**
     * 首页删除功能
     * @param sysUserComponent
     * @return
     */
    int updateByUserId(SysUserComponent sysUserComponent);

}