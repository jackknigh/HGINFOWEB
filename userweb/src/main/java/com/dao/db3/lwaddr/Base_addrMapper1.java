package com.dao.db3.lwaddr;


import com.dao.entity.lwaddress.Base_addr;
import com.dto.vo.BaseAddrVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface Base_addrMapper1 {

    List<Base_addr> selectByShortPhone(String shortPhone);

    int insert1(List<Base_addr> message);

    int insert3(Base_addr base_addr);

    int insert3_2(List<Base_addr> base_addr);

    int insert5(Base_addr message);

    int insert5_2(List<Base_addr> base_addr);

    int insert6(List<Base_addr> message);

    List<Base_addr> selectAddr_sj(int number1, int number2);

    List<Base_addr> selectMsg(@Param("name") String name, @Param("phone") String phone);

    List<Base_addr> selectMsg1(@Param("name") String name, @Param("phone") String phone);

    int insert4(List<Base_addr> message);

    Base_addr selectIsExistence(Integer rowId);
}