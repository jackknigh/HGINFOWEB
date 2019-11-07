package com.api.decisionForm;

import com.dto.pojo.sys.ResponseMessage;
import com.service.decisionForm.QueryInOutShipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "决策系统接口")
@RestController
@RequestMapping("sysapi/DecisionForm")
public class QueryInOutShipController {

    @Autowired
    QueryInOutShipService queryInOutShipService;

    @ApiOperation(value = "船舶进出情况查询，产品", notes = "船舶进出情况查询，按产品名求平均值,按AvgShipNetWeight降序")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "queryInOutShipByGoodsName", method = RequestMethod.POST)
    public ResponseMessage queryInOutShipByGoodsName(String beginTime, String endTime) {
        return ResponseMessage.sendOK(queryInOutShipService.queryInOutShipByGoodsName(beginTime,endTime));
    }

    @ApiOperation(value = "船舶进出情况查询，批次", notes = "船舶进出情况查询，按批次分别求装卸作业数量平均值")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "queryInOutShipByBatch", method = RequestMethod.POST)
    public ResponseMessage queryInOutShipByBatch(String beginTime, String endTime) {
        return ResponseMessage.sendOK(queryInOutShipService.queryInOutShipByBatch(beginTime,endTime));
    }

    @ApiOperation(value = "船舶进出情况查询，安全等级", notes = "船舶进出情况查询，按安全等级分别求装卸作业数量平均值")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "queryInOutShipByIsSafety", method = RequestMethod.POST)
    public ResponseMessage queryInOutShipByIsSafety(String beginTime, String endTime) {
        return ResponseMessage.sendOK(queryInOutShipService.queryInOutShipByIsSafety(beginTime,endTime));
    }
    @ApiOperation(value = "船舶进出情况查询，基础数据", notes = "船舶进出情况查询，基础数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "queryInOutShipBasics", method = RequestMethod.POST)
    public ResponseMessage queryInOutShipBasics(String beginTime, String endTime) {
        return ResponseMessage.sendOK(queryInOutShipService.queryInOutShipBasics(beginTime,endTime));
    }

    @ApiOperation(value = "船舶进出情况查询，泊位", notes = "船舶进出情况查询，按泊位求平均值,按AvgShipNetWeight降序")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "queryInOutShipByBerthID", method = RequestMethod.POST)
    public ResponseMessage queryInOutShipByBerthID(String beginTime, String endTime) {
        return ResponseMessage.sendOK(queryInOutShipService.queryInOutShipByBerthID(beginTime,endTime));
    }
}
