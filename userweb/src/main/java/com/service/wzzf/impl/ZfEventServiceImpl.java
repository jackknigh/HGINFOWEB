package com.service.wzzf.impl;/*
package com.service.wzzf.impl;

import com.dao.db2.wzzf.ZfEventMapper;
import com.dto.constants.Constants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.wzzf.ZfEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

*/
/**
 * Created by CuiL on 2019-03-04.
 *//*

@Transactional
@Service("ZfEventService")
public class ZfEventServiceImpl implements ZfEventService{

    @Autowired
    ZfEventMapper zfEventMapper;

    @Override
    public List<?> quXianStat(Map<String, Object> queryMap) {
        return zfEventMapper.quXianStat(queryMap);
    }

    @Override
    public List<?> jieDaoStat(Map<String, Object> queryMap) {
        return zfEventMapper.jieDaoStat(queryMap);
    }

    @Override
    public PageInfo<?> jinJiEventList(Map<String, Object> queryMap) {
        Integer pageNum = (Integer) queryMap.get(Constants.PAGE_CURRENT);
        Integer pageSize = (Integer) queryMap.get(Constants.PAGE_SIZE);
        PageHelper.startPage(pageNum, pageSize);
        List<?> res = zfEventMapper.jinJiEventList(queryMap);
        PageInfo<?> pageList = new PageInfo<>(res);
        return pageList;
    }

    @Override
    public PageInfo<?> classEventList(Map<String, Object> queryMap) {
        Integer pageNum = (Integer) queryMap.get(Constants.PAGE_CURRENT);
        Integer pageSize = (Integer) queryMap.get(Constants.PAGE_SIZE);
        PageHelper.startPage(pageNum, pageSize);
        List<?> res = zfEventMapper.classEventList(queryMap);
        PageInfo<?> pageList = new PageInfo<>(res);
        return pageList;
    }
}
*/
