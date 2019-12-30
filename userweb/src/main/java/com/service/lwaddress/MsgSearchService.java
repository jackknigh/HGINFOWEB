package com.service.lwaddress;

import com.dto.form.SearchContentForm;
import com.dto.form.SumAddressForm;
import com.dto.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MsgSearchService {
    List<BaseAddrVo> queryMsg(String name, String phone, String address);

    SearchContentVo searchContent(SearchContentForm searchContentForm);

    List<PersonMsgVo> searchPeopleList(String id);

    AddressListMsgVo searchAddressList(String id, Integer currentPage, Integer pageSize);

    void removeAddressList(String id);

    void sumAddress(String addressId, String keyData);

    List<PersonMsgVo> otherAddress(String phone);

    List<ChartData> acceptDelivery(String id);
}
