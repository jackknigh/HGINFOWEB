package com.lwaddress;

import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.Base_addrMapper;
import com.dao.db3.lwaddr.Base_addrMapper1;
import com.dao.entity.lwaddress.*;
import com.service.antiEncode.EncodeStartWayService;
import com.service.antiEncode.InsertEncodeService;
import com.service.lwaddress.Bs_startWayService;
import com.service.lwaddress.Bs_utilService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private InsertEncodeService insertEncodeService;
    @Autowired
    private HgApplicationProperty applicationProperty;
    @Autowired
    private Bs_utilService bs_utilService;
    @Autowired
    private Base_addrMapper bs_addrMapper;
    @Autowired
    private Base_addrMapper1 base_addrMapper1;


    @Test
    public void test() {
        log.debug("========================测试开始==========================");
        insertEncodeService.errorProcess();
        log.debug("========================测试结束==========================");
    }

//    @Test
//    public void test1() {
//        log.debug("========================测试开始==========================");
//        String reg = getReg();
//        //todo 获取增量表的数据，模拟进来的数据，正常被处理后的数据p5type=7,异常数据=6
//        List<Base_addr> baseAddrList = bs_addrMapper.getInsertDate();
//        log.info("===========================================查到增量数据 {} 条",baseAddrList.size());
//
//        if(baseAddrList.size()<1){
//            return;
//        }
//
//        for (Base_addr base_addr : baseAddrList) {
//            if (StringUtils.isBlank(base_addr.getPhone()) || base_addr.getPhone().length() < 5) {
//                //插入废弃表
//                base_addrMapper1.insertDiscard(base_addr);
//                base_addrMapper1.updateP5type(base_addr);
//            }
//            //增量碰撞处理
//            bs_utilService.increment(base_addr, reg);
//        }
//        log.debug("========================测试结束==========================");
//    }



    //配置的区的省市区街道关键字正则生成，并去除对应参数中包含的关键字
    public String getReg() {
        //配置的市的编号
        String cityCode = applicationProperty.getCityCode();
        StringBuilder regex = new StringBuilder();

        //查询市
        Bs_city cityName = bs_addrMapper.getCity(cityCode);
        regex.append(cityName.getCityName());

        //查询省
        Bs_province provinceName = bs_addrMapper.getProvince(cityName.getProvinceCode());
        regex.append("|").append(provinceName.getProvinceName()).append("|").append(provinceName.getShortName());

        //查询区
        List<Bs_area> areaNames = bs_addrMapper.getArea(cityCode);

        //todo 街道不需要去除
        for (Bs_area areaName : areaNames) {
            regex.append("|").append(areaName.getAreaName());
//            //查询街道
            List<Bs_street> streetNames = bs_addrMapper.getStreetName(areaName.getAreaCode());
            for (Bs_street streetName : streetNames) {
                regex.append("|").append(streetName.getStreetName()).append("|").append(streetName.getShortName());
            }
        }
        log.info(regex.toString());
        return regex.toString();
    }



    /**
     * 获取省市区街道集合对象
     *
     * @return
     */
    @Test
    public void getMap() {
        //用来存放所有省市区街道集合和分值对象的
        Map<String, Object> allMessage = new HashMap<>();

        List<Bs_province> provinceMessage = new ArrayList<>();
        List<Bs_area> areaMessage = new ArrayList<>();
        List<Bs_city> cityMessage = new ArrayList<>();
        List<Bs_street> streetNames;
        List<Bs_street> streetMessage = new ArrayList<>();

        //优先查询温州的省市区街道全程和简称

        //配置的市的编号
        String cityCode = applicationProperty.getCityCode();
        //查询市
        Bs_city cityName = bs_addrMapper.getCity(cityCode);
        //查询省
        Bs_province provinceName = bs_addrMapper.getProvince(cityName.getProvinceCode());
        //查询区
        List<Bs_area> areaNames = bs_addrMapper.getArea(cityCode);
        //查询街道不需要去除
        for (Bs_area areaName : areaNames) {
            //查询街道
            streetNames = bs_addrMapper.getStreetName(areaName.getAreaCode());
            streetMessage.addAll(streetNames);
        }

        //查询除了浙江省以外的省
        List<Bs_province> allProvince =  bs_addrMapper.getAllProvince();
        provinceMessage.add(provinceName);
        cityMessage.add(cityName);
        areaMessage.addAll(areaNames);

        allMessage.put("streetMessage", streetMessage);
        allMessage.put("provinceMessage", provinceMessage);
        allMessage.put("cityMessage", cityMessage);
        allMessage.put("areaMessage", areaMessage);
        allMessage.put("allProvince", allProvince);
    }
}
