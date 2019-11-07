package com.dao.db2.lwaddress;

import com.dao.entity.lwaddress.Bs_province;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Bs_provinceMapper {

    Bs_province selectByPrimaryKey(String id);
    //查询所有的省的简称
    List<Bs_province> selectShortNameAndProvinceName();

}