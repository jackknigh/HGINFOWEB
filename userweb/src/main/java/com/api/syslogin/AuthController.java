package com.api.syslogin;

import com.alibaba.fastjson.JSONObject;
import com.dto.enums.SessionItem;
import com.dto.pojo.spsys.ResponseMessage;
import com.utils.sys.GenUtil;
import com.utils.sys.SessionUtils;
import com.utils.sys.VerifyCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

/**
 * Created by CuiL on 2018-11-01.
 */

@Api(tags = "系统验证码")
@RestController
@RequestMapping("sysapi/login")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @ApiOperation(value = "获取验证码图片", notes = "获取验证码图片，字体只显示大写，去掉了1,0,i,o几个容易混淆的字符")
    @RequestMapping(value="/getImage",method= RequestMethod.GET)
    public void authImage(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);

        // 删除以前的
        SessionUtils.removeSessionAttribute(SessionItem.authCode.name());
        SessionUtils.removeSessionAttribute(SessionItem.codeTime.name());
        //存储新的校验码
        SessionUtils.setSessionAttribute(SessionItem.authCode.name(),verifyCode.toLowerCase());
        SessionUtils.setSessionAttribute(SessionItem.codeTime.name(),LocalDateTime.now());

        // 生成图片
        int w = 100, h = 30;
        OutputStream out = response.getOutputStream();
        VerifyCodeUtils.outputImage(w, h, out, verifyCode);
    }

    @ApiOperation(value = "获取加密信息", notes = "获取加密公开key等，具体算法其他参数咨询开发人员")
    @RequestMapping(value="/getEncryptInfo",method= RequestMethod.GET)
    public ResponseMessage getEncryptInfo(HttpServletRequest request){
        String key = GenUtil.getUUID().substring(16);
        String iv = GenUtil.getUUID().substring(16);
        // 删除以前的
        SessionUtils.removeSessionAttribute(SessionItem.encryptKey.name());
        SessionUtils.removeSessionAttribute(SessionItem.encryptIv.name());
        //存储新的校验码
        SessionUtils.setSessionAttribute(SessionItem.encryptKey.name(),key);
        SessionUtils.setSessionAttribute(SessionItem.encryptIv.name(),iv);
        JSONObject retBean = new JSONObject();
        retBean.put(SessionItem.encryptKey.name(),key);
        retBean.put(SessionItem.encryptIv.name(),iv);
        retBean.put(SessionItem.encryptTime.name(), LocalDateTime.now());
        return ResponseMessage.sendOK(retBean);
    }
}
