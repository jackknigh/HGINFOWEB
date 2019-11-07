package com.dao.entity.zjstat;

import com.dao.entity.sys.ComColumn;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ZbresBrightpointValue extends ComColumn implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_value.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_value.dep_code
     *
     * @mbg.generated
     */
    private String depCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_value.zb_assess_main_refid
     *
     * @mbg.generated
     */
    private String zbAssessMainRefid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_value.zb_brightpoint_refid
     *
     * @mbg.generated
     */
    private String zbBrightpointRefid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_value.zbvalue
     *
     * @mbg.generated
     */
    private BigDecimal zbvalue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_value.stat_unit
     *
     * @mbg.generated
     */
    private String statUnit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_value.remark2
     *
     * @mbg.generated
     */
    private String remark2;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_value.other_date
     *
     * @mbg.generated
     */
    private Date otherDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_value.remark1
     *
     * @mbg.generated
     */
    private String remark1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_value.zbvalue2
     *
     * @mbg.generated
     */
    private BigDecimal zbvalue2;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_value.zbvalue3
     *
     * @mbg.generated
     */
    private BigDecimal zbvalue3;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_value.stat_date
     *
     * @mbg.generated
     */
    private Date statDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_value.stat_cycle
     *
     * @mbg.generated
     */
    private String statCycle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table zbres_brightpoint_value
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_value.id
     *
     * @return the value of zbres_brightpoint_value.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_value.id
     *
     * @param id the value for zbres_brightpoint_value.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_value.dep_code
     *
     * @return the value of zbres_brightpoint_value.dep_code
     *
     * @mbg.generated
     */
    public String getDepCode() {
        return depCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_value.dep_code
     *
     * @param depCode the value for zbres_brightpoint_value.dep_code
     *
     * @mbg.generated
     */
    public void setDepCode(String depCode) {
        this.depCode = depCode == null ? null : depCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_value.zb_assess_main_refid
     *
     * @return the value of zbres_brightpoint_value.zb_assess_main_refid
     *
     * @mbg.generated
     */
    public String getZbAssessMainRefid() {
        return zbAssessMainRefid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_value.zb_assess_main_refid
     *
     * @param zbAssessMainRefid the value for zbres_brightpoint_value.zb_assess_main_refid
     *
     * @mbg.generated
     */
    public void setZbAssessMainRefid(String zbAssessMainRefid) {
        this.zbAssessMainRefid = zbAssessMainRefid == null ? null : zbAssessMainRefid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_value.zb_brightpoint_refid
     *
     * @return the value of zbres_brightpoint_value.zb_brightpoint_refid
     *
     * @mbg.generated
     */
    public String getZbBrightpointRefid() {
        return zbBrightpointRefid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_value.zb_brightpoint_refid
     *
     * @param zbBrightpointRefid the value for zbres_brightpoint_value.zb_brightpoint_refid
     *
     * @mbg.generated
     */
    public void setZbBrightpointRefid(String zbBrightpointRefid) {
        this.zbBrightpointRefid = zbBrightpointRefid == null ? null : zbBrightpointRefid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_value.zbvalue
     *
     * @return the value of zbres_brightpoint_value.zbvalue
     *
     * @mbg.generated
     */
    public BigDecimal getZbvalue() {
        return zbvalue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_value.zbvalue
     *
     * @param zbvalue the value for zbres_brightpoint_value.zbvalue
     *
     * @mbg.generated
     */
    public void setZbvalue(BigDecimal zbvalue) {
        this.zbvalue = zbvalue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_value.stat_unit
     *
     * @return the value of zbres_brightpoint_value.stat_unit
     *
     * @mbg.generated
     */
    public String getStatUnit() {
        return statUnit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_value.stat_unit
     *
     * @param statUnit the value for zbres_brightpoint_value.stat_unit
     *
     * @mbg.generated
     */
    public void setStatUnit(String statUnit) {
        this.statUnit = statUnit == null ? null : statUnit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_value.remark2
     *
     * @return the value of zbres_brightpoint_value.remark2
     *
     * @mbg.generated
     */
    public String getRemark2() {
        return remark2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_value.remark2
     *
     * @param remark2 the value for zbres_brightpoint_value.remark2
     *
     * @mbg.generated
     */
    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_value.other_date
     *
     * @return the value of zbres_brightpoint_value.other_date
     *
     * @mbg.generated
     */
    public Date getOtherDate() {
        return otherDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_value.other_date
     *
     * @param otherDate the value for zbres_brightpoint_value.other_date
     *
     * @mbg.generated
     */
    public void setOtherDate(Date otherDate) {
        this.otherDate = otherDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_value.remark1
     *
     * @return the value of zbres_brightpoint_value.remark1
     *
     * @mbg.generated
     */
    public String getRemark1() {
        return remark1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_value.remark1
     *
     * @param remark1 the value for zbres_brightpoint_value.remark1
     *
     * @mbg.generated
     */
    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_value.zbvalue2
     *
     * @return the value of zbres_brightpoint_value.zbvalue2
     *
     * @mbg.generated
     */
    public BigDecimal getZbvalue2() {
        return zbvalue2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_value.zbvalue2
     *
     * @param zbvalue2 the value for zbres_brightpoint_value.zbvalue2
     *
     * @mbg.generated
     */
    public void setZbvalue2(BigDecimal zbvalue2) {
        this.zbvalue2 = zbvalue2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_value.zbvalue3
     *
     * @return the value of zbres_brightpoint_value.zbvalue3
     *
     * @mbg.generated
     */
    public BigDecimal getZbvalue3() {
        return zbvalue3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_value.zbvalue3
     *
     * @param zbvalue3 the value for zbres_brightpoint_value.zbvalue3
     *
     * @mbg.generated
     */
    public void setZbvalue3(BigDecimal zbvalue3) {
        this.zbvalue3 = zbvalue3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_value.stat_date
     *
     * @return the value of zbres_brightpoint_value.stat_date
     *
     * @mbg.generated
     */
    public Date getStatDate() {
        return statDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_value.stat_date
     *
     * @param statDate the value for zbres_brightpoint_value.stat_date
     *
     * @mbg.generated
     */
    public void setStatDate(Date statDate) {
        this.statDate = statDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_value.stat_cycle
     *
     * @return the value of zbres_brightpoint_value.stat_cycle
     *
     * @mbg.generated
     */
    public String getStatCycle() {
        return statCycle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_value.stat_cycle
     *
     * @param statCycle the value for zbres_brightpoint_value.stat_cycle
     *
     * @mbg.generated
     */
    public void setStatCycle(String statCycle) {
        this.statCycle = statCycle == null ? null : statCycle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zbres_brightpoint_value
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", depCode=").append(depCode);
        sb.append(", zbAssessMainRefid=").append(zbAssessMainRefid);
        sb.append(", zbBrightpointRefid=").append(zbBrightpointRefid);
        sb.append(", zbvalue=").append(zbvalue);
        sb.append(", statUnit=").append(statUnit);
        sb.append(", remark2=").append(remark2);
        sb.append(", otherDate=").append(otherDate);
        sb.append(", remark1=").append(remark1);
        sb.append(", zbvalue2=").append(zbvalue2);
        sb.append(", zbvalue3=").append(zbvalue3);
        sb.append(", statDate=").append(statDate);
        sb.append(", statCycle=").append(statCycle);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}