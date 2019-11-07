package com.dao.entity.sys;

import java.io.Serializable;

public class ComColumn implements Serializable {

    protected String updateFlg;

    protected String depName;

    protected String depNameAbb;

    public String getUpdateFlg() {
        return updateFlg;
    }

    public void setUpdateFlg(String updateFlg) {
        this.updateFlg = updateFlg;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDepNameAbb() {
        return depNameAbb;
    }

    public void setDepNameAbb(String depNameAbb) {
        this.depNameAbb = depNameAbb;
    }
}