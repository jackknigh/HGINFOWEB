package com.service.decisionForm.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.config.HgApplicationProperty;
import com.dao.db1.mock.MockGoodMapper;
import com.dao.entity.mock.MockGood;
import com.service.decisionForm.QueryInOutShipService;
import com.service.syscomp.SqlCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class QueryInOutShipServiceImpl implements QueryInOutShipService {


    @Autowired
    SqlCommand sqlCommand;
    @Autowired
    MockGoodMapper mockGoodMapper;
    @Autowired
    private HgApplicationProperty applicationProperty;

    /**
     * 按照GoodsName查询
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List queryInOutShipByGoodsName(String beginTime, String endTime) {
        List<Map> list = sqlMethod(beginTime, endTime);
        Map<String, Map> map = mainMethod(list, "GoodsName");
        List<Map<String, Object>> listL = methodF(map, "GoodsName");
        return listL;
    }

    /**
     * 按照Batch查询
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List queryInOutShipByBatch(String beginTime, String endTime) {
        List<Map> list = sqlMethod(beginTime, endTime);
        Map<String, Map> map = mainMethod(list, "Batch");
        List<Map> listL = method(map);
        return listL;
    }

    /**
     * 按照IsSafety查询
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List queryInOutShipByIsSafety(String beginTime, String endTime) {
        List<Map> list = sqlMethod(beginTime, endTime);
        Map<String, Map> map = mainMethod(list, "IsSafety");
        List<Map> listL = method(map);
        return listL;
    }

    /**
     * 基础数据
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List queryInOutShipBasics(String beginTime, String endTime) {
        List<Map> list = sqlMethod(beginTime, endTime);
        List<Map> listM = new ArrayList<>();
        List<MockGood> listGood = mockGoodMapper.selectAll();
        Map map = new HashMap();
        for (Map mapL : list) {
            map.put("BerthDate", mapL.get("BerthDate"));
            map.put("BLNumber", new BigDecimal(Double.parseDouble(mapL.get("BLNumber").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map.put("ShipNetWeight", new BigDecimal(Double.parseDouble(mapL.get("ShipNetWeight").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            if (mapL.containsKey("Batch")) {
                map.put("Batch", mapL.get("Batch"));
            } else {
                map.put("Batch", "无");
            }
            map.put("MainOperateID", mapL.get("MainOperateID"));
            map.put("MainOrderID", mapL.get("MainOrderID"));
            map.put("BerthID", mapL.get("BerthID"));
            map.put("ShipName", mapL.get("ShipName"));
            // map.put("GoodsName",mapL.get("GoodsName"));
            map.put("IsSafety", mapL.get("IsSafety"));
            if (Integer.valueOf(applicationProperty.getChinOrEng().toString()) == 1) {
                map.put("VGuestName", mapL.get("VGuestName").toString().substring(0, 3) + "****" + mapL.get("VGuestName").toString().substring(mapL.get("VGuestName").toString().length() - 4));
                if (mapL.get("ShipFactor").toString().length() > 4) {
                    map.put("ShipFactor", mapL.get("ShipFactor").toString().substring(0, 3) + "****" + mapL.get("ShipFactor").toString().substring(mapL.get("ShipFactor").toString().length() - 4));
                } else {
                    map.put("ShipFactor", mapL.get("ShipFactor").toString().substring(0, 2) + "****" + mapL.get("ShipFactor").toString().substring(mapL.get("ShipFactor").toString().length() - 2));
                }
                if (mapL.containsKey("GoodsFactor")) {
                    if (mapL.get("GoodsFactor").toString().length() > 4) {
                        map.put("GoodsFactor", mapL.get("GoodsFactor").toString().substring(0, 2) + "****" + mapL.get("GoodsFactor").toString().substring(mapL.get("GoodsFactor").toString().length() - 2));
                    } else {
                        map.put("GoodsFactor", mapL.get("GoodsFactor").toString().substring(0, 2) + "****" + mapL.get("GoodsFactor").toString().substring(mapL.get("GoodsFactor").toString().length() - 1));
                    }
                }

                for (MockGood good : listGood) {
                    if (good.getName().equals(mapL.get("GoodsName").toString())) {
                        map.put("GoodsName", good.getEngname());
                    }
                }

            } else {
                map.put("VGuestName", mapL.get("VGuestName"));
                map.put("ShipFactor", mapL.get("ShipFactor"));
                map.put("GoodsName", mapL.get("GoodsName"));
                map.put("GoodsFactor", mapL.get("GoodsFactor"));
            }
            map.put("ServiceName", mapL.get("ServiceName"));
            map.put("BLNO", mapL.get("BLNO"));
            map.put("ATA", mapL.get("ATA"));
            map.put("BerthTime", mapL.get("BerthTime"));
            map.put("maxBitt", new BigDecimal(Double.parseDouble(mapL.get("maxBitt").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map.put("procedureTime", mapL.get("procedureTime"));
            map.put("MeetTubeTime", mapL.get("MeetTubeTime"));
            map.put("OpenPumpTime", mapL.get("OpenPumpTime"));
            map.put("ClosePumpTime", mapL.get("ClosePumpTime"));
            map.put("RemoveTubeTime", mapL.get("RemoveTubeTime"));
            map.put("LeaveTime", mapL.get("LeaveTime"));
            if (mapL.containsKey("BerthTimeToLeaveTime")) {
                map.put("BerthTimeToLeaveTime", new BigDecimal(Double.parseDouble(mapL.get("BerthTimeToLeaveTime").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            } else {
                map.put("BerthTimeToLeaveTime", 0);
            }

            // map.put("GoodsFactor",mapL.get("GoodsFactor"));
            map.put("ShipCountry", mapL.get("ShipCountry"));
            listM.add(JSON.parseObject(JSON.toJSONString(map), new TypeReference<Map>() {
            }));
            map.clear();
        }
        return listM;
    }

    /**
     * 按照BerthID查询
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List queryInOutShipByBerthID(String beginTime, String endTime) {
        List<Map> list = sqlMethod(beginTime, endTime);
        Map<String, Map> map = mainMethod(list, "BerthID");
        List<Map<String, Object>> listL = methodF(map, "BerthID");
        return listL;
    }

    /**
     * Goodsname BerthID 通用方法
     *
     * @param map
     * @return
     */
    public List methodF(Map<String, Map> map, String strL) {
        Map mapL = new HashMap();
        List<MockGood> listGood = mockGoodMapper.selectAll();
        List<Map<String, Object>> listL = new ArrayList<>();
        for (String str : map.keySet()) {
            mapL.put(strL, str);
            mapL.put("AvgShipNetWeight", new BigDecimal(Double.parseDouble(map.get(str).get("AvgShipNetWeight").toString()) / Double.parseDouble(map.get(str).get("Sum").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            mapL.put("AvgBLNumber", new BigDecimal(Double.parseDouble(map.get(str).get("AvgBLNumber").toString()) / Double.parseDouble(map.get(str).get("Sum").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            mapL.put("sum", new BigDecimal(Double.parseDouble(map.get(str).get("Sum").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            if (Integer.valueOf(applicationProperty.getChinOrEng().toString()) == 1) {
                for (MockGood good : listGood) {
                    if (good.getName().equals(map.get(str).get("GoodsName").toString())) {
                        mapL.put("GoodsName", good.getEngname());
                    }
                }
            }
            listL.add(JSON.parseObject(JSON.toJSONString(mapL), new TypeReference<Map>() {
            }));
            mapL.clear();
        }
        Collections.sort(listL, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get("AvgShipNetWeight").toString());//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get("AvgShipNetWeight").toString()); //name1是从你list里面拿出来的第二个name
                return name1.compareTo(name2);
            }
        });
        return listL;
    }


    /**
     * IsSafety Batch 通用方法
     *
     * @param map
     * @return
     */
    public List method(Map<String, Map> map) {
        Map mapL = new HashMap();
        List<Map> listL = new ArrayList<>();
        for (String str : map.keySet()) {
            mapL.put("Batch", str);

            if (Double.parseDouble((map.get(str).get("Sum1")).toString()) > 0) {
                mapL.put("BLNumber1", new BigDecimal(Double.parseDouble(map.get(str).get("BLNumber1").toString()) / Double.parseDouble(map.get(str).get("Sum1").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            } else {
                mapL.put("BLNumber1", new BigDecimal(Double.parseDouble(map.get(str).get("BLNumber1").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            }
            if (Double.parseDouble(map.get(str).get("Sum2").toString()) > 0) {
                mapL.put("BLNumber2", new BigDecimal(Double.parseDouble(map.get(str).get("BLNumber2").toString()) / Double.parseDouble(map.get(str).get("Sum2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            } else {
                mapL.put("BLNumber2", new BigDecimal(Double.parseDouble(map.get(str).get("BLNumber2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            }
            listL.add(JSON.parseObject(JSON.toJSONString(mapL), new TypeReference<Map>() {
            }));
            mapL.clear();
        }

        return listL;
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
        parms.add("ATA");
        parms.add(beginTime);
        parms.add(endTime);

        List<Object> listTest = sqlCommand.ExecSQLNoSession("Query_Stat_QueryInOutShip", "Ourway", "Admin", parms);

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
    public Map mainMethod(List<Map> list, String str) {
        Map<String, Map> map = new HashMap();

        for (Map map1 : list) {
            if (map1.get(str) == null) {
                map1.put(str, "无");
            }
            if (map.containsKey(map1.get(str).toString())) {
                map.get(map1.get(str).toString()).put("Sum", Double.parseDouble(map.get(map1.get(str).toString()).get("Sum").toString()) + 1);
                if (map1.get("ServiceName").toString().equals("装船")) {
                    map.get(map1.get(str).toString()).put("Sum1", Double.parseDouble(map.get(map1.get(str).toString()).get("Sum1").toString()) + 1);
                    map.get(map1.get(str).toString()).put("BLNumber1", Double.parseDouble(map.get(map1.get(str).toString()).get("BLNumber1").toString()) + Double.parseDouble(map1.get("BLNumber").toString()));
                }
                if (map1.get("ServiceName").toString().equals("卸船")) {
                    map.get(map1.get(str).toString()).put("Sum2", Double.parseDouble(map.get(map1.get(str).toString()).get("Sum2").toString()) + 1);
                    map.get(map1.get(str).toString()).put("BLNumber2", Double.parseDouble(map.get(map1.get(str).toString()).get("BLNumber2").toString()) + Double.parseDouble(map1.get("BLNumber").toString()));
                }
                map.get(map1.get(str).toString()).put("AvgShipNetWeight", Double.parseDouble(map.get(map1.get(str).toString()).get("AvgShipNetWeight").toString()) + Double.parseDouble(map1.get("ShipNetWeight").toString()));
                map.get(map1.get(str).toString()).put("AvgBLNumber", Double.parseDouble(map.get(map1.get(str).toString()).get("AvgBLNumber").toString()) + Double.parseDouble(map1.get("BLNumber").toString()));
            } else {
                map.put(map1.get(str).toString(), map1);
                map.get(map1.get(str).toString()).put("Sum", 1);
                map.get(map1.get(str).toString()).put("AvgShipNetWeight", map1.get("ShipNetWeight"));
                map.get(map1.get(str).toString()).put("AvgBLNumber", map1.get("BLNumber"));
                if (map1.get("ServiceName").toString().equals("装船")) {
                    map.get(map1.get(str).toString()).put("BLNumber1", map1.get("BLNumber"));
                    map.get(map1.get(str).toString()).put("Sum1", 1);
                } else {
                    map.get(map1.get(str).toString()).put("BLNumber1", 0);
                    map.get(map1.get(str).toString()).put("Sum1", 0);
                }
                if (map1.get("ServiceName").toString().equals("卸船")) {
                    map.get(map1.get(str).toString()).put("BLNumber2", map1.get("BLNumber"));
                    map.get(map1.get(str).toString()).put("Sum2", 1);
                } else {
                    map.get(map1.get(str).toString()).put("BLNumber2", 0);
                    map.get(map1.get(str).toString()).put("Sum2", 0);
                }
            }
        }

        return map;
    }
}
