package com.dto.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class ChartData implements Serializable {
    @ApiModelProperty(value = "同地址下的人数维度（时间）", name = "time")
    private String time;
    @ApiModelProperty(value = "同地址下的人数个数", name = "number")
    private String number;
    @ApiModelProperty(value = "该月收发快递详情数据", name = "acceptDeliveryPeoperVo")
    private List<AcceptDeliveryPeoperVo> acceptDeliveryPeoperVo;

    public List<AcceptDeliveryPeoperVo> getAcceptDeliveryPeoperVo() {
        return acceptDeliveryPeoperVo;
    }

    public void setAcceptDeliveryPeoperVo(List<AcceptDeliveryPeoperVo> acceptDeliveryPeoperVo) {
        this.acceptDeliveryPeoperVo = acceptDeliveryPeoperVo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
