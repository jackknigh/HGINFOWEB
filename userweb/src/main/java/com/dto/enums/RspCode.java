package com.dto.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;

/**
 * @title [错误码定义]
 * @description [一句话描述]
 * @copyright Copyright 2013 SHIPING INFO Corporation. All rights reserved.
 * @company SHIPING INFO.
 * @author Caspar Du
 * @version v 1.0
 * @create 2013-6-9 下午10:53:28
 */
public enum RspCode
{
	// --------------------------------ERPSYS--------------------------------//
	AUDIT_OPERATOR_ERROR("erpsys00001", "审核操作员与当前操作员不匹配！"),

	AUTH_OPERATOR_ERROR("erpsys00002", "您无操作此功能的权限！"),


	// --------------------------------系统类代码定义--------------------------------//
	/**
	 * 操作成功
	 */
	SUCCESS("000", "OK"),

	/**
	 * 操作失败
	 */
	FAILURE("111", "failure"),

	/**
	 * 内部错误
	 */
	INNER_ERROR("001", "内部错误。"),

	/**
	 * 参数错误
	 */
	PARAMERTER_ERROR("002", "参数错误。"),

	/**
	 * 重名校验失败
	 */
	DUPLICATION_ERROR("003", "重名校验失败！"),

	/**
	 * 不能删除默认
	 */
	DEFAULT_CAN_NOT_DELETE("004", "can not delete default."),

	/**
	 * 对象不存在
	 */
	OBJECE_NOT_EXIST("005", "the object not exists."),

	// --------------------------------登陆相关--------------------------------//
	SYS_AUTH_ERROR("sys001", "验证码错误!"),
	SYS_AUTH_TIMEOUT("sys002", "验证码已过期，重新获取!"),
	SYS_AUTH_ENCRYPT("sys003", "解密密码错误!"),
	SYS_AUTH_ENCRYPT_TIMEOUT("sys004", "密钥过期!"),
	// --------------------------------用户类代码定义--------------------------------//

	/**
	 * session未初始化或超时
	 */
	INVALID_SESSION("101", "Invalid session."),

	/**
	 * 用户不存在
	 */
	ACCOUNT_NOT_EXIST("102", "account is not exist."),

	/**
	 * 登录失败
	 */
	LOGIN_FAILED("103", "login failed"),

	/**
	 * 用户密码错误
	 */
	FIND_PASSWORD_ERROR("104", "find user password error."),

	/**
	 * 旧密码错误
	 */
	OLD_PASSWORD_ERROR("105", "旧密码错误."),

	/**
	 * 显示验证码
	 */
	SHOW_AUTH_CODE("106", "true"),

	/**
	 * 隐藏验证码
	 */
	HIDE_AUTH_CODE("107", "false"),

	/**
	 * 密码复杂度验证失败
	 */
	PASSWORD_COMPLEXITY_VALID_FAIL("108", "密码复杂度验证失败！"),

	/**
	 * 预制用户不能删除
	 */
	USER_PRE_DEFINE_NO_DELETING("109", "系统预置用户不能删除"),

	/**
	 * 鉴权超时或未登录
	 */
	INVALID_TOKENID("110", "Invalid tokenId."),

	/**
	 * 账户过期
	 */
	ACCOUNT_OUT_OF_DATE("112", "账户已过期，请联系管理员！"),

	/**
	 * 非法地址登录
	 */
	INVALID_IP("113", "非法IP登录，请使用信任主机登录本系统！"),

	/**
	 * 密码已过期，请修改密码
	 */
	PASSWORD_OUT_OF_DATE("114", "密码已过期，请修改密码！"),

	/**
	 * 密码即将过期，请修改密码
	 */
	PASSWORD_WILL_OUT_OF_DATE("115", "密码即将过期，请修改密码！"),

	/**
	 * 首次登陆请修改密码
	 */
	MODIFY_PASSWORD_FIRST("116", "首次登陆请修改密码！"),

	/**
	 * 当前用户已经登录
	 */
	USER_LOGIN_AGING("117", "当前用户已经登录"),

	PKFAILURE("118", "主键冲突"),
	API_AUTH_AILURE("119", "API校验失败"),
	CANNOT_DELETE("120", "不允许删除"),
	JSONERRPR("121", "JSON参数转换失败"),
	// --------------------------------文件类代码定义--------------------------------//

	/**
	 * 导出文件失败
	 */
	EXPORT_FILE_ERROR("201", "export file error."),

	/**
	 * 文件不存在
	 */
	FILE_NOT_EXIST("202", "file not exist."),

	/**
	 * 归档开始时间不能大于结束时间
	 */
	LOG_ARCHIVE_DATE_INVALID("203", "归档开始时间不能大于结束时间"),

	/**
	 * 数据恢复失败
	 */
	RECOVER_BACKUP_ERROR("204", "recover backup error."),

	/**
	 * 字典添加重复
	 */
	DIC_CODE_FAILURE("205", "添加失败，字典编号已存在"),


	// --------------------------------测试连接类代码定义--------------------------------//

	/**
	 * 连接成功
	 */
	CONNECT_SUCCESS("300", "连接成功!"),

	/**
	 * 连接失败
	 */
	CONNECT_ERROR("301", "连接失败！"),

	/**
	 * 连接失败,用户名或密码不正确
	 */
	CONNECT_AUTH_ERROR("302", "连接失败,用户名或密码不正确！"),
	/**
	 * 连接失败,服务器端口不正确
	 */
	CONNECT_PORT_ERROR("303", "连接失败,服务器地址或端口不正确！"),

	/**
	 * 测试连接错误码定义 连接失败,网络路径不可用
	 */
	CONNECT_PATH_ERROR("304", "连接失败,网络路径不可用！"),
	/**
	 * 连接失败,服务器地址不正确
	 */
	CONNECT_HOST_ERROR("305", "连接失败,服务器地址不正确！"),

	/**
	 * 连接失败,邮箱地址或密码不正确
	 */
	CONNECT_EMAIL_AUTH_ERROR("306", "连接失败,邮箱地址或密码不正确！"),
	/**
	 * 原密码错误
	 */
	OLDPWD_AUTH_ERROR("307", "原密码错误！"),
	/**
	 * 帐套编号不正确
	 */
	ACCOUNT_AUTH_ERROR("308", "连接失败,帐套编号校验不正确！"),

	//-----------------------------校验类------------------
	/**
	 * 当前用户名已存在
	 */
	USER_EXIST("401", "当前用户名已存在"),

	/**
	 * 当前机构编码已存在
	 */
	DEPT_EXIST("402", "当前机构编码已存在"),

	/**
	 * 当前资源编号已存在
	 */
	SOURCE_EXIST("403", "当前资源编号已存在"),

	// --------------------------------策略任务操作类代码定义--------------------------------//
	ROLE_USED("405", "角色已经被使用"),
	/**
	 * 策略操作状态
	 */
	JOB_SUCCESS("510000", "操作成功！");


	/**
	 * 状态码
	 */
	private final String code;
	/**
	 * 状态描述
	 */
	private String description;

	private RspCode(String code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * @throws
	 * @Title: getRspCodeByCode
	 * @Description: TODO(根据状态码返回错误码对象)
	 * @param: @param code 传入状态码
	 * @param: @return 返回RspCode对象
	 * @return: RspCode 返回RspCode对象
	 */
	public static RspCode getRspCodeByCode(String code) {
		EnumSet<RspCode> enumSet = EnumSet.allOf(RspCode.class);
		for (RspCode rspCode : enumSet) {
			if (StringUtils.isNotEmpty(code) && code.equals(rspCode.getCode())) {
				return rspCode;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(RspCode rspCode, String description) {
		rspCode.description = description;
	}

	@Override
	public String toString() {
		return code + ": " + description;
	}

	/**
	 * @throws
	 * @Title: toJson
	 * @Description: TODO(将对象转为json)
	 * @param: @return 返回json字符串
	 * @return: String 返回json字符窜
	 */
	public String toJson() {
		return "{\"code\":\"" + code + "\",\"msg\":\"" + description + "\"}";
	}
}
