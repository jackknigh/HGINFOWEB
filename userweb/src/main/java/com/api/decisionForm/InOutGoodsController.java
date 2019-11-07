package com.api.decisionForm;

import com.dto.pojo.sys.ResponseMessage;
import com.service.decisionForm.InOutGoodsService;
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
public class InOutGoodsController {
    @Autowired
    InOutGoodsService inOutGoodsService;

    @ApiOperation(value = "月度货物进出明细", notes = "月度货物进出明细")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "inOutGoods", method = RequestMethod.POST)
    public ResponseMessage inOutGoods(String beginTime, String endTime) {
        return ResponseMessage.sendOK(inOutGoodsService.inOutGoods(beginTime,endTime));
    }
}
