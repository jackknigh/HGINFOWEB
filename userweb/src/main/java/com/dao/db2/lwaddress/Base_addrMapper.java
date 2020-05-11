package com.dao.db2.lwaddress;


import com.dao.entity.lwaddress.*;
import com.dto.form.AdvancedSearch;
import com.dto.form.SearchContentForm;
import com.dto.pojo.lwaddr.AddressName;
import com.dto.vo.*;
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

    List<Base_addr> selectByShortPhone(@Param("shortPhone") String shortPhone, @Param("city") String city);

    int truncateTable(String table);

    int updateMergeTable(String contrast_id, String id);

    int deleteBasics(List<Base_addr> idList);

    List<BaseAddrVo> selectMsg(@Param("name") String name, @Param("phone") String phone, @Param("length") Integer length);

    List<BaseAddrVo> selectMsg1(@Param("name") String name, @Param("phone") String phone, @Param("length") Integer length);

    Bs_city getCity(String cityCode);

    Bs_province getProvince(String code);

    List<Bs_area> getArea(String cityCode);

    List<Bs_street> getStreetName(String code);

    List<Base_addr> selectByAddress(Address address);

    List<PersonMsgVo> searchContent(SearchContentForm searchContentForm);

    String searchMergeNum(String id);

    PersonMsgVo searchContrastIdByMerge(String id);

    List<PersonMsgVo> searchPeopleListByContrastId(String contrastId);

    List<PersonMsgVo> searchPeopleListByBasic(String id);

    List<PersonMsgVo> searchPeopleListByMerge(String id);

    List<PersonMsgVo> searchAddressList(@Param("id") String id, @Param("currentPage") Integer currentPage, @Param("pageSize") Integer pageSize);

    Base_addr searchMergeById(String id);

    int insertBasics(Base_addr baseAddr);

    int deleteMergeById(String id);

    Base_addr searchBasicsById(String keyData);

    int insertMerge(Base_addr baseAddr);

    int deleteBasicsById(String keyData);

    List<ChartData> searchMergeKdNum(@Param("id") String id, @Param("year") String year);

    List<ChartData> searchBasicsKdNum(@Param("id") String id, @Param("year") String year);

    List<PersonMsgVo> searchBasicsByPhone(String phone);

    void deleteBasicsNormal(String keyData);

    void deleteMergeNormal(String keyData);

    String searchAddressById(String id);

    List<AcceptDeliveryPeoperVo> searchMergeAcceptDelivery(@Param("id") String id, @Param("time") String time, @Param("year") String year);

    List<AcceptDeliveryPeoperVo> searchBaiscAcceptDelivery(@Param("id") String id, @Param("time") String time, @Param("year") String year);

    List<Base_addr> selectBaseAddr(@Param("streetName") String streetName);

    long getTotal(SearchContentForm searchContentForm);

    List<Base_addr> searchPeopleListByContrastIds(List<PersonMsgVo> personMsgVos);

    List<Base_addr> searchBasicsMsg(List<PersonMsgVo> list);

    long getTotalById(String id);

    List<MergeNums> searchMergeNums(List<PersonMsgVo> list);

    List<Base_addr> selectDataByPhone(@Param("phone") String phone, @Param("street") String street);

    List<Base_addr> selectData(@Param("startCount") int startCount,@Param("count") int count);

//    List<Base_addr> getBaseAddrs(@Param("start")int start, @Param("count")int count, @Param("phone")String phone);

    List<Base_addr> getBaseAddrs(@Param("phone")String phone);

    List<Base_addr> getDate(@Param("shortPhone")String shortPhone,@Param("province")String province,@Param("city")String city,@Param("area")String area,@Param("street")String street);

    List<Base_addr> getDate1(@Param("area") String area, @Param("street") String street,@Param("shortPhone")String shortPhone);

    List<Base_addr> getInsertDate();

    List<Bs_province> getAllProvince();

    List<Base_addr> selectBaseAddrByStreet(@Param("streetName") String streetName);

    List<Base_addr> selectBaseAddr1(@Param("start") int start, @Param("batchcCount") int batchcCount,@Param("streetName") String streetName);
    List<Base_addr> selectBaseAddr2(@Param("streetName") String streetName);

    int selectCount(@Param("streetName") String streetName);

    List<Base_addr> selectBaseAddrByArea(@Param("areaName")String areaName);

    List<Base_addr> selectBaseAddrs(@Param("streetName")String streetName, @Param("start")int start, @Param("count")int count);

    void function(@Param("start")int start, @Param("end")int end);

    void insertDiscard(Base_addr base_addr);

    void updateP5type(Base_addr base_addr);

    List<BaseAddrVo> selectMsg2(@Param("area") String area, @Param("street") String street,@Param("shortPhone") String shortPhone);

    void insertBaseAddr(Base_addr baseAddr);

    BaseAddrVo queryInfoById(@Param("id") String id);

    List<BaseAddrByPhone> queryMergeInfoById(String id);

    List<BaseAddrByPhone> queryBasicsMsgByPhone(@Param("phone") String phone);

    void updateBasics(Base_addr base_addr);

    List<BaseAddrByPhone> queryMsgByPhone(String phone);
}