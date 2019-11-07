package com.dao.db3.lwaddr;

import com.dao.entity.lwaddress.Bs_area;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface Bs_areaMapper1 {

    Bs_area selectByPrimaryKey(String id);

    List<Bs_area> selectAreaAllName();

    List<Bs_area> selectByCityCode(String Code);

    List<Bs_area> selectByProvince(List<String> list);

    List<Bs_area> selectByAreaCode(String areaCode);
    /*连表查询区相关信息*/
    List<Bs_area> selectAreaMessage();
}