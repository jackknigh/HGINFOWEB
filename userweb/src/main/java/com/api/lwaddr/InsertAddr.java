package com.api.lwaddr;

import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.Base_addrMapper;
import com.dao.entity.lwaddress.*;
import com.service.lwaddress.Bs_startWayService;
import com.service.lwaddress.MsgSearchService;
import com.utils.sys.TextUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class InsertAddr implements Job {
    @Autowired
    private Bs_startWayService bs_startWayService;
    @Value("${sysExecutor.similarityCorePoolSize}")
    private Integer similarityCorePoolSize;
    @Value("${sysExecutor.similarityMaxPoolSize}")
    private Integer similarityMaxPoolSize;
    @Autowired
    private Base_addrMapper bs_addrMapper;
    @Autowired
    private MsgSearchService msgSearchService;
    @Autowired
    private HgApplicationProperty applicationProperty;

    private static final Logger log = LoggerFactory.getLogger(InsertAddr.class);


    /**
     * 增量定时处理
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        int count = Integer.valueOf(applicationProperty.getCount());
        //从两天前最大的count_id开始
//        int start = bs_addrMapper.getStart();
        //从指定的count_id开始
        int start = Integer.valueOf(applicationProperty.getStartCount());

        //省市区街道拼接的正则字符串方法
        String reg = getReg();
        //获取温州所有省市区街道集合方法
        Map<String, Object> map = getMap();

        while (true) {
            //获取增量表的数据，每次都会取完，start都会加上指定步进值，直到获取不到数据
            List<Base_addr> baseAddrList = bs_addrMapper.getInsertDate1(start, start+count);
            if (TextUtils.isEmpty(baseAddrList)) {
                return;
            }
            //对增量数据进行处理方法
            for (Base_addr base_addr : baseAddrList) {
                msgSearchService.insertMsgProcess1(base_addr,reg,map);
            }
            start = start+count;
        }
    }

    /**
     * 配置的区的省市区街道关键字正则生成，并去除对应参数中包含的关键字
     *
     * @return
     */
    public String getReg() {
        //配置的市的编号，目前默认温州市
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
        for (Bs_area areaName : areaNames) {
            regex.append("|").append(areaName.getAreaName());
//            //查询街道
            List<Bs_street> streetNames = bs_addrMapper.getStreetName(areaName.getAreaCode());
            for (Bs_street streetName : streetNames) {
                regex.append("|").append(streetName.getStreetName()).append("|").append(streetName.getShortName());
            }
        }
        return regex.toString();
    }

    /**
     * 获取省市区街道集合对象
     *
     * @return
     */
    public Map<String, Object> getMap() {
        //用来存放所有省市区街道集合和分值对象的
        Map<String, Object> allMessage = new HashMap<>();

        List<Bs_province> provinceMessage = new ArrayList<>();
        List<Bs_city> cityMessage = new ArrayList<>();
        List<Bs_area> areaMessage = new ArrayList<>();
        List<Bs_street> streetMessage = new ArrayList<>();

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
            List<Bs_street> streetNames = bs_addrMapper.getStreetName(areaName.getAreaCode());
            streetMessage.addAll(streetNames);
        }
        provinceMessage.add(provinceName);
        cityMessage.add(cityName);
        areaMessage.addAll(areaNames);

        //查询社区表
        List<BsCommunity> communityMessage = bs_addrMapper.getCommunities();

        allMessage.put("provinceMessage", provinceMessage);
        allMessage.put("cityMessage", cityMessage);
        allMessage.put("areaMessage", areaMessage);
        allMessage.put("streetMessage", streetMessage);
        allMessage.put("communityMessage", communityMessage);

        //分值计算
        BigDecimal dec2 = new BigDecimal(-2);
        BigDecimal dec1 = new BigDecimal(-5);
        BigDecimal dec3 = new BigDecimal(-1);
        BigDecimal dec5 = new BigDecimal(100);
        BigDecimal dec4 = new BigDecimal(0);
        BigDecimal dec6 = new BigDecimal(0);
        BigDecimal dec7 = new BigDecimal(-20);
        BigDecimal dec8 = new BigDecimal(-10);

        allMessage.put("dec2", dec2);
        allMessage.put("dec1", dec1);
        allMessage.put("dec3", dec3);
        allMessage.put("dec5", dec5);
        allMessage.put("dec4", dec4);
        allMessage.put("dec6", dec6);
        allMessage.put("dec7", dec7);
        allMessage.put("dec8", dec8);
        return allMessage;
    }
}
