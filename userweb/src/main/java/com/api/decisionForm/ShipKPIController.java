package com.api.decisionForm;

import com.dto.pojo.sys.ResponseMessage;
import com.service.decisionForm.ShipKPIService;
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
public class ShipKPIController {
    @Autowired
    ShipKPIService shipKPIService;

    @ApiOperation(value = "船舶KPI，基础数据", notes = "船舶KPI，基础数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "shipKPIBasics", method = RequestMethod.POST)
    public ResponseMessage shipKPIBasics(String beginTime, String endTime) {
        return ResponseMessage.sendOK(shipKPIService.shipKPIBasics(beginTime,endTime));
    }

    @ApiOperation(value = "船舶KPI，泊位", notes = "船舶KPI，以泊位求装卸速率平均值")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "shipKPIAverage", method = RequestMethod.POST)
    public ResponseMessage shipKPIAverage(String beginTime, String endTime) {
        return ResponseMessage.sendOK(shipKPIService.shipKPIAverage(beginTime,endTime));
    }
    @ApiOperation(value = "船舶KPI，船名", notes = "船舶KPI，以船名求装卸速率平均值")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "shipKPIShipName", method = RequestMethod.POST)
    public ResponseMessage shipKPIShipName(String beginTime, String endTime) {
        return ResponseMessage.sendOK(shipKPIService.shipKPIShipName(beginTime,endTime));
    }
    @ApiOperation(value = "船舶KPI，Top", notes = "船舶KPI，Top")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "shipKPIDesc", method = RequestMethod.POST)
    public ResponseMessage shipKPIDesc(String beginTime, String endTime) {
        return ResponseMessage.sendOK(shipKPIService.shipKPIDesc(beginTime,endTime));
    }
    @ApiOperation(value = "船舶KPI，Bottom", notes = "船舶KPI，Bottom")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "shipKPIAsc", method = RequestMethod.POST)
    public ResponseMessage shipKPIAsc(String beginTime, String endTime) {
        return ResponseMessage.sendOK(shipKPIService.shipKPIAsc(beginTime,endTime));
    }
}
