package com.service.system;

import com.dao.entity.system.SysDicColumnValue;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SysDicColumnValueService {

    PageInfo list(Integer currentPage, Integer pageSize);

    SysDicColumnValue detail(String id);

    int save(SysDicColumnValue sysDicColumnValue);

    void startUse(List<String> ids,int state);

    void delete(List<String> ids);
}
