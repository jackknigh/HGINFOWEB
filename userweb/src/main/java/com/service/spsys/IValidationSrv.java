package com.service.spsys;


import com.dto.pojo.spsys.common.ValidationBean;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName IValidationSrv
 * @Description: 〈后台校验接口〉
 * @date 2018/10/24
 * All rights Reserved, Designed By SPINFO
 */
public interface IValidationSrv
{
	/**
	 * 重名校验
	 * @param checkData
	 * @return
	 */
	boolean checkDuplicateData(ValidationBean checkData);
}
