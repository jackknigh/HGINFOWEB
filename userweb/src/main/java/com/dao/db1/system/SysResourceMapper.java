package com.dao.db1.system;

import com.dao.entity.system.SysResource;
import com.dao.entity.system.SysRoleFunctionResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysResourceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_resource
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(List<String> ids);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_resource
     *
     * @mbg.generated
     */
    int insert(SysResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_resource
     *
     * @mbg.generated
     */
    SysResource selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_resource
     *
     * @mbg.generated
     */
    List<SysResource> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_resource
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysResource record);
    //******************************************************自定义开始*****************************************************

    /**
     * 分页查询资源
     *
     * @param queryMap
     * @return 实体类集合
     * @date 2019-01-10
     */
    List<SysResource> search(Map<String, Object> queryMap);

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
     * 根据功能id查询所有资源
     *
     * @param id
     * @return 实体类集合
     * @date 2019-01-10
     */
    List<SysResource> selectResourceByFunctionId(String id);

    /**
     * 根据功能id查询所有资源
     *
     * @param queryMap
     * @return 实体类集合
     * @date 2019-01-22
     */
    List<SysRoleFunctionResource> selectAuthorityByFunctionId(Map<String, Object> queryMap);

    /**
     * 根据资源编码查询资源
     *
     * @param queryMap
     * @return 实体类集合
     * @date 2019-01-24
     */
    List<SysResource> selectResourceByCode(Map<String, Object> queryMap);

    /**
     * 根据资源主键Ids查询资源
     *
     * @param ids
     * @return 实体类集合
     * @date 2019-01-24
     */
    List<SysResource> selectResourceByIds(List<String> ids);

    /**
     * 更新开启状态
     * @param ids
     * @param state
     * @return
     */
    int updateStateByIds(@Param("ids") List<String> ids, @Param("state") Integer state);

    /**
     * 逻辑删除
     * @param ids
     * @return
     */
    int deleteByIds(@Param("ids") List<String> ids);

    /**
     * 根据url查询资源
     * @param afterUrl
     * @return
     */
    List<SysResource> selectByAfterUrl(@Param("afterUrl") String afterUrl);
}