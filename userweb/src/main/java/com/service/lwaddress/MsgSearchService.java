package com.service.lwaddress;

import com.dao.entity.lwaddress.BaseAddrByPhone;
import com.dao.entity.lwaddress.Base_addr;
import com.dto.form.SearchContentForm;
import com.dto.form.SumAddressForm;
import com.dto.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface MsgSearchService {
//    List<BaseAddrVo> queryMsg(String name, String phone, String address);

    List<BaseAddrVo> queryMsg(String address,String shortPhone,String surname);

    SearchContentVo searchContent(SearchContentForm searchContentForm);

    List<PersonMsgVo> searchPeopleList(String id);

    AddressListMsgVo searchAddressList(String id,Integer currentPage, Integer pageSize);

    void removeAddressList(String id);

    void sumAddress( String addressId, String keyData);

    List<PersonMsgVo> otherAddress(String phone);

    List<ChartData> acceptDelivery(String id);

    String querySimilarity(String basicsAddr, String matchAddr);

    List<BaseAddrByPhone> queryMsgByPhone(String phone);

    List<BaseAddrVo> queryMsgByCode(String code);

    BaseAddrVo queryMsgById(String id);

    BaseAddrVo  insertMsgProcess(BaseAddrVo baseAddrVo);

    void insertMsgProcess1(Base_addr baseAddr, String reg, Map<String, Object> map);

    BaseAddrVo queryInfoById(String id);

    List<BaseAddrByPhone> queryMergeInfoById(String id);

    List<BaseAddrByPhone> queryBasicsMsgByPhone(String phone);

    Base_addr queryAddressInfo(String address);
}
