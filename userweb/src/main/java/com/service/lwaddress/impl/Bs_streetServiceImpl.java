package com.service.lwaddress.impl;

import com.dao.entity.lwaddress.BsCommunity;
import com.dao.entity.lwaddress.Bs_street;
import com.service.lwaddress.Bs_streetService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Bs_streetServiceImpl implements Bs_streetService {

    @Override
    public Map streetJudge(String address, List<Bs_street> streetAllName) {

        String addressUse;
        Map<String,Object> streetMap = new HashMap<String, Object>();
        String streetName = null;//区名称
        String streetCode = null;//区编号
        String areaCodeSec = null;

       if (address.length() <= 25) {
            addressUse = address;
       } else {
            addressUse = address.substring(0,25);
       }

        if(streetAllName.size() > 0) {
            for (int i = 0; i < streetAllName.size();i++) {
                Pattern pattern = Pattern.compile(streetAllName.get(i).getStreetName());
                Matcher matcher = pattern.matcher(addressUse);
                if (matcher.find()) {
                    streetName = matcher.group();
                    streetCode = streetAllName.get(i).getStreetCode();
                    address = address.replace(streetName,"");
                    areaCodeSec = streetAllName.get(i).getAreaCode();
                    break;
                }
            }
        }
        streetMap.put("streetName",streetName);
        streetMap.put("address",address);
        streetMap.put("streetCode",streetCode);
        streetMap.put("areaCodeSec",areaCodeSec);
        streetMap.put("streetAllName",streetAllName);
        return streetMap;
    }

    @Override
    public Map streetDecide(List<BsCommunity> communityMessage, String areaName,String address) {
        //社区名称
        Map<String,Object> streetMap = new HashMap<String, Object>();
        if(communityMessage.size() > 0) {
            //匹配社区
            for (int i = 0; i < communityMessage.size();i++) {
                Pattern pattern = Pattern.compile(communityMessage.get(i).getCommunity());
                Matcher matcher = pattern.matcher(address);
                //如果匹配到社区，再判断街道简称是否匹配
                if (matcher.find()) {
                    Pattern pattern1 = Pattern.compile(communityMessage.get(i).getStreetShort());
                    Matcher matcher1 = pattern1.matcher(address);
                    //如果都匹配
                    if (matcher1.find()) {
                        String streetShortName = matcher1.group();
                        address = address.replace(streetShortName,"");
                        String streetName = communityMessage.get(i).getStreet();
                        streetMap.put("streetName",streetName);
                        streetMap.put("address",address);
                        return streetMap;
                    }
                    //判断社区和区县是否匹配
                    if(communityMessage.get(i).getArea().equals(areaName)){
                        String streetName = communityMessage.get(i).getStreet();
                        streetMap.put("streetName",streetName);
                        streetMap.put("address",address);
                        return streetMap;
                    }
                }
            }
        }

        streetMap.put("address",address);
        streetMap.put("areaName",areaName);
        return streetMap;
    }

}
