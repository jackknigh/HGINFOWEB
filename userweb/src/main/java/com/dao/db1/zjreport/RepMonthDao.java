package com.dao.db1.zjreport;

import com.dao.entity.zjreport.RepMonth;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RepMonthDao {

    int deleteByPrimaryKey(String id);

    int insert(RepMonth record);

    int insertBatch(List<RepMonth> records);

    int updateStateBatch(@Param("state") Integer state, @Param("list") List<RepMonth> records);

    int updateStateBatchByIds(@Param("state") Integer state, @Param("updator") String updator
            , @Param("updatorName") String updatorName, @Param("list") List<String> ids);

    int updateStateBatchJoinByDetailIds(@Param("state") Integer state, @Param("list") List<String> ids);

    RepMonth selectByPrimaryKey(String id);

    List<RepMonth> selectByIds(List<String> ids);

    List<RepMonth> selectByDetailIds(List<String> ids);

    List<RepMonth> selectAll();

    List<RepMonth> search(Map<String, Object> queryMap);

    int updateByPrimaryKey(RepMonth record);
}