package com.service.lwaddress;

import com.dao.entity.lwaddress.Base_addr;

import java.util.Map;

public interface Base_addrService {
    Base_addr addrSet(String address, Map<String, Object> allMessage);
    Base_addr addrSet(Base_addr base_addr, Map<String, Object> allMessage);
}
