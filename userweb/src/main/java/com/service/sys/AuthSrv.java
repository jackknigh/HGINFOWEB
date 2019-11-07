package com.service.sys;


import com.dto.pojo.spsys.LoginUserBean;
import com.dto.pojo.system.Token;

import java.util.List;

/**
 *
 * @version version 1.0
 * @ClassName IAuthSrv
 * @Description: 〈鉴权服务接口〉
 * @date 2018/10/9
 */
public interface AuthSrv
{

	/**
	 * 通过用户ID获取信任主机IP
	 * @param userId
	 * @return
	 */
	List<String> getHostIpByUserId(String userId);

	/**
	 * 鉴权获取token
	 * @return
	 */
	public Token getToken();


	public LoginUserBean getLoginUser(String accountId, String userName, String password);

}
