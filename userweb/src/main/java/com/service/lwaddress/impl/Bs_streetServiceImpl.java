package com.service.lwaddress.impl;

import com.dao.db2.lwaddress.Bs_areaMapper;
import com.dao.db2.lwaddress.Bs_streetMapper;
import com.dao.entity.lwaddress.Bs_street;
import com.service.lwaddress.Bs_streetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Bs_streetServiceImpl implements Bs_streetService {
    /*private static final Logger log = LoggerFactory.getLogger(Bs_utilServiceImpl.class);*/

    @Autowired
    Bs_streetMapper bs_streetMapper;

    @Autowired
    Bs_areaMapper bs_areaMapper;

    @Override
    public Map streetJudge(String address, List<Bs_street> streetAllName) {

        String addressUse = null;
        Map<String,Object> streetMap = new HashMap<String, Object>();
        String streetName = null;//区名称
        String streetCode = null;//区编号
        String areaCodeSec = null;
        Pattern pattern = null;
        Matcher matcher = null;

       if (address.length() <= 25) {
            addressUse = address;
       } else {
            addressUse = address.substring(0,25);
       }

        if(streetAllName.size() > 0) {
            for (int i = 0; i < streetAllName.size();i++) {
                pattern = Pattern.compile(streetAllName.get(i).getStreetName());
                matcher = pattern.matcher(addressUse);
                if (matcher.find()) {
                    streetName = matcher.group();
                    streetCode = streetAllName.get(i).getStreetCode();
                    address = address.replace(streetName,"");
                    areaCodeSec = streetAllName.get(i).getAreaCode();
                    break;
                } else {
                    pattern = Pattern.compile(streetAllName.get(i).getShortName());
                    matcher = pattern.matcher(addressUse);
                    if (matcher.find()) {
                        address = address.replace(matcher.group(),"");
                        streetName = streetAllName.get(i).getStreetName();
                        streetCode = streetAllName.get(i).getStreetCode();
                        areaCodeSec = streetAllName.get(i).getAreaCode();

                        break;
                    } else {
                        continue;
                    }
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

}
