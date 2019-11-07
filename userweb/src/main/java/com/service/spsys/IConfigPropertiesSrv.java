package com.service.spsys;


import com.dao.entity.spsys.SpConfigProperties;

import java.util.List;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName IBasicSettingSrv
 * @Description: 〈基本设置业务接口〉
 * @date 2018/10/25
 * All rights Reserved, Designed By SPINFO
 */
public interface IConfigPropertiesSrv
{
	/**
	 * 根据基本设置类型名获取设置参数
	 * @param groupName
	 * @return
	 */
	List<SpConfigProperties> getSettingByGroupName(String groupName);

	/**
	 * 保存配置参数
	 * @param configPropertiesList
	 */
	void saveConfigProperties(List<SpConfigProperties> configPropertiesList) throws Exception;

}
