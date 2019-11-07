package com.dao.db1.system;

import com.dao.entity.system.SysUser;
import com.dto.pojo.spsys.LoginUserBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(@Param("list") List<String> ids);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated
     */
    int insert(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated
     */
    SysUser selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated
     */
    List<SysUser> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysUser record);
    //************************************************自定义开始***************************************************

    /**
     * 保存用户
     *
     * @param sysUser
     * @return 1成功
     * @date 2019-01-8
     */
    int save(SysUser sysUser);

    /**
     * 分页查询用户
     *
     * @param queryMap
     * @return 实体类集合
     * @date 2019-01-08
     */
    List<SysUser> search(Map<String, Object> queryMap);

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

    /**
     * 获取用户信息
     *
     * @param userName
     * @param password
     * @return 数据集合
     * @date 2019-01-22
     */
    public LoginUserBean getLoginUser(@Param("userName") String userName, @Param("password") String password);

    LoginUserBean getLoginUserBySfzh(String sfzh);

    int updateStateByIds(@Param("ids") List<String> ids, @Param("state") Integer state);

    int checkRoleExist(String roleId);
}