package com.dto.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "信息搜索条件", description = "信息搜索条件")
public class SearchContentForm implements Serializable {
    @ApiModelProperty(value = "搜索内容文字", name = "searchText", required = true)
    private String searchText;
    @ApiModelProperty(value = "高级搜索数据", name = "tableData")
    private List<AdvancedSearch> tableData;
    @ApiModelProperty(value = "当前页", name = "currentPage", required = true)
    @Min(value = 1,message = "页数必须大于1")
    private int currentPage;
    @ApiModelProperty(value = "一页显示的条数", name = "pageSize", required = true)
    @Min(value = 1,message = "每页显示数必须大于1")
    private int pageSize;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public List<AdvancedSearch> getTableData() {
        return tableData;
    }

    public void setTableData(List<AdvancedSearch> tableData) {
        this.tableData = tableData;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
