package com.service.spsys;


import com.dto.pojo.spsys.common.CodeRsp;
import com.dto.pojo.spsys.system.ADConfig;
import com.dto.pojo.spsys.system.EmailConnectData;
import com.dto.pojo.spsys.system.LdapData;

import javax.naming.directory.DirContext;
import java.util.List;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName ITestConnectSrv
 * @Description: 〈测试连接业务接口〉
 * @date 2018/10/30
 * All rights Reserved, Designed By SPINFO
 */
public interface ITestConnectSrv
{
	/**
	 * 测试连接邮箱
	 * @param connectData
	 * @return
	 */
	CodeRsp testEmail(EmailConnectData connectData);

	/**
	 * 测试连接Exchange
	 * @param config
	 * @return
	 * @throws Exception
	 */
	DirContext connectExchange(ADConfig config) throws Exception;

	/**
	 * Exchange连接获取信息
	 * @param ou
	 * @return
	 * @throws Exception
	 */
	List<LdapData> getLdapUsers(String ou) throws Exception;


	/**
	 * 组装Exchange认证配置信息
	 * @return
	 */
	ADConfig getLdapConfig();
}
