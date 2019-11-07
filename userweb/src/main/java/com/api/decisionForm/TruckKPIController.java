package com.api.decisionForm;


import com.dto.pojo.sys.ResponseMessage;
import com.service.decisionForm.TruckKPIService;
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
public class TruckKPIController {

    @Autowired
    TruckKPIService truckKPIService;

    @ApiOperation(value = "车辆KPI依据产品名", notes = "车辆KPI依据产品名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "truckKPIByGoodsName", method = RequestMethod.POST)
    public ResponseMessage truckKPIByGoodsName(String beginTime, String endTime) {
       // System.out.println(new Date());
        return ResponseMessage.sendOK(truckKPIService.truckKPIBySome(beginTime,endTime,"GoodsName"));
    }


    @ApiOperation(value = "车辆KPI依据装车台", notes = "车辆KPI依据装车台")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "truckKPIByLoadingBay", method = RequestMethod.POST)
    public ResponseMessage truckKPIByLoadingBay(String beginTime, String endTime) {
        return ResponseMessage.sendOK(truckKPIService.truckKPIBySome(beginTime,endTime,"LoadingBay"));
    }

    @ApiOperation(value = "车辆KPI客户平均装卸速率降序", notes = "车辆KPI客户平均装卸速率降序")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "truckKPIDesc", method = RequestMethod.POST)
    public ResponseMessage truckKPIDesc(String beginTime, String endTime) {
        return ResponseMessage.sendOK(truckKPIService.truckKPIDesc(beginTime,endTime));
    }

    @ApiOperation(value = "车辆KPI客户平均装卸速率升序", notes = "车辆KPI客户平均装卸速率升序")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "truckKPIAsc", method = RequestMethod.POST)
    public ResponseMessage truckKPIAsc(String beginTime, String endTime) {
        return ResponseMessage.sendOK(truckKPIService.truckKPIAsc(beginTime,endTime));
    }

}
