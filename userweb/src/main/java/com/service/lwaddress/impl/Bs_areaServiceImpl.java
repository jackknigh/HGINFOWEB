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
    //private static final Logger log = LoggerFactory.getLogger(Bs_utilServiceImpl.class);

    @Autowired
    Bs_areaMapper bs_areaMapper;
   /* @Autowired
    Bs_streetService bs_streetService;*/

    @Override
    //public Map areaJudge(String Address, String cityCode, List<String> list,String provinceCode) {
    public Map areaJudge(String address, List<Bs_area> areaAllName ) {


        String addressUse = null;

        Map<String,Object> areaMap = new HashMap<String, Object>();
        String areaName = null;//区名称
        String areaCode = null;//区编号
        String cityCode = null;
        Pattern pattern = null;
        Matcher matcher = null;

       if (address.length() <= 15) {
            addressUse = address;
        } else {
            addressUse = address.substring(0,15);
        }

       for (int i = 0; i < areaAllName.size();i++) {
            pattern = Pattern.compile(areaAllName.get(i).getAreaName());
            matcher = pattern.matcher(addressUse);
            if (matcher.find()) {
//                String[] split = Address.split(areaAllName.get(i).getAreaName());
//                areaName = matcher.group();
//                if(split.length>0) {
//                    Address = split[split.length - 1];
//                }else {
//                    Address = Address.replace(areaName,"");
//                }
//                areaCode = areaAllName.get(i).getAreaCode();
//                cityCode = areaAllName.get(i).getCityCode();
////                Address = Address.replace(areaName,"");
//                break;
                areaName = matcher.group();
                areaCode = areaAllName.get(i).getAreaCode();
                cityCode = areaAllName.get(i).getCityCode();
                address = address.replace(areaName,"");
                break;
            } else {
                pattern = Pattern.compile(areaAllName.get(i).getShortName());
                matcher = pattern.matcher(addressUse);
                if (matcher.find()) {
                    address = address.replace(matcher.group(),"");
                    areaName = areaAllName.get(i).getAreaName();
                    areaCode = areaAllName.get(i).getAreaCode();
                    cityCode = areaAllName.get(i).getCityCode();

                    break;
                } else {
                    continue;
                }
            }
        }
        /*log.info("areaCode"+areaCode);
        log.info("areaName"+areaName);
        log.info("areaAddress"+Address);
*/
        areaMap.put("areaCode",areaCode);
        areaMap.put("address",address);
        areaMap.put("areaName",areaName);
        areaMap.put("areaAllName",areaAllName);
        areaMap.put("cityCodeSec",cityCode);

        return areaMap;
    }

}
