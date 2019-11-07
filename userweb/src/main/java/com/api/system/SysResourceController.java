package com.api.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dao.entity.system.SysResource;
import com.dto.enums.RspCode;
import com.dto.pojo.system.KeyValues;
import com.dto.pojo.spsys.ResponseMessage;
import com.service.system.SysResourceService;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiezx on 2019-01-10.
 */
@Api(tags = "资源管理")
@RestController
@RequestMapping("sysapi/resource")
public class SysResourceController {
    @Autowired
    private SysResourceService sysResourceService;

    @ApiOperation(value = "资源详情（增删改操作），保存", notes = "资源详情（增删改操作），保存")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage save(@ApiParam(value = "保存的实体JSON字符串,必须携带updateFlg字段") @RequestBody String data, HttpServletRequest req) {
        SysResource sysResource = JSON.parseObject(data, new TypeReference<SysResource>() {
        });
        int saveRes = sysResourceService.save(sysResource);
        if (saveRes == -1) {
            return ResponseMessage.sendDefined(RspCode.SOURCE_EXIST);
        }
        if (saveRes == 1) {
            return ResponseMessage.sendOK(sysResource);
        } else {
            return ResponseMessage.sendError();
        }
    }

    @ApiOperation(value = "资源，根据id查询详情", notes = "资源，根据id查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "String", value = "主键编号")
    })
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public ResponseMessage detail(String id) {
        SysResource sysResource = sysResourceService.selectByPrimaryKey(id);
        return ResponseMessage.sendOK(sysResource);
    }

    @ApiOperation(value = "资源，条件查询", notes = "资源，条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", dataType = "String", value = "机构代码"),
            @ApiImplicitParam(paramType = "query", name = "functionId", dataType = "String", required = true, value = "功能id"),
            @ApiImplicitParam(paramType = "query", name = "name", dataType = "String", value = "资源名称"),
            @ApiImplicitParam(paramType = "query", name = "checked", dataType = "Integer", value = "在查询的当前功能下该按钮是否已勾选（0：未勾选 1：已勾选）"),
    })
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ResponseMessage list(String depCode, String functionId, Integer checked, String name) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("functionId", functionId);
        queryMap.put("checked", checked);
        if (name != null && !"".equals(name)) {
            //关键字空格处理
            String _name = name.replace(" ", "");
            queryMap.put("name", _name);
        }
        if (depCode != null && !"".equals(depCode)) {
            queryMap.put("depCode", DepCodeUtils.getFirstChildDepCodeLike(depCode));
        }
        List<SysResource> sysResourceList = sysResourceService.selectResource(queryMap);
        return ResponseMessage.sendOK(sysResourceList);
    }

    @ApiOperation(value = "资源，启用", notes = "角色，启用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]"),
            @ApiImplicitParam(paramType = "query", name = "state", required = true, dataType = "String", value = "状态,1=启用  0=禁用")
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
            sysResourceService.updateStateOpenByIds(_ids);
        } else {
            sysResourceService.updateStateCloseByIds(_ids);
        }

        return ResponseMessage.sendOK();
    }
    // @ApiOperation(value = "资源，禁用", notes = "角色，禁用")
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
    //     sysResourceService.updateStateCloseByIds(_ids);
    //     return ResponseMessage.sendOK();
    // }

    @ApiOperation(value = "资源，修改是否勾选状态", notes = "资源，修改是否勾选状态")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage update(@ApiParam(value = "保存的实体JSON字符串") @RequestBody String data) {
        JSONObject jsonObject = JSON.parseObject(data);
        String str = jsonObject.getString("ids");
        List<String> ids = Arrays.asList(str.split(","));
        String checked = jsonObject.getString("checked");
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("id", ids);
        queryMap.put("checked", checked);
        int updateRes = sysResourceService.updateStatus(queryMap);
        return ResponseMessage.sendOK(updateRes);
    }

    @ApiOperation(value = "资源，查询所有组件", notes = "资源，查询所有组件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", dataType = "String", value = "机构代码"),
            @ApiImplicitParam(paramType = "query", name = "name", dataType = "String", value = "资源名称"),
    })
    @RequestMapping(value = "selectComponent", method = RequestMethod.POST)
    public ResponseMessage selectComponent(String depCode, String name) {

        Map<String, Object> queryMap = new HashMap<String, Object>();
        if (name != null && !"".equals(name)) {
            //关键字空格处理
            String _name = name.replace(" ", "");
            queryMap.put("name", _name);
        }
        List<SysResource> sysResources = sysResourceService.selectComponent(queryMap);
        return ResponseMessage.sendOK(sysResources);
    }

    @ApiOperation(value = "资源，删除", notes = "资源，删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]")
    })

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseMessage deleteResource(String ids) {
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
        sysResourceService.deleteResourceByIds(_ids);
        return ResponseMessage.sendOK();
    }

}
