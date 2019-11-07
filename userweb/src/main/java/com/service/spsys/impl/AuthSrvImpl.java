package com.service.spsys.impl;


import com.dao.db1.spsys.AdminDao;
import com.dao.entity.spsys.SpAdmins;
import com.dao.entity.spsys.SpCodeDecodes;
import com.dto.pojo.system.Token;
import com.dto.pojo.spsys.common.TreeData;
import com.dto.pojo.spsys.system.CustomSpAdminsBean;
import com.dto.pojo.spsys.system.UserData;
import com.service.spsys.IAuthSrv;
import com.spinfosec.system.LoginFailedInfo;
import com.spinfosec.system.MemInfo;
import com.spinfosec.system.RspCode;
import com.spinfosec.system.TMCException;
import com.utils.sys.GenUtil;
import com.utils.sys.sm2.Sm2Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName AuthSrvImpl
 * @Description: 〈鉴权服务接口实现类〉
 * @date 2018/10/9
 * All rights Reserved, Designed By SPINFO
 */
@Transactional
@Service("authSrvSp")
public class AuthSrvImpl implements IAuthSrv
{
	private static final Logger log = LoggerFactory.getLogger(AuthSrvImpl.class);


	@Autowired
	private AdminDao adminDao;

	/**
	 * 通过用户名查询用户
	 * @param userName 用户名
	 * @return
	 */
	@Override
	public List<SpAdmins> findUserByName(String userName)
	{
		return adminDao.findUserByName(userName);
	}

	/**
	 * 通过用户名查询用户信息和角色信息
	 * @param userName
	 * @return
	 */
	@Override
	public CustomSpAdminsBean findLogUserInfo(String userName)
	{
		return adminDao.findLogUserInfo(userName);
	}

	/**
	 * 通过用户ID获取信任主机IP
	 * @param userId
	 * @return
	 */
	@Override
	public List<String> getHostIpByUserId(String userId)
	{
		return adminDao.findTrustHostByUserId(userId);
	}

	/**
	 * 鉴权获取token
	 * @return
	 */
	@Override
	public Token getToken()
	{
		Token token = new Token();
		String tokenId = GenUtil.getUUID();
		token.setTokenId(tokenId);
		log.info("get a tocken success!");
		return token;
	}

	/**
	 * 初始化用户权限
	 * @param userId
	 */
	@Override
	public List<TreeData> initRoleFunctions(String userId) throws IOException
	{
		CustomSpAdminsBean customSpAdminsBean = adminDao.findLoginInfoByUserId(userId);
		if (null == customSpAdminsBean)
		{
			log.info("该用户没有对应的角色信息！");
			return null;
		}

		// 角色ID
		String roleId = customSpAdminsBean.getRoleId();
		List<SpCodeDecodes> spCodeDecodesList = adminDao.getRoleUrlFunctions(roleId);
		if (spCodeDecodesList.isEmpty())
		{
			log.info("用户获取权限失败！");
			return null;
		}

		List<TreeData> moduleTreeData = GenUtil.getModuleTree(spCodeDecodesList);

		return moduleTreeData;
	}

	@Override
	public String setLoginInfo(String loginIp, String logUserName, Integer maxLoginTimes)
	{
		String failedDes = "";
		LoginFailedInfo failedInfoNew = MemInfo.getLoginFailedMap().get(loginIp);
		// 第一次登录失败 创建登录失败对象并存储在缓存中
		if (null == failedInfoNew)
		{
			failedInfoNew = new LoginFailedInfo();
			failedInfoNew.setFailedTime(1);
			failedDes = "登录失败 " + failedInfoNew.getFailedTime() + " 次,剩余 "
					+ (maxLoginTimes - failedInfoNew.getFailedTime()) + "次机会! ";
			MemInfo.getLoginFailedMap().put(loginIp, failedInfoNew);
		}
		else
		{
			// 判断是否达到最大限制次数
			if (failedInfoNew.getFailedTime() >= maxLoginTimes)
			{
				failedInfoNew.setFailedTime(failedInfoNew.getFailedTime() + 1);
				MemInfo.getLoginFailedMap().put(loginIp, failedInfoNew);
				long leave = System.currentTimeMillis() - failedInfoNew.getLockStartDate().getTime();
				int leaveMinute = 10 - (int) (leave / (60 * 1000));
				failedDes = "登录失败次数过多,请于" + leaveMinute + "分钟后重新登录系统! ";
			} // 判断还有几次登录机会
			else
			{
				failedInfoNew.setFailedTime(failedInfoNew.getFailedTime() + 1);
				MemInfo.getLoginFailedMap().put(loginIp, failedInfoNew);
				failedDes = "登录失败 " + failedInfoNew.getFailedTime() + " 次,剩余 "
						+ (maxLoginTimes - failedInfoNew.getFailedTime()) + "次机会! ";
				if ((maxLoginTimes - failedInfoNew.getFailedTime()) == 0)
				{
					failedInfoNew.setLockStartDate(new Date());
				}
			}
		}
		// 存储登录失败时间
		failedInfoNew.setLastFailedTime(new Date());
		return failedDes;
	}

	/**
	 * 获取当前用户的具体信息
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public CustomSpAdminsBean getCurruserUserById(String userId) throws Exception
	{

		return adminDao.getCurruserUserById(userId);
	}

	/**
	 * 修改当前用户密码
	 * @param data
	 * @param req
	 */
	@Override
	public void updateCurrentUser(UserData data, HttpServletRequest req)
	{
		if (null != data)
		{
			SpAdmins user = adminDao.findSpAdminById(data.getId());
			if (null == user)
			{
				throw new TMCException(RspCode.OBJECE_NOT_EXIST);
			}
			// 更新密码
			if (StringUtils.isNotEmpty(data.getOldPwd()) && StringUtils.isNotEmpty(data.getNewPwd()))
			{
				// 旧密码解密
				String decryptOldPass = new String(Sm2Utils.decrypt(Sm2Utils.PRIVATE_KEY, data.getOldPwd()));
				// 数据库密码解密
				String decryptUsePass = new String(Sm2Utils.decrypt(Sm2Utils.PRIVATE_KEY, user.getPassword()));
				if (decryptOldPass.equals(decryptUsePass))
				{
					user.setPassword(data.getNewPwd());
					user.setPasswordChangeFlag(new Double("1"));
					user.setPasswordModifyDate(new Date());
					adminDao.updateSpAdmin(user);
				}
				else
				{
					throw new TMCException(RspCode.OLD_PASSWORD_ERROR);
				}
			}
		}
	}
}
