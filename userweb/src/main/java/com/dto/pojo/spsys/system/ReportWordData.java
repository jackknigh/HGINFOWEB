package com.dto.pojo.spsys.system;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName ReportWordData
 * @Description: 〈一句话功能简述〉
 * @date 2018/11/14
 * All rights Reserved, Designed By SPINFO
 */
public class ReportWordData
{
	/**
	 * 主键ID
	 */
	private String id;

	/**
	 * 任务名称
	 */
	private String name;

	/**
	 * 目标ID
	 */
	private String targetIp;

	/**
	 * 被检单位名称
	 */
	private String beCheckOrgName;

	/**
	 * 检查单位名称
	 */
	private String checkOrgName;

	/**
	 * 检查时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date checkDate;

	/**
	 * 检查数量
	 */
	private String checkNum;

	/**
	 * 是否加密
	 */
	private double isEncrypt;

	/**
	 * 预期加密算法
	 */
	private String expectAlgorithm;

	/**
	 * 实际机密算法
	 */
	private String realityAlgorithm;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTargetIp()
	{
		return targetIp;
	}

	public void setTargetIp(String targetIp)
	{
		this.targetIp = targetIp;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getBeCheckOrgName()
	{
		return beCheckOrgName;
	}

	public void setBeCheckOrgName(String beCheckOrgName)
	{
		this.beCheckOrgName = beCheckOrgName;
	}

	public String getCheckOrgName()
	{
		return checkOrgName;
	}

	public void setCheckOrgName(String checkOrgName)
	{
		this.checkOrgName = checkOrgName;
	}

	public Date getCheckDate()
	{
		return checkDate;
	}

	public void setCheckDate(Date checkDate)
	{
		this.checkDate = checkDate;
	}

	public double getIsEncrypt()
	{
		return isEncrypt;
	}

	public void setIsEncrypt(double isEncrypt)
	{
		this.isEncrypt = isEncrypt;
	}

	public String getExpectAlgorithm()
	{
		return expectAlgorithm;
	}

	public void setExpectAlgorithm(String expectAlgorithm)
	{
		this.expectAlgorithm = expectAlgorithm;
	}

	public String getRealityAlgorithm()
	{
		return realityAlgorithm;
	}

	public void setRealityAlgorithm(String realityAlgorithm)
	{
		this.realityAlgorithm = realityAlgorithm;
	}

	public String getCheckNum()
	{
		return checkNum;
	}

	public void setCheckNum(String checkNum)
	{
		this.checkNum = checkNum;
	}
}
