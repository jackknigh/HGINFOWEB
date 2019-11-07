package com.service.system.impl;

import com.dao.db1.system.SysPortLogMapper;
import com.dao.entity.system.SysPortLog;
import com.dto.constants.Constants;
import com.dto.pojo.system.TerminalOperate;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * SysPortLog 接口调用日志接口实现类
 * Created by Xiezx on 2019-01-15.
 */
@Transactional
@Service("sysPortLogService")
public class SysPortLogServiceImpl implements com.service.system.SysPortLogService {
    @Autowired
    private SysPortLogMapper sysPortLogMapper;


    /**
     * 获取最上级部门
     *
     * @param deptCode
     * @return
     */
    private String getParentDepCode(String deptCode) {
        int i = deptCode.length() - 1;
        for (; i > -1; i--) {
            if (deptCode.charAt(i) != '0') {
                break;
            }
        }
        if (i == 0) {
            return deptCode;
        }
        return deptCode.substring(0, i + 1);
    }

    @Override
    public int insert(SysPortLog sysPortLog) {
        return sysPortLogMapper.insert(sysPortLog);
    }

    @Override
    public SysPortLog selectByPrimaryKey(String id) {
        return sysPortLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysPortLog> selectAll() {
        return sysPortLogMapper.selectAll();
    }

    @Override
    public int save(SysPortLog sysPortLog) {
        return 0;
    }

    @Override
    public PageInfo<SysPortLog> search(Map<String, Object> queryMap) {
        Integer pageNUm = (Integer) queryMap.get(Constants.PAGE_CURRENT);
        Integer pageSize = (Integer) queryMap.get(Constants.PAGE_SIZE);
        PageHelper.startPage(pageNUm, pageSize);
        List<SysPortLog> search = sysPortLogMapper.search(queryMap);
        if (!CollectionUtils.isEmpty(search)) {
            search = search.stream().map(e -> {
                String content = e.getContent();
                if (content.startsWith("BASE64:")) {
                    content = new String(Base64.getDecoder().decode(content.substring(7)));
                }
                e.setContent(content);
                return e;
            }).collect(Collectors.toList());
        }
        PageInfo<SysPortLog> pageList = new PageInfo<>(search);
        return pageList;
    }

    @Override
    public List<HashMap<String, Object>> getPoliceNumProportion(Map<String, Object> queryMap) {
        List<HashMap<String, Object>> listOne = sysPortLogMapper.getLogByParentId(queryMap);
        for (HashMap<String, Object> listAdd : listOne) {
            listAdd.put("num", 1);
            List<HashMap<String, Object>> listTwo = sysPortLogMapper.getLogBySuperId(listAdd.get("id").toString());
            for (HashMap<String, Object> listTwoAdd : listTwo) {
                if (!listTwoAdd.get("userName").equals(listAdd.get("userName"))) {
                    listAdd.put("num", ((Number) listAdd.get("num")).intValue() + 1);
                }
            }
        }
        TreeSet<Integer> hset = new TreeSet<Integer>();
        for (HashMap<String, Object> listAdd : listOne) {
            hset.add(((Number) listAdd.get("num")).intValue());
        }
        List<HashMap<String, Object>> listOut = new ArrayList<>();
        for (Integer intOut : hset) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("num", intOut);
            Integer numNum = 0;
            for (HashMap<String, Object> listAdd : listOne) {
                if (intOut.equals(listAdd.get("num"))) {
                    numNum = numNum + 1;
                }
            }
            hashMap.put("numNum", numNum);
            hashMap.put("proportion", ((numNum * 100) / listOne.size()) + "%");
            listOut.add(hashMap);
        }
        return listOut;
    }

    @Override
    public List<HashMap<String, Object>> getHierarchyNumProportion(Map<String, Object> queryMap) {
        List<HashMap<String, Object>> listGrade = sysPortLogMapper.getHierarchyNumProportion(queryMap);
        for (HashMap<String, Object> listAdd : listGrade) {
            Integer maxGrade = ((Number) listAdd.get("grade")).intValue();
            Integer minGrade = ((Number) listAdd.get("grade")).intValue();
            List<HashMap<String, Object>> listChildrenGrade = sysPortLogMapper.getHierarchyNumProportionBySuperId(((Number) listAdd.get("id")).intValue());
            for (HashMap<String, Object> listChildrenAdd : listChildrenGrade) {
                if (maxGrade < ((Number) listChildrenAdd.get("grade")).intValue()) {
                    maxGrade = ((Number) listChildrenAdd.get("grade")).intValue();
                }
                if (minGrade > ((Number) listChildrenAdd.get("grade")).intValue()) {
                    minGrade = ((Number) listChildrenAdd.get("grade")).intValue();
                }
            }
            listAdd.put("diffGrade", maxGrade - minGrade);
        }
        for (HashMap<String, Object> listAdd : listGrade) {
            if (3 > ((Number) listAdd.get("diffGrade")).intValue()) {
                listGrade.remove(listAdd);
            }
        }
        TreeSet<Integer> hset = new TreeSet<Integer>();
        for (HashMap<String, Object> listAdd : listGrade) {
            hset.add(((Number) listAdd.get("diffGrade")).intValue());
        }
        List<HashMap<String, Object>> listOut = new ArrayList<>();
        for (Integer intOut : hset) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("diffGrade", intOut);
            Integer numNum = 0;
            for (HashMap<String, Object> listAdd : listGrade) {
                if (intOut.equals(listAdd.get("num"))) {
                    numNum = numNum + 1;
                }
            }
            hashMap.put("diffGradeNum", numNum);
            hashMap.put("proportion", ((numNum * 100) / listGrade.size()) + "%");
            listOut.add(hashMap);
        }
        return listOut;
    }

    /**
     * 日志环节分析
     *
     * @param queryMap
     * @return
     */
    @Override
    public List<Map<String, Object>> getlogSegmentAnalyse(Map<String, Object> queryMap) {
        List<Map<String, Object>> list = new ArrayList<>();
        Object depCode = queryMap.get("depCode");
        if (Objects.nonNull(depCode)) {
            queryMap.put("depCode", getParentDepCode((String) depCode));
        }
        List<Map<String, Object>> rsList = sysPortLogMapper.logSegmentAnalyse(queryMap);
        if (CollectionUtils.isEmpty(rsList)) {
            Map<String, Object> map = new HashMap<>();
            map.put("departNumType", 0);
            map.put("departNum", 0);
            map.put("scale", 1);
            list.add(map);
            return list;
        } else {
            int sum = 0;
            for (int i = 0; i < rsList.size(); i++) {
                Map map = rsList.get(i);
                map.put("departNum", ((Long) map.get("departNum")).intValue());
                sum += (int) map.get("departNum");
            }
            final double num = sum;
            return rsList.stream().map(e -> {
                e.put("scale", (int) e.get("departNum") / num);
                return e;
            }).collect(Collectors.toList());
        }
    }

    /**
     * 民警数比例分析
     *
     * @param queryMap
     * @return 实体类集合
     * @date 2019-01-14
     */
    @Override
    public List<Map<String, Object>> getPoliceNumProportionNew(Map<String, Object> queryMap) {
        List<Map<String, Object>> list = new ArrayList<>();
        Object depCode = queryMap.get("depCode");
        if (Objects.nonNull(depCode)) {
            queryMap.put("depCode", getParentDepCode((String) depCode));
        }
        List<Map<String, Object>> rsList = sysPortLogMapper.policeNumProportionNew(queryMap);
        if (CollectionUtils.isEmpty(rsList)) {
            Map<String, Object> map = new HashMap<>();
            map.put("policeNumType", 0);
            map.put("policeNum", 0);
            map.put("scale", 1);
            list.add(map);
            return list;
        } else {
            int sum = 0;
            for (int i = 0; i < rsList.size(); i++) {
                Map map = rsList.get(i);
                map.put("policeNum", ((Long) map.get("policeNum")).intValue());
                sum += (int) map.get("policeNum");
            }
            final double num = sum;
            return rsList.stream().map(e -> {
                e.put("scale", (int) e.get("policeNum") / num);
                return e;
            }).collect(Collectors.toList());
        }
    }

    /**
     * 耗时分析
     *
     * @param queryMap
     * @return 实体类集合
     * @date 2019-01-14
     */
    @Override
    public List<Map<String, Object>> analyseConsumeTime(Map<String, Object> queryMap) {
        List<Map<String, Object>> list = new ArrayList<>();
        Object depCode = queryMap.get("depCode");
        if (Objects.nonNull(depCode)) {
            queryMap.put("depCode", getParentDepCode((String) depCode));
        }
        List<Map<String, Object>> rsList = sysPortLogMapper.analyseConsumeTime(queryMap);
        return rsList;
    }

    /**
     * 终端分析
     *
     * @param queryMap
     * @return 实体类集合
     * @date 2019-01-14
     */
    @Override
    public List<Map<String, Object>> analyseTerminal(Map<String, Object> queryMap) {
        List<Map<String, Object>> list = new ArrayList<>();
        Object depCode = queryMap.get("depCode");
        if (Objects.nonNull(depCode)) {
            queryMap.put("depCode", getParentDepCode((String) depCode));
        }
        List<Map<String, Object>> rsList = sysPortLogMapper.analyseTerminal(queryMap);
        if (CollectionUtils.isEmpty(rsList)) {
            Map<String, Object> map = new HashMap<>();
            map.put("terminalNumType", 0);
            map.put("terminalNum", 0);
            map.put("scale", 1);
            list.add(map);
            return list;
        } else {
            int sum = 0;
            for (int i = 0; i < rsList.size(); i++) {
                Map map = rsList.get(i);
                map.put("terminalNum", ((Long) map.get("terminalNum")).intValue());
                sum += (int) map.get("terminalNum");
            }
            final double num = sum;
            return rsList.stream().map(e -> {
                e.put("scale", (int) e.get("terminalNum") / num);
                return e;
            }).collect(Collectors.toList());
        }
    }

    /**
     * 终端分析
     *
     * @param queryMap
     * @return 实体类集合
     * @date 2019-01-14
     */
    @Override
    public List<TerminalOperate> analyseTerminalOperate(Map<String, Object> queryMap) {
        Object depCode = queryMap.get("depCode");
        if (Objects.nonNull(depCode)) {
            queryMap.put("depCode", getParentDepCode((String) depCode));
        }
        List<TerminalOperate> rsList = sysPortLogMapper.analyseTerminalOperate(queryMap);
        if (CollectionUtils.isEmpty(rsList)) {
            return new ArrayList<>();
        }
        int temp = 0;
        String tempTerminalId = "";
        Map<String, TerminalOperate> map = new HashMap<>();
        for (TerminalOperate terminalOperate : rsList) {
            if (temp < terminalOperate.getOperateNum() && tempTerminalId.equals(terminalOperate.getTerminalId())) {
                map.put(terminalOperate.getTerminalId(), terminalOperate);
            }
            temp = terminalOperate.getOperateNum();
            tempTerminalId = terminalOperate.getTerminalId();
        }
        List<TerminalOperate> list = new ArrayList<>();
        for (String key : map.keySet()) {
            list.add(map.get(key));
        }
        return list;
    }

    /**
     * 操作时段分析
     *
     * @param queryMap
     * @return
     */
    @Override
    public List<Map<String, Object>> analyseTimeSegment(Map<String, Object> queryMap) {
        List<Map<String, Object>> list = new ArrayList<>();
        Object depCode = queryMap.get("depCode");
        if (Objects.nonNull(depCode)) {
            queryMap.put("depCode", getParentDepCode((String) depCode));
        }
        List<Map<String, Object>> rsList = sysPortLogMapper.analyseTimeSegment(queryMap);
        // if (CollectionUtils.isEmpty(rsList)) {
        //     Map<String, Object> map = new HashMap<>();
        //     map.put("operateHour", 0);
        //     map.put("operateNumber", 0);
        //     map.put("scale", 1);
        //     list.add(map);
        //     return list;
        // } else
        int sum = 0;
        for (int i = 0; i < rsList.size(); i++) {
            Map map = rsList.get(i);
            map.put("operateNumber", ((Long) map.get("operateNumber")).intValue());
            sum += (int) map.get("operateNumber");
        }
        final double num = sum;
        return rsList.stream().map(e -> {
            e.put("scale", (int) e.get("operateNumber") / num);
            return e;
        }).collect(Collectors.toList());
    }
}
