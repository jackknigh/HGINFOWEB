package com.service.lwaddress.impl;

import com.dao.db2.lwaddress.Bs_provinceMapper;
import com.dao.entity.lwaddress.Bs_city;
import com.dao.entity.lwaddress.Bs_province;
import com.service.lwaddress.Bs_provinceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Bs_provinceServiceImpl implements Bs_provinceService {
    private static final Logger log = LoggerFactory.getLogger(Bs_utilServiceImpl.class);
    @Autowired
    Bs_provinceMapper bs_provinceMapper;

    @Override
    public Map provinceJudge( String address,List<Bs_province> provinceAllName) {


        String addressUse;
        String firstAddress = address;
        String provinceName = null;//省名称
        String provinceCode = null;//省编号
        Pattern pattern = null;
        Matcher matcher = null;
        Map<String,Object> provinceMap = new HashMap<String, Object>();
        //如果开头是中国，就把中国去了
        if (address.startsWith("中国")) {
            address = address.replace("中国","");
        }

        //如果全地址大于9，就取前9位
        if (address.length() <= 13) {
            addressUse = address;
        } else {
            addressUse = address.substring(0,9);
        }

        //遍历存了省名称的集合
        for (int i = 0; i < provinceAllName.size(); i++) {
            //将地址和标准地址进行比较，如果比较成功就将地址中去掉这个省
            pattern = Pattern.compile(provinceAllName.get(i).getProvinceName());
            matcher = pattern.matcher(addressUse);
            if (matcher.find()) {
                provinceName = matcher.group();
                provinceCode = provinceAllName.get(i).getProvinceCode();
                address = address.replace(provinceName,"");
                break;
            } else {
                //如果比较不成功就比较短地址，如果比较成功就将短地址改为标准地址并地址中去掉这个短地址
                pattern = Pattern.compile(provinceAllName.get(i).getShortName());
                matcher = pattern.matcher(addressUse);
                if (matcher.find()) {
                    provinceName = provinceAllName.get(i).getProvinceName();
                    provinceCode = provinceAllName.get(i).getProvinceCode();
                    address = address.replace(matcher.group(),"");
                    break;
                } else {
                    continue;
                }
            }
        }
       //地址名
        provinceMap.put("firstAddress",firstAddress);
        //省名
        provinceMap.put("provinceName",provinceName);
        //匹配到的话就是去掉省名后的地址，匹配不到的话就是地址名
        provinceMap.put("address",address);
        //省编码
        provinceMap.put("provinceCode",provinceCode);
        //标准省集合
        provinceMap.put("provinceAllName",provinceAllName);
        return provinceMap;
    }
}
