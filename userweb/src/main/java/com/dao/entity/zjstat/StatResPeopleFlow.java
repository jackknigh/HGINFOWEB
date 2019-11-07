package com.dao.entity.zjstat;

import java.io.Serializable;
import java.util.Date;

public class StatResPeopleFlow implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.dep_code
     *
     * @mbg.generated
     */
    private String depCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.sfzh
     *
     * @mbg.generated
     */
    private String sfzh;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.people_type
     *
     * @mbg.generated
     */
    private String peopleType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.source_dep_code
     *
     * @mbg.generated
     */
    private String sourceDepCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.from_dep_code
     *
     * @mbg.generated
     */
    private String fromDepCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.to_dep_code
     *
     * @mbg.generated
     */
    private String toDepCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.in_out_flg
     *
     * @mbg.generated
     */
    private Integer inOutFlg;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.in_out_num
     *
     * @mbg.generated
     */
    private Integer inOutNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.in_out_type1
     *
     * @mbg.generated
     */
    private String inOutType1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.in_out_type2
     *
     * @mbg.generated
     */
    private String inOutType2;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.in_out_type3
     *
     * @mbg.generated
     */
    private String inOutType3;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.in_out_time
     *
     * @mbg.generated
     */
    private Date inOutTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.in_time
     *
     * @mbg.generated
     */
    private Date inTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.out_time
     *
     * @mbg.generated
     */
    private Date outTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.source_code
     *
     * @mbg.generated
     */
    private String sourceCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.sync_time
     *
     * @mbg.generated
     */
    private Date syncTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.source_system
     *
     * @mbg.generated
     */
    private String sourceSystem;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.remark1
     *
     * @mbg.generated
     */
    private String remark1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stat_res_people_flow.remark2
     *
     * @mbg.generated
     */
    private String remark2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table stat_res_people_flow
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.id
     *
     * @return the value of stat_res_people_flow.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.id
     *
     * @param id the value for stat_res_people_flow.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.dep_code
     *
     * @return the value of stat_res_people_flow.dep_code
     *
     * @mbg.generated
     */
    public String getDepCode() {
        return depCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.dep_code
     *
     * @param depCode the value for stat_res_people_flow.dep_code
     *
     * @mbg.generated
     */
    public void setDepCode(String depCode) {
        this.depCode = depCode == null ? null : depCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.sfzh
     *
     * @return the value of stat_res_people_flow.sfzh
     *
     * @mbg.generated
     */
    public String getSfzh() {
        return sfzh;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.sfzh
     *
     * @param sfzh the value for stat_res_people_flow.sfzh
     *
     * @mbg.generated
     */
    public void setSfzh(String sfzh) {
        this.sfzh = sfzh == null ? null : sfzh.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.people_type
     *
     * @return the value of stat_res_people_flow.people_type
     *
     * @mbg.generated
     */
    public String getPeopleType() {
        return peopleType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.people_type
     *
     * @param peopleType the value for stat_res_people_flow.people_type
     *
     * @mbg.generated
     */
    public void setPeopleType(String peopleType) {
        this.peopleType = peopleType == null ? null : peopleType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.source_dep_code
     *
     * @return the value of stat_res_people_flow.source_dep_code
     *
     * @mbg.generated
     */
    public String getSourceDepCode() {
        return sourceDepCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.source_dep_code
     *
     * @param sourceDepCode the value for stat_res_people_flow.source_dep_code
     *
     * @mbg.generated
     */
    public void setSourceDepCode(String sourceDepCode) {
        this.sourceDepCode = sourceDepCode == null ? null : sourceDepCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.from_dep_code
     *
     * @return the value of stat_res_people_flow.from_dep_code
     *
     * @mbg.generated
     */
    public String getFromDepCode() {
        return fromDepCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.from_dep_code
     *
     * @param fromDepCode the value for stat_res_people_flow.from_dep_code
     *
     * @mbg.generated
     */
    public void setFromDepCode(String fromDepCode) {
        this.fromDepCode = fromDepCode == null ? null : fromDepCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.to_dep_code
     *
     * @return the value of stat_res_people_flow.to_dep_code
     *
     * @mbg.generated
     */
    public String getToDepCode() {
        return toDepCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.to_dep_code
     *
     * @param toDepCode the value for stat_res_people_flow.to_dep_code
     *
     * @mbg.generated
     */
    public void setToDepCode(String toDepCode) {
        this.toDepCode = toDepCode == null ? null : toDepCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.in_out_flg
     *
     * @return the value of stat_res_people_flow.in_out_flg
     *
     * @mbg.generated
     */
    public Integer getInOutFlg() {
        return inOutFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.in_out_flg
     *
     * @param inOutFlg the value for stat_res_people_flow.in_out_flg
     *
     * @mbg.generated
     */
    public void setInOutFlg(Integer inOutFlg) {
        this.inOutFlg = inOutFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.in_out_num
     *
     * @return the value of stat_res_people_flow.in_out_num
     *
     * @mbg.generated
     */
    public Integer getInOutNum() {
        return inOutNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.in_out_num
     *
     * @param inOutNum the value for stat_res_people_flow.in_out_num
     *
     * @mbg.generated
     */
    public void setInOutNum(Integer inOutNum) {
        this.inOutNum = inOutNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.in_out_type1
     *
     * @return the value of stat_res_people_flow.in_out_type1
     *
     * @mbg.generated
     */
    public String getInOutType1() {
        return inOutType1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.in_out_type1
     *
     * @param inOutType1 the value for stat_res_people_flow.in_out_type1
     *
     * @mbg.generated
     */
    public void setInOutType1(String inOutType1) {
        this.inOutType1 = inOutType1 == null ? null : inOutType1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.in_out_type2
     *
     * @return the value of stat_res_people_flow.in_out_type2
     *
     * @mbg.generated
     */
    public String getInOutType2() {
        return inOutType2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.in_out_type2
     *
     * @param inOutType2 the value for stat_res_people_flow.in_out_type2
     *
     * @mbg.generated
     */
    public void setInOutType2(String inOutType2) {
        this.inOutType2 = inOutType2 == null ? null : inOutType2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.in_out_type3
     *
     * @return the value of stat_res_people_flow.in_out_type3
     *
     * @mbg.generated
     */
    public String getInOutType3() {
        return inOutType3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.in_out_type3
     *
     * @param inOutType3 the value for stat_res_people_flow.in_out_type3
     *
     * @mbg.generated
     */
    public void setInOutType3(String inOutType3) {
        this.inOutType3 = inOutType3 == null ? null : inOutType3.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.in_out_time
     *
     * @return the value of stat_res_people_flow.in_out_time
     *
     * @mbg.generated
     */
    public Date getInOutTime() {
        return inOutTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.in_out_time
     *
     * @param inOutTime the value for stat_res_people_flow.in_out_time
     *
     * @mbg.generated
     */
    public void setInOutTime(Date inOutTime) {
        this.inOutTime = inOutTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.in_time
     *
     * @return the value of stat_res_people_flow.in_time
     *
     * @mbg.generated
     */
    public Date getInTime() {
        return inTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.in_time
     *
     * @param inTime the value for stat_res_people_flow.in_time
     *
     * @mbg.generated
     */
    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.out_time
     *
     * @return the value of stat_res_people_flow.out_time
     *
     * @mbg.generated
     */
    public Date getOutTime() {
        return outTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.out_time
     *
     * @param outTime the value for stat_res_people_flow.out_time
     *
     * @mbg.generated
     */
    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.source_code
     *
     * @return the value of stat_res_people_flow.source_code
     *
     * @mbg.generated
     */
    public String getSourceCode() {
        return sourceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.source_code
     *
     * @param sourceCode the value for stat_res_people_flow.source_code
     *
     * @mbg.generated
     */
    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode == null ? null : sourceCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.sync_time
     *
     * @return the value of stat_res_people_flow.sync_time
     *
     * @mbg.generated
     */
    public Date getSyncTime() {
        return syncTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.sync_time
     *
     * @param syncTime the value for stat_res_people_flow.sync_time
     *
     * @mbg.generated
     */
    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.source_system
     *
     * @return the value of stat_res_people_flow.source_system
     *
     * @mbg.generated
     */
    public String getSourceSystem() {
        return sourceSystem;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.source_system
     *
     * @param sourceSystem the value for stat_res_people_flow.source_system
     *
     * @mbg.generated
     */
    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem == null ? null : sourceSystem.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.remark1
     *
     * @return the value of stat_res_people_flow.remark1
     *
     * @mbg.generated
     */
    public String getRemark1() {
        return remark1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.remark1
     *
     * @param remark1 the value for stat_res_people_flow.remark1
     *
     * @mbg.generated
     */
    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stat_res_people_flow.remark2
     *
     * @return the value of stat_res_people_flow.remark2
     *
     * @mbg.generated
     */
    public String getRemark2() {
        return remark2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stat_res_people_flow.remark2
     *
     * @param remark2 the value for stat_res_people_flow.remark2
     *
     * @mbg.generated
     */
    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_res_people_flow
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
        sb.append(", sfzh=").append(sfzh);
        sb.append(", peopleType=").append(peopleType);
        sb.append(", sourceDepCode=").append(sourceDepCode);
        sb.append(", fromDepCode=").append(fromDepCode);
        sb.append(", toDepCode=").append(toDepCode);
        sb.append(", inOutFlg=").append(inOutFlg);
        sb.append(", inOutNum=").append(inOutNum);
        sb.append(", inOutType1=").append(inOutType1);
        sb.append(", inOutType2=").append(inOutType2);
        sb.append(", inOutType3=").append(inOutType3);
        sb.append(", inOutTime=").append(inOutTime);
        sb.append(", inTime=").append(inTime);
        sb.append(", outTime=").append(outTime);
        sb.append(", sourceCode=").append(sourceCode);
        sb.append(", syncTime=").append(syncTime);
        sb.append(", sourceSystem=").append(sourceSystem);
        sb.append(", remark1=").append(remark1);
        sb.append(", remark2=").append(remark2);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}