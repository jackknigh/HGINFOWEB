package com.dao.entity.zjreport;

import com.dao.entity.sys.ComColumn;

import java.io.Serializable;
import java.util.List;

public class RepDepConfig extends ComColumn implements Serializable {

    private String id;


    private String depCode;



    private String depProm;


    private String depPhone;


    private String depFlg;


    private String remark;

    private List<RepDepConfig> childRepDepConfigs;

    private static final long serialVersionUID = 1L;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }


    public String getDepCode() {
        return depCode;
    }


    public void setDepCode(String depCode) {
        this.depCode = depCode == null ? null : depCode.trim();
    }


    public String getDepProm() {
        return depProm;
    }


    public void setDepProm(String depProm) {
        this.depProm = depProm == null ? null : depProm.trim();
    }


    public String getDepPhone() {
        return depPhone;
    }


    public void setDepPhone(String depPhone) {
        this.depPhone = depPhone == null ? null : depPhone.trim();
    }


    public String getDepFlg() {
        return depFlg;
    }


    public void setDepFlg(String depFlg) {
        this.depFlg = depFlg == null ? null : depFlg.trim();
    }


    public String getRemark() {
        return remark;
    }


    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public List<RepDepConfig> getChildRepDepConfigs() {
        return childRepDepConfigs;
    }

    public void setChildRepDepConfigs(List<RepDepConfig> childRepDepConfigs) {
        this.childRepDepConfigs = childRepDepConfigs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", depCode=").append(depCode);
        sb.append(", depName=").append(depName);
        sb.append(", depNameAbb=").append(depNameAbb);
        sb.append(", depProm=").append(depProm);
        sb.append(", depPhone=").append(depPhone);
        sb.append(", depFlg=").append(depFlg);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}