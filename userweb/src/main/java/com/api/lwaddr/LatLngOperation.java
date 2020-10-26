package com.api.lwaddr;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dao.db2.lwaddress.Base_addrMapper;
import com.dao.entity.lwaddress.Base_addr;
import com.utils.sys.HttpUtils;
import com.utils.sys.TextUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class LatLngOperation implements Job {
    private static final String URL = "http://41.188.33.137:8080/PADD_S_Geocode//addressService";
    private static final String TYPE = "Geocode";
    private static final String MODE = "single";
    @Autowired
    private Base_addrMapper base_addrMapper;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //查询指定步进值的地址信息,p5type不等于3，并且经纬度不为空的数据
        List<Base_addr> date = base_addrMapper.getAddress();

        if(TextUtils.isEmpty(date)){
            return;
        }

        for (Base_addr base_addr : date) {
            String address = base_addr.getAddrSj();

            Map paramsMap = new LinkedHashMap<String, String>();
            paramsMap.put("type", TYPE);
            paramsMap.put("mode", MODE);
            paramsMap.put("address", address);

            String result = HttpUtils.URLGet(URL, paramsMap, "UTF-8");

            //解析返回的xml格式的字符串result，从中拿到经纬度
            //调用高德API，拿到json格式的字符串结果
            JSONObject jsonObject = JSON.parseObject(result);

            //拿到返回报文的success值，高德的该接口返回值有两个：false-请求失败，true-请求成功；
            Boolean status = Boolean.valueOf(jsonObject.getString("success"));

            if (!status) {
                base_addr.setP5type(base_addr.getP5type() + 1);
                continue;
            }

            try {
                JSONArray jsonArray = jsonObject.getJSONArray("content");
                if (jsonArray.size() > 0) {
                    JSONObject json = jsonArray.getJSONObject(0);
//                  //标准化地址  比如：/浙江省/温州市/瓯海区/仙岩街道/新街/2号
//                  String addressPath = json.getString("addressPath");
//                  //标准地址编码  比如：330304201401000581000002
//                  String geocode = json.getString("geocode");
                    BigDecimal longitude = (BigDecimal) json.get("x");
                    BigDecimal latitude = (BigDecimal) json.get("y");
                    base_addr.setLongitude(longitude);
                    base_addr.setLatitude(latitude);
                }
            } catch (Exception ex) {
                base_addr.setP5type(base_addr.getP5type() + 1);
            }
        }

        //批量更新经纬度
        base_addrMapper.updateBatchByIds(date);
    }
}
