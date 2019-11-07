package com.dao.db1.system;

import com.dao.entity.system.SysFunction;

import java.util.List;
import java.util.Map;

public interface SysFunctionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_function
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_function
     *
     * @mbg.generated
     */
    int insert(SysFunction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_function
     *
     * @mbg.generated
     */
    SysFunction selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_function
     *
     * @mbg.generated
     */
    List<SysFunction> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_function
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysFunction record);
    //******************************************************自定义开始**************************************************

    /**
     * 保存功能
     *
     * @param sysFunction
     * @return 1成功
     * @date 2019-01-12
     */
    int save(SysFunction sysFunction);

    /**
     * 分页查询功能
     *
     * @param queryMap
     * @return 实体类集合
     * @date 2019-01-12
     */
    List<SysFunction> search(Map<String, Object> queryMap);

    /**
     * 查询所有顶级功能名称
     *
     * @param
     * @return list集合
     * @date 2019-01-12
     */

    List<SysFunction> selectAllTopFunction(Map<String, Object> queryMap);

    /**
     * 根据查询所有子功能名称
     *
     * @param id
     * @return list集合
     * @date 2019-01-12
     */
    List<SysFunction> selectAllChildren(String id);

    /**
     * 根据类型查询所有子功能名称
     *
     * @param queryMap
     * @return list集合
     * @date 2019-01-21
     */
    List<SysFunction> selectAllChildrenByType(Map<String, Object> queryMap);

    /**
     * 根据角色ids查询功能权限
     *
     * @param ids
     * @return list集合
     * @date 2019-01-15
     */
    List<SysFunction> selectFunctionByRoleIds(List<String> ids);

    int updateStateByIds(List<String> ids, Integer state);


}