package com.service.spsys;

import com.dao.entity.spsys.SpEncryptionAlgorithm;
import com.dao.entity.spsys.SpPlcFileTypes;
import com.dao.entity.spsys.SpTargetRes;
import com.dto.pojo.spsys.system.tatic.DiscoveryTaskFormData;
import com.dto.pojo.spsys.system.tatic.TargetResFormData;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName ITacticSrv
 * @Description: 〈扫描任务业务处理接口〉
 * @date 2018/10/22
 * All rights Reserved, Designed By SPINFO
 */
public interface ITacticSrv
{
	/**
	 * 保存目标资源
	 * @param formData
	 * @return
	 * @throws Exception
	 */
	SpTargetRes saveTargetRes(TargetResFormData formData) throws Exception;

	/**
	 * 保存扫描任务
	 * @param data
	 */
	void saveDiscoveryTask(DiscoveryTaskFormData data);

	/**
	 * @Title: updateSpPlcDiscoveryTask
	 * @Description: 更新扫描策略
	 * @param: @param id
	 * @return: void
	 * @throws
	 */
	void updateSpPlcDiscoveryTask(DiscoveryTaskFormData data) throws Exception;

	/**
	 * 根据ID查询扫描策略
	 * @param id
	 * @return
	 */
	DiscoveryTaskFormData getDiscoveryTaskById(String id);

	/**
	 * 根据ID删除扫描策略
	 * @param idList
	 */
	void deleteDiscoveryTaskById(List<String> idList);

	/**
	 * 根据主机资源ID删除主机资源信息
	 * @param targetId
	 */
	void deleteTargetRes(String targetId);

	/**
	 * 获取所有文件类型
	 * @return
	 */
	PageInfo<SpPlcFileTypes> getFileTypesByPage(Map<String, Object> queryMap);

	/**
	 * 获取常用文件类型
	 * @return
	 */
	List<SpPlcFileTypes> getCommonFileType();

	/**
	 * 添加/移除常用文件类型
	 * @param id
	 * @param state
	 */
	void updateFileTypeCommon(String id, String state);

	/**
	 * 获取加密算法库
	 * @return
	 */
	List<SpEncryptionAlgorithm> getEncryptionAlgorithm();

}
