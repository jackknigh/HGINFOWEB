package com.service.lwaddress.impl;

import com.alibaba.fastjson.JSON;
import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.Base_addrMapper;
import com.dao.db2.lwaddress.PhoneMapper;
import com.dao.db3.lwaddr.Base_addrMapper1;
import com.dao.entity.lwaddress.Address;
import com.dao.entity.lwaddress.Base_addr;
import com.dao.entity.lwaddress.Phone;
import com.service.lwaddress.ProcessInterfService;
import com.utils.sys.lwaddress.CompareRunnable;
import com.utils.sys.lwaddress.DateUtil;
import com.utils.sys.lwaddress.ListUtil;
import com.utils.sys.lwaddress.ProcessPhoneRunnable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class ProcessInterfServiceImpl implements ProcessInterfService {

    private static final Logger log = LoggerFactory.getLogger(ProcessInterfServiceImpl.class);

    //todo 被比较的值
    volatile List<Base_addr> volList = new ArrayList<>();

    @Autowired
    private HgApplicationProperty applicationProperty;

    @Autowired
    private PhoneMapper phoneMapper;

    @Autowired
    private Base_addrMapper base_addrMapper;

    @Autowired
    private Base_addrMapper1 base_addrMapper1;

    @Async("asyncPromiseExecutor")
    @Override
    public void processInterf(int start, int batchcCount, BigDecimal n, ThreadPoolTaskExecutor executor, ThreadPoolTaskExecutor executor1) {

        /*n1是数字的权重*/
        //滑块的大小
        int blockSizeByStr = Integer.valueOf(applicationProperty.getBlockSizeByStr());
        int blockSizeByNum = Integer.valueOf(applicationProperty.getBlockSizeByNum());
        //标准地址再清洗
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

        if ("0".equals(applicationProperty.getStandardAddress())) {
            log.info("开始手机号处理");
            //查询phone表,phone的号码都短号，前三后四
            List<Phone> listPhone = phoneMapper.selectAll(start, batchcCount);
            log.info("********查询到手机号:" + JSON.toJSONString(listPhone) + "**********");
            //搜索街道
            List<Address> addrPhoneList = phoneMapper.selectAllAddressPhone();
            log.info("********查询到街道:" + addrPhoneList.size() + "条**********");
            //获取指定手机号组的每个元素
            for (int i = 0; i < listPhone.size(); i++) {
                String shortPhone = listPhone.get(i).getPhone();

                //获取每个街道
                for (Address address : addrPhoneList) {
                    //循环遍历街道，根据街道和短号查询数据
                    List list = base_addrMapper.selectByShortPhone(shortPhone, address.getStreet());
                    if (list.size() == 0) {
                        continue;
                    }

                    log.info("开始处理手机号为: {},街道为：{},共 {} 条数据",shortPhone,address.getStreet(),list.size());
                    //多线程相似度方法
                    list = processThread(list, blockSizeByNum, blockSizeByStr,executor, executor1);
                    //插入数据
                    insertMsg(list);
                }
            }
        }
            /*for (int j = 0; j < list.size();j++) {
                if (list.get(j).getP2type() == 223) {
                    //basList.add(list.get(j));
                   try {
                        bs_addrMapper.insert5(list.get(j));
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(JSON.toJSONString(list.get(j)));
                    }
                } else {
                  *//*  if (list.get(j).getP2type() == 222 ||list.get(j).getP2type() == 224 ) {*//*
                        //meList.add(list.get(j));
              *//*      }*//*
                    try {
                        bs_addrMapper.insert3(list.get(j));
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(JSON.toJSONString(list.get(j)));
                    }
                }
            }*/

            /*try {
                bs_addrMapper.insert3(meList);
            } catch (Exception e) {
                 e.printStackTrace();
                log.error(JSONObject.toJSONString(meList));
                log.error(e.getMessage());

            }*/

          /*  try {
                bs_addrMapper.insert4(basList);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        log.info("结束++++++++++++++++++++++++++++++++++++++++++++++");
    }

    /**
     * 相似度碰撞方法
     * @param executor
     */
    @Override
    public void compare(ThreadPoolTaskExecutor executor) {
        long startTime = System.currentTimeMillis();
        //滑块的大小
        int blockSizeByStr = Integer.valueOf(applicationProperty.getBlockSizeByStr());
        int blockSizeByNum = Integer.valueOf(applicationProperty.getBlockSizeByNum());
        int splitListSize = applicationProperty.getSplitListSize();
        //todo 给共享变量赋值被比较的集合--》200W(db3)basics_addr数据
        volList = base_addrMapper1.selectBaseAddr();
        log.info("有 {} 条被比较值数据",volList.size());

//        Base_addr baseAddr = new Base_addr();
//        baseAddr.setShortAddr("庐山路凤凰城2-104");
//        System.out.println("基准值:"+baseAddr.getShortAddr());
//        volList.add(baseAddr);

//        //todo 比较的集合2000W(db2)标准街道
        List<Base_addr> baseAddrs = base_addrMapper.selectBaseAddr();
        log.info("有 {} 条比较值数据",baseAddrs.size());

//        Base_addr baseAddr1 = new Base_addr();
//        baseAddr1.setShortAddr("凤凰城2-104");
//        System.out.println("标准值:"+baseAddr1.getShortAddr());
//        List<Base_addr> baseAddrs = new ArrayList<>();
//        baseAddrs.add(baseAddr1);

        CountDownLatch countDownLatch = new CountDownLatch(baseAddrs.size());
        //拆成大小为10的集合
        List<List<Base_addr>> listList = ListUtil.splitList(baseAddrs, splitListSize);
        log.info("比较值数据被拆成 {} 个大小为 {} 的集合",listList.size(),listList.get(0).size());

        //开启多线程匹配
        for (int i = 0; i < listList.size(); i++) {
//            log.info("送入第 {} 个集合",i);
            executor.execute(new CompareRunnable(i,blockSizeByNum,blockSizeByStr,listList.get(i),volList,countDownLatch));
        }


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }

        //数据切割,如果需要插入的数据的结果大于20w就切割成20W一份
        List<List<Base_addr>> lists = ListUtil.splitList(volList, 200000);

        log.info("切割成：{} 份集合",lists.size());
        for (int i = 0; i < lists.size(); i++) {
            //插入合并值
            List<Base_addr> baseAddrs1 = insertCompareByMerge(lists.get(i));

            try {
                if (baseAddrs1 != null && baseAddrs1.size() > 0) {
                    //todo 插入基准值
                    log.info("准备插入数据：{} 条 ",baseAddrs1.size());
                    base_addrMapper1.insert5_2_1(baseAddrs1);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error(JSON.toJSONString(baseAddrs1));
            }
        }

        long endTime = System.currentTimeMillis();
        log.info("总用时：{}",endTime-startTime);
    }

    /**
     * 插入数据
     *
     * @param list
     */
    public void insertMsg(List<Base_addr> list) {
        String insertIndex = applicationProperty.getInsertIndex();
        List<Base_addr> indexAddr = new ArrayList<>();

        //插入合并值，返回基准值
        List<Base_addr> base_addrs = insertMsgByMerge(list);

        //如果是增量
        if (insertIndex.equals("1")) {
            for (int k = 0; k < base_addrs.size(); k++) {
                if (base_addrs.get(k).getP3type() == 223) {
                    base_addrs.remove(k);
                    k = k - 1;
                }
            }

            for (int k = 0; k < list.size(); k++) {
                //将插入合并值表的字段进行判断如果是223就将新值插入basics_addr_1
                if (list.get(k).getP3type() == 223) {
                    Base_addr base_addr = list.get(k);
                    indexAddr.add(base_addr);
                    /*若不相等将进行存储过程，将merge表中的合并项的contrastid改变,同时删除basics表中的该字段*/
                }
            }

            try {
                if (indexAddr != null && indexAddr.size() > 0) {
                    //将出现修改的字段插入indexaddr标记表(日记记录新来的值替换了旧值)
                    base_addrMapper1.insert4(indexAddr);
                    base_addrMapper1.insert6(indexAddr);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error(JSON.toJSONString(indexAddr));
            }
        }

        //插入基准值
        try {
            if (base_addrs != null && base_addrs.size() > 0) {
                base_addrMapper1.insert5_2(base_addrs);
            }
        } catch (Exception e1) {
            log.error(e1.getMessage());
            log.error(JSON.toJSONString(base_addrs));
        }
    }


    /**
     * 插入合并值
     *
     * @param list1
     * @return 返回基准值
     */
    public List<Base_addr> insertCompareByMerge(List<Base_addr> list1) {
        /*插入数据库阶段*/
        List<Base_addr> mergeAddrs = new ArrayList<>();
        List<Base_addr> baseAddrss = new ArrayList<>();
        for (int k = 0; k < list1.size(); k++) {
            if(list1.get(k) == null){
                continue;
            }
            if (!StringUtils.isBlank(list1.get(k).getContrastId())) {
                mergeAddrs.add(list1.get(k));
            }else {
                //todo 两份，一份是把自身插入合并表，一份是当做基准数据使用
                list1.get(k).setContrastId(list1.get(k).getId());
                baseAddrss.add(list1.get(k));
            }
        }

        try {
            if (mergeAddrs != null && mergeAddrs.size() > 0) {
                //todo 插入合并值
                log.info("准备插入数据：{} 条 ",mergeAddrs.size());
                base_addrMapper1.insert3_2_1(mergeAddrs);
            }
            if (baseAddrss != null && baseAddrss.size() > 0) {
                //todo 插入标准合并值
                log.info("准备插入数据：{} 条 ",baseAddrss.size());
                base_addrMapper1.insert3_2_1(baseAddrss);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(JSON.toJSONString(list1));
        }
        return baseAddrss;
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
            log.error(JSON.toJSONString(list));
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
        //需处理的数据总数
        //配置的指定值大小
        int splitListSize = applicationProperty.getSplitListSize();
        //如果总数大于指定配置值
        if (baseAddrs.size() >= splitListSize) {
            //判断结果是否大于配置的指定值的百分之70，如果大于配置的指定值的百分之70，就切割成若干个小于等于指定值的集合
            for (int j = 0; j < 2 && baseAddrs.size() > (splitListSize * 0.7); j++) {
                List<Base_addr> list1 = new ArrayList<>();
                flag = true;
                //等份切割集合
                List<List<Base_addr>> listList = ListUtil.splitList(baseAddrs, splitListSize);
                log.info("把 {} 条数据切割成 {} 个集合。。。。。。。。。。第 {} 次循环", baseAddrs.size(), listList.size(), j + 1);
                Future<List<Base_addr>>[] future = new Future[listList.size()];
                //对拆分后的若干个集合进行相似度处理
                for (int a = 0; a < listList.size(); a++) {
                    future[a] = executor1.submit(new ProcessPhoneRunnable(blockSizeByNum, blockSizeByStr,executor, listList.get(a), flag));
                }

                //获取多线程的处理结果
                try {
                    for (int k = 0; k < future.length; k++) {
                        if (future[k] == null) {
                            break;
                        }
                        list1.addAll(future[k].get());
                    }
                    //将所有合并值入库，返回所有的基准值再次进行判断
                    baseAddrs = insertMsgByMerge(list1);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                } catch (ExecutionException e) {
                    log.error(e.getMessage());
                }
            }
        }

        long startTime = System.currentTimeMillis();
        //将最后结果再比一次
        log.info("处理最后一次比较");
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





