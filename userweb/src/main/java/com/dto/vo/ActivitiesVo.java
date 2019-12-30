package com.dto.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "人员活动", description = "人员活动")
public class ActivitiesVo implements Serializable {
    @ApiModelProperty(value = "时间", name = "timestamp")
    private String timestamp;
    @ApiModelProperty(value = "新增人数", name = "contentAdd")
    private String contentAdd;
    @ApiModelProperty(value = "减少人数", name = "contentCut")
    private String contentCut;
    @ApiModelProperty(value = "主键id", name = "timestamp")
    private List<PersonChangeVo> tableData;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getContentAdd() {
        return contentAdd;
    }

    public void setContentAdd(String contentAdd) {
        this.contentAdd = contentAdd;
    }

    public String getContentCut() {
        return contentCut;
    }

    public void setContentCut(String contentCut) {
        this.contentCut = contentCut;
    }

    public List<PersonChangeVo> getTableData() {
        return tableData;
    }

    public void setTableData(List<PersonChangeVo> tableData) {
        this.tableData = tableData;
    }
}
