package com.service.lwaddress.impl;

import com.alibaba.fastjson.JSONObject;
import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.*;
import com.dao.db3.lwaddr.Base_addrMapper1;
import com.dao.entity.lwaddress.*;
import com.service.lwaddress.Base_addrService;
import com.service.lwaddress.Bs_utilService;
import com.utils.sys.lwaddress.AsciiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Bs_utilServiceImpl implements Bs_utilService {

    private static final Logger log = LoggerFactory.getLogger(Bs_utilServiceImpl.class);

    @Autowired
    private HgApplicationProperty applicationProperty;
    @Autowired
    private Base_addrMapper1 base_addrMapper1;
    @Autowired
    private Base_addrMapper base_addrMapper;
    @Autowired
    private Bs_cityMapper bs_cityMapper;
    @Autowired
    private Bs_areaMapper bs_areaMapper;
    @Autowired
    private Bs_streetMapper bs_streetMapper;
    @Autowired
    private Bs_provinceMapper bs_provinceMapper;
    @Autowired
    private Base_addrService base_addrService;

    @Override
    @Async("asyncPromiseExecutor")
    public void addressStart(int number1, int number2, BigDecimal n) {
        List<Base_addr> list;
        Base_addr base_addr;

        //用来存放所有省市区街道集合和分值对象的
        Map<String, Object> allMessage = new HashMap<>();

        List<Bs_province> provinceMessage;
        List<Bs_city> cityMessage;
        List<Bs_area> areaMessage;
        List<Bs_street> streetMessage;
        List<Base_addr> updateMessage = new ArrayList<>();

        //查询省市区街道的全称和简称存进各自的List集合中
        provinceMessage = bs_provinceMapper.selectShortNameAndProvinceName();
        cityMessage = bs_cityMapper.selectCityAllName();
        areaMessage = bs_areaMapper.selectAreaMessage();
        streetMessage = bs_streetMapper.selectStreetMessage();

        allMessage.put("provinceMessage", provinceMessage);
        allMessage.put("cityMessage", cityMessage);
        allMessage.put("areaMessage", areaMessage);
        allMessage.put("streetMessage", streetMessage);

        //分值计算
        BigDecimal dec1 = new BigDecimal(-5);
        BigDecimal dec2 = new BigDecimal(-2);
        BigDecimal dec3 = new BigDecimal(-1);
        BigDecimal dec4 = new BigDecimal(0);
        BigDecimal dec5 = new BigDecimal(100);
        BigDecimal dec6 = new BigDecimal(0);
        BigDecimal dec7 = new BigDecimal(-20);
        BigDecimal dec8 = new BigDecimal(-10);

        allMessage.put("dec1", dec1);
        allMessage.put("dec2", dec2);
        allMessage.put("dec3", dec3);
        allMessage.put("dec4", dec4);
        allMessage.put("dec5", dec5);
        allMessage.put("dec6", dec6);
        allMessage.put("dec7", dec7);
        allMessage.put("dec8", dec8);

        //如果是增量查询增量表
        if (applicationProperty.getInsertIndex().equals("1")) {
            list = base_addrMapper.selectFromInsertAddr_sj(number1, number2);
        } else {
            //查询原始数据表
            list = base_addrMapper.selectAddr_sj(number1, number2);
        }

        //将查到的数据加入集合中
        for (int i = 0; i < list.size(); i++) {
            //如果长地址不为空
            if (list.get(i).getAddrSj() != null) {
                //处理特殊字符串
                String addrSj = AsciiUtil.SpecialHandl(list.get(i).getAddrSj());
                addrSj = addrSj.replace(list.get(i).getName1(),"");
                //参数是所有标准地址集合和加减分值
                base_addr = base_addrService.addrSet(addrSj, allMessage);
                base_addr.setId(list.get(i).getId());
                base_addr.setCountId(list.get(i).getCountId());
                base_addr.setOldPhone(list.get(i).getPhone());
                base_addr.setOldName1(list.get(i).getName1());
                base_addr.setName1(list.get(i).getName1());
                base_addr.setPhone(list.get(i).getPhone());
                base_addr.setShortPhone(list.get(i).getShortPhone());
                base_addr.setRowId(list.get(i).getRowId());
                base_addr.setTableName(list.get(i).getTableName());
                updateMessage.add(base_addr);
            } else {
                continue;
            }
        }

        //如果是增量操作，就需要将总分乘一个配置文件配置的权重值
        if (applicationProperty.getInsertIndex().equals("1")) {
            for (int i = 0; i < updateMessage.size(); i++) {
                updateMessage.get(i).setMulWeight(updateMessage.get(i).getMulWeight().multiply(n));
            }
        }

        try {
            if(updateMessage == null || updateMessage.size()==0){
                log.error("start:{} , batchcCount:{}",number1,number2);
                log.error("原始表查到的数据:{}",JSONObject.toJSONString(list));
                return;
            }
            //将处理完的值存进sec_addr表
            base_addrMapper1.insert1(updateMessage);
            log.info("=======当前线程名:{}",Thread.currentThread().getName());
            log.info("=======开始处理的数据编号:{} 步进值:{}",number1,number2);
         } catch (Exception e) {
            e.printStackTrace();
            log.error(JSONObject.toJSONString(updateMessage));
            log.error(e.getMessage());
        }
    }

    /*
@param sum为总处理数
@param count为一次处理数
* */

/*  @Override
    public void startway(int start, int total,int batchcCount) {
        log.debug("start susscces");
        for(int j = 0;j <total/batchcCount; j++){
            start=start+batchcCount;
            if(j==total/batchcCount-1){
                batchcCount=total-(j+1)*batchcCount;
            }
            insertLngLat(start,batchcCount);
        }
        log.debug("finish susscces");
    }
}*/
}
