package com.service.lwaddress;

import com.dao.entity.lwaddress.Bs_city;
import com.dao.entity.lwaddress.Bs_province;

import java.util.List;
import java.util.Map;

public interface Bs_provinceService {
    Map provinceJudge(String address, List<Bs_province> provinceAllName);

    boolean provinceAllJudge(String address, List<Bs_city> provinceAllName);
}
