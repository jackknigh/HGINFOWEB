package com.api.decisionForm;

import com.dto.pojo.sys.ResponseMessage;
import com.service.decisionForm.InOutCarService;
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
public class InOutCarController {

    @Autowired
    InOutCarService inOutCarService;

    @ApiOperation(value = "车辆进出情况查询，基础数据", notes = "车辆进出情况查询，基础数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "inOutCarBas", method = RequestMethod.POST)
    public ResponseMessage inOutCarBas(String beginTime, String endTime) {
        return ResponseMessage.sendOK(inOutCarService.inOutCarBas(beginTime,endTime));
    }
    @ApiOperation(value = "车辆进出情况查询，根据Number排序", notes = "车辆进出情况查询，根据Number排序")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "inOutCarSort", method = RequestMethod.POST)
    public ResponseMessage inOutCarSort(String beginTime, String endTime) {
        return ResponseMessage.sendOK(inOutCarService.inOutCarSort(beginTime,endTime));
    }

    @ApiOperation(value = "车辆进出情况查询，根据KPI排序", notes = "车辆进出情况查询，根据KPI排序")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "inOutCarGK", method = RequestMethod.POST)
    public ResponseMessage inOutCarGK(String beginTime, String endTime) {
        return ResponseMessage.sendOK(inOutCarService.inOutCarGK(beginTime,endTime));
    }
}
