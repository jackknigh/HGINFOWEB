package com.dao.entity.lwaddress;

import java.io.Serializable;

public class Discrepancy implements Serializable {
    //取数据时的表名
    private String obtain;
    //没撞上的数据入库时的表名
    private String depositBasics;
    //撞上的数据入库时的表名
    private String depositMerge;

    public String getObtain() {
        return obtain;
    }

    public void setObtain(String obtain) {
        this.obtain = obtain;
    }

    public String getDepositBasics() {
        return depositBasics;
    }

    public void setDepositBasics(String depositBasics) {
        this.depositBasics = depositBasics;
    }

    public String getDepositMerge() {
        return depositMerge;
    }

    public void setDepositMerge(String depositMerge) {
        this.depositMerge = depositMerge;
    }
}
