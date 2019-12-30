package com.service.decisionForm;

import java.util.List;

public interface QueryInOutShipService {
    /*
        按产品名求平均值,按ShipNetWeight降序
     */
    public List queryInOutShipByGoodsName(String beginTime, String endTime);

    /*
       按批次分别求装卸作业数量平均值
    */
    public List queryInOutShipByBatch(String beginTime, String endTime);

    /*
       按分别求装卸作业数量平均值
    */
    public List queryInOutShipByIsSafety(String beginTime, String endTime);

    /*
      原数据
   */
    public List queryInOutShipBasics(String beginTime, String endTime);

    /*
    按泊位求平均值,按ShipNetWeight降序
 */
    public List queryInOutShipByBerthID(String beginTime, String endTime);
}
