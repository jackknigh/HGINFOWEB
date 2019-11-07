package com.dao.entity.lwaddress;

import java.math.BigDecimal;

public class Third_addr {
    private String id;

    private String name1;

    private String name2;

    private String addrJj;

    private String addrSj;

    private String phone;

    private String shortAddr;

    private String addrSign1;

    private String addrSign2;

    private String province;

    private BigDecimal proWeight;

    private String city;

    private BigDecimal cityWeight;

    private String area;

    private BigDecimal areaWeight;

    private String street;

    private BigDecimal streWeight;

    private BigDecimal mulWeight;

    private BigDecimal finaWeight;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Integer type1;

    private Integer type2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1 == null ? null : name1.trim();
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2 == null ? null : name2.trim();
    }

    public String getAddrJj() {
        return addrJj;
    }

    public void setAddrJj(String addrJj) {
        this.addrJj = addrJj;
    }

    public String getAddrSj() {
        return addrSj;
    }

    public void setAddrSj(String addrSj) {
        this.addrSj = addrSj;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getShortAddr() {
        return shortAddr;
    }

    public void setShortAddr(String shortAddr) {
        this.shortAddr = shortAddr == null ? null : shortAddr.trim();
    }

    public String getAddrSign1() {
        return addrSign1;
    }

    public void setAddrSign1(String addrSign1) {
        this.addrSign1 = addrSign1 == null ? null : addrSign1.trim();
    }

    public String getAddrSign2() {
        return addrSign2;
    }

    public void setAddrSign2(String addrSign2) {
        this.addrSign2 = addrSign2 == null ? null : addrSign2.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public BigDecimal getProWeight() {
        return proWeight;
    }

    public void setProWeight(BigDecimal proWeight) {
        this.proWeight = proWeight;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public BigDecimal getCityWeight() {
        return cityWeight;
    }

    public void setCityWeight(BigDecimal cityWeight) {
        this.cityWeight = cityWeight;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public BigDecimal getAreaWeight() {
        return areaWeight;
    }

    public void setAreaWeight(BigDecimal areaWeight) {
        this.areaWeight = areaWeight;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street == null ? null : street.trim();
    }

    public BigDecimal getStreWeight() {
        return streWeight;
    }

    public void setStreWeight(BigDecimal streWeight) {
        this.streWeight = streWeight;
    }

    public BigDecimal getMulWeight() {
        return mulWeight;
    }

    public void setMulWeight(BigDecimal mulWeight) {
        this.mulWeight = mulWeight;
    }

    public BigDecimal getFinaWeight() {
        return finaWeight;
    }

    public void setFinaWeight(BigDecimal finaWeight) {
        this.finaWeight = finaWeight;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Integer getType1() {
        return type1;
    }

    public void setType1(Integer type1) {
        this.type1 = type1;
    }

    public Integer getType2() {
        return type2;
    }

    public void setType2(Integer type2) {
        this.type2 = type2;
    }
}