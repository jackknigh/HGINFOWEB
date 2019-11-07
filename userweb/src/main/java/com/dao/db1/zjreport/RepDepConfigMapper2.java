package com.dao.db1.zjreport;

import com.dao.entity.zjreport.RepDepConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RepDepConfigMapper2 {

    int deleteByPrimaryKey(String id);


    int insert(RepDepConfig record);


    RepDepConfig selectByPrimaryKey(String id);

    RepDepConfig selectByDepCode(String depCodeLike);


    List<RepDepConfig> selectAll(@Param("depCodeLike") String depCodeLike, @Param("depCode") String depCode);

    List<RepDepConfig> selectAllRepCode();

    int updateByPrimaryKey(RepDepConfig record);

    List<RepDepConfig> selectAllChild(String depCode);
}