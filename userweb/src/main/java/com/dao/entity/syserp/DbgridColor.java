/*
 * Created with [CuiLiangTools]
 * 2019-03-15
 */

package com.dao.entity.syserp;


import com.dao.entity.sys.ComColumn;

import java.io.Serializable;

public class DbgridColor extends ComColumn implements Serializable {
 

	/*字段说明*/
	private String accountId;


	/*字段说明*/
	private String operatorId;


	/*字段说明*/
	private Integer functionId;


	/*字段说明*/
	private Integer dataDictId;


	/*字段说明*/
	private Integer type;


	/*字段说明*/
	private Integer conditionIndex;


	/*字段说明*/
	private Integer logicType;


	/*字段说明*/
	private String fieldName;


	/*字段说明*/
	private Integer condition;


	/*字段说明*/
	private String value;


	/*字段说明*/
	private Integer color;


	/*字段说明*/
	private Integer backColor;
	/*字段说明*/
    private static final long serialVersionUID = 1L;

	public void setAccountId(String value) {
		this.accountId = value;
	}

	public String getAccountId() {
		return this.accountId;
	}
	public void setOperatorId(String value) {
		this.operatorId = value;
	}

	public String getOperatorId() {
		return this.operatorId;
	}
	public void setFunctionId(Integer value) {
		this.functionId = value;
	}

	public Integer getFunctionId() {
		return this.functionId;
	}
	public void setDataDictId(Integer value) {
		this.dataDictId = value;
	}

	public Integer getDataDictId() {
		return this.dataDictId;
	}
	public void setType(Integer value) {
		this.type = value;
	}

	public Integer getType() {
		return this.type;
	}
	public void setConditionIndex(Integer value) {
		this.conditionIndex = value;
	}

	public Integer getConditionIndex() {
		return this.conditionIndex;
	}
	public void setLogicType(Integer value) {
		this.logicType = value;
	}

	public Integer getLogicType() {
		return this.logicType;
	}
	public void setFieldName(String value) {
		this.fieldName = value;
	}

	public String getFieldName() {
		return this.fieldName;
	}
	public void setCondition(Integer value) {
		this.condition = value;
	}

	public Integer getCondition() {
		return this.condition;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
	public void setColor(Integer value) {
		this.color = value;
	}

	public Integer getColor() {
		return this.color;
	}
	public void setBackColor(Integer value) {
		this.backColor = value;
	}

	public Integer getBackColor() {
		return this.backColor;
	}
}

