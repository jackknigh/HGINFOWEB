package com.dao.db1.zjstat;

import com.dao.entity.zjstat.ZbresCalResDay;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface Db2ComStatMapper {

    List<?> comStatInterface(Map<String, Object> queryMap);

    List<ZbresCalResDay> comStatZbresCalResDayAllSql(@Param("allSql") String allSql);

}