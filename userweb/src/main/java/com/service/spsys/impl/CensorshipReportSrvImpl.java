package com.service.spsys.impl;


import com.dao.db1.spsys.DscvrFilesDao;
import com.dao.db1.spsys.DscvrFilesDao;
import com.dto.pojo.spsys.system.DscvrFilesRsp;
import com.dto.pojo.spsys.system.ExportBusiDataDto;
import com.dto.pojo.spsys.system.ReportWordData;
import com.service.spsys.ICensorshipReportSrv;
import com.spinfosec.system.RspCode;
import com.spinfosec.system.TMCException;
import com.utils.sys.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName CensorshipReportSrvImpl
 * @Description: 〈审计报告管理业务类实现类〉
 * @date 2018/10/31
 * All rights Reserved, Designed By SPINFO
 */
@Transactional
@Service("censorshipReportSrv")
public class CensorshipReportSrvImpl implements ICensorshipReportSrv
{

	@Autowired
	private DscvrFilesDao dscvrFilesDao;

	private static final Logger log = LoggerFactory.getLogger(CensorshipReportSrvImpl.class);

	/**
	 * 导出事件PDF
	 * @param busiDataDto
	 * @param userId
	 * @return
	 */
	@Override
	public String getPdfForEvent(ExportBusiDataDto busiDataDto, String userId)
	{
		try
		{
			// 根据查询条件获取相应的检查事件数据
			List<DscvrFilesRsp> dscvrFilesRspList = dscvrFilesDao.getDscvrFilesByBusiDataDto(busiDataDto);

			if (dscvrFilesRspList.size() == 0)
			{
				throw new TMCException(RspCode.OBJECE_NOT_EXIST);
			}

			ClassLoader classLoader = CensorshipReportSrvImpl.class.getClassLoader();
			URL templates = classLoader.getResource("templates");
			String path = templates.getPath().replaceFirst("/", "");

			String eventPdfFile = FileUtils.createEventPdfFile(dscvrFilesRspList, path);
			return eventPdfFile;
		}
		catch (Exception e)
		{
			log.error("检查事件文件导出失败...", e);
		}

		return null;

	}

	/**
	 * 根据任务ID查询导出word所需数据
	 * @param taskId
	 * @return
	 */
	@Override
	public ReportWordData getReportWordByTaskId(String taskId)
	{
		return dscvrFilesDao.getReportWordByTaskId(taskId);
	}

	public static void main(String[] args) throws Exception
	{
		String pdfPath = "G:/encryptionRecognition/target/classes/templates/1541059293131.pdf";
		File pdfFile = new File(pdfPath);
		org.apache.commons.io.FileUtils.deleteQuietly(pdfFile);

	}

}
