package com.api.decisionForm;

import com.dto.pojo.sys.ResponseMessage;
import com.service.decisionForm.ShipPerformanceService;
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
public class ShipPerformanceController {

    @Autowired
    ShipPerformanceService shipPerformanceService;

   /* @ApiOperation(value = "船舶数量对比，基础数据", notes = "船舶数量对比，基础数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "boatComparison", method = RequestMethod.POST)
    public ResponseMessage boatComparison(String beginTime, String endTime) {
        return ResponseMessage.sendOK(shipPerformanceService.ShipPerformance(beginTime,endTime));
    }*/

    @ApiOperation(value = "船舶数量对比,查询所有", notes = "船舶数量对比查询所有")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "boatSelectAll", method = RequestMethod.POST)
    public ResponseMessage boatSelectAll(String beginTime, String endTime) {
        return ResponseMessage.sendOK(shipPerformanceService.boatSelectAll(beginTime,endTime));
    }

    @ApiOperation(value = "船舶数量对比,按照客户统计三个不同百分比", notes = "按照客户统计三个不同百分比")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "boatBycustomer", method = RequestMethod.POST)
    public ResponseMessage boatBycustomer(String beginTime, String endTime) {
        return ResponseMessage.sendOK(shipPerformanceService.boatBycustomer(beginTime,endTime));
    }
}
