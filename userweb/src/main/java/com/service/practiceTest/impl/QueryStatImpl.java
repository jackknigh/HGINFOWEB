package com.service.practiceTest.impl;

import com.service.practiceTest.QueryStatService;
import com.service.syscomp.SqlCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueryStatImpl implements QueryStatService {

    @Autowired
    SqlCommand sqlCommand;

    /**
     * 收入汇总
     * @return
     */
    public List<Object> receivableTest(){
        ArrayList<Object> parms = new ArrayList<Object>();
        parms.add("Ourway");
        parms.add("");
        parms.add("");
        parms.add("2019-01-23");
        parms.add("2019-04-23");
        List<Object> listTest = sqlCommand.ExecSQLNoSession("Query_Stat_Receivables","Ourway","Admin",parms);
        return listTest;
    }

    /**
     * 吞吐量汇总
     * @return
     */
    public List<Object> inOutNumberTest(){
        ArrayList<Object> parms = new ArrayList<Object>();
        parms.add("Ourway");
        parms.add("0");
        parms.add("2019-01-01 00:00:01");
        parms.add("2019-04-23 23:59:59");
        parms.add("0");
        List<Object> listTest = sqlCommand.ExecSQLNoSession("Query_Stat_InOutNumber","Ourway","Admin",parms);
        return listTest;
    }

    /**
     * 储罐存货数量统计
     * @return
     */
    public List<Object> tankNumberTest(){
        ArrayList<Object> parms = new ArrayList<Object>();
        parms.add("Ourway");
        parms.add("Admin");
        parms.add("");
        List<Object> listTest = sqlCommand.ExecSQLNoSession("Query_Stat_TankNumber","Ourway","Admin",parms);
        return listTest;
    }
}
