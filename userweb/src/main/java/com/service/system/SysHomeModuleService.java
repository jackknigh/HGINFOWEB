package com.service.system;

import com.dao.entity.system.SysHomeModule;

import java.util.List;
import java.util.Map;

/**
 * SysHomeModule接口
 * Created by Xiezx on 2019-01-12.
 */
public interface SysHomeModuleService  {
    /**
     * 根据id删除组件
     *
     * @param id
     * @return 1成功
     * @date 2019-01-12
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增首页组件
     *
     * @param sysHomeModule
     * @return 1成功
     * @date 2019-01-12
     */
    int insert(SysHomeModule sysHomeModule);

    /**
     * 根据id展示首页组件
     *
     * @param id
     * @return 实体类
     * @date 2019-01-12
     */
    SysHomeModule selectByPrimaryKey(String id);

    /**
     * 保存首页组件
     *
     * @param
     * @return 实体类集合
     * @date 2019-01-12
     */
    List<SysHomeModule> selectAll();

    /**
     * 保存首页组件
     *
     * @param sysHomeModule
     * @return 1成功
     * @date 2019-01-12
     */
    int updateByPrimaryKey(SysHomeModule sysHomeModule);
    //******************************************************自定义开始*****************************************************

    /**
     * 保存首页组件
     *
     * @param sysHomeModule
     * @return 1成功
     * @date 2019-01-12
     */
    int save(SysHomeModule sysHomeModule);

    /**
     * 分页查询首页组件
     *
     * @param queryMap
     * @return 实体类集合
     * @date 2019-01-12
     */
    List<SysHomeModule> search(Map<String, Object> queryMap);

    /**
     * 查询所有顶级首页组件名称
     *
     * @param
     * @return list集合
     * @date 2019-01-12
     */
    List<SysHomeModule> selectAllTopHomeModule();

    /**
     * 根据查询所有子级首页组件名称
     *
     * @param id
     * @return list集合
     * @date 2019-01-12
     */
    List<SysHomeModule> selectAllChildren(String id);
}
