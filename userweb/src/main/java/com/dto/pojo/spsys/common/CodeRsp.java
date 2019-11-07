package com.dto.pojo.spsys.common;

import com.spinfosec.system.RspCode;

/**
 * 状态码说明集合
 */
public class CodeRsp
{

	private String code;

	private String msg;

	public CodeRsp(RspCode e)
	{
		this.code = e.getCode();
		this.msg = e.getDescription();
	}

	public CodeRsp()
	{
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
}
