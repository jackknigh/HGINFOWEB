package com.service.system.impl;

import com.dao.db1.system.SysDicColumnMapper;
import com.dao.db1.system.SysDicCommsMapper;
import com.dao.db1.system.SysDicTableMapper;
import com.dao.entity.system.SysDicColumn;
import com.dao.entity.system.SysDicComms;
import com.dao.entity.system.SysDicTable;
import com.service.system.SysDicService;
import com.utils.sys.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("SysDicServiceImpl")
public class SysDicServiceImpl implements SysDicService {

    @Autowired
    private SysDicTableMapper sysDicTableMapper;
    @Autowired
    private SysDicColumnMapper sysDicColumnMapper;
    @Autowired
    private SysDicCommsMapper sysDicCommsMapper;

    @Override
    public List<SysDicTable> tableList() {
        return sysDicTableMapper.selectAll();
    }

    @Override
    public List<SysDicColumn> columnList(String tableID) {
        return sysDicColumnMapper.selectByTableID(tableID);
    }

    @Override
    public List<SysDicComms> calculationList(String dicType) {
        return sysDicCommsMapper.calculationList(dicType);
    }

    @Override
    public int calculationSave(SysDicComms sysDicComms) {
        int ret = 0;
        if (sysDicComms == null) return 0;

        //修改
        if (!TextUtils.isEmpty(sysDicComms.getUpdateFlg())) {
            if (sysDicComms.getUpdateFlg().equalsIgnoreCase("update")) {
                return sysDicCommsMapper.updateByPrimaryKey(sysDicComms);
            }
            //删除
            if (sysDicComms.getUpdateFlg().equalsIgnoreCase("delete")) {
                return sysDicCommsMapper.deleteByPrimaryKey(sysDicComms.getId());
            }
            //新增
            if (sysDicComms.getUpdateFlg().equalsIgnoreCase("add")) {
                SysDicComms sysDicComms1 = sysDicCommsMapper.selectByDicCode(sysDicComms.getDicCode());
                if (sysDicComms1 != null) {
                    return -1;
                }
                sysDicComms.setId(TextUtils.getUUID());
                return sysDicCommsMapper.insert(sysDicComms);
            }
        }

        return ret;
    }

    @Override
    public SysDicComms detail(String id) {
        return sysDicCommsMapper.selectByPrimaryKey(id);
    }

    @Override
    public void startUse(List<String> ids, int state) {
        sysDicCommsMapper.updateStateByPrimaryKey(ids, state);
    }

    @Override
    public int delete(List<String> ids) {
        return sysDicCommsMapper.deleteByPrimaryKeys(ids);
    }
}
