package com.service.system.impl;

import com.alibaba.fastjson.JSONObject;
import com.dao.db1.system.SysFunctionMapper;
import com.dao.db1.system.SysResourceMapper;
import com.dao.db1.system.SysRoleFunctionResourceMapper;
import com.dao.entity.system.SysFunction;
import com.dao.entity.system.SysResource;
import com.dao.entity.system.SysRoleFunctionResource;
import com.service.system.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 权限控制接口实现类
 * Created by Xiezx on 2019-01-18.
 */
@Transactional
@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private SysResourceMapper sysResourceMapper;
    @Autowired
    private SysFunctionMapper sysFunctionMapper;
    @Autowired
    private SysRoleFunctionResourceMapper sysRoleFunctionResourceMapper;

    @Override
    public List<Object> selectAllTopFunction(Map<String, Object> queryMap) {
        List<SysFunction> sysFunctionList = sysFunctionMapper.selectAllTopFunction(queryMap);
        List<Object> list = new ArrayList<>();
        for (SysFunction sysFunction : sysFunctionList) {
            JSONObject treeObject = new JSONObject(true);
            treeObject.put("id", sysFunction.getId());
            treeObject.put("label", sysFunction.getName());
            treeObject.put("type", sysFunction.getType());
            treeObject.put("sourceFlag", false);
            List<SysFunction> children = (List<SysFunction>) getChildren(sysFunction.getId());
            if (children.size() == 0) {
            } else {
                treeObject.put("children", children);
            }
            list.add(treeObject);
        }
        return list;
    }

    @Override
    public List<SysFunction> selectAllChildren(String id) {
        return sysFunctionMapper.selectAllChildren(id);
    }

    //递归显示所有功能和资源
    private Object getChildren(String id) {
        List<Object> list = new ArrayList<>();
        List<SysFunction> children = sysFunctionMapper.selectAllChildren(id);
        for (SysFunction sysFunction : children) {
            if (id.equals(sysFunction.getParentId())) {
                JSONObject obj = new JSONObject(true);
                obj.put("id", sysFunction.getId());
                obj.put("label", sysFunction.getName());
                obj.put("type", sysFunction.getType());
                obj.put("sourceFlag", false);
                List<SysFunction> _children = (List<SysFunction>) getChildren(sysFunction.getId());
                if (_children.size() == 0) {
                    List<SysResource> sysResources = sysResourceMapper.selectResourceByFunctionId(sysFunction.getId());
                    List<Object> listResource = new ArrayList<>();
                    for (SysResource sysResource : sysResources) {
                        JSONObject objResource = new JSONObject(true);
                        objResource.put("id", sysResource.getId());
                        objResource.put("label", sysResource.getName());
                        objResource.put("type", sysResource.getType());
                        objResource.put("sourceFlag", true);
                        listResource.add(objResource);
                    }
                    obj.put("children", listResource);
                } else {
                    obj.put("children", _children);
                }
                list.add(obj);
            }
        }
        return list;
    }

    @Override
    public List<Object> selectFunctionByRoleIds(List<String> ids, Integer functionType) {
        List<SysRoleFunctionResource> sysRoleFunctionResources = sysRoleFunctionResourceMapper.selectByRoleIds(ids);
        List<String> list = new ArrayList<>();
        for (SysRoleFunctionResource sysRoleFunctionResource : sysRoleFunctionResources) {
            String functionId = sysRoleFunctionResource.getFunctionId();
            List<String> functionIds = Arrays.asList(functionId.split(","));
            for (String id : functionIds) {
                list.add(id);
            }
        }
        //去重
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        System.out.println(list);
        Map<String, Object> queryMap = new HashMap<>();
        //先查询出顶级功能
        queryMap.put("functionIds", list);
        queryMap.put("type",functionType);
        List<SysFunction> sysFunctionList = sysFunctionMapper.selectAllTopFunction(queryMap);
        List<Object> list1 = new ArrayList<>();
        for (SysFunction sysFunction : sysFunctionList) {
            JSONObject treeObject = new JSONObject(true);
            treeObject.put("id", sysFunction.getId());
            treeObject.put("name", sysFunction.getName());
            treeObject.put("path", sysFunction.getFunctionPath());
            treeObject.put("icon", sysFunction.getIconClass());
            treeObject.put("closeStatus", sysFunction.getCloseStatus());
            treeObject.put("parentId", sysFunction.getParentId());
            treeObject.put("sort", sysFunction.getSort());
            treeObject.put("meta", sysFunction.getMeta());
            treeObject.put("path", sysFunction.getPath());
            treeObject.put("routerPath", sysFunction.getRouterPath());
            treeObject.put("component", sysFunction.getComponent());
            treeObject.put("componentName", sysFunction.getComponentName());
            treeObject.put("showMenu", sysFunction.isShowMenu());
            treeObject.put("leaf", sysFunction.isLeaf());
            treeObject.put("code", sysFunction.getCode());
            List<SysFunction> children = (List<SysFunction>) getFunctionChildren(sysFunction.getId(), list,functionType);
            if (children.size() == 0) {
            } else {
                treeObject.put("children", children);
            }
            list1.add(treeObject);
        }
        return list1;
    }

    //递归只显示全部功能
    private Object getFunctionChildren(String id, List<String> functionIds, Integer functionType) {
        List<Object> list = new ArrayList<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("id", id);
        queryMap.put("functionIds", functionIds);
        queryMap.put("type", functionType);
        List<SysFunction> children = sysFunctionMapper.selectAllChildrenByType(queryMap);
        for (SysFunction sysFunction : children) {
            if (id.equals(sysFunction.getParentId())) {
                JSONObject obj = new JSONObject(true);
                obj.put("id", sysFunction.getId());
                obj.put("name", sysFunction.getName());
                obj.put("path", sysFunction.getFunctionPath());
                obj.put("icon", sysFunction.getIconClass());
                obj.put("closeStatus", sysFunction.getCloseStatus());
                obj.put("parentId", sysFunction.getParentId());
                obj.put("sort", sysFunction.getSort());
                obj.put("meta", sysFunction.getMeta());
                obj.put("path", sysFunction.getPath());
                obj.put("routerPath", sysFunction.getRouterPath());
                obj.put("component", sysFunction.getComponent());
                obj.put("componentName", sysFunction.getComponentName());
                obj.put("showMenu", sysFunction.isShowMenu());
                obj.put("leaf", sysFunction.isLeaf());
                obj.put("code", sysFunction.getCode());
                List<SysFunction> _children = (List<SysFunction>) getFunctionChildren(sysFunction.getId(), functionIds, functionType);
                if (_children.size() == 0) {
                } else {
                    obj.put("children", _children);
                }
                list.add(obj);
            }
        }
        return list;
    }
}
