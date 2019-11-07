package com.api.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dao.entity.system.SysDepart;
import com.dao.entity.system.SysPortLog;
import com.dto.constants.Constants;
import com.dto.pojo.spsys.ResponseMessage;
import com.github.pagehelper.PageInfo;
import com.service.system.SysDepartService;
import com.service.system.SysPortLogService;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SysPortLog 接口调用日志控制类
 * Created by Xiezx on 2019-01-15.
 */
@Api(tags = "接口调用日志")
@RestController
@RequestMapping("sysapi/portLog")
public class SysPortLogServiceController {
    @Autowired
    private SysPortLogService sysPortLogService;

    @Autowired
    private SysDepartService sysDepartService;

    @ApiOperation(value = "接口调用日志，保存", notes = "接口调用日志，保存")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage save(@ApiParam(value = "保存的实体JSON字符串,必须携带updateFlg字段") @RequestBody String data, HttpServletRequest req) {
        SysPortLog sysPortLog = JSON.parseObject(data, new TypeReference<SysPortLog>() {
        });
        int saveRes = sysPortLogService.save(sysPortLog);
        if (saveRes == 1) {
            return ResponseMessage.sendOK(sysPortLog);
        } else {
            return ResponseMessage.sendError();
        }
    }

    @ApiOperation(value = "接口调用日志，分页查询", notes = "接口调用日志，分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", dataType = "String", value = "机构代码"),
            @ApiImplicitParam(paramType = "query", name = "startDate", dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endDate", dataType = "String", value = "结束时间"),
            @ApiImplicitParam(paramType = "query", name = "departIds", dataType = "String", value = "机构id(“id1，id2,id3”)"),
            @ApiImplicitParam(paramType = "query", name = "portType", dataType = "Integer", value = "接口类型"),
            @ApiImplicitParam(paramType = "query", name = "userName", dataType = "String", value = "用户名称"),
            @ApiImplicitParam(paramType = "query", name = "currentPage", required = true, dataType = "int", value = "当前页，第一页为1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", required = true, dataType = "int", value = "每页数量，默认传递50")
    })
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ResponseMessage list(String depCode, String startDate, String endDate,String userName, String departIds, String portType, Integer currentPage, Integer pageSize) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        //queryMap.put("depCode", DepCodeUtils.getFirstChildDepCodeLike(depCode));
        if (startDate != null && !"".equals(startDate)) {
            String _startDate = startDate.replace("+", " ");
            queryMap.put("startDate", _startDate);
        }
        if (endDate != null && !"".equals(endDate)) {
            String _endDate = endDate.replace("+", " ");
            queryMap.put("endDate", _endDate);
        }
        if (departIds != null && !"".equals(departIds)) {
            queryMap.put("departIds", departIds);
        }
        if (portType != null && !"".equals(portType)) {
            queryMap.put("portType", portType);
        }
        if (userName != null && !"".equals(userName)) {
            queryMap.put("userName", userName);
        }
        queryMap.put("remark", "EDIT");
        queryMap.put(Constants.PAGE_CURRENT, currentPage);
        queryMap.put(Constants.PAGE_SIZE, pageSize);
        PageInfo<SysPortLog> sysPortLogPageInfo = sysPortLogService.search(queryMap);
        return ResponseMessage.sendOK(sysPortLogPageInfo);
    }

    @ApiOperation(value = "民警数比例分析", notes = "民警数比例分析")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", dataType = "String", value = "机构代码"),
            @ApiImplicitParam(paramType = "query", name = "startDate", dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endDate", dataType = "String", value = "结束时间"),
    })
    @RequestMapping(value = "getPoliceNumProportion", method = RequestMethod.POST)
    public ResponseMessage getPoliceNumProportion(String depCode, String startDate, String endDate) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        if (depCode != null && !"".equals(depCode)) {
            queryMap.put("depCode", depCode);
        }
        if (startDate != null && !"".equals(startDate)) {
            queryMap.put("startDate", startDate);
        }
        if (endDate != null && !"".equals(endDate)) {
            queryMap.put("endDate", endDate);
        }
        List<Integer> operatorTypeList = new ArrayList<>();
        //用于增加操作类型
        //operatorTypeList.add(-1);
        if (!CollectionUtils.isEmpty(operatorTypeList)) {
            queryMap.put("operatorTypeList", operatorTypeList);
        }
        Map map = new HashMap();
        if (StringUtils.isEmpty(depCode)) {
            depCode = "330000000000";
        }
        SysDepart sysDepart = sysDepartService.selectByDepCode(depCode);
        map.put(sysDepart.getName(), sysPortLogService.getPoliceNumProportionNew(queryMap));
        List<SysDepart> sysDepartList = sysDepartService.selectFirstChildAll(depCode);
        if (CollectionUtils.isEmpty(sysDepartList)) {
            return ResponseMessage.sendOK(map);
        }
        for (SysDepart depart : sysDepartList) {
            queryMap.put("depCode", depart.getDepCode());
            map.put(depart.getName(), sysPortLogService.getPoliceNumProportionNew(queryMap));
        }
        return ResponseMessage.sendOK(map);
    }

    @ApiOperation(value = "单据跨越层级比例分析", notes = "日志环节分析")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", dataType = "String", value = "机构代码"),
            @ApiImplicitParam(paramType = "query", name = "startDate", dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endDate", dataType = "String", value = "结束时间"),
    })
    @RequestMapping(value = "getHierarchyNumProportion", method = RequestMethod.POST)
    public ResponseMessage getHierarchyNumProportion(String depCode, String startDate, String endDate) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        if (depCode != null && !"".equals(depCode)) {
            queryMap.put("depCode", depCode);
        }
        if (startDate != null && !"".equals(startDate)) {
            queryMap.put("startDate", startDate);
        }
        if (endDate != null && !"".equals(endDate)) {
            queryMap.put("endDate", endDate);
        }
        Map map = new HashMap();
        if (StringUtils.isEmpty(depCode)) {
            depCode = "330000000000";
        }
        SysDepart sysDepart = sysDepartService.selectByDepCode(depCode);
        map.put(sysDepart.getName(), sysPortLogService.getlogSegmentAnalyse(queryMap));
        List<SysDepart> sysDepartList = sysDepartService.selectFirstChildAll(depCode);
        if (CollectionUtils.isEmpty(sysDepartList)) {
            return ResponseMessage.sendOK(map);
        }
        for (SysDepart depart : sysDepartList) {
            queryMap.put("depCode", depart.getDepCode());
            map.put(depart.getName(), sysPortLogService.getlogSegmentAnalyse(queryMap));
        }
        return ResponseMessage.sendOK(map);
        //List<HashMap<String, Object>> hierarchyNumProportion =  sysPortLogService.getHierarchyNumProportion(queryMap);
    }

    @ApiOperation(value = "单据耗时分析", notes = "耗时分析")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", dataType = "String", value = "机构代码"),
            @ApiImplicitParam(paramType = "query", name = "startDate", dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endDate", dataType = "String", value = "结束时间"),
    })
    @RequestMapping(value = "getAnalyseConsumeTime", method = RequestMethod.POST)
    public ResponseMessage analyseConsumeTime(String depCode, String startDate, String endDate) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        if (depCode != null && !"".equals(depCode)) {
            queryMap.put("depCode", depCode);
        }
        if (startDate != null && !"".equals(startDate)) {
            queryMap.put("startDate", startDate);
        }
        if (endDate != null && !"".equals(endDate)) {
            queryMap.put("endDate", endDate);
        }
        Map map = new HashMap();
        if (StringUtils.isEmpty(depCode)) {
            depCode = "330000000000";
        }
        SysDepart sysDepart = sysDepartService.selectByDepCode(depCode);
        map.put(sysDepart.getName(), sysPortLogService.analyseConsumeTime(queryMap));
        List<SysDepart> sysDepartList = sysDepartService.selectFirstChildAll(depCode);
        if (CollectionUtils.isEmpty(sysDepartList)) {
            return ResponseMessage.sendOK(map);
        }
        for (SysDepart depart : sysDepartList) {
            queryMap.put("depCode", depart.getDepCode());
            map.put(depart.getName(), sysPortLogService.analyseConsumeTime(queryMap));
        }
        return ResponseMessage.sendOK(map);
    }

    @ApiOperation(value = "操作终端分析", notes = "操作终端分析")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", dataType = "String", value = "机构代码"),
            @ApiImplicitParam(paramType = "query", name = "startDate", dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endDate", dataType = "String", value = "结束时间"),
    })
    @RequestMapping(value = "getAnalyseTerminal", method = RequestMethod.POST)
    public ResponseMessage analyseTerminal(String depCode, String startDate, String endDate) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        if (depCode != null && !"".equals(depCode)) {
            queryMap.put("depCode", depCode);
        }
        if (startDate != null && !"".equals(startDate)) {
            queryMap.put("startDate", startDate);
        }
        if (endDate != null && !"".equals(endDate)) {
            queryMap.put("endDate", endDate);
        }
        List<Integer> operatorTypeList = new ArrayList<>();
        //用于增加操作类型
        //operatorTypeList.add(-1);
        if (!CollectionUtils.isEmpty(operatorTypeList)) {
            queryMap.put("operatorTypeList", operatorTypeList);
        }
        Map map = new HashMap();
        if (StringUtils.isEmpty(depCode)) {
            depCode = "330000000000";
        }
        SysDepart sysDepart = sysDepartService.selectByDepCode(depCode);
        map.put(sysDepart.getName(), sysPortLogService.analyseTerminal(queryMap));
        List<SysDepart> sysDepartList = sysDepartService.selectFirstChildAll(depCode);
        if (CollectionUtils.isEmpty(sysDepartList)) {
            return ResponseMessage.sendOK(map);
        }
        for (SysDepart depart : sysDepartList) {
            queryMap.put("depCode", depart.getDepCode());
            map.put(depart.getName(), sysPortLogService.analyseTerminal(queryMap));
        }
        return ResponseMessage.sendOK(map);
    }


    @ApiOperation(value = "操作时段分析", notes = "操作时段ss分析")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "depCode", dataType = "String", value = "机构代码"),
            @ApiImplicitParam(paramType = "query", name = "startDate", dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endDate", dataType = "String", value = "结束时间"),
    })
    @RequestMapping(value = "getAnalyseTimeSegment", method = RequestMethod.POST)
    public ResponseMessage analyseTimeSegment(String depCode, String startDate, String endDate) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        if (depCode != null && !"".equals(depCode)) {
            queryMap.put("depCode", depCode);
        }
        if (startDate != null && !"".equals(startDate)) {
            queryMap.put("startDate", startDate);
        }
        if (endDate != null && !"".equals(endDate)) {
            queryMap.put("endDate", endDate);
        }
        Map map = new HashMap();
        if (StringUtils.isEmpty(depCode)) {
            depCode = "330000000000";
        }
        SysDepart sysDepart = sysDepartService.selectByDepCode(depCode);
        map.put(sysDepart.getName(), sysPortLogService.analyseTimeSegment(queryMap));
        List<SysDepart> sysDepartList = sysDepartService.selectFirstChildAll(depCode);
        if (CollectionUtils.isEmpty(sysDepartList)) {
            return ResponseMessage.sendOK(map);
        }
        for (SysDepart depart : sysDepartList) {
            queryMap.put("depCode", depart.getDepCode());
            map.put(depart.getName(), sysPortLogService.analyseTimeSegment(queryMap));
        }
        return ResponseMessage.sendOK(map);
    }
}
