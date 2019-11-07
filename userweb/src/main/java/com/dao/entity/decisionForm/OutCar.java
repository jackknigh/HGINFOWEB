package com.dao.entity.decisionForm;

import java.math.BigDecimal;
import java.util.Date;

public class OutCar extends OutCarKey {
    private String outcarid;

    private Date weighdate;

    private String loadometerid;

    private String mainorderid;

    private String opennoteid;

    private String guardid;

    private String carno;

    private String drivername;

    private String drivecardno;

    private String unitname;

    private BigDecimal loadweight;

    private String tankid;

    private String especialrequest;

    private BigDecimal weight1;

    private String weighman1;

    private Date weighttime1;

    private BigDecimal weight2;

    private String weighman2;

    private Date weighttime2;

    private BigDecimal netweight;

    private BigDecimal outadjust;

    private BigDecimal sendadjust;

    private String blno;

    private BigDecimal sendnumber;

    private BigDecimal number;

    private String leadsealno;

    private BigDecimal leadsealnumber;

    private BigDecimal onnumber;

    private BigDecimal undernumber;

    private String loader;

    private Date openpumptime;

    private Date closepumptime;

    private Date begintime;

    private Date endtime;

    private Integer noteway;

    private Date createdate;

    private String operatorid;

    private Integer state;

    private String remark1;

    private String remark2;

    private BigDecimal radarnumber1;

    private BigDecimal radarnumber2;

    private String receiptid;

    private String checkid;

    private String remark3;

    private BigDecimal flowmeternum;

    private String craneplace;

    public String getOutcarid() {
        return outcarid;
    }

    public void setOutcarid(String outcarid) {
        this.outcarid = outcarid == null ? null : outcarid.trim();
    }

    public Date getWeighdate() {
        return weighdate;
    }

    public void setWeighdate(Date weighdate) {
        this.weighdate = weighdate;
    }

    public String getLoadometerid() {
        return loadometerid;
    }

    public void setLoadometerid(String loadometerid) {
        this.loadometerid = loadometerid == null ? null : loadometerid.trim();
    }

    public String getMainorderid() {
        return mainorderid;
    }

    public void setMainorderid(String mainorderid) {
        this.mainorderid = mainorderid == null ? null : mainorderid.trim();
    }

    public String getOpennoteid() {
        return opennoteid;
    }

    public void setOpennoteid(String opennoteid) {
        this.opennoteid = opennoteid == null ? null : opennoteid.trim();
    }

    public String getGuardid() {
        return guardid;
    }

    public void setGuardid(String guardid) {
        this.guardid = guardid == null ? null : guardid.trim();
    }

    public String getCarno() {
        return carno;
    }

    public void setCarno(String carno) {
        this.carno = carno == null ? null : carno.trim();
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername == null ? null : drivername.trim();
    }

    public String getDrivecardno() {
        return drivecardno;
    }

    public void setDrivecardno(String drivecardno) {
        this.drivecardno = drivecardno == null ? null : drivecardno.trim();
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname == null ? null : unitname.trim();
    }

    public BigDecimal getLoadweight() {
        return loadweight;
    }

    public void setLoadweight(BigDecimal loadweight) {
        this.loadweight = loadweight;
    }

    public String getTankid() {
        return tankid;
    }

    public void setTankid(String tankid) {
        this.tankid = tankid == null ? null : tankid.trim();
    }

    public String getEspecialrequest() {
        return especialrequest;
    }

    public void setEspecialrequest(String especialrequest) {
        this.especialrequest = especialrequest == null ? null : especialrequest.trim();
    }

    public BigDecimal getWeight1() {
        return weight1;
    }

    public void setWeight1(BigDecimal weight1) {
        this.weight1 = weight1;
    }

    public String getWeighman1() {
        return weighman1;
    }

    public void setWeighman1(String weighman1) {
        this.weighman1 = weighman1 == null ? null : weighman1.trim();
    }

    public Date getWeighttime1() {
        return weighttime1;
    }

    public void setWeighttime1(Date weighttime1) {
        this.weighttime1 = weighttime1;
    }

    public BigDecimal getWeight2() {
        return weight2;
    }

    public void setWeight2(BigDecimal weight2) {
        this.weight2 = weight2;
    }

    public String getWeighman2() {
        return weighman2;
    }

    public void setWeighman2(String weighman2) {
        this.weighman2 = weighman2 == null ? null : weighman2.trim();
    }

    public Date getWeighttime2() {
        return weighttime2;
    }

    public void setWeighttime2(Date weighttime2) {
        this.weighttime2 = weighttime2;
    }

    public BigDecimal getNetweight() {
        return netweight;
    }

    public void setNetweight(BigDecimal netweight) {
        this.netweight = netweight;
    }

    public BigDecimal getOutadjust() {
        return outadjust;
    }

    public void setOutadjust(BigDecimal outadjust) {
        this.outadjust = outadjust;
    }

    public BigDecimal getSendadjust() {
        return sendadjust;
    }

    public void setSendadjust(BigDecimal sendadjust) {
        this.sendadjust = sendadjust;
    }

    public String getBlno() {
        return blno;
    }

    public void setBlno(String blno) {
        this.blno = blno == null ? null : blno.trim();
    }

    public BigDecimal getSendnumber() {
        return sendnumber;
    }

    public void setSendnumber(BigDecimal sendnumber) {
        this.sendnumber = sendnumber;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public String getLeadsealno() {
        return leadsealno;
    }

    public void setLeadsealno(String leadsealno) {
        this.leadsealno = leadsealno == null ? null : leadsealno.trim();
    }

    public BigDecimal getLeadsealnumber() {
        return leadsealnumber;
    }

    public void setLeadsealnumber(BigDecimal leadsealnumber) {
        this.leadsealnumber = leadsealnumber;
    }

    public BigDecimal getOnnumber() {
        return onnumber;
    }

    public void setOnnumber(BigDecimal onnumber) {
        this.onnumber = onnumber;
    }

    public BigDecimal getUndernumber() {
        return undernumber;
    }

    public void setUndernumber(BigDecimal undernumber) {
        this.undernumber = undernumber;
    }

    public String getLoader() {
        return loader;
    }

    public void setLoader(String loader) {
        this.loader = loader == null ? null : loader.trim();
    }

    public Date getOpenpumptime() {
        return openpumptime;
    }

    public void setOpenpumptime(Date openpumptime) {
        this.openpumptime = openpumptime;
    }

    public Date getClosepumptime() {
        return closepumptime;
    }

    public void setClosepumptime(Date closepumptime) {
        this.closepumptime = closepumptime;
    }

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getNoteway() {
        return noteway;
    }

    public void setNoteway(Integer noteway) {
        this.noteway = noteway;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid == null ? null : operatorid.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    public BigDecimal getRadarnumber1() {
        return radarnumber1;
    }

    public void setRadarnumber1(BigDecimal radarnumber1) {
        this.radarnumber1 = radarnumber1;
    }

    public BigDecimal getRadarnumber2() {
        return radarnumber2;
    }

    public void setRadarnumber2(BigDecimal radarnumber2) {
        this.radarnumber2 = radarnumber2;
    }

    public String getReceiptid() {
        return receiptid;
    }

    public void setReceiptid(String receiptid) {
        this.receiptid = receiptid == null ? null : receiptid.trim();
    }

    public String getCheckid() {
        return checkid;
    }

    public void setCheckid(String checkid) {
        this.checkid = checkid == null ? null : checkid.trim();
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3 == null ? null : remark3.trim();
    }

    public BigDecimal getFlowmeternum() {
        return flowmeternum;
    }

    public void setFlowmeternum(BigDecimal flowmeternum) {
        this.flowmeternum = flowmeternum;
    }

    public String getCraneplace() {
        return craneplace;
    }

    public void setCraneplace(String craneplace) {
        this.craneplace = craneplace == null ? null : craneplace.trim();
    }
}