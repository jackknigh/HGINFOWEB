package com.dao.db3.lwaddr;

import com.dao.entity.lwaddress.Bs_province;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface Bs_provinceMapper1 {

    Bs_province selectByPrimaryKey(String id);
    //查询所有的省的简称
    List<Bs_province> selectShortNameAndProvinceName();

}