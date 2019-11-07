package com.service.decisionForm.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.config.HgApplicationProperty;
import com.dao.db1.mock.MockGoodMapper;
import com.dao.entity.mock.MockGood;
import com.service.decisionForm.StatInOutService;
import com.service.syscomp.SqlCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class StatInOutServiceImpl implements StatInOutService {

    @Autowired
    SqlCommand sqlCommand;
    @Autowired
    MockGoodMapper mockGoodMapper;
    @Autowired
    private HgApplicationProperty applicationProperty;

    /**
     * 按照产品求平均值，并排序
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List statInOutSort(String beginTime, String endTime) {
        List<MockGood> listGood = mockGoodMapper.selectAll();
        List<Map> list = sqlMethod(beginTime, endTime);
        Map<String, Map> map = mainMethod(list);
        List<Map<String, Object>> list2 = JSON.parseObject(JSON.toJSONString(mapMethod(map).values()), new TypeReference<List<Map<String, Object>>>() {
        });
        if (Integer.valueOf(applicationProperty.getChinOrEng().toString()) == 1) {
            for (Map map1 : list2) {
                for (MockGood good : listGood) {
                    if (good.getName().equals(map1.get("GoodsName").toString())) {
                        map1.put("GoodsName", good.getEngname());
                    }
                }
            }
        }
        Collections.sort(list2, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get("NetWeight").toString());//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get("NetWeight").toString()); //name1是从你list里面拿出来的第二个name
                return -(name1.compareTo(name2));
            }
        });
        return list2;
    }

    /**
     * 基础数据，未经处理
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List statInOutBasics(String beginTime, String endTime) {
        List<Map> list = sqlMethod(beginTime, endTime);
        List<Map<String, Object>> list2 = new ArrayList<>();
        List<MockGood> listGood = mockGoodMapper.selectAll();
        Map mapList = new HashMap();
        for (Map map : list) {
            if (map.get("TankID").toString().equals("小计") || map.get("TankID").toString().equals("累计") || map.get("TankID").toString().equals("总计")) {
                continue;
            } else {
                //mapList.put("GoodsName",map.get("GoodsName"));
                mapList.put("BeginDate", map.get("BeginDate"));
                mapList.put("TankID", map.get("TankID"));
                if (Integer.valueOf(applicationProperty.getChinOrEng().toString()) == 1) {
                    mapList.put("VGuestName", map.get("VGuestName").toString().substring(0, 3) + "****" + map.get("VGuestName").toString().substring(map.get("VGuestName").toString().length() - 4));
                    for (MockGood good : listGood) {
                        if (good.getName().equals(map.get("GoodsName").toString())) {
                            mapList.put("GoodsName", good.getEngname());
                        }
                    }
                } else {
                    mapList.put("VGuestName", map.get("VGuestName"));
                    mapList.put("GoodsName", map.get("GoodsName"));
                }

                mapList.put("CarNo", map.get("CarNo"));
                mapList.put("Weight1", new BigDecimal(Double.parseDouble(map.get("Weight1").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
                mapList.put("Weight2", new BigDecimal(Double.parseDouble(map.get("Weight2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
                mapList.put("SendNumber", new BigDecimal(Double.parseDouble(map.get("SendNumber").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
                mapList.put("OutCarID", map.get("OutCarID"));
                mapList.put("NetWeight", new BigDecimal(Double.parseDouble(map.get("NetWeight").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
                mapList.put("BLNO", map.get("BLNO"));
                mapList.put("EndDate", map.get("EndDate"));
                mapList.put("OutDateTime", map.get("OutDateTime"));
                mapList.put("InGateDate", map.get("InGateDate"));
                mapList.put("OutGateDate", map.get("OutGateDate"));
                mapList.put("IsOutTrade", map.get("IsOutTrade"));

                list2.add(JSON.parseObject(JSON.toJSONString(mapList), new TypeReference<Map>() {
                }));
                mapList.clear();
            }
        }
        return list2;
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
        parms.add("");
        parms.add("");
        parms.add("");
        parms.add("");
        parms.add("");
        parms.add(beginTime);
        parms.add(endTime);

        List<Object> listTest = sqlCommand.ExecSQLNoSession("Query_Stat_InOut", "Ourway", "Admin", parms);

        String str = JSON.toJSONString(listTest);
        List<Map> list = JSON.parseObject(str, new TypeReference<List<Map>>() {
        });
        return list;
    }

    /**
     * 具体的数据处理
     *
     * @param list
     * @return
     */
    public Map mainMethod(List<Map> list) {
        Map<String, Map> map = new HashMap();

        for (Map map2 : list) {
            if (((String) map2.get("TankID")).equals("小计") || ((String) map2.get("TankID")).equals("累计") || ((String) map2.get("TankID")).equals("总计")) {
                continue;
            }
            if (map.containsKey(map2.get("GoodsName"))) {
                map.get((String) map2.get("GoodsName")).put("NetWeight", Double.parseDouble(map.get(map2.get("GoodsName")).get("NetWeight").toString()) + Double.parseDouble(map2.get("NetWeight").toString()));
                map.get((String) map2.get("GoodsName")).put("Sum", Double.parseDouble(map.get((String) map2.get("GoodsName")).get("Sum").toString()) + 1);
                map.get((String) map2.get("GoodsName")).put("SendNumber", Double.parseDouble(map.get(map2.get("GoodsName")).get("SendNumber").toString()) + Double.parseDouble(map2.get("SendNumber").toString()));
                map.get((String) map2.get("GoodsName")).put("OutDateTime", Double.parseDouble(map.get(map2.get("GoodsName")).get("OutDateTime").toString()) + Double.parseDouble(map2.get("OutDateTime").toString()));

            } else {
                map.put((String) map2.get("GoodsName"), map2);
                map.get((String) map2.get("GoodsName")).put("Sum", 1);
            }
        }
        return map;
    }

    /**
     * 数据二次处理，以及格式的规整化
     *
     * @param map
     * @return
     */
    public Map mapMethod(Map<String, Map> map) {
        Map<String, Map> mapList = new HashMap<>();
        Map map2 = new HashMap();
        for (String strKey : map.keySet()) {
            map2.put("GoodsName", strKey);
            map2.put("Weight", new BigDecimal((Double.parseDouble(map.get(strKey).get("SendNumber").toString()) - Double.parseDouble(map.get(strKey).get("NetWeight").toString())) / Double.parseDouble(map.get(strKey).get("Sum").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map2.put("OutDateTime", new BigDecimal(Double.parseDouble(map.get(strKey).get("OutDateTime").toString()) / Double.parseDouble(map.get(strKey).get("Sum").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map2.put("NetWeight", new BigDecimal(Double.parseDouble(map.get(strKey).get("NetWeight").toString()) / Double.valueOf(map.get(strKey).get("Sum").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map2.put("sum", new BigDecimal(Double.parseDouble(map.get(strKey).get("Sum").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            mapList.put(strKey, JSON.parseObject(JSON.toJSONString(map2), new TypeReference<Map>() {
            }));
            map2.clear();
        }
        return mapList;
    }


}
