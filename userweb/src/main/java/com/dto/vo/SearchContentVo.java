package com.dto.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "信息搜索返回结果", description = "信息搜索返回结果")
public class SearchContentVo implements Serializable {
    @ApiModelProperty(value = "总条数", name = "total")
    private long total;
    @ApiModelProperty(value = "人员信息", name = "data")
    private List<PersonMsgVo> data;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<PersonMsgVo> getData() {
        return data;
    }

    public void setData(List<PersonMsgVo> data) {
        this.data = data;
    }
}
