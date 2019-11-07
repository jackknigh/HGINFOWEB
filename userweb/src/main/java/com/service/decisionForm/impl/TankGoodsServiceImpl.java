package com.service.decisionForm.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.service.decisionForm.TankGoodsService;
import com.service.syscomp.SqlCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TankGoodsServiceImpl implements TankGoodsService {
    @Autowired
    SqlCommand sqlCommand;

    @Override
    public List tankGoods() {
        List<Map> list = sqlMethod();
        return list;
    }


    /**
     * 调用sqlCommand
     * @return
     */
    public List sqlMethod() {
        ArrayList<Object> parms = new ArrayList<Object>();
        parms.add("Ourway");
        parms.add(1);
        parms.add("");
        parms.add(1);
        parms.add("");
        parms.add(1);
        parms.add("");
        parms.add(1);
        parms.add("");
        parms.add(1);
        parms.add("");
        parms.add(1);
        parms.add("");

        List<Object> listTest = sqlCommand.ExecSQLNoSession("Query_Stat_TankGoods","Ourway","Admin",parms);

        String str= JSON.toJSONString(listTest);
        List<Map> list = JSON.parseObject(str,new TypeReference<List<Map>>(){});
        return list;
    }

    /**
     * 具体的数据处理
     * @param list
     * @return
     */
    public Map mainMethod(List<Map> list) {
        Map map2 = new HashMap();
        for (Map map: list) {
            if (map2.containsKey(map.get("GoodsName"))) {
                map2.put("GoodsNumber",Double.parseDouble(map2.get("GoodsLockNumber").toString()) + Double.parseDouble(map.get("Number").toString()));
                map2.put("GoodsDeductNumber",Double.parseDouble(map2.get("GoodsLockNumber").toString()) + Double.parseDouble(map.get("DeductNumber").toString()));
                map2.put("GoodsCanNumber",Double.parseDouble(map2.get("GoodsLockNumber").toString()) + Double.parseDouble(map.get("CanNumber").toString()));
                map2.put("GoodsLockNumber",Double.parseDouble(map2.get("GoodsLockNumber").toString()) + Double.parseDouble(map.get("LockNumber").toString()));
            } else {
                map2.put("GoodsName",map.get("GoodsName"));
                map2.put("GoodsNumber",map.get("Number"));
                map2.put("GoodsDeductNumber",map.get("DeductNumber"));
                map2.put("GoodsCanNumber",map.get("CanNumber"));
                map2.put("GoodsLockNumber",map.get("LockNumber"));
            }
        }
        Map map3 = new HashMap();
        //List<Map> list =
        for (Map map: list) {
            if (map3.containsKey(map.get("TankID"))) {
                map3.put("Number",Double.parseDouble(map3.get("Number").toString()) + Double.parseDouble(map.get("Number").toString()));
            } else {
                map3.put("TankID",map.get("TankID"));
                map3.put("Number",map.get("Number"));
            }
        }


        return null;
    }
}
