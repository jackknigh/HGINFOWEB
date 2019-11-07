package com.api.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dao.entity.system.SysDepart;
import com.dao.entity.system.SysOperateLog;
import com.dto.constants.Constants;
import com.dto.pojo.spsys.ResponseMessage;
import com.github.pagehelper.PageInfo;
import com.mchange.v2.util.CollectionUtils;
import com.service.system.SysDepartService;
import com.service.system.SysOperateLogService;
import com.utils.sys.DepCodeUtils;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SysOperateLog 行为日志接口控制类
 * Created by Xiezx on 2019-01-14.
 */
@Api(tags = "操作行为日志")
@RestController
@RequestMapping("sysapi/operateLog")
public class SysOperateLogServiceController {
    @Autowired
    private SysOperateLogService sysOperateLogService;

    @Autowired
    private SysDepartService sysDepartService;

    @ApiOperation(value = "操作行为日志，保存", notes = "操作行为日志，保存")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage save(@ApiParam(value = "保存的实体JSON字符串,必须携带updateFlg字段") @RequestBody String data, HttpServletRequest req) {
        SysOperateLog sysOperateLog = JSON.parseObject(data, new TypeReference<SysOperateLog>() {
        });
        int saveRes = sysOperateLogService.save(sysOperateLog);
        if (saveRes == 1) {
            return ResponseMessage.sendOK(sysOperateLog);
        } else {
            return ResponseMessage.sendError();
        }
    }

    @ApiOperation(value = "操作行为日志，分页查询", notes = "操作行为日志，分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", required = true, dataType = "String", value = "机构代码"),
            @ApiImplicitParam(paramType = "query", name = "startDate", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endDate", required = true, dataType = "String", value = "结束时间"),
            @ApiImplicitParam(paramType = "query", name = "operateType", dataType = "Integer", value = "操作类型"),
            @ApiImplicitParam(paramType = "query", name = "userName", dataType = "String", value = "用户名称"),
            @ApiImplicitParam(paramType = "query", name = "currentPage", required = true, dataType = "int", value = "当前页，第一页为1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", required = true, dataType = "int", value = "每页数量，默认传递50")
    })
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ResponseMessage list(String depCode, String startDate, String endDate, Integer operateType, Integer currentPage, Integer pageSize,String userName) {
        Map<String, Object> queryMap = new HashMap<String, Object>();

        if (depCode != null && !"".equals(depCode)) {
            queryMap.put("depCode", DepCodeUtils.getFirstChildDepCodeLike(depCode));
        }
        if (startDate != null && !"".equals(startDate)) {
            String _startDate = startDate.replace("+", " ");
            queryMap.put("startDate", _startDate);
        }
        if (endDate != null && !"".equals(endDate)) {
            String _endDate = endDate.replace("+", " ");
            queryMap.put("endDate", _endDate);
        }
        if (operateType != null && !"".equals(operateType)) {
            queryMap.put("operateType", operateType);
        }
        if (userName != null && !"".equals(userName)) {
            queryMap.put("userName", userName);
        }
        //queryMap.put("remark", "EDIT");
        queryMap.put(Constants.PAGE_CURRENT, currentPage);
        queryMap.put(Constants.PAGE_SIZE, pageSize);
        PageInfo<SysOperateLog> pageInfo = sysOperateLogService.search(queryMap);
        return ResponseMessage.sendOK(pageInfo);
    }

    @ApiOperation(value = "日志统计地区的使用详情_柱状图", notes = "日志统计地区的使用详情_柱状图")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", required = true, dataType = "String", value = "机构代码,默认顶级"),
            @ApiImplicitParam(paramType = "query", name = "userName", required = true, dataType = "String", value = "用户名称"),
            @ApiImplicitParam(paramType = "query", name = "startDate", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endDate", required = true, dataType = "String", value = "结束时间")

    })
    @RequestMapping(value = "departHistogram", method = RequestMethod.POST)
    public ResponseMessage departHistogram(String depCode, String userName, String startDate, String endDate, String operateId) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        if (StringUtils.isBlank(depCode)) {
            depCode = "330000000000";
        }
        // if (depCode != null && !"".equals(depCode)) {
        //     queryMap.put("depCode", depCode);
        // }
        if (startDate != null && !"".equals(startDate)) {
            queryMap.put("startDate", startDate);
        }
        if (userName != null && !"".equals(userName)) {
            queryMap.put("userName", userName);
        }
        if (endDate != null && !"".equals(endDate)) {
            queryMap.put("endDate", endDate);
        }
        List<String> depCodeList = Arrays.asList(depCode.split(";"));
        Map map = new HashMap();
        for (String code : depCodeList) {
            queryMap.put("depCode", code);
            map.put(code,sysOperateLogService.departHistogram(queryMap));
        }
        return ResponseMessage.sendOK(map);
    }

    @ApiOperation(value = "日志统计地区的使用详情_饼图", notes = "日志统计地区的使用详情_饼图")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", required = true, dataType = "String", value = "机构代码,不传返回空"),
            @ApiImplicitParam(paramType = "query", name = "startDate", required = true, dataType = "String", value = "开始时间 yyyy-mm-dd hh24:mi:ss"),
            @ApiImplicitParam(paramType = "query", name = "endDate", required = true, dataType = "String", value = "结束时间 yyyy-mm-dd hh24:mi:ss")

    })
    @RequestMapping(value = "departPie", method = RequestMethod.POST)
    public ResponseMessage departPie(String depCode, String startDate, String endDate, String operateId) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        if (startDate != null && !"".equals(startDate)) {
            queryMap.put("startDate", startDate);
        }
        if (endDate != null && !"".equals(endDate)) {
            queryMap.put("endDate", endDate);
        }
        if (StringUtils.isBlank(depCode)) {
            return ResponseMessage.sendOK();
        }
        List<SysDepart> sysDepartList = sysDepartService.selectFirstChildAll(depCode);
        if (null == sysDepartList || sysDepartList.isEmpty()) {
            SysDepart sysDepart = new SysDepart();
            sysDepart.setDepCode(depCode);
            sysDepartList = Arrays.asList(sysDepart);
        }
        Map map = new HashMap();
        for (SysDepart sysDepart : sysDepartList) {
            queryMap.put("depCode", sysDepart.getDepCode());
            Map operateMap = sysOperateLogService.departPie(queryMap);
            if (null == operateMap) {
                continue;
            }
            map.put(sysDepart.getName(), Integer.parseInt(String.valueOf(operateMap.get("operateTimes"))));
        }
        return ResponseMessage.sendOK(map);
    }



 /* @ApiOperation(value = "日志，统计单据跨越层级的详情", notes = "日志，统计单据跨越层级的详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "startDate",required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endDate",required = true, dataType = "String", value = "结束时间"),
    })

    @RequestMapping(value = "departLevelNumber", method = RequestMethod.POST)
    public ResponseMessage departLevelNumber(String startDate,String endDate ) {
        Map<String, Object> queryMap = new HashMap<String, Object>();

        queryMap.put("remark", "EDIT");
        PageInfo<Object> sysOperateLogPageInfo = sysOperateLogService.search(queryMap);
        return ResponseMessage.sendOK(sysOperateLogPageInfo);
    }*/

}
