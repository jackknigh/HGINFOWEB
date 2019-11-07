package com.dto.pojo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "流程消息调用时推送的信息", description = "流程消息调用时推送的信息")
public class ProcessInfo {

    @ApiModelProperty(value = "帐套编号", required = true)
    private String pAccountId;

    @ApiModelProperty(value = "流程编号，单纯审核无流程配置时传空", required = false)
    private Integer pProId;

    @ApiModelProperty(value = "步骤名称，同ERP流程维护'过程名'", required = false)
    private String pProName;

    @ApiModelProperty(value = "功能编号", required = false)
    private Integer pFuncId;

    @ApiModelProperty(value = "操作类别,0=浏览;1=新增;2=删除;3=审核;4=退审;5=完成;6=自定义", required = false)
    private String pDsState;

    @ApiModelProperty(value = "流程步骤号", required = false)
    private Integer pStep;

    @ApiModelProperty(value = "主键编号tableName+ID", required = false)
    private String pPkId;

    @ApiModelProperty(value = "父主键编号tableName+ID", required = false)
    private String pParentPkId;

    @ApiModelProperty(value = "自定义接受人ID，'|'进行分割", required = false)
    private String pReceiveID;

    @ApiModelProperty(value = "自定义消息,拼接与系统格式化消息后", required = false)
    private String pSelfMsg;

    @ApiModelProperty(value = "APP自定义消息推送title", required = false)
    private String pMsgTitle;

    @ApiModelProperty(value = "APP自定义消息推送内容", required = false)
    private String pMsgContent;

    @ApiModelProperty(value = "扩展内容（APP点击跳转用）", required = false)
    private String pExtContent;

    public String getpAccountId() {
        return pAccountId;
    }

    public void setpAccountId(String pAccountId) {
        this.pAccountId = pAccountId;
    }

    public Integer getpProId() {
        return pProId;
    }

    public void setpProId(Integer pProId) {
        this.pProId = pProId;
    }

    public String getpProName() {
        return pProName;
    }

    public void setpProName(String pProName) {
        this.pProName = pProName;
    }

    public Integer getpFuncId() {
        return pFuncId;
    }

    public void setpFuncId(Integer pFuncId) {
        this.pFuncId = pFuncId;
    }

    public String getpDsState() {
        return pDsState;
    }

    public void setpDsState(String pDsState) {
        this.pDsState = pDsState;
    }

    public Integer getpStep() {
        return pStep;
    }

    public void setpStep(Integer pStep) {
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

    public String getpReceiveID() {
        return pReceiveID;
    }

    public void setpReceiveID(String pReceiveID) {
        this.pReceiveID = pReceiveID;
    }

    public String getpSelfMsg() {
        return pSelfMsg;
    }

    public void setpSelfMsg(String pSelfMsg) {
        this.pSelfMsg = pSelfMsg;
    }

    public String getpMsgTitle() {
        return pMsgTitle;
    }

    public void setpMsgTitle(String pMsgTitle) {
        this.pMsgTitle = pMsgTitle;
    }

    public String getpMsgContent() {
        return pMsgContent;
    }

    public void setpMsgContent(String pMsgContent) {
        this.pMsgContent = pMsgContent;
    }

    public String getpExtContent() {
        return pExtContent;
    }

    public void setpExtContent(String pExtContent) {
        this.pExtContent = pExtContent;
    }
}
