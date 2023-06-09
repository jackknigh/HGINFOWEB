package com.dao.db1.system;

import com.dao.entity.system.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbg.generated
     */
    int updateStateByIds(@Param("ids") List<String> ids, @Param("state") Integer state);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbg.generated
     */
    int insert(SysRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbg.generated
     */
    SysRole selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbg.generated
     */
    List<SysRole> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysRole record);
    //**********************************************************自定义开始*****************************************************************************************
    /**
     * Created by XieZX on 2019-01-09.
     */
    /**
     * 保存角色
     *
     * @param record 实体类
     * @return 1代表成功
     * @date 2019-01-09
     */
    int save(SysRole record);

    /**
     * 根据角色名查询
     *
     * @param roleName
     * @return 实体类
     * @date 2019-01-09
     */
    SysRole selectByRoleName(String roleName);

    /**
     * 根据条件分页查询角色
     *
     * @param queryMap 集合
     * @return 实体类集合
     * @date 2019-01-09
     */
    List<SysRole> search(Map<String, Object> queryMap);

    /**
     * 查询所有角色名称
     *
     * @param
     * @return list集合
     * @date 2019-01-09
     */
    List<?> selectAllRoleName();

    /**
     * 根据ids查询角色名称
     *
     * @param ids
     * @return list集合
     * @date 2019-01-15
     */
    List<String> selectNameByIds(List<String> ids);


    void delByPrimaryKey(String id);
}