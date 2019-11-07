package com.dao.db1.spsys;


import com.dao.entity.spsys.SpTask;
import com.dto.pojo.spsys.system.TaskCenterRsp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName TaskCenterDao
 * @Description: 〈任务中心信息成就才能〉
 * @date 2018/10/26
 * All rights Reserved, Designed By SPINFO
 */
public interface TaskCenterDao
{
	/**
	 * 根据条件查询任务中心信息
	 * @param queryMap
	 * @return
	 */
	List<TaskCenterRsp> queryTaskCent(Map<String, Object> queryMap);

	/**
	 * 任务启动停止时修改资源的operationStatus值
	 * @param jobId
	 * @param operationStatus 操作状态值
	 */
	void updateTaskOperationStatus(String jobId, String operationStatus);

	/**
	 * 根据策略ID获取所有相关的任务信息
	 * @param disId
	 * @return
	 */
	List<TaskCenterRsp> getTaskInfoByDisId(String disId);

	/**
	 * 保存任务信息
	 * @param task
	 */
	void saveTask(SpTask task);

	/**
	 * 更新任务信息
	 * @param task
	 */
	void updateTask(SpTask task);

	/**
	 * 根据ID删除对应任务
	 * @param id
	 */
	void deleteTaskById(String id);

	/**
	 * 根据ID获取任务信息
	 * @param taskId 任务ID
	 * @return
	 */
	SpTask getTaskById(String taskId);

	/**
	 * 通过策略ID获取最新的任务信息
	 * @param jobid 策略ID
	 * @return
	 */
	SpTask getIsLastTaskByJobId(String jobid);

	/**
	 * 根据策略ID修改任务isLast状态
	 * @param jobid
	 * @param isLast 0最新任务 1历史任务
	 */
	void updateTaskIsLastByJobId(String jobid, String isLast);

	/**
	 * 获取任务总数
	 * @return
	 */
	int getTaskNum(@Param("status") String status);

	/**
	 * 获取一周前的总任务数
	 * @return
	 */
	int getTaskNumTimeAgo(@Param("status") String status);

}
