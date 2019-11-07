package com.service.system;

import com.dao.entity.system.SysUser;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * SysUser接口
 * Created by Xiezx on 2019-01-08.
 */
public interface SysUserService {

    /**
     * 根据id批量删除用户
     *
     * @param ids
     * @return 实体类集合
     * @date 2019-01-08
     */
    int deleteByPrimaryKey(List<String> ids);

    /**
     * 新增用户
     *
     * @param sysUser
     * @return 1代表成功
     * @date 2019-01-08
     */
    int insert(SysUser sysUser);

    /**
     * 根据id查询用户
     *
     * @param id
     * @return 实体类
     * @date 2019-01-08
     */
    SysUser selectByPrimaryKey(String id);

    /**
     * 查询所有用户
     *
     * @param
     * @return 实体类集合
     * @date 2019-01-08
     */
    List<SysUser> selectAll();

    /**
     * 编辑用户
     *
     * @param sysUser 实体类
     * @return 1成功
     * @date 2019-01-08
     */
    int updateByPrimaryKey(SysUser sysUser);

    /**
     * 保存用户
     *
     * @param sysUser
     * @return 1成功
     * @date 2019-01-08
     */
    int save(SysUser sysUser);

    /**
     * 分页条件查询用户
     *
     * @param queryMap
     * @return 实体类集合
     * @date 2019-01-08
     */
    PageInfo<Object> search(Map<String, Object> queryMap);

    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return 实体类
     * @date 2019-01-08
     */
    SysUser selectByUserName(String userName);

    /**
     * 查询用户权限
     *
     * @param id
     * @return 数据集合
     * @date 2019-01-22
     */
    SysUser selectAuthorityByUserId(String id);

    String updateStateOpenByIds(List<String> ids);

    String updateStateCloseByIds(List<String> ids);

    List<SysUser> selectUserNameByRoleId(String roleId);
}
