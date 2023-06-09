package com.dao.entity.zjstat;

import com.dao.entity.sys.ComColumn;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ZbresBrightpointResource extends ComColumn implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_resource.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_resource.dep_code
     *
     * @mbg.generated
     */
    private String depCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_resource.zb_assess_main_refid
     *
     * @mbg.generated
     */
    private String zbAssessMainRefid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_resource.zb_brightpoint_refid
     *
     * @mbg.generated
     */
    private String zbBrightpointRefid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_resource.source_code
     *
     * @mbg.generated
     */
    private String sourceCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_resource.source_code1
     *
     * @mbg.generated
     */
    private String sourceCode1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_resource.source_code2
     *
     * @mbg.generated
     */
    private String sourceCode2;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_resource.source_system
     *
     * @mbg.generated
     */
    private String sourceSystem;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_resource.zbvalue
     *
     * @mbg.generated
     */
    private BigDecimal zbvalue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_resource.stat_unit
     *
     * @mbg.generated
     */
    private String statUnit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_resource.stat_date
     *
     * @mbg.generated
     */
    private Date statDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_resource.stat_cycle
     *
     * @mbg.generated
     */
    private String statCycle;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_resource.other_date
     *
     * @mbg.generated
     */
    private Date otherDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_resource.remark1
     *
     * @mbg.generated
     */
    private String remark1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zbres_brightpoint_resource.remark2
     *
     * @mbg.generated
     */
    private String remark2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table zbres_brightpoint_resource
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_resource.id
     *
     * @return the value of zbres_brightpoint_resource.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_resource.id
     *
     * @param id the value for zbres_brightpoint_resource.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_resource.dep_code
     *
     * @return the value of zbres_brightpoint_resource.dep_code
     *
     * @mbg.generated
     */
    public String getDepCode() {
        return depCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_resource.dep_code
     *
     * @param depCode the value for zbres_brightpoint_resource.dep_code
     *
     * @mbg.generated
     */
    public void setDepCode(String depCode) {
        this.depCode = depCode == null ? null : depCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_resource.zb_assess_main_refid
     *
     * @return the value of zbres_brightpoint_resource.zb_assess_main_refid
     *
     * @mbg.generated
     */
    public String getZbAssessMainRefid() {
        return zbAssessMainRefid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_resource.zb_assess_main_refid
     *
     * @param zbAssessMainRefid the value for zbres_brightpoint_resource.zb_assess_main_refid
     *
     * @mbg.generated
     */
    public void setZbAssessMainRefid(String zbAssessMainRefid) {
        this.zbAssessMainRefid = zbAssessMainRefid == null ? null : zbAssessMainRefid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_resource.zb_brightpoint_refid
     *
     * @return the value of zbres_brightpoint_resource.zb_brightpoint_refid
     *
     * @mbg.generated
     */
    public String getZbBrightpointRefid() {
        return zbBrightpointRefid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_resource.zb_brightpoint_refid
     *
     * @param zbBrightpointRefid the value for zbres_brightpoint_resource.zb_brightpoint_refid
     *
     * @mbg.generated
     */
    public void setZbBrightpointRefid(String zbBrightpointRefid) {
        this.zbBrightpointRefid = zbBrightpointRefid == null ? null : zbBrightpointRefid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_resource.source_code
     *
     * @return the value of zbres_brightpoint_resource.source_code
     *
     * @mbg.generated
     */
    public String getSourceCode() {
        return sourceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_resource.source_code
     *
     * @param sourceCode the value for zbres_brightpoint_resource.source_code
     *
     * @mbg.generated
     */
    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode == null ? null : sourceCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_resource.source_code1
     *
     * @return the value of zbres_brightpoint_resource.source_code1
     *
     * @mbg.generated
     */
    public String getSourceCode1() {
        return sourceCode1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_resource.source_code1
     *
     * @param sourceCode1 the value for zbres_brightpoint_resource.source_code1
     *
     * @mbg.generated
     */
    public void setSourceCode1(String sourceCode1) {
        this.sourceCode1 = sourceCode1 == null ? null : sourceCode1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_resource.source_code2
     *
     * @return the value of zbres_brightpoint_resource.source_code2
     *
     * @mbg.generated
     */
    public String getSourceCode2() {
        return sourceCode2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_resource.source_code2
     *
     * @param sourceCode2 the value for zbres_brightpoint_resource.source_code2
     *
     * @mbg.generated
     */
    public void setSourceCode2(String sourceCode2) {
        this.sourceCode2 = sourceCode2 == null ? null : sourceCode2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_resource.source_system
     *
     * @return the value of zbres_brightpoint_resource.source_system
     *
     * @mbg.generated
     */
    public String getSourceSystem() {
        return sourceSystem;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_resource.source_system
     *
     * @param sourceSystem the value for zbres_brightpoint_resource.source_system
     *
     * @mbg.generated
     */
    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem == null ? null : sourceSystem.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_resource.zbvalue
     *
     * @return the value of zbres_brightpoint_resource.zbvalue
     *
     * @mbg.generated
     */
    public BigDecimal getZbvalue() {
        return zbvalue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_resource.zbvalue
     *
     * @param zbvalue the value for zbres_brightpoint_resource.zbvalue
     *
     * @mbg.generated
     */
    public void setZbvalue(BigDecimal zbvalue) {
        this.zbvalue = zbvalue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_resource.stat_unit
     *
     * @return the value of zbres_brightpoint_resource.stat_unit
     *
     * @mbg.generated
     */
    public String getStatUnit() {
        return statUnit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_resource.stat_unit
     *
     * @param statUnit the value for zbres_brightpoint_resource.stat_unit
     *
     * @mbg.generated
     */
    public void setStatUnit(String statUnit) {
        this.statUnit = statUnit == null ? null : statUnit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_resource.stat_date
     *
     * @return the value of zbres_brightpoint_resource.stat_date
     *
     * @mbg.generated
     */
    public Date getStatDate() {
        return statDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_resource.stat_date
     *
     * @param statDate the value for zbres_brightpoint_resource.stat_date
     *
     * @mbg.generated
     */
    public void setStatDate(Date statDate) {
        this.statDate = statDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_resource.stat_cycle
     *
     * @return the value of zbres_brightpoint_resource.stat_cycle
     *
     * @mbg.generated
     */
    public String getStatCycle() {
        return statCycle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_resource.stat_cycle
     *
     * @param statCycle the value for zbres_brightpoint_resource.stat_cycle
     *
     * @mbg.generated
     */
    public void setStatCycle(String statCycle) {
        this.statCycle = statCycle == null ? null : statCycle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_resource.other_date
     *
     * @return the value of zbres_brightpoint_resource.other_date
     *
     * @mbg.generated
     */
    public Date getOtherDate() {
        return otherDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_resource.other_date
     *
     * @param otherDate the value for zbres_brightpoint_resource.other_date
     *
     * @mbg.generated
     */
    public void setOtherDate(Date otherDate) {
        this.otherDate = otherDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_resource.remark1
     *
     * @return the value of zbres_brightpoint_resource.remark1
     *
     * @mbg.generated
     */
    public String getRemark1() {
        return remark1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_resource.remark1
     *
     * @param remark1 the value for zbres_brightpoint_resource.remark1
     *
     * @mbg.generated
     */
    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zbres_brightpoint_resource.remark2
     *
     * @return the value of zbres_brightpoint_resource.remark2
     *
     * @mbg.generated
     */
    public String getRemark2() {
        return remark2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zbres_brightpoint_resource.remark2
     *
     * @param remark2 the value for zbres_brightpoint_resource.remark2
     *
     * @mbg.generated
     */
    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zbres_brightpoint_resource
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
        sb.append(", sourceCode=").append(sourceCode);
        sb.append(", sourceCode1=").append(sourceCode1);
        sb.append(", sourceCode2=").append(sourceCode2);
        sb.append(", sourceSystem=").append(sourceSystem);
        sb.append(", zbvalue=").append(zbvalue);
        sb.append(", statUnit=").append(statUnit);
        sb.append(", statDate=").append(statDate);
        sb.append(", statCycle=").append(statCycle);
        sb.append(", otherDate=").append(otherDate);
        sb.append(", remark1=").append(remark1);
        sb.append(", remark2=").append(remark2);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}