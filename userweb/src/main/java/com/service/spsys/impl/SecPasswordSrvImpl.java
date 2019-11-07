package com.service.spsys.impl;


import com.dao.db1.spsys.AdminDao;
import com.dao.db1.spsys.SecPasswordPolicyDao;
import com.dao.entity.spsys.SpAdmins;
import com.dao.entity.spsys.SpSecPasswordComplexityItem;
import com.dao.entity.spsys.SpSecPasswordPolicy;
import com.dto.enums.SessionItem;
import com.dto.pojo.spsys.system.SecPasswordPolicyFormData;
import com.service.spsys.ISecPasswordSrv;
import com.spinfosec.system.MemInfo;
import com.spinfosec.system.RspCode;
import com.spinfosec.system.TMCException;
import com.utils.sys.sm2.Sm2Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName SecPasswordSrvImpl
 * @Description: 〈密码安全策略实现类〉
 * @date 2018/10/12
 * All rights Reserved, Designed By SPINFO
 */
@Transactional
@Service("secPasswordSrv")
public class SecPasswordSrvImpl implements ISecPasswordSrv
{
	@Autowired
	private SecPasswordPolicyDao secPasswordPolicyDao;

	@Autowired
	private AdminDao adminDao;

	/**
	 * 获取密码安全策略
	 * @return
	 */
	@Override
	public SpSecPasswordPolicy getSpSecPasswordPolicy()
	{
		return secPasswordPolicyDao.getSpSecPasswordPolicy();
	}

	/**
	 * 获取密码复杂度选项
	 * @return List<SpSecPasswordComplexityItem>
	 */
	@Override
	public List<SpSecPasswordComplexityItem> getComplexityItem()
	{
		return secPasswordPolicyDao.getComplexityItem();
	}

	/**
	 * 保存密码安全策略
	 * @param req
	 * @param data
	 */
	@Override
	public void saveSecPasswordPolicy(HttpServletRequest req, SecPasswordPolicyFormData data)
	{
		List<String> complexityItem = data.getSecPasswordComplexityItem();
		if (null != complexityItem && !complexityItem.isEmpty())
		{
			// 先重置密码复杂度
			secPasswordPolicyDao.resetSpSecPasswordComplexityItem();
			for (String name : complexityItem)
			{
				// 再根据设置更新密码复杂度
				secPasswordPolicyDao.updateSpSecPasswordComplexityItem(name);
			}
		}
		// 用系统初始化的ID去更新而不是新增
		SpSecPasswordPolicy querySpSecPasswordPolicy = secPasswordPolicyDao.getSpSecPasswordPolicy();
		data.getSecPasswordPolicy().setId(querySpSecPasswordPolicy.getId());
		secPasswordPolicyDao.saveSecPasswordPolicy(data.getSecPasswordPolicy());

		// 将密码安全策略内容存储在服务器内存中
		MemInfo.getSecPasswordPolicyInfo().put(SessionItem.passwordValidity.name(),
				data.getSecPasswordPolicy().getIsRepeatLogin());
		MemInfo.getSecPasswordPolicyInfo().put(SessionItem.passwordLengthMin.name(),
				data.getSecPasswordPolicy().getIsRepeatLogin());
		MemInfo.getSecPasswordPolicyInfo().put(SessionItem.passwordLengthMax.name(),
				data.getSecPasswordPolicy().getIsRepeatLogin());
		MemInfo.getSecPasswordPolicyInfo().put(SessionItem.maxLoginTimes.name(),
				data.getSecPasswordPolicy().getIsRepeatLogin());
		MemInfo.getSecPasswordPolicyInfo().put(SessionItem.isModifyPasswordFirst.name(),
				data.getSecPasswordPolicy().getIsRepeatLogin());
		MemInfo.getSecPasswordPolicyInfo().put(SessionItem.ukeyEnable.name(),
				data.getSecPasswordPolicy().getIsRepeatLogin());
		MemInfo.getSecPasswordPolicyInfo().put(SessionItem.isRepeatLogin.name(),
				data.getSecPasswordPolicy().getIsRepeatLogin());
		MemInfo.getSecPasswordPolicyInfo().put(SessionItem.sechostEnable.name(),
				data.getSecPasswordPolicy().getIsRepeatLogin());
	}

	/**
	 * 验证密码有效性
	 * @param password
	 * @param name 禁止用户口令与用户名相同或包含用户名
	 * @return String
	 */
	@Override
	public String passwordValid(String password, String name)
	{
		StringBuilder errors = new StringBuilder();
		if (StringUtils.isEmpty(password))
		{
			throw new TMCException(RspCode.OBJECE_NOT_EXIST);
		}

		// 解密后密码
		password = new String(Sm2Utils.decrypt(Sm2Utils.PRIVATE_KEY, password));
		// 查看该用户的密码是否和姓名相同
		List<SpAdmins> userByName = adminDao.getUserByName(name);
		// 获取密码策略和复杂度
		SpSecPasswordPolicy secPasswordPolicy = getSpSecPasswordPolicy();
		// 获取密码复杂度
		List<SpSecPasswordComplexityItem> secPasswordComplexityItem = getComplexityItem();
		// 允许密码最小位数
		String passwordLengthMin = secPasswordPolicy.getPasswordLengthMin();
		// 允许密码最大位数
		String passwordLengthMax = secPasswordPolicy.getPasswordLengthMax();
		if (StringUtils.isEmpty(name))
		{
			// 新建用户时可能用户先输入密码未输入用户名而获取不到name无法校验
			errors.append("请先输入用户名");
		}
		else if (password.length() < Integer.parseInt(passwordLengthMin)
				|| password.length() > Integer.parseInt(passwordLengthMax))
		{
			errors.append("密码长度为" + passwordLengthMin + "-" + passwordLengthMax + "之间");
		}
		else if (password.equals(name) || password.contains(name))
		{
			// 禁止用户口令与用户名相同或包含用户名；
			errors.append("禁止用户口令与用户名相同或包含用户名");
		}
		else if (StringUtils.isNotEmpty(password) && userByName != null && userByName.size() != 0
				&& password.equals(new String(Sm2Utils.decrypt(Sm2Utils.PRIVATE_KEY, userByName.get(0).getPassword()))))
		{
			// 禁止用户口令与用户名相同或包含用户名；
			errors.append("修改后的密码不能与上次密码相同！");
		}
		else
		{
			for (SpSecPasswordComplexityItem spSecPasswordComplexityItem : secPasswordComplexityItem)
			{
				if ("1".equals(spSecPasswordComplexityItem.getIsEnable()))
				{
					Pattern pattern = Pattern.compile(spSecPasswordComplexityItem.getValue());
					Matcher matcher = pattern.matcher(password);
					if (!matcher.find())
					{
						if (errors.length() == 0)
						{
							errors.append(spSecPasswordComplexityItem.getDescription());
						}
						else
						{
							errors.append("、").append(spSecPasswordComplexityItem.getDescription());
						}
					}
				}
			}
			if (errors.length() > 0)
			{
				errors.insert(0, "密码必须包含");
			}
		}
		return errors.toString();
	}
}
