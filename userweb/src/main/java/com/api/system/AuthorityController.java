package com.api.system;

import com.alibaba.fastjson.JSONObject;
import com.dao.entity.system.SysResource;
import com.dao.entity.system.SysRoleFunctionResource;
import com.dao.entity.system.SysUser;
import com.dto.enums.RspCode;
import com.dto.pojo.system.KeyValues;
import com.dto.pojo.spsys.ResponseMessage;
import com.service.system.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 权限控制类
 * Created by Xiezx on 2019-01-18.
 */
@Api(tags = "权限管理")
@RestController
@RequestMapping("sysapi/authority")
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysResourceService sysResourceService;


    @ApiOperation(value = "权限详情，显示权限树", notes = "权限详情，显示权限树")
    @RequestMapping(value = "selectAuthorityTree", method = RequestMethod.POST)
    public ResponseMessage selectAuthorityTree(Map<String, Object> queryMap) {
        List<Object> list = authorityService.selectAllTopFunction(queryMap);
        return ResponseMessage.sendOK(list);
    }

    @ApiOperation(value = "权限详情，获取用户功能权限", notes = "权限详情，获取用户功能权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "String", value = "用户主键编号"),
            @ApiImplicitParam(paramType = "query", name = "type", required = false, dataType = "String", value = "查询类型,不传为所有.menu为菜单.其他为菜单外信息")
    })
    @RequestMapping(value = "selectAuthorityByUserId", method = RequestMethod.POST)
    public ResponseMessage selectAuthorityByUserId(String id, String type) {
        SysUser sysUser = sysUserService.selectAuthorityByUserId(id);
        List<String> _roleIds = Arrays.asList(sysUser.getRoleId().split(","));
        Integer functionType = null;
        if (StringUtils.isBlank(type)) {
            functionType = null;
        } else if ("menu".equals(type)) {
            functionType = 1;
        } else {
            functionType = 0;
        }
        List<Object> functions = authorityService.selectFunctionByRoleIds(_roleIds,functionType);
        return ResponseMessage.sendOK(functions);
    }

    @ApiOperation(value = "权限详情，获取用户资源权限", notes = "权限详情，获取用户资源权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String", value = "角色主键编号，[{'value':'id1'},{'value':'id2'}]")
    })
    @RequestMapping(value = "selectAuthorityByFunctionId", method = RequestMethod.POST)
    public ResponseMessage selectAuthorityByFunctionethodId(String ids) {
        List<KeyValues> keyValues = null;
        List<String> _ids = new ArrayList<>();
        try {
            keyValues = JSONObject.parseArray(ids, KeyValues.class);
            for (KeyValues _key : keyValues)
                _ids.add(_key.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.sendDefined(RspCode.JSONERRPR);
        }
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<Object> list3 = new ArrayList<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("ids", _ids);
        List<SysRoleFunctionResource> sysRoleFunctionResources = sysResourceService.selectAuthorityByFunctionId(queryMap);
        for (SysRoleFunctionResource sysRoleFunctionResource : sysRoleFunctionResources) {
            String resourceId = sysRoleFunctionResource.getResourceId();
            List<String> resourceIds = Arrays.asList(resourceId.split(","));
            for (String id : resourceIds) {
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
        List<SysResource> sysResources = sysResourceService.selectResourceByIds(list);
        for (SysResource sysResource : sysResources) {
            list2.add(sysResource.getCode());
        }
        //整理数据
        JSONObject object = new JSONObject(true);
        object.put("roleId", ids);
        object.put("code", list2);
        list3.add(object);
        return ResponseMessage.sendOK(list3);
    }
}
