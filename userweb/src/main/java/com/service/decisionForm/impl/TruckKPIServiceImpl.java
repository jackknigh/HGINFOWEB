package com.service.decisionForm.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.config.HgApplicationProperty;
import com.dao.db1.mock.MockGoodMapper;
import com.dao.entity.mock.MockGood;
import com.service.decisionForm.TruckKPIService;
import com.service.syscomp.SqlCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TruckKPIServiceImpl implements TruckKPIService {

    @Autowired
    SqlCommand sqlCommand;
    @Autowired
    MockGoodMapper mockGoodMapper;
    @Autowired
    private HgApplicationProperty applicationProperty;

    /**
     * 按照字段名查询，LoadingBay  GoodsName
     *
     * @param beginTime
     * @param endTime
     * @param strNeed
     * @return
     */
    @Override
    public List truckKPIBySome(String beginTime, String endTime, String strNeed) {

        List<Map> list = sqlMethod(beginTime, endTime);
        Map<String, Map> map = mainMethod(list, strNeed);
        Map<String, Map> mapS = mapMethod(map);
        List<Map<String, Object>> list2 = JSON.parseObject(JSON.toJSONString(mapS.values()), new TypeReference<List<Map<String, Object>>>() {
        });
        /*
        排序
         */
        Collections.sort(list2, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get("Speed4").toString());//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get("Speed4").toString()); //name1是从你list里面拿出来的第二个name
                return -(name1.compareTo(name2));
            }
        });
        return list2;
    }

    /**
     * 按照装车台查询
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public Map truckKPIByLoadingBay(String beginTime, String endTime) {

        List<Map> list = sqlMethod(beginTime, endTime);
        Map<String, Map> map = mainMethod(list, "LoadingBay");
        Map<String, Map> mapS = mapMethod(map);
        List<Map> listLast = new ArrayList<>();
        for (String str : mapS.keySet()) {
            listLast.add(mapS.get(str));
        }
        return map;
    }

    /**
     * 按照VGuestName查询，按照speed3降序
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List truckKPIDesc(String beginTime, String endTime) {
        List<Map> list = sqlMethod(beginTime, endTime);
        Map<String, Map> map = mainMethod(list, "VGuestName");
        Map<String, Map> mapS = mapMethod(map);
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (String strKey : mapS.keySet()) {
            if (Integer.valueOf(applicationProperty.getChinOrEng().toString()) == 1) {
                map.get(strKey).put("VGuestName", strKey.toString().substring(0, 3) + "****" + strKey.substring(strKey.length() - 4));
            } else {
                map.get(strKey).put("VGuestName", strKey);
            }
            listMap.add(map.get(strKey));
        }
        /*
        按照speed3排序
         */
        Collections.sort(listMap, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get("Speed3").toString());//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get("Speed3").toString()); //name1是从你list里面拿出来的第二个name
                return -(name1.compareTo(name2));
            }
        });
        List<Map<String, Object>> lastList = new ArrayList<>();
        for (int i = 0; i < listMap.size(); i++) {
            if (i < 5) {
                lastList.add(listMap.get(i));
            }
        }

        return lastList;
    }

    /**
     * 按照VGuestName查询，按照speed3升序
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List truckKPIAsc(String beginTime, String endTime) {
        List<Map> list = sqlMethod(beginTime, endTime);
        Map<String, Map> map = mainMethod(list, "VGuestName");
        Map<String, Map> mapS = mapMethod(map);
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (String strKey : mapS.keySet()) {
           /* if (Integer.valueOf(applicationProperty.getChinOrEng().toString()) == 1) {
                map.get(strKey).put("VGuestName",strKey.toString().substring(0,3)+"****"+ strKey.substring(strKey.length()-4));
            } else {
                map.get(strKey).put("VGuestName",strKey);
            }*/
            listMap.add(map.get(strKey));
        }
        /*
        按照speed3排序
         */
        Collections.sort(listMap, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get("Speed3").toString());//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get("Speed3").toString()); //name1是从你list里面拿出来的第二个name
                return name1.compareTo(name2);
            }
        });
        List<Map<String, Object>> lastList = new ArrayList<>();
        for (int i = 0; i < listMap.size(); i++) {
            if (i < 5) {
                lastList.add(listMap.get(i));
            }
        }

        return lastList;
    }

    /**
     * 数据二次处理，以及格式的规整化
     *
     * @param map
     * @return
     */
    public Map mapMethod(Map<String, Map> map) {
        List<MockGood> listGood = mockGoodMapper.selectAll();
        for (String strKey : map.keySet()) {

            if (Double.parseDouble(map.get(strKey).get("Speed1").toString()) > 0 && Double.parseDouble(map.get(strKey).get("Speed2").toString()) > 0) {
                map.get(strKey).put("Speed3", new BigDecimal((Double.parseDouble(map.get(strKey).get("Speed1").toString()) + Double.parseDouble(map.get(strKey).get("Speed2").toString())) / Double.parseDouble(map.get(strKey).get("sum").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            } else if (Double.parseDouble(map.get(strKey).get("Speed1").toString()) > 0 && Double.parseDouble(map.get(strKey).get("Speed2").toString()) <= 0) {
                map.get(strKey).put("Speed3", new BigDecimal((Double.parseDouble(map.get(strKey).get("Speed1").toString()) / Double.parseDouble(map.get(strKey).get("sum1").toString()))).setScale(3, BigDecimal.ROUND_HALF_UP));
            } else if (Double.parseDouble(map.get(strKey).get("Speed1").toString()) <= 0 && Double.parseDouble(map.get(strKey).get("Speed2").toString()) > 0) {
                map.get(strKey).put("Speed3", new BigDecimal((Double.parseDouble(map.get(strKey).get("Speed2").toString()) / Double.parseDouble(map.get(strKey).get("sum2").toString()))).setScale(3, BigDecimal.ROUND_HALF_UP));
            }
            if (Double.parseDouble(map.get(strKey).get("sum1").toString()) > 0) {
                map.get(strKey).put("Speed1", new BigDecimal(Double.parseDouble(map.get(strKey).get("Speed1").toString()) / Double.parseDouble(map.get(strKey).get("sum1").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            } else {
                map.get(strKey).put("Speed1", new BigDecimal(Double.parseDouble(map.get(strKey).get("Speed1").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            }
            if (Double.parseDouble(map.get(strKey).get("sum2").toString()) > 0) {
                map.get(strKey).put("Speed2", new BigDecimal(Double.parseDouble(map.get(strKey).get("Speed2").toString()) / Double.parseDouble(map.get(strKey).get("sum2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            } else {
                map.get(strKey).put("Speed2", new BigDecimal(Double.parseDouble(map.get(strKey).get("Speed2").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            }
            map.get(strKey).put("Speed4", new BigDecimal(Double.parseDouble(map.get(strKey).get("Speed2").toString()) + Double.parseDouble(map.get(strKey).get("Speed1").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map.get(strKey).put("ReadTime", new BigDecimal(Double.parseDouble(map.get(strKey).get("ReadTime").toString()) / Double.parseDouble(map.get(strKey).get("sum").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map.get(strKey).put("OperateTime", new BigDecimal(Double.parseDouble(map.get(strKey).get("OperateTime").toString()) / Double.parseDouble(map.get(strKey).get("sum").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map.get(strKey).put("FileTime", new BigDecimal(Double.parseDouble(map.get(strKey).get("FileTime").toString()) / Double.parseDouble(map.get(strKey).get("sum").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map.get(strKey).put("AllTime", new BigDecimal(Double.parseDouble(map.get(strKey).get("ReadTime").toString()) + Double.parseDouble(map.get(strKey).get("OperateTime").toString()) + Double.parseDouble(map.get(strKey).get("FileTime").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            if (Integer.valueOf(applicationProperty.getChinOrEng().toString()) == 1) {
                map.get(strKey).put("VGuestName", map.get(strKey).get("VGuestName").toString().substring(0, 3) + "****" + map.get(strKey).get("VGuestName").toString().substring(map.get(strKey).get("VGuestName").toString().length() - 4));
                for (MockGood good : listGood) {
                    if (good.getName().equals(map.get(strKey).get("GoodsName").toString())) {
                        map.get(strKey).put("GoodsName", good.getEngname());
                    }
                }
            }
        }
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
        parms.add(beginTime);
        parms.add(endTime);
        parms.add(0);//(default 0,1234)
        List<Object> listTest = sqlCommand.ExecSQLNoSession("Query_Stat_TruckKPI", "Ourway", "Admin", parms);
        String str = JSON.toJSONString(listTest);
        List<Map> list = JSON.parseObject(str, new TypeReference<List<Map>>() {
        });
        return list;
    }


    /**
     * 具体的数据处理
     *
     * @param list
     * @param str
     * @return
     */
    public Map mainMethod(List<Map> list, String str) {
        Map<String, Map> map = new HashMap();

        for (Map map1 : list) {
            if (map.containsKey(map1.get(str))) {
                if (map1.get("LoadType").equals("发车")) {
                    map.get(map1.get(str)).put("Speed1", Double.parseDouble((map.get(map1.get(str)).get("Speed1")).toString()) + Double.parseDouble(map1.get("Speed").toString()));
                    map.get(map1.get(str)).put("sum1", Double.parseDouble((map.get(map1.get(str)).get("sum1")).toString()) + 1);
                }
                if (map1.get("LoadType").equals("卸车")) {
                    map.get(map1.get(str)).put("Speed2", Double.parseDouble(map.get(map1.get(str)).get("Speed2").toString()) + Double.parseDouble(map1.get("Speed").toString()));
                    map.get(map1.get(str)).put("sum2", Double.parseDouble((map.get(map1.get(str)).get("sum2")).toString()) + 1);
                }

                map.get(map1.get(str)).put("ReadTime", Double.parseDouble(map.get(map1.get(str)).get("ReadTime").toString()) + Double.parseDouble(map1.get("ReadTime").toString()));
                map.get(map1.get(str)).put("OperateTime", Double.parseDouble(map.get(map1.get(str)).get("OperateTime").toString()) + Double.parseDouble(map1.get("OperateTime").toString()));
                map.get(map1.get(str)).put("FileTime", Double.parseDouble(map.get(map1.get(str)).get("FileTime").toString()) + Double.parseDouble(map1.get("FileTime").toString()));
                map.get(map1.get(str)).put("sum", Double.parseDouble(map.get(map1.get(str)).get("sum").toString()) + 1);
            } else {
                map.put((String) map1.get(str), map1);
                if (map1.get("LoadType").equals("发车")) {
                    map.get(map1.get(str)).put("Speed1", map1.get("Speed"));
                    map.get(map1.get(str)).put("sum1", 1);
                } else {
                    map.get(map1.get(str)).put("Speed1", 0d);
                    map.get(map1.get(str)).put("sum1", 0);
                }
                if (map1.get("LoadType").equals("卸车")) {
                    map.get(map1.get(str)).put("Speed2", map1.get("Speed"));
                    map.get(map1.get(str)).put("sum2", 1);
                } else {
                    map.get(map1.get(str)).put("Speed2", 0d);
                    map.get(map1.get(str)).put("sum2", 0);
                }
                map.get(map1.get(str)).put("sum", 1);
            }
        }
        return map;
    }

}
