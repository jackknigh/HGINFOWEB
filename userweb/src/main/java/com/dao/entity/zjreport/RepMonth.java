package com.dao.entity.zjreport;

import com.dao.entity.sys.ComColumn;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RepMonth extends ComColumn implements Serializable {

    private String id;


    private Date inputdate;


    private Date loaddata;


    private Date finaldata;


    private String tableNo;


    private String docNo;


    private String tabName;


    private String bakName;


    private String inputName;


    private String finalName;


    private String remark;


    private String remark2;


    private String remark3;


    private String remark4;


    private String remark5;


    private String type;


    private String linkowid;


    private String linkowid1;


    private String linkowid2;


    private String linkowid3;


    private String linkowid4;


    private String linkowid5;


    private String repNo;


    private Date effBegindate;


    private Date effEnddate;


    private String depCode;


    private Date createtime;


    private String creator;


    private String creatorName;


    private Date lastupdate;


    private String updator;


    private String updatorName;


    private Integer state;


    private Integer ver;


    private Date vertime;


    private Integer deptId;


    private String deptPath;


    private Integer delflg;

    private RepMonthLink repMonthLink;
    private List<RepMonthDetail> repMonthDetails;




    public RepMonthLink getRepMonthLink() {
        return repMonthLink;
    }

    public void setRepMonthLink(RepMonthLink repMonthLink) {
        this.repMonthLink = repMonthLink;
    }

    public List<RepMonthDetail> getRepMonthDetails() {
        return repMonthDetails;
    }

    public void setRepMonthDetails(List<RepMonthDetail> repMonthDetails) {
        this.repMonthDetails = repMonthDetails;
    }


    private static final long serialVersionUID = 1L;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }


    public Date getInputdate() {
        return inputdate;
    }


    public void setInputdate(Date inputdate) {
        this.inputdate = inputdate;
    }


    public Date getLoaddata() {
        return loaddata;
    }


    public void setLoaddata(Date loaddata) {
        this.loaddata = loaddata;
    }


    public Date getFinaldata() {
        return finaldata;
    }


    public void setFinaldata(Date finaldata) {
        this.finaldata = finaldata;
    }


    public String getTableNo() {
        return tableNo;
    }


    public void setTableNo(String tableNo) {
        this.tableNo = tableNo == null ? null : tableNo.trim();
    }


    public String getDocNo() {
        return docNo;
    }


    public void setDocNo(String docNo) {
        this.docNo = docNo == null ? null : docNo.trim();
    }


    public String getTabName() {
        return tabName;
    }


    public void setTabName(String tabName) {
        this.tabName = tabName == null ? null : tabName.trim();
    }


    public String getBakName() {
        return bakName;
    }


    public void setBakName(String bakName) {
        this.bakName = bakName == null ? null : bakName.trim();
    }


    public String getInputName() {
        return inputName;
    }


    public void setInputName(String inputName) {
        this.inputName = inputName == null ? null : inputName.trim();
    }


    public String getFinalName() {
        return finalName;
    }


    public void setFinalName(String finalName) {
        this.finalName = finalName == null ? null : finalName.trim();
    }


    public String getRemark() {
        return remark;
    }


    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }


    public String getRemark2() {
        return remark2;
    }


    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }


    public String getRemark3() {
        return remark3;
    }


    public void setRemark3(String remark3) {
        this.remark3 = remark3 == null ? null : remark3.trim();
    }


    public String getRemark4() {
        return remark4;
    }


    public void setRemark4(String remark4) {
        this.remark4 = remark4 == null ? null : remark4.trim();
    }


    public String getRemark5() {
        return remark5;
    }


    public void setRemark5(String remark5) {
        this.remark5 = remark5 == null ? null : remark5.trim();
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }


    public String getLinkowid() {
        return linkowid;
    }


    public void setLinkowid(String linkowid) {
        this.linkowid = linkowid == null ? null : linkowid.trim();
    }


    public String getLinkowid1() {
        return linkowid1;
    }


    public void setLinkowid1(String linkowid1) {
        this.linkowid1 = linkowid1 == null ? null : linkowid1.trim();
    }


    public String getLinkowid2() {
        return linkowid2;
    }


    public void setLinkowid2(String linkowid2) {
        this.linkowid2 = linkowid2 == null ? null : linkowid2.trim();
    }


    public String getLinkowid3() {
        return linkowid3;
    }


    public void setLinkowid3(String linkowid3) {
        this.linkowid3 = linkowid3 == null ? null : linkowid3.trim();
    }


    public String getLinkowid4() {
        return linkowid4;
    }


    public void setLinkowid4(String linkowid4) {
        this.linkowid4 = linkowid4 == null ? null : linkowid4.trim();
    }


    public String getLinkowid5() {
        return linkowid5;
    }


    public void setLinkowid5(String linkowid5) {
        this.linkowid5 = linkowid5 == null ? null : linkowid5.trim();
    }


    public String getRepNo() {
        return repNo;
    }


    public void setRepNo(String repNo) {
        this.repNo = repNo == null ? null : repNo.trim();
    }


    public Date getEffBegindate() {
        return effBegindate;
    }


    public void setEffBegindate(Date effBegindate) {
        this.effBegindate = effBegindate;
    }


    public Date getEffEnddate() {
        return effEnddate;
    }


    public void setEffEnddate(Date effEnddate) {
        this.effEnddate = effEnddate;
    }


    public String getDepCode() {
        return depCode;
    }


    public void setDepCode(String depCode) {
        this.depCode = depCode == null ? null : depCode.trim();
    }


    public Date getCreatetime() {
        return createtime;
    }


    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }


    public String getCreator() {
        return creator;
    }


    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }


    public String getCreatorName() {
        return creatorName;
    }


    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }


    public Date getLastupdate() {
        return lastupdate;
    }


    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }


    public String getUpdator() {
        return updator;
    }


    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }


    public String getUpdatorName() {
        return updatorName;
    }


    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName == null ? null : updatorName.trim();
    }


    public Integer getState() {
        return state;
    }


    public void setState(Integer state) {
        this.state = state;
    }


    public Integer getVer() {
        return ver;
    }


    public void setVer(Integer ver) {
        this.ver = ver;
    }


    public Date getVertime() {
        return vertime;
    }


    public void setVertime(Date vertime) {
        this.vertime = vertime;
    }


    public Integer getDeptId() {
        return deptId;
    }


    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }


    public String getDeptPath() {
        return deptPath;
    }


    public void setDeptPath(String deptPath) {
        this.deptPath = deptPath == null ? null : deptPath.trim();
    }


    public Integer getDelflg() {
        return delflg;
    }


    public void setDelflg(Integer delflg) {
        this.delflg = delflg;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", inputdate=").append(inputdate);
        sb.append(", loaddata=").append(loaddata);
        sb.append(", finaldata=").append(finaldata);
        sb.append(", tableNo=").append(tableNo);
        sb.append(", docNo=").append(docNo);
        sb.append(", tabName=").append(tabName);
        sb.append(", bakName=").append(bakName);
        sb.append(", inputName=").append(inputName);
        sb.append(", finalName=").append(finalName);
        sb.append(", remark=").append(remark);
        sb.append(", remark2=").append(remark2);
        sb.append(", remark3=").append(remark3);
        sb.append(", remark4=").append(remark4);
        sb.append(", remark5=").append(remark5);
        sb.append(", type=").append(type);
        sb.append(", linkowid=").append(linkowid);
        sb.append(", linkowid1=").append(linkowid1);
        sb.append(", linkowid2=").append(linkowid2);
        sb.append(", linkowid3=").append(linkowid3);
        sb.append(", linkowid4=").append(linkowid4);
        sb.append(", linkowid5=").append(linkowid5);
        sb.append(", repNo=").append(repNo);
        sb.append(", effBegindate=").append(effBegindate);
        sb.append(", effEnddate=").append(effEnddate);
        sb.append(", depCode=").append(depCode);
        sb.append(", createtime=").append(createtime);
        sb.append(", creator=").append(creator);
        sb.append(", creatorName=").append(creatorName);
        sb.append(", lastupdate=").append(lastupdate);
        sb.append(", updator=").append(updator);
        sb.append(", updatorName=").append(updatorName);
        sb.append(", state=").append(state);
        sb.append(", ver=").append(ver);
        sb.append(", vertime=").append(vertime);
        sb.append(", deptId=").append(deptId);
        sb.append(", deptPath=").append(deptPath);
        sb.append(", delflg=").append(delflg);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}