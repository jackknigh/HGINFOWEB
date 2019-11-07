package com.dao.db1.spsys;


import com.dao.entity.spsys.SpDscvrFiles;
import com.dto.pojo.spsys.system.DscvrFilesRsp;
import com.dto.pojo.spsys.system.ExportBusiDataDto;
import com.dto.pojo.spsys.system.ReportWordData;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName DscvrFilesDao
 * @Description: 〈检查事件持久层〉
 * @date 2018/10/17
 * All rights Reserved, Designed By SPINFO
 */
public interface DscvrFilesDao
{
	/**
	 * 查询检查事件
	 * @param queryMap
	 * @return
	 */
	List<DscvrFilesRsp> queryDscvrFiles(Map<String, Object> queryMap);

	/**
	 * 根据查询条件模型获取对应的事件数据
	 * @param busiDataDto
	 * @return
	 */
	List<DscvrFilesRsp> getDscvrFilesByBusiDataDto(ExportBusiDataDto busiDataDto);
	/**
	 * 保存检查事件
	 * @param spDscvrFiles
	 */
	void saveDscvrFiles(SpDscvrFiles spDscvrFiles);

	/**
	 * 更新检查事件
	 * @param spDscvrFiles
	 */
	void updateDscvrFiles(SpDscvrFiles spDscvrFiles);

	/**
	 * 通过ID查询事件信息
	 * @param id
	 * @return
	 */
	DscvrFilesRsp queryDscvrFilesById(String id);

	/**
	 * 根据ID删除检查事件
	 * @param id
	 */
	void deleteDscvrFilesById(String id);

	/**
	 *根据条件统计事件数
	 */
	Long countEventByCondition(Map<String, Object> conditionMap);

	/**
	 * 根据条件删除事件
	 */
	void deleteEventByCondition(Map<String, Object> conditionMap);

	/**
	 * 根据任务ID查询导出word所需数据
	 * @param taskId
	 * @return
	 */
	ReportWordData getReportWordByTaskId(String taskId);

	/**
	 * 按时间周期获取事件加密算法类型比例
	 * @param period 周期 DAY日 WEEK周 MONTH月 YEAR年
	 * @return
	 */
	List<Map<String, Object>> getAlgorithmScale(String period);

	/**
	 * 按时间周期获取事件 加密或未加密文件数量
	 * @param period 周期 DAY日 WEEK周 MONTH月 YEAR年
	 * @return
	 */
	List<Map<String, Object>> getIsEncrypScale(String period);
}
