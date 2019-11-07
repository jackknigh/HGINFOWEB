package com.api.decisionForm;


import com.dto.pojo.sys.ResponseMessage;
import com.service.decisionForm.QueryInvoiceService;
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
public class QueryInvoiceController {
    @Autowired
    QueryInvoiceService queryInvoiceService;

    @ApiOperation(value = "开票汇总", notes = "开票汇总")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间"),
            @ApiImplicitParam(paramType = "query", name = "type", required = true, dataType = "String", value = "日期类型")
    })
    @RequestMapping(value = "queryInvoice", method = RequestMethod.POST)
    public ResponseMessage queryInvoice(String beginTime, String endTime, String type) {
        return ResponseMessage.sendOK(queryInvoiceService.queryInvoice(beginTime,endTime,type));
    }
}
