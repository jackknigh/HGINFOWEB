package com.service.system;

import com.dao.entity.system.SysRole;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * SysRole接口
 * Created by XieZX on 2019-01-09.
 */
public interface SysRoleService {

    /**
     * 启用角色
     *
     * @param ids
     * @return
     * @date 2019-01-09
     */
    String updateStateOpenByIds(List<String> ids);

    /**
     * 禁用角色
     *
     * @param ids
     * @return
     * @date 2019-01-09
     */
    String updateStateCloseByIds(List<String> ids);

    /**
     * 新增角色
     *
     * @param record
     * @return 1代表成功
     * @date 2019-01-09
     */
    int insert(SysRole record);

    /**
     * 根据id查询角色
     *
     * @param id
     * @return 实体类
     * @date 2019-01-09
     */
    SysRole selectByPrimaryKey(String id);

    /**
     * 查询所有角色
     *
     * @param
     * @return 实体类集合
     * @date 2019-01-09
     */
    List<SysRole> selectAll();

    /**
     * 修改角色
     *
     * @param record
     * @return 1代表成功
     * @date 2019-01-09
     */
    int updateByPrimaryKey(SysRole record);

    /**
     * 保存角色
     *
     * @param record
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

    PageInfo<SysRole> search(Map<String, Object> queryMap);

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


    Map<String,List<String>> delByIds (List<String> ids);

}
