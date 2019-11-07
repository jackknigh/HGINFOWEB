package com.service.decisionForm;

import java.util.List;
import java.util.Map;

public interface TruckKPIService {
    /*
    按照字段名查询，LoadingBay  GoodsName
     */
    public List truckKPIBySome(String beginTime, String endTime, String str);

    public Map truckKPIByLoadingBay(String beginTime, String endTime);
    /*
    按照VGuestName查询，按照speed3降序
     */
    public List truckKPIDesc(String beginTime, String endTime);

    /*
    按照VGuestName查询，按照speed3升序
     */
    public List truckKPIAsc(String beginTime, String endTime);
}
