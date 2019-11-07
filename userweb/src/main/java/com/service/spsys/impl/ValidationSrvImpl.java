package com.service.spsys.impl;


import com.dao.db1.spsys.common.ValidationDao;
import com.dto.pojo.spsys.common.ValidationBean;
import com.service.spsys.IValidationSrv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName ValidationSrvImpl
 * @Description: 〈后台校验实现类〉
 * @date 2018/10/24
 * All rights Reserved, Designed By SPINFO
 */
@Service("validationSrv")
public class ValidationSrvImpl implements IValidationSrv
{
	@Autowired
	private ValidationDao validationDao;

	/**
	 * 重名校验
	 * @param checkData
	 * @return
	 */
	@Override
	public boolean checkDuplicateData(ValidationBean checkData)
	{
		boolean reslut = true;
		List<Object> list = validationDao.duplicate(checkData);
		if (!list.isEmpty())
		{
			reslut = false;
		}
		return reslut;
	}
}
