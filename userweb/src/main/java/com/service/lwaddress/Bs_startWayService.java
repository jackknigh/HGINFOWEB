package com.service.lwaddress;

import java.util.Map;

public interface Bs_startWayService {
    void startway(int start, int total, int batchcCount);

    Map getThreadInfo();

    void increment();
}
