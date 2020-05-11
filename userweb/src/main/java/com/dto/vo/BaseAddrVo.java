package com.dto.vo;

import java.io.Serializable;

public class BaseAddrVo implements Serializable {
    private String id;

    //    private String addrJj;
//
    private String addrSj;
    //
    private String shortAddr;

    private String address;

    private String addrCode;
    //
    private String name1;
    //
//    private String name2;
//
//    private String name3;
//
    private String phone;
    //
    private String province;
    //
//    private String proWeight;
//
    private String city;
    //
//    private String cityWeight;
//
    private String area;
    //
//    private String areaWeight;
//
    private String street;
    //
//    private String streWeight;
//
//    private String pcWeight;
//
//    private String mulWeight;
//
//    private String strScore;
//
//    private String numberScore;
//
//    private String nameScore;
//
//    private String phoneScore;
//
//    private String latitude;
//
//    private String addrSign1;
//
//    private String addrSign2;
//
    private String contrastScore;
    //
    private Integer mergeNum;

    private String earliestTime;

    private String latestTime;

    private String createTime;
    //
//    private Integer p1type;
//
//    private Integer p2type;
//
//    private Integer p3type;
//
//    private Integer p4type;
//
//    private Integer p5type;
//
//    private String total1;
//
//    private String total2;
//
//    private String total3;
//
//    private String total4;
//
//    private String total5;
//
//    private String shortPhone;
//
    private String oldPhone;

    private String oldName1;
    //
//    private Integer rowId;
//
    private String tableName;
//
//    private Integer countId;
//


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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

    public String getAddrCode() {
        return addrCode;
    }

    public void setAddrCode(String addrCode) {
        this.addrCode = addrCode;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContrastScore() {
        return contrastScore;
    }

    public void setContrastScore(String contrastScore) {
        this.contrastScore = contrastScore;
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
}