package com.service.decisionForm.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.config.HgApplicationProperty;
import com.service.decisionForm.InOutGoodsService;
import com.service.syscomp.SqlCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InOutGoodsServiceImpl implements InOutGoodsService {
    @Autowired
    SqlCommand sqlCommand;
    @Autowired
    private HgApplicationProperty applicationProperty;

    @Override
    public Map inOutGoods(String beginTime, String endTime) {


        List<Map<String, Object>> list = sqlMethod(beginTime, endTime);
        //DecimalFormat df = new DecimalFormat( "0.000");
        Map mapS1 = new HashMap();
       /* Map mapS2 = new HashMap();
        Map mapS3 = new HashMap();*/
        List<Map<String, Object>> list1 = new ArrayList<>();
        List<Map<String, Object>> list2 = new ArrayList<>();
        List<Map<String, Object>> list3 = new ArrayList<>();
        //List<Map<String,Object>> list2 = list;
        /*
        具体的数据处理
         */
        for (Map mapList : list) {
            mapS1.put("produceName", mapList.get("GoodsName").toString());
            mapS1.put("startNumber", Double.parseDouble(mapList.get("NumQCHJ").toString()));
            mapS1.put("endNumber", Double.parseDouble(mapList.get("NumQMHJ").toString()));
            mapS1.put("outNumber", Double.parseDouble(mapList.get("NumOutHJ").toString()));
            mapS1.put("inNumber", Double.parseDouble(mapList.get("NumInHJ").toString()));
            mapS1.put("inInside", Double.parseDouble(mapList.get("NumIn2").toString()));
            mapS1.put("inOutside", Double.parseDouble(mapList.get("NumIn1").toString()));
            mapS1.put("outInside", Double.parseDouble(mapList.get("NumOut2").toString()));
            mapS1.put("outOutside", Double.parseDouble(mapList.get("NumOut1").toString()));
            mapS1.put("startInside", Double.parseDouble(mapList.get("NumQC2").toString()));
            mapS1.put("startOutside", Double.parseDouble(mapList.get("NumQC1").toString()));
            mapS1.put("endInside", Double.parseDouble(mapList.get("NumQM2").toString()));
            mapS1.put("endOutside", Double.parseDouble(mapList.get("NumQM1").toString()));
            mapS1.put("CarNumOut1", Double.parseDouble(mapList.get("CarNumOut1").toString()));
            mapS1.put("CarNumOut2", Double.parseDouble(mapList.get("CarNumOut2").toString()));
            mapS1.put("CarNumOutHJ", Double.parseDouble(mapList.get("CarNumOutHJ").toString()));
            list1.add(JSON.parseObject(JSON.toJSONString(mapS1), new TypeReference<Map>() {
            }));
            mapS1.clear();
            mapS1.put("produceName", mapList.get("GoodsName").toString());
            mapS1.put("startNumber", Double.parseDouble(mapList.get("NumQCHJ").toString()));
            list2.add(JSON.parseObject(JSON.toJSONString(mapS1), new TypeReference<Map>() {
            }));
            mapS1.clear();
            mapS1.put("produceName", mapList.get("GoodsName").toString());
            mapS1.put("endNumber", Double.parseDouble(mapList.get("NumQMHJ").toString()));
            list3.add(JSON.parseObject(JSON.toJSONString(mapS1), new TypeReference<Map>() {
            }));
            mapS1.clear();
        }
        //System.out.println("123"+list2.toString());
        Map<String, List> map = new HashMap<>();
        map.put("main", JSON.parseObject(JSON.toJSONString(list1), new TypeReference<List<Map>>() {
        }));
         /*
        按照startNumber降序排序
         */
        Collections.sort(list2, new Comparator<Map<String, Object>>() {//JSON.parseObject(JSON.toJSONString(list2),new TypeReference<List<Map<String,Object>>>(){}
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get("startNumber").toString());//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get("startNumber").toString()); //name1是从你list里面拿出来的第二个name
                return -(name1.compareTo(name2));
            }
        });
       /* for(int i = 0;i<list2.size();i++) {
            System.out.println(list.get(i).get("startNumber"));
        }*/
        map.put("allStartNumberSort", JSON.parseObject(JSON.toJSONString(list2), new TypeReference<List<Map<String, Object>>>() {
        }));
        /*
        按照endNumber降序排序
         */
        Collections.sort(list3, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get("endNumber").toString());//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get("endNumber").toString()); //name1是从你list里面拿出来的第二个name
                return -(name1.compareTo(name2));
            }
        });
        map.put("allEndNumberSort", JSON.parseObject(JSON.toJSONString(list3), new TypeReference<List<Map<String, Object>>>() {
        }));

        return map;
    }

    /**
     * 调用sqlCommand
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public List sqlMethod(String beginTime, String endTime) {
        ArrayList<Object> parms = new ArrayList<Object>();
        parms.add("Ourway");
        parms.add("");
        parms.add(beginTime);
        parms.add(endTime);

        List<Object> listTest = sqlCommand.ExecSQLNoSession("Query_Stat_InOutGoods", "Ourway", "Admin", parms);
        String str = JSON.toJSONString(listTest);
        List<Map> list = JSON.parseObject(str, new TypeReference<List<Map>>() {
        });
        return list;
    }

}
