package com.api.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dao.db1.system.SysDicCommsMapper;
import com.dao.entity.system.SysDicColumn;
import com.dao.entity.system.SysDicComms;
import com.dao.entity.system.SysDicTable;
import com.dto.enums.RspCode;
import com.dto.pojo.system.KeyValues;
import com.dto.pojo.spsys.ResponseMessage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.system.SysDicService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "字典通用接口")
@RestController
@RequestMapping("sysapi/SysDicController")
public class SysDicController {

    @Autowired
    private SysDicService sysDicService;
    @Autowired
    private SysDicCommsMapper sysDicCommsMapper;

    @ApiOperation(value = "字典，表名", notes = "字典，表名")
    @RequestMapping(value = "dicTable", method = RequestMethod.POST)
    public ResponseMessage dicTable() {
        List<SysDicTable> sysDicTables = sysDicService.tableList();
        return ResponseMessage.sendOK(sysDicTables);
    }

    @ApiOperation(value = "字典，表的字段名", notes = "字典，表的字段名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tableID", required = true, dataType = "String", value = "表的主键编号")
    })
    @RequestMapping(value = "dicColumn", method = RequestMethod.POST)
    public ResponseMessage dicColumn(String tableID) {
        List<SysDicColumn> sysDicColumns = sysDicService.columnList(tableID);
        return ResponseMessage.sendOK(sysDicColumns);
    }

    @ApiOperation(value = "字典通用", notes = "字典通用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "dicType", required = true, dataType = "String", value = "字典类型")
    })
    @RequestMapping(value = "dicCalculations", method = RequestMethod.POST)
    public ResponseMessage dicCalculations(String dicType) {
        List<SysDicComms> sysDicComms = sysDicService.calculationList(dicType);
        return ResponseMessage.sendOK(sysDicComms);
    }

    @ApiOperation(value = "获取字典列表，分页查询", notes = "获取字典列表，分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "currentPage", required = true, dataType = "int", value = "当前页，第一页为1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", required = true, dataType = "int", value = "每页数量，默认传递50")
    })
    @RequestMapping(value = "dicCalculationList", method = RequestMethod.POST)
    public ResponseMessage dicCalculationList(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<SysDicComms> sysDicComms = sysDicCommsMapper.selectAll();
        return ResponseMessage.sendOK(new PageInfo<>(sysDicComms));
    }

    @ApiOperation(value = "字典，保存", notes = "字典，保存")
    @RequestMapping(value = "assessMainSave", method = RequestMethod.POST)
    public ResponseMessage assessMainSave(@ApiParam(value = "保存的实体JSON字符串,必须携带updateFlg字段,值为update") @RequestBody String data) {
        SysDicComms sysDicComms = JSON.parseObject(data, new TypeReference<SysDicComms>() {
        });

        int save = sysDicService.calculationSave(sysDicComms);
        if (save == 1) {
            return ResponseMessage.sendOK(sysDicComms);
        }else if (save == -1) {
            return ResponseMessage.sendDefined(RspCode.DIC_CODE_FAILURE);
        } else {

            return ResponseMessage.sendError();
        }
    }

    @ApiOperation(value = "字典详情", notes = "字典详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "String", value = "字典主键编号")
    })
    @RequestMapping(value = "dicCalculationDetail", method = RequestMethod.POST)
    public ResponseMessage dicCalculationDetail(String id) {
        SysDicComms sysDicComm = sysDicService.detail(id);
        return ResponseMessage.sendOK(sysDicComm);
    }

    @ApiOperation(value = "字典，批量删除", notes = "字典，批量删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]"),
    })
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseMessage delete(String ids) {
        List<KeyValues> keyValues = null;
        List<String> _ids = new ArrayList<>();
        try {
            keyValues = JSONObject.parseArray(ids, KeyValues.class);
            for (KeyValues _key : keyValues)
                _ids.add(_key.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.sendDefined(RspCode.JSONERRPR);
        }

        int audit = sysDicService.delete(_ids);
        if (audit < 1) {
            return ResponseMessage.sendError();
        }
        return ResponseMessage.sendOK();
    }

    @ApiOperation(value = "字典，批量启用或停用", notes = "字典，批量启用或停用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]"),
            @ApiImplicitParam(paramType = "query", name = "state", required = true, dataType = "Integer", value = "启用状态：0：停用，1：启用")
    })
    @RequestMapping(value = "startUse", method = RequestMethod.POST)
    public ResponseMessage startUse(String ids, Integer state) {
        List<KeyValues> keyValues = null;
        List<String> _ids = new ArrayList<>();
        try {
            keyValues = JSONObject.parseArray(ids, KeyValues.class);
            for (KeyValues _key : keyValues)
                _ids.add(_key.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.sendDefined(RspCode.JSONERRPR);
        }

        sysDicService.startUse(_ids, state);
        return ResponseMessage.sendOK();
    }
}
