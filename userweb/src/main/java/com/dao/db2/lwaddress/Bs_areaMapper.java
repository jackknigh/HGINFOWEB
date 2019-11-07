package com.dao.db2.lwaddress;

import com.dao.entity.lwaddress.Bs_area;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Bs_areaMapper {

    Bs_area selectByPrimaryKey(String id);

    List<Bs_area> selectAreaAllName();

    List<Bs_area> selectByCityCode(String Code);

    List<Bs_area> selectByProvince(List<String> list);

    List<Bs_area> selectByAreaCode(String areaCode);
    /*连表查询区相关信息*/
    List<Bs_area> selectAreaMessage();
}