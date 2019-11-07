package com.dao.db1.zjstat;

import com.dao.entity.zjstat.ZbresStatRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZbresStatResMapper {
    void insertBatch(@Param("tableName") String tableName, @Param("list") List<ZbresStatRes> zbresStatRes);

    void deleteAllData(@Param("tableName") String tableName);

    List<ZbresStatRes> stateSumDetailValue(@Param("tableName") String tableName);


    List<?> selectLikeDepCode(@Param("tableName") String tableName, @Param("depCode") String depCode, @Param("statDepCode") String statDepCode,
                              @Param("zbAssessMainRefid") String zbAssessMainRefid, @Param("statUnit") String statUnit,
                              @Param("statDate") String statDate, @Param("statCycle") String statCycle,
                              @Param("calculationRefId") String calculationRefId, @Param("preNum") int preNum);


}