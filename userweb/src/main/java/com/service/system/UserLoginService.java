package com.service.system;
/**
 * SysUser用户登陆接口
 * Created by Xiezx on 2019-01-22.
 */
import com.dto.pojo.spsys.LoginUserBean;
import com.dto.pojo.system.Token;

import java.util.List;

public interface UserLoginService  {

    /**
     * 通过用户ID获取信任主机IP
     * @param userId
     * @return
     */
    List<String> getHostIpByUserId(String userId);

    /**
     * 获取用户信息
     *
     * @param userName
     * @param password
     * @return  数据集合
     * @date 2019-01-22
     */
    public LoginUserBean getLoginUser(String userName, String password);

    /**
     * 鉴权获取token
     * @return
     */
    public Token getToken();

    LoginUserBean getLoginUserBySfzh(String sfzh);
}
