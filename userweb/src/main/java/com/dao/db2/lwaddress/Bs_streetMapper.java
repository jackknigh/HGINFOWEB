package com.dao.db2.lwaddress;

import com.dao.entity.lwaddress.Bs_street;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Bs_streetMapper {

    Bs_street selectByPrimaryKey(Integer streetId);
    //按照区编号查询区中的所有街道
    List<Bs_street> selectByAreaCode(String areaCode);
    //按照市编号查询市中的所有街道
    List<Bs_street>  selectByCityCode(List<String> cityCode);

    String selectAreaCodeByStreetCode(String streetCode);

    List<Bs_street> selectAllStreet();
    /*连表查询街道相关信息*/
    List<Bs_street> selectStreetMessage();
}