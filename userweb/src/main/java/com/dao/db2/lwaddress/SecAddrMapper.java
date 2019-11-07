package com.dao.db2.lwaddress;

import com.dao.entity.lwaddress.Sec_addr;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SecAddrMapper {

    List<Sec_addr> selectAddrsec(@Param("number1") int number1, @Param("number2") int number2);

    void insert2(List<Sec_addr> message);

    List<Sec_addr> searchError();

    void update(List<Sec_addr> secAddrs);
}