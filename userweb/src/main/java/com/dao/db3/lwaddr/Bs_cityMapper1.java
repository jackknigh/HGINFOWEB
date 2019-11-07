package com.dao.db3.lwaddr;

import com.dao.entity.lwaddress.Bs_city;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface Bs_cityMapper1 {

    Bs_city selectByPrimaryKey(String id);
   //查询所有的市的名称和简称
    List<Bs_city> selectCityAllName();
    //按省编号查询所有的市的名称和简称
    List<Bs_city> selectCityAllNameByProvinceCode(String provinceCode);

    List<Bs_city> selectCityNameByCityCode(String cityCode);
}