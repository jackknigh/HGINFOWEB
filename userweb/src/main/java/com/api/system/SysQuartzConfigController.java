package com.api.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dao.entity.system.SysQuartzConfig;
import com.dto.enums.RspCode;
import com.dto.pojo.spsys.ResponseMessage;
import com.dto.pojo.system.KeyValues;
import com.github.pagehelper.PageInfo;
import com.service.system.SysQuartzConfigService;
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

@Api(tags = "定时任务维护")
@RestController
@RequestMapping("sysapi/quartzConfig")
public class SysQuartzConfigController {

    @Autowired
    private SysQuartzConfigService quartzConfigService;



    @ApiOperation(value = "定时任务维护台账", notes = "定时任务维护台账")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "currentPage", required = true, dataType = "int", value = "当前页，第一页为1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", required = true, dataType = "int", value = "每页数量，默认传递50"),
            @ApiImplicitParam(paramType = "query", name = "depCode", required = true, dataType = "String", value = "机构编号"),
            @ApiImplicitParam(paramType = "query", name = "userId", required = true, dataType = "String", value = "用户编号"),
            @ApiImplicitParam(paramType = "query", name = "taskType", required = true, dataType = "String", value = "查询任务类型")
    })
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ResponseMessage list(Integer currentPage, Integer pageSize,String depCode,String userId,String taskType) {
        PageInfo pageInfo = quartzConfigService.list(currentPage, pageSize,depCode,userId,taskType);
        return ResponseMessage.sendOK(pageInfo);
    }

    @ApiOperation(value = "定时任务维护台账，详情", notes = "定时任务维护台账，详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "String", value = "主键编号")
    })
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public ResponseMessage detail(String id) {
        SysQuartzConfig sysQuartzConfig = quartzConfigService.detail(id);
        return ResponseMessage.sendOK(sysQuartzConfig);
    }

    @ApiOperation(value = "定时任务维护详情，保存", notes = "定时任务维护详情，保存")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResponseMessage save(@ApiParam(value = "保存的实体JSON字符串,必须携带updateFlg字段,值为update") @RequestBody String data, HttpServletRequest req) {
        SysQuartzConfig sysQuartzConfig = JSON.parseObject(data, new TypeReference<SysQuartzConfig>() {
        });

        int save = quartzConfigService.save(sysQuartzConfig);
        if (save == 1) {
            return ResponseMessage.sendOK(new HashMap(){{
                put("id",sysQuartzConfig.getId());
            }});
        } else {
            return ResponseMessage.sendError();
        }
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

        quartzConfigService.delete(_ids);
        return ResponseMessage.sendOK();
    }

    @ApiOperation(value = "批量启用/停用", notes = "批量启用/停用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]"),
            @ApiImplicitParam(paramType = "query", name = "state", required = true, dataType = "String", value = "状态值 1：启用，-1：停用"),
    })
    @RequestMapping(value = "startUse", method = RequestMethod.POST)
    public ResponseMessage startUse(String ids,String state) {
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
        try {
            quartzConfigService.startUse(_ids,state);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseMessage.sendOK();
    }
}
