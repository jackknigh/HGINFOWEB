package com.api.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dao.entity.system.SysDicColumnValue;
import com.dto.enums.RspCode;
import com.dto.pojo.system.KeyValues;
import com.dto.pojo.spsys.ResponseMessage;
import com.github.pagehelper.PageInfo;
import com.service.system.SysDicColumnValueService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Api(tags = "字典列取值信息接口")
@RestController
@RequestMapping("sysapi/SysDicColumnValueController")
public class SysDicColumnValueController {

    @Autowired
    private SysDicColumnValueService sysDicColumnValueService;

    @ApiOperation(value = "字典列取值信息台账", notes = "字典列取值信息台账")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "currentPage", required = true, dataType = "int", value = "当前页，第一页为1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", required = true, dataType = "int", value = "每页数量，默认传递50")
    })
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ResponseMessage list(Integer currentPage, Integer pageSize) {
        PageInfo pageInfo = sysDicColumnValueService.list(currentPage, pageSize);
        return ResponseMessage.sendOK(pageInfo);
    }

    @ApiOperation(value = "字典列取值信息台账，详情", notes = "字典列取值信息台账，详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "String", value = "主键编号")
    })
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public ResponseMessage detail(String id) {
        SysDicColumnValue sysDicColumnValue = sysDicColumnValueService.detail(id);
        return ResponseMessage.sendOK(sysDicColumnValue);
    }

    @ApiOperation(value = "字典列取值信息详情，保存", notes = "字典列取值信息详情，保存")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResponseMessage save(@ApiParam(value = "保存的实体JSON字符串,必须携带updateFlg字段,值为update") @RequestBody String data, HttpServletRequest req) {
        SysDicColumnValue sysDicColumnValue = JSON.parseObject(data, new TypeReference<SysDicColumnValue>() {
        });

        int save = sysDicColumnValueService.save(sysDicColumnValue);
        if (save == 1) {
            return ResponseMessage.sendOK(new HashMap(){{
                put("id",sysDicColumnValue.getId());
            }});
        } else {
            return ResponseMessage.sendError();
        }
    }

    @ApiOperation(value = "批量启用或停用", notes = "批量启用或停用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]"),
            @ApiImplicitParam(paramType = "query", name = "state", required = true, dataType = "Integer", value = "启用状态：0：停用，1：启用")
    })
    @RequestMapping(value = "startUse", method = RequestMethod.POST)
    public ResponseMessage startUp(String ids,Integer state) {
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

        sysDicColumnValueService.startUse(_ids,state);
        return ResponseMessage.sendOK();
    }

    @ApiOperation(value = "批量删除", notes = "批量删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]"),
    })
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseMessage delete(String ids) {
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

        sysDicColumnValueService.delete(_ids);
        return ResponseMessage.sendOK();
    }
}
