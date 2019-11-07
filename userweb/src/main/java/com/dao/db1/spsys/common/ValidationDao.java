package com.dao.db1.spsys.common;


import com.dto.pojo.spsys.common.ValidationBean;

import java.util.List;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName ValidationDao
 * @Description: 〈后台校验持久层〉
 * @date 2018/10/24
 * All rights Reserved, Designed By SPINFO
 */
public interface ValidationDao
{
	/**
	 * 重名校验
	 * @param checkData
	 * @return
	 */
	List<Object> duplicate(ValidationBean checkData);
}
