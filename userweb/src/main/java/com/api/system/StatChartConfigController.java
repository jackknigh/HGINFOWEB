package com.api.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dao.db1.zjstat.entity.StatChartConfig;
import com.dto.enums.RspCode;
import com.dto.pojo.spsys.ResponseMessage;
import com.dto.pojo.system.KeyValues;
import com.github.pagehelper.PageInfo;
import com.service.system.StatChartConfigService;
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

@Api(tags = "echart保存配置")
@RestController
@RequestMapping("ZjStata/chartConfig")
public class StatChartConfigController {

    @Autowired
    private StatChartConfigService chartConfigService;

    @ApiOperation(value = "定时任务维护台账", notes = "定时任务维护台账")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "currentPage", required = true, dataType = "int", value = "当前页，第一页为1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", required = true, dataType = "int", value = "每页数量，默认传递50"),
            @ApiImplicitParam(paramType = "query", name = "depCode", required = true, dataType = "String", value = "机构编号"),
            @ApiImplicitParam(paramType = "query", name = "userId", required = true, dataType = "String", value = "用户编号")
    })
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ResponseMessage list(Integer currentPage, Integer pageSize, String depCode, String userId) {
        PageInfo pageInfo = chartConfigService.list(currentPage, pageSize,depCode,userId);
        return ResponseMessage.sendOK(pageInfo);
    }

    @ApiOperation(value = "定时任务维护台账，详情", notes = "定时任务维护台账，详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "String", value = "主键编号")
    })
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public ResponseMessage detail(String id) {
        StatChartConfig statChartConfig = chartConfigService.detail(id);
        return ResponseMessage.sendOK(statChartConfig);
    }

    @ApiOperation(value = "定时任务维护详情，保存", notes = "定时任务维护详情，保存")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResponseMessage save(@ApiParam(value = "保存的实体JSON字符串,必须携带updateFlg字段,值为update") @RequestBody String data, HttpServletRequest req) {
        StatChartConfig statChartConfig = JSON.parseObject(data, new TypeReference<StatChartConfig>() {
        });

        int save = chartConfigService.save(statChartConfig);
        if (save == 1) {
            return ResponseMessage.sendOK(new HashMap(){{
                put("id",statChartConfig.getId());
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

        chartConfigService.delete(_ids);
        return ResponseMessage.sendOK();
    }
}
