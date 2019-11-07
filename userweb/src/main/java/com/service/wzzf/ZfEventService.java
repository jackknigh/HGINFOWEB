package com.service.wzzf;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by CuiL on 2018-12-03.
 */
public interface ZfEventService {

    List<?> quXianStat(Map<String, Object> queryMap);
    List<?> jieDaoStat(Map<String, Object> queryMap);
    PageInfo<?> jinJiEventList(Map<String, Object> queryMap);
    PageInfo<?> classEventList(Map<String, Object> queryMap);

}
