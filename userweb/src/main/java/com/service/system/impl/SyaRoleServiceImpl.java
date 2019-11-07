package com.service.system.impl;

import com.dao.db1.system.SysRoleMapper;
import com.dao.entity.system.SysRole;
import com.dao.entity.system.SysUser;
import com.dto.constants.Constants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.system.SysRoleFunctionResourceService;
import com.service.system.SysRoleService;
import com.service.system.SysUserService;
import com.utils.sys.GenUtil;
import com.utils.sys.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SysRole接口实现类
 * Created by XieZX on 2019-01-09.
 */
@Transactional
@Service("sysRoleService")
public class SyaRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleFunctionResourceService sysRoleFunctionResourceService;

    @Override
    public String updateStateOpenByIds(List<String> ids) {
        String resStr = "success";
        sysRoleMapper.updateStateByIds(ids, 1);
        return resStr;
    }

    @Override
    public String updateStateCloseByIds(List<String> ids) {
        String resStr = "success";
        sysRoleMapper.updateStateByIds(ids, 0);
        return resStr;
    }

    @Override
    public int insert(SysRole sysRole) {
        return sysRoleMapper.insert(sysRole);
    }

    @Override
    public SysRole selectByPrimaryKey(String id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysRole> selectAll() {
        return sysRoleMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(SysRole sysRole) {
        return sysRoleMapper.updateByPrimaryKey(sysRole);
    }

    @Override
    public int save(SysRole sysRole) {
        if ((sysRole != null) && (!TextUtils.isEmpty(sysRole.getUpdateFlg()))) {
            // 修改
            if (sysRole.getUpdateFlg().equalsIgnoreCase("update")) {
                sysRole.setUpdateDate(new Date());
                return sysRoleMapper.updateByPrimaryKey(sysRole);
            }
            // 新增
            if (sysRole.getUpdateFlg().equalsIgnoreCase("add")) {
                sysRole.setId(GenUtil.getUUID());
                sysRole.setCreateDate(new Date());
                return sysRoleMapper.insert(sysRole);
            }
        }
        return 0;
    }

    @Override
    public SysRole selectByRoleName(String roleName) {
        return sysRoleMapper.selectByRoleName(roleName);
    }

    @Override
    public PageInfo<SysRole> search(Map<String, Object> queryMap) {
        Integer pageNum = (Integer) queryMap.get(Constants.PAGE_CURRENT);
        Integer pageSize = (Integer) queryMap.get(Constants.PAGE_SIZE);
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> invoices = sysRoleMapper.search(queryMap);
        PageInfo<SysRole> pageList = new PageInfo<>(invoices);
        return pageList;
    }

    @Override
    public List<?> selectAllRoleName() {
        return sysRoleMapper.selectAllRoleName();
    }

    @Override
    public List<String> selectNameByIds(List<String> ids) {
        return sysRoleMapper.selectNameByIds(ids);
    }

    @Override
    public Map<String, List<String>> delByIds(List<String> ids) {
        Map<String, List<String>> existsMap = new HashMap<>();
        if (null == ids || ids.isEmpty()) {
            return existsMap;
        }
        for (String id : ids) {
            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
            if (null == sysRole) {
                continue;
            }
            List<SysUser> list = sysUserService.selectUserNameByRoleId(id);
            if (null == list || list.isEmpty()) {
                sysRoleMapper.delByPrimaryKey(id);
                sysRoleFunctionResourceService.deleteByPrimaryKey(Arrays.asList(id));
                continue;
            }
            existsMap.put(sysRole.getName(), list.stream().map(SysUser::getUsername).collect(Collectors.toList()));
        }
        return existsMap;
    }
}
