package com.service.lwaddress.impl;

import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.*;
import com.dao.entity.lwaddress.*;
import com.service.lwaddress.*;
import com.utils.sys.lwaddress.AsciiUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.dto.constants.Constants.REGEX_XMSJ;

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
    public Base_addr addrSet(Base_addr bs_addr, Map<String, Object> allMessage) {
        String name1 = "";
        String name2 = "";
        bs_addr.setP1type(0);
        Map<String, Object> provinceMap;
        Map<String, Object> cityMap;
        Map<String, Object> areaMap;
        Map<String, Object> streetMap;

        BigDecimal dec6 = new BigDecimal(0);
        List<Bs_city> listCity = new ArrayList<>();
        List<Bs_area> listArea = new ArrayList<>();
        List<Bs_street> listStreet = new ArrayList<>();

        //去除姓名中的特殊字符
        bs_addr.setName1(bs_addr.getName1().replaceAll(REGEX_XMSJ, ""));
        //去除手机中的特殊字符
        bs_addr.setPhone(bs_addr.getPhone().replaceAll(REGEX_XMSJ, ""));
        //地址处理特殊字符串
        String address = AsciiUtil.SpecialHandl(bs_addr.getAddrSj(), bs_addr.getName1());

        //对省的操作
        provinceMap = bs_provinceService.provinceJudge(address, (List<Bs_province>) allMessage.get("provinceMessage"));
        //如果有匹配到省
        if (provinceMap.get("provinceCode") != null) {
            //遍历所有城市
            for (int i = 0; i < ((List<Bs_city>) allMessage.get("cityMessage")).size(); i++) {
                //如果城市的省编码等于匹配到的省编码
                if (((List<Bs_city>) allMessage.get("cityMessage")).get(i).getProvinceCode().equals(provinceMap.get("provinceCode"))) {
                    //将该城市存进城市集合
                    listCity.add(((List<Bs_city>) allMessage.get("cityMessage")).get(i));
                }
            }
        } else {
            //没有匹配到省就将所有城市存进城市集合
            listCity = (List<Bs_city>) allMessage.get("cityMessage");
        }
        //对城市的操作
        cityMap = bs_cityService.cityJudge((String) provinceMap.get("address"), listCity);
        //如果有匹配到城市
        if (cityMap.get("cityCode") != null) {
            //遍历所有区域
            for (int i = 0; i < ((List<Bs_area>) allMessage.get("areaMessage")).size(); i++) {
                //找到区域的城市编码等于匹配到的城市编码的数据存进区域集合
                if (((List<Bs_area>) allMessage.get("areaMessage")).get(i).getCityCode().equals(cityMap.get("cityCode"))) {
                    listArea.add(((List<Bs_area>) allMessage.get("areaMessage")).get(i));
                }
            }
        } else if (cityMap.get("cityCode") == null && provinceMap.get("provinceCode") != null) {
            //如果城市编码为空和省编码不为空
            for (int i = 0; i < ((List<Bs_area>) allMessage.get("areaMessage")).size(); i++) {
                if (((List<Bs_area>) allMessage.get("areaMessage")).get(i).getProvinceCode().equals(provinceMap.get("provinceCode"))) {
                    listArea.add(((List<Bs_area>) allMessage.get("areaMessage")).get(i));
                }
            }
        } else if (cityMap.get("cityCode") == null && provinceMap.get("provinceCode") == null) {
            //如果省市的编码都为空就将标准的区域集合存进区域集合
            listArea = (List<Bs_area>) allMessage.get("areaMessage");
        }
        //对区域的操作(这里的listArea可能是前面赋值的区域)
        areaMap = bs_areaService.areaJudge((String) cityMap.get("address"), listArea);
        //如果没匹配到区域
        if (areaMap.get("areaCode") == null) {
            //如果城市和省都不为空
            if (cityMap.get("cityCode") != null || provinceMap.get("provinceCode") != null) {
                listArea = (List<Bs_area>) allMessage.get("areaMessage");
                //就与标准区域重新匹配一次
                areaMap = bs_areaService.areaJudge((String) cityMap.get("address"), listArea);
            }
        }

        //如果匹配到了区域
        if (areaMap.get("areaCode") != null) {
            for (int i = 0; i < ((List<Bs_street>) allMessage.get("streetMessage")).size(); i++) {
                //如果有街道的区域编号等于匹配到的区域编号，就将街道信息存进街道集合
                if (((List<Bs_street>) allMessage.get("streetMessage")).get(i).getAreaCode().equals(areaMap.get("areaCode"))) {
                    listStreet.add(((List<Bs_street>) allMessage.get("streetMessage")).get(i));
                }
            }
        } else if (areaMap.get("areaCode") == null && cityMap.get("cityCode") != null) {
            //如果区域为空，城市不为空
            for (int i = 0; i < ((List<Bs_street>) allMessage.get("streetMessage")).size(); i++) {
                if (((List<Bs_street>) allMessage.get("streetMessage")).get(i).getCityCode().equals(cityMap.get("cityCode"))) {
                    listStreet.add(((List<Bs_street>) allMessage.get("streetMessage")).get(i));
                }
            }
        } else if (areaMap.get("areaCode") == null && cityMap.get("cityCode") == null) {
            //如果区域编码和城市编码都为空
            for (int i = 0; i < ((List<Bs_street>) allMessage.get("streetMessage")).size(); i++) {
                if (((List<Bs_street>) allMessage.get("streetMessage")).get(i).getAreaCode().substring(0, 4).equals("3303")) {
                    listStreet.add(((List<Bs_street>) allMessage.get("streetMessage")).get(i));
                }
            }
        }
        streetMap = bs_streetService.streetJudge((String) areaMap.get("address"), listStreet);

        if (StringUtils.isBlank((String) streetMap.get("streeCode"))) {
            for (Bs_street streetMessage : ((List<Bs_street>) allMessage.get("streetMessage"))) {
                listStreet.add(streetMessage);
            }
            streetMap = bs_streetService.streetJudge((String) areaMap.get("address"), listStreet);
        }

        //如果城市区域街道都为空就标记为-100
        if (streetMap.get("streetCode") == null && areaMap.get("areaCode") == null && cityMap.get("cityCode") == null) {
            if (applicationProperty.getOpenOrNot().equals("1")) {
                listStreet = (List<Bs_street>) allMessage.get("streetMessage");
                streetMap = bs_streetService.streetJudge((String) areaMap.get("address"), listStreet);
            }
            bs_addr.setP1type(-100);
        }

        /*
         * 对信息的标记，1表示存在，0表示不存在
         */

        //开始算分
        if (provinceMap.get("provinceName") != null) {
            bs_addr.setProWeight((BigDecimal) allMessage.get("dec4"));
            name1 = name1 + "1";
        } else {
            bs_addr.setProWeight((BigDecimal) allMessage.get("dec3"));
            name1 = name1 + "0";
        }
        if (cityMap.get("cityName") != null) {
            bs_addr.setCityWeight((BigDecimal) allMessage.get("dec4"));
            name1 = name1 + "1";
        } else {
            bs_addr.setCityWeight((BigDecimal) allMessage.get("dec1"));
            name1 = name1 + "0";
        }
        if (areaMap.get("areaName") != null) {
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
        //街道不为空,区为空
        if (streetMap.get("streetCode") != null && areaMap.get("areaCode") == null) {
            String areacode = (String) streetMap.get("areaCodeSec");

            for (int i = 0; i < ((List<Bs_area>) areaMap.get("areaAllName")).size(); i++) {
                if (((List<Bs_area>) areaMap.get("areaAllName")).get(i).getAreaCode().equals(areacode)) {
                    areaMap.put("areaName", ((List<Bs_area>) areaMap.get("areaAllName")).get(i).getAreaName());
                    areaMap.put("areaCode", ((List<Bs_area>) areaMap.get("areaAllName")).get(i).getAreaCode());
                    areaMap.put("cityCodeSec", ((List<Bs_area>) areaMap.get("areaAllName")).get(i).getCityCode());
                    break;
                }
            }
        }
        //区不为空，城市为空
        if (cityMap.get("cityCode") == null && areaMap.get("areaCode") != null) {
            String citycode = (String) areaMap.get("cityCodeSec");

            for (int i = 0; i < ((List<Bs_city>) cityMap.get("cityAllName")).size(); i++) {
                if (((List<Bs_city>) cityMap.get("cityAllName")).get(i).getCityCode().equals(citycode)) {
                    cityMap.put("cityName", ((List<Bs_city>) cityMap.get("cityAllName")).get(i).getCityName());
                    cityMap.put("cityCode", ((List<Bs_city>) cityMap.get("cityAllName")).get(i).getCityCode());
                    cityMap.put("provinceCodeSec", ((List<Bs_city>) cityMap.get("cityAllName")).get(i).getProvinceCode());
                    break;
                }
            }
        }

        //城市不为空，省为空
        if (cityMap.get("cityCode") != null && provinceMap.get("provinceCode") == null) {
            String provincecode = (String) cityMap.get("provinceCodeSec");

            for (int i = 0; i < ((List<Bs_province>) provinceMap.get("provinceAllName")).size(); i++) {
                if ((((List<Bs_province>) provinceMap.get("provinceAllName")).get(i).getProvinceCode()).equals(provincecode)) {
                    // bs_addr.setProvince(((List<Bs_province>)provinceMap.get("provinceAllName")).get(i).getProvinceName());
                    provinceMap.put("provinceName", ((List<Bs_province>) provinceMap.get("provinceAllName")).get(i).getProvinceName());
                    provinceMap.put("provinceCode", ((List<Bs_province>) provinceMap.get("provinceAllName")).get(i).getProvinceCode());
                    break;
                }
            }
        }

        //判断是否错误，1表示正确，0表示错误,2表示不存在
        if (areaMap.get("areaCode") != null && streetMap.get("areaCodeSec") != null) {
            if (areaMap.get("areaCode").equals(streetMap.get("areaCodeSec"))) {
                name2 = name2 + "1";
            } else {
                name2 = name2 + "0";
                dec6 = dec6.add((BigDecimal) allMessage.get("dec8"));
            }
        } else {
            name2 = name2 + "2";
        }
        if (cityMap.get("cityCode") != null && areaMap.get("cityCodeSec") != null) {
            if (cityMap.get("cityCode").equals(areaMap.get("cityCodeSec"))) {
                name2 = name2 + "1";
            } else {
                name2 = name2 + "0";
                dec6 = dec6.add((BigDecimal) allMessage.get("dec7"));
            }
        } else {
            name2 = name2 + "2";
        }
        if (provinceMap.get("provinceCode") != null && cityMap.get("provinceCodeSec") != null) {
            if (provinceMap.get("provinceCode").equals(cityMap.get("provinceCodeSec"))) {
                name2 = name2 + "1";
            } else {
                name2 = name2 + "0";
                dec6 = (BigDecimal) allMessage.get("dec4");
            }
        } else {
            name2 = name2 + "2";
        }
        if (provinceMap.get("provinceName") != null) {
            bs_addr.setProvince((String) provinceMap.get("provinceName"));
        } else {
            for (int i = 0; i < ((List<Bs_province>) allMessage.get("provinceMessage")).size(); i++) {
                if (((List<Bs_province>) allMessage.get("provinceMessage")).get(i).getProvinceCode().equals(provinceMap.get("provinceCode"))) {
                    bs_addr.setProvince(((List<Bs_province>) allMessage.get("provinceMessage")).get(i).getProvinceName());
                }
            }
        }

        if (cityMap.get("cityCode") == null) {
            bs_addr.setCity((String) cityMap.get("cityCode"));
        } else {
            bs_addr.setCity((String) cityMap.get("cityName"));
        }

        bs_addr.setArea((String) areaMap.get("areaName"));
        bs_addr.setStreet((String) streetMap.get("streetName"));
        bs_addr.setShortAddr((String) streetMap.get("address"));

        //生成手机短号，前三后四
        if (bs_addr.getPhone().length() >= 7) {
            String startPhone = bs_addr.getPhone().substring(1, 3);
            String endPhone = bs_addr.getPhone().substring(bs_addr.getPhone().length() - 4);
            bs_addr.setShortPhone(startPhone + endPhone);
        }

        //短地址长度异常的数据分数扣一半
        if (bs_addr.getShortAddr().length() < 5 || bs_addr.getShortAddr().length() > 30) {
            dec6 = dec6.multiply(new BigDecimal(0.5));
        }

        //手机号或者姓名带*的各扣5分
        if (StringUtils.isBlank(bs_addr.getPhone()) || bs_addr.getPhone().contains("*")) {
            dec6 = dec6.subtract(new BigDecimal(5));
        }
        if (StringUtils.isBlank(bs_addr.getName1()) || bs_addr.getName1().contains("*")) {
            dec6 = dec6.subtract(new BigDecimal(5));
        }

        //短地址末尾是 - 结尾的扣5分
        if (bs_addr.getShortAddr().endsWith("-")) {
            dec6 = dec6.subtract(new BigDecimal(5));
        }

        //短地址中包含一些关键字的加分项
        if (bs_addr.getShortAddr().contains("室")) {
            dec6 = dec6.add(new BigDecimal(5));
        }

        if (bs_addr.getShortAddr().contains("号")) {
            dec6 = dec6.add(new BigDecimal(5));
        }

        if (bs_addr.getShortAddr().contains("-")) {
            dec6 = dec6.add(new BigDecimal(20));
        }

        if (bs_addr.getShortAddr().contains("楼")) {
            dec6 = dec6.add(new BigDecimal(5));
        }

        //短地址过长的扣分项
        if (bs_addr.getShortAddr().length() > 25) {
            dec6 = dec6.subtract(new BigDecimal(10));
        }

        bs_addr.setMulWeight(dec6);
        bs_addr.setAddrSign1(name1);
        bs_addr.setAddrSign2(name2);

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
