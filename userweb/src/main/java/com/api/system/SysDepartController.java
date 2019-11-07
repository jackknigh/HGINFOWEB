package com.api.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dao.entity.system.SysDepart;
import com.dto.enums.RspCode;
import com.dto.pojo.system.KeyValues;
import com.dto.pojo.spsys.ResponseMessage;
import com.service.system.SysDepartService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Xiezx on 2019-01-08.
 */
@Api(tags = "机构管理")
@RestController
@RequestMapping("sysapi/depart")
public class SysDepartController {
    @Autowired
    private SysDepartService sysDepartService;

    @ApiOperation(value = "机构详情，保存", notes = "机构详情，保存")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage save(@ApiParam(value = "保存的实体JSON字符串,必须携带updateFlg字段,值为save") @RequestBody String data, HttpServletRequest req) {
        SysDepart sysDepart = JSON.parseObject(data, new TypeReference<SysDepart>() {
        });
        sysDepart.setCreateDate(new Date());
        int saveRes = sysDepartService.save(sysDepart);
        if (-1 == saveRes) {
            return ResponseMessage.sendDefined(RspCode.DEPT_EXIST);
        }
        if (saveRes == 1)
            return ResponseMessage.sendOK(new HashMap() {{
                put("id", sysDepart.getId());
            }});
        else
            return ResponseMessage.sendError();
    }

    @ApiOperation(value = "机构，机构列表查询", notes = "机构，机构列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", required = true, dataType = "String", value = "机构代码"),
    })
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ResponseMessage list(String depCode) {
        // SysDepart sysDepart = ;
        // List<SysDepart> sysDeparts = sysDepartService.selectAll(depCode);
        // sysDepart.setChildRepDepConfigs(sysDeparts);
        return ResponseMessage.sendOK(sysDepartService.selectAllTopDepart());
    }

    @ApiOperation(value = "机构，启用", notes = "角色，启用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCodes", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'depCode1'},{'value':'depCode1'}]")
    })
    @RequestMapping(value = "open", method = RequestMethod.POST)
    public ResponseMessage openState(String depCodes, String state) {
        List<KeyValues> keyValues = null;
        List<String> _depCodes = new ArrayList<>();
        try {
            keyValues = JSONObject.parseArray(depCodes, KeyValues.class);
            for (KeyValues _key : keyValues)
                _depCodes.add(_key.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.sendDefined(RspCode.JSONERRPR);
        }
        if ("1".equals(state)) {
            sysDepartService.updateStateOpenByCodes(_depCodes);
        } else {
            sysDepartService.updateStateCloseByCodes(_depCodes);
        }
        return ResponseMessage.sendOK();
    }

    @ApiOperation(value = "机构，禁用", notes = "角色，禁用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCodes", required = true, dataType = "String", value = "机构编号JSON字符串,[{'value':'depCode1'},{'value':'depCode1'}]")
    })

    @RequestMapping(value = "close", method = RequestMethod.POST)
    public ResponseMessage closeState(String depCodes) {
        List<KeyValues> keyValues = null;
        List<String> _depCodes = new ArrayList<>();
        try {
            keyValues = JSONObject.parseArray(depCodes, KeyValues.class);
            for (KeyValues _key : keyValues)
                _depCodes.add(_key.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.sendDefined(RspCode.JSONERRPR);
        }
        sysDepartService.updateStateCloseByCodes(_depCodes);
        return ResponseMessage.sendOK();
    }


    @ApiOperation(value = "机构，查询所有上级详情", notes = "机构，查询所有上级详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "String", value = "主键编号")
    })

    @RequestMapping(value = "pid", method = RequestMethod.POST)
    public ResponseMessage pid(String id) {
        List<Object> sysDepartList = sysDepartService.selectByPrimaryKey(id);
        return ResponseMessage.sendOK(sysDepartList);

    }

    @ApiOperation(value = "一次显示两级机构嵌套树型,下拉框调用", notes = "一次显示两级机构嵌套树型,下拉框调用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCodes", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'depCode1'},{'value':'depCode1'}]")
    })
    @RequestMapping(value = "listAll", method = RequestMethod.POST)
    public ResponseMessage listAll(String depcode) {
        return ResponseMessage.sendOK(sysDepartService.listAll(depcode));
    }

    @ApiOperation(value = "机构详情", notes = "机构详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", required = true, dataType = "String", value = "当前机构编号")
    })
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public ResponseMessage detail(String depCode) {
        return ResponseMessage.sendOK(sysDepartService.selectByDepCode(depCode));
    }

    @ApiOperation(value = "一次性查询所有子机构", notes = "一次性查询所有子机构")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", required = true, dataType = "String", value = "当前机构编号")
    })
    @RequestMapping(value = "listAllChildDep", method = RequestMethod.POST)
    public ResponseMessage listAllChildDep(String depCode) {
        return ResponseMessage.sendOK(sysDepartService.selectAll(depCode));
    }

    /*@ApiOperation(value = "一次性查询所有子机构嵌套树型", notes = "一次性查询所有子机构嵌套树型")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", required = true, dataType = "String", value = "当前机构编号")
    })
    @RequestMapping(value = "listAllChildDepTree", method = RequestMethod.POST)
    public ResponseMessage listAllChildDepTree(String depCode) {
        return ResponseMessage.sendOK(sysDepartService.selectAllTree(depCode));
    }*/

    @ApiOperation(value = "查询一级子机构", notes = "查询一级子机构")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", required = true, dataType = "String", value = "当前机构编号")
    })
    @RequestMapping(value = "listAllChildDepAjax", method = RequestMethod.POST)
    public ResponseMessage listAllChildDepAjax(String depCode) {
        return ResponseMessage.sendOK(sysDepartService.selectFirstChildAll(depCode));
    }

}
