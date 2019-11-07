/*
 * Created with [CuiLiangTools]
 * 2019-03-15
 */

package com.dao.entity.syserp;

import com.dao.entity.sys.ComColumn;

import java.io.Serializable;


public class DataDictDetail extends ComColumn implements Serializable{
 

	/*字段说明*/
	private String accountId;


	/*字段说明*/
	private Integer dataDictId;


	/*字段说明*/
	private String operatorId;


	/*字段说明*/
	private String tableName;


	/*字段说明*/
	private Integer fieldIndex;


	/*字段说明*/
	private String fieldName;


	/*字段说明*/
	private String fieldAlias;


	/*字段说明*/
	private Integer fieldType;


	/*字段说明*/
	private Integer fieldLength;


	/*字段说明*/
	private Integer displayIndex;


	/*字段说明*/
	private Integer titleAlignment;


	/*字段说明*/
	private Integer titleColor;


	/*字段说明*/
	private Integer alignment;


	/*字段说明*/
	private Integer fieldColor;


	/*字段说明*/
	private String editMask;


	/*字段说明*/
	private String displayFormat;


	/*字段说明*/
	private Integer displayWidth;


	/*字段说明*/
	private Boolean onlyOne;


	/*字段说明*/
	private Boolean isNull;


	/*字段说明*/
	private Boolean readOnly;


	/*字段说明*/
	private Boolean primaryKey;


	/*字段说明*/
	private Boolean realField;


	/*字段说明*/
	private Boolean changed;


	/*字段说明*/
	private Integer functionId;
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
	public void setTableName(String value) {
		this.tableName = value;
	}

	public String getTableName() {
		return this.tableName;
	}
	public void setFieldIndex(Integer value) {
		this.fieldIndex = value;
	}

	public Integer getFieldIndex() {
		return this.fieldIndex;
	}
	public void setFieldName(String value) {
		this.fieldName = value;
	}

	public String getFieldName() {
		return this.fieldName;
	}
	public void setFieldAlias(String value) {
		this.fieldAlias = value;
	}

	public String getFieldAlias() {
		return this.fieldAlias;
	}
	public void setFieldType(Integer value) {
		this.fieldType = value;
	}

	public Integer getFieldType() {
		return this.fieldType;
	}
	public void setFieldLength(Integer value) {
		this.fieldLength = value;
	}

	public Integer getFieldLength() {
		return this.fieldLength;
	}
	public void setDisplayIndex(Integer value) {
		this.displayIndex = value;
	}

	public Integer getDisplayIndex() {
		return this.displayIndex;
	}
	public void setTitleAlignment(Integer value) {
		this.titleAlignment = value;
	}

	public Integer getTitleAlignment() {
		return this.titleAlignment;
	}
	public void setTitleColor(Integer value) {
		this.titleColor = value;
	}

	public Integer getTitleColor() {
		return this.titleColor;
	}
	public void setAlignment(Integer value) {
		this.alignment = value;
	}

	public Integer getAlignment() {
		return this.alignment;
	}
	public void setFieldColor(Integer value) {
		this.fieldColor = value;
	}

	public Integer getFieldColor() {
		return this.fieldColor;
	}
	public void setEditMask(String value) {
		this.editMask = value;
	}

	public String getEditMask() {
		return this.editMask;
	}
	public void setDisplayFormat(String value) {
		this.displayFormat = value;
	}

	public String getDisplayFormat() {
		return this.displayFormat;
	}
	public void setDisplayWidth(Integer value) {
		this.displayWidth = value;
	}

	public Integer getDisplayWidth() {
		return this.displayWidth;
	}
	public void setOnlyOne(Boolean value) {
		this.onlyOne = value;
	}

	public Boolean getOnlyOne() {
		return this.onlyOne;
	}
	public void setIsNull(Boolean value) {
		this.isNull = value;
	}

	public Boolean getIsNull() {
		return this.isNull;
	}
	public void setReadOnly(Boolean value) {
		this.readOnly = value;
	}

	public Boolean getReadOnly() {
		return this.readOnly;
	}
	public void setPrimaryKey(Boolean value) {
		this.primaryKey = value;
	}

	public Boolean getPrimaryKey() {
		return this.primaryKey;
	}
	public void setRealField(Boolean value) {
		this.realField = value;
	}

	public Boolean getRealField() {
		return this.realField;
	}
	public void setChanged(Boolean value) {
		this.changed = value;
	}

	public Boolean getChanged() {
		return this.changed;
	}
	public void setFunctionId(Integer value) {
		this.functionId = value;
	}

	public Integer getFunctionId() {
		return this.functionId;
	}
}

