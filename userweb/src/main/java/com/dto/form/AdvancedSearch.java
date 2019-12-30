package com.dto.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "高级搜索条件", description = "高级搜索条件")
public class AdvancedSearch implements Serializable {
    @ApiModelProperty(value = "身份证号", name = "IDNumber")
    private String idNumber;
    @ApiModelProperty(value = "地址", name = "address")
    private String address;
    @ApiModelProperty(value = "电话号码", name = "telNumber")
    private String telNumber;
    @ApiModelProperty(value = "条件值:and || or", name = "condition")
    private String condition;

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
