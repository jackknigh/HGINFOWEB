package com.service.spsys;

import com.dto.pojo.spsys.system.ExportBusiDataDto;
import com.dto.pojo.spsys.system.ReportWordData;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName ICensorshipReportSrv
 * @Description: 〈审计报告管理业务类〉
 * @date 2018/10/31
 * All rights Reserved, Designed By SPINFO
 */
public interface ICensorshipReportSrv
{
	/**
	 * 导出事件PDF
	 * @param busiDataDto
	 * @param userId
	 * @return
	 */
	String getPdfForEvent(ExportBusiDataDto busiDataDto, String userId);

	/**
	 * 根据任务ID查询导出word所需数据
	 * @param taskId
	 * @return
	 */
	ReportWordData getReportWordByTaskId(String taskId);
}
