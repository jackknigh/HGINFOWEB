package com.dao.db1.zjreport;

import com.dao.entity.zjreport.RepMonthLink;

import java.util.List;

public interface RepMonthLinkDao {

    int deleteByPrimaryKey(String id);

    int deleteByLinkowidList(List<String> ids);


    int insert(RepMonthLink record);

    int insertBatch(List<RepMonthLink> records);

    RepMonthLink selectByPrimaryKey(String id);

    List<RepMonthLink> selectByLinkowidList(List<String> ids);

    List<RepMonthLink> selectAll();

    List<RepMonthLink> detailRowSumChangeList(String id);

    int updateByPrimaryKey(RepMonthLink record);
}