package com.dto.pojo.spsys.system;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName ExportBusiDataDto
 * @Description: 〈导出条件封装〉
 * @date 2018/10/31
 * All rights Reserved, Designed By SPINFO
 */
public class ExportBusiDataDto
{
	/**
	 * 任务名称
	 */
	private String name;

	/**
	 * 任务类型
	 */
	private String type;

	/**
	 * 任务状态
	 */
	private String status;

	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * 文件类型
	 */
	private String fileType;

	/**
	 * 文件路径
	 */
	private String filePath;

	/**
	 * 检查时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date detectDateTs;

	/**
	 * 是否加密 0.否 1.是
	 */
	private double isEncrypt;

	/**
	 * 算法类型
	 */
	private String algorithmType;

	/**
	 * 以哪个字段排序
	 */
	private String sort;

	/**
	 * 升序 或 降序
	 */
	private String order;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getFileType()
	{
		return fileType;
	}

	public void setFileType(String fileType)
	{
		this.fileType = fileType;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public Date getDetectDateTs()
	{
		return detectDateTs;
	}

	public void setDetectDateTs(Date detectDateTs)
	{
		this.detectDateTs = detectDateTs;
	}

	public double getIsEncrypt()
	{
		return isEncrypt;
	}

	public void setIsEncrypt(double isEncrypt)
	{
		this.isEncrypt = isEncrypt;
	}

	public String getAlgorithmType()
	{
		return algorithmType;
	}

	public void setAlgorithmType(String algorithmType)
	{
		this.algorithmType = algorithmType;
	}

	public String getSort()
	{
		return sort;
	}

	public void setSort(String sort)
	{
		this.sort = sort;
	}

	public String getOrder()
	{
		return order;
	}

	public void setOrder(String order)
	{
		this.order = order;
	}
}
