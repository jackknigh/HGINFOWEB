package com.spinfosec.system;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Caspar Du
 * @version v 1.0
 * @title [内存静态信息]
 * @description [一句话描述]
 * @copyright Copyright 2013 SHIPING INFO Corporation. All rights reserved.
 * @company SHIPING INFO.
 * @create 2013-6-9 下午10:53:43
 */
public class MemInfo
{
	/**
	 * 用户登录失败信息
	 */
	private static Map<String, LoginFailedInfo> loginFailedMap = new HashMap<String, LoginFailedInfo>();

	/**
	 * 邮件配置信息
	 */
	private static Map<String, String> emailInfo = new HashMap<String, String>();

	/**
	 * 密码安全策略
	 */
	private static Map<String, String> secPasswordPolicyInfo = new HashMap<String, String>();

	/**
	 * 工程上下文路径
	 */
	private static String servletContextPath;

	/**
	 * 工程
	 */
	private static ServletContext servletContext;

    /**
     * 操作日志信息对象
     */
	private static JSONObject operatorLogObj;

	public static ServletContext getServletContext()
	{
		return servletContext;
	}

	public static void setServletContext(ServletContext servletContext)
	{
		MemInfo.servletContext = servletContext;
	}

	public static String getServletContextPath()
	{
		return servletContextPath;
	}

	public static void setServletContextPath(String servletContextPath)
	{
		MemInfo.servletContextPath = servletContextPath;
	}

	public static Map<String, String> getSecPasswordPolicyInfo()
	{
		return secPasswordPolicyInfo;
	}

	public static void setSecPasswordPolicyInfo(Map<String, String> secPasswordPolicyInfo)
	{
		MemInfo.secPasswordPolicyInfo = secPasswordPolicyInfo;
	}

	public static Map<String, String> getEmailInfo()
	{
		return emailInfo;
	}

	public static void setEmailInfo(Map<String, String> emailInfo)
	{
		MemInfo.emailInfo = emailInfo;
	}

	public static Map<String, LoginFailedInfo> getLoginFailedMap()
	{
		return loginFailedMap;
	}

	public static void setLoginFailedMap(Map<String, LoginFailedInfo> loginFailedMap)
	{
		MemInfo.loginFailedMap = loginFailedMap;
	}

    public static JSONObject getOperatorLogObj()
    {
        return operatorLogObj;
    }

    public static void setOperatorLogObj(JSONObject operatorLogObj)
    {
        MemInfo.operatorLogObj = operatorLogObj;
    }
}
