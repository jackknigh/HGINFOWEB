package com.service.spsys;


import com.dto.pojo.spsys.system.OperateMsg;
import com.dto.pojo.spsys.system.TaskCenterRsp;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName ITaskCenterSrv
 * @Description: 〈任务中心业务接口〉
 * @date 2018/10/26
 * All rights Reserved, Designed By SPINFO
 */
public interface ITaskCenterSrv
{

	/**
	 * 分页查询任务中心信息
	 * @param queryMap
	 * @return
	 */
	PageInfo<TaskCenterRsp> queryTaskCentByPage(Map<String, Object> queryMap);

	/**
	 * 根据策略ID获取更多的任务信息
	 * @param disId
	 * @return
	 */
	List<TaskCenterRsp> getTaskInfoByDisId(String disId);

	/**
	 * 根据ID删除任务
	 * @param ids
	 */
	void deleteTaskById(String[] ids);

	/**
	 * 获取任务总数
	 * @param status 任务状态
	 * @return
	 */
	int getTaskNum(String status);

	/**
	 * 获取一周前的总任务数
	 * @return
	 */
	int getTaskNumTimeAgo(String status);

	/**
	 * 启动任务
	 * @param jobId 任务ID
	 * @return
	 * @throws Exception
	 */
	OperateMsg startJob(String jobId) throws Exception;

	/**
	 * 暂停任务
	 * @param jobId 任务ID
	 * @return
	 * @throws Exception
	 */
	OperateMsg pauseJob(String jobId) throws Exception;

	/**
	 * 任务启动停止时修改资源的operationStatus值
	 * @param jobId
	 * @param operationStatus 操作状态值
	 */
	void updateTaskOperationStatus(String jobId, String operationStatus);

}
