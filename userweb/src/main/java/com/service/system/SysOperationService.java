package com.service.system;

import com.dao.entity.system.SysOperation;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * SysOperation接口
 * Created by Xiezx on 2019-01-08.
 */
public interface SysOperationService {

    /**
     * 新增系统
     *
     * @param record 实体类
     * @return 1代表成功
     * @date 2019-01-8
     */
    // int insert(SysOperation record);

    /**
     * 根据id查询系统
     *
     * @param id
     * @return 实体类
     * @date 2019-01-8
     */
    SysOperation selectByPrimaryKey(String id);

    /**
     * 查询系统
     *
     * @param
     * @return 实体类集合
     * @date 2019-01-8
     */
    List<SysOperation> selectAll();

    /**
     * 更新系统
     *
     * @param record 实体类
     * @return 1代表成功
     * @date 2019-01-8
     */
    int updateByPrimaryKey(SysOperation record);

    /**
     * 保存机构
     *
     * @param sysOperation
     * @return 1成功
     * @date 2019-01-8
     */
    int save(SysOperation sysOperation);

    /**
     * 分页条件查询系统
     *
     * @param queryMap
     * @return 实体类集合
     * @date 2019-01-8
     */
    PageInfo<SysOperation> search(Map<String, Object> queryMap);

    String updateStateOpenByIds(List<String> ids);

    String updateStateCloseByIds(List<String> ids);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteByIds(List<String> ids);
}
