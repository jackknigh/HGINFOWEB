package com.api.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dao.entity.system.SysOperation;
import com.dto.constants.Constants;
import com.dto.enums.RspCode;
import com.dto.pojo.system.KeyValues;
import com.dto.pojo.spsys.ResponseMessage;
import com.github.pagehelper.PageInfo;
import com.service.system.SysOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiezx on 2019-01-08.
 */
@Api(tags = "系统管理")
@RestController
@RequestMapping("sysapi/operation")
public class SysOperationController {
    @Autowired
    private SysOperationService sysOperationService;

    @ApiOperation(value = "系统详情（增删改操作），保存", notes = "系统详情（增删改操作），保存")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage save(@ApiParam(value = "保存的实体JSON字符串,必须携带updateFlg字段") @RequestBody String data, HttpServletRequest req) {
        SysOperation sysOperation = JSON.parseObject(data, new TypeReference<SysOperation>() {
        });
        int saveRes = sysOperationService.save(sysOperation);
        if (saveRes == 1) {
            return ResponseMessage.sendOK(sysOperation);
        } else {
            return ResponseMessage.sendError();
        }
    }


    @ApiOperation(value = "系统，批量删除", notes = "系统，批量删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]"),
    })
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseMessage audit(String ids) {
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
        sysOperationService.deleteByIds(_ids);
        return ResponseMessage.sendOK();
    }


    @ApiOperation(value = "系统，根据id查询详情", notes = "系统，根据id查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "String", value = "主键编号")
    })
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public ResponseMessage detail(String id) {
        SysOperation sysOperation = sysOperationService.selectByPrimaryKey(id);
        return ResponseMessage.sendOK(sysOperation);
    }

    @ApiOperation(value = "系统，分页查询", notes = "系统，分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", dataType = "String", value = "机构代码"),
            @ApiImplicitParam(paramType = "query", name = "name", dataType = "String", value = "系统名称"),
            @ApiImplicitParam(paramType = "query", name = "currentPage", required = true, dataType = "int", value = "当前页，第一页为1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", required = true, dataType = "int", value = "每页数量，默认传递50")
    })
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ResponseMessage list(String depCode, String name, Integer currentPage, Integer pageSize) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put(Constants.PAGE_CURRENT, currentPage);
        queryMap.put(Constants.PAGE_SIZE, pageSize);
        if (name != null && !"".equals(name)) {
            String _name = name.replace(" ", "");
            queryMap.put("name", _name);
        }
        queryMap.put("remark", "EDIT");
        PageInfo<SysOperation> sysOperateionPageInfo = sysOperationService.search(queryMap);
        return ResponseMessage.sendOK(sysOperateionPageInfo);
    }

    @ApiOperation(value = "系统，启用", notes = "系统，启用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]")
    })
    @RequestMapping(value = "open", method = RequestMethod.POST)
    public ResponseMessage openState(String ids) {
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
        sysOperationService.updateStateOpenByIds(_ids);
        return ResponseMessage.sendOK();
    }

    @ApiOperation(value = "系统，禁用", notes = "系统，禁用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", required = true, dataType = "String", value = "主键编号JSON字符串,[{'value':'id1'},{'value':'id2'}]")
    })
    @RequestMapping(value = "close", method = RequestMethod.POST)
    public ResponseMessage closeState(String ids) {
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
        sysOperationService.updateStateCloseByIds(_ids);
        return ResponseMessage.sendOK();
    }

    @ApiOperation(value = "系统详情，查询所有系统", notes = "系统详情，查询所有系统")
    @RequestMapping(value = "selectAll", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage selectAll() {
        List<SysOperation> sysOperations = sysOperationService.selectAll();
        return ResponseMessage.sendOK(sysOperations);
    }


    @ApiOperation(value = "系统，执行", notes = "系统，执行")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "String", value = "系统主键id"),
    })
    @RequestMapping(value = "execute", method = RequestMethod.POST)
    public ResponseMessage execute(String id) {
        SysOperation sysOperation = sysOperationService.selectByPrimaryKey(id);
        if (null == sysOperation || StringUtils.isBlank(sysOperation.getPath())) {
            return ResponseMessage.sendError();
        }
        String path = sysOperation.getPath();
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("sh " + path);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseMessage.sendDefined(RspCode.FAILURE);
        }
        return ResponseMessage.sendOK();
    }

}
