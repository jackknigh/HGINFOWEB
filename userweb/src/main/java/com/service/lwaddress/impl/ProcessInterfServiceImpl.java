package com.service.lwaddress.impl;

import com.alibaba.fastjson.JSON;
import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.Base_addrMapper;
import com.dao.db2.lwaddress.PhoneMapper;
import com.dao.db3.lwaddr.Base_addrMapper1;
import com.dao.db3.lwaddr.PhoneMapper1;
import com.dao.entity.lwaddress.Address;
import com.dao.entity.lwaddress.Base_addr;
import com.dao.entity.lwaddress.Phone;
import com.service.lwaddress.ProcTaskService;
import com.service.lwaddress.ProcessInterfService;
import com.service.lwaddress.ProcessService;
import com.utils.sys.lwaddress.AsciiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProcessInterfServiceImpl implements ProcessInterfService {

    private static final Logger log = LoggerFactory.getLogger(ProcessInterfServiceImpl.class);

    @Autowired
    private HgApplicationProperty applicationProperty;

    @Autowired
    PhoneMapper phoneMapper;

    @Autowired
    PhoneMapper1 phoneMapper1;

    @Autowired
    ProcessService processService;

    @Autowired
    ProcTaskService procTaskService;

    @Autowired
    Base_addrMapper base_addrMapper;

    @Autowired
    Base_addrMapper1 base_addrMapper1;

    @Async("asyncPromiseExecutor")
    @Override
    public void processInterf(int start, int batchcCount, BigDecimal n,String reg) {

        String insertIndex = applicationProperty.getInsertIndex();

        /*n1是数字的权重*/
        //滑块的大小
        int blockSizeByStr = Integer.valueOf(applicationProperty.getBlockSizeByStr());
        int blockSizeByNum = Integer.valueOf(applicationProperty.getBlockSizeByNum());
        //标准地址再清洗
        if ("1".equals(applicationProperty.getStandardAddress())) {
            log.info("开始执行标准街道清洗。。。。。。。。。。。。。。。。");
            List<Address> addresses = phoneMapper.selectAllAddress(start, batchcCount);
            log.info("********查询到街道:"+JSON.toJSONString(addresses)+"**********");
            List<Base_addr> indexAddr = new ArrayList<>();

            for (int i = 0; i < addresses.size(); i++) {
                long startTime = System.currentTimeMillis();
                List<Base_addr> addressMessage;
                Address address = addresses.get(i);
                log.info("********开始执行街道:"+address.getStreet()+"**********");
                if(address.getStreet().equals("")){
                    address.setStreet(null);
                }
                addressMessage = base_addrMapper.selectByAddress(address);
                log.info("街道: "+address.getStreet()+" 共查到 "+addressMessage.size()+" 条数据");
                //重置P2为0，因为原先的数据分222和223
                for (int j = 0; j < addressMessage.size(); j++) {
                    addressMessage.get(j).setP2type(0);
                }

                int s = -1;
                List<Base_addr> list = new ArrayList<>();

                for (int j = 0; j < addressMessage.size(); j++) {
                    //对数据进行处理，参数是所有数据，一个空的集合，滑块值
                    Map<String, List<Base_addr>> map = processService.processService(addressMessage, list, blockSizeByStr, blockSizeByNum, reg);
                    if ((map.get("insertMessage")).size() != s) {
                        addressMessage = map.get("addressMessage");
                        list = map.get("insertMessage");
                        s = (map.get("insertMessage")).size();
                    } else {
                        break;
                    }
                }

                /*插入数据库阶段*/
                List<Base_addr> base_addrs = new ArrayList<>();
                for (int k = 0; k < list.size(); k++) {
                    if (list.get(k).getP2type() == 223) {
                        base_addrs.add(list.get(k));
                        list.remove(k);
                        k = k - 1;
                    }
                }

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
                        if (!indexAddr.isEmpty()) {
                            //将出现修改的字段插入indexaddr标记表(日记记录新来的值替换了旧值)
                            base_addrMapper1.insert4(indexAddr);
                            base_addrMapper1.insert6(indexAddr);
                        }
                    } catch (Exception e) {
                        log.error(JSON.toJSONString(indexAddr));
                    }
                }

                try {
                    if (!list.isEmpty()) {
                        base_addrMapper1.insert3_2(list);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(JSON.toJSONString(list));
                    for (Base_addr _base_addr : list) {
                        try {
                            base_addrMapper1.insert3(_base_addr);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            log.error(JSON.toJSONString(_base_addr));
                        }
                    }
                }

                try {
                    if (!base_addrs.isEmpty()) {
                        base_addrMapper1.insert5_2(base_addrs);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    log.error(JSON.toJSONString(base_addrs));
                    for (Base_addr _base_addr : base_addrs) {
                        try {
                            base_addrMapper1.insert5(_base_addr);
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error(JSON.toJSONString(_base_addr));
                        }
                    }
                }
                long endTime = System.currentTimeMillis();
                long time = endTime - startTime;
                log.info("*************************"+"处理"+addressMessage.size()+"条数据共用了:"+time+"毫秒");
            }
            indexAddr.clear();
        }

        if ("0".equals(applicationProperty.getStandardAddress())) {
            //查询phone表,phone的号码都短号，前三后四
            List<Phone> listPhone = phoneMapper.selectAll(start, batchcCount);
//        List<Base_addr> addressMessage = new ArrayList<>();
//        Map<String, List<Base_addr>> map = new HashMap<>();
            List<Base_addr> indexAddr = new ArrayList<>();
            for (int i = 0; i < listPhone.size(); i++) {
                String shortPhone;
                List<Base_addr> addressMessage;
                shortPhone = listPhone.get(i).getPhone();
                //            long startTime = System.currentTimeMillis();
                addressMessage = base_addrMapper.selectByShortPhone(shortPhone);

//            long endTime = System.currentTimeMillis();
//
//            if (endTime - startTime < 50) {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    log.error(e.getMessage());
//                    e.printStackTrace();
//                }
//            }

                //重置P2为0，因为原先的数据分222和223
                for (int j = 0; j < addressMessage.size(); j++) {
                    addressMessage.get(j).setP2type(0);
                }

                int s = -1;
                List<Base_addr> list = new ArrayList<>();

                for (int j = 0; j < addressMessage.size(); j++) {
                    //对数据进行处理，参数是所有数据，一个空的集合，滑块值
                    Map<String, List<Base_addr>> map = processService.processService(addressMessage, list, blockSizeByStr, blockSizeByNum, reg);
                    if ((map.get("insertMessage")).size() != s) {
                        addressMessage = map.get("addressMessage");
                        list = map.get("insertMessage");
                        s = (map.get("insertMessage")).size();
                    } else {
                        break;
                    }
                }

                /*插入数据库阶段*/
                List<Base_addr> base_addrs = new ArrayList<>();
                for (int k = 0; k < list.size(); k++) {
                    if (list.get(k).getP2type() == 223) {
                        base_addrs.add(list.get(k));
                        list.remove(k);
                        k = k - 1;
                    }
                }

                if (insertIndex.equals("1")) {
                    for (int k = 0; k < base_addrs.size(); k++) {
                        if (base_addrs.get(k).getP3type() == 223) {
                            base_addrs.remove(k);
                            k = k - 1;
                        }
                    }

                    for (int k = 0; k < list.size(); k++) {
//                Base_addr base_addr=new Base_addr();
                        //将插入合并值表的字段进行判断如果是223就将新值插入basics_addr_1
                        if (list.get(k).getP3type() == 223) {
                            Base_addr base_addr = list.get(k);
                            indexAddr.add(base_addr);
                            /*若不相等将进行存储过程，将merge表中的合并项的contrastid改变,同时删除basics表中的该字段*/
                        }
                    }

                    try {
                        if (!indexAddr.isEmpty()) {
                            //将出现修改的字段插入indexaddr标记表(日记记录新来的值替换了旧值)
                            base_addrMapper1.insert4(indexAddr);
                            base_addrMapper1.insert6(indexAddr);
                        }
                    } catch (Exception e) {
//                    log.error(JSON.toJSONString(list));
                        log.error(JSON.toJSONString(indexAddr));
                    }

//                try {
//                    if (!indexAddr.isEmpty()) {
//                        //将基准值被修改的字段插入basics_Addr_1表(将旧的基准值插入)
//                          base_addrMapper1.insert6(indexAddr);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    log.error(JSON.toJSONString(list));
//                }
                }

                try {
                    if (!list.isEmpty()) {
                        base_addrMapper1.insert3_2(list);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(JSON.toJSONString(list));
                    for (Base_addr _base_addr : list) {
                        try {
                            base_addrMapper1.insert3(_base_addr);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            log.error(JSON.toJSONString(_base_addr));
                        }
                    }
                }

                try {
                    if (!base_addrs.isEmpty()) {
                        base_addrMapper1.insert5_2(base_addrs);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    log.error(JSON.toJSONString(base_addrs));
                    for (Base_addr _base_addr : base_addrs) {
                        try {
                            base_addrMapper1.insert5(_base_addr);
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error(JSON.toJSONString(_base_addr));
                        }
                    }
                }
                indexAddr.clear();
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

    }
}





