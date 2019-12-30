package com.dto.vo;

import java.io.Serializable;
import java.util.List;

public class AddressListMsgVo implements Serializable {
    private long total;
    private List<PersonMsgVo> personMsgVos;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<PersonMsgVo> getPersonMsgVos() {
        return personMsgVos;
    }

    public void setPersonMsgVos(List<PersonMsgVo> personMsgVos) {
        this.personMsgVos = personMsgVos;
    }
}
