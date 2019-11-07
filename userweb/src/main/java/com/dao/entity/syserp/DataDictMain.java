/*
 * Created with [CuiLiangTools]
 * 2019-03-15
 */

package com.dao.entity.syserp;

import com.dao.entity.sys.ComColumn;

import java.io.Serializable;
import java.util.List;


public class DataDictMain extends ComColumn implements Serializable {


    /*字段说明*/
    private String accountId;


    /*字段说明*/
    private Integer dataDictId;


    /*字段说明*/
    private String operatorId;


    /*字段说明*/
    private String tableName;


    /*字段说明*/
    private String tableAlias;


    /*字段说明*/
    private String formCaption;


    /*字段说明*/
    private Integer titleCount;


    /*字段说明*/
    private Integer footCount;


    /*字段说明*/
    private Integer fixedIndex;


    /*字段说明*/
    private Integer fixedColor;


    /*字段说明*/
    private Integer tbfcolor;


    /*字段说明*/
    private Integer tbkcolor;


    /*字段说明*/
    private Integer cbfcolor;


    /*字段说明*/
    private Integer cbkcolor;


    /*字段说明*/
    private Integer fbfcolor;


    /*字段说明*/
    private Integer fbkcolor;


    /*字段说明*/
    private Integer sbfcolor;


    /*字段说明*/
    private Integer sbkcolor;


    /*字段说明*/
    private Integer state;


    /*字段说明*/
    private String remark;
    /*字段说明*/
    private static final long serialVersionUID = 1L;

    private List<DataDictDetail> dataDictDetails ;

    private List<DataDictFoot> dataDictFoots;

    private List<DbgridColor> dbgridColors;

    public List<DataDictDetail> getDataDictDetails() {
        return dataDictDetails;
    }

    public void setDataDictDetails(List<DataDictDetail> dataDictDetails) {
        this.dataDictDetails = dataDictDetails;
    }

    public List<DataDictFoot> getDataDictFoots() {
        return dataDictFoots;
    }

    public void setDataDictFoots(List<DataDictFoot> dataDictFoots) {
        this.dataDictFoots = dataDictFoots;
    }

    public List<DbgridColor> getDbgridColors() {
        return dbgridColors;
    }

    public void setDbgridColors(List<DbgridColor> dbgridColors) {
        this.dbgridColors = dbgridColors;
    }

    public void setAccountId(String value) {
        this.accountId = value;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setDataDictId(Integer value) {
        this.dataDictId = value;
    }

    public Integer getDataDictId() {
        return this.dataDictId;
    }

    public void setOperatorId(String value) {
        this.operatorId = value;
    }

    public String getOperatorId() {
        return this.operatorId;
    }

    public void setTableName(String value) {
        this.tableName = value;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableAlias(String value) {
        this.tableAlias = value;
    }

    public String getTableAlias() {
        return this.tableAlias;
    }

    public void setFormCaption(String value) {
        this.formCaption = value;
    }

    public String getFormCaption() {
        return this.formCaption;
    }

    public void setTitleCount(Integer value) {
        this.titleCount = value;
    }

    public Integer getTitleCount() {
        return this.titleCount;
    }

    public void setFootCount(Integer value) {
        this.footCount = value;
    }

    public Integer getFootCount() {
        return this.footCount;
    }

    public void setFixedIndex(Integer value) {
        this.fixedIndex = value;
    }

    public Integer getFixedIndex() {
        return this.fixedIndex;
    }

    public void setFixedColor(Integer value) {
        this.fixedColor = value;
    }

    public Integer getFixedColor() {
        return this.fixedColor;
    }

    public void setTbfcolor(Integer value) {
        this.tbfcolor = value;
    }

    public Integer getTbfcolor() {
        return this.tbfcolor;
    }

    public void setTbkcolor(Integer value) {
        this.tbkcolor = value;
    }

    public Integer getTbkcolor() {
        return this.tbkcolor;
    }

    public void setCbfcolor(Integer value) {
        this.cbfcolor = value;
    }

    public Integer getCbfcolor() {
        return this.cbfcolor;
    }

    public void setCbkcolor(Integer value) {
        this.cbkcolor = value;
    }

    public Integer getCbkcolor() {
        return this.cbkcolor;
    }

    public void setFbfcolor(Integer value) {
        this.fbfcolor = value;
    }

    public Integer getFbfcolor() {
        return this.fbfcolor;
    }

    public void setFbkcolor(Integer value) {
        this.fbkcolor = value;
    }

    public Integer getFbkcolor() {
        return this.fbkcolor;
    }

    public void setSbfcolor(Integer value) {
        this.sbfcolor = value;
    }

    public Integer getSbfcolor() {
        return this.sbfcolor;
    }

    public void setSbkcolor(Integer value) {
        this.sbkcolor = value;
    }

    public Integer getSbkcolor() {
        return this.sbkcolor;
    }

    public void setState(Integer value) {
        this.state = value;
    }

    public Integer getState() {
        return this.state;
    }

    public void setRemark(String value) {
        this.remark = value;
    }

    public String getRemark() {
        return this.remark;
    }
}

