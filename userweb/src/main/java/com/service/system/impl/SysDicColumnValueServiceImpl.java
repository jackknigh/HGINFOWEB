package com.service.system.impl;

import com.dao.db1.system.SysDicColumnValueMapper;
import com.dao.entity.system.SysDicColumnValue;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.system.SysDicColumnValueService;
import com.utils.sys.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("sysDicColumnValueService")
public class SysDicColumnValueServiceImpl implements SysDicColumnValueService {

    @Autowired
    private SysDicColumnValueMapper sysDicColumnValueMapper;

    @Override
    public PageInfo list(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<SysDicColumnValue> sysDicColumnValues = sysDicColumnValueMapper.selectAll();
        return new PageInfo<>(sysDicColumnValues);
    }

    @Override
    public SysDicColumnValue detail(String id) {
        return sysDicColumnValueMapper.selectByPrimaryKey(id);
    }

    @Override
    public int save(SysDicColumnValue sysDicColumnValue) {
        int ret = 0;
        if (sysDicColumnValue == null) return ret;

        //修改
        if (!TextUtils.isEmpty(sysDicColumnValue.getUpdateFlg())) {
            if (sysDicColumnValue.getUpdateFlg().equalsIgnoreCase("update")) {
                return sysDicColumnValueMapper.updateByPrimaryKey(sysDicColumnValue);
            }
            //删除
            if (sysDicColumnValue.getUpdateFlg().equalsIgnoreCase("delete")) {
                return sysDicColumnValueMapper.deleteByPrimaryKey(sysDicColumnValue.getId());
            }
            //新增
            if (sysDicColumnValue.getUpdateFlg().equalsIgnoreCase("add")) {
                sysDicColumnValue.setId(TextUtils.getUUID());
                return sysDicColumnValueMapper.insert(sysDicColumnValue);
            }
        }

        return ret;
    }

    @Override
    public void startUse(List<String> ids, int state) {
        sysDicColumnValueMapper.updateStateByPrimaryKey(ids,state);
    }

    @Override
    public void delete(List<String> ids) {
        sysDicColumnValueMapper.deleteByPrimaryKeys(ids);
    }
}
