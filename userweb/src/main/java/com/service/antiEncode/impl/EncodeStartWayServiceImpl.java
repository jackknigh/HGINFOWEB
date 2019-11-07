package com.service.antiEncode.impl;

import com.alibaba.fastjson.JSONObject;
import com.dao.db2.lwaddress.SecAddrMapper;
import com.dao.entity.lwaddress.Sec_addr;
import com.service.antiEncode.EncodeStartWayService;
import com.service.antiEncode.FindEncodeService;
import com.service.antiEncode.InsertEncodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("encodeStartWayService")
public class EncodeStartWayServiceImpl implements EncodeStartWayService {
    private static final Logger log = LoggerFactory.getLogger(InsertEncodeServiceImpl.class);

    @Autowired
    private InsertEncodeService insertEncodeService;

    /*
    @param sum为总处理数
    @param count为一次处理数
    * */
    @Override
    public void startway(int start, int total, int batchcCount) {
        log.info("start susscces");
        int count = total / batchcCount + 1;
//        for (int j = 0; j < count; j++) {
            insertEncodeService.init();
//            log.info("current j:" + j);
//            if (batchcCount != 1) {
//                if (j == total / batchcCount) {
//                    batchcCount = total - j * batchcCount;
//                }
//                if (batchcCount == 0) {
//                    log.info("success");
//                    break;
//                }
//                insertEncodeService.insertLngLat(start, batchcCount);
//                start = start + batchcCount;
//                log.info("finish susscces");
//            } else {
//                if (total / batchcCount == 0) {
//                    log.info("success");
//                    break;
//                }
//                insertEncodeService.insertLngLat(start, batchcCount);
//                start = start + batchcCount;
//                log.info("finish susscces");
//            }
//        }

        //异常数据重试机制
        for (int i = 0; i < 10; i++) {
            insertEncodeService.errorProcess();
        }

    }
}
