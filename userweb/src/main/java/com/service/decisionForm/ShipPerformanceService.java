package com.service.decisionForm;

import java.util.List;

public interface ShipPerformanceService {
    List<Object> ShipPerformance(String beginTime, String endTime);
    List boatSelectAll(String beginTime, String endTime);
    List boatBycustomer(String beginTime, String endTime);
}
