package com.api.syslogin;

import com.alibaba.fastjson.JSONObject;
import com.config.HgApplicationProperty;
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
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

/**
 * Created by CuiL on 2018-10-31.
 */
@Api(tags = "系统登录接口")
@RestController
@RequestMapping("sysapi/login")
public class LogInController {
    private static final Logger log = LoggerFactory.getLogger(LogInController.class);
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private HgApplicationProperty applicationProperty;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    @ApiOperation(value = "ERP用户登录", notes = "根据用户名、密码和验证码登陆系统")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "accountId", value = "帐套编号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "userName", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码，通过getEncryptInfo获取的信息进行加密后递交", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "authCode", value = "验证码", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "autoLogin", value = "下次是否自动登录", required = true, dataType = "int"),
    })
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseMessage login(String accountId, String userName, String password, String authCode, Integer autoLogin, HttpServletRequest req) {
        JSONObject retBean = new JSONObject();
        log.info("SessionId:" + req.getRequestedSessionId() + " accountId:" + accountId + " userName:" + userName);
        if (applicationProperty.getLoginWithAutoCode().equals("1")) {
            Object verCode = SessionUtils.getSessionAttribute(SessionItem.authCode.name());
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
                return ResponseMessage.sendDefined(RspCode.SYS_AUTH_TIMEOUT);
            }
        }

        String loginIp = IpUtils.getIpAddr(req);
        log.info("登录ip地址为： " + loginIp);

        String sKey = (String) SessionUtils.getSessionAttribute(SessionItem.encryptKey.name());
        String sIv = (String) SessionUtils.getSessionAttribute(SessionItem.encryptIv.name());
        log.info("sKey:" + sKey + "  sIv:" + sIv);
        if (sKey == null || sIv == null) {
            return ResponseMessage.sendDefined(RspCode.SYS_AUTH_ENCRYPT_TIMEOUT);
        }
        try {
            password = AESPython.Decrypt(password, sKey, sIv);
            if (password == null) {
                return ResponseMessage.sendDefined(RspCode.SYS_AUTH_ENCRYPT);
            }
            log.info("解密密码：" + password);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseMessage.sendDefined(RspCode.SYS_AUTH_ENCRYPT);
        }
        LoginUserBean loginUserBean = userLoginService.getLoginUser(userName, password);
        if (loginUserBean == null) {
            return ResponseMessage.sendDefined(RspCode.CONNECT_AUTH_ERROR);
        }
        //用户名密码正确，校验IP地址是否允许访问
        if (CheckHostIP(loginUserBean, loginIp)) {
//            loginUserBean.setPasswordEnc(DigestUtils.md5Hex(password));
            //1、验证码验证成功，删除存储的验证码
            SessionUtils.removeSessionAttribute(SessionItem.authCode.name());
            SessionUtils.removeSessionAttribute(SessionItem.checkAuthCode.name());
            SessionUtils.removeSessionAttribute(SessionItem.encryptKey.name());
            SessionUtils.removeSessionAttribute(SessionItem.encryptIv.name());
            // 获取鉴权token
            Token token = userLoginService.getToken();
            //------------------------------设置session-----------------------//
            // 保存tokenId到session
//            SessionUtils.setSessionAttribute(SessionItem.accountId.name(), loginUserBean.getAccountId());
            SessionUtils.setSessionAttribute(SessionItem.tokenId.name(), token.getTokenId());
            // 用户ID
            SessionUtils.setSessionAttribute(SessionItem.userId.name(), loginUserBean.getId());
            // 用户名称
            SessionUtils.setSessionAttribute(SessionItem.userName.name(), loginUserBean.getUserName());
            // 姓名
            SessionUtils.setSessionAttribute(SessionItem.name.name(), loginUserBean.getName());
            // 用户邮箱
            SessionUtils.setSessionAttribute(SessionItem.email.name(), loginUserBean.getEmail());
            // 角色ID
            SessionUtils.setSessionAttribute(SessionItem.roleId.name(), loginUserBean.getRoleId());
            // 角色名称
            SessionUtils.setSessionAttribute(SessionItem.roleName.name(), loginUserBean.getRoleName());
            // 用户整体信息
            SessionUtils.setSessionAttribute(SessionItem.loginUserBean.name(), loginUserBean);
            //7天自动登录
            if (autoLogin == 1) {
                String redisKey = DigestUtils.md5Hex(password + token.getTokenId());
                String redisVal = JSONObject.toJSONString(loginUserBean);
                // 将用户信息写入redis
                redisTemplate.opsForValue().set(redisKey, redisVal);
                redisTemplate.expire(redisKey, 24 * 7, TimeUnit.HOURS);
                //存储一个关联值，用于用户禁用自动登录时关联删除redis
                //key =md5(帐套编号明文+用户名明文) 用户名redis内存储的是上次的md5(pwd+tokenId)主要用于用户主动在其他终端停用自动登录使用，查找到value，以value为key删掉redis的自动登录信息
                redisTemplate.opsForValue().set(DigestUtils.md5Hex(accountId + userName), redisKey);
                redisTemplate.expire(DigestUtils.md5Hex(accountId + userName), 24 * 7, TimeUnit.HOURS);

            }
            retBean.put("loginIp", loginIp);
            retBean.put("loginUserBean", loginUserBean);
            retBean.put("tokenId", token.getTokenId());
            retBean.put("loginIp", loginIp);
            return ResponseMessage.sendOK(retBean);
        }
        return ResponseMessage.sendError();
    }

    @ApiOperation(value = "ERP用户登录", notes = "根据token登陆系统")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userKey", value = "随机UUID", required = true, dataType = "String"),
    })
    @RequestMapping(value = "zjlogin", method = RequestMethod.GET)
    public ModelAndView zjlogin(String userKey) {
        return new ModelAndView("/login.html?userKey=" + userKey);
    }


    public Boolean CheckHostIP(LoginUserBean loginUserBean, String loginIp) {
        //临时屏蔽
        return true;

//        String userId = loginUserBean.getId();
//        List<String> hostIps = authSrv.getHostIpByUserId(userId);
//        // 如果未给该用户添加信任主机IP 则正常登录
//        if (null != hostIps && !hostIps.isEmpty()) {
//            if (!hostIps.contains(loginIp)) {
//                log.error("非法ip登录，登录ip地址为： " + loginIp);
//            } else {
//                return true;
//            }
//        }
//        return false;
    }
}
