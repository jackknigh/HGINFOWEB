package com.dao.entity.lwaddress;

import java.io.Serializable;

public class BaseAddrByPhone implements Serializable {
    private String id;

    private String addrSj;

    private String address;

    private String basicsAddress;

    private String shortAddr;

    private String name1;

    private String idCard;

    private String phone;

    private String province;

    private String city;

    private String area;

    private String street;

//    private String alley;
//
//    private String alleyNum;
//
//    private String plot;
//
//    private String buildingNum;
//
//    private String unitNum;
//
//    private String floorNum;
//
//    private String doorplateNum;

    private Integer mergeNum;

    private String earliestTime;

    private String latestTime;

    private String longitude;

    private String latitude;

    private String basicsLongitude;

    private String basicsLatitude;

    private String contrastId;

    private String oldPhone;

    private String oldName1;

    private String createTime;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBasicsAddress() {
        return basicsAddress;
    }

    public void setBasicsAddress(String basicsAddress) {
        this.basicsAddress = basicsAddress;
    }

    public String getBasicsLongitude() {
        return basicsLongitude;
    }

    public void setBasicsLongitude(String basicsLongitude) {
        this.basicsLongitude = basicsLongitude;
    }

    public String getBasicsLatitude() {
        return basicsLatitude;
    }

    public void setBasicsLatitude(String basicsLatitude) {
        this.basicsLatitude = basicsLatitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddrSj() {
        return addrSj;
    }

    public void setAddrSj(String addrSj) {
        this.addrSj = addrSj;
    }

    public String getShortAddr() {
        return shortAddr;
    }

    public void setShortAddr(String shortAddr) {
        this.shortAddr = shortAddr;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

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

    public Integer getMergeNum() {
        return mergeNum;
    }

    public void setMergeNum(Integer mergeNum) {
        this.mergeNum = mergeNum;
    }

    public String getEarliestTime() {
        return earliestTime;
    }

    public void setEarliestTime(String earliestTime) {
        this.earliestTime = earliestTime;
    }

    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getContrastId() {
        return contrastId;
    }

    public void setContrastId(String contrastId) {
        this.contrastId = contrastId;
    }

    public String getOldPhone() {
        return oldPhone;
    }

    public void setOldPhone(String oldPhone) {
        this.oldPhone = oldPhone;
    }

    public String getOldName1() {
        return oldName1;
    }

    public void setOldName1(String oldName1) {
        this.oldName1 = oldName1;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}