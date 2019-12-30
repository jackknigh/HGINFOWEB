package com.service.system;

import com.dao.entity.system.SysQuartzConfig;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SysQuartzConfigService {

    List<SysQuartzConfig> listAllOk(String depCode, String userId, String taskType, Integer state);

    PageInfo list(Integer currentPage, Integer pageSize, String depCode, String userId, String taskType);

    SysQuartzConfig detail(String id);

    int save(SysQuartzConfig sysDicColumnValue);

    void delete(List<String> ids);

    void startUse(List<String> ids, String state) throws ClassNotFoundException;
}
