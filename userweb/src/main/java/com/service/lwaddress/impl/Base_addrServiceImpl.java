package com.service.lwaddress.impl;

import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.*;
import com.dao.entity.lwaddress.*;
import com.service.lwaddress.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Base_addrServiceImpl implements Base_addrService {

    @Autowired
    Base_addrMapper base_addrMapper;
    @Autowired
    Bs_cityMapper bs_cityMapper;
    @Autowired
    Bs_areaMapper bs_areaMapper;
    @Autowired
    Bs_streetMapper bs_streetMapper;
    @Autowired
    Bs_provinceMapper bs_provinceMapper;

    @Autowired
    private HgApplicationProperty applicationProperty;

    @Autowired
    Bs_cityService bs_cityService;
    @Autowired
    Bs_areaService bs_areaService;
    @Autowired
    Bs_streetService bs_streetService;
    @Autowired
    Bs_provinceService bs_provinceService;

    @Override
    public Base_addr addrSet(String address, Map<String, Object> allMessage) {
        String name1 = "";
        String name2 = "";
        Base_addr bs_addr = new Base_addr();
        bs_addr.setP1type(0);
        Map<String, Object> provinceMap;
        Map<String, Object> cityMap;
        Map<String, Object> areaMap;
        Map<String, Object> streetMap;

        /*BigDecimal dec1 = new BigDecimal(-5);
        BigDecimal dec2 = new BigDecimal(-2);
        BigDecimal dec3 = new BigDecimal(-1);
        BigDecimal dec4 = new BigDecimal(0);
        BigDecimal dec5 = new BigDecimal(100);
        BigDecimal dec6 = new BigDecimal(0);
        BigDecimal dec7 = new BigDecimal(-20);
        BigDecimal dec8 = new BigDecimal(-10);*/
        BigDecimal dec6 = new BigDecimal(0);
        List<Bs_city> listCity = new ArrayList<>();
        List<Bs_area> listArea = new ArrayList<>();
        List<Bs_street> listStreet = new ArrayList<>();

        //对省的操作
        provinceMap = bs_provinceService.provinceJudge(address, (List<Bs_province>) allMessage.get("provinceMessage"));
        //如果有匹配到省
        if ((String) provinceMap.get("provinceCode") != null) {
            //遍历所有城市
            for (int i = 0; i < ((List<Bs_city>) allMessage.get("cityMessage")).size(); i++) {
                //如果城市的省编码等于匹配到的省编码
                if (((List<Bs_city>) allMessage.get("cityMessage")).get(i).getProvinceCode().equals((String) provinceMap.get("provinceCode"))) {
                    //将该城市存进城市集合
                    listCity.add(((List<Bs_city>) allMessage.get("cityMessage")).get(i));
                }
            }
        } else {
            //没有匹配到省就将所有城市存进城市集合
            listCity = (List<Bs_city>) allMessage.get("cityMessage");
        }
        //对城市的操作
        cityMap = bs_cityService.cityJudge((String) provinceMap.get("Address"), listCity);
        //如果有匹配到城市
        if ((String) cityMap.get("cityCode") != null) {
            //遍历所有区域
            for (int i = 0; i < ((List<Bs_area>) allMessage.get("areaMessage")).size(); i++) {
                //找到区域的城市编码等于匹配到的城市编码的数据存进区域集合
                if (((List<Bs_area>) allMessage.get("areaMessage")).get(i).getCityCode().equals((String) cityMap.get("cityCode"))) {
                    listArea.add(((List<Bs_area>) allMessage.get("areaMessage")).get(i));
                }
            }
        } else if ((String) cityMap.get("cityCode") == null && (String) provinceMap.get("provinceCode") != null) {
            //如果城市编码为空和省编码不为空
            for (int i = 0; i < ((List<Bs_area>) allMessage.get("areaMessage")).size(); i++) {
                if (((List<Bs_area>) allMessage.get("areaMessage")).get(i).getCityCode().equals((String) provinceMap.get("provinceCode"))) {
                    listArea.add(((List<Bs_area>) allMessage.get("areaMessage")).get(i));
                }
            }
        } else if ((String) cityMap.get("cityCode") == null && (String) provinceMap.get("provinceCode") == null) {
            //如果省市的编码都为空就将标准的区域集合存进区域集合
            listArea = (List<Bs_area>) allMessage.get("areaMessage");
        }
        //对区域的操作(这里的listArea可能是前面赋值的区域)
        areaMap = bs_areaService.areaJudge((String) cityMap.get("Address"), listArea);
        //如果没匹配到区域
        if ((String) areaMap.get("areaCode") == null) {
            //如果城市和省都不为空
            if ((String) cityMap.get("cityCode") != null || (String) provinceMap.get("provinceCode") != null) {
                listArea = (List<Bs_area>) allMessage.get("areaMessage");
                //就与标准区域重新匹配一次
                areaMap = bs_areaService.areaJudge((String) cityMap.get("Address"), listArea);
            }
        }

        //如果匹配到了区域
        if ((String) areaMap.get("areaCode") != null) {
            for (int i = 0; i < ((List<Bs_street>) allMessage.get("streetMessage")).size(); i++) {
                //如果有街道的区域编号等于匹配到的区域编号，就将街道信息存进街道集合
                if (((List<Bs_street>) allMessage.get("streetMessage")).get(i).getAreaCode().equals((String) areaMap.get("areaCode"))) {
                    listStreet.add(((List<Bs_street>) allMessage.get("streetMessage")).get(i));
                }
            }
        } else if ((String) areaMap.get("areaCode") == null && (String) cityMap.get("cityCode") != null) {
            //如果区域为空，城市不为空
            for (int i = 0; i < ((List<Bs_street>) allMessage.get("streetMessage")).size(); i++) {
                if (((List<Bs_street>) allMessage.get("streetMessage")).get(i).getAreaCode().equals((String) cityMap.get("cityCode"))) {
                    listStreet.add(((List<Bs_street>) allMessage.get("streetMessage")).get(i));
                }
            }
        } else if ((String) areaMap.get("areaCode") == null && (String) cityMap.get("cityCode") == null) {
            //如果区域编码和城市编码都为空
            for (int i = 0; i < ((List<Bs_street>) allMessage.get("streetMessage")).size(); i++) {
                if (((List<Bs_street>) allMessage.get("streetMessage")).get(i).getAreaCode().substring(0,4).equals("3303")) {
                    listStreet.add(((List<Bs_street>) allMessage.get("streetMessage")).get(i));
                }
            }
        }
        streetMap = bs_streetService.streetJudge((String) areaMap.get("Address"), listStreet);

        if (StringUtils.isBlank((String) streetMap.get("streeCode"))) {
            for (Bs_street streetMessage : ((List<Bs_street>) allMessage.get("streetMessage"))) {
                listStreet.add(streetMessage);
            }
            streetMap = bs_streetService.streetJudge((String) areaMap.get("Address"), listStreet);
        }

        //如果城市区域街道都为空就标记为-100
        if ((String) streetMap.get("streetCode") == null && (String) areaMap.get("areaCode") == null && (String) cityMap.get("cityCode") == null) {
            if (applicationProperty.getOpenOrNot().equals("1")) {
                listStreet = (List<Bs_street>) allMessage.get("streetMessage");
                streetMap = bs_streetService.streetJudge((String) areaMap.get("Address"), listStreet);
            }
            bs_addr.setP1type(-100);
        }

        /*
         * 对信息的标记，1表示存在，0表示不存在
         */

//
//        BigDecimal dec1 = new BigDecimal(-5);
//        BigDecimal dec2 = new BigDecimal(-2);
//        BigDecimal dec3 = new BigDecimal(-1);
//        BigDecimal dec4 = new BigDecimal(0);
//        BigDecimal dec5 = new BigDecimal(100);
//        BigDecimal dec6 = new BigDecimal(0);
//        BigDecimal dec7 = new BigDecimal(-20);
//        BigDecimal dec8 = new BigDecimal(-10);
        //开始算分
        if (provinceMap.get("provinceName") != null) {
            bs_addr.setProWeight((BigDecimal) allMessage.get("dec4"));
            name1 = name1 + "1";
        } else {
            bs_addr.setProWeight((BigDecimal) allMessage.get("dec3"));
            name1 = name1 + "0";
        }
        if ((String) cityMap.get("cityName") != null) {
            bs_addr.setCityWeight((BigDecimal) allMessage.get("dec4"));
            name1 = name1 + "1";
        } else {
            bs_addr.setCityWeight((BigDecimal) allMessage.get("dec1"));
            name1 = name1 + "0";
        }
        if ((String) areaMap.get("areaName") != null) {
            bs_addr.setAreaWeight((BigDecimal) allMessage.get("dec4"));
            name1 = name1 + "1";
        } else {
            bs_addr.setAreaWeight((BigDecimal) allMessage.get("dec2"));
            name1 = name1 + "0";
        }
        if (streetMap.get("streetName") != null) {
            bs_addr.setStreWeight((BigDecimal) allMessage.get("dec4"));
            name1 = name1 + "1";
        } else {
            bs_addr.setStreWeight((BigDecimal) allMessage.get("dec3"));
            name1 = name1 + "0";
        }
        if (bs_addr.getShortAddr() != null) {
            name1 = name1 + "1";
        } else {
            name1 = name1 + "0";
        }

        //最终得分
        dec6 = ((BigDecimal) allMessage.get("dec5")).add(bs_addr.getAreaWeight().add(bs_addr.getCityWeight().add(bs_addr.getProWeight().add(bs_addr.getStreWeight()))));

        /*
         * 通过已知的信息反推未知的省市区街道信息
         */

        if (name1.startsWith("101") || name1.startsWith("011") || name1.startsWith("1101")) {
            if ((String) streetMap.get("streetCode") != null && (String) areaMap.get("areaCode") == null) {
                areaMap.put("areaCode", (((String) streetMap.get("areaCodeSec"))));
            } else if ((String) cityMap.get("cityCode") == null && (String) areaMap.get("areaCode") != null) {
                cityMap.put("cityCode", ((String) areaMap.get("cityCodeSec")));
            } else if ((String) cityMap.get("cityCode") != null && (String) provinceMap.get("provinceCode") == null) {
                provinceMap.put("provinceCode", ((String) cityMap.get("provinceCodeSec")));
            }
        } else {
            if ((String) streetMap.get("streetCode") != null && (String) areaMap.get("areaCode") == null) {
                for (int i = 0; i < ((List<Bs_area>) areaMap.get("areaAllName")).size(); i++) {
                    String areacode = (String) streetMap.get("areaCodeSec");
                    if (((List<Bs_area>) areaMap.get("areaAllName")).get(i).getAreaCode().equals(areacode)) {
                        areaMap.put("areaName", ((List<Bs_area>) areaMap.get("areaAllName")).get(i).getAreaName());
                        areaMap.put("areaCode", ((List<Bs_area>) areaMap.get("areaAllName")).get(i).getAreaCode());
                        areaMap.put("cityCodeSec", ((List<Bs_area>) areaMap.get("areaAllName")).get(i).getCityCode());
                    }

                }
            }
            if ((String) cityMap.get("cityCode") == null && (String) areaMap.get("areaCode") != null) {

                for (int i = 0; i < ((List<Bs_city>) cityMap.get("cityAllName")).size(); i++) {
                    String citycode = (String) areaMap.get("cityCodeSec");
                    if (((List<Bs_city>) cityMap.get("cityAllName")).get(i).getCityCode().equals(citycode)) {

                        cityMap.put("cityName", ((List<Bs_city>) cityMap.get("cityAllName")).get(i).getCityName());
                        cityMap.put("cityCode", ((List<Bs_city>) cityMap.get("cityAllName")).get(i).getCityCode());
                        cityMap.put("provinceCodeSec", ((List<Bs_city>) cityMap.get("cityAllName")).get(i).getProvinceCode());
                    }
                }
            }

            if ((String) cityMap.get("cityCode") != null && (String) provinceMap.get("provinceCode") == null) {
                String provincecode = (String) cityMap.get("provinceCodeSec");

                for (int i = 0; i < ((List<Bs_province>) provinceMap.get("provinceAllName")).size(); i++) {
                    if ((((List<Bs_province>) provinceMap.get("provinceAllName")).get(i).getProvinceCode()).equals(provincecode)) {
                        // bs_addr.setProvince(((List<Bs_province>)provinceMap.get("provinceAllName")).get(i).getProvinceName());
                        provinceMap.put("provinceName", ((List<Bs_province>) provinceMap.get("provinceAllName")).get(i).getProvinceName());
                        provinceMap.put("provinceCode", ((List<Bs_province>) provinceMap.get("provinceAllName")).get(i).getProvinceCode());
                    }
                }
            }
        }

        /*
         * 判断是否错误，1表示正确，0表示错误,2表示不存在
         */
        if ((String) areaMap.get("areaCode") != null && (String) streetMap.get("areaCodeSec") != null) {
            if (((String) areaMap.get("areaCode")).equals((String) streetMap.get("areaCodeSec"))) {
                name2 = name2 + "1";
            } else {
                name2 = name2 + "0";
                dec6 = dec6.add((BigDecimal) allMessage.get("dec8"));
            }
        } else {
            name2 = name2 + "2";
        }
        if ((String) cityMap.get("cityCode") != null && (String) areaMap.get("cityCodeSec") != null) {
            if (((String) cityMap.get("cityCode")).equals((String) areaMap.get("cityCodeSec"))) {
                name2 = name2 + "1";
            } else {
                name2 = name2 + "0";
                dec6 = dec6.add((BigDecimal) allMessage.get("dec7"));
            }
        } else {
            name2 = name2 + "2";
        }
        if ((String) provinceMap.get("provinceCode") != null && (String) cityMap.get("provinceCodeSec") != null) {
            if (((String) provinceMap.get("provinceCode")).equals((String) cityMap.get("provinceCodeSec"))) {
                name2 = name2 + "1";
            } else {
                name2 = name2 + "0";
                dec6 = (BigDecimal) allMessage.get("dec4");
            }
        } else {
            name2 = name2 + "2";
        }
        if (((String) provinceMap.get("provinceName")) != null) {
            bs_addr.setProvince((String) provinceMap.get("provinceName"));
        } else {
            //bs_addr.setProvince((String) provinceMap.get("provinceCode"));
            for (int i = 0; i < ((List<Bs_province>) allMessage.get("provinceMessage")).size(); i++) {
                if (((List<Bs_province>) allMessage.get("provinceMessage")).get(i).getProvinceCode().equals((String) provinceMap.get("provinceCode"))) {
                    bs_addr.setProvince(((List<Bs_province>) allMessage.get("provinceMessage")).get(i).getProvinceName());
                }
            }
        }

        if ((String) cityMap.get("cityCode") == null) {
            bs_addr.setCity((String) cityMap.get("cityCode"));
        } else {
            bs_addr.setCity((String) cityMap.get("cityName"));
        }
        bs_addr.setArea((String) areaMap.get("areaName"));
        bs_addr.setStreet((String) streetMap.get("streetName"));
        bs_addr.setShortAddr((String) streetMap.get("Address"));
        if (((String) ((String) streetMap.get("Address"))).length() < 4 || ((String) ((String) streetMap.get("Address"))).length() > 30) {
            bs_addr.setMulWeight(dec6.multiply(new BigDecimal(0.5)));
        } else {
            bs_addr.setMulWeight(dec6);

        }
        bs_addr.setAddrSign1(name1);
        bs_addr.setAddrSign2(name2);
        bs_addr.setAddrSj(address);
        if (bs_addr.getAreaWeight() == null) {
            bs_addr.setAreaWeight(BigDecimal.ZERO);
        }
        if (bs_addr.getCityWeight() == null) {
            bs_addr.setCityWeight(BigDecimal.ZERO);
        }
        if (bs_addr.getStreWeight() == null) {
            bs_addr.setStreWeight(BigDecimal.ZERO);
        }
        if (bs_addr.getProWeight() == null) {
            bs_addr.setProWeight(BigDecimal.ZERO);
        }
        if (bs_addr.getMulWeight() == null) {
            bs_addr.setMulWeight(BigDecimal.ZERO);
        }

        return bs_addr;
    }
}
