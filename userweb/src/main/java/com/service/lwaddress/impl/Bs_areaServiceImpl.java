package com.service.lwaddress.impl;

import com.dao.db2.lwaddress.Bs_areaMapper;
import com.dao.entity.lwaddress.Bs_area;
import com.service.lwaddress.Bs_areaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Bs_areaServiceImpl implements Bs_areaService {

    @Autowired
    Bs_areaMapper bs_areaMapper;

    @Override
    public Map areaJudge(String address, List<Bs_area> areaAllName ) {
        String addressUse;
        Map<String,Object> areaMap = new HashMap<String, Object>();
        String areaName = null;//区名称
        String areaCode = null;//区编号
        String cityCode = null;

       if (address.length() <= 15) {
            addressUse = address;
        } else {
            addressUse = address.substring(0,15);
        }

       for (int i = 0; i < areaAllName.size();i++) {
           Pattern pattern = Pattern.compile(areaAllName.get(i).getAreaName());
           Matcher matcher = pattern.matcher(addressUse);
            if (matcher.find()) {
                areaName = matcher.group();
                areaCode = areaAllName.get(i).getAreaCode();
                cityCode = areaAllName.get(i).getCityCode();
                address = address.replace(areaName,"");
                break;
            } else {
                Pattern pattern1 = Pattern.compile(areaAllName.get(i).getShortName());
                Matcher matcher1 = pattern1.matcher(addressUse);
                if (matcher1.find()) {
                    address = address.replace(matcher1.group(),"");
                    areaName = areaAllName.get(i).getAreaName();
                    areaCode = areaAllName.get(i).getAreaCode();
                    cityCode = areaAllName.get(i).getCityCode();
                    break;
                }
            }
        }
        areaMap.put("areaCode",areaCode);
        areaMap.put("address",address);
        areaMap.put("areaName",areaName);
        areaMap.put("areaAllName",areaAllName);
        areaMap.put("cityCodeSec",cityCode);
        return areaMap;
    }
}
