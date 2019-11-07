package com.dto.pojo.stata;

import java.io.Serializable;

public class StatPGISDTO implements Serializable {

    private String depCode;
    private String depName;
    private Integer sum;
    private Integer lastSum;
    private String proportion;

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getLastSum() {
        return lastSum;
    }

    public void setLastSum(Integer lastSum) {
        this.lastSum = lastSum;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }
}
