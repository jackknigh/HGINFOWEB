package com.api.decisionForm;

import com.dto.pojo.sys.ResponseMessage;
import com.service.decisionForm.TankGoodsService;
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
public class TankGoodsController {

    @Autowired
    TankGoodsService tankGoodsService;

    @ApiOperation(value = "库存数查询", notes = "月度货物进出明细")
    @RequestMapping(value = "tankGoods", method = RequestMethod.POST)
    public ResponseMessage tankGoods(String beginTime, String endTime) {
        return ResponseMessage.sendOK(tankGoodsService.tankGoods());
    }
}
