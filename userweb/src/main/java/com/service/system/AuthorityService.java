package com.service.system;

import com.dao.entity.system.SysFunction;

import java.util.List;
import java.util.Map;

/**
 * 权限控制接口
 * Created by Xiezx on 2019-01-18.
 */
public interface AuthorityService {
    /**
     * 根据查询所有顶级功能名称
     *
     * @param queryMap
     * @return list集合
     * @date 2019-01-12
     */
    List<Object> selectAllTopFunction(Map<String, Object> queryMap);

    /**
     * 根据查询所有子功能名称
     *
     * @param id
     * @return list集合
     * @date 2019-01-12
     */
    List<SysFunction> selectAllChildren(String id);

    /**
     * 根据角色ids查询功能权限
     *
     * @param ids
     * @return list集合
     * @date 2019-01-15
     */
    List<Object> selectFunctionByRoleIds(List<String> ids, Integer functionType);
}
