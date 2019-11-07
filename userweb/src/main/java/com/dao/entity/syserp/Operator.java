package com.dao.entity.syserp;

import java.io.Serializable;

public class Operator implements Serializable {
    
    private String accountid;

    
    private String operatorid;

    
    private String name;

    
    private String operatorgroupid;

    
    private String language;

    
    private Integer state;

    
    private String remark;

    
    private byte[] password;

    
    private byte[] popedom;
    private String popedomStr;

    
    private static final long serialVersionUID = 1L;

    
    public String getAccountid() {
        return accountid;
    }

    
    public void setAccountid(String accountid) {
        this.accountid = accountid == null ? null : accountid.trim();
    }

    
    public String getOperatorid() {
        return operatorid;
    }

    
    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid == null ? null : operatorid.trim();
    }

    public String getPopedomStr() {
        return popedomStr;
    }

    public void setPopedomStr(String popedomStr) {
        this.popedomStr = popedomStr;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    
    public String getOperatorgroupid() {
        return operatorgroupid;
    }

    
    public void setOperatorgroupid(String operatorgroupid) {
        this.operatorgroupid = operatorgroupid == null ? null : operatorgroupid.trim();
    }

    
    public String getLanguage() {
        return language;
    }

    
    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    
    public Integer getState() {
        return state;
    }

    
    public void setState(Integer state) {
        this.state = state;
    }

    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    
    public byte[] getPassword() {
        return password;
    }

    
    public void setPassword(byte[] password) {
        this.password = password;
    }

    
    public byte[] getPopedom() {
        return popedom;
    }

    
    public void setPopedom(byte[] popedom) {
        this.popedom = popedom;
    }

    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", accountid=").append(accountid);
        sb.append(", operatorid=").append(operatorid);
        sb.append(", name=").append(name);
        sb.append(", operatorgroupid=").append(operatorgroupid);
        sb.append(", language=").append(language);
        sb.append(", state=").append(state);
        sb.append(", remark=").append(remark);
        sb.append(", password=").append(password);
        sb.append(", popedom=").append(popedom);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}