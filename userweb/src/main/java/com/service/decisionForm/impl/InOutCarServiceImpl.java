package com.service.decisionForm.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.config.HgApplicationProperty;
import com.dao.db1.mock.MockGoodMapper;
import com.dao.entity.mock.MockGood;
import com.service.decisionForm.InOutCarService;
import com.service.decisionForm.InOutGoodsService;
import com.service.syscomp.SqlCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
public class InOutCarServiceImpl implements InOutCarService {

    @Autowired
    SqlCommand sqlCommand;
    @Autowired
    MockGoodMapper mockGoodMapper;
    @Autowired
    private HgApplicationProperty applicationProperty;

    /*
        基础数据，未修改
     */
    @Override
    public List inOutCarBas(String beginTime, String endTime) {
        List<Map> list = sqlMethod(beginTime, endTime);
        List<Map> listMap = new ArrayList<>();

        List<MockGood> listGood = mockGoodMapper.selectAll();

        Map mapL = new HashMap();
        for (Map map : list) {

            mapL.put("WeightTime1", map.get("WeightTime1"));
            mapL.put("TypeName", map.get("TypeName"));
            mapL.put("CarNo", map.get("CarNo"));
            mapL.put("Number", new BigDecimal(Double.parseDouble(map.get("Number").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            if (Integer.valueOf(applicationProperty.getChinOrEng().toString()) == 1) {
                mapL.put("VGuestName", map.get("VGuestName").toString().substring(0, 3) + "****" + map.get("VGuestName").toString().substring(map.get("VGuestName").toString().length() - 4));
                mapL.put("PersonName", map.get("PersonName").toString().substring(0, 1) + "**");
                for (MockGood good : listGood) {
                    if (good.getName().equals(map.get("GoodsName").toString())) {
                        mapL.put("GoodsName", good.getEngname());
                    }
                }
            } else {
                mapL.put("VGuestName", map.get("VGuestName"));
                mapL.put("GoodsName", map.get("GoodsName"));
                mapL.put("PersonName", map.get("PersonName"));
            }
            mapL.put("StateName", map.get("StateName"));
            mapL.put("BLNo", map.get("BLNo"));
            mapL.put("MainOrderID", map.get("MainOrderID"));
            mapL.put("CranePlace", map.get("CranePlace"));
            mapL.put("InOutCarID", map.get("InOutCarID"));
            mapL.put("IndateTime", new BigDecimal(Double.parseDouble(map.get("IndateTime").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            mapL.put("OPdateTime", new BigDecimal(Double.parseDouble(map.get("OPdateTime").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            mapL.put("WaitDateTime", new BigDecimal(Double.parseDouble(map.get("WaitDateTime").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            mapL.put("OutDateTime", new BigDecimal(Double.parseDouble(map.get("OutDateTime").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            mapL.put("W1W2", new BigDecimal(Double.parseDouble(map.get("W1W2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            mapL.put("CenterTime", new BigDecimal(Double.parseDouble(map.get("CenterTime").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            mapL.put("AllTime", new BigDecimal(Double.parseDouble(map.get("AllTime").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            mapL.put("KPI", new BigDecimal(Double.parseDouble(map.get("KPI").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            mapL.put("MainOrderID", map.get("MainOrderID"));
            mapL.put("GuardDate", map.get("GuardDate"));
            mapL.put("TankID", map.get("TankID"));
            mapL.put("OpenPumpTime", map.get("OpenPumpTime"));
            mapL.put("ClosePumpTime", map.get("ClosePumpTime"));
            mapL.put("WeightTime2", map.get("WeightTime2"));
            mapL.put("OutDate", map.get("OutDate"));

            listMap.add(JSON.parseObject(JSON.toJSONString(mapL), new TypeReference<Map>() {
            }));
            mapL.clear();
        }

        return listMap;
    }

    /*
       根据客户名查询，并根据Number排序
     */
    @Override
    public List inOutCarSort(String beginTime, String endTime) {
        List<Map> list = sqlMethod(beginTime, endTime);
        Map<String, Map> map = mainMethod(list);
        List<MockGood> listGood = mockGoodMapper.selectAll();
        Map mapL = new HashMap();
        List<Map<String, Object>> listM = new ArrayList<>();
        /*
            数据处理
         */
        for (String str : map.keySet()) {
            if (Integer.valueOf(applicationProperty.getChinOrEng().toString()) == 1) {
                for (MockGood good : listGood) {
                    if (good.getName().equals(str.toString())) {
                        mapL.put("GoodsName", good.getEngname());
                    }
                }
            } else {
                mapL.put("GoodsName", str);
            }

            mapL.put("sum", Double.parseDouble(map.get(str).get("Sum1").toString()) + Double.parseDouble(map.get(str).get("Sum2").toString()));
            if (Double.parseDouble(map.get(str).get("Sum1").toString()) > 0 && Double.parseDouble(map.get(str).get("Sum2").toString()) > 0) {
                mapL.put("NumSum", (Double.parseDouble(map.get(str).get("Number1").toString()) / Double.parseDouble(map.get(str).get("Sum1").toString())) + Double.parseDouble(map.get(str).get("Number2").toString()) / Double.parseDouble(map.get(str).get("Sum2").toString()));
            } else if (Double.parseDouble(map.get(str).get("Sum1").toString()) > 0 && Double.parseDouble(map.get(str).get("Sum2").toString()) <= 0) {
                mapL.put("NumSum", (Double.parseDouble(map.get(str).get("Number1").toString()) / Double.parseDouble(map.get(str).get("Sum1").toString())));
            } else if (Double.parseDouble(map.get(str).get("Sum1").toString()) <= 0 && Double.parseDouble(map.get(str).get("Sum2").toString()) > 0) {
                mapL.put("NumSum", (Double.parseDouble(map.get(str).get("Number2").toString()) / Double.parseDouble(map.get(str).get("Sum2").toString())));
            }
            if (Double.parseDouble(map.get(str).get("Sum1").toString()) > 0) {
                mapL.put("AllTime1", (Double.parseDouble(map.get(str).get("AllTime1").toString()) / Double.parseDouble(map.get(str).get("Sum1").toString())));
                mapL.put("Number1", Double.parseDouble(map.get(str).get("Number1").toString()) / Double.parseDouble(map.get(str).get("Sum1").toString()));
            } else {
                mapL.put("AllTime1", (Double.parseDouble(map.get(str).get("AllTime1").toString())));
                mapL.put("Number1", Double.parseDouble(map.get(str).get("Number1").toString()));
            }
            if (Double.parseDouble(map.get(str).get("Sum2").toString()) > 0) {
                mapL.put("AllTime2", Double.parseDouble(map.get(str).get("AllTime2").toString()) / Double.parseDouble(map.get(str).get("Sum2").toString()));
                mapL.put("Number2", Double.parseDouble(map.get(str).get("Number2").toString()) / Double.parseDouble(map.get(str).get("Sum2").toString()));
            } else {
                mapL.put("AllTime2", (Double.parseDouble(map.get(str).get("AllTime2").toString())));
                mapL.put("Number2", Double.parseDouble(map.get(str).get("Number2").toString()));
            }


            listM.add(JSON.parseObject(JSON.toJSONString(mapL), new TypeReference<Map>() {
            }));
            mapL.clear();
        }

        for (Map map2 : listM) {
            if (map2.get("AllTime1") == null) {
                map2.put("AllTime1", 0);
            } else {
                map2.put("AllTime1", new BigDecimal(Double.parseDouble(map2.get("AllTime1").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            }
            if (map2.get("Number1") == null) {
                map2.put("Number1", 0);
            } else {
                map2.put("Number1", new BigDecimal(Double.parseDouble(map2.get("Number1").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            }
            if (map2.get("AllTime2") == null) {
                map2.put("AllTime2", 0);
            } else {
                map2.put("AllTime2", new BigDecimal(Double.parseDouble(map2.get("AllTime2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            }
            if (map2.get("Number2") == null) {
                map2.put("Number2", 0);
            } else {
                map2.put("Number2", new BigDecimal(Double.parseDouble(map2.get("Number2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            }
            map2.put("sum", new BigDecimal(Double.parseDouble(map2.get("sum").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));

        }
        /*
            根据Number之和排序
         */
        Collections.sort(listM, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get("NumSum").toString());//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get("NumSum").toString()); //name1是从你list里面拿出来的第二个name
                return (name1.compareTo(name2));
            }
        });
        return listM;
    }

    /*
        根据客户名查询，并根据KPI排序
     */
    @Override
    public List inOutCarGK(String beginTime, String endTime) {
        List<Map> list = sqlMethod(beginTime, endTime);
        Map<String, Map> map = mainMethod(list);
        List<MockGood> listGood = mockGoodMapper.selectAll();
        Map mapL = new HashMap();
        List<Map<String, Object>> listM = new ArrayList<>();

        /*
            数据处理
         */
        for (String str : map.keySet()) {
            mapL.put("GoodsName", str);
            mapL.put("sum", new BigDecimal(Double.parseDouble(map.get(str).get("Sum1").toString()) + Double.parseDouble(map.get(str).get("Sum2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            if (Double.parseDouble(map.get(str).get("Sum1").toString()) != 0) {
                mapL.put("KPI1", new BigDecimal(Double.parseDouble(map.get(str).get("KPI1").toString()) / Double.parseDouble(map.get(str).get("Sum1").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
                mapL.put("smallNumber1", new BigDecimal(Double.parseDouble(map.get(str).get("smallNumber1").toString()) / Double.parseDouble(map.get(str).get("Sum1").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            } else {
                mapL.put("KPI1", map.get(str).get("KPI1"));
                mapL.put("smallNumber1", map.get(str).get("smallNumber1"));
            }
            if (Double.parseDouble(map.get(str).get("Sum2").toString()) != 0) {
                mapL.put("KPI2", new BigDecimal(Double.parseDouble(map.get(str).get("KPI2").toString()) / Double.parseDouble(map.get(str).get("Sum2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
                mapL.put("smallNumber2", new BigDecimal(Double.parseDouble(map.get(str).get("smallNumber2").toString()) / Double.parseDouble(map.get(str).get("Sum2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            } else {
                mapL.put("KPI2", map.get(str).get("KPI2"));
                mapL.put("smallNumber2", map.get(str).get("smallNumber2"));
            }
            mapL.put("SumKPI", Double.parseDouble(mapL.get("KPI1").toString()) + Double.parseDouble(mapL.get("KPI2").toString()));
            if (Integer.valueOf(applicationProperty.getChinOrEng().toString()) == 1) {
                for (MockGood good : listGood) {
                    if (good.getName().equals(map.get(str).get("GoodsName").toString())) {
                        mapL.put("GoodsName", good.getEngname());
                    }
                }
            }
            listM.add(JSON.parseObject(JSON.toJSONString(mapL), new TypeReference<Map>() {
            }));
            mapL.clear();
        }
        /*
            根据KPI之和排序
         */
        Collections.sort(listM, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get("SumKPI").toString());//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get("SumKPI").toString()); //name1是从你list里面拿出来的第二个name
                return (name1.compareTo(name2));
            }
        });
        return listM;
    }

    /**
     * 调用sqlCommand
     * @param beginTime
     * @param endTime
     * @return
     */
    public List sqlMethod(String beginTime, String endTime) {
        ArrayList<Object> parms = new ArrayList<Object>();
        /*
            查询条件
         */
        parms.add("Ourway");
        parms.add("");
        parms.add("");
        parms.add("");
        parms.add("");
        parms.add("WeightTime1");
        parms.add(beginTime);
        parms.add(endTime);

        List<Object> listTest = sqlCommand.ExecSQLNoSession("Query_Stat_InOutCar", "Ourway", "Admin", parms);
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

        for (Map map1 : list) {
            if (map.containsKey(map1.get("GoodsName"))) {
                if (((String) map1.get("TypeName")).equals("装汽车")) {
                    map.get((String) map1.get("GoodsName")).put("AllTime1", Double.parseDouble(map.get((String) map1.get("GoodsName")).get("AllTime1").toString()) + Double.parseDouble(map1.get("AllTime").toString()));
                    map.get((String) map1.get("GoodsName")).put("Number1", Double.parseDouble(map.get((String) map1.get("GoodsName")).get("Number1").toString()) + Double.parseDouble(map1.get("Number").toString()));
                    map.get((String) map1.get("GoodsName")).put("Sum1", Double.parseDouble(map.get((String) map1.get("GoodsName")).get("Sum1").toString()) + 1);
                    map.get((String) map1.get("GoodsName")).put("KPI1", Double.parseDouble(map.get((String) map1.get("GoodsName")).get("KPI1").toString()) + Double.parseDouble(map1.get("KPI").toString()));
                    map.get((String) map1.get("GoodsName")).put("smallNumber1", Double.parseDouble(map.get((String) map1.get("GoodsName")).get("smallNumber1").toString()) + Double.parseDouble(map1.get("smallNumber").toString()));
                }
                if (((String) map1.get("TypeName")).equals("卸汽车")) {
                    map.get((String) map1.get("GoodsName")).put("AllTime2", Double.parseDouble(map.get((String) map1.get("GoodsName")).get("AllTime2").toString()) + Double.parseDouble(map1.get("AllTime").toString()));
                    map.get((String) map1.get("GoodsName")).put("Number2", Double.parseDouble(map.get((String) map1.get("GoodsName")).get("Number2").toString()) + Double.parseDouble(map1.get("Number").toString()));
                    map.get((String) map1.get("GoodsName")).put("Sum2", Double.parseDouble(map.get((String) map1.get("GoodsName")).get("Sum2").toString()) + 1);
                    map.get((String) map1.get("GoodsName")).put("KPI2", Double.parseDouble(map.get((String) map1.get("GoodsName")).get("KPI2").toString()) + Double.parseDouble(map1.get("KPI").toString()));
                    map.get((String) map1.get("GoodsName")).put("smallNumber2", Double.parseDouble(map.get((String) map1.get("GoodsName")).get("smallNumber2").toString()) + Double.parseDouble(map1.get("smallNumber").toString()));
                }
            } else {
                map.put((String) map1.get("GoodsName"), map1);
                if (((String) map1.get("TypeName")).equals("装汽车")) {
                    map.get((String) map1.get("GoodsName")).put("AllTime1", map1.get("AllTime"));
                    map.get((String) map1.get("GoodsName")).put("Number1", map1.get("Number"));
                    map.get((String) map1.get("GoodsName")).put("KPI1", map1.get("KPI"));
                    map.get((String) map1.get("GoodsName")).put("smallNumber1", map1.get("smallNumber"));
                    map.get((String) map1.get("GoodsName")).put("Sum1", 1);
                } else {
                    map.get((String) map1.get("GoodsName")).put("AllTime1", 0);
                    map.get((String) map1.get("GoodsName")).put("Number1", 0);
                    map.get((String) map1.get("GoodsName")).put("KPI1", 0);
                    map.get((String) map1.get("GoodsName")).put("smallNumber1", 0);
                    map.get((String) map1.get("GoodsName")).put("Sum1", 0);
                }
                if (((String) map1.get("TypeName")).equals("卸汽车")) {
                    map.get((String) map1.get("GoodsName")).put("AllTime2", map1.get("AllTime"));
                    map.get((String) map1.get("GoodsName")).put("Number2", map1.get("Number"));
                    map.get((String) map1.get("GoodsName")).put("KPI2", map1.get("KPI"));
                    map.get((String) map1.get("GoodsName")).put("smallNumber2", map1.get("smallNumber"));
                    map.get((String) map1.get("GoodsName")).put("Sum2", 1);

                } else {
                    map.get((String) map1.get("GoodsName")).put("AllTime2", 0);
                    map.get((String) map1.get("GoodsName")).put("Number2", 0);
                    map.get((String) map1.get("GoodsName")).put("KPI2", 0);
                    map.get((String) map1.get("GoodsName")).put("smallNumber2", 0);
                    map.get((String) map1.get("GoodsName")).put("Sum2", 0);
                }
            }
        }

        return map;
    }

}
