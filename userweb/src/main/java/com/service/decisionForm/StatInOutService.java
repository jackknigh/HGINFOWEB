package com.service.decisionForm;

import java.util.List;

public interface StatInOutService {
    /*
    按照产品求平均值，并排序
     */
    public List statInOutSort(String beginTime,String endTime);
    /*
    基础数据
     */
    public List statInOutBasics(String beginTime,String endTime);
}
