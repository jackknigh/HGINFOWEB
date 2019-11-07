package com.service.system;

import com.dao.entity.system.SysFunction;
import com.dao.entity.system.SysRoleFunctionResource;

import java.util.List;

/**
 * 角色关联功能&资源接口
 * Created by Xiezx on 2019-01-19.
 */
public interface SysRoleFunctionResourceService {

    /**
     * 根据ids批量删除角色权限
     *
     * @param ids
     * @return 1代表成功
     * @date 2019-01-19
     */
    int deleteByPrimaryKey(List<String> ids);

    /**
     * 新增角色权限
     *
     * @param record
     * @return 1代表成功
     * @date 2019-01-19
     */
    int insert(SysRoleFunctionResource record);

    /**
     * 根据角色id查询角色权限
     *
     * @param id
     * @return 实体类
     * @date 2019-01-19
     */
    SysRoleFunctionResource selectByPrimaryKey(String id);

    /**
     * 查询角色权限
     *
     * @param
     * @return 实体类集合
     * @date 2019-01-19
     */
    List<SysRoleFunctionResource> selectAll();

    /**
     * 修改角色权限
     *
     * @param record
     * @return 1代表成功
     * @date 2019-01-19
     */
    int updateByPrimaryKey(SysRoleFunctionResource record);

    /**
     * 保存角色权限
     *
     * @param
     * @return 实体类集合
     * @date 2019-01-19
     */
    int save(SysRoleFunctionResource sysRoleFunctionResource);


    /**
     * 根据角色ID列表获取所有权限
     *
     * @param ids
     * @return
     */
    List<SysRoleFunctionResource> listSysRoleFunctionByRoleIds(List<String> ids);

    /**
     * 根据角色ID列表删除记录
     *
     * @param ids
     * @return
     */
    void delSysRoleFunctionByRoleIds(List<String> ids);

    List<SysFunction> selectByFunctionId(String functionId);
}
