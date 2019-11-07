package com.service.decisionForm.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.config.HgApplicationProperty;
import com.dao.db1.mock.MockGoodMapper;
import com.dao.entity.mock.MockGood;
import com.service.decisionForm.ShipPerformanceService;
import com.service.syscomp.SqlCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ShipPerformanceServiceImpl implements ShipPerformanceService {

    @Autowired
    SqlCommand sqlCommand;
    @Autowired
    MockGoodMapper mockGoodMapper;
    @Autowired
    private HgApplicationProperty applicationProperty;

    @Override
    public List ShipPerformance(String beginTime, String endTime) {
        ArrayList<Object> parms = new ArrayList<Object>();
        parms.add("Ourway");
        parms.add("");
        parms.add("");
        parms.add("");
        parms.add("OPBeginDate");//OPEndDate
        parms.add(beginTime);
        parms.add(endTime);
        parms.add("");

        //OpEndDAte

        List<Object> listTest = sqlCommand.ExecSQLNoSession("Query_Stat_ShipPerformance", "Ourway", "Admin", parms);
        String str = JSON.toJSONString(listTest);
        List<Map> list = JSON.parseObject(str, new TypeReference<List<Map>>() {
        });
        return list;

    }

    @Override
    public List boatSelectAll(String beginTime, String endTime) {
        List<Map> list = ShipPerformance(beginTime, endTime);
        Map<String, Map> map = new HashMap();
        List<MockGood> listGood = mockGoodMapper.selectAll();
        List value = new ArrayList();
        String str = "cEShipName";
        for (Map map2 : list) {
          /*  if (i == 0) {

                map.put((String) list.get(0).get(str), list.get(0));
                map.get(list.get(0).get(str)).put("tichuan", Double.parseDouble(list.get(0).get("BL_Ship").toString()));
                map.get(list.get(0).get(str)).put("tique", Double.parseDouble(list.get(0).get("BL_Affirm").toString()));
                map.get(list.get(0).get(str)).put("chuanque", Double.parseDouble(list.get(0).get("Ship_Affirm").toString()));
                map.get(list.get(0).get(str)).put("kaobo", list.get(0).get("BerthTime").toString());
                map.get(list.get(0).get(str)).put("data", 0);

                if (list.get(0).get("OrderTypeName").equals("发船")) {
                    map.get(list.get(0).get(str)).put("fachuanshu", 1);
                    map.get(list.get(0).get(str)).put("shangjianf", Double.parseDouble(list.get(0).get("AffirmNumber").toString()));
                    map.get(list.get(0).get(str)).put("speedf", Double.parseDouble(list.get(0).get("Speed").toString()));
                } else {
                    map.get(list.get(0).get(str)).put("fachuanshu", 0);
                    map.get(list.get(0).get(str)).put("shangjianf", 0d);
                    map.get(list.get(0).get(str)).put("speedf", 0d);
                }

                if (list.get(0).get("OrderTypeName").equals("收船")) {
                    map.get(list.get(0).get(str)).put("shouchuanshu", 1);
                    map.get(list.get(0).get(str)).put("shangjians", Double.parseDouble(list.get(0).get("AffirmNumber").toString()));
                    map.get(list.get(0).get(str)).put("speeds", Double.parseDouble(list.get(0).get("Speed").toString()));
                } else {
                    map.get(list.get(0).get(str)).put("shouchuanshu", 0);
                    map.get(list.get(0).get(str)).put("shangjians", 0d);
                    map.get(list.get(0).get(str)).put("speeds", 0d);
                }
            } else {*/
            if (map.containsKey(map2.get(str))) {
                map.get(map2.get(str)).put("tichuan", Double.parseDouble((map.get(map2.get(str)).get("tichuan")).toString()) + Double.parseDouble(map2.get("BL_Ship").toString()));
                map.get(map2.get(str)).put("tique", Double.parseDouble((map.get(map2.get(str)).get("tique")).toString()) + Double.parseDouble(map2.get("BL_Affirm").toString()));
                map.get(map2.get(str)).put("chuanque", Double.parseDouble((map.get(map2.get(str)).get("chuanque")).toString()) + Double.parseDouble(map2.get("Ship_Affirm").toString()));

                map.get(map2.get(str)).put("kaobo", (map.get(map2.get(str)).get("kaobo")) + "," + (map2.get("BerthTime").toString()));
                //计算第二个List
                if (map2.get("OrderTypeName").equals("发船")) {
                    map.get(map2.get(str)).put("fachuanshu", Integer.parseInt((map.get(map2.get(str)).get("fachuanshu")).toString()) + 1);
                    map.get(map2.get(str)).put("shangjianf", Double.parseDouble((map.get(map2.get(str)).get("shangjianf")).toString()) + Double.parseDouble(map2.get("AffirmNumber").toString()));
                    map.get(map2.get(str)).put("speedf", Double.parseDouble((map.get(map2.get(str)).get("speedf")).toString()) + Double.parseDouble(map2.get("Speed").toString()));
                } else if (map2.get("OrderTypeName").equals("收船")) {
                    map.get(map2.get(str)).put("shouchuanshu", Integer.parseInt((map.get(map2.get(str)).get("shouchuanshu")).toString()) + 1);
                    map.get(map2.get(str)).put("shangjians", Double.parseDouble((map.get(map2.get(str)).get("shangjians")).toString()) + Double.parseDouble(map2.get("AffirmNumber").toString()));
                    map.get(map2.get(str)).put("speeds", Double.parseDouble((map.get(map2.get(str)).get("speeds")).toString()) + Double.parseDouble(map2.get("Speed").toString()));
                }
            } else {

                map.put((String) map2.get(str), map2);
                map.get(map2.get(str)).put("tichuan", Double.parseDouble(map2.get("BL_Ship").toString()));
                if (map2.get("BL_Affirm") != null) {
                    map.get(map2.get(str)).put("tique", Double.parseDouble(map2.get("BL_Affirm").toString()));
                } else {
                    map.get(map2.get(str)).put("tique", 0);
                }
                if (map2.get("Ship_Affirm") != null) {
                    map.get(map2.get(str)).put("chuanque", Double.parseDouble(map2.get("Ship_Affirm").toString()));
                } else {
                    map.get(map2.get(str)).put("chuanque", 0);
                }

                map.get(map2.get(str)).put("kaobo", map2.get("BerthTime").toString());
                map.get(map2.get(str)).put("data", 0);
                if (map2.get("OrderTypeName").equals("发船")) {
                    map.get(map2.get(str)).put("fachuanshu", 1);
                    if (map2.get("AffirmNumber") != null) {
                        map.get(map2.get(str)).put("shangjianf", Double.parseDouble(map2.get("AffirmNumber").toString()));
                    } else {
                        map.get(map2.get(str)).put("shangjianf", 0);
                    }
                    if (map2.get("Speed") != null) {
                        map.get(map2.get(str)).put("speedf", Double.parseDouble(map2.get("Speed").toString()));

                    } else {
                        map.get(map2.get(str)).put("speedf", 0);

                    }
                } else {
                    map.get(map2.get(str)).put("fachuanshu", 0);
                    map.get(map2.get(str)).put("shangjianf", 0d);
                    map.get(map2.get(str)).put("speedf", 0d);
                }
                if (map2.get("OrderTypeName").equals("收船")) {
                    map.get(map2.get(str)).put("shouchuanshu", 1);
                    if (map2.get("AffirmNumber") == null) {
                        map.get(map2.get(str)).put("shangjians", Double.parseDouble(map2.get("AffirmNumber").toString()));

                    } else {
                        map.get(map2.get(str)).put("shangjians", 0);

                    }
                    if (map2.get("Speed") == null) {
                        map.get(map2.get(str)).put("speeds", Double.parseDouble(map2.get("Speed").toString()));
                    } else {
                        map.get(map2.get(str)).put("speeds", 0);
                    }

                } else {
                    map.get(map2.get(str)).put("shouchuanshu", 0);
                    map.get(map2.get(str)).put("shangjians", 0d);
                    map.get(map2.get(str)).put("speeds", 0d);
                }
            }
        }


        map = mapMethod(map);


        for (String maps : map.keySet()) {
            String t = map.get(maps).get("kaobo").toString();
            String shangjianf = map.get(maps).get("shangjianf").toString();
            String shangjians = map.get(maps).get("shangjians").toString();
            String speedf = map.get(maps).get("speedf").toString();
            String speeds = map.get(maps).get("speeds").toString();
            String shouchuanshu = map.get(maps).get("shouchuanshu").toString();
            String fachuanshu = map.get(maps).get("fachuanshu").toString();
            List<Map> lm = new ArrayList<>();
            Map ma = new HashMap();
            Map mg = new HashMap();
            if (Integer.parseInt(map.get(maps).get("fachuanshu").toString()) != 0) {
                ma.put("type", "发船");
                ma.put("shangjian", shangjianf);
                ma.put("speed", Double.parseDouble(speedf) / Double.parseDouble(fachuanshu));
            } else {
                ma.put("type", "发船");
                ma.put("shangjian", 0);
                ma.put("speed", 0);
            }
            ma.put("speed", new BigDecimal(ma.get("speed").toString()).setScale(3, BigDecimal.ROUND_HALF_UP));
            lm.add(ma);

            if (Integer.parseInt(map.get(maps).get("shouchuanshu").toString()) != 0) {
                mg.put("type", "收船");
                mg.put("shangjian", shangjians);
                mg.put("speed", Double.parseDouble(speeds) / Double.parseDouble(shouchuanshu));
            } else {
                mg.put("type", "收船");
                mg.put("shangjian", 0);
                mg.put("speed", 0);
            }
            mg.put("speed", new BigDecimal(mg.get("speed").toString()).setScale(3, BigDecimal.ROUND_HALF_UP));
            lm.add(mg);


            String[] m = t.split(",");
            Set set = new HashSet();
            //遍历数组并存入集合,如果元素已存在则不会重复存入
            for (int i = 0; i < m.length; i++) {
                set.add(m[i]);
            }
            map.get(maps).put("kaobo", set.toArray());
            map.get(maps).put("data", lm);
            if (Integer.valueOf(applicationProperty.getChinOrEng()) == 1) {
                map.get(maps).put("VGuestName", map.get(maps).get("VGuestName").toString().substring(0, 3) + "****" + map.get(maps).get("VGuestName").toString().substring(map.get(maps).get("VGuestName").toString().length() - 4));
                for (MockGood good : listGood) {
                    if (good.getName().equals(map.get(maps).get("GoodsName").toString())) {
                        map.get(maps).put("GoodsName", good.getEngname());
                    }
                }
            }
            value.add(map.get(maps));
        }

        return value;
    }

    @Override
    public List boatBycustomer(String beginTime, String endTime) {
        List<Map> list = ShipPerformance(beginTime, endTime);
        Map<String, Map> map = new HashMap();
        List<MockGood> listGood = mockGoodMapper.selectAll();
        List value = new ArrayList();
        String str = "VGuestName";
        for (Map map2 : list) {
           /* if (i == 0) {
                map.put((String) list.get(0).get(str), list.get(0));
                map.get(list.get(0).get(str)).put("BLShip", Double.parseDouble(list.get(0).get("BL_Ship_").toString()));
                map.get(list.get(0).get(str)).put("BLAffirm", Double.parseDouble(list.get(0).get("BL_Affirm_").toString()));
                map.get(list.get(0).get(str)).put("ShipAffirm", Double.parseDouble(list.get(0).get("Ship_Affirm_").toString()));
            } else {*/
            if (map.containsKey(map2.get(str))) {
                map.get(map2.get(str)).put("BLShip", Double.parseDouble((map.get(map2.get(str)).get("BLShip")).toString()) + Double.parseDouble(map2.get("BL_Ship_").toString()));
                map.get(map2.get(str)).put("BLAffirm", Double.parseDouble((map.get(map2.get(str)).get("BLAffirm")).toString()) + Double.parseDouble(map2.get("BL_Affirm_").toString()));
                map.get(map2.get(str)).put("ShipAffirm", Double.parseDouble((map.get(map2.get(str)).get("ShipAffirm")).toString()) + Double.parseDouble(map2.get("Ship_Affirm_").toString()));
            } else {
                map.put((String) map2.get(str), map2);
                map.get(map2.get(str)).put("BLShip", Double.parseDouble(map2.get("BL_Ship_").toString()));
                map.get(map2.get(str)).put("BLAffirm", Double.parseDouble(map2.get("BL_Affirm_").toString()));
                map.get(map2.get(str)).put("ShipAffirm", Double.parseDouble(map2.get("Ship_Affirm_").toString()));
            }
        }
        /* }*/
        mapMethodbyCus(map);
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (String strKey : map.keySet()) {
            if (Integer.valueOf(applicationProperty.getChinOrEng()) == 1) {
                map.get(strKey).put("VGuestName", strKey.substring(0, 3) + "****" + strKey.substring(strKey.length() - 4));
                for (MockGood good : listGood) {
                    if (good.getName().equals(map.get(strKey).get("GoodsName").toString())) {
                        map.get(strKey).put("GoodsName", good.getEngname());
                    }
                }
            }

            /* map.get(strKey).put("EngName",strKey.substring(0,2)+ "****"+strKey.substring(-4));*/
            listMap.add(map.get(strKey));
        }
        List th = new ArrayList();

        List<Map<String, Object>> BL = sxpailie(listMap, "BLShip");
        List<Map<String, Object>> lastList = lastpx(JSON.parseObject((JSON.toJSONString(BL)), new TypeReference<List<Map<String, Object>>>() {
        }));
        th.add(lastList);
        List<Map<String, Object>> FirstList = firtpx(JSON.parseObject((JSON.toJSONString(BL)), new TypeReference<List<Map<String, Object>>>() {
        }));
        // Collections.reverse(FirstList);
        th.add(FirstList);

        List<Map<String, Object>> BLA = sxpailie(listMap, "BLAffirm");
        List<Map<String, Object>> lastList1 = lastpx(JSON.parseObject((JSON.toJSONString(BLA)), new TypeReference<List<Map<String, Object>>>() {
        }));//(提单数-确认标准)/确认标准%
        th.add(lastList1);

        List<Map<String, Object>> FirstList1 = firtpx(JSON.parseObject((JSON.toJSONString(BLA)), new TypeReference<List<Map<String, Object>>>() {
        }));
        //Collections.reverse(FirstList1);
        th.add(FirstList1);

        List<Map<String, Object>> SA = sxpailie(listMap, "ShipAffirm");
        List<Map<String, Object>> lastList2 = lastpx(JSON.parseObject((JSON.toJSONString(SA)), new TypeReference<List<Map<String, Object>>>() {
        })); //(船检数-确认标准)/确认标准%
        th.add(lastList2);
        List<Map<String, Object>> FirstList2 = firtpx(JSON.parseObject((JSON.toJSONString(SA)), new TypeReference<List<Map<String, Object>>>() {
        }));
        // Collections.reverse(FirstList2);
        th.add(FirstList2);

        return th;
    }

    public List lastpx(List<Map<String, Object>> BTS) {
        List<Map<String, Object>> lastList = new ArrayList<>(); //(提单数-船检数)/船检数%

        for (int i = 0; i < BTS.size(); i++) {
            if (i < 5) {
                lastList.add(BTS.get(i));
            }
        }
        return lastList;
    }

    public List firtpx(List<Map<String, Object>> BTS) {
        List<Map<String, Object>> firtList = new ArrayList<>();
        for (int i = BTS.size() - 1; i >= 0; i--) {
            if (i > BTS.size() - 6) {
                firtList.add(BTS.get(i));
            }
        }
        return firtList;
    }

    public List sxpailie(List<Map<String, Object>> listMap, String str) {
        Collections.sort(listMap, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get(str).toString());//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get(str).toString()); //name1是从你list里面拿出来的第二个name
                return name1.compareTo(name2);
            }
        });
        return listMap;
    }

    public Map mapMethod(Map<String, Map> map) {
        for (String strKey : map.keySet()) {
            map.get(strKey).put("chuanque", new BigDecimal(Double.parseDouble(map.get(strKey).get("chuanque").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map.get(strKey).put("speeds", new BigDecimal(Double.parseDouble(map.get(strKey).get("speeds").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map.get(strKey).put("tichuan", new BigDecimal((Double.parseDouble(map.get(strKey).get("tichuan").toString()))).setScale(3, BigDecimal.ROUND_HALF_UP));
            map.get(strKey).put("speedf", new BigDecimal(Double.parseDouble(map.get(strKey).get("speedf").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map.get(strKey).put("tique", new BigDecimal(Double.parseDouble(map.get(strKey).get("tique").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map.get(strKey).put("shangjianf", new BigDecimal(Double.parseDouble(map.get(strKey).get("shangjianf").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map.get(strKey).put("shangjians", new BigDecimal(Double.parseDouble(map.get(strKey).get("shangjians").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
        }
        return map;
    }

    public Map mapMethodbyCus(Map<String, Map> map) {
        for (String strKey : map.keySet()) {
            map.get(strKey).put("BLShip", new BigDecimal(Double.parseDouble(map.get(strKey).get("BLShip").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map.get(strKey).put("BLAffirm", new BigDecimal(Double.parseDouble(map.get(strKey).get("BLAffirm").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map.get(strKey).put("ShipAffirm", new BigDecimal((Double.parseDouble(map.get(strKey).get("ShipAffirm").toString()))).setScale(3, BigDecimal.ROUND_HALF_UP));
        }
        return map;
    }


}
