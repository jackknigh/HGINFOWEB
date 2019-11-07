package com.api.decisionForm;

import com.dto.pojo.sys.ResponseMessage;
import com.service.decisionForm.DiffTankGoodsService;
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
public class DiffTankGoodsController {

    @Autowired
    DiffTankGoodsService diffTankGoodsService;

    @ApiOperation(value = "每日储罐货物计量表，基础数据", notes = "每日储罐货物计量表，基础数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "month", required = true, dataType = "String", value = "月份"),
    })
    @RequestMapping(value = "diffTankGoods", method = RequestMethod.POST)
    public ResponseMessage diffTankGoods(String month) {
        return ResponseMessage.sendOK(diffTankGoodsService.diffTankGoods(month));
    }
}
