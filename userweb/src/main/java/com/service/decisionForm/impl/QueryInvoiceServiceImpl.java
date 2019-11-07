package com.service.decisionForm.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.config.HgApplicationProperty;
import com.service.decisionForm.QueryInvoiceService;
import com.service.syscomp.SqlCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class QueryInvoiceServiceImpl implements QueryInvoiceService {
    @Autowired
    SqlCommand sqlCommand;

    @Autowired
    private HgApplicationProperty applicationProperty;

    @Override
    public List queryInvoice(String beginTime, String endTime, String type) {

        List<Map> list = sqlMethod(beginTime, endTime, type);
        List listLast = mainMethod(list);

        return listLast;
    }

    /**
     * 调用sqlCommand
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public List sqlMethod(String beginTime, String endTime, String type) {
        ArrayList<Object> parms = new ArrayList<Object>();
        parms.add("Ourway");
        parms.add("");
        parms.add(type);//OccurDate  InvoiceDate
        parms.add(beginTime);
        parms.add(endTime);
        List<Object> listTest = sqlCommand.ExecSQLNoSession("Querty_Stat_QueryInvoice", "Ourway", "Admin", parms);
        String str = JSON.toJSONString(listTest);
        List<Map> list = JSON.parseObject(str, new TypeReference<List<Map>>() {
        });
        return list;
    }

    /**
     * 具体数据处理
     *
     * @param list
     * @return
     */
    public List mainMethod(List<Map> list) {
        Map<String, Map> map = new HashMap();
        Map<String, Map> mapS = new HashMap();
        String str = "GuestName";
        List<Map> list2 = list;
        List<Map<String, Object>> listMap = new ArrayList<>();
            /*
                数据处理
             */
        for (Map map2 : list2) {
            if (mapS.containsKey(map2.get("BargainID"))) {
                mapS.get(map2.get("BargainID")).put("BargainStorageCharges", Double.parseDouble(mapS.get(map2.get("BargainID")).get("BargainStorageCharges").toString()) + Double.parseDouble(map2.get("StorageCharges").toString()));
                mapS.get(map2.get("BargainID")).put("BargainPortCharges", Double.parseDouble(mapS.get(map2.get("BargainID")).get("BargainPortCharges").toString()) + Double.parseDouble(map2.get("PortCharges").toString()));
                mapS.get(map2.get("BargainID")).put("BargainOperationCharges", Double.parseDouble(mapS.get(map2.get("BargainID")).get("BargainOperationCharges").toString()) + Double.parseDouble(map2.get("OperationCharges").toString()));
                mapS.get(map2.get("BargainID")).put("SumBargain", Double.parseDouble(mapS.get(map2.get("BargainID")).get("SumBargain").toString()) + Double.parseDouble(map2.get("StorageCharges").toString()) + Double.parseDouble(map2.get("OperationCharges").toString()) + Double.parseDouble(map2.get("PortCharges").toString()));
            } else {
                mapS.put((String) map2.get("BargainID"), map2);
                mapS.get(map2.get("BargainID")).put("SumBargain", Double.parseDouble(map2.get("StorageCharges").toString()) + Double.parseDouble(map2.get("PortCharges").toString()) + Double.parseDouble(map2.get("OperationCharges").toString()));
                mapS.get(map2.get("BargainID")).put("BargainStorageCharges", mapS.get(map2.get("BargainID")).get("StorageCharges"));
                mapS.get(map2.get("BargainID")).put("BargainPortCharges", mapS.get(map2.get("BargainID")).get("PortCharges"));
                mapS.get(map2.get("BargainID")).put("BargainOperationCharges", mapS.get(map2.get("BargainID")).get("OperationCharges"));
            }
        }
        /*
            数据格式化，保留三位小数
         */
        for (String keyStr : mapS.keySet()) {
            mapS.get(keyStr).put("BargainStorageCharges", new BigDecimal(Double.parseDouble(mapS.get(keyStr).get("BargainStorageCharges").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            mapS.get(keyStr).put("BargainPortCharges", new BigDecimal(Double.parseDouble(mapS.get(keyStr).get("BargainPortCharges").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            mapS.get(keyStr).put("BargainOperationCharges", new BigDecimal(Double.parseDouble(mapS.get(keyStr).get("BargainOperationCharges").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            mapS.get(keyStr).put("SumBargain", new BigDecimal(Double.parseDouble(mapS.get(keyStr).get("SumBargain").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            listMap.add(mapS.get(keyStr));
        }

        /*
            List<Map> 排序
         */
        Collections.sort(listMap, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get("SumBargain").toString());//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get("SumBargain").toString()); //name1是从你list里面拿出来的第二个name
                return -(name1.compareTo(name2));
            }
        });
        /*
            数据处理
         */
        for (Map map3 : list) {
            if (map.containsKey(map3.get(str))) {
                map.get(map3.get(str)).put("SumStorageCharges", Double.parseDouble(map.get(map3.get(str)).get("SumStorageCharges").toString()) + Double.parseDouble(map3.get("StorageCharges").toString()));
                map.get(map3.get(str)).put("SumPortCharges", Double.parseDouble(map.get(map3.get(str)).get("SumPortCharges").toString()) + Double.parseDouble(map3.get("PortCharges").toString()));
                map.get(map3.get(str)).put("SumOperationCharges", Double.parseDouble(map.get(map3.get(str)).get("SumOperationCharges").toString()) + Double.parseDouble(map3.get("OperationCharges").toString()));
            } else {
                map.put((String) map3.get(str), map3);
                map.get(map3.get(str)).put("SumStorageCharges", map3.get("StorageCharges"));
                map.get(map3.get(str)).put("SumPortCharges", map3.get("PortCharges"));
                map.get(map3.get(str)).put("SumOperationCharges", map3.get("OperationCharges"));
            }
        }
         /*
            数据格式化，保留三位小数
         */
        for (String sumKey : map.keySet()) {
            map.get(sumKey).put("SumAll", Double.parseDouble(map.get(sumKey).get("SumStorageCharges").toString()) + Double.parseDouble(map.get(sumKey).get("SumPortCharges").toString()) + Double.parseDouble(map.get(sumKey).get("SumOperationCharges").toString()));
            map.get(sumKey).put("SumStorageCharges", new BigDecimal(Double.parseDouble(map.get(sumKey).get("SumStorageCharges").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map.get(sumKey).put("SumPortCharges", new BigDecimal(Double.parseDouble(map.get(sumKey).get("SumPortCharges").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
            map.get(sumKey).put("SumOperationCharges", new BigDecimal(Double.parseDouble(map.get(sumKey).get("SumOperationCharges").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));

        }

        /*
            listC Top五，listD 更多项，
         */
        List<Object> listC = new ArrayList<>();
        List<Map> listD = new ArrayList<>();
        List<Map<String, Object>> last = new ArrayList<>();
        Map mapLL = new HashMap();
        Map mapLL2 = new HashMap();

        for (String strKey : map.keySet()) {
            for (int i = 0; i < JSON.parseObject(JSON.toJSONString(listMap), new TypeReference<List<Map>>() {
            }).size(); i++) {
                String str4 = listMap.get(i).get(str).toString();
                String str5 = strKey;
                Integer num = i;
                if (str4.equals(str5)) {
                    if (Integer.valueOf(applicationProperty.getChinOrEng().toString()) == 1) {
                        listMap.get(num).put("GuestName", listMap.get(num).get("GuestName").toString().substring(0, 3) + "****" + listMap.get(num).get("GuestName").toString().substring(listMap.get(num).get("GuestName").toString().length() - 4));
                    }
                    if (listC.size() < 6) {
                        listC.add(listMap.get(i));
                    } else {
                        if (!mapLL.containsKey("SumStorageCharges")) {
                            mapLL.put("SumStorageCharges", 0);
                            mapLL.put("SumPortCharges", 0);
                            mapLL.put("SumOperationCharges", 0);
                            mapLL.put("SumAll", 0);
                        }

                        listD.add(listMap.get(i));
                        mapLL.put("SumStorageCharges", Double.parseDouble(listMap.get(i).get("BargainStorageCharges").toString()) + Double.parseDouble(mapLL.get("SumStorageCharges").toString()));
                        mapLL.put("SumPortCharges", Double.parseDouble(listMap.get(i).get("BargainPortCharges").toString()) + Double.parseDouble(mapLL.get("SumPortCharges").toString()));
                        mapLL.put("SumOperationCharges", Double.parseDouble(listMap.get(i).get("BargainOperationCharges").toString()) + Double.parseDouble(mapLL.get("SumOperationCharges").toString()));
                        mapLL.put("SumAll", Double.parseDouble(listMap.get(i).get("SumBargain").toString()) + Double.parseDouble(mapLL.get("SumAll").toString()));
                    }

                }
            }
            if (listD.size() > 0) {
                mapLL.put("SumAll", new BigDecimal(Double.parseDouble(mapLL.get("SumStorageCharges").toString()) + Double.parseDouble(mapLL.get("SumPortCharges").toString()) + Double.parseDouble(mapLL.get("SumOperationCharges").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
                mapLL.put("SumStorageCharges", new BigDecimal(Double.parseDouble(mapLL.get("SumStorageCharges").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
                mapLL.put("SumPortCharges", new BigDecimal(Double.parseDouble(mapLL.get("SumPortCharges").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
                mapLL.put("SumOperationCharges", new BigDecimal(Double.parseDouble(mapLL.get("SumOperationCharges").toString())).setScale(3, BigDecimal.ROUND_HALF_UP));
                mapLL.put("MoreList", JSON.parseObject(JSON.toJSONString(listD), new TypeReference<List<Map>>() {
                }));
                mapLL2.put("More", JSON.parseObject(JSON.toJSONString(mapLL), new TypeReference<Map>() {
                }));
                listC.add(JSON.parseObject(JSON.toJSONString(mapLL2), new TypeReference<Map>() {
                }));
                listD.clear();
                mapLL.clear();
                mapLL2.clear();
            }
            map.get(strKey).put("BargainList", JSON.parseObject(JSON.toJSONString(listC), new TypeReference<List<Map>>() {
            }));
            if (Integer.valueOf(applicationProperty.getChinOrEng().toString()) == 1) {
                map.get(strKey).put("GuestName", map.get(strKey).get("GuestName").toString().substring(0, 3) + "****" + map.get(strKey).get("GuestName").toString().substring(map.get(strKey).get("GuestName").toString().length() - 4));
            }

            listC.clear();
            last.add(map.get(strKey));
        }
        /*
            排序
         */
        Collections.sort(last, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get("SumAll").toString());//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get("SumAll").toString()); //name1是从你list里面拿出来的第二个name
                return -(name1.compareTo(name2));
            }
        });


        return last;
    }
}
