package com.service.lwaddress;

import com.dao.entity.lwaddress.Bs_city;

import java.util.List;
import java.util.Map;

public interface Bs_cityService {
    Map cityJudge(String address, List<Bs_city> cityAllName);
}
