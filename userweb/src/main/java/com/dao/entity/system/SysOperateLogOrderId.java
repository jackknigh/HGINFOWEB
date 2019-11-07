package com.dao.entity.system;

import com.dao.entity.sys.ComColumn;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Noah
 * @since 1.0.0, 2019/03/22
 */
public class SysOperateLogOrderId extends ComColumn implements Serializable {

    private String id;

    private String orderId;

    private String operatorId;

    private Date createDate;

    private Integer operateType ;

    private Date nextDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Date getNextDate() {
        return nextDate;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }
}
