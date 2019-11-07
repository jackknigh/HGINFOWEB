package com.api.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dao.db1.system.SysUserMapper;
import com.dao.entity.system.SysRole;
import com.dao.entity.system.SysRoleFunctionResource;
import com.dto.constants.Constants;
import com.dto.enums.RspCode;
import com.dto.pojo.spsys.ResponseMessage;
import com.dto.pojo.system.KeyValues;
import com.github.pagehelper.PageInfo;
import com.service.system.SysRoleFunctionResourceService;
import com.service.system.SysRoleService;
import com.service.system.SysUserService;
import com.utils.sys.DepCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiezx on 2019-01-08.
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("sysapi/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleFunctionResourceService sysRoleFunctionResourceService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "角色详情，保存", notes = "角色详情，保存")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage save(@ApiParam(value = "保存的实体JSON字符串,必须携带updateFlg字段") @RequestBody String data, HttpServletRequest req) {
        JSONObject jsonObject = JSON.parseObject(data);
        String roleName = jsonObject.getString("name");
        String updateFlg = jsonObject.getString("updateFlg");
        String functionId = jsonObject.getString("functionId");
        String resourceId = jsonObject.getString("resourceId");
        String state = jsonObject.getString("state");
        //角色主键id,新增时用
        String id = jsonObject.getString("id");
        JSONObject roleJson = new JSONObject(true);
        if (id != null) {
            roleJson.put("id", id);
        }
        roleJson.put("name", roleName);
        roleJson.put("updateFlg", updateFlg);
        roleJson.put("state", state);
        String s = JSON.toJSONString(roleJson);
        SysRole sysRole = JSON.parseObject(s, new TypeReference<SysRole>() {
        });
        int i = sysRoleService.save(sysRole);
        //获取新增角色的UUID
        String roleId = sysRole.getId();
        JSONObject authorityJson = new JSONObject(true);
        authorityJson.put("updateFlg", updateFlg);
        authorityJson.put("function_id", functionId);
        authorityJson.put("resource_id", resourceId);
        authorityJson.put("role_id", roleId);
        String authority = JSON.toJSONString(authorityJson);
        SysRoleFunctionResource sysRoleFunctionResource = JSON.parseObject(authority, new TypeReference<SysRoleFunctionResource>() {
        });
        sysRoleFunctionResourceService.save(sysRoleFunctionResource);

        return ResponseMessage.sendOK(sysRole);

    }

    @ApiOperation(value = "角色，根据id查询详情", notes = "角色，根据id查询详情")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "String", value = "主键编号")})
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public ResponseMessage detail(String id) {
        SysRole sysOperation = sysRoleService.selectByPrimaryKey(id);
        return ResponseMessage.sendOK(sysOperation);
    }

    @ApiOperation(value = "角色，分页查询", notes = "角色，分页查询")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "name", dataType = "String", value = "角色名称"),
            @ApiImplicitParam(paramType = "query", name = "depCode", dataType = "String", value = "机构编码"),
            @ApiImplicitParam(paramType = "query", name = "currentPage", required = true, dataType = "int",
                    value = "当前页，第一页为1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", required = true, dataType = "int",
                    value = "每页数量，默认传递50")})
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ResponseMessage list(String name, String depCode, Integer currentPage, Integer pageSize) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put(Constants.PAGE_CURRENT, currentPage);
        queryMap.put(Constants.PAGE_SIZE, pageSize);
        if (name != null && !"".equals(name)) {
            String _name = name.replace(" ", "");
            queryMap.put("name", _name);
        }
        if (depCode != null && !"".equals(depCode)) {
            queryMap.put("depCode", DepCodeUtils.getFirstChildDepCodeLike(depCode));
        }
        PageInfo<SysRole> sysSysRolePageInfo = sysRoleService.search(queryMap);
        return ResponseMessage.sendOK(sysSysRolePageInfo);
    }

    @ApiOperation(value = "角色，显示所角色名", notes = "角色，显示所角色名")
    @RequestMapping(value = "selectAllRoleName", method = RequestMethod.POST)
    public ResponseMessage selectAllRoleName() {
        List<?> sysRoles = sysRoleService.selectAllRoleName();
        return ResponseMessage.sendOK(sysRoles);
    }

    @ApiOperation(value = "角色，启用", notes = "角色，启用")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String",
            value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]"),
            @ApiImplicitParam(paramType = "query", name = "state", required = true, dataType = "String",
                    value = "状态,1=启用  0=禁用")})
    @RequestMapping(value = "open", method = RequestMethod.POST)
    public ResponseMessage openState(String ids, String state) {
        List<KeyValues> keyValues = null;
        List<String> _ids = new ArrayList<>();
        try {
            keyValues = JSONObject.parseArray(ids, KeyValues.class);
            for (KeyValues _key : keyValues) {
                _ids.add(_key.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.sendDefined(RspCode.JSONERRPR);
        }
        if ("1".equals(state)) {
            sysRoleService.updateStateOpenByIds(_ids);
        } else {
            sysRoleService.updateStateCloseByIds(_ids);
        }
        return ResponseMessage.sendOK();
    }

    @ApiOperation(value = "角色，删除", notes = "角色，删除")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String",
            value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]"),})
    @RequestMapping(value = "del", method = RequestMethod.POST)
    public ResponseMessage delRole(String ids) {
        List<KeyValues> keyValues = null;
        List<String> _ids = new ArrayList<>();
        try {
            keyValues = JSONObject.parseArray(ids, KeyValues.class);
            for (KeyValues _key : keyValues) {
                _ids.add(_key.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.sendDefined(RspCode.JSONERRPR);
        }
        for (String id : _ids) {
            int i = sysUserMapper.checkRoleExist("%" + id + "%");
            if (i > 0) {
                return ResponseMessage.sendDefined(RspCode.ROLE_USED);
            }
        }
        Map<String, List<String>> map = sysRoleService.delByIds(_ids);
        return ResponseMessage.sendOK(map);
    }
    // @ApiOperation(value = "角色，禁用", notes = "角色，禁用")
    // @ApiImplicitParams({
    //         @ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]")
    // })
    //
    // @RequestMapping(value = "close", method = RequestMethod.POST)
    // public ResponseMessage closeState(String ids) {
    //     List<KeyValues> keyValues = null;
    //     List<String> _ids = new ArrayList<>();
    //     try {
    //         keyValues = JSONObject.parseArray(ids, KeyValues.class);
    //         for (KeyValues _key : keyValues)
    //             _ids.add(_key.getValue());
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseMessage.sendDefined(RspCode.JSONERRPR);
    //     }
    //     sysRoleService.updateStateCloseByIds(_ids);
    //     return ResponseMessage.sendOK();
    // }

}

