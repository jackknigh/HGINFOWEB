package com.dao.db1.system;

import com.dao.entity.system.SysHomeModule;

import java.util.List;
import java.util.Map;

public interface SysHomeModuleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_home_module
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_home_module
     *
     * @mbg.generated
     */
    int insert(SysHomeModule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_home_module
     *
     * @mbg.generated
     */
    SysHomeModule selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_home_module
     *
     * @mbg.generated
     */
    List<SysHomeModule> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_home_module
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysHomeModule record);
    //******************************************************自定义开始*****************************************************

    /**
     * 保存首页组件
     *
     * @param sysHomeModule
     * @return 1成功
     * @date 2019-01-12
     */
    int save(SysHomeModule sysHomeModule);

    /**
     * 分页查询首页组件
     *
     * @param queryMap
     * @return 实体类集合
     * @date 2019-01-12
     */
    List<SysHomeModule> search(Map<String, Object> queryMap);

    /**
     * 查询所有顶级首页组件名称
     *
     * @param
     * @return list集合
     * @date 2019-01-12
     */
    List<SysHomeModule> selectAllTopModule();

    /**
     * 根据查询所有子级首页组件名称
     *
     * @param id
     * @return list集合
     * @date 2019-01-12
     */
    List<SysHomeModule> selectAllChildren(String id);
}