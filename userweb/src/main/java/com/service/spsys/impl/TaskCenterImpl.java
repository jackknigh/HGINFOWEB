package com.service.spsys.impl;

import com.alibaba.fastjson.JSON;
import com.dao.db1.spsys.TaskCenterDao;
import com.dto.constants.Constants;
import com.dto.pojo.spsys.system.OperateMsg;
import com.dto.pojo.spsys.system.TaskCenterRsp;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.spsys.ITaskCenterSrv;
import com.utils.sys.MQUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName TaskCenterImpl
 * @Description: 〈任务中心业务实现类〉
 * @date 2018/10/26
 * All rights Reserved, Designed By SPINFO
 */
@Transactional
@Service("taskCenterSrv")
public class TaskCenterImpl implements ITaskCenterSrv
{
	private Logger log = LoggerFactory.getLogger(TaskCenterImpl.class);

	@Autowired
	private TaskCenterDao taskCenterDao;

	@Autowired
	private ActiveMQConnectionFactory connectionFactory;

	/**
	 * 分页查询任务中心信息
	 * @param queryMap
	 * @return
	 */
	@Override
	public PageInfo<TaskCenterRsp> queryTaskCentByPage(Map<String, Object> queryMap)
	{
		Integer pageNum = Integer.valueOf(queryMap.get("currentPage").toString());
		Integer pageSize = Integer.valueOf(queryMap.get("pageSize").toString());
		PageHelper.startPage(pageNum, pageSize);
		List<TaskCenterRsp> taskCenterRspList = taskCenterDao.queryTaskCent(queryMap);
		PageInfo<TaskCenterRsp> pageInfo = new PageInfo<>(taskCenterRspList);
		return pageInfo;
	}

	/**
	 * 根据策略ID获取更多的任务信息
	 * @param disId
	 * @return
	 */
	@Override
	public List<TaskCenterRsp> getTaskInfoByDisId(String disId)
	{
		return taskCenterDao.getTaskInfoByDisId(disId);
	}

	/**
	 * 根据ID删除任务
	 * @param ids
	 */
	@Override
	public void deleteTaskById(String[] ids)
	{
		for (String id : ids)
		{
			taskCenterDao.deleteTaskById(id);
		}

	}

	/**
	 * 获取任务总数
	 * @return
	 */
	@Override
	public int getTaskNum(String status)
	{
		return taskCenterDao.getTaskNum(status);
	}

	/**
	 * 获取一周前的总任务数
	 * @return
	 */
	@Override
	public int getTaskNumTimeAgo(String status)
	{
		return taskCenterDao.getTaskNumTimeAgo(status);
	}

	/**
	 * 启动任务
	 * @param jobId 任务ID
	 * @return
	 * @throws Exception
	 */
	@Override
	public OperateMsg startJob(String jobId) throws Exception
	{
		String msg = "{\"type\": \"startJob\",\"content\": [{\"jobId\": \"" + jobId + "\"}]}";
		log.debug("startJob send :" + msg);
		String res = MQUtil.sendMessage(connectionFactory, Constants.JOB_OPERATE, msg, 2 * 60 * 1000);
		// 解析返回内容
		log.debug("startJob return :" + res);
		log.debug("startJob return :" + res);
		OperateMsg result = JSON.parseObject(res, OperateMsg.class);
		if (null != result)
		{

		}
		return result;
	}

	/**
	 * 暂停任务
	 * @param jobId 任务ID
	 * @return
	 * @throws Exception
	 */
	@Override
	public OperateMsg pauseJob(String jobId) throws Exception
	{
		String msg = "{\"type\": \"pauseJob\",\"content\": [{\"jobId\": \"" + jobId + "\"}]}";
		log.debug("pauseJob send :" + msg);
		String res = MQUtil.sendMessage(connectionFactory, Constants.JOB_OPERATE, msg, 2 * 60 * 1000);
		// 解析返回内容
		log.debug("pauseJob return :" + res);
		OperateMsg result = JSON.parseObject(res, OperateMsg.class);
		return result;
	}

	/**
	 * 任务启动停止时修改资源的operationStatus值
	 * @param jobId
	 * @param operationStatus 操作状态值
	 */
	@Override
	public void updateTaskOperationStatus(String jobId, String operationStatus)
	{
		taskCenterDao.updateTaskIsLastByJobId(jobId, operationStatus);
	}
}
