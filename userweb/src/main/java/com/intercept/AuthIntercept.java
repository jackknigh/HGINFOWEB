package com.intercept;

import com.alibaba.fastjson.JSONObject;
import com.dao.entity.system.SysResource;
import com.dao.entity.system.SysRoleFunctionResource;
import com.dto.enums.SessionItem;
import com.dto.pojo.sys.LoginUserBean;
import com.service.system.SysDepartService;
import com.service.system.SysResourceService;
import com.service.system.SysRoleFunctionResourceService;
import com.service.system.SysUserService;
import com.utils.sys.GenUtil;
import com.utils.sys.SysLogUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ank
 * @version v 1.0
 * @title [鉴权拦截器]
 * @ClassName: com.spinfosec.intercept.AuthIntercept
 * @description [鉴权拦截器]
 * @create 2018/10/31 16:22
 * @copyright Copyright(C) 2018 SHIPING INFO Corporation. All rights reserved.
 */
@Component("AuthIntercept")
public class AuthIntercept implements HandlerInterceptor {
    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private SysUserService sysUserService;


    @Autowired
    private SysDepartService sysDepartService;

    @Autowired
    private SysRoleFunctionResourceService sysRoleFunctionResourceService;

    private Logger logger = LoggerFactory.getLogger(AuthIntercept.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        try {
            if (handler instanceof HandlerMethod) {
                String sb = "----------------------开始时间：" + new SimpleDateFormat("hh:mm:ss.SSS").format(startTime) + "" + "------------\n" +
                        "Controller：" + ((HandlerMethod) handler).getBean().getClass().getName() + "\n" + "Method：" +
                        ((HandlerMethod) handler).getMethod().getName() + "\n" +
                        //"Value：" + ((HandlerMethod) handler).getMethod().getAnnotation(ApiOperation.class).value() + "\n" +
                        "RequestMethod：" + request.getMethod() + "\n" + "Params：" + getParamString(request) + "\n" + "URL：" +
                        request.getRequestURL() + "\n" + "SessionID：" + request.getSession().getId() + "\n" + "ApplyID：" +
                        request.getSession().getAttribute("applyId") + "\n" + "OpenID：" + request.getSession().getAttribute("openid") +
                        "\n";
                //通过输入流获取POST请求中的参数
                logger.info(sb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String servletPath = request.getServletPath();
//        String path = servletPath.replace("/sysapi/", "");
//        List<SysResource> resourceList = sysResourceService.selectResourceByAfterUrl(path);
//        if (!CollectionUtils.isEmpty(resourceList)) {
//            createThreadLog(request, servletPath, handler, resourceList);
//            boolean auth = selectAuth(resourceList, path, request);
//            if (!auth) {
//                returnJson(response, JSONObject.toJSONString(ResponseMessage.sendDefined(RspCode.AUTH_OPERATOR_ERROR)));
//            }
//            logger.info("preHandle......是否有权限校验,路径:" + path + " ----------- 是否有权限" + auth);
//        }
        // 重新设置JSESSIONID
        Cookie c = new Cookie("JSESSIONID", request.getSession().getId());
        c.setPath("/");
        c.setHttpOnly(true);
        c.setSecure(false);
        c.setMaxAge(-1);
        response.addCookie(c);
        return true;

//        Long startTime = System.currentTimeMillis();
//        request.setAttribute("startTime", startTime);
//
//        RspCode failResp;
//        String servletPath = request.getServletPath();
//        if(servletPath.contains("/sysapi/login/")){
//            // 重新设置JSESSIONID
//            Cookie c = new Cookie("JSESSIONID", request.getSession().getId());
//            c.setPath("/");
//            c.setHttpOnly(true);
//            c.setSecure(false);
//            c.setMaxAge(-1);
//            response.addCookie(c);
//            return true;
//        }else{
//            // 判断用户是否session超时
//            String userId = (String) request.getSession().getAttribute(SessionItem.userId.name());
//            logger.debug("currentUserId = " + userId);
//            if (StringUtils.isNotEmpty(userId))
//            {
//                // 用户未失效  判断token
//                String authorization = request.getHeader(SessionItem.tokenId.name());
//                if (StringUtils.isNotEmpty(authorization))
//                {
//                    String tokenId = (String) request.getSession().getAttribute(SessionItem.tokenId.name());
//                    if (StringUtils.isNotEmpty(tokenId))
//                    {
//                        if (tokenId.equalsIgnoreCase(authorization))
//                        {
//
//                            try {
//                                if (handler instanceof HandlerMethod) {
//                                    String sb = "----------------------开始时间：" + new SimpleDateFormat("hh:mm:ss.SSS").format(startTime) + "" + "------------\n" +
//                                            "Controller：" + ((HandlerMethod) handler).getBean().getClass().getName() + "\n" + "Method：" +
//                                            ((HandlerMethod) handler).getMethod().getName() + "\n" +
//                                            //"Value：" + ((HandlerMethod) handler).getMethod().getAnnotation(ApiOperation.class).value() + "\n" +
//                                            "RequestMethod：" + request.getMethod() + "\n" + "Params：" + getParamString(request) + "\n" + "URL：" +
//                                            request.getRequestURL() + "\n" + "SessionID：" + request.getSession().getId() + "\n" + "ApplyID：" +
//                                            request.getSession().getAttribute("applyId") + "\n" + "OpenID：" + request.getSession().getAttribute("openid") +
//                                            "\n";
//                                    //通过输入流获取POST请求中的参数
//                                    logger.info(sb);
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            String path = servletPath.replace("/sysapi/", "");
//                            List<SysResource> resourceList = sysResourceService.selectResourceByAfterUrl(path);
//                            if (!CollectionUtils.isEmpty(resourceList)) {
//                                createThreadLog(request, servletPath, handler, resourceList);
//                                boolean auth = selectAuth(resourceList, path, request);
//                                if (!auth) {
//                                    returnJson(response, JSONObject.toJSONString(ResponseMessage.sendDefined(RspCode.AUTH_OPERATOR_ERROR)));
//                                }
//                                logger.info("preHandle......是否有权限校验,路径:" + path + " ----------- 是否有权限" + auth);
//                            }
//                            // 重新设置JSESSIONID
//                            Cookie c = new Cookie("JSESSIONID", request.getSession().getId());
//                            c.setPath("/");
//                            c.setHttpOnly(true);
//                            c.setSecure(false);
//                            c.setMaxAge(-1);
//                            response.addCookie(c);
//                            return true;
//                        }
//                        else
//                        {
//                            logger.info("请求中token和session中token不一致，token失效！");
//                            failResp = RspCode.INVALID_TOKENID;
//                        }
//                    }
//                    else
//                    {
//                        logger.info("session中token失效");
//                        failResp = RspCode.INVALID_TOKENID;
//                    }
//                }
//                else
//                {
//                    logger.info("请求中缺失token！");
//                    failResp =RspCode.INVALID_TOKENID;
//                }
//            }
//            else
//            {
//                logger.info("用户session失效！");
//                failResp = RspCode.INVALID_SESSION;
//            }
//            // 验证失败
//            response(response, JSONObject.toJSONString(ResponseMessage.sendDefined(failResp)));
//            Cookie c = new Cookie("JSESSIONID", request.getSession().getId());
//            c.setPath("/");
//            c.setHttpOnly(true);
//            c.setSecure(false);
//            c.setMaxAge(-1);
//            response.addCookie(c);
//        }
//        return false;
    }

    private void response(HttpServletResponse response, String json) throws Exception {
        PrintWriter out = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            out = response.getWriter();
            out.print(json);
        } catch (Exception e) {
            logger.error("write response error", e);
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     * 权限拦截不通过后,向页面提示信息
     *
     * @param response
     * @param json
     * @throws Exception
     */
    private void returnJson(HttpServletResponse response, String json) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            logger.error("response error", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        if (handler instanceof HandlerMethod) {
            StringBuilder sb = new StringBuilder(1000);
            sb.append("CostTime  : ").append(executeTime).append("ms").append("\n");
            sb.append("-------------------------------------------------------------------------------");
            System.out.println(sb.toString());
        }

        System.out.println("postHandle......");
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        // saveLog(request, response);
        // saveOperaterLog(request, response);
        // SysLogUtil.remove();
        System.out.println("afterCompletion......");
    }


    /**
     * 设置线程新
     *
     * @param request
     * @param path
     * @param handler
     * @param resourceList
     */
    private void createThreadLog(HttpServletRequest request, String path, Object handler, List<SysResource> resourceList) {
        final SysLogUtil.SysLog sysLog = new SysLogUtil.SysLog();
        sysLog.setTracerId(GenUtil.getUUID());
        LoginUserBean loginUserBean = (LoginUserBean) request.getSession().getAttribute(SessionItem.loginUserBean.name());
        try {
            if (null != loginUserBean) {
                sysLog.setUserName(loginUserBean.getUserName());
                sysLog.setUrlName(path);
                sysLog.setUrlNameCN(((HandlerMethod) handler).getMethod().getAnnotation(ApiOperation.class).value());
                sysLog.setDealIp(getIpAddr(request));
                sysLog.setOperateCondition(getParamString(request));
                sysLog.setStartTime(new Date());
                if (null != resourceList && !resourceList.isEmpty()) {
                    if (null != resourceList.get(0)) {
                        sysLog.setPortType(resourceList.get(0).getOperateType());
                    }
                }
                sysLog.setUserId(loginUserBean.getId());
                sysLog.setDepartCode(loginUserBean.getDepCode());
                sysLog.setDepartName(loginUserBean.getDepartName());
            } else {
                sysLog.setUserName(request.getParameter("userName"));
            }
            if (StringUtils.isBlank(sysLog.getUserName())) {
                return;
            }
        } catch (Exception ex) {

        }
        logger.info("当前请求信息:================" + sysLog);
        SysLogUtil.setThreadLocalLog(sysLog);
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }


    private String getParamString(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            sb.append(HttpHelper.getBodyString(request));
        }
        //sb.append(JSONObject.toJSONString(request.getParameterMap()));

        Map map = new HashMap();
        for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
            map.put(e.getKey(), Arrays.asList(e.getValue()));
            // sb.append(e.getKey()).append("=");
            // String[] value = e.getValue();
            // if (value != null && value.length == 1) {
            //     sb.append(value[0]).append("\t");
            // } else {
            //     sb.append(Arrays.toString(value)).append("\t");
            // }
        }
        if (map.size() > 0) {
            sb.append(JSONObject.toJSONString(map));
        }

        return sb.toString();
    }

    //请求路径校验用户是否有权限
    private boolean selectAuth(List<SysResource> resourceList, String path, HttpServletRequest request) {
        if ("login/getEncryptInfo".equals(path) || "login/login".equals(path)) {
            return true;
        }
        /*********************超级管理员Admin权限独立判断begin*****************/
        String userName = (String) request.getSession().getAttribute(SessionItem.userName.name());
        if ("admin".equals(userName)) {
            return true;
        }
        /*********************超级管理员Admin权限独立判断end*****************/

        if (CollectionUtils.isEmpty(resourceList)) {
            return true;
        }
        String roleId = (String) request.getSession().getAttribute(SessionItem.roleId.name());
        if (StringUtils.isBlank(roleId)) {
            return false;
        }
        List<String> _roleIds = Arrays.asList(roleId.split(","));
        List<String> functionList = new ArrayList<>();
        List<SysRoleFunctionResource> sysRoleFunctionResources = sysRoleFunctionResourceService.listSysRoleFunctionByRoleIds(_roleIds);
        if (CollectionUtils.isEmpty(sysRoleFunctionResources)) {
            return false;
        }
        sysRoleFunctionResources.stream().forEach(e -> {
            String functionId = e.getFunctionId();
            if (StringUtils.isNotBlank(functionId)) {
                functionList.addAll(Arrays.asList(functionId.split(",")));
            }
        });
        if (CollectionUtils.isEmpty(functionList)) {
            return false;
        }

        SysResource resource = resourceList.get(0);
        if (functionList.contains(resource.getFunctionId())) {
            return true;
        }
        return true;

    }
}

//package com.intercept;
//
//import com.alibaba.fastjson.JSONObject;
//import com.dao.entity.system.SysResource;
//import com.dao.entity.system.SysRoleFunctionResource;
//import com.dto.enums.RspCode;
//import com.dto.enums.SessionItem;
//import com.dto.pojo.sys.LoginUserBean;
//import com.dto.pojo.sys.ResponseMessage;
//import com.service.system.SysDepartService;
//import com.service.system.SysResourceService;
//import com.service.system.SysRoleFunctionResourceService;
//import com.service.system.SysUserService;
//import com.utils.sys.GenUtil;
//import com.utils.sys.SysLogUtil;
//import io.swagger.annotations.ApiOperation;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.InetAddress;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * @author ank
// * @version v 1.0
// * @title [鉴权拦截器]
// * @ClassName: com.spinfosec.intercept.AuthIntercept
// * @description [鉴权拦截器]
// * @create 2018/10/31 16:22
// * @copyright Copyright(C) 2018 SHIPING INFO Corporation. All rights reserved.
// */
//@Component("AuthIntercept")
//public class AuthIntercept implements HandlerInterceptor {
//    @Autowired
//    private SysResourceService sysResourceService;
//
//    @Autowired
//    private SysUserService sysUserService;
//
//
//    @Autowired
//    private SysDepartService sysDepartService;
//
//    @Autowired
//    private SysRoleFunctionResourceService sysRoleFunctionResourceService;
//
//    private Logger logger = LoggerFactory.getLogger(AuthIntercept.class);
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Long startTime = System.currentTimeMillis();
//        request.setAttribute("startTime", startTime);
//
//        try {
//            if (handler instanceof HandlerMethod) {
//                String sb = "----------------------开始时间：" + new SimpleDateFormat("hh:mm:ss.SSS").format(startTime) + "" + "------------\n" +
//                        "Controller：" + ((HandlerMethod) handler).getBean().getClass().getName() + "\n" + "Method：" +
//                        ((HandlerMethod) handler).getMethod().getName() + "\n" +
//                        //"Value：" + ((HandlerMethod) handler).getMethod().getAnnotation(ApiOperation.class).value() + "\n" +
//                        "RequestMethod：" + request.getMethod() + "\n" + "Params：" + getParamString(request) + "\n" + "URL：" +
//                        request.getRequestURL() + "\n" + "SessionID：" + request.getSession().getId() + "\n" + "ApplyID：" +
//                        request.getSession().getAttribute("applyId") + "\n" + "OpenID：" + request.getSession().getAttribute("openid") +
//                        "\n";
//                //通过输入流获取POST请求中的参数
//                logger.info(sb);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String servletPath = request.getServletPath();
//        String path = servletPath.replace("/sysapi/", "");
//        List<SysResource> resourceList = sysResourceService.selectResourceByAfterUrl(path);
//        if (!CollectionUtils.isEmpty(resourceList)) {
//            createThreadLog(request, servletPath, handler, resourceList);
//            boolean auth = selectAuth(resourceList, path, request);
//            if (!auth) {
//                returnJson(response, JSONObject.toJSONString(ResponseMessage.sendDefined(RspCode.AUTH_OPERATOR_ERROR)));
//            }
//            logger.info("preHandle......是否有权限校验,路径:" + path + " ----------- 是否有权限" + auth);
//        }
//        // 重新设置JSESSIONID
//        Cookie c = new Cookie("JSESSIONID", request.getSession().getId());
//        c.setPath("/");
//        c.setHttpOnly(true);
//        c.setSecure(false);
//        c.setMaxAge(-1);
//        response.addCookie(c);
//        return true;
//    }
//
//    /**
//     * 权限拦截不通过后,向页面提示信息
//     *
//     * @param response
//     * @param json
//     * @throws Exception
//     */
//    private void returnJson(HttpServletResponse response, String json) throws Exception {
//        PrintWriter writer = null;
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html; charset=utf-8");
//        try {
//            writer = response.getWriter();
//            writer.print(json);
//        } catch (IOException e) {
//            logger.error("response error", e);
//        } finally {
//            if (writer != null) {
//                writer.close();
//            }
//        }
//    }
//
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
//            throws Exception {
//        long startTime = (Long) request.getAttribute("startTime");
//        long endTime = System.currentTimeMillis();
//        long executeTime = endTime - startTime;
//        if (handler instanceof HandlerMethod) {
//            StringBuilder sb = new StringBuilder(1000);
//            sb.append("CostTime  : ").append(executeTime).append("ms").append("\n");
//            sb.append("-------------------------------------------------------------------------------");
//            System.out.println(sb.toString());
//        }
//
//        System.out.println("postHandle......");
//    }
//
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
//        // saveLog(request, response);
//        // saveOperaterLog(request, response);
//        // SysLogUtil.remove();
//        System.out.println("afterCompletion......");
//    }
//
//
//    /**
//     * 设置线程新
//     *
//     * @param request
//     * @param path
//     * @param handler
//     * @param resourceList
//     */
//    private void createThreadLog(HttpServletRequest request, String path, Object handler, List<SysResource> resourceList) {
//        final SysLogUtil.SysLog sysLog = new SysLogUtil.SysLog();
//        sysLog.setTracerId(GenUtil.getUUID());
//        LoginUserBean loginUserBean = (LoginUserBean) request.getSession().getAttribute(SessionItem.loginUserBean.name());
//        try {
//            if (null != loginUserBean) {
//                sysLog.setUserName(loginUserBean.getUserName());
//                sysLog.setUrlName(path);
//                sysLog.setUrlNameCN(((HandlerMethod) handler).getMethod().getAnnotation(ApiOperation.class).value());
//                sysLog.setDealIp(getIpAddr(request));
//                sysLog.setOperateCondition(getParamString(request));
//                sysLog.setStartTime(new Date());
//                if (null != resourceList && !resourceList.isEmpty()) {
//                    if (null != resourceList.get(0)) {
//                        sysLog.setPortType(resourceList.get(0).getOperateType());
//                    }
//                }
//                sysLog.setUserId(loginUserBean.getId());
//                sysLog.setDepartCode(loginUserBean.getDepCode());
//                sysLog.setDepartName(loginUserBean.getDepartName());
//            } else {
//                sysLog.setUserName(request.getParameter("userName"));
//            }
//            if (StringUtils.isBlank(sysLog.getUserName())) {
//                return;
//            }
//        } catch (Exception ex) {
//
//        }
//        logger.info("当前请求信息:================" + sysLog);
//        SysLogUtil.setThreadLocalLog(sysLog);
//    }
//
//    private String getIpAddr(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//            if (ip.equals("127.0.0.1")) {
//                //根据网卡取本机配置的IP
//                InetAddress inet = null;
//                try {
//                    inet = InetAddress.getLocalHost();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                ip = inet.getHostAddress();
//            }
//        }
//        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
//        if (ip != null && ip.length() > 15) {
//            if (ip.indexOf(",") > 0) {
//                ip = ip.substring(0, ip.indexOf(","));
//            }
//        }
//        return ip;
//    }
//
//
//    private String getParamString(HttpServletRequest request) {
//        StringBuffer sb = new StringBuffer();
//        if ("POST".equalsIgnoreCase(request.getMethod())) {
//            sb.append(HttpHelper.getBodyString(request));
//        }
//        //sb.append(JSONObject.toJSONString(request.getParameterMap()));
//
//        Map map = new HashMap();
//        for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
//            map.put(e.getKey(), Arrays.asList(e.getValue()));
//            // sb.append(e.getKey()).append("=");
//            // String[] value = e.getValue();
//            // if (value != null && value.length == 1) {
//            //     sb.append(value[0]).append("\t");
//            // } else {
//            //     sb.append(Arrays.toString(value)).append("\t");
//            // }
//        }
//        if (map.size() > 0) {
//            sb.append(JSONObject.toJSONString(map));
//        }
//
//        return sb.toString();
//    }
//
//    //请求路径校验用户是否有权限
//    private boolean selectAuth(List<SysResource> resourceList, String path, HttpServletRequest request) {
//        if ("login/getEncryptInfo".equals(path) || "login/login".equals(path)) {
//            return true;
//        }
//        /*********************超级管理员Admin权限独立判断begin*****************/
//        String userName = (String) request.getSession().getAttribute(SessionItem.userName.name());
//        if ("admin".equals(userName)) {
//            return true;
//        }
//        /*********************超级管理员Admin权限独立判断end*****************/
//
//        if (CollectionUtils.isEmpty(resourceList)) {
//            return true;
//        }
//        String roleId = (String) request.getSession().getAttribute(SessionItem.roleId.name());
//        if (StringUtils.isBlank(roleId)) {
//            return false;
//        }
//        List<String> _roleIds = Arrays.asList(roleId.split(","));
//        List<String> functionList = new ArrayList<>();
//        List<SysRoleFunctionResource> sysRoleFunctionResources = sysRoleFunctionResourceService.listSysRoleFunctionByRoleIds(_roleIds);
//        if (CollectionUtils.isEmpty(sysRoleFunctionResources)) {
//            return false;
//        }
//        sysRoleFunctionResources.stream().forEach(e -> {
//            String functionId = e.getFunctionId();
//            if (StringUtils.isNotBlank(functionId)) {
//                functionList.addAll(Arrays.asList(functionId.split(",")));
//            }
//        });
//        if (CollectionUtils.isEmpty(functionList)) {
//            return false;
//        }
//
//        SysResource resource = resourceList.get(0);
//        if (functionList.contains(resource.getFunctionId())) {
//            return true;
//        }
//        return true;
//
//    }
//}
