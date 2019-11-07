package com.dao.db1.zjstat;

import com.dao.entity.zjstat.ZbresBrightpointResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface Db1ComStatMapper {

    List<?> comStatInterface(Map<String, Object> queryMap);

    List<ZbresBrightpointResource> comBrightInterfaceAllSql(@Param("allSql") String allSql);

}