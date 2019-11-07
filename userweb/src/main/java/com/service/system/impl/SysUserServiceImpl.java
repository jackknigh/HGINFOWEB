package com.service.system.impl;

import com.alibaba.fastjson.JSONObject;
import com.dao.db1.system.SysDepartMapper;
import com.dao.db1.system.SysRoleMapper;
import com.dao.db1.system.SysUserMapper;
import com.dao.entity.system.SysDepart;
import com.dao.entity.system.SysUser;
import com.dto.constants.Constants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.system.SysUserComponentService;
import com.service.system.SysUserService;
import com.utils.sys.GenUtil;
import com.utils.sys.TextUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiezx on 2019-01-08.
 */
@Transactional
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysDepartMapper sysDepartMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserComponentService sysUserComponentService;

    @Override
    public int deleteByPrimaryKey(List<String> ids) {
        return sysUserMapper.deleteByPrimaryKey(ids);
    }

    @Override
    public int insert(SysUser sysUser) {
        return sysUserMapper.insert(sysUser);
    }

    @Override
    public SysUser selectByPrimaryKey(String id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysUser> selectAll() {
        return sysUserMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(SysUser sysUser) {
        return sysUserMapper.updateByPrimaryKey(sysUser);
    }

    @Override
    public int save(SysUser sysUser) {
        int ret = 0;
        if ((sysUser != null) && (!TextUtils.isEmpty(sysUser.getUpdateFlg()))) {
            SysUser existsUser = sysUserMapper.selectByUserName(sysUser.getUsername());
            // 修改
            if (sysUser.getUpdateFlg().equalsIgnoreCase("update")) {
                if (null != existsUser && !existsUser.getId().equals(sysUser.getId())) {
                    return -1;
                }
                sysUser.setDelStatus(1);
                sysUser.setUpdateDate(new Date());
                return sysUserMapper.updateByPrimaryKey(sysUser);
            }
            // 新增
            if (sysUser.getUpdateFlg().equalsIgnoreCase("add")) {
                if (null != existsUser) {
                    return -1;
                }
                sysUser.setId(GenUtil.getUUID());
                sysUser.setDelStatus(1);
                sysUser.setCreateDate(new Date());
                sysUser.setUpdateDate(new Date());
                int rs = sysUserMapper.insert(sysUser);
                //初始化首页
                sysUserComponentService.init(sysUser.getUserId());
                return rs;
            }
        }
        return ret;
    }

    @Override
    public PageInfo<Object> search(Map<String, Object> queryMap) {
        Integer pageNum = (Integer) queryMap.get(Constants.PAGE_CURRENT);
        Integer pageSize = (Integer) queryMap.get(Constants.PAGE_SIZE);
        List<Object> list = new ArrayList<>();
        PageHelper.startPage(pageNum, pageSize);
        List search = sysUserMapper.search(queryMap);
        for (int i = 0; i < search.size(); i++) {
            SysUser sysUser = (SysUser) search.get(i);
            List<String> _departIds = Arrays.asList(sysUser.getDefaultDepart().split(","));
            List<String> _roleIds = Arrays.asList(sysUser.getRoleId().split(","));
            SysDepart departNames = sysDepartMapper.selectMinGrandeByDepCodes(_departIds);
            List<String> roleNames = sysRoleMapper.selectNameByIds(_roleIds);
            JSONObject treeObject = new JSONObject(true);
            //treeObject.put("user", sysUser);
            treeObject.put("depart", null == departNames ? "" : departNames.getName());
            treeObject.put("role", StringUtils.join(roleNames, ","));
            treeObject.put("departId", StringUtils.join(_departIds, ","));
            treeObject.put("id", sysUser.getId());
            treeObject.put("roleId", StringUtils.join(_roleIds, ","));
            treeObject.put("status", sysUser.getStatus());
            treeObject.put("userName", sysUser.getUsername());

            list.add(treeObject);
        }
        PageInfo pageList = new PageInfo(search);
        pageList.setList(list);
        return pageList;
    }

    @Override
    public SysUser selectByUserName(String userName) {
        return sysUserMapper.selectByUserName(userName);
    }

    @Override
    public SysUser selectAuthorityByUserId(String id) {
        return sysUserMapper.selectAuthorityByUserId(id);
    }

    @Override
    public String updateStateOpenByIds(List<String> ids) {
        String resStr = "success";
        sysUserMapper.updateStateByIds(ids, 1);
        return resStr;
    }

    @Override
    public String updateStateCloseByIds(List<String> ids) {
        String resStr = "success";
        sysUserMapper.updateStateByIds(ids, 0);
        return resStr;
    }

    @Override
    public List<SysUser> selectUserNameByRoleId(String roleId) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("roleId", roleId);
        List search = sysUserMapper.search(queryMap);
        return CollectionUtils.isEmpty(search) ? Collections.emptyList() : search;
    }
}
