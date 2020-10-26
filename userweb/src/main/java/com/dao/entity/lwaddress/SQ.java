package com.dao.entity.lwaddress;

import java.io.Serializable;
import java.math.BigDecimal;

public class SQ implements Serializable {
    private String name;
    private String bound;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBound() {
        return bound;
    }

    public void setBound(String bound) {
        this.bound = bound;
    }
}