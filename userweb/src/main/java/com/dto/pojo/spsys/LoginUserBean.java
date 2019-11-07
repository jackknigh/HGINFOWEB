package com.dto.pojo.spsys;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @version version 1.0
 * @ClassName Test
 * @Description: 〈用户信息和角色信息模型〉
 * @date 2018/10/9
 */
public class LoginUserBean implements Serializable {

    private static final long serialVersionUID = 4249582445587190134L;
    /**
     * 用户AccountID
     */
    private String accountId;
    /**
     * 帐套名称
     */
    private String accountName;
    /**
     * 用户ID
     */
    private String id;
    /**
     * 用户nic
     */
    private String nic;

    /**
     * 密码
     */
    private byte[] password;

    private String PasswordEnc;
    /**
     * 权限
     */
    private byte[] popedom;
    /**
     * 用户名
     */
    private String name;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 用户使用状态 0激活  1注销  2 休眠
     */
    private BigDecimal accountIsDisabled;

    /**
     * 部门名称
     */
    private String departName;

    /**
     * 注册日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date registrationDate;

    /**
     * 修改密码时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date passwordModifyDate;

    /**
     * 账户修改标识
     */
    private BigDecimal passwordChangeFlag;

    /**
     * 角色ID，多个时使用|分割
     */
    private String roleId;


    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色类型
     */
    private String roleType;

    /**
     * 主所属部门ID
     */
    private String depCode;

    /**
     * 多部门情况下其他不部门,使用|分割
     */
    private String depCodes;

    /**
     * 所属部门名称
     */
    private String orgName;
    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    public String getPasswordEnc() {
        return PasswordEnc;
    }

    public void setPasswordEnc(String passwordEnc) {
        PasswordEnc = passwordEnc;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public byte[] getPopedom() {
        return popedom;
    }

    public void setPopedom(byte[] popedom) {
        this.popedom = popedom;
    }

    public BigDecimal getPasswordChangeFlag() {
        return passwordChangeFlag;
    }

    public void setPasswordChangeFlag(BigDecimal passwordChangeFlag) {
        this.passwordChangeFlag = passwordChangeFlag;
    }

    public Date getPasswordModifyDate() {
        return passwordModifyDate;
    }

    public void setPasswordModifyDate(Date passwordModifyDate) {
        this.passwordModifyDate = passwordModifyDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getAccountIsDisabled() {
        return accountIsDisabled;
    }

    public void setAccountIsDisabled(BigDecimal accountIsDisabled) {
        this.accountIsDisabled = accountIsDisabled;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getDepCodes() {
        return depCodes;
    }

    public void setDepCodes(String depCodes) {
        this.depCodes = depCodes;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }
}
