package com.dto.pojo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "流程跳转使用的结构体", description = "转换为jsonstring赋值给ProcessInfode ExtContent字段")
public class ProcessInfoExtContent {

    @ApiModelProperty(value = "帐套编号", required = true)
    private String pAccountId;

    @ApiModelProperty(value = "流程编号", required = true)
    private String pProId;

    @ApiModelProperty(value = "功能编号", required = false)
    private String pFuncId;

    @ApiModelProperty(value = "流程步骤号", required = false)
    private String pStep;

    @ApiModelProperty(value = "主键编号tableName+ID", required = false)
    private String pPkId;

    @ApiModelProperty(value = "父主键编号tableName+ID", required = false)
    private String pParentPkId;

    public String getpAccountId() {
        return pAccountId;
    }

    public void setpAccountId(String pAccountId) {
        this.pAccountId = pAccountId;
    }

    public String getpProId() {
        return pProId;
    }

    public void setpProId(String pProId) {
        this.pProId = pProId;
    }

    public String getpFuncId() {
        return pFuncId;
    }

    public void setpFuncId(String pFuncId) {
        this.pFuncId = pFuncId;
    }

    public String getpStep() {
        return pStep;
    }

    public void setpStep(String pStep) {
        this.pStep = pStep;
    }

    public String getpPkId() {
        return pPkId;
    }

    public void setpPkId(String pPkId) {
        this.pPkId = pPkId;
    }

    public String getpParentPkId() {
        return pParentPkId;
    }

    public void setpParentPkId(String pParentPkId) {
        this.pParentPkId = pParentPkId;
    }
}
