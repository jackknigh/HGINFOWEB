package com.service.lwaddress;

import com.dao.entity.lwaddress.Bs_street;

import java.util.List;
import java.util.Map;

public interface Bs_streetService {

    Map streetJudge(String address, List<Bs_street> streetAllName);
}
