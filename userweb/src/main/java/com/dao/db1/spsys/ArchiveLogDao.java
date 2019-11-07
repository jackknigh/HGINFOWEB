package com.dao.db1.spsys;


import com.dao.entity.spsys.SpEventArchiveLog;
import com.dao.entity.spsys.SpSystemOperateArchiveLog;
import com.dto.pojo.spsys.system.EventArchiveLogRsp;
import com.dto.pojo.spsys.system.SysOperateArchiveLogRsp;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName EventArchiveLogDao
 * @Description: 〈安全日志归档持久层〉
 * @date 2018/10/17
 * All rights Reserved, Designed By SPINFO
 */
public interface ArchiveLogDao
{
	/**
	 * 根据条件查询安全日志归档信息
	 * @param queryMap
	 * @return
	 */
	List<EventArchiveLogRsp> queryEventByPage(Map<String, Object> queryMap);

	/**
	 * 保存安全归档日志
	 * @param archiveLog
	 */
	void saveEventArchiveLog(SpEventArchiveLog archiveLog);

	/**
	 * 更新安全归档记录
	 * @param archiveLog
	 */
	void updateEventArchiveLog(SpEventArchiveLog archiveLog);

	/**
	 * 更新系统操作日志当归记录
	 * @param operateArchiveLog
	 */
	void updateOperateArchiveLog(SpSystemOperateArchiveLog operateArchiveLog);

	/**
	 * 根据ID获取安全日志归档记录
	 * @param id
	 * @return
	 */
	SpEventArchiveLog getEventAcrhiveLogById(String id);

	/**
	 * 据ID获取系统操作日志归档记录
	 * @param id
	 * @return
	 */
	SpSystemOperateArchiveLog getOperateArchiveLogById(String id);

	/**
	 * 根据ID删除安全日志记录
	 * @param id
	 */
	void deleteEventArchiveLogById(String id);

	/**
	 * 根据ID删除系统操作日志归档记录
	 * @param id
	 */
	void deleteOperateArchiveLog(String id);

	/**
	 * 根据条件查询系统操作日志归档记录
	 * @param queryMap
	 * @return
	 */
	List<SysOperateArchiveLogRsp> querySysByPage(Map<String, Object> queryMap);

	/**
	 * 保存操作日志归档记录
	 * @param operateArchiveLog
	 */
	void saveSystemOperateArchiveLog(SpSystemOperateArchiveLog operateArchiveLog);

}
