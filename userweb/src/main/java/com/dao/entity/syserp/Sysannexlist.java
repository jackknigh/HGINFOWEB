package com.dao.entity.syserp;

import com.dao.entity.sys.ComColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="附件保存实体",description="附件保存实体")
public class Sysannexlist extends ComColumn implements Serializable {
    @ApiModelProperty(value = "帐套编号")
    private String accountid;

    @ApiModelProperty(value = "单据对应的表名")
    private String tableid;

    @ApiModelProperty(value = "单据的tableName+Id字段，个别情况为billid")
    private String billsid;

    @ApiModelProperty(value = "本单据内的自增编号")
    private Integer listid;

    @ApiModelProperty(value = "本单据内的排序编号")
    private Integer showindex;

    @ApiModelProperty(value = "附件说明")
    private String explain;

    @ApiModelProperty(value = "附件名")
    private String annexname;

    @ApiModelProperty(value = "上传人ID")
    private String operatorid;

    @ApiModelProperty(value = "上传日期")
    private Date adddate;

    @ApiModelProperty(value = "附件大小")
    private BigDecimal annexsize;

    @ApiModelProperty(value = "状态，默认0")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "附件二进制")
    private byte[] annex;

    @ApiModelProperty(value = "附件二进制")
    private byte[] annexex;
    @ApiModelProperty(value = "分步上传时服务器返回的临时文件名")
    private String tmpAnnexName;

    private static final long serialVersionUID = 1L;

    public String getAccountid() {
        return accountid;
    }


    public void setAccountid(String accountid) {
        this.accountid = accountid == null ? null : accountid.trim();
    }


    public String getTableid() {
        return tableid;
    }


    public void setTableid(String tableid) {
        this.tableid = tableid == null ? null : tableid.trim();
    }

    public String getTmpAnnexName() {
        return tmpAnnexName;
    }

    public void setTmpAnnexName(String tmpAnnexName) {
        this.tmpAnnexName = tmpAnnexName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getBillsid() {
        return billsid;
    }


    public void setBillsid(String billsid) {
        this.billsid = billsid == null ? null : billsid.trim();
    }


    public Integer getListid() {
        return listid;
    }


    public void setListid(Integer listid) {
        this.listid = listid;
    }


    public Integer getShowindex() {
        return showindex;
    }


    public void setShowindex(Integer showindex) {
        this.showindex = showindex;
    }


    public String getExplain() {
        return explain;
    }


    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }


    public String getAnnexname() {
        return annexname;
    }


    public void setAnnexname(String annexname) {
        this.annexname = annexname == null ? null : annexname.trim();
    }


    public String getOperatorid() {
        return operatorid;
    }


    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid == null ? null : operatorid.trim();
    }


    public Date getAdddate() {
        return adddate;
    }


    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }


    public BigDecimal getAnnexsize() {
        return annexsize;
    }


    public void setAnnexsize(BigDecimal annexsize) {
        this.annexsize = annexsize;
    }


    public Integer getState() {
        return state;
    }


    public void setState(Integer state) {
        this.state = state;
    }


    public String getRemark() {
        return remark;
    }


    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }


    public byte[] getAnnex() {
        return annex;
    }


    public void setAnnex(byte[] annex) {
        this.annex = annex;
    }


    public byte[] getAnnexex() {
        return annexex;
    }


    public void setAnnexex(byte[] annexex) {
        this.annexex = annexex;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", accountid=").append(accountid);
        sb.append(", tableid=").append(tableid);
        sb.append(", billsid=").append(billsid);
        sb.append(", listid=").append(listid);
        sb.append(", showindex=").append(showindex);
        sb.append(", explain=").append(explain);
        sb.append(", annexname=").append(annexname);
        sb.append(", operatorid=").append(operatorid);
        sb.append(", adddate=").append(adddate);
        sb.append(", annexsize=").append(annexsize);
        sb.append(", state=").append(state);
        sb.append(", remark=").append(remark);
        sb.append(", annex=").append(annex);
        sb.append(", annexex=").append(annexex);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}