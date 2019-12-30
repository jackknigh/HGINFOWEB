package com.dao.db2.lwaddress;


import com.dao.entity.lwaddress.Address;
import com.dao.entity.lwaddress.Phone;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PhoneMapper {
    List<Phone> selectAll(int number1, int number2);

    List<Address> selectAllAddress(@Param("number1") int number1, @Param("number2") int number2);

    List<Address> selectAllAddressPhone();
}