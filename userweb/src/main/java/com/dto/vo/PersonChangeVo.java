package com.dto.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "人员变动", description = "人员变动")
public class PersonChangeVo implements Serializable {
    @ApiModelProperty(value = "人员的状态（1:新增，0:减少）", name = "inOut")
    private int inOut;
    @ApiModelProperty(value = "身份证号", name = "iDNumber")
    private String iDNumber;
    @ApiModelProperty(value = "姓名", name = "name")
    private String name;
    @ApiModelProperty(value = "地址", name = "address")
    private String address;
    @ApiModelProperty(value = "电话", name = "telNumber")
    private String telNumber;

    public int getInOut() {
        return inOut;
    }

    public void setInOut(int inOut) {
        this.inOut = inOut;
    }

    public String getiDNumber() {
        return iDNumber;
    }

    public void setiDNumber(String iDNumber) {
        this.iDNumber = iDNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }
}
