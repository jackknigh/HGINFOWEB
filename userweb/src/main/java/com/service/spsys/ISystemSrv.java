package com.service.spsys;


import com.dao.entity.spsys.SpAdmins;
import com.dao.entity.spsys.SpRoles;
import com.dao.entity.spsys.SpSystemOperateLogInfo;
import com.dto.pojo.spsys.common.CodeRsp;
import com.dto.pojo.spsys.common.TreeData;
import com.dto.pojo.spsys.system.AdminReq;
import com.dto.pojo.spsys.system.CustomSpAdminsBean;
import com.dto.pojo.spsys.system.RoleReq;
import com.github.pagehelper.PageInfo;
import com.spinfosec.system.RspCode;
import org.apache.commons.mail.EmailException;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName ISystemSrv
 * @Description: 〈系统业务处理接口〉
 * @date 2018/10/11
 * All rights Reserved, Designed By SPINFO
 */
public interface ISystemSrv
{
	/**
	 * 分页条件查询用户信息
	 * @param queryMap
	 * @return
	 */
	PageInfo<CustomSpAdminsBean> querySpAdminsByPage(Map<String, Object> queryMap);

	/**
	 * 根据ID查询用户详细信息
	 * @param id
	 * @return
	 */
	AdminReq getAdminReqById(String id);

	/**
	 * 根据用户ID查询用户
	 * @param userId
	 * @return
	 */
	SpAdmins getAdminById(String userId);

	/**
	 * 保存用户信息
	 * @param data
	 */
	void saveSpAdmin(AdminReq data);

	/**
	 * 更新用户信息
	 * @param data
	 */
	void updateSpAdmin(AdminReq data);

	/**
	 * 删除用户信息
	 * @return
	 */
	RspCode deleteSpAdmins(String[] ids);

	/**
	 * 分页条件查询角色
	 * @param queryMap
	 * @return
	 */
	PageInfo<SpRoles> querySpRolesByPage(Map<String, Object> queryMap);

	/**
	 * 根据角色ID获取对应权限
	 * @return
	 */
	List<TreeData> getMenuByRoleId(String roleId);

	/**
	 * 根据角色ID获取该角色的拥有权限和角色信息
	 * @param roleId
	 * @return
	 */
	RoleReq getRoleDataById(String roleId);

	/**
	 * 获取所有角色信息
	 * @return
	 */
	List<SpRoles> getAllSpRole();

	/**
	 * 保存角色信息
	 *
	 * @return
	 */
	public void saveSpRoles(RoleReq data, String userId);

	/**
	 * 修改角色信息
	 *
	 * @return
	 */
	void updateSpRoles(RoleReq data);

	/**
	 * 删除角色信息
	 *
	 * @return
	 */
	boolean deleteSpRoles(String[] ids);

	/**
	 * 分页查询系统操作日志
	 * @param queryMap
	 * @return
	 */
	PageInfo<SpSystemOperateLogInfo> queryOperateLogByPage(Map<String, Object> queryMap);

	/**
	 * 忘记密码
	 * @param username
	 * @param email
	 */
	void forgetPassword(String username, String email) throws EmailException;

	/**
	 * 首次/密码过期修改密码
	 * @param oldPwd
	 * @param newPwd
	 * @param currentUserId
	 * @return
	 */
	CodeRsp updatePassword(String oldPwd, String newPwd, String currentUserId);

	/**
	 * 记录系统操作日志
	 * @param operateLogInfo 需要记录的日志
	 */
	void saveOperateLog(SpSystemOperateLogInfo operateLogInfo);

	/**
	 * 修改用户使用状态 0激活  1注销  2 休眠
	 * @param id  用户ID
	 * @param state  使用状态
	 */
	void updateAdminState(String id, String state);

}
