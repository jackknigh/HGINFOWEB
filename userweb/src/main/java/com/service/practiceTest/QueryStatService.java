package com.service.practiceTest;

import java.util.List;

public interface QueryStatService {
    /**
     * 收入汇总
     * @return
     */
    List<Object> receivableTest();

    /**
     * 吞吐量汇总
     * @return
     */
    List<Object> inOutNumberTest();

    /**
     * 储罐存货数量统计
     * @return
     */
    List<Object> tankNumberTest();

}
