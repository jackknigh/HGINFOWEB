/*
package com.api.wzzf;

import com.dto.constants.Constants;
import com.dto.pojo.spsys.ResponseMessage;
import com.github.pagehelper.PageInfo;
import com.service.wzzf.ZfEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = "事件查询相关")
@RestController
@RequestMapping("zjReport/ZfEventController")
public class ZfEventController {

    @Autowired
    ZfEventService zfEventService;

    @ApiOperation(value = "区县统计", notes = " 区县统计")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "eventCounty", required = false, dataType = "String", value = "区县名"),
            @ApiImplicitParam(paramType = "query", name = "beginDate", required = true, dataType = "String", value = "开始时间,周期内的第一天00:00:00"),
            @ApiImplicitParam(paramType = "query", name = "endDate", required = true, dataType = "String", value = "结束时间，周期内最后一天的23:59:59")
    })
    @RequestMapping(value = "quXianStat", method = RequestMethod.POST)
    public ResponseMessage quXianStat(String eventCounty,String beginDate, String endDate) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("eventCounty", eventCounty);
        queryMap.put("beginDate", beginDate);
        queryMap.put("endDate", endDate);
        List<?> res = zfEventService.quXianStat(queryMap);
        return ResponseMessage.sendOK(res);
    }

    @ApiOperation(value = "某区县下所有街道统计", notes = " 某区县下所有街道统计")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "eventCounty", required = false, dataType = "String", value = "区县名"),
            @ApiImplicitParam(paramType = "query", name = "beginDate", required = true, dataType = "String", value = "开始时间,周期内的第一天00:00:00"),
            @ApiImplicitParam(paramType = "query", name = "endDate", required = true, dataType = "String", value = "结束时间，周期内最后一天的23:59:59")
    })
    @RequestMapping(value = "jieDaoStat", method = RequestMethod.POST)
    public ResponseMessage jieDaoStat( String eventCounty, String beginDate, String endDate) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("eventCounty", eventCounty);
        queryMap.put("beginDate", beginDate);
        queryMap.put("endDate", endDate);
        List<?> res = zfEventService.jieDaoStat(queryMap);
        return ResponseMessage.sendOK(res);
    }

    @ApiOperation(value = " 紧急事件检索，分页查询", notes = " 紧急事件检索，分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "eventCounty", required = false, dataType = "String", value = "区县名"),
            @ApiImplicitParam(paramType = "query", name = "eventTown", required = false, dataType = "String", value = "街道名"),
            @ApiImplicitParam(paramType = "query", name = "beginDate", required = true, dataType = "String", value = "开始时间,周期内的第一天00:00:00"),
            @ApiImplicitParam(paramType = "query", name = "endDate", required = true, dataType = "String", value = "结束时间，周期内最后一天的23:59:59"),
            @ApiImplicitParam(paramType = "query", name = "currentPage", required = true, dataType = "int", value = "当前页，第一页为1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", required = true, dataType = "int", value = "每页数量，默认传递50")
    })
    @RequestMapping(value = "jinJiEventList", method = RequestMethod.POST)
    public ResponseMessage jinJiEventList(String eventCounty, String eventTown, String beginDate, String endDate, Integer currentPage, Integer pageSize) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put(Constants.PAGE_CURRENT, currentPage);
        queryMap.put(Constants.PAGE_SIZE, pageSize);
        queryMap.put("eventCounty", eventCounty);
        queryMap.put("eventTown", eventTown);
        queryMap.put("beginDate", beginDate);
        queryMap.put("endDate", endDate);
        PageInfo<?> res = zfEventService.jinJiEventList(queryMap);
        return ResponseMessage.sendOK(res);
    }

    @ApiOperation(value = " 按照某种事件类别，检索明细，分页查询", notes = " 按照某种事件类别，检索明细，分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "className", required = false, dataType = "String", value = "类别"),
            @ApiImplicitParam(paramType = "query", name = "beginDate", required = true, dataType = "String", value = "开始时间,周期内的第一天00:00:00"),
            @ApiImplicitParam(paramType = "query", name = "endDate", required = true, dataType = "String", value = "结束时间，周期内最后一天的23:59:59"),
            @ApiImplicitParam(paramType = "query", name = "currentPage", required = true, dataType = "int", value = "当前页，第一页为1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", required = true, dataType = "int", value = "每页数量，默认传递50")
    })
    @RequestMapping(value = "classEventList", method = RequestMethod.POST)
    public ResponseMessage classEventList(String className,String beginDate, String endDate, Integer currentPage, Integer pageSize) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put(Constants.PAGE_CURRENT, currentPage);
        queryMap.put(Constants.PAGE_SIZE, pageSize);
        queryMap.put("className", className);
        queryMap.put("beginDate", beginDate);
        queryMap.put("endDate", endDate);
        PageInfo<?> res = zfEventService.classEventList(queryMap);
        return ResponseMessage.sendOK(res);
    }

}
*/
