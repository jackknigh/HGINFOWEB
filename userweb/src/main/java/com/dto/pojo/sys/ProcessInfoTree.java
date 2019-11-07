package com.dto.pojo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value="查询的流程信息分组树",description="查询的流程信息分组树")
public class ProcessInfoTree
{

	@ApiModelProperty(value = "帐套编号")
	private String accountId;

	@ApiModelProperty(value = "流程名称")
	private String proName;

	@ApiModelProperty(value = "流程编号")
	private Integer proId;

	@ApiModelProperty(value = "步骤编号")
	private Integer step;

	@ApiModelProperty(value = "功能编号")
	private Integer funcId;

	@ApiModelProperty(value = "汇总数量")
	private Integer sumCount;//审核内容

	@ApiModelProperty(value = "是否叶子节点，true为叶子节点")
	private boolean iLast;//审核结果

	@ApiModelProperty(value = "自定义查询存储过程名")
	private String searchProName;

	@ApiModelProperty(value = "数据字典")
	private Integer datadictId;

	@ApiModelProperty(value = "子节点")
	private List<ProcessInfoTree> child = new ArrayList<>();

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public Integer getProId() {
		return proId;
	}

	public void setProId(Integer proId) {
		this.proId = proId;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Integer getFuncId() {
		return funcId;
	}

	public void setFuncId(Integer funcId) {
		this.funcId = funcId;
	}

	public Integer getSumCount() {
		return sumCount;
	}

	public void setSumCount(Integer sumCount) {
		this.sumCount = sumCount;
	}

	public boolean isiLast() {
		return iLast;
	}

	public void setiLast(boolean iLast) {
		this.iLast = iLast;
	}

	public String getSearchProName() {
		return searchProName;
	}

	public void setSearchProName(String searchProName) {
		this.searchProName = searchProName;
	}

	public Integer getDatadictId() {
		return datadictId;
	}

	public void setDatadictId(Integer datadictId) {
		this.datadictId = datadictId;
	}

	public List<ProcessInfoTree> getChild() {
		return child;
	}

	public void setChild(List<ProcessInfoTree> child) {
		this.child = child;
	}
}
