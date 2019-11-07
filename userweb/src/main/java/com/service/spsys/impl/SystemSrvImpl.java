package com.service.spsys.impl;

import com.dao.db1.spsys.AdminDao;
import com.dao.db1.spsys.RoleDao;
import com.dao.db1.spsys.SecPasswordPolicyDao;
import com.dao.db1.spsys.SysOperateLogDao;
import com.dao.db1.spsys.tactic.BasicSettingDao;
import com.dao.entity.spsys.*;
import com.dto.constants.Constants;
import com.dto.pojo.spsys.common.CodeRsp;
import com.dto.pojo.spsys.common.TreeData;
import com.dto.pojo.spsys.system.AdminReq;
import com.dto.pojo.spsys.system.CustomSpAdminsBean;
import com.dto.pojo.spsys.system.RoleReq;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.spsys.ISystemSrv;
import com.spinfosec.system.RspCode;
import com.spinfosec.system.TMCException;
import com.utils.sys.EmailUtil;
import com.utils.sys.EncUtil;
import com.utils.sys.GenUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName SystemImpl
 * @Description: 〈系统业务处理接口实现类〉
 * @date 2018/10/11
 * All rights Reserved, Designed By SPINFO
 */

@Transactional
@Service("systemSrv")
public class SystemSrvImpl implements ISystemSrv
{
	private static final Logger log = LoggerFactory.getLogger(SystemSrvImpl.class);

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private SecPasswordPolicyDao secPasswordPolicyDao;

	@Autowired
	private BasicSettingDao basicSettingDao;

	@Autowired
	private SysOperateLogDao sysOperateLogDao;

	/**
	 * 分页条件查询用户信息
	 * @param queryMap
	 * @return
	 */
	@Override
	public PageInfo<CustomSpAdminsBean> querySpAdminsByPage(Map<String, Object> queryMap)
	{
		Integer pageNum = Integer.valueOf(queryMap.get("currentPage").toString());
		Integer pageSize = Integer.valueOf(queryMap.get("pageSize").toString());
		PageHelper.startPage(pageNum, pageSize);
		List<CustomSpAdminsBean> customSpAdminsBeanList = adminDao.querySpAdmins(queryMap);
		PageInfo<CustomSpAdminsBean> pageList = new PageInfo<>(customSpAdminsBeanList);
		return pageList;
	}

	/**
	 * 根据ID查询用户详细信息
	 * @param id
	 * @return
	 */
	@Override
	public AdminReq getAdminReqById(String id)
	{
		AdminReq adminReq = new AdminReq();
		SpAdmins adminById = adminDao.findSpAdminById(id);
		adminReq.setSpAdmins(adminById);
		// 通过用户ID查询角色ID
		String roleIdByUserId = adminDao.findRoleIdByUserId(adminById.getId());
		adminReq.setRoleId(roleIdByUserId);
		// 通过用户ID查询组织信息
		String orgIdByUserId = adminDao.findOrgIdByUserId(adminById.getId());
		adminReq.setOrgId(orgIdByUserId);
		// 通过用户ID查询信任主机集合
		List<String> trustHostByUserId = adminDao.findTrustHostByUserId(adminById.getId());
		adminReq.setHostItems(trustHostByUserId);
		return adminReq;
	}

	/**
	 * 根据用户ID查询用户
	 * @param userId
	 * @return
	 */
	@Override
	public SpAdmins getAdminById(String userId)
	{
		return adminDao.findSpAdminById(userId);
	}

	/**
	 * 保存用户信息
	 * @param data
	 */
	@Override
	public void saveSpAdmin(AdminReq data)
	{
		SpAdmins spAdmins = data.getSpAdmins();
		String adminId = GenUtil.getUUID();
		spAdmins.setId(adminId);
		spAdmins.setDefinitionType("C_USER_DEFIN");
		spAdmins.setRegistrationDate(new Date());

		// 保存用户信任主机关系
		List<String> secHostIds = data.getHostItems();
		if (null != secHostIds && !secHostIds.isEmpty())
		{
			for (String hostId : secHostIds)
			{
				String relationId = GenUtil.getUUID();
				SpAdminTrustHost adminTrustHost = new SpAdminTrustHost();
				adminTrustHost.setId(relationId);
				adminTrustHost.setAdminId(adminId);
				adminTrustHost.setHostId(hostId);
				adminDao.saveAdminTrustHost(adminTrustHost);
			}
		}
		// 保存用户和角色的关系
		SpRoleAdminRelation roleAdminRelation = new SpRoleAdminRelation();
		roleAdminRelation.setId(GenUtil.getUUID());
		roleAdminRelation.setAdminId(spAdmins.getId());
		roleAdminRelation.setRoleId(data.getRoleId());
		adminDao.saveUserAndRole(roleAdminRelation);

		// 保存用户和组织关系
		SpOrgAdminRelation orgAdminRelation = new SpOrgAdminRelation();
		orgAdminRelation.setId(GenUtil.getUUID());
		orgAdminRelation.setAdminId(spAdmins.getId());
		orgAdminRelation.setOrgId(data.getOrgId());
		adminDao.saveUserAndOrg(orgAdminRelation);

		// 保存用户
		adminDao.saveSpAdmin(spAdmins);
	}

	/**
	 * 更新用户信息
	 * @param data
	 */
	@Override
	public void updateSpAdmin(AdminReq data)
	{
		SpAdmins tempSpAdminsBean = adminDao.findSpAdminById(data.getSpAdmins().getId());

		data.getSpAdmins().setRegistrationDate(tempSpAdminsBean.getRegistrationDate());
		data.getSpAdmins().setPasswordChangeFlag(tempSpAdminsBean.getPasswordChangeFlag());
		data.getSpAdmins().setCreatedBy(tempSpAdminsBean.getCreatedBy());
		data.getSpAdmins().setDefinitionType(tempSpAdminsBean.getDefinitionType());
		// 如果更新密码，则同时更新密码修改时间；否则密码修改时间保持原状
		if (StringUtils.isNotEmpty(data.getSpAdmins().getPassword()))
		{
			data.getSpAdmins().setPasswordModifyDate(new Date());
		}
		else
		{
			data.getSpAdmins().setPasswordModifyDate(tempSpAdminsBean.getPasswordModifyDate());
		}
		// 删除对应的用户和信任主机关系
		adminDao.deldeteAdminTrustHostByAdminId(data.getSpAdmins().getId());
		// 重新添加用户和信息主机关系
		List<String> secHostIds = data.getHostItems();
		String adminId = data.getSpAdmins().getId();
		if (null != secHostIds && !secHostIds.isEmpty())
		{
			for (String hostId : secHostIds)
			{
				String relationId = GenUtil.getUUID();
				SpAdminTrustHost adminTrustHost = new SpAdminTrustHost();
				adminTrustHost.setId(relationId);
				adminTrustHost.setAdminId(adminId);
				adminTrustHost.setHostId(hostId);
				adminDao.saveAdminTrustHost(adminTrustHost);
			}
		}

		// 先删除用户和角色关系
		adminDao.deleteUserAndRole(adminId);
		// 重新保存用户和角色关系
		SpRoleAdminRelation spRoleAdminRelation = new SpRoleAdminRelation();
		spRoleAdminRelation.setId(GenUtil.getUUID());
		spRoleAdminRelation.setAdminId(data.getSpAdmins().getId());
		spRoleAdminRelation.setRoleId(data.getRoleId());
		adminDao.saveUserAndRole(spRoleAdminRelation);

		// 先删除用户和组织关系
		adminDao.deleteUserAndOrg(adminId);
		// 重新保存用户和组织关系
		SpOrgAdminRelation orgAdminRelation = new SpOrgAdminRelation();
		orgAdminRelation.setId(GenUtil.getUUID());
		orgAdminRelation.setAdminId(adminId);
		orgAdminRelation.setOrgId(data.getOrgId());
		adminDao.saveUserAndOrg(orgAdminRelation);

		// 更新用户
		adminDao.updateSpAdmin(data.getSpAdmins());

	}

	/**
	 * 删除用户信息
	 * @return
	 */
	@Override
	public RspCode deleteSpAdmins(String[] ids)
	{
		RspCode rspCode = RspCode.SUCCESS;
		for (String id : ids)
		{
			// 预制用户不能删除
			SpAdmins spAdminById = adminDao.findSpAdminById(id);
			if (Constants.C_PRE_DEFINE.equals(spAdminById.getDefinitionType()))
			{
				rspCode = RspCode.USER_PRE_DEFINE_NO_DELETING;
				return rspCode;
			}
			// 删除用户和信息主机关系
			adminDao.deldeteAdminTrustHostByAdminId(id);
			// 删除用户和角色关系表
			adminDao.deleteUserAndRole(id);
			// 删除用户和组织关系
			adminDao.deleteUserAndOrg(id);
			// 删除用户信息
			adminDao.deleteSpAdminById(id);
		}
		return rspCode;
	}

	/**
	 * 分页条件查询角色
	 * @param queryMap
	 * @return
	 */
	@Override
	public PageInfo<SpRoles> querySpRolesByPage(Map<String, Object> queryMap)
	{
		Integer pageNum = Integer.valueOf(queryMap.get("currentPage").toString());
		Integer pageSize = Integer.valueOf(queryMap.get("pageSize").toString());
		PageHelper.startPage(pageNum, pageSize);
		List<SpRoles> rolesList = roleDao.querySpRolesByPage(queryMap);
		PageInfo<SpRoles> pageList = new PageInfo<>(rolesList);
		return pageList;
	}

	/**
	 * 根据角色ID获取对应权限
	 * @return
	 */
	@Override
	public List<TreeData> getMenuByRoleId(String roleId)
	{
		List<SpCodeDecodes> allPermission = roleDao.getPermissionByRole(roleId);
		List<TreeData> treeData = GenUtil.getModuleTree(allPermission);
		return treeData;
	}

	/**
	 * 根据角色ID获取该角色的拥有权限和角色信息
	 * @param roleId
	 * @return
	 */
	@Override
	public RoleReq getRoleDataById(String roleId)
	{
		// 根据ID查询角色信息
		RoleReq roleReq = new RoleReq();
		SpRoles roleById = roleDao.getRoleById(roleId);
		roleReq.setSpRoles(roleById);

		// 根据角色ID获取所拥有的模块
		List<SpRoleModulePermissions> permissionsList = roleDao.getModulePermissions(roleId);
		roleReq.setSpRoleModulePermissions(permissionsList);
		return roleReq;
	}

	/**
	 * 获取所有角色信息
	 * @return
	 */
	@Override
	public List<SpRoles> getAllSpRole()
	{
		return roleDao.getAllSpRole();
	}

	/**
	 * 保存角色信息
	 *
	 * @return
	 */
	@Override
	public void saveSpRoles(RoleReq data, String userId)
	{
		SpRoles role = data.getSpRoles();
		String roleId = GenUtil.getUUID();
		role.setId(roleId);
		role.setCreateDate(new Date());
		role.setCreatedBy(userId);
		// 保存角色
		roleDao.savaSpRole(role);
		// 保存权限
		List<SpRoleModulePermissions> roleModuleList = data.getSpRoleModulePermissions();
		for (SpRoleModulePermissions spRoleModulePermissions : roleModuleList)
		{
			String id = GenUtil.getUUID();
			spRoleModulePermissions.setId(id);
			spRoleModulePermissions.setRoleId(roleId);
			roleDao.saveModulePermissions(spRoleModulePermissions);
		}
	}

	/**
	 * 修改角色信息
	 *
	 * @return
	 */
	@Override
	public void updateSpRoles(RoleReq data)
	{
		String roleId = data.getSpRoles().getId();
		if (StringUtils.isEmpty(roleId))
		{
			log.error("更新角色失败,失败原因 : 根据角色ID查询不到该角色!");
			throw new TMCException(RspCode.PARAMERTER_ERROR);
		}
		SpRoles role = roleDao.getRoleById(roleId);
		role.setName(data.getSpRoles().getName());
		role.setDescription(data.getSpRoles().getDescription());
		role.setCreateDate(new Date());
		// 更新角色信息
		roleDao.updateSpRole(role);
		// 非预置角色将原先的权限清空
		if (StringUtils.isNotEmpty(role.getDefinitionType()) && !Constants.C_PRE_DEFINE.equals(role.getDefinitionType()))
		{
			// 先删除原来的权限信息
			roleDao.deleteModulePermissionsByRoleId(roleId);
			// 重新保存权限
			List<SpRoleModulePermissions> roleModuleList = data.getSpRoleModulePermissions();
			for (SpRoleModulePermissions spRoleModulePermissions : roleModuleList)
			{
				String id = GenUtil.getUUID();
				spRoleModulePermissions.setId(id);
				spRoleModulePermissions.setRoleId(roleId);
				roleDao.saveModulePermissions(spRoleModulePermissions);
			}
		}
	}

	/**
	 * 删除角色信息
	 *
	 * @return
	 */
	@Override
	public boolean deleteSpRoles(String[] ids)
	{
		boolean result = true;
		for (String id : ids)
		{
			SpRoles role = roleDao.getRoleById(id);
			if (null != role && "SYSTEM".equals(role.getRoleType()))
			{
				result = false;
				continue;
			}

			// 删除该角色下对应的权限信息
			roleDao.deleteModulePermissionsByRoleId(role.getId());
			// 删除用户和角色关系表
			roleDao.deleteUserAndRole(role.getId());
			// 删除角色信息
			roleDao.deleteRoleById(id);
		}
		return result;
	}

	/**
	 * 分页查询系统操作日志
	 * @param queryMap
	 * @return
	 */
	@Override
	public PageInfo<SpSystemOperateLogInfo> queryOperateLogByPage(Map<String, Object> queryMap)
	{
		Integer pageNum = Integer.valueOf(queryMap.get("currentPage").toString());
		Integer pageSize = Integer.valueOf(queryMap.get("pageSize").toString());
		PageHelper.startPage(pageNum, pageSize);
		List<SpSystemOperateLogInfo> systemOperateLogInfoList = sysOperateLogDao.queryOperateLog(queryMap);
		PageInfo<SpSystemOperateLogInfo> pageList = new PageInfo<>(systemOperateLogInfoList);
		return pageList;
	}

	/**
	 * 忘记密码
	 * @param username
	 * @param email
	 */
	@Override
	public void forgetPassword(String username, String email) throws EmailException
	{
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("email", email);
		queryMap.put("userName", username);
		List<SpAdmins> users = adminDao.findUserByEmailAndName(queryMap);
		if (null != users && users.size() > 0)
		{
			SpAdmins user = users.get(0);
			// 获取密码策略和复杂度
			List<SpSecPasswordComplexityItem> complexityItem = secPasswordPolicyDao.getComplexityItem();
			SpSecPasswordPolicy secPasswordPolicy = secPasswordPolicyDao.getSpSecPasswordPolicy();
			int passwordLengthMin = Integer.parseInt(secPasswordPolicy.getPasswordLengthMin());
			int passwordLengthMax = Integer.parseInt(secPasswordPolicy.getPasswordLengthMax());
			Random random = new Random();
			// 得到一个符合密码策略中设置的随机密码长度
			int randomNum = (random.nextInt(passwordLengthMax - passwordLengthMin + 1)) + passwordLengthMin;
			// 得到密码复杂度
			List<String> spSecPasswordComplexityItemIdList = new ArrayList<String>();
			for (SpSecPasswordComplexityItem spSecPasswordComplexityItem : complexityItem)
			{
				if ("1".equals(spSecPasswordComplexityItem.getIsEnable()))
				{
					spSecPasswordComplexityItemIdList.add(spSecPasswordComplexityItem.getId());
				}
			}
			// 生成随机密码
			String initPwd = EncUtil.getRandomPwdBySecPasswordPolicy(randomNum, spSecPasswordComplexityItemIdList);

			// 发送邮箱
			EmailUtil.sendTextMail(user.getEmail(), null, "hi " + user.getName() + ",\n\t您在加密文件检查系统的账号为："
					+ user.getUsername() + ",新密码为：" + initPwd + "\n\n\t登录系统后，可在\"个人中心\"修改您的密码。", "加密文件检查系统-密码重置");

			// 修改用户状态
			user.setPassword(initPwd);
			user.setPasswordModifyDate(new Date());
			adminDao.updateSpAdmin(user);

		}
		else
		{
			throw new TMCException(RspCode.ACCOUNT_NOT_EXIST);
		}
	}

	/**
	 * 首次/密码过期修改密码
	 * @param oldPwd
	 * @param newPwd
	 * @param currentUserId
	 * @return
	 */
	@Override
	public CodeRsp updatePassword(String oldPwd, String newPwd, String currentUserId)
	{
		CodeRsp codeRsp = new CodeRsp(RspCode.SUCCESS);
		SpAdmins adminById = adminDao.findSpAdminById(currentUserId);
		if (!oldPwd.equals(adminById.getPassword()))
		{
			codeRsp = new CodeRsp(RspCode.OLD_PASSWORD_ERROR);
		}
		else
		{
			adminById.setPasswordChangeFlag(Double.valueOf("1"));
			adminById.setPasswordModifyDate(new Date());
			adminById.setPassword(newPwd);
			adminDao.updateSpAdmin(adminById);
		}
		return codeRsp;
	}

	/**
	 * 记录系统操作日志
	 * @param operateLogInfo 需要记录的日志
	 */
	@Override
	public void saveOperateLog(SpSystemOperateLogInfo operateLogInfo)
	{
		operateLogInfo.setId(GenUtil.getUUID());
		sysOperateLogDao.saveOperateLog(operateLogInfo);
	}

	/**
	 * 修改用户使用状态 0激活  1注销  2 休眠
	 * @param id  用户ID
	 * @param state  使用状态
	 */
	@Override
	public void updateAdminState(String id, String state)
	{
		adminDao.updateAdminState(id, state);
	}
}
