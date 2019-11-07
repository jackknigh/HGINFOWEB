package com.service.system.impl;

import com.dao.db1.system.SysDepartMapper;
import com.dao.db1.system.SysRoleMapper;
import com.dao.db1.system.SysUserMapper;
import com.dao.entity.system.SysDepart;
import com.dto.pojo.spsys.LoginUserBean;
import com.dto.pojo.system.Token;
import com.service.sys.impl.AuthSrvImpl;
import com.service.system.UserLoginService;
import com.utils.sys.GenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Transactional
@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {
    private static final Logger log = LoggerFactory.getLogger(AuthSrvImpl.class);
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysDepartMapper sysDepartMapper;

    @Override
    public List<String> getHostIpByUserId(String userId) {
        return null;
    }

    @Override
    public LoginUserBean getLoginUser(String userName, String password) {
        LoginUserBean loginUserBean = sysUserMapper.getLoginUser(userName, password);
        if (loginUserBean != null) {
            List<String> _departIds = Arrays.asList(loginUserBean.getDepCodes().split(","));
            List<String> _roleIds = Arrays.asList(loginUserBean.getRoleId().split(","));
            List<String> departNames = sysDepartMapper.selectNameByIds(_departIds);
            List<String> roleNames = sysRoleMapper.selectNameByIds(_roleIds);
            String roleName = StringUtils.join(roleNames.toArray(), ",");
            loginUserBean.setRoleName(roleName);
            String departName = StringUtils.join(departNames.toArray(), ",");
            loginUserBean.setOrgName(departName);
            if (null != loginUserBean.getDepCode()) {
                SysDepart depart = sysDepartMapper.selectByDepCode(loginUserBean.getDepCode());
                loginUserBean.setDepartName(null == depart ? "" : depart.getName());
            }
        }
        return loginUserBean;
    }

    @Override
    public Token getToken() {
        Token token = new Token();
        String tokenId = GenUtil.getUUID();
        token.setTokenId(tokenId);
        log.info("get a tocken success!" + tokenId);
        return token;
    }

    @Override
    public LoginUserBean getLoginUserBySfzh(String sfzh) {
        LoginUserBean loginUserBean = sysUserMapper.getLoginUserBySfzh(sfzh);
        if (loginUserBean != null) {
            List<String> _departIds = Arrays.asList(loginUserBean.getDepCodes().split(","));
            List<String> _roleIds = Arrays.asList(loginUserBean.getRoleId().split(","));
            List<String> departNames = sysDepartMapper.selectNameByIds(_departIds);
            List<String> roleNames = sysRoleMapper.selectNameByIds(_roleIds);
            String roleName = StringUtils.join(roleNames.toArray(), ",");
            loginUserBean.setRoleName(roleName);
            String departName = StringUtils.join(departNames.toArray(), ",");
            loginUserBean.setOrgName(departName);
            if (null != loginUserBean.getDepCode()) {
                SysDepart depart = sysDepartMapper.selectByDepCode(loginUserBean.getDepCode());
                loginUserBean.setDepartName(null == depart ? "" : depart.getName());
            }
        }
        return loginUserBean;
    }
}
