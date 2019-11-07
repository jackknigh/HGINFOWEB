package com.service.system.impl;

import com.alibaba.fastjson.JSONObject;
import com.dao.db1.system.SysFunctionMapper;
import com.dao.entity.system.SysFunction;
import com.service.system.SysFunctionService;
import com.service.system.SysRoleFunctionResourceService;
import com.utils.sys.GenUtil;
import com.utils.sys.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SysFunction接口实现类
 * Created by Xiezx on 2019-01-12.
 */
@Transactional
@Service("sysFunctionService")
public class SysFunctionServiceImpl implements SysFunctionService {
    @Autowired
    private SysFunctionMapper sysFunctionMapper;

    @Autowired
    private SysRoleFunctionResourceService sysRoleFunctionResourceService;

    @Autowired
    private SyaRoleServiceImpl syaRoleService;


    @Override
    public Map<String, List<String>> deleteByPrimaryKey(List<String> ids) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> leftIds = new ArrayList();
        for (String id : ids) {
            delete(id, map);
        }
        return map;
    }

    private void delete(String id, Map<String, List<String>> map) {

        SysFunction function = sysFunctionMapper.selectByPrimaryKey(id);
        List<SysFunction> parent = sysFunctionMapper.selectAllChildren(id);
        if (null != parent && !parent.isEmpty()) {
            for (SysFunction sysFunction : parent) {
                delete(sysFunction.getId(), map);
            }
        }
        List<SysFunction> parent2 = sysFunctionMapper.selectAllChildren(id);
        if (null == function || parent2.isEmpty()) {
            deleteById(id);
            return;
        }
        List<SysFunction> roleIds = sysRoleFunctionResourceService.selectByFunctionId(id);
        if (null == roleIds || roleIds.isEmpty()) {
            deleteById(id);
            return;
        }
        List<String> roleNameList = syaRoleService.selectNameByIds(roleIds.stream().map(SysFunction::getId).collect(Collectors.toList()));
        map.put(function.getName(), roleNameList);
    }

    @Override
    public int insert(SysFunction sysFunction) {
        return sysFunctionMapper.insert(sysFunction);
    }

    @Override
    public SysFunction selectByPrimaryKey(String id) {
        return sysFunctionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysFunction> selectAll() {
        return sysFunctionMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(SysFunction sysFunction) {
        return sysFunctionMapper.updateByPrimaryKey(sysFunction);
    }

    @Override
    public int save(SysFunction sysFunction) {
        int ret = 0;

        if ((sysFunction != null) && (!TextUtils.isEmpty(sysFunction.getUpdateFlg()))) {
            // 修改
            if (sysFunction.getUpdateFlg().equalsIgnoreCase("update")) {
                sysFunction.setUpdateDate(new Date());
                return sysFunctionMapper.updateByPrimaryKey(sysFunction);
            }
            // 新增
            if (sysFunction.getUpdateFlg().equalsIgnoreCase("add")) {
                sysFunction.setId(GenUtil.getUUID());
                sysFunction.setCreateDate(new Date());
                return sysFunctionMapper.insert(sysFunction);
            }
        }
        return ret;
    }


    @Override
    public List<SysFunction> selectAllTopFunction(Map<String, Object> queryMap) {
        List<SysFunction> sysFunctionList = sysFunctionMapper.selectAllTopFunction(queryMap);
        return sysFunctionList;
    }

    @Override
    public List<Map<String, Object>> selectAllChildren(String id) {
        List<SysFunction> parent = sysFunctionMapper.selectAllChildren(id);
        if (CollectionUtils.isEmpty(parent)) {
            return new ArrayList<>();
        }
        return parent.stream().map(e -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", e.getId());
            map.put("label", e.getName());
            map.put("type", e.getType());
            map.put("state", e.getState());
            map.put("meta", e.getMeta());
            map.put("path", e.getPath());
            map.put("routerPath", e.getRouterPath());
            map.put("component", e.getComponent());
            map.put("componentName", e.getComponentName());
            map.put("leaf", e.isLeaf());
            map.put("showMenu", e.isShowMenu());
            map.put("children", selectAllChildren(e.getId()));
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public String updateStateOpenByIds(List<String> ids) {
        String resStr = "success";
        sysFunctionMapper.updateStateByIds(ids, 1);
        return resStr;
    }

    @Override
    public String updateStateCloseByIds(List<String> ids) {
        String resStr = "success";
        sysFunctionMapper.updateStateByIds(ids, 0);
        return resStr;
    }

    private Object getChildren(String id) {
        List<Object> list = new ArrayList<>();
        List<SysFunction> children = sysFunctionMapper.selectAllChildren(id);
        System.out.println(children);
        for (SysFunction sysFunction : children) {
            if (id.equals(sysFunction.getParentId())) {
                JSONObject obj = new JSONObject(true);
                obj.put("id", sysFunction.getId());
                obj.put("label", sysFunction.getName());
                obj.put("type", sysFunction.getType());
                List<SysFunction> _children = (List<SysFunction>) getChildren(sysFunction.getId());
                if (_children.size() == 0) {
                } else {
                    obj.put("children", _children);
                }
                list.add(obj);
            }
        }
        return list;
    }


    @Override
    public void deleteById(String id) {
        sysFunctionMapper.deleteByPrimaryKey(id);
    }
}

