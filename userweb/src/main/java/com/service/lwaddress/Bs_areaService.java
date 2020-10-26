package com.service.lwaddress;

import com.dao.entity.lwaddress.Bs_area;

import java.util.List;
import java.util.Map;

public interface Bs_areaService {
    Map areaJudge(String address, List<Bs_area> areaAllName);
}
