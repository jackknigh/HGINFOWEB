package com.dao.db1.zjstat;

import com.dao.entity.zjstat.ZbresStatResDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ZbresStatResDetailMapper {

    BigDecimal selectByAssessDetail1Id(@Param("tableName") String tableName, String assessDetail1Id, String statUnit, String statCycle);

    void insertBatch(@Param("tableName") String tableName, @Param("list") List<ZbresStatResDetail> zbresStatResDetailList);


    List<?> selectByDepCode(@Param("tableName") String tableName, @Param("depCode") String depCode, @Param("zbAssessMainRefid") String zbAssessMainRefid,
                            @Param("statUnit") String statUnit, @Param("statDate") String statDate, @Param("statCycle") String statCycle,
                            @Param("calculationRefId") String calculationRefId);

    List<ZbresStatResDetail> stateSumValueByDateAndDepCode(@Param("tableName") String tableName, @Param("assessMainId") String assessMainId, @Param("assessDetail1Id") String assessDetail1Id,
                                                           @Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("depCode") String depCode, @Param("statCycle") String statCycle);

    void deleteAllData(@Param("tableName") String tableName);
}