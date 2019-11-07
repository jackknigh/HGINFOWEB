package com.service.system.impl;

import com.dao.db1.system.SysOperationMapper;
import com.dao.entity.system.SysOperation;
import com.dto.constants.Constants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.system.SysOperationService;
import com.utils.sys.GenUtil;
import com.utils.sys.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiezx on 2019-01-08.
 */
@Transactional
@Service("sysOperationService")
public class SysOperationServiceImpl implements SysOperationService {
    @Autowired
    private SysOperationMapper sysOperationMapper;

    // @Override
    // public int insert(SysOperation sysOperation) {
    //     return sysOperationMapper.insert(sysOperation);
    // }

    @Override
    public SysOperation selectByPrimaryKey(String id) {
        return sysOperationMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysOperation> selectAll() {
        return sysOperationMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(SysOperation sysOperation) {
        return sysOperationMapper.updateByPrimaryKey(sysOperation);
    }

    @Override
    public int save(SysOperation sysOperation) {
        int ret = 0;

        if ((sysOperation != null) && (!TextUtils.isEmpty(sysOperation.getUpdateFlg()))) {
            // 修改
            if (sysOperation.getUpdateFlg().equalsIgnoreCase("update")) {
                sysOperation.setUpdateDate(new Date());
                return sysOperationMapper.updateByPrimaryKey(sysOperation);
            }
            // 新增
            if (sysOperation.getUpdateFlg().equalsIgnoreCase("add")) {
                sysOperation.setId(GenUtil.getUUID());
                sysOperation.setCreateDate(new Date());
                return sysOperationMapper.insert(sysOperation);
            }
        }
        return ret;
    }

    @Override
    public PageInfo<SysOperation> search(Map<String, Object> queryMap) {
        Integer pageNUm = (Integer) queryMap.get(Constants.PAGE_CURRENT);
        Integer pageSize = (Integer) queryMap.get(Constants.PAGE_SIZE);
        PageHelper.startPage(pageNUm, pageSize);
        List<SysOperation> search = sysOperationMapper.search(queryMap);
        PageInfo<SysOperation> pageList = new PageInfo<>(search);
        return pageList;
    }

    @Override
    public String updateStateOpenByIds(List<String> ids) {
        String resStr ="success";
        //sysOperationMapper.updateStateByIds(ids,1);
        return resStr;
    }

    @Override
    public String updateStateCloseByIds(List<String> ids) {
        String resStr ="success";
       // sysOperationMapper.updateStateByIds(ids,0);
        return resStr;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteByIds(List<String> ids) {
        return sysOperationMapper.batchDelete(ids);
    }
}
