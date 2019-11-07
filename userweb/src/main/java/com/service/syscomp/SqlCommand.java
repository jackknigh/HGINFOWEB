package com.service.syscomp;


import com.dao.entity.syserp.DataDictMain;

import java.util.ArrayList;
import java.util.List;

public interface SqlCommand {

    public DataDictMain GetDataDict(String dataDictId);
    public ArrayList<Object> ExecSelfSQL(String selfSql, List<Object> parms);
    public ArrayList<Object> ExecSQL(String sqlId, ArrayList<Object> parms);
    public ArrayList<Object> ExecSQLNoSession(String sqlId, String accountId, String operatorId, ArrayList<Object> parms);
    public boolean ExecSQLNoResult(String sqlId, ArrayList<Object> parms);
}
