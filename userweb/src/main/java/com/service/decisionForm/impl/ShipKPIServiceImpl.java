package com.service.decisionForm.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.config.HgApplicationProperty;
import com.dao.db1.mock.MockGoodMapper;
import com.dao.entity.mock.MockGood;
import com.service.decisionForm.ShipKPIService;
import com.service.syscomp.SqlCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ShipKPIServiceImpl implements ShipKPIService {

    @Autowired
    SqlCommand sqlCommand;
    @Autowired
    MockGoodMapper mockGoodMapper;
    @Autowired
    private HgApplicationProperty applicationProperty;

    /**
     * 基础数据
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List shipKPIBasics(String beginTime, String endTime) {
        List<Map> list = sqlMethod(beginTime, endTime);
        List<Map> listM = new ArrayList<>();
        List<MockGood> listGood = mockGoodMapper.selectAll();
        Map map1 = new HashMap();
        /*
        数据封装，以及规格化
         */
        for (Map map : list) {
            map1.put("ATA", map.get("ATA"));
            map1.put("LoadType", map.get("LoadType"));
            map1.put("Speed", new BigDecimal(Double.parseDouble(map.get("Speed").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map1.put("MainOrderID", map.get("MainOrderID"));
            map1.put("ShipName", map.get("ShipName"));
            map1.put("BerthID", map.get("BerthID"));
            if (Integer.valueOf(applicationProperty.getChinOrEng().toString()) == 1) {
                map1.put("VGuestName", map.get("VGuestName").toString().substring(0, 3) + "****" + map.get("VGuestName").toString().substring(map.get("VGuestName").toString().length() - 4));
                for (MockGood good : listGood) {
                    if (good.getName().equals(map.get("GoodsName").toString())) {
                        map1.put("GoodsName", good.getEngname());
                    }
                }
            } else {
                map1.put("VGuestName", map.get("VGuestName"));
                map1.put("GoodsName", map.get("GoodsName"));
            }
            //map1.put("GoodsName",map.get("GoodsName"));
            map1.put("ATA_BerthTime", new BigDecimal(Double.parseDouble(map.get("ATA_BerthTime").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map1.put("Berth_MeetTube", new BigDecimal(Double.parseDouble(map.get("Berth_MeetTube").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map1.put("MeetTube_OpenPump", new BigDecimal(Double.parseDouble(map.get("MeetTube_OpenPump").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map1.put("OpenPump_ClosePump", new BigDecimal(Double.parseDouble(map.get("OpenPump_ClosePump").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map1.put("ClosePump_RemoveTube", new BigDecimal(Double.parseDouble(map.get("ClosePump_RemoveTube").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map1.put("RemoveTube_LeaveTime", new BigDecimal(Double.parseDouble(map.get("RemoveTube_LeaveTime").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map1.put("Berth_RemoveTube", new BigDecimal(Double.parseDouble(map.get("Berth_RemoveTube").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map1.put("MeetTube_RemoveTube", new BigDecimal(Double.parseDouble(map.get("MeetTube_RemoveTube").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map1.put("MeetTube_LeaveTime", new BigDecimal(Double.parseDouble(map.get("MeetTube_LeaveTime").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map1.put("BerthTime_LeaveTime", new BigDecimal(Double.parseDouble(map.get("BerthTime_LeaveTime").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            listM.add(JSON.parseObject(JSON.toJSONString(map1), new TypeReference<Map>() {
            }));
            map1.clear();
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
    public List shipKPIAverage(String beginTime, String endTime) {
        List<Map> list = sqlMethod(beginTime, endTime);
        Map<String, Map> map = mainMethod(list, "BerthID");
        List<Map<String, Object>> listL = new ArrayList<>();
        Map mapL = new HashMap();
        /*
            数据处理
        */
        for (String str : map.keySet()) {
            mapL.put("BerthID", str);
            mapL.put("SumSpeed", new BigDecimal(Double.parseDouble(map.get(str).get("Speed1").toString()) + Double.parseDouble(map.get(str).get("Speed2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            if (Double.parseDouble(map.get(str).get("Sum1").toString()) > 0) {
                mapL.put("Speed1", new BigDecimal(Double.parseDouble(map.get(str).get("Speed1").toString()) / Double.parseDouble(map.get(str).get("Sum1").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            } else {
                mapL.put("Speed1", new BigDecimal(Double.parseDouble(map.get(str).get("Speed1").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            }
            if (Double.parseDouble(map.get(str).get("Sum2").toString()) > 0) {
                mapL.put("Speed2", new BigDecimal(Double.parseDouble(map.get(str).get("Speed2").toString()) / Double.parseDouble(map.get(str).get("Sum2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            } else {
                mapL.put("Speed2", new BigDecimal(Double.parseDouble(map.get(str).get("Speed2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            }

            listL.add(JSON.parseObject(JSON.toJSONString(mapL), new TypeReference<Map>() {
            }));
            mapL.clear();
        }
        /*
        排序
         */
        Collections.sort(listL, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get("SumSpeed").toString());//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get("SumSpeed").toString()); //name1是从你list里面拿出来的第二个name
                return -(name1.compareTo(name2));
            }
        });
        return listL;
    }

    /**
     * 按照ShipName查询
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List shipKPIShipName(String beginTime, String endTime) {
        List<Map> list = sqlMethod(beginTime, endTime);
        Map<String, Map> map = mainMethod(list, "ShipName");
        List<Map<String, Object>> listL = new ArrayList<>();
        Map mapL = new HashMap();
        /*
        数据处理
         */
        for (String str : map.keySet()) {
            mapL.put("ShipName", str);
            if (Double.parseDouble(map.get(str).get("Sum1").toString()) > 0 && Double.parseDouble(map.get(str).get("Sum2").toString()) > 0) {
                mapL.put("Speed3", new BigDecimal(Double.parseDouble(map.get(str).get("Speed1").toString()) / Double.parseDouble(map.get(str).get("Sum1").toString()) + Double.parseDouble(map.get(str).get("Speed2").toString()) / Double.parseDouble(map.get(str).get("Sum2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));

            }
            if (Double.parseDouble(map.get(str).get("Sum1").toString()) > 0 && Double.parseDouble(map.get(str).get("Sum2").toString()) <= 0) {
                mapL.put("Speed3", new BigDecimal(Double.parseDouble(map.get(str).get("Speed1").toString()) / Double.parseDouble(map.get(str).get("Sum1").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));

            }
            if (Double.parseDouble(map.get(str).get("Sum1").toString()) <= 0 && Double.parseDouble(map.get(str).get("Sum2").toString()) > 0) {
                mapL.put("Speed3", new BigDecimal(Double.parseDouble(map.get(str).get("Speed2").toString()) / Double.parseDouble(map.get(str).get("Sum2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));

            }
            if (Double.parseDouble(map.get(str).get("Sum1").toString()) > 0) {
                mapL.put("Speed1", new BigDecimal(Double.parseDouble(map.get(str).get("Speed1").toString()) / Double.parseDouble(map.get(str).get("Sum1").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            } else {
                mapL.put("Speed1", new BigDecimal(Double.parseDouble(map.get(str).get("Speed1").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            }
            if (Double.parseDouble(map.get(str).get("Sum2").toString()) > 0) {
                mapL.put("Speed2", new BigDecimal(Double.parseDouble(map.get(str).get("Speed2").toString()) / Double.parseDouble(map.get(str).get("Sum2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            } else {
                mapL.put("Speed2", new BigDecimal(Double.parseDouble(map.get(str).get("Speed2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            }
            mapL.put("MeetTube_OpenPump", new BigDecimal(Double.parseDouble(map.get(str).get("MeetTube_OpenPump").toString()) / Double.parseDouble(map.get(str).get("Sum").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            mapL.put("OpenPump_ClosePump", new BigDecimal(Double.parseDouble(map.get(str).get("OpenPump_ClosePump").toString()) / Double.parseDouble(map.get(str).get("Sum").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            mapL.put("ClosePump_RemoveTube", new BigDecimal(Double.parseDouble(map.get(str).get("ClosePump_RemoveTube").toString()) / Double.parseDouble(map.get(str).get("Sum").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            listL.add(JSON.parseObject(JSON.toJSONString(mapL), new TypeReference<Map>() {
            }));
            mapL.clear();
        }
        /*
        排序
         */
        Collections.sort(listL, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get("Speed3").toString());//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get("Speed3").toString()); //name1是从你list里面拿出来的第二个name
                return -(name1.compareTo(name2));
            }
        });
        return listL;
    }

    /**
     * TOP
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List shipKPIDesc(String beginTime, String endTime) {

        List<Map<String, Object>> listL = methodSort(beginTime, endTime);

         /*
        排序
         */
        Collections.sort(listL, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get("Speed3").toString());//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get("Speed3").toString()); //name1是从你list里面拿出来的第二个name
                return -(name1.compareTo(name2));
            }
        });
        return listL;
    }

    /**
     * Bottom
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List shipKPIAsc(String beginTime, String endTime) {
        List<Map<String, Object>> listL = methodSort(beginTime, endTime);

         /*
        排序
         */
        Collections.sort(listL, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get("Speed3").toString());//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get("Speed3").toString()); //name1是从你list里面拿出来的第二个name
                return (name1.compareTo(name2));
            }
        });
        return listL;
    }

    /**
     * 排序方法
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public List methodSort(String beginTime, String endTime) {
        List<Map> list = sqlMethod(beginTime, endTime);
        Map<String, Map> map = mainMethod(list, "VGuestName");
        List<Map<String, Object>> listL = new ArrayList<>();
        Map mapL = new HashMap();
        /*
        数据处理
         */
        for (String str : map.keySet()) {

            if (Integer.valueOf(applicationProperty.getChinOrEng().toString()) == 1) {
                mapL.put("VGuestName", str.substring(0, 3) + "****" + str.substring(str.length() - 4));
            } else {
                mapL.put("VGuestName", str);
            }
            if (Double.parseDouble(map.get(str).get("Sum1").toString()) > 0 && Double.parseDouble(map.get(str).get("Sum2").toString()) > 0) {
                mapL.put("Speed3", new BigDecimal(Double.parseDouble(map.get(str).get("Speed1").toString()) / Double.parseDouble(map.get(str).get("Sum1").toString()) + Double.parseDouble(map.get(str).get("Speed2").toString()) / Double.parseDouble(map.get(str).get("Sum2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));

            }
            if (Double.parseDouble(map.get(str).get("Sum1").toString()) > 0 && Double.parseDouble(map.get(str).get("Sum2").toString()) <= 0) {
                mapL.put("Speed3", new BigDecimal(Double.parseDouble(map.get(str).get("Speed1").toString()) / Double.parseDouble(map.get(str).get("Sum1").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));

            }
            if (Double.parseDouble(map.get(str).get("Sum1").toString()) <= 0 && Double.parseDouble(map.get(str).get("Sum2").toString()) > 0) {
                mapL.put("Speed3", new BigDecimal(Double.parseDouble(map.get(str).get("Speed2").toString()) / Double.parseDouble(map.get(str).get("Sum2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));

            }
            if (listL.size() <= 5) {
                listL.add(JSON.parseObject(JSON.toJSONString(mapL), new TypeReference<Map>() {
                }));
            } else {
                break;
            }

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
        parms.add(beginTime);
        parms.add(endTime);

        List<Object> listTest = sqlCommand.ExecSQLNoSession("Query_Stat_ShipKPI", "Ourway", "Admin", parms);
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
            if (map.containsKey(map1.get(str))) {
                map.get(map1.get(str).toString()).put("Sum", Double.parseDouble(map.get(map1.get(str).toString()).get("Sum").toString()) + 1);
                map.get(map1.get(str).toString()).put("MeetTube_OpenPump", Double.parseDouble(map.get(map1.get(str).toString()).get("MeetTube_OpenPump").toString()) + Double.parseDouble(map1.get("MeetTube_OpenPump").toString()));
                map.get(map1.get(str).toString()).put("OpenPump_ClosePump", Double.parseDouble(map.get(map1.get(str).toString()).get("OpenPump_ClosePump").toString()) + Double.parseDouble(map1.get("OpenPump_ClosePump").toString()));
                map.get(map1.get(str).toString()).put("ClosePump_RemoveTube", Double.parseDouble(map.get(map1.get(str).toString()).get("ClosePump_RemoveTube").toString()) + Double.parseDouble(map1.get("ClosePump_RemoveTube").toString()));
                if (map1.get("LoadType").toString().equals("装船")) {
                    map.get(map1.get(str).toString()).put("Sum1", Double.parseDouble(map.get(map1.get(str).toString()).get("Sum1").toString()) + 1);
                    map.get(map1.get(str).toString()).put("Speed1", Double.parseDouble(map.get(map1.get(str).toString()).get("Speed1").toString()) + Double.parseDouble(map1.get("Speed").toString()));
                }
                if (map1.get("LoadType").toString().equals("卸船")) {
                    map.get(map1.get(str).toString()).put("Sum2", Double.parseDouble(map.get(map1.get(str).toString()).get("Sum2").toString()) + 1);
                    map.get(map1.get(str).toString()).put("Speed2", Double.parseDouble(map.get(map1.get(str).toString()).get("Speed2").toString()) + Double.parseDouble(map1.get("Speed").toString()));
                }
            } else {
                map.put(map1.get(str).toString(), map1);
                map.get(map1.get(str).toString()).put("Sum", 1);
                map.get(map1.get(str).toString()).put("MeetTube_OpenPump", map1.get("MeetTube_OpenPump"));
                map.get(map1.get(str).toString()).put("OpenPump_ClosePump", map1.get("OpenPump_ClosePump"));
                map.get(map1.get(str).toString()).put("ClosePump_RemoveTube", map1.get("ClosePump_RemoveTube"));
                if (map1.get("LoadType").toString().equals("装船")) {
                    map.get(map1.get(str).toString()).put("Sum1", 1);
                    map.get(map1.get(str).toString()).put("Speed1", map1.get("Speed"));
                } else {
                    map.get(map1.get(str).toString()).put("Sum1", 0);
                    map.get(map1.get(str).toString()).put("Speed1", 0);
                }
                if (map1.get("LoadType").toString().equals("卸船")) {
                    map.get(map1.get(str).toString()).put("Sum2", 1);
                    map.get(map1.get(str).toString()).put("Speed2", map1.get("Speed"));
                } else {
                    map.get(map1.get(str).toString()).put("Sum2", 0);
                    map.get(map1.get(str).toString()).put("Speed2", 0);
                }
            }
        }

        return map;
    }
}
