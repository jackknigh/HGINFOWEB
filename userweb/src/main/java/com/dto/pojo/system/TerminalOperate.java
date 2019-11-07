package com.dto.pojo.system;

import java.io.Serializable;

/**
 * @author Noah
 * @since 1.0.0, 2019/03/23
 */
public class TerminalOperate implements Serializable{

    private static final long serialVersionUID = -622229049807474721L;

    private String terminalId;

    private String functionModule;

    private Integer operateNum;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getFunctionModule() {
        return functionModule;
    }

    public void setFunctionModule(String functionModule) {
        this.functionModule = functionModule;
    }

    public Integer getOperateNum() {
        return operateNum;
    }

    public void setOperateNum(Integer operateNum) {
        this.operateNum = operateNum;
    }
}
