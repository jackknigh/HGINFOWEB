package com.service.lwaddress;

import com.dto.vo.BaseAddrVo;

import java.util.List;

public interface MsgSearchService {
    List<BaseAddrVo> queryMsg(String name, String phone, String address);
}
