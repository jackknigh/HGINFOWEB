package com.service.system;

import com.dao.entity.system.SysPortLog;
import com.dto.pojo.system.TerminalOperate;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SysPortLog 接口调用日志接口
 * Created by Xiezx on 2019-01-15.
 */
public interface SysPortLogService {

    /**
     * 新增接口调用日志
     *
     * @param sysPortLog
     * @return 1代表成功
     * @date 2019-01-15
     */
    int insert(SysPortLog sysPortLog);

    /**
     * 根据id查询接口调用日志详情
     *
     * @param id
     * @return 实体类
     * @date 2019-01-15
     */
    SysPortLog selectByPrimaryKey(String id);

    /**
     * 根据id查询接口调用日志详情
     *
     * @param
     * @return 实体类集合
     * @date 2019-01-15
     */
    List<SysPortLog> selectAll();

    //******************************************************自定义开始*****************************************************

    /**
     * 保存接口调用日志
     *
     * @param sysPortLog
     * @return 1代表成功
     * @date 2019-01-15
     */
    int save(SysPortLog sysPortLog);

    /**
     * 分页查询接口调用日志
     *
     * @param queryMap
     * @return 实体类集合
     * @date 2019-01-14
     */
    PageInfo<SysPortLog> search(Map<String, Object> queryMap);

    /**
     * 民警数比例分析
     *
     * @return 实体类集合
     * @date 2019-01-14
     */
    List<HashMap<String, Object>> getPoliceNumProportion(Map<String, Object> queryMap);

    /**
     * 单据跨越层级比例分析
     *
     * @author gwq
     * @date 2019/1/24 14:50
     */
    List<HashMap<String, Object>> getHierarchyNumProportion(Map<String, Object> queryMap);


    /**
     * 日志环节分析
     *
     * @param queryMap
     * @return
     */
    List<Map<String, Object>> getlogSegmentAnalyse(Map<String, Object> queryMap);

    /**
     * 民警数比例分析
     *
     * @return 实体类集合
     * @date 2019-01-14
     */
    List<Map<String, Object>> getPoliceNumProportionNew(Map<String, Object> queryMap);

    /**
     * 耗时分析
     *
     * @return 实体类集合
     * @date 2019-01-14
     */
    List<Map<String, Object>> analyseConsumeTime(Map<String, Object> queryMap);

    /**
     * 终端分析
     *
     * @return 实体类集合
     * @date 2019-01-14
     */
    List<Map<String, Object>> analyseTerminal(Map<String, Object> queryMap);

    /**
     * 终端分析
     *
     * @return 实体类集合
     * @date 2019-01-14
     */
    List<TerminalOperate> analyseTerminalOperate(Map<String, Object> queryMap);

    /**
     * 操作时段分析
     *
     * @param queryMap
     * @return
     */
    List<Map<String, Object>> analyseTimeSegment(Map<String, Object> queryMap);
}
