package com.api.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dao.entity.system.SysFunction;
import com.dto.enums.RspCode;
import com.dto.pojo.system.KeyValues;
import com.dto.pojo.spsys.ResponseMessage;
import com.service.system.SysFunctionService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * * SysFunction控制类
 * * Created by Xiezx on 2019-01-12.
 */
@Api(tags = "功能管理")
@RestController
@RequestMapping("sysapi/function")
public class SysFunctionController {
    @Autowired
    private SysFunctionService sysFunctionService;

    @ApiOperation(value = "功能详情，保存", notes = "功能详情，保存")

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage save(@ApiParam(value = "保存的实体JSON字符串,必须携带updateFlg字段,值为save") @RequestBody String data, HttpServletRequest req) {
        SysFunction sysFunction = JSON.parseObject(data, new TypeReference<SysFunction>() {
        });

        sysFunction.setCreateDate(new Date());

        int saveRes = sysFunctionService.save(sysFunction);
        if (saveRes == 1) {
            return ResponseMessage.sendOK(sysFunction);
        } else {
            return ResponseMessage.sendError();
        }
    }

    // @ApiOperation(value = "功能，显示所有功能名", notes = "功能，显示所有功能名")
    // @RequestMapping(value = "selectAllFunctionName", method = RequestMethod.POST)
    // public ResponseMessage selectAllFunctionName(Map<String, Object> queryMap) {
    //     List<Object> list = sysFunctionService.selectAllTopFunction(queryMap);
    //     return ResponseMessage.sendOK(list);
    // }

    @ApiOperation(value = "功能，启用", notes = "角色，启用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]")
    })
    @RequestMapping(value = "open", method = RequestMethod.POST)
    public ResponseMessage openState(String ids) {
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
        sysFunctionService.updateStateOpenByIds(_ids);
        return ResponseMessage.sendOK();
    }

    @ApiOperation(value = "功能，禁用", notes = "角色，禁用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]")
    })
    @RequestMapping(value = "close", method = RequestMethod.POST)
    public ResponseMessage closeState(String ids) {
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
        sysFunctionService.updateStateCloseByIds(_ids);
        return ResponseMessage.sendOK();
    }


    @ApiOperation(value = "功能，删除", notes = "角色，删除")
    @ApiImplicitParams({
                               @ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]")
                       })
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseMessage del(String ids) {
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
        return ResponseMessage.sendOK(sysFunctionService.deleteByPrimaryKey(_ids));
    }

    @ApiOperation(value = "功能，显示所有功能名", notes = "功能，显示所有功能名")
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ResponseMessage list() {
        List<Map<String, Object>> list = sysFunctionService.selectAllChildren("0");
        return ResponseMessage.sendOK(list);
    }

    @ApiOperation(value = "功能，根据id查询详情", notes = "功能，根据id查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "String", value = "主键编号")
    })
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public ResponseMessage detail(String id) {
        return ResponseMessage.sendOK(sysFunctionService.selectByPrimaryKey(id));
    }

}
