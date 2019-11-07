package com.spinfosec.system;

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

	/**
	 * 加密解密
	 */
	ENC_DEC_FIELD("006", "AESPython Encrypt password or Decrypt password failed"),

	/**
	 * 对象已被使用
	 */
	OBJECT_IS_USED("007", "object is used"),

	/**
	 * 密码不存在
	 */
	PASSWORD_CAN_NOT_BE_NULL("008", "密码不能为空！"),

	/**
	 * 未授权或授权失败
	 */
	ERROR_LICENSE("009", "error license."),

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

	/**
	 * 账号处于休眠状态
	 */
	ACCOUNT_IS_SLEEP("118", "账号处于休眠状态，请联系管理员！"),

	/**
	 * 当前账号已被注销
	 */
	ACCOUNT_IS_CANCEL("176", "当前账号已被注销！"),

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
	 * 上传失败
	 */
	FILE_UPLOAD_ERROR("205", "file upload error"),

	/**
	 * 删除失败
	 */
	DELETE_FAILED("206", "Delete failed"),

	/**
	 * 无效的文件类型
	 */
	INVALID_FILE_TYPE("207", "无效的文件类型！"),

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
	 * Exchange连接失败
	 */
	DOMAIN_CONNECT_FAIL("307", "域控链接失败，请检查认证设置是否正确。"),

	// --------------------------------License许可类代码定义--------------------------------//
	/**
	 * License安装失败
	 */
	LICENSE_INSTALL_FAIL("401", "License安装失败！"),

	/**
	 * License验证失败
	 */
	LICENSE_VERIFY_FAIL("402", "License验证失败！"),

	/**
	 * License不在有效期内
	 */
	LICENSE_OUT_OF_DATE("403", "License不在有效期内！"),

	/**
	 * License证书不存在
	 */
	LICENSE_NOT_EXIST("404", "License证书不存在！"),

	/**
	 * 许可更新成功，请重新分配权限
	 */
	LICENSE_PRODUCT_CHANGE("405", "许可更新成功，请重新分配权限！"),

	/**
	 * License与当前机器不匹配
	 */
	LICENSE_NOT_MATCH_MACHINE("406", "License与当前机器不匹配"),

	// --------------------------------扫描任务通信返回状态码定义-----------------------------//

	/**
	 * 消息通信错误(JMSException统一抛出)
	 */
	MQ_CONNECTION_ERROR("501", "通信错误,请检查网络连接"),

	/**
	 * 消息通信错误(JMSException统一抛出)
	 */
	MQ_MESSAGE_NULL("502", "消息为空!"),

	/**
	 * 消息通信错误(JMSException统一抛出)
	 */
	MQ_QUEUE_NULL("503", "目标队列为空!"),

	// --------------------------------策略任务操作类代码定义--------------------------------//

	/**
	 * 策略操作状态
	 */
	JOB_SUCCESS("600", "操作成功！"),

	/**
	 * 部署策略失败
	 */
	DEPLOY_POLICY_FAILURE("601", "部署策略失败！"),

	/**
	 * 任务操作失败
	 */
	JOB_FAILURE("602", "操作失败！");

	/**
	 * 状态码
	 */
	private final String code;
	/**
	 * 状态描述
	 */
	private String description;

	private RspCode(String code, String description)
	{
		this.code = code;
		this.description = description;
	}

	public String getCode()
	{
		return code;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(RspCode rspCode, String description)
	{
		rspCode.description = description;
	}

	/**
	 * @Title: getRspCodeByCode
	 * @Description: TODO(根据状态码返回错误码对象)
	 * @param: @param code 传入状态码
	 * @param: @return 返回RspCode对象
	 * @return: RspCode 返回RspCode对象
	 * @throws
	 */
	public static RspCode getRspCodeByCode(String code)
	{
		EnumSet<RspCode> enumSet = EnumSet.allOf(RspCode.class);
		for (RspCode rspCode : enumSet)
		{
			if (StringUtils.isNotEmpty(code) && code.equals(rspCode.getCode()))
			{
				return rspCode;
			}
		}
		return null;
	}

	@Override
	public String toString()
	{
		return code + ": " + description;
	}

	/**
	 * @Title: toJson
	 * @Description: TODO(将对象转为json)
	 * @param: @return 返回json字符串
	 * @return: String 返回json字符窜
	 * @throws
	 */
	public String toJson()
	{
		return "{\"code\":\"" + code + "\",\"msg\":\"" + description + "\"}";
	}
}
