package com.api.lwaddr;

import com.dto.form.SearchForm;
import com.dto.pojo.sys.ResponseMessage;
import com.service.lwaddress.Bs_startWayService;
import com.service.lwaddress.MsgSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "人员信息查询接口")
@RestController
@RequestMapping("/sysapi/lwaddr")
public class MsgSearchController {

    @Autowired
    private MsgSearchService msgSearchService;
    @Autowired
    private Bs_startWayService bs_startWayService;

    @ApiOperation(value = "查询接口", notes = "查询接口")
    @RequestMapping(value = "/queryMsg", method = RequestMethod.POST)
    public ResponseMessage queryMsg(@RequestBody SearchForm searchForm) {
        return ResponseMessage.sendOK(msgSearchService.queryMsg(searchForm.getName(), searchForm.getPhone(), searchForm.getAddress()));
    }

    @ApiOperation(value = "查询接口", notes = "查询接口")
    @RequestMapping(value = "/getThreadInfo", method = RequestMethod.GET)
    public ResponseMessage getThreadInfo() {
        return ResponseMessage.sendOK(bs_startWayService.getThreadInfo());
    }
}
