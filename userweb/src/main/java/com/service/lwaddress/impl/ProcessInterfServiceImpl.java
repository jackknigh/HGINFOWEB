package com.service.lwaddress.impl;

import com.alibaba.fastjson.JSON;
import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.Base_addrMapper;
import com.dao.db2.lwaddress.PhoneMapper;
import com.dao.db3.lwaddr.Base_addrMapper1;
import com.dao.entity.lwaddress.*;
import com.service.lwaddress.ProcessInterfService;
import com.utils.sys.TextUtils;
import com.utils.sys.lwaddress.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class ProcessInterfServiceImpl implements ProcessInterfService {

    private static final Logger log = LoggerFactory.getLogger(ProcessInterfServiceImpl.class);

    @Autowired
    private HgApplicationProperty applicationProperty;
    @Autowired
    private PhoneMapper phoneMapper;
    @Autowired
    private Base_addrMapper base_addrMapper;
    @Autowired
    private Bs_startWayServiceImpl bs_startWayService;
    @Autowired
    private Base_addrMapper1 base_addrMapper1;

    @Async("asyncPromiseExecutor")
    @Override
    public void processInterf(int start, int batchcCount, BigDecimal n, ThreadPoolTaskExecutor executor, ThreadPoolTaskExecutor executor1,List<Phone> phoneList) {
        //滑块的大小
        int blockSizeByStr = Integer.valueOf(applicationProperty.getBlockSizeByStr());
        int blockSizeByNum = Integer.valueOf(applicationProperty.getBlockSizeByNum());
        //根据标准街道清洗数据
        if ("1".equals(applicationProperty.getStandardAddress())) {
            log.info("开始执行标准街道清洗。。。。。。。。。。。。。。。。");

            List<Address> addresses = phoneMapper.selectAllAddress(start, batchcCount);
            log.info("********查询到街道:" + JSON.toJSONString(addresses) + "**********");

            for (int i = 0; i < addresses.size(); i++) {
                long startTime = System.currentTimeMillis();
                List addressMessage;
                Address address = addresses.get(i);
                log.info("********开始执行街道:" + address.getStreet() + "**********");
                if ("".equals(address.getStreet())) {
                    address.setStreet(null);
                }

                addressMessage = base_addrMapper.selectByAddress(address);
                log.info("街道: " + address.getStreet() + " 共查到 " + addressMessage.size() + " 条数据");

                //多线程相似度方法
                List<Base_addr> baseAddrs = processThread(addressMessage, blockSizeByNum, blockSizeByStr, executor, executor1);

                //插入数据
                insertMsg(baseAddrs);
                long endTime = System.currentTimeMillis();
                long time = endTime - startTime;
                log.error("*************************" + "处理" + addressMessage.size() + "条数据共用了:" + time + "毫秒");
            }
        }

        //根据手机号短号清洗数据,只反写不合并操作
        if ("0".equals(applicationProperty.getStandardAddress()) || "2".equals(applicationProperty.getStandardAddress())) {
            log.info("*********开始手机号处理*********");
            //查询phone表,phone的号码都是短号，前二至三+后四
            List<Phone> listPhone = phoneMapper.selectAll(start, start+batchcCount);

            log.info("*********开始处理进度为：{} --》 {} 的数据*********",start,start+batchcCount);

            //获取指定手机号组的每个元素
            for (int i = 0; i < listPhone.size(); i++) {
                String shortPhone = listPhone.get(i).getPhone();
                if(StringUtils.isBlank(shortPhone) && "2".equals(applicationProperty.getStandardAddress())){
                    continue;
                }

                //获取指定短号码的数据
                List list = base_addrMapper.selectByShortPhone(shortPhone, null);
                if (TextUtils.isEmpty(list)) {
                    continue;
                }

                log.info("*********手机短号为: {},共 {} 条数据*********",shortPhone,list.size());
                //多线程相似度方法
                list = processThread(list, blockSizeByNum, blockSizeByStr,executor, executor1);
                //短地址为空可能会导致集合为空
                if (TextUtils.isEmpty(list)) {
                    continue;
                }
                //只反写不合并操作
                if("2".equals(applicationProperty.getStandardAddress())){
                    base_addrMapper1.updatePhone(list);
                    continue;
                }
                //插入数据
                insertMsg(list);
            }
        }

        //只清洗手机长号不为*的数据
        if ("3".equals(applicationProperty.getStandardAddress())) {
            //查询phone表,只查询手机号不为* 的，且不重复的
            List<Phone> listPhone = new ArrayList<>();
            for (int i = start; i < start+batchcCount; i++) {
                listPhone.add(phoneList.get(i));
            }

            //获取指定手机号组的每个元素
            for (int i = 0; i < listPhone.size(); i++) {
                String phone = listPhone.get(i).getPhone();
                List list = base_addrMapper.selectDataByPhone(phone, null);
                if (list.size() == 0) {
                    continue;
                }

                log.info("*********开始处理手机号为: {},共 {} 条数据*********",phone,list.size());
                //多线程相似度方法
                list = processThread(list, blockSizeByNum, blockSizeByStr,executor, executor1);
                //插入数据
                insertMsg(list);
            }
            log.info("*********已处理至：  {} -》 {} *********",start,start+batchcCount);
        }
    }

//    /**
//     * 左右相似度碰撞方法
//     * @param executor
//     */
//    @Override
//    public void compare(ThreadPoolTaskExecutor executor) {
//        //获取温州的所有街道
//        Map<String, Object> map = bs_startWayService.getMap();
//        List<Bs_street> streetNames = (List<Bs_street>) map.get("streetMessage");
//        //将街道为null的对象存进街道集合中
//        //todo 街道为null的最后单独处理
////        Bs_street bs_street = new Bs_street();
////        streetNames.add(bs_street);
//
//        for (Bs_street streetName : streetNames) {
//            //滑块的大小
//            int blockSizeByStr = Integer.valueOf(applicationProperty.getBlockSizeByStr());
//            int blockSizeByNum = Integer.valueOf(applicationProperty.getBlockSizeByNum());
//
//            int count = Integer.valueOf(applicationProperty.getCount());
//            int start = Integer.valueOf(applicationProperty.getStartCount());
//            int total = Integer.valueOf(applicationProperty.getTotalCount());
//
//            while (true){
//                //需碰撞的数据
//                List<Base_addr> baseAddrs1 = base_addrMapper.selectBaseAddrs(streetName.getStreetName(),start,count);
//                log.error("街道  {} 的进度 {} ---> {} , 有 【{}】 条数据",streetName.getStreetName(),start,total,baseAddrs1.size());
//                if(TextUtils.isEmpty(baseAddrs1)){
//                    start = start + count;
//                    if(start>total){
//                        break;
//                    }
//                    continue;
//                }
//
//                log.info("*********街道 ：{}  有 {} 条比较值数据*********",streetName.getStreetName(),baseAddrs1.size());
//
//                //标准数据,如果需碰撞数据街道为null,就与所有标准数据进行碰撞
//                List<Base_addr> volList = base_addrMapper1.selectBaseAddr2(null,streetName.getStreetName());
//                log.error("街道  {} 的进度 {} ---> {} , 有 【{}】 条被比较数据",streetName.getStreetName(),start,total,volList.size());
//                if(TextUtils.isEmpty(volList)){
//                    start = start + count;
//                    if(start>total){
//                        break;
//                    }
//                    continue;
//                }
//                log.info("*********有 {} 条被比较值数据*********",volList.size());
//                executor.execute(new CompareRunnable4(blockSizeByNum,blockSizeByStr,volList,baseAddrs1));
//                start = start + count;
//                if(start>total){
//                    break;
//                }
//            }
//        }
//    }

    /**
     * 左右相似度碰撞方法
     * @param executor
     */
    @Override
    public void compare(ThreadPoolTaskExecutor executor) {
        //获取温州的所有街道
        Map<String, Object> map = bs_startWayService.getMap();
        List<Bs_street> streetNames = (List<Bs_street>) map.get("streetMessage");
        //将街道为null的对象存进街道集合中
        //todo 街道为null的最后单独处理
//        Bs_street bs_street = new Bs_street();
//        streetNames.add(bs_street);

        for (Bs_street streetName : streetNames) {
            //滑块的大小
            int blockSizeByStr = Integer.valueOf(applicationProperty.getBlockSizeByStr());
            int blockSizeByNum = Integer.valueOf(applicationProperty.getBlockSizeByNum());
//            --         count_id &gt;= #{start} and count_id &lt; #{count}
            while (true){
                //需碰撞的数据
                List<Base_addr> baseAddrs1 = base_addrMapper.selectBaseAddrs(streetName.getStreetName(),0,0);
                log.error("街道 {} ，共 {} 条比较数据",streetName.getStreetName(),baseAddrs1.size());
                if(TextUtils.isEmpty(baseAddrs1)){
                break;
                }

                //标准数据,如果需碰撞数据街道为null,就与所有标准数据进行碰撞
                List<Base_addr> volList = base_addrMapper1.selectBaseAddr2(null,streetName.getStreetName());
                log.error("街道 {} ，共 {} 条被比较数据",streetName.getStreetName(),volList.size());
                if(TextUtils.isEmpty(volList)){
                break;
                }
                executor.execute(new CompareRunnable4(blockSizeByNum,blockSizeByStr,volList,baseAddrs1));
                break;
            }
        }
    }

    /**
     * 清洗手机长号不为* 的数据
     * @param executor
     * @param executor1
     */
    @Override
    public void startwayPhone(ThreadPoolTaskExecutor executor, ThreadPoolTaskExecutor executor1) {
        int startCount = 0;
        int count = Integer.valueOf(applicationProperty.getCount());
        //滑块的大小
        int blockSizeByStr = Integer.valueOf(applicationProperty.getBlockSizeByStr());
        int blockSizeByNum = Integer.valueOf(applicationProperty.getBlockSizeByNum());
        //存手机号的map集合
        Map<String,String> phoneMap = new HashMap<>();

        while (true){
            //循环遍历街道，根据街道和短号查询数据
            List<Base_addr> list = base_addrMapper.selectData(startCount,count);
            //如果都处理完了就跳出循环
            if (list.size() == 0) {
                break;
            }

            for (Base_addr baseAddr : list) {
                //判断该手机号是否被处理过
                String s = phoneMap.get(baseAddr.getPhone());
                //说明被处理过,不需要处理了
                if(!StringUtils.isBlank(s)){
                    continue;
                }
                //没被处理过,就存进map集合
                phoneMap.put(baseAddr.getPhone(),baseAddr.getPhone());

                List<Base_addr> baseAddrs = base_addrMapper.selectDataByPhone(baseAddr.getPhone(), null);
                log.info("*********开始处理手机号为: {},共 {} 条数据*********",baseAddr.getPhone(),baseAddrs.size());
                //多线程相似度方法
                list = processThread(baseAddrs, blockSizeByNum, blockSizeByStr,executor, executor1);
            }

            //插入数据
            insertMsg(list);
        }
    }


    /**
     * 自己撞自己
     * @param executor
     */
    @Override
    public void compareSelf(ThreadPoolTaskExecutor executor) {
        //获取温州的所有街道
        Map<String, Object> map = bs_startWayService.getMap();
        List<Bs_street> streetNames = (List<Bs_street>) map.get("streetMessage");
        CountDownLatch countDownLatch = new CountDownLatch(streetNames.size());
        //滑块的大小
        int blockSizeByStr = Integer.valueOf(applicationProperty.getBlockSizeByStr());
        int blockSizeByNum = Integer.valueOf(applicationProperty.getBlockSizeByNum());

        for (Bs_street streetName : streetNames) {
            //将数据进行自己碰自己
            executor.execute(new CompareRunnable1(blockSizeByNum,blockSizeByStr,countDownLatch,streetName.getStreetName()));
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }

        //获取所有区
        List<Bs_area> areaMessage = (List<Bs_area>) map.get("areaMessage");
        for (Bs_area bs_area : areaMessage) {
            //将数据进行自己碰自己
            executor.execute(new CompareRunnable2(blockSizeByNum,blockSizeByStr,countDownLatch,bs_area.getAreaName()));
        }
    }

    @Async("asyncPromiseExecutor")
    @Override
    public void function(int start, int end) {
        base_addrMapper.function(start,end);
        log.info("***************** 进度：  {} ----》 {} *****************",start,end);
    }

    @Async("asyncPromiseExecutor")
    @Override
    public void function1(int start, int end) {
        //获取third_addr表指定步进值的数据
        List<String> ids = base_addrMapper.function1(start,end);
        //更新基准表数据对应的经纬度
        int i = base_addrMapper.function2(start, end);
        //获取基准表对应数据
        List<Base_addr> base_addrs = base_addrMapper.selectByIds(ids);

        for (Base_addr base_addr : base_addrs) {
            boolean flag = false;
            //根据街道获取对应社区
            if(!TextUtils.isEmpty(base_addr.getStreet()) || !TextUtils.isEmpty(base_addr.getLongitude())){
                List<SQ> sqList = base_addrMapper.getSQSJ(base_addr.getStreet());
                //判断经纬度是否在社区经纬度多边形范围内
                for (SQ sq : sqList) {
                    boolean pointInPolygon = GraphUtils.isPointInPolygon(base_addr.getLongitude(),base_addr.getLatitude(),sq.getBound());
                    if(pointInPolygon){
                        base_addr.setCommunity(sq.getName());
                        flag = true;
                        break;
                    }
                }
            }

            //根据区域判断经纬度是否在责任区经纬度多边形范围内
            //根据街道获取对应社区
            if(!TextUtils.isEmpty(base_addr.getArea()) || !TextUtils.isEmpty(base_addr.getLongitude())){
                List<SQ> sqList = base_addrMapper.getZRQSJ(base_addr.getArea());
                //判断经纬度是否在社区经纬度多边形范围内
                for (SQ sq : sqList) {
                    boolean pointInPolygon = GraphUtils.isPointInPolygon(base_addr.getLongitude(),base_addr.getLatitude(),sq.getBound());
                    if(pointInPolygon){
                        base_addr.setResponsibilityArea(sq.getName());
                        flag = true;
                        break;
                    }
                }
            }
            if(flag){
                base_addrMapper.updateBasicsAdd(base_addr);
            }
        }
        log.info("***************** 进度：  {} ----》 {} *****************",start,end);
    }

    /**
     * 插入数据
     *
     * @param list
     */
    public void insertMsg(List<Base_addr> list) {
        //插入合并值，返回基准值
        List<Base_addr> base_addrs = insertMsgByMerge(list);

        //插入基准值
        try {
            if (base_addrs != null && base_addrs.size() > 0) {
                base_addrMapper1.insert5_2(base_addrs);
            }
        } catch (Exception e1) {
            log.error(e1.getMessage());
        }
    }

    /**
     * 插入合并值
     *
     * @param list
     * @return 返回基准值
     */
    public List<Base_addr> insertMsgByMerge(List<Base_addr> list) {
        /*插入数据库阶段*/
        List<Base_addr> baseAddrs = new ArrayList<>();

        for (int k = 0; k < list.size(); k++) {
            if (list.get(k) == null) {
                list.remove(k);
                k = k - 1;
                log.error("删除为null");
            }
            //用于打散集合内的值
            if (list.get(k).getP2type() == 223) {
                //设置合并数
                int count = list.get(k).getMergeNum();
                for (Base_addr baseAddr : list) {
                    if(list.get(k).getId().equals(baseAddr.getContrastId())){
                        //设置基准值的地址合并数值和最早最晚收件时间
                        count = count +1 + baseAddr.getMergeNum();
                        if(baseAddr.getLatestTime().compareTo(list.get(k).getLatestTime())>0){
                            list.get(k).setLatestTime(baseAddr.getLatestTime());
                        }
                        if(baseAddr.getEarliestTime().compareTo(list.get(k).getEarliestTime())<0){
                            list.get(k).setEarliestTime(baseAddr.getEarliestTime());
                        }
                    }
                }

                list.get(k).setMergeNum(count);
                baseAddrs.add(list.get(k));
                list.remove(k);
                k = k - 1;
            }
        }

        try {
            if (list != null && list.size() > 0) {
                base_addrMapper1.insert3_2(list);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return baseAddrs;
    }

    /**
     * 相似度匹配方法
     *
     * @param baseAddrs 需要处理的数据
     * @param blockSizeByNum 数字阈值
     * @param blockSizeByStr 字符阈值
     * @param executor 线程池1
     * @param executor1 线程池2
     * @return
     */
    public List<Base_addr> processThread(List<Base_addr> baseAddrs, int blockSizeByNum, int blockSizeByStr,ThreadPoolTaskExecutor executor, ThreadPoolTaskExecutor executor1) {
        //相似度碰撞方法
        Future<List<Base_addr>> submit = executor1.submit(new ProcessPhoneRunnable(blockSizeByNum, blockSizeByStr,executor, baseAddrs));

        try {
            //该方法会阻塞，在线程执行完后才执行
            baseAddrs = submit.get();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } catch (ExecutionException e) {
            log.error(e.getMessage());
        }
        return baseAddrs;
    }
}





