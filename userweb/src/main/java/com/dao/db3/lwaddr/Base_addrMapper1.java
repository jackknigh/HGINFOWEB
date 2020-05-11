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

    int insert3_2_1(List<Base_addr> base_addr);

    int insert5(Base_addr message);

    int insert5_2(List<Base_addr> base_addr);

    int insert5_2_1(List<Base_addr> base_addr);

    int insert5_2_11(List<Base_addr> base_addr);

    int insert6(List<Base_addr> message);

    List<Base_addr> selectAddr_sj(int number1, int number2);

    List<Base_addr> selectMsg(@Param("name") String name, @Param("phone") String phone);

    List<Base_addr> selectMsg1(@Param("name") String name, @Param("phone") String phone);

    int insert4(List<Base_addr> message);

    List<BaseAddrVo> queryMsgByCode(String code);

    BaseAddrVo queryMsgById(String id);

    void updateMerge(@Param("mergeId") String mergeId, @Param("basicsId") String basicsId);

    List<Base_addr> selectBaseAddr(@Param("start") int start,@Param("batchcCount") int batchcCount);

    void updatePhone(@Param(value = "list") List<Base_addr> list);

    void insertDiscard(Base_addr baseAddr1);

    void updateBasics(Base_addr base_addr);

    void updateErrDate(Base_addr baseAddr);

    void updateP5type(Base_addr base_addr);

    List<Base_addr> selectBaseAddr2(@Param("streetName") String streetName);
}