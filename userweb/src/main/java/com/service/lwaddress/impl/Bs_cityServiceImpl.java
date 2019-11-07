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

   /* private static final Logger log = LoggerFactory.getLogger(Bs_utilServiceImpl.class);*/

    @Autowired
    Bs_cityMapper bs_cityMapper;

    @Override
    public Map cityJudge(String address,List<Bs_city> cityAllName) {
        String addressUse = null;
        Map<String,Object> cityMap = new HashMap<String, Object>();
        String cityName = null;//市名称
        String cityCode = null;//市编号
        String provincecodesec = null;
        Pattern pattern = null;
        Matcher matcher = null;
       if (address.length() <= 10) {
            addressUse = address;
       } else {
            addressUse = address.substring(0,10);
       }

       for (int i = 0; i < cityAllName.size();i++) {
            pattern = Pattern.compile(cityAllName.get(i).getCityName());
            matcher = pattern.matcher(addressUse);
            if (matcher.find()) {
//                cityName = matcher.group();
//                String[] split = Address.split(cityAllName.get(i).getCityName());
//                if(split.length>0){
//                    Address = split[split.length-1];
//                }else {
//                    Address = Address.replace(matcher.group(),"");
//                }
//                cityCode = cityAllName.get(i).getCityCode();
//                provincecodesec = cityAllName.get(i).getProvinceCode();
////                Address = Address.replace(matcher.group(),"");
//                break;
                cityName = matcher.group();
                cityCode = cityAllName.get(i).getCityCode();
                provincecodesec = cityAllName.get(i).getProvinceCode();
                address = address.replace(matcher.group(),"");
                break;
            } else {
                pattern = Pattern.compile(cityAllName.get(i).getShortName());
                matcher = pattern.matcher(addressUse);
                if (matcher.find()) {
                    address = address.replace(matcher.group(),"");
                    cityName = cityAllName.get(i).getCityName();;
                    cityCode = cityAllName.get(i).getCityCode();
                    provincecodesec = cityAllName.get(i).getProvinceCode();

                    break;
                } else {
                    continue;
                }
            }
       }

     /*  log.info("cityCode"+cityCode);
        log.info("cityName"+cityName);
        log.info("cityAddress"+Address);*/
       cityMap.put("address",address);
       cityMap.put("cityCode",cityCode);
       cityMap.put("provinceCodeSec",provincecodesec);
       cityMap.put("cityName",cityName);
       cityMap.put("cityAllName",cityAllName);

        return cityMap;
    }

}
