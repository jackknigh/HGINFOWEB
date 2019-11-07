package com.api.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dao.entity.system.SysUser;
import com.dao.entity.system.SysUserComponent;
import com.dto.constants.Constants;
import com.dto.enums.RspCode;
import com.dto.pojo.system.KeyValues;
import com.dto.pojo.spsys.ResponseMessage;
import com.github.pagehelper.PageInfo;
import com.service.system.SysResourceService;
import com.service.system.SysUserComponentService;
import com.service.system.SysUserDepartService;
import com.service.system.SysUserService;
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
import java.util.Objects;

/**
 * Created by Xiezx on 2019-01-08.
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("sysapi/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserComponentService sysUserComponentService;
    @Autowired
    private SysUserDepartService sysUserDepartService;

    @Autowired
    private SysResourceService sysResourceService;

    @ApiOperation(value = "用户详情，保存", notes = "用户详情，保存")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage save(@ApiParam(value = "保存的实体JSON字符串,必须携带updateFlg字段") @RequestBody String data, HttpServletRequest req) {
        JSONObject jsonObject = JSON.parseObject(data);
        SysUser sysUser = JSON.parseObject(data, new TypeReference<SysUser>() {
        });
        int saveRes = sysUserService.save(sysUser);
        if (-1 == saveRes) {
            return ResponseMessage.sendDefined(RspCode.USER_EXIST);
        }
        return saveRes == 1 ? ResponseMessage.sendOK(sysUser) : ResponseMessage.sendError();
    }

    @ApiOperation(value = "用户，分页查询", notes = "用户，分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", dataType = "String", value = "机构代码"),
            @ApiImplicitParam(paramType = "query", name = "userName", dataType = "String", value = "用户名"),
            @ApiImplicitParam(paramType = "query", name = "departId", dataType = "String", value = "机构id"),
            @ApiImplicitParam(paramType = "query", name = "roleId", dataType = "String", value = "角色id"),
            @ApiImplicitParam(paramType = "query", name = "status", dataType = "Integer", value = "用户状态"),
            @ApiImplicitParam(paramType = "query", name = "currentPage", required = true, dataType = "int", value = "当前页，第一页为1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", required = true, dataType = "int", value = "每页数量，默认传递50")
    })

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ResponseMessage list(String depCode, String userName, String departId, String roleId, Integer status, Integer currentPage, Integer pageSize) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put(Constants.PAGE_CURRENT, currentPage);
        queryMap.put(Constants.PAGE_SIZE, pageSize);
        //queryMap.put("depCode", DepCodeUtils.getFirstChildDepCodeLike(depCode));
        if (!"".equals(userName) && userName != null) {
            String _userName = userName.replace(" ", "");
            queryMap.put("userName", _userName);
        }
        if (!"".equals(departId) && departId != null) {
            queryMap.put("departId", departId);
        } else {
        }
        if (!"".equals(roleId) && roleId != null) {
            queryMap.put("roleId", roleId);
        } else {
        }
        if (!"".equals(status) && status != null) {
            queryMap.put("status", status);
        }
        queryMap.put("remark", "EDIT");
        PageInfo<Object> sysUserPageInfo = sysUserService.search(queryMap);
        return ResponseMessage.sendOK(sysUserPageInfo);
    }

    @ApiOperation(value = "用户，根据id查询详情", notes = "用户，根据id查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "String", value = "主键编号")
    })
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public ResponseMessage detail(String id) {
        SysUser sysUser = sysUserService.selectByPrimaryKey(id);
        return ResponseMessage.sendOK(sysUser);
    }

    @ApiOperation(value = "用户，根据用户名查询", notes = "用户，根据用户名查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userName", required = true, dataType = "String", value = "用户名")
    })
    @RequestMapping(value = "getUserByName", method = RequestMethod.POST)
    public ResponseMessage selectByUserName(String userName) {
        SysUser sysUser = sysUserService.selectByUserName(userName);
        Map map = new HashMap();
        if (Objects.nonNull(sysUser)){
            map.put("id",sysUser.getId());
            map.put("username",userName);
            map.put("nickName",sysUser.getNickName());
        }
        return ResponseMessage.sendOK(map);

    }

    @ApiOperation(value = "用户，批量删除", notes = "用户，批量删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]"),
            @ApiImplicitParam(paramType = "query", name = "delStatus", required = true, dataType = "Integer", value = "用户存在状态：1：未删除,2：已删除")
    })
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseMessage audit(String ids) {
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
        sysUserService.deleteByPrimaryKey(_ids);
        return ResponseMessage.sendOK();
    }

    @ApiOperation(value = "用户详情，保存用户组件详情", notes = "用户详情，保存用户组件详情")
    @RequestMapping(value = "saveComponent", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage saveComponent(@ApiParam(value = "保存的实体JSON字符串,必须携带updateFlg字段") @RequestBody String data, HttpServletRequest req) {
        SysUserComponent sysUserComponent = JSON.parseObject(data, new TypeReference<SysUserComponent>() {
        });
        int saveRes = sysUserComponentService.insert(sysUserComponent);
        if (saveRes == 1)
            return ResponseMessage.sendOK();
        else
            return ResponseMessage.sendError();
    }

    @ApiOperation(value = "用户，启用", notes = "用户，启用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]")
    })
    @RequestMapping(value = "open", method = RequestMethod.POST)
    public ResponseMessage openState(String ids, String state) {
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
        if ("1".equals(state)) {
            sysUserService.updateStateOpenByIds(_ids);
        } else {
            sysUserService.updateStateCloseByIds(_ids);
        }
        return ResponseMessage.sendOK();
    }
    // @ApiOperation(value = "用户，禁用", notes = "用户，禁用")
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
    //     sysUserService.updateStateCloseByIds(_ids);
    //     return ResponseMessage.sendOK();
    // }
}
