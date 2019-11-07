package com.service.decisionForm.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.service.decisionForm.DiffTankGoodsService;
import com.service.syscomp.SqlCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiffTankGoodsServiceImpl implements DiffTankGoodsService {

    @Autowired
    SqlCommand sqlCommand;

    @Override
    public List diffTankGoods(String month) {
        List<Map> list = sqlMethod(month);
        return list;
    }

    /**
     * 调用sqlCommand
     * @param month
     * @return
     */
    public List sqlMethod(String month) {
        ArrayList<Object> parms = new ArrayList<Object>();
        parms.add("Ourway");
        parms.add(month);
        parms.add("");
        parms.add("");
        parms.add("");
        parms.add(1);
        List<Object> listTest = sqlCommand.ExecSQLNoSession("Query_Stat_DiffTankGoods","Ourway","Admin",parms);
        String str= JSON.toJSONString(listTest);
        List<Map> list = JSON.parseObject(str,new TypeReference<List<Map>>(){});
        return list;
    }

    /**
     * 具体的数据处理
     * @return
     */
    public Map mainMethod(List<Map> list) {
        Map<String,Map> map = new HashMap();
        List listM = new ArrayList();
        for (Map map1: list) {
            if (map.containsKey(map1.get("GoodsName").toString())) {

            } else {
                map.put(map1.get("GoodsName").toString(),map1);
                map.get(map1.get("GoodsName").toString()).put("SumNumber",Double.parseDouble(map1.get("Number").toString()));
                map.get(map1.get("GoodsName").toString()).put("SumTotalqty",Double.parseDouble(map1.get("Totalqty").toString()));
                map.get(map1.get("GoodsName").toString()).put("SumDiffTNum",Double.parseDouble(map1.get("DiffTNum").toString()));
                map.get(map1.get("GoodsName").toString()).put("SumPhysicalStock",Double.parseDouble(map1.get("PhysicalStock").toString()));
                map.get(map1.get("GoodsName").toString()).put("GuestList",listM);
            }
        }
        return map;
    }
}
