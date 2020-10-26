package com.service.antiEncode.impl;

import com.service.antiEncode.EncodeStartWayService;
import com.service.antiEncode.InsertEncodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;


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
        int count = total / batchcCount + 1;

        //初始化高德key和url
        insertEncodeService.init();
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int j = 0; j < count; j++) {
            if (batchcCount != 1) {
                if (j == total / batchcCount) {
                    batchcCount = total - j * batchcCount;
                }
                if (batchcCount == 0) {
                    break;
                }
                insertEncodeService.insertLngLat(start, batchcCount,countDownLatch);
                start = start + batchcCount;
            } else {
                if (total / batchcCount == 0) {
                    break;
                }
                insertEncodeService.insertLngLat(start, batchcCount,countDownLatch);
                start = start + batchcCount;
            }
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //异常数据重试机制
        log.error("************开始异常重试机制************");
        for (int i = 0; i < 10; i++) {
            insertEncodeService.errorProcess();
        }
        log.error("************异常重试机制结束************");

    }


    /*
@param sum为总处理数
@param count为一次处理数
* */
    @Override
    public void getAddr(int start, int total, int batchcCount) {
        int count = total / batchcCount + 1;

        //初始化高德key和url
        insertEncodeService.init();
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int j = 0; j < count; j++) {
            if (batchcCount != 1) {
                if (j == total / batchcCount) {
                    batchcCount = total - j * batchcCount;
                }
                if (batchcCount == 0) {
                    break;
                }
                insertEncodeService.getAddr(start, batchcCount,countDownLatch);
                start = start + batchcCount;
            } else {
                if (total / batchcCount == 0) {
                    break;
                }
                insertEncodeService.getAddr(start, batchcCount,countDownLatch);
                start = start + batchcCount;
            }
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
@param sum为总处理数
@param count为一次处理数
* */
    @Override
    public void getAddrByBD(int start, int total, int batchcCount) {
        int count = total / batchcCount + 1;

        //初始化高德key和url
        insertEncodeService.init();
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int j = 0; j < count; j++) {
            if (batchcCount != 1) {
                if (j == total / batchcCount) {
                    batchcCount = total - j * batchcCount;
                }
                if (batchcCount == 0) {
                    break;
                }
                insertEncodeService.getAddrByBD(start, batchcCount,countDownLatch);
                start = start + batchcCount;
            } else {
                if (total / batchcCount == 0) {
                    break;
                }
                insertEncodeService.getAddrByBD(start, batchcCount,countDownLatch);
                start = start + batchcCount;
            }
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /*
@param sum为总处理数
@param count为一次处理数
* */
    @Override
    public void getAddrBDD(int start, int total, int batchcCount) {
        int count = total / batchcCount + 1;

        //初始化高德key和url
        insertEncodeService.init();
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int j = 0; j < count; j++) {
            if (batchcCount != 1) {
                if (j == total / batchcCount) {
                    batchcCount = total - j * batchcCount;
                }
                if (batchcCount == 0) {
                    break;
                }
                insertEncodeService.getAddrBD(start, batchcCount,countDownLatch);
                start = start + batchcCount;
            } else {
                if (total / batchcCount == 0) {
                    break;
                }
                insertEncodeService.getAddrBD(start, batchcCount,countDownLatch);
                start = start + batchcCount;
            }
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
