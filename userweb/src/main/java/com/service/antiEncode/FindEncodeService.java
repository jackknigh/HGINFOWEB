package com.service.antiEncode;

import com.dao.entity.lwaddress.Sec_addr;

/**
 *
 * @version version 1.0
 * @ClassName antiEncode
 * @Description: 〈调用高德逆编码接口接口〉
 * @date 2019/06/06
 */
public interface FindEncodeService {
    /**
     * 通过经纬度获取地址
     *
     *
     * @return
     */
     Sec_addr getLngLatFromOneAddr(Sec_addr sec_addr, String key, String url);
}