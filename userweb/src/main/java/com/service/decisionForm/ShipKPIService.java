package com.service.decisionForm;

import java.util.List;

public interface ShipKPIService {
    /*
    基础数据不需要修改
     */
    public List shipKPIBasics(String beginTime, String endTime);
    /*
    按泊位求装卸船平均速率
     */
    public List shipKPIAverage(String beginTime, String endTime);

    /*
    按船名求装卸船平均速率
     */
    public List shipKPIShipName(String beginTime, String endTime);

    /*
    desc
     */
    public List shipKPIDesc(String beginTime, String endTime);
    /*
   asc
    */
    public List shipKPIAsc(String beginTime, String endTime);
}
