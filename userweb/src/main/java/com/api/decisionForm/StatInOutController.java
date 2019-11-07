package com.api.decisionForm;

import com.dto.pojo.sys.ResponseMessage;
import com.service.decisionForm.StatInOutService;
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
public class StatInOutController {

    @Autowired
    StatInOutService statInOutService;

    @ApiOperation(value = "汽车计量日报表,排序", notes = "汽车计量日报表,按照产品求平均值，并排序")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "statInOutSort", method = RequestMethod.POST)
    public ResponseMessage statInOutSort(String beginTime, String endTime) {
        return ResponseMessage.sendOK(statInOutService.statInOutSort(beginTime,endTime));
    }

    @ApiOperation(value = "汽车计量日报表,基础数据", notes = "汽车计量日报表,基础数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "statInOutBasics", method = RequestMethod.POST)
    public ResponseMessage statInOutBasics(String beginTime, String endTime) {
        return ResponseMessage.sendOK(statInOutService.statInOutBasics(beginTime,endTime));
    }
}
