package com.service.antiEncode;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @version version 1.0
 * @ClassName antiEncode
 * @Description: 〈调用高德逆编码接口接口〉
 * @date 2019/06/06
 */
public interface InsertEncodeService {
    /**
     * 通过经纬度获取地址
     *
     *
     * @return
     */
     void  insertLngLat(int start, int number, CountDownLatch countDownLatch);

    /**
     * 初始化配置信息
     */
    void  init();

    /**
     * 异常数据处理
     */
    void errorProcess();

}