package com.dao.entity.lwaddress;

import java.io.Serializable;
import java.util.Date;

public class BsCommunity implements Serializable {
    private String area;

    private String street;

    private String streetShort;

    private String community;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetShort() {
        return streetShort;
    }

    public void setStreetShort(String streetShort) {
        this.streetShort = streetShort;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }
}