package com.service.lwaddress;

import com.dao.entity.lwaddress.Base_addr;

import java.math.BigDecimal;

public interface Bs_utilService {
    void addressStart(int number1, int number2, BigDecimal n, String reg);

    void increment(Base_addr baseAddr,String reg);
}
