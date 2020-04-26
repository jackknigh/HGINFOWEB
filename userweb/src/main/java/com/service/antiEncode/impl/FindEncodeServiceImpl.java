package com.service.antiEncode.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dao.entity.lwaddress.Sec_addr;
import com.service.antiEncode.FindEncodeService;
import com.utils.sys.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Transactional
@Service("antiEncodeService")
public class FindEncodeServiceImpl implements FindEncodeService {
    private static final String OUTPUT = "JSON";
    private Logger log = LoggerFactory.getLogger(FindEncodeService.class);

    /**
     * @param
     * @return Pair<BigDecimal, BigDecimal> 左节点值为经度，右节点值为纬度
     * @description 根据传进来的一个地址，查询对应的经纬度
     * @author quyl
     * @date 2019年6月10日
     */
    @Override
    public Sec_addr getLngLatFromOneAddr(Sec_addr sec_addr, String key, String url) {
        Map<String, String> params = new HashMap<String, String>();
        String address = sec_addr.getAddrSj();
        String[] address3 = address.split("%7C");
        if (address3.length > 1) {
            String address4 = "";
            for (String add : address3) {
                address4 = address4.concat(add);
            }
            params.put("address", address4);
        } else {
            params.put("address", address);
        }
        params.put("output", OUTPUT);
        params.put("key", key);

        String result = HttpUtils.URLPost(url, params, "UTF-8");
        List<BigDecimal> list = new ArrayList();

        // 解析返回的xml格式的字符串result，从中拿到经纬度
        // 调用高德API，拿到json格式的字符串结果
        JSONObject jsonObject = JSON.parseObject(result);

        // 拿到返回报文的status值，高德的该接口返回值有两个：0-请求失败，1-请求成功；
        int status = Integer.valueOf(jsonObject.getString("status"));

        if (status == 1) {
            int counts = Integer.valueOf(jsonObject.getString("count"));
            if (counts == 0) {
                sec_addr.setType(Integer.valueOf(127));
                return sec_addr;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("geocodes");
            JSONObject json = jsonArray.getJSONObject(0);
            String lngLat = json.getString("location");

            String[] lngLatArr = lngLat.split(",");
            if (lngLatArr.length == 1) {
                /*125为发起请求但是未收到*/
                sec_addr.setType(Integer.valueOf(125));
                return sec_addr;
            }
            // 经度
            BigDecimal longitude = new BigDecimal(lngLatArr[0]);
            // 纬度
            BigDecimal latitude = new BigDecimal(lngLatArr[1]);
            list.add(longitude);
            list.add(latitude);
            sec_addr.setLongitude(list.get(0));
            sec_addr.setLatitude(list.get(1));
            return sec_addr;
        }
        /*type=127为未返回结果
         * type=126为请求失败*/
        else {
            sec_addr.setType(Integer.valueOf(126));
            return sec_addr;
        }
    }
}

