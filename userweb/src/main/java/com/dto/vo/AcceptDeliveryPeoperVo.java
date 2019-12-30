package com.dto.vo;

import io.swagger.annotations.ApiModelProperty;

public class AcceptDeliveryPeoperVo {
    @ApiModelProperty(value = "具体时间", name = "date")
    private String date;
    @ApiModelProperty(value = "姓名", name = "name")
    private String name;
    @ApiModelProperty(value = "收/发", name = "status")
    private String status;
    @ApiModelProperty(value = "手机号", name = "telNumber")
    private String telNumber;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }
}
