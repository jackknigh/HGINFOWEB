package com.service.system;

import com.dao.entity.system.SysDicColumn;
import com.dao.entity.system.SysDicComms;
import com.dao.entity.system.SysDicTable;

import java.util.List;

public interface SysDicService {

    List<SysDicTable> tableList();

    List<SysDicColumn> columnList(String tableID);

    List<SysDicComms> calculationList(String dicType);

    int calculationSave(SysDicComms sysDicComms);

    SysDicComms detail(String id);

    void startUse(List<String> ids, int state);

    int delete(List<String> ids);
}
