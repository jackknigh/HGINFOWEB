package com.service.system.impl;


import com.dao.db1.system.SysResourceMapper;
import com.dao.entity.system.SysResource;
import com.dao.entity.system.SysRoleFunctionResource;
import com.dto.constants.Constants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.system.SysResourceService;
import com.utils.sys.GenUtil;
import com.utils.sys.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SysResource接口实现类
 * Created by Xiezx on 2019-01-10.
 */
@Transactional
@Service("sysResourceService")
public class SysResourceServiceImpl implements SysResourceService {
    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Override
    public String updateStateOpenByIds(List<String> ids) {
        String resStr = "success";
        sysResourceMapper.updateStateByIds(ids, 1);
        return resStr;
    }

    @Override
    public String updateStateCloseByIds(List<String> ids) {
        String resStr = "success";
        sysResourceMapper.updateStateByIds(ids, 0);
        return resStr;
    }

    @Override
    public int insert(SysResource sysResource) {
        return sysResourceMapper.insert(sysResource);
    }

    @Override
    public SysResource selectByPrimaryKey(String id) {
        return sysResourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysResource> selectAll() {
        return sysResourceMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(SysResource sysResource) {
        return sysResourceMapper.updateByPrimaryKey(sysResource);
    }

    @Override
    public PageInfo<SysResource> search(Map<String, Object> queryMap) {
        Integer pageNUm = (Integer) queryMap.get(Constants.PAGE_CURRENT);
        Integer pageSize = (Integer) queryMap.get(Constants.PAGE_SIZE);
        PageHelper.startPage(pageNUm, pageSize);
        List<SysResource> search = sysResourceMapper.search(queryMap);
        PageInfo<SysResource> pageList = new PageInfo<>(search);
        return pageList;
    }

    @Override
    public int save(SysResource sysResource) {
        int ret = 0;

        if ((sysResource != null) && (!TextUtils.isEmpty(sysResource.getUpdateFlg()))) {
            // 修改
            if (sysResource.getUpdateFlg().equalsIgnoreCase("update")) {
                //校验资源编号是否已经存在
                Map<String, Object> queryMap = new HashMap<String, Object>();
                String code = sysResource.getCode();
                String id = sysResource.getId();
                queryMap.put("code", code);
                List<SysResource> sysResources = sysResourceMapper.selectResourceByCode(queryMap);
                if (CollectionUtils.isEmpty(sysResources) || !id.equals(sysResources.get(0).getId())) {
                    return -1;
                } else {
                    sysResource.setUpdateDate(new Date());
                    return sysResourceMapper.updateByPrimaryKey(sysResource);
                }
            }
            // 新增
            if (sysResource.getUpdateFlg().equalsIgnoreCase("add")) {
                //校验资源编号是否已经存在
                String code = sysResource.getCode();
                Map<String, Object> queryMap = new HashMap<String, Object>();
                queryMap.put("code", code);
                List<SysResource> sysResources = sysResourceMapper.selectResourceByCode(queryMap);
                if (CollectionUtils.isEmpty(sysResources) && sysResources.size() == 0) {
                    sysResource.setChecked(0);
                    sysResource.setId(GenUtil.getUUID());
                    sysResource.setCreateDate(new Date());
                    return sysResourceMapper.insert(sysResource);
                } else {
                    return -1;
                }
            }
        }
        return ret;
    }

    @Override
    public List<SysResource> selectResource(Map<String, Object> queryMap) {
        return sysResourceMapper.selectResource(queryMap);
    }

    @Override
    public int updateStatus(Map<String, Object> queryMap) {
        return sysResourceMapper.updateStatus(queryMap);
    }

    @Override
    public List<SysResource> selectComponent(Map<String, Object> queryMap) {
        return sysResourceMapper.selectComponent(queryMap);
    }

    @Override
    public List<SysRoleFunctionResource> selectAuthorityByFunctionId(Map<String, Object> queryMap) {
        return sysResourceMapper.selectAuthorityByFunctionId(queryMap);
    }

    @Override
    public List<SysResource> selectResourceByFunctionId(String id) {
        return sysResourceMapper.selectResourceByFunctionId(id);
    }

    @Override
    public List<SysResource> selectResourceByIds(List<String> ids) {
        return sysResourceMapper.selectResourceByIds(ids);
    }

    /**
     * 根据资源主键逻辑删除
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteResourceByIds(List<String> ids) {
        return sysResourceMapper.deleteByIds(ids);
    }

    /**
     * 根据url获取资源
     *
     * @param afterUrl
     * @return 实体类集合
     * @date 2019-01-22
     */
    @Override
    public List<SysResource> selectResourceByAfterUrl(String afterUrl) {
        return sysResourceMapper.selectByAfterUrl(afterUrl);
    }
}
