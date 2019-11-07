package com.service.spsys.impl;


import com.dao.entity.spsys.SpConfigProperties;
import com.dao.db1.spsys.tactic.BasicSettingDao;
import com.service.spsys.IBasicSettingSrv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName BasicSettingSrvImpl
 * @Description: 〈基本设置业务实现类〉
 * @date 2018/10/25
 * All rights Reserved, Designed By SPINFO
 */
@Transactional
@Service("basicSettingSrv")
public class BasicSettingSrvImpl implements IBasicSettingSrv
{
	private static final Logger log = LoggerFactory.getLogger(BasicSettingSrvImpl.class);

	@Autowired
	private BasicSettingDao basicSettingDao;

	/**
	 * 根据基本设置类型名获取设置参数
	 * @param groupName
	 * @return
	 */
	@Override
	public List<SpConfigProperties> getSettingByGroupName(String groupName)
	{
		return basicSettingDao.getConfigByGroupName(groupName);
	}

	/**
	 * 保存基本设置参数
	 * @param configPropertiesList
	 */
	@Override
	public void saveConfigProperties(List<SpConfigProperties> configPropertiesList)
	{
		for (SpConfigProperties configProperties : configPropertiesList)
		{
			basicSettingDao.saveConfigProperties(configProperties);
		}

	}

}
