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
import org.springframework.beans.factory.annotation.Value;
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
import java.util.concurrent.ThreadPoolExecutor;

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

        //根据手机号短号清洗数据
        if ("0".equals(applicationProperty.getStandardAddress()) || "2".equals(applicationProperty.getStandardAddress())) {
            log.info("*********开始手机号处理*********");
            //查询phone表,phone的号码都短号，前三后四
            List<Phone> listPhone = phoneMapper.selectAll(start, start+batchcCount);

            log.info("*********开始处理进度为：{} --》 {} 的数据*********",start,start+batchcCount);

            //获取指定手机号组的每个元素
            for (int i = 0; i < listPhone.size(); i++) {
                String shortPhone = listPhone.get(i).getPhone();
                if(StringUtils.isBlank(shortPhone) && "2".equals(applicationProperty.getStandardAddress())){
                    continue;
                }

                //获取指定短号码数据
                List list = base_addrMapper.selectByShortPhone(shortPhone, null);
                if (list.size() == 0) {
                    continue;
                }

                log.info("*********手机号为: {},共 {} 条数据*********",shortPhone,list.size());
                //多线程相似度方法
                list = processThread(list, blockSizeByNum, blockSizeByStr,executor, executor1);
                //短地址为空可能会导致集合为空
                if (list.size() == 0) {
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

                //循环遍历街道，根据街道和短号查询数据
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
        log.info("***************结束***************");
    }

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
        Bs_street bs_street = new Bs_street();
        streetNames.add(bs_street);

        for (Bs_street streetName : streetNames) {
            //滑块的大小
            int blockSizeByStr = Integer.valueOf(applicationProperty.getBlockSizeByStr());
            int blockSizeByNum = Integer.valueOf(applicationProperty.getBlockSizeByNum());

            int count = Integer.valueOf(applicationProperty.getCount());
            int start = Integer.valueOf(applicationProperty.getStartCount());

            while (true){
                //需碰撞的数据
                List<Base_addr> baseAddrs1 = base_addrMapper.selectBaseAddrs(streetName.getStreetName(),start,count);
                if(TextUtils.isEmpty(baseAddrs1)){
                    break;
                }

                log.info("*********街道 ：{}  有 {} 条比较值数据*********",streetName.getStreetName(),baseAddrs1.size());

                //标准数据
                List<Base_addr> volList = base_addrMapper.selectBaseAddr2(streetName.getStreetName());

                log.info("*********有 {} 条被比较值数据*********",volList.size());
                executor.execute(new CompareRunnable3(blockSizeByNum,blockSizeByStr,volList,baseAddrs1));
                start = start + count;
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
        List<Base_addr> baseAddrs1 = new ArrayList<>();
        List<Base_addr> baseAddrs2 = new ArrayList<>();
        List<Base_addr> baseAddrs3 = new ArrayList<>();
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
                if (k % 3 == 0) {
                    baseAddrs1.add(list.get(k));
                }
                if (k % 3 == 1) {
                    baseAddrs2.add(list.get(k));
                }
                if (k % 3 == 2) {
                    baseAddrs3.add(list.get(k));
                }
                list.remove(k);
                k = k - 1;
            }
        }

        baseAddrs.addAll(baseAddrs1);
        baseAddrs.addAll(baseAddrs2);
        baseAddrs.addAll(baseAddrs3);

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
     * 大集合切割相似度匹配方法
     *
     * @param baseAddrs 需要处理的数据
     * @param blockSizeByNum 数字阈值
     * @param blockSizeByStr 字符阈值
     * @param executor 线程池1
     * @param executor1 线程池2
     * @return
     */
    public List<Base_addr> processThread(List<Base_addr> baseAddrs, int blockSizeByNum, int blockSizeByStr,ThreadPoolTaskExecutor executor, ThreadPoolTaskExecutor executor1) {
        //用来标记是否是集合切割操作，是的话需要修改合并表的关联id
        boolean flag = false;

        long startTime = System.currentTimeMillis();

        //相似度碰撞方法
        Future<List<Base_addr>> submit = executor1.submit(new ProcessPhoneRunnable(blockSizeByNum, blockSizeByStr,executor, baseAddrs, flag));

        try {
            baseAddrs = submit.get();
            long endTime = System.currentTimeMillis();
            log.info("处理 {} 条数据用时 {}毫秒",baseAddrs.size(),endTime-startTime);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } catch (ExecutionException e) {
            log.error(e.getMessage());
        }
        return baseAddrs;
    }
}





