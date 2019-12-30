package com.service.system;

import com.dao.entity.system.SysResource;
import com.dao.entity.system.SysRoleFunctionResource;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * SysResource接口
 * Created by Xiezx on 2019-01-10.
 */
public interface SysResourceService {

    /**
     * 启用角色
     *
     * @param ids
     * @return
     * @date 2019-01-09
     */
    String updateStateOpenByIds(List<String> ids);

    /**
     * 禁用角色
     *
     * @param ids
     * @return
     * @date 2019-01-09
     */
    String updateStateCloseByIds(List<String> ids);

    /**
     * 新增资源
     *
     * @param record 实体类
     * @return 1代表成功
     * @date 2019-01-10
     */
    int insert(SysResource record);

    /**
     * 根据id查询资源详情
     *
     * @param id
     * @return 实体类
     * @date 2019-01-10
     */
    SysResource selectByPrimaryKey(String id);

    /**
     * 查询所有资源
     *
     * @param
     * @return 实体类集合
     * @date 2019-01-10
     */
    List<SysResource> selectAll();

    /**
     * 修改资源
     *
     * @param record 实体类
     * @return 1代表成功
     * @date 2019-01-10
     */
    int updateByPrimaryKey(SysResource record);

    /**
     * 分页查询资源
     *
     * @param queryMap
     * @return 实体类集合
     * @date 2019-01-10
     */
    PageInfo<SysResource> search(Map<String, Object> queryMap);

    /**
     * 保存资源
     *
     * @param sysResource
     * @return 1代表成功
     * @date 2019-01-10
     */
    int save(SysResource sysResource);

    /**
     * 根据条件查询所有资源
     *
     * @param queryMap (功能id必填，资源名可填)
     * @return 实体类集合
     * @date 2019-01-10
     */
    List<SysResource> selectResource(Map<String, Object> queryMap);


    /**
     * 修改资源是否勾选状态
     *
     * @param queryMap
     * @return 1代表成功
     * @date 2019-01-16
     */
    int updateStatus(Map<String, Object> queryMap);

    /**
     * 查询资源中的所有组件
     *
     * @param
     * @return 实体类集合
     * @date 2019-01-16
     */
    List<SysResource> selectComponent(Map<String, Object> queryMap);

    /**
     * 根据功能id,角色id查询所有资源
     *
     * @param queryMap
     * @return 实体类集合
     * @date 2019-01-22
     */
    List<SysRoleFunctionResource>  selectAuthorityByFunctionId(Map<String, Object> queryMap);

    /**
     * 根据功能id所有资源
     *
     * @param id
     * @return 实体类集合
     * @date 2019-01-22
     */
    List<SysResource> selectResourceByFunctionId(String id);

    /**
     * 根据资源主键Ids查询资源
     *
     * @param ids
     * @return 实体类集合
     * @date 2019-01-24
     */
    List<SysResource>  selectResourceByIds(List<String> ids);

    /**
     * 根据资源主键逻辑删除
     * @param ids
     * @return
     */
    int deleteResourceByIds(List<String> ids);

    /**
     * 根据url获取资源
     *
     * @param afterUrl
     * @return 实体类集合
     * @date 2019-01-22
     */
    List<SysResource> selectResourceByAfterUrl(String afterUrl);
}

