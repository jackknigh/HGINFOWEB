/*
 * Created with [CuiLiangTools]
 * 2019-03-15
 */

package com.dao.entity.syserp;

import com.dao.entity.sys.ComColumn;

import java.io.Serializable;


public class DataDictFoot extends ComColumn implements Serializable{
 

	/*字段说明*/
	private String accountId;


	/*字段说明*/
	private Integer dataDictId;


	/*字段说明*/
	private String operatorId;


	/*字段说明*/
	private Integer ftindex;


	/*字段说明*/
	private String ftfieldName;


	/*字段说明*/
	private Integer ftalignment;


	/*字段说明*/
	private String ftdisplayFormat;


	/*字段说明*/
	private Integer ftvalueType;


	/*字段说明*/
	private String ftvalue;
	/*字段说明*/
    private static final long serialVersionUID = 1L;

	public void setAccountId(String value) {
		this.accountId = value;
	}

	public String getAccountId() {
		return this.accountId;
	}
	public void setDataDictId(Integer value) {
		this.dataDictId = value;
	}

	public Integer getDataDictId() {
		return this.dataDictId;
	}
	public void setOperatorId(String value) {
		this.operatorId = value;
	}

	public String getOperatorId() {
		return this.operatorId;
	}
	public void setFtindex(Integer value) {
		this.ftindex = value;
	}

	public Integer getFtindex() {
		return this.ftindex;
	}
	public void setFtfieldName(String value) {
		this.ftfieldName = value;
	}

	public String getFtfieldName() {
		return this.ftfieldName;
	}
	public void setFtalignment(Integer value) {
		this.ftalignment = value;
	}

	public Integer getFtalignment() {
		return this.ftalignment;
	}
	public void setFtdisplayFormat(String value) {
		this.ftdisplayFormat = value;
	}

	public String getFtdisplayFormat() {
		return this.ftdisplayFormat;
	}
	public void setFtvalueType(Integer value) {
		this.ftvalueType = value;
	}

	public Integer getFtvalueType() {
		return this.ftvalueType;
	}
	public void setFtvalue(String value) {
		this.ftvalue = value;
	}

	public String getFtvalue() {
		return this.ftvalue;
	}
}

