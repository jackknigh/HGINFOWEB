package com.dto.pojo.stata;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;

public class StatList implements Serializable {

    private PageInfo currentTabPageInfo;
    private Integer ajxxSum;
    private Integer jjcSum;
    private Integer perpleSrcCzrkSum;
    private Integer perpleSrcZzrkSum;
    private Integer zdpeopleSum;

    public PageInfo getCurrentTabPageInfo() {
        return currentTabPageInfo;
    }

    public void setCurrentTabPageInfo(PageInfo currentTabPageInfo) {
        this.currentTabPageInfo = currentTabPageInfo;
    }

    public Integer getAjxxSum() {
        return ajxxSum;
    }

    public void setAjxxSum(Integer ajxxSum) {
        this.ajxxSum = ajxxSum;
    }

    public Integer getJjcSum() {
        return jjcSum;
    }

    public void setJjcSum(Integer jjcSum) {
        this.jjcSum = jjcSum;
    }

    public Integer getPerpleSrcCzrkSum() {
        return perpleSrcCzrkSum;
    }

    public void setPerpleSrcCzrkSum(Integer perpleSrcCzrkSum) {
        this.perpleSrcCzrkSum = perpleSrcCzrkSum;
    }

    public Integer getPerpleSrcZzrkSum() {
        return perpleSrcZzrkSum;
    }

    public void setPerpleSrcZzrkSum(Integer perpleSrcZzrkSum) {
        this.perpleSrcZzrkSum = perpleSrcZzrkSum;
    }

    public Integer getZdpeopleSum() {
        return zdpeopleSum;
    }

    public void setZdpeopleSum(Integer zdpeopleSum) {
        this.zdpeopleSum = zdpeopleSum;
    }
}
