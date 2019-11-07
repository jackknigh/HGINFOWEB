package com.service.spsys;

import com.alibaba.fastjson.JSONObject;
import com.dto.pojo.spsys.common.CodeRsp;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName ILicenseSrv
 * @Description: 〈License许可管理业务处理接口〉
 * @date 2018/11/8
 * All rights Reserved, Designed By SPINFO
 */
public interface ILicenseSrv
{
	/**
	* 获取当前License
	* @return
	*/
	JSONObject getCurrentLicenseInfo();

	/**
	 * 校验License
	 * @return
	 */
	CodeRsp installAndVerifyLicense();

	/**
	 * 校验许可
	 * @return
	 * @throws Exception
	 */
	boolean checkLicense() throws Exception;

}
