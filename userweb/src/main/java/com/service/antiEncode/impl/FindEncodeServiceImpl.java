package com.service.antiEncode.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dao.entity.lwaddress.Sec_addr;
import com.service.antiEncode.FindEncodeService;
import com.utils.sys.DateUtil;
import com.utils.sys.HttpUtils;
import com.utils.sys.SnCal;
import com.utils.sys.lwaddress.LngLonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;


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
            String level = json.getString("level");
            if("省".equals(level)||"市".equals(level)||"区县".equals(level)){
                sec_addr.setType(Integer.valueOf(129));
                return sec_addr;
            }
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
            //todo 经纬度装换 高德--->84
//            double[] doubles = LngLonUtil.gcj02_To_Gps84(latitude.doubleValue(), longitude.doubleValue());
//            sec_addr.setLongitude(new BigDecimal(doubles[1]));
//            sec_addr.setLatitude(new BigDecimal(doubles[0]));
            sec_addr.setLongitude(longitude);
            sec_addr.setLatitude(latitude);
            return sec_addr;
        }
        /*type=127为未返回结果
         * type=126为请求失败*/
        else {
            sec_addr.setType(Integer.valueOf(126));
            return sec_addr;
        }
    }

    @Override
    public Sec_addr getAddr(Sec_addr secAddr, String key, String url) {
        String address = secAddr.getAddrJj();
        String result = HttpUtils.URLGet1(url, address,key, "UTF-8");

        // 解析返回的xml格式的字符串result，从中拿到经纬度
        // 调用高德API，拿到json格式的字符串结果
        JSONObject jsonObject = JSON.parseObject(result);

        // 拿到返回报文的status值，高德的该接口返回值有两个：0-请求失败，1-请求成功；
        int status = Integer.valueOf(jsonObject.getString("status"));

        if (status == 1) {
            try {
            JSONObject regeocode = jsonObject.getJSONObject("regeocode");
            JSONObject addressComponent = regeocode.getJSONObject("addressComponent");
            String province = (String) addressComponent.get("province");
            if(!"[]".equals(addressComponent.get("city").toString())) {
                String city = (String) addressComponent.get("city");
                secAddr.setCity(city);
            }
            String district = (String) addressComponent.get("district");
            if(!"[]".equals(addressComponent.get("township").toString())){
            String township = (String) addressComponent.get("township");
            secAddr.setStreet(township);
            }
            secAddr.setProvince(province);
            secAddr.setArea(district);
            secAddr.setType(127);
            }catch (Exception ex){
                log.error(jsonObject.toJSONString()+"发生异常！！！！");
                return null;
            }
            return secAddr;
        }
        /*type=127为未返回结果
         * type=126为请求失败*/
        else {
            secAddr.setType(Integer.valueOf(126));
            return secAddr;
        }
    }

    @Override
    public Sec_addr getAddrByBD(Sec_addr secAddr, String keyAk,String keySk, String url) {
        String address = secAddr.getAddrSj();

        Map paramsMap = new LinkedHashMap<String, String>();
//        paramsMap.put("query", address);
//        paramsMap.put("region","浙江省");
//        paramsMap.put("radius_limit","true");
//        paramsMap.put("output", "json");
//        paramsMap.put("ak",keyAk);

        paramsMap.put("address", "浙江省"+address);
        paramsMap.put("ak",keyAk);
        paramsMap.put("output", "json");

        String result = HttpUtils.URLGet(url,paramsMap, "UTF-8");

        // 解析返回的xml格式的字符串result，从中拿到经纬度
        // 调用高德API，拿到json格式的字符串结果
        JSONObject jsonObject = JSON.parseObject(result);

        // 拿到返回报文的status值，高德的该接口返回值有两个：0-请求失败，1-请求成功；
        int status = Integer.valueOf(jsonObject.getString("status"));

        if (status == 0) {
            try {
//                JSONArray jsonArray = jsonObject.getJSONArray("results");
//                JSONObject json = jsonArray.getJSONObject(0);
//                JSONObject location = json.getJSONObject("location");
//                // 经度
//                BigDecimal latitude = (BigDecimal) location.get("lat");
//                // 纬度
//                BigDecimal longitude = (BigDecimal) location.get("lng");
                JSONObject results = jsonObject.getJSONObject("result");
                JSONObject location = results.getJSONObject("location");
                // 经度
                BigDecimal longitude = (BigDecimal) location.get("lng");
                // 纬度
                BigDecimal latitude = (BigDecimal) location.get("lat");

                secAddr.setLatitude(latitude);
                secAddr.setLongitude(longitude);

                secAddr.setLatitude(latitude);
                secAddr.setLongitude(longitude);
            }catch (Exception ex){
                log.error(jsonObject.toJSONString()+"发生异常！！！！");
                return null;
            }
            return secAddr;
        }
        /*type=127为未返回结果
         * type=126为请求失败*/
        else {
            secAddr.setType(Integer.valueOf(126));
            return secAddr;
        }
    }

    @Override
    public Sec_addr getAddrBDD(Sec_addr secAddr, String keyAk, String keyValueSK, String url) {
        String address = secAddr.getAddrJj();

        Map paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("location", address);
        paramsMap.put("ak", keyAk);
        paramsMap.put("output", "json");
        paramsMap.put("extensions_road", "true");
        paramsMap.put("extensions_town", "true");

        String result = HttpUtils.URLGet(url, paramsMap, "UTF-8");

        // 解析返回的xml格式的字符串result，从中拿到经纬度
        // 调用高德API，拿到json格式的字符串结果
        JSONObject jsonObject = JSON.parseObject(result);

        // 拿到返回报文的status值，高德的该接口返回值有两个：0-请求失败，1-请求成功；
        int status = Integer.valueOf(jsonObject.getString("status"));

        if (status == 0) {
            try {
                JSONObject results = jsonObject.getJSONObject("result");
                JSONObject addressComponent = results.getJSONObject("addressComponent");

                // 经度
                String province = (String) addressComponent.get("province");
                String city = (String) addressComponent.get("city");
                String district = (String) addressComponent.get("district");
                String street = (String) addressComponent.get("town");

                secAddr.setProvince(province);
                secAddr.setCity(city);
                secAddr.setArea(district);
                secAddr.setStreet(street);
            } catch (Exception ex) {
                log.error(jsonObject.toJSONString() + "发生异常！！！！");
                return null;
            }
            return secAddr;
        }
        /*type=127为未返回结果
         * type=126为请求失败*/
        else {
            secAddr.setType(Integer.valueOf(126));
            return secAddr;
        }
    }
}

