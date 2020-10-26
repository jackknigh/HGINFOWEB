package com.service.lwaddress.impl;

import com.dao.db2.lwaddress.Bs_cityMapper;
import com.dao.entity.lwaddress.Bs_city;
import com.service.lwaddress.Bs_cityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Bs_cityServiceImpl implements Bs_cityService {

    @Autowired
    Bs_cityMapper bs_cityMapper;

    @Override
    public Map cityJudge(String address,List<Bs_city> cityAllName) {
        String addressUse;
        Map<String,Object> cityMap = new HashMap<String, Object>();
        String cityName = null;//市名称
        String cityCode = null;//市编号
        String provincecodesec = null;
       if (address.length() <= 10) {
            addressUse = address;
       } else {
            addressUse = address.substring(0,10);
       }

       for (int i = 0; i < cityAllName.size();i++) {
           Pattern pattern = Pattern.compile(cityAllName.get(i).getCityName());
           Matcher matcher = pattern.matcher(addressUse);
            if (matcher.find()) {
                cityName = matcher.group();
                cityCode = cityAllName.get(i).getCityCode();
                provincecodesec = cityAllName.get(i).getProvinceCode();
                address = address.replace(matcher.group(),"");
                break;
            } else {
                Pattern pattern1 = Pattern.compile(cityAllName.get(i).getShortName());
                Matcher matcher1 = pattern1.matcher(addressUse);
                if (matcher1.find()) {
                    address = address.replace(matcher1.group(),"");
                    cityName = cityAllName.get(i).getCityName();
                    cityCode = cityAllName.get(i).getCityCode();
                    provincecodesec = cityAllName.get(i).getProvinceCode();
                    break;
                }
            }
       }

       cityMap.put("address",address);
       cityMap.put("cityCode",cityCode);
       cityMap.put("provinceCodeSec",provincecodesec);
       cityMap.put("cityName",cityName);
       cityMap.put("cityAllName",cityAllName);
        return cityMap;
    }
}
