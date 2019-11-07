package com.dao.db1.zjstat;

import com.dao.entity.zjstat.ZbresCalResDay;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZbresCalResDayMapper {
    /**
    *<p>方法:deleteByTimeAndDepCode 用于给edit表删除历史数据使用 </p>
    *<ul>
     *<li> @param beginDate TODO</li>
     *<li> @param ednDate TODO</li>
     *<li> @param depCode TODO</li>
    *<li>@return int  </li>
    *<li>@author CuiLiang </li>
    *<li>@date 2019-03-22 8:58  </li>
    *</ul>
    */
    int deleteByTimeAndDepCode(@Param("tableName") String tableName, @Param("zbAssessMainId") String zbAssessMainId, @Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("depCode") String depCode);

    void insertBatch(@Param("tableName") String tableName, @Param("list") List<ZbresCalResDay> zbresCalResDayList);


    String getMaxStatDate(@Param("tableName") String tableName, @Param("zbAssessMainId") String zbAssessMainId);

}