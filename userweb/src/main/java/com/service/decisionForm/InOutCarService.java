package com.service.decisionForm;

import java.util.List;

public interface InOutCarService {
    /*
        基础数据，未经修改
     */
    public List inOutCarBas(String beginTime, String endTime);
    /*
        根据客户名查询，并根据Number排序
     */
    public List inOutCarSort(String beginTime, String endTime);

    /*
        根据客户名查询，并根据KPI排序
     */
    public List inOutCarGK(String beginTime, String endTime);
}
