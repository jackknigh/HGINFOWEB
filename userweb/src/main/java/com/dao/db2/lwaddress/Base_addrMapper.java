package com.dao.db2.lwaddress;


import com.dao.entity.lwaddress.Address;
import com.dao.entity.lwaddress.Base_addr;
import com.dto.pojo.lwaddr.AddressName;
import com.dto.vo.BaseAddrVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Base_addrMapper {

    List<Base_addr> selectAddr_sj(int number1, int number2);

    List<Base_addr> selectFromInsertAddr_sj(int number1, int number2);

    List<Base_addr> selectUseCondition(String phone);

    int selectSum();

    int insert1(List<Base_addr> message);

    int insert2(List<Base_addr> message);

    //int insert3(List<Base_addr> message);
    int insert3(Base_addr base_addr);

    int insert3_2(List<Base_addr> base_addr);

    int insert4(List<Base_addr> message);

    int insert5(Base_addr message);

    int insert5_2(List<Base_addr> base_addr);

    List<Base_addr> selectByPhone(int number1, int number2);

    List<Base_addr> selectByShortPhone(String shortPhone);

    int truncateTable(String table);

    int updateMergeTable(String contrast_id, String id);

    int deleteBasics(List<Base_addr> idList);

    List<BaseAddrVo> selectMsg(@Param("name") String name, @Param("phone") String phone, @Param("length") Integer length);

    List<BaseAddrVo> selectMsg1(@Param("name") String name, @Param("phone") String phone,@Param("length") Integer length);

    AddressName getCity(String cityCode);

    AddressName getProvince(String code);

    List<AddressName> getArea(String cityCode);

    List<AddressName> getStreetName(String code);

    List<Base_addr> selectByAddress(Address address);
}