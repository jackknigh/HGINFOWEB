package com.service.system;

import com.dao.db1.zjstat.entity.StatChartConfig;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface StatChartConfigService {

    PageInfo list(Integer currentPage, Integer pageSize, String depCode, String userId);

    StatChartConfig detail(String id);

    int save(StatChartConfig statDicTimes);

    void delete(List<String> ids);
}
