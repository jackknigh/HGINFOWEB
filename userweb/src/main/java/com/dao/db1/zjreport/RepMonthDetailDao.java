package com.dao.db1.zjreport;

import com.dao.entity.zjreport.RepMonthDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RepMonthDetailDao {

    int deleteByPrimaryKey(String id);

    int deleteByRefId(String id);

    int insert(RepMonthDetail record);

    int insertBatch(List<RepMonthDetail> records);

    int updateStateBatch(@Param("state") Integer state, @Param("list") List<RepMonthDetail> records);

    int updateStateBatchByRefIds(@Param("state") Integer state, @Param("list") List<String> ids);

    RepMonthDetail selectByPrimaryKey(String id);

    List<RepMonthDetail> selectAll();

    List<RepMonthDetail> selectByRepMonthRefId(String repMonthRefId);

    List<RepMonthDetail> selectByRepMonthRefIdAndVer(@Param("repMonthRefId") String repMonthRefId, @Param("ver") Integer ver);

    List<RepMonthDetail> selectByPrimaryKeyAndSheetSort(@Param("repMonthRefId") String repMonthRefId, @Param("sheetSort") Integer sheetSort);

    List<RepMonthDetail> selectSumByRepMonthRefIds(List<String> ids);

    List<RepMonthDetail> selectByRepMonthRefIds(List<String> ids);

    List<RepMonthDetail> selectByRepMonthRefIdsAndSheetName(@Param("list") List<String> ids, @Param("sheetName") String sheetName);

    int updateByPrimaryKey(RepMonthDetail record);



}