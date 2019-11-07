package com.dao.entity.system;

import com.dao.entity.sys.ComColumn;

import java.io.Serializable;
import java.util.Date;

public class SysResource extends ComColumn implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.sort
     *
     * @mbg.generated
     */
    private Integer sort;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.function_id
     *
     * @mbg.generated
     */
    private String functionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.code
     *
     * @mbg.generated
     */
    private String code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.operate_type
     *
     * @mbg.generated
     */
    private Integer operateType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.after_url
     *
     * @mbg.generated
     */
    private String afterUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.route_url
     *
     * @mbg.generated
     */
    private String routeUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.getway
     *
     * @mbg.generated
     */
    private String getway;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.use
     *
     * @mbg.generated
     */
    private String use;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.type
     *
     * @mbg.generated
     */
    private Integer type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.checked
     *
     * @mbg.generated
     */
    private Integer checked;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.component
     *
     * @mbg.generated
     */
    private String component;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.dep_code
     *
     * @mbg.generated
     */
    private String depCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.creator
     *
     * @mbg.generated
     */
    private String creator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.create_date
     *
     * @mbg.generated
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.update_date
     *
     * @mbg.generated
     */
    private Date updateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_resource.remark
     *
     * @mbg.generated
     */
    private String remark;

    private Integer state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_resource
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.id
     *
     * @return the value of sys_resource.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.id
     *
     * @param id the value for sys_resource.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.sort
     *
     * @return the value of sys_resource.sort
     *
     * @mbg.generated
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.sort
     *
     * @param sort the value for sys_resource.sort
     *
     * @mbg.generated
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.function_id
     *
     * @return the value of sys_resource.function_id
     *
     * @mbg.generated
     */
    public String getFunctionId() {
        return functionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.function_id
     *
     * @param functionId the value for sys_resource.function_id
     *
     * @mbg.generated
     */
    public void setFunctionId(String functionId) {
        this.functionId = functionId == null ? null : functionId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.code
     *
     * @return the value of sys_resource.code
     *
     * @mbg.generated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.code
     *
     * @param code the value for sys_resource.code
     *
     * @mbg.generated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.name
     *
     * @return the value of sys_resource.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.name
     *
     * @param name the value for sys_resource.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.operate_type
     *
     * @return the value of sys_resource.operate_type
     *
     * @mbg.generated
     */
    public Integer getOperateType() {
        return operateType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.operate_type
     *
     * @param operateType the value for sys_resource.operate_type
     *
     * @mbg.generated
     */
    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.after_url
     *
     * @return the value of sys_resource.after_url
     *
     * @mbg.generated
     */
    public String getAfterUrl() {
        return afterUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.after_url
     *
     * @param afterUrl the value for sys_resource.after_url
     *
     * @mbg.generated
     */
    public void setAfterUrl(String afterUrl) {
        this.afterUrl = afterUrl == null ? null : afterUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.route_url
     *
     * @return the value of sys_resource.route_url
     *
     * @mbg.generated
     */
    public String getRouteUrl() {
        return routeUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.route_url
     *
     * @param routeUrl the value for sys_resource.route_url
     *
     * @mbg.generated
     */
    public void setRouteUrl(String routeUrl) {
        this.routeUrl = routeUrl == null ? null : routeUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.getway
     *
     * @return the value of sys_resource.getway
     *
     * @mbg.generated
     */
    public String getGetway() {
        return getway;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.getway
     *
     * @param getway the value for sys_resource.getway
     *
     * @mbg.generated
     */
    public void setGetway(String getway) {
        this.getway = getway == null ? null : getway.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.use
     *
     * @return the value of sys_resource.use
     *
     * @mbg.generated
     */
    public String getUse() {
        return use;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.use
     *
     * @param use the value for sys_resource.use
     *
     * @mbg.generated
     */
    public void setUse(String use) {
        this.use = use == null ? null : use.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.type
     *
     * @return the value of sys_resource.type
     *
     * @mbg.generated
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.type
     *
     * @param type the value for sys_resource.type
     *
     * @mbg.generated
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.checked
     *
     * @return the value of sys_resource.checked
     *
     * @mbg.generated
     */
    public Integer getChecked() {
        return checked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.checked
     *
     * @param checked the value for sys_resource.checked
     *
     * @mbg.generated
     */
    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.component
     *
     * @return the value of sys_resource.component
     *
     * @mbg.generated
     */
    public String getComponent() {
        return component;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.component
     *
     * @param component the value for sys_resource.component
     *
     * @mbg.generated
     */
    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.dep_code
     *
     * @return the value of sys_resource.dep_code
     *
     * @mbg.generated
     */
    public String getDepCode() {
        return depCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.dep_code
     *
     * @param depCode the value for sys_resource.dep_code
     *
     * @mbg.generated
     */
    public void setDepCode(String depCode) {
        this.depCode = depCode == null ? null : depCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.creator
     *
     * @return the value of sys_resource.creator
     *
     * @mbg.generated
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.creator
     *
     * @param creator the value for sys_resource.creator
     *
     * @mbg.generated
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.create_date
     *
     * @return the value of sys_resource.create_date
     *
     * @mbg.generated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.create_date
     *
     * @param createDate the value for sys_resource.create_date
     *
     * @mbg.generated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.update_date
     *
     * @return the value of sys_resource.update_date
     *
     * @mbg.generated
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.update_date
     *
     * @param updateDate the value for sys_resource.update_date
     *
     * @mbg.generated
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_resource.remark
     *
     * @return the value of sys_resource.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_resource.remark
     *
     * @param remark the value for sys_resource.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_resource
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
        sb.append(", sort=").append(sort);
        sb.append(", functionId=").append(functionId);
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", operateType=").append(operateType);
        sb.append(", afterUrl=").append(afterUrl);
        sb.append(", routeUrl=").append(routeUrl);
        sb.append(", getway=").append(getway);
        sb.append(", use=").append(use);
        sb.append(", type=").append(type);
        sb.append(", checked=").append(checked);
        sb.append(", component=").append(component);
        sb.append(", depCode=").append(depCode);
        sb.append(", creator=").append(creator);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}