package com.dao.db3.lwaddr;


import com.dao.entity.lwaddress.Phone;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneMapper1 {
    List<Phone> selectAll(int number1, int number2);
}