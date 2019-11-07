package com.api.system;

import com.alibaba.fastjson.JSONObject;
import com.api.syslogin.LogInController;
import com.dto.enums.RspCode;
import com.dto.enums.SessionItem;
import com.dto.pojo.spsys.LoginUserBean;
import com.dto.pojo.spsys.ResponseMessage;
import com.dto.pojo.system.Token;
import com.service.system.UserLoginService;
import com.utils.sys.AESPython;
import com.utils.sys.IpUtils;
import com.utils.sys.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by Xiezx on 2019-01-08.
 */
@Api(tags = "用户登录校验")
@RestController
@RequestMapping("sysapi/userLogin")
public class UserLoginController {
    private static final Logger log = LoggerFactory.getLogger(LogInController.class);

    @Autowired
    private UserLoginService userLoginService;

    @ApiOperation(value = "用户，登录校验", notes = "用户，登录校验")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userName", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码，通过getEncryptInfo获取的信息进行加密后递交", required = true, dataType = "String"),
            //@ApiImplicitParam(paramType = "query", name = "authCode", value = "验证码", required = true, dataType = "String"),
    })
    @RequestMapping(value = "userLogin", method = RequestMethod.POST)
    public ResponseMessage userLogin(String userName, String password, String authCode, HttpServletRequest req) {
        JSONObject retBean = new JSONObject();
        //临时屏蔽
        /*Object verCode = SessionUtils.getSessionAttribute(SessionItem.authCode.name());
        if (null == verCode) {
            return ResponseMessage.sendDefined(RspCode.SYS_AUTH_ERROR);
        }
        String verCodeStr = verCode.toString();
        LocalDateTime localDateTime = (LocalDateTime) SessionUtils.getSessionAttribute("codeTime");
        long past = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        if (verCodeStr == null || authCode == null || authCode.isEmpty() || !verCodeStr.equalsIgnoreCase(authCode)) {
            return ResponseMessage.sendDefined(RspCode.SYS_AUTH_ERROR);
        } else if ((now - past) / 1000 / 60 > 5) {
            return ResponseMessage.sendDefined(RspCode.SYS_AUTH_TIMEOUT);*/
       // } else {
            String loginIp = IpUtils.getIpAddr(req);
            log.info("登录ip地址为： " + loginIp);
            //用户名密码合法性校验
            String sKey = (String) SessionUtils.getSessionAttribute(SessionItem.encryptKey.name());
            String sIv = (String) SessionUtils.getSessionAttribute(SessionItem.encryptIv.name());
            try {
                password = AESPython.Decrypt(password, sKey, sIv);
                if (password == null) {
                    return ResponseMessage.sendDefined(RspCode.SYS_AUTH_ENCRYPT);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
                return ResponseMessage.sendDefined(RspCode.SYS_AUTH_ENCRYPT);
            }
            LoginUserBean loginUserBean = userLoginService.getLoginUser( userName,password
                    //临时屏蔽
                   /* OwPasswordEncrypt.str2HexStr(OwPasswordEncrypt.Encrypt(password, userName, userName.length()))*/);
            if (loginUserBean == null) {
                return ResponseMessage.sendDefined(RspCode.CONNECT_AUTH_ERROR);
            }
            //用户名密码正确，校验IP地址是否允许访问
            if (CheckHostIP(loginUserBean, loginIp)) {
                //1、验证码验证成功，删除存储的验证码
                SessionUtils.removeSessionAttribute(SessionItem.authCode.name());
                SessionUtils.removeSessionAttribute(SessionItem.checkAuthCode.name());
                // 获取鉴权token
                Token token = userLoginService.getToken();
                //------------------------------设置session-----------------------//
                // 保存tokenId到session
                SessionUtils.setSessionAttribute(SessionItem.tokenId.name(), token.getTokenId());
                // 用户ID
                SessionUtils.setSessionAttribute(SessionItem.userId.name(), loginUserBean.getId());
                // 用户名称
                SessionUtils.setSessionAttribute(SessionItem.userName.name(), loginUserBean.getUserName());
                // 角色ID
                SessionUtils.setSessionAttribute(SessionItem.roleId.name(), loginUserBean.getRoleId());
                // 角色名称
                SessionUtils.setSessionAttribute(SessionItem.roleName.name(), loginUserBean.getRoleName());
                // 用户整体信息
                SessionUtils.setSessionAttribute(SessionItem.loginUserBean.name(), loginUserBean);
                retBean.put("loginIp", loginIp);
                retBean.put("loginUserBean", loginUserBean);
                retBean.put("tokenId", token.getTokenId());
                retBean.put("loginIp", loginIp);
                return ResponseMessage.sendOK(retBean);
            }


        return ResponseMessage.sendError();
    }
    public  Boolean CheckHostIP(LoginUserBean loginUserBean, String loginIp) {
        //临时屏蔽
        return true;

     /* String userId = loginUserBean.getId();
      List<String> hostIps = userLoginService.getHostIpByUserId(userId);
       // 如果未给该用户添加信任主机IP 则正常登录
       if (null != hostIps && !hostIps.isEmpty()) {
           if (!hostIps.contains(loginIp)) {
               log.error("非法ip登录，登录ip地址为： " + loginIp);
           } else {
                return true;
           }
       }
        return false;*/
    }
    }

