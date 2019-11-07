package com.service.system;

import com.dao.entity.system.SysFunction;

import java.util.List;
import java.util.Map;

/**
 * SysFunction接口
 * Created by Xiezx on 2019-01-12.
 */
public interface SysFunctionService {
    /**
     * 根据id批量删除功能
     *
     * @param ids
     * @return 1成功
     * @date 2019-01-12
     */
    Map<String, List<String>> deleteByPrimaryKey(List<String> ids);

    /**
     * 保存功能
     *
     * @param record
     * @return 1成功
     * @date 2019-01-12
     */
    int insert(SysFunction record);

    /**
     * 根据id查询功能详情
     *
     * @param id
     * @return 实体类
     * @date 2019-01-12
     */
    SysFunction selectByPrimaryKey(String id);

    /**
     * 查询所有功能
     *
     * @param
     * @return 1成功
     * @date 2019-01-12
     */
    List<SysFunction> selectAll();

    /**
     * 修改功能
     *
     * @param record
     * @return 1成功
     * @date 2019-01-12
     */
    int updateByPrimaryKey(SysFunction record);

    /**
     * 保存功能
     *
     * @param sysFunction
     * @return 1成功
     * @date 2019-01-12
     */
    int save(SysFunction sysFunction);

    /**
     * 根据查询所有顶级功能名称
     *
     * @param  queryMap
     * @return list集合
     * @date 2019-01-12
     */
    List<SysFunction> selectAllTopFunction(Map<String, Object> queryMap);

    /**
     * 根据查询所有子功能名称
     *
     * @param id
     * @return list集合
     * @date 2019-01-12
     */
    List<Map<String, Object>>  selectAllChildren(String id);

    String updateStateOpenByIds(List<String> ids);

    String updateStateCloseByIds(List<String> ids);


    void deleteById(String id);
}
