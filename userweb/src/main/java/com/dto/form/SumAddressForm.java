package com.dto.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "合并地址参数", description = "合并地址参数")
public class SumAddressForm implements Serializable {
    @ApiModelProperty(value = "该地址id", name = "addressId")
    @NotBlank(message = "地址id不能为空")
    private String addressId;
    @ApiModelProperty(value = "要合并的主键", name = "keyData")
    @NotBlank(message = "合并地址id不能为空")
    private String keyData;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getKeyData() {
        return keyData;
    }

    public void setKeyData(String keyData) {
        this.keyData = keyData;
    }
}
