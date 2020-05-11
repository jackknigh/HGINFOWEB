package com.api.lwaddr;

import com.dto.form.SearchContentForm;
import com.dto.form.SearchForm;
import com.dto.form.SumAddressForm;
import com.dto.pojo.sys.ResponseMessage;
import com.dto.vo.BaseAddrVo;
import com.service.lwaddress.Bs_startWayService;
import com.service.lwaddress.MsgSearchService;
import com.spinfosec.system.RspCode;
import com.spinfosec.system.TMCException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "人员信息查询接口")
@RestController
@RequestMapping("/sysapi/lwaddr")
public class MsgSearchController {

    @Autowired
    private MsgSearchService msgSearchService;
    @Autowired
    private Bs_startWayService bs_startWayService;

//    @ApiOperation(value = "查询接口", notes = "查询接口")
//    @RequestMapping(value = "/queryMsg", method = RequestMethod.POST)
//    public ResponseMessage queryMsg(@RequestBody SearchForm searchForm) {
//        return ResponseMessage.sendOK(msgSearchService.queryMsg(searchForm.getName(), searchForm.getPhone(), searchForm.getAddress()));
//    }

    @ApiOperation(value = "查询接口", notes = "查询接口")
    @RequestMapping(value = "/queryMsg", method = RequestMethod.GET)
    public ResponseMessage queryMsg(@RequestParam String address,@RequestParam String shortPhone,@RequestParam String surname) {
        return ResponseMessage.sendOK(msgSearchService.queryMsg(address,shortPhone,surname));
    }

    @ApiOperation(value = "增量数据处理接口", notes = "增量数据处理接口")
    @RequestMapping(value = "/insertMsgProcess", method = RequestMethod.POST)
    public ResponseMessage insertMsgProcess(@RequestBody BaseAddrVo baseAddrVo) {
        return ResponseMessage.sendOK(msgSearchService.insertMsgProcess(baseAddrVo));
    }

    @ApiOperation(value = "根据房屋编码查询接口", notes = "查询接口")
    @RequestMapping(value = "/queryMsgByCode", method = RequestMethod.GET)
    public ResponseMessage queryMsgByCode(@RequestParam String addrCode) {
        return ResponseMessage.sendOK(msgSearchService.queryMsgByCode(addrCode));
    }

    @ApiOperation(value = "根据编码id查询该地址下人员信息接口", notes = "查询接口")
    @RequestMapping(value = "/queryMsgById", method = RequestMethod.GET)
    public ResponseMessage queryMsgById(@RequestParam String id) {
        return ResponseMessage.sendOK(msgSearchService.queryMsgById(id));
    }

    @ApiOperation(value = "通过手机号查询所有数据接口", notes = "通过手机号查询所有数据接口")
    @RequestMapping(value = "/queryMsgByPhone", method = RequestMethod.GET)
    public ResponseMessage queryMsgByPhone(@RequestParam String phone) {
        return ResponseMessage.sendOK(msgSearchService.queryMsgByPhone(phone));
    }

    @ApiOperation(value = "通过手机号查询所有基准数据接口", notes = "通过手机号查询所有基准数据接口")
    @RequestMapping(value = "/queryBasicsMsgByPhone", method = RequestMethod.GET)
    public ResponseMessage queryBasicsMsgByPhone(@RequestParam String phone) {
        return ResponseMessage.sendOK(msgSearchService.queryBasicsMsgByPhone(phone));
    }

    @ApiOperation(value = "根据地址返回省市区街道", notes = "根据地址返回省市区街道")
    @RequestMapping(value = "/queryAddressInfo", method = RequestMethod.GET)
    public ResponseMessage queryAddressInfo(@RequestParam String address) {
        return ResponseMessage.sendOK(msgSearchService.queryAddressInfo(address));
    }

    @ApiOperation(value = "获取两个地址的相似度值", notes = "查询接口")
    @RequestMapping(value = "/querySimilarity", method = RequestMethod.GET)
    public ResponseMessage querySimilarity(@RequestParam String basicsAddr,@RequestParam String matchAddr) {
        return ResponseMessage.sendOK(msgSearchService.querySimilarity(basicsAddr,matchAddr));
    }

    @ApiOperation(value = "根据id查询详情接口", notes = "根据id查询详情接口")
    @RequestMapping(value = "/queryInfoById", method = RequestMethod.GET)
    public ResponseMessage queryInfoById(@RequestParam String id) {
        return ResponseMessage.sendOK(msgSearchService.queryInfoById(id));
    }

    @ApiOperation(value = "查询某地址快递信息接口", notes = "查询某地址快递信息接口")
    @RequestMapping(value = "/queryMergeInfoById", method = RequestMethod.GET)
    public ResponseMessage queryMergeInfoById(@RequestParam String id) {
        return ResponseMessage.sendOK(msgSearchService.queryMergeInfoById(id));
    }

    @ApiOperation(value = "查询线程运行状态接口", notes = "查询线程运行状态接口")
    @RequestMapping(value = "/getThreadInfo", method = RequestMethod.GET)
    public ResponseMessage getThreadInfo() {
        return ResponseMessage.sendOK(bs_startWayService.getThreadInfo());
    }

    @ApiOperation(value = "搜索", notes = "搜索")
    @RequestMapping(value = "/searchContent", method = RequestMethod.POST)
    public ResponseMessage searchContent(@RequestBody SearchContentForm searchContentForm) {
        return ResponseMessage.sendOK(msgSearchService.searchContent(searchContentForm));
    }

    @ApiOperation(value = "同地址人数详情", notes = "同地址人数详情")
    @RequestMapping(value = "/searchPeopleList", method = RequestMethod.GET)
    public ResponseMessage searchPeopleList(@RequestParam String id) {
        if(StringUtils.isBlank(id)){
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "参数不能为空");
            throw new TMCException(errCode);
        }
        return ResponseMessage.sendOK(msgSearchService.searchPeopleList(id));
    }

    @ApiOperation(value = "合并地址列表详情", notes = "合并地址列表详情")
    @RequestMapping(value = "/searchAddressList", method = RequestMethod.GET)
    public ResponseMessage searchAddressList(@RequestParam String id,@RequestParam Integer currentPage,@RequestParam Integer pageSize) {
        if(StringUtils.isBlank(id)){
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "参数不能为空");
            throw new TMCException(errCode);
        }
        return ResponseMessage.sendOK(msgSearchService.searchAddressList(id,currentPage,pageSize));
    }

    @ApiOperation(value = "合并地址列表移除", notes = "合并地址列表移除")
    @RequestMapping(value = "/removeAddressList", method = RequestMethod.GET)
    public ResponseMessage removeAddressList(@RequestParam String id) {
        if(StringUtils.isBlank(id)){
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "参数不能为空");
            throw new TMCException(errCode);
        }
        msgSearchService.removeAddressList(id);
        return ResponseMessage.sendOK();
    }

    @ApiOperation(value = "合并地址", notes = "合并地址")
    @RequestMapping(value = "/sumAddress", method = RequestMethod.GET)
    public ResponseMessage sumAddress(@RequestParam String addressId, @RequestParam String keyData) {
        if(StringUtils.isBlank(addressId) || StringUtils.isBlank(keyData)){
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "参数不能为空");
            throw new TMCException(errCode);
        }
        msgSearchService.sumAddress(addressId,keyData);
        return ResponseMessage.sendOK();
    }

    @ApiOperation(value = "收快递数", notes = "收快递数")
    @RequestMapping(value = "/acceptDelivery", method = RequestMethod.GET)
    public ResponseMessage acceptDelivery(@RequestParam String id) {
        if(StringUtils.isBlank(id) || StringUtils.isBlank(id)){
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "参数不能为空");
            throw new TMCException(errCode);
        }
        return ResponseMessage.sendOK(msgSearchService.acceptDelivery(id));
    }

    @ApiOperation(value = "人员其他地址情况", notes = "人员其他地址情况")
    @RequestMapping(value = "/otherAddress", method = RequestMethod.GET)
    public ResponseMessage otherAddress(@RequestParam String phone) {
        if(StringUtils.isBlank(phone)){
            RspCode errCode = RspCode.FAILURE;
            errCode.setDescription(errCode, "参数不能为空");
            throw new TMCException(errCode);
        }
        return ResponseMessage.sendOK(msgSearchService.otherAddress(phone));
    }
}
