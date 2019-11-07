package com.dto.pojo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="审核时递交的审核信息",description="审核时递交的审核信息")
public class BillAuditInfo
{

	@ApiModelProperty(value = "帐套编号", required = true)
	private String aAccountId;

	@ApiModelProperty(value = "表单名称", required = true)
	private String aTableId;

	@ApiModelProperty(value = "单据内部编号", required = true)
	private Integer aBillId;

	@ApiModelProperty(value = "单据名称", required = false)
	private String aBillName;

	@ApiModelProperty(value = "审核步骤，非审核流程传0,审核实际步骤从0开始", required = true)
	private Integer aAuditStep;

	@ApiModelProperty(value = "审核内容", required = false)
	private String aAuditContent;//审核内容

	@ApiModelProperty(value = "审核结果", required = false)
	private String aAuditResult;//审核结果

//	@ApiModelProperty(value = "审核时间,默认系统赋值", required = false)
//	private String checkData;

	@ApiModelProperty(value = "功能编号", required = true)
	private Integer aFuncId;


	@ApiModelProperty(value = "审核人编号", required = true)
	private String aOperatorId;

//	@ApiModelProperty(value = "用于其他终端的扩展消息", required = false)
//	private String extAuditMsg;


	public String getaAccountId() {
		return aAccountId;
	}

	public void setaAccountId(String aAccountId) {
		this.aAccountId = aAccountId;
	}

	public String getaTableId() {
		return aTableId;
	}

	public void setaTableId(String aTableId) {
		this.aTableId = aTableId;
	}

	public Integer getaBillId() {
		return aBillId;
	}

	public void setaBillId(Integer aBillId) {
		this.aBillId = aBillId;
	}

	public String getaBillName() {
		return aBillName;
	}

	public void setaBillName(String aBillName) {
		this.aBillName = aBillName;
	}

	public Integer getaAuditStep() {
		return aAuditStep;
	}

	public void setaAuditStep(Integer aAuditStep) {
		this.aAuditStep = aAuditStep;
	}

	public String getaAuditContent() {
		return aAuditContent;
	}

	public void setaAuditContent(String aAuditContent) {
		this.aAuditContent = aAuditContent;
	}

	public String getaAuditResult() {
		return aAuditResult;
	}

	public void setaAuditResult(String aAuditResult) {
		this.aAuditResult = aAuditResult;
	}

	public Integer getaFuncId() {
		return aFuncId;
	}

	public void setaFuncId(Integer aFuncId) {
		this.aFuncId = aFuncId;
	}

	public String getaOperatorId() {
		return aOperatorId;
	}

	public void setaOperatorId(String aOperatorId) {
		this.aOperatorId = aOperatorId;
	}
}
