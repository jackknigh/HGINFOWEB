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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;


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

    private static ThreadPoolTaskExecutor executor;

    public void initExecutor(){
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(similarityCorePoolSize);//核心线程大小
        executor.setMaxPoolSize(similarityMaxPoolSize);//最大线程大小
        executor.setQueueCapacity(9999999);//队列最大容量
        executor.setKeepAliveSeconds(60);//存活时间
        executor.setThreadNamePrefix("async-match1-");//线程名称
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
    }

    private static final Logger log = LoggerFactory.getLogger(InsertAddr.class);


    /**
     * 增量
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        log.info("================================开始增量操作======================= 开始时间:{}", DateUtil.getCurrDateTimeStr());
//        initExecutor();
//        bs_startWayService.increment(executor);

        int count = Integer.valueOf(applicationProperty.getCount());
        //获取两天前最大的步进值
        int start = bs_addrMapper.getStart();
//        int start = 0;
        String reg = getReg();
        Map<String, Object> map = getMap();
        //获取增量表的数据
        while (true) {
            List<Base_addr> baseAddrList = bs_addrMapper.getInsertDate1(start, start+count);
            if (TextUtils.isEmpty(baseAddrList)) {
                return;
            }

//            //修改处理过的数据的状态为7
//            bs_addrMapper.updateState(start, start+count);

            for (Base_addr base_addr : baseAddrList) {
                msgSearchService.insertMsgProcess1(base_addr,reg,map);
            }
            start = start+count;
        }
    }


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

        allMessage.put("provinceMessage", provinceMessage);
        allMessage.put("cityMessage", cityMessage);
        allMessage.put("areaMessage", areaMessage);
        allMessage.put("streetMessage", streetMessage);

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


//    /**
//     * 增量
//     * @param jobExecutionContext
//     * @throws JobExecutionException
//     */
//    @Override
//    public void execute1(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        log.info("================================开始增量操作。。。。。。。。。。 开始时间:{}", DateUtil.getCurrDateTimeStr());
//        initExecutor();
//        bs_startWayService.increment(executor);
//    }
}
