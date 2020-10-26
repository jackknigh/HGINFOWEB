package com.dao.entity.lwaddress;

import java.io.Serializable;
import java.math.BigDecimal;

public class Base_addr implements Serializable {
    private String id;

    private String addrJj;

    private String addrSj;

    private String addrCode;

    private String shortAddr;

    private String name1;

    private String name2;

    private String name3;

    private String idCard;

    private String phone;

    private String province;

    private String proWeight;

    private String city;

    private String cityWeight;

    private String area;

    private String areaWeight;

    private String street;

    private String streWeight;

    private String pcWeight;

    private String community;

    private String alley;

    private String alleyNum;

    private String plot;

    private String buildingNum;

    private String unitNum;

    private String floorNum;

    private String doorplateNum;

    private Integer mergeNum;

    private String earliestTime;

    private String latestTime;

    //第一步骤的总分
    private String mulWeight;

    private String strScore;

    private String numberScore;

    private String nameScore;

    private String phoneScore;

    private String longitude;

    private String latitude;

    private String addrSign1;

    private String addrSign2;

    private String contrastScore;

    private String contrastId;

    private Integer p1type;

    private Integer p2type;

    private Integer p3type;

    private Integer p4type;

    private Integer p5type;

    private String total1;

    private String total2;

    private String total3;

    private String total4;

    private String total5;

    private String shortPhone;

    private String oldPhone;

    private String oldName1;

    private Integer rowId;

    private String tableName;

    private Integer countId;

    private String createTime;

    private String responsibilityArea;

    public String getResponsibilityArea() {
        return responsibilityArea;
    }

    public void setResponsibilityArea(String responsibilityArea) {
        this.responsibilityArea = responsibilityArea;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getAlley() {
        return alley;
    }

    public void setAlley(String alley) {
        this.alley = alley;
    }

    public String getAlleyNum() {
        return alleyNum;
    }

    public void setAlleyNum(String alleyNum) {
        this.alleyNum = alleyNum;
    }

    public String getAddrCode() {
        return addrCode;
    }

    public void setAddrCode(String addrCode) {
        this.addrCode = addrCode;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(String buildingNum) {
        this.buildingNum = buildingNum;
    }

    public String getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(String unitNum) {
        this.unitNum = unitNum;
    }

    public String getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(String floorNum) {
        this.floorNum = floorNum;
    }

    public String getDoorplateNum() {
        return doorplateNum;
    }

    public void setDoorplateNum(String doorplateNum) {
        this.doorplateNum = doorplateNum;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public BigDecimal getLongitude() {
        if(longitude == null){
            return new BigDecimal(0);
        }
        return new BigDecimal(longitude);
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude.toString();
    }

    public BigDecimal getLatitude() {
        if(latitude == null){
            return new BigDecimal(0);
        }
        return new BigDecimal(latitude);

    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude.toString();
    }

    public Integer getCountId() {
        return countId;
    }

    public void setCountId(Integer countId) {
        this.countId = countId;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAddrJj() {
        return addrJj;
    }

    public void setAddrJj(String addrJj) {
        this.addrJj = addrJj == null ? null : addrJj.trim();
    }

    public String getAddrSj() {
        return addrSj;
    }

    public void setAddrSj(String addrSj) {
        this.addrSj = addrSj == null ? null : addrSj.trim();
    }

    public String getShortAddr() {
        return shortAddr;
    }

    public void setShortAddr(String shortAddr) {
        this.shortAddr = shortAddr == null ? null : shortAddr.trim();
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

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3 == null ? null : name3.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public BigDecimal getProWeight() {
        if(proWeight == null){
            return new BigDecimal(0);
        }
        return new BigDecimal(proWeight);
    }

    public void setProWeight(BigDecimal proWeight) {
        this.proWeight = proWeight.toString();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public BigDecimal getCityWeight() {
        if(cityWeight == null){
            return new BigDecimal(0);
        }
        return new BigDecimal(cityWeight);
    }

    public void setCityWeight(BigDecimal cityWeight) {
        this.cityWeight = cityWeight.toString();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public BigDecimal getAreaWeight() {
        if(areaWeight == null){
            return new BigDecimal(0);
        }
        return new BigDecimal(areaWeight);

    }

    public void setAreaWeight(BigDecimal areaWeight) {
        this.areaWeight = areaWeight.toString();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street == null ? null : street.trim();
    }

    public BigDecimal getStreWeight() {
        if(streWeight == null){
            return new BigDecimal(0);
        }
        return new BigDecimal(streWeight);

    }

    public void setStreWeight(BigDecimal streWeight) {
        this.streWeight = streWeight.toString();
    }

    public BigDecimal getPcWeight() {
        if(pcWeight == null){
            return new BigDecimal(0);
        }
        return new BigDecimal(pcWeight);

    }

    public void setPcWeight(BigDecimal pcWeight) {
        this.pcWeight = pcWeight.toString();
    }

    public BigDecimal getMulWeight() {
        if(mulWeight == null){
            return new BigDecimal(0);
        }
        return new BigDecimal(mulWeight);

    }

    public void setMulWeight(BigDecimal mulWeight) {
        this.mulWeight = mulWeight.toString();
    }

    public String getStrScore() {
        return strScore;
    }

    public void setStrScore(String strScore) {
        this.strScore = strScore;
    }

    public String getNumberScore() {
        return numberScore;
    }

    public void setNumberScore(String numberScore) {
        this.numberScore = numberScore;
    }

    public BigDecimal getNameScore() {
        if(nameScore == null){
            return new BigDecimal(0);
        }
        return new BigDecimal(nameScore);

    }

    public void setNameScore(BigDecimal nameScore) {
        this.nameScore = nameScore.toString();
    }

    public BigDecimal getPhoneScore() {
        if(phoneScore == null){
            return new BigDecimal(0);
        }
        return new BigDecimal(phoneScore);

    }

    public void setPhoneScore(BigDecimal phoneScore) {
        this.phoneScore = phoneScore.toString();
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

    public BigDecimal getContrastScore() {
        if(contrastScore == null){
            return new BigDecimal(0);
        }
        return new BigDecimal(contrastScore);
    }

    public void setContrastScore(BigDecimal contrastScore) {
        this.contrastScore = contrastScore.toString();
    }

    public String getContrastId() {
        return contrastId;
    }

    public void setContrastId(String contrastId) {
        this.contrastId = contrastId == null ? null : contrastId.trim();
    }

    public Integer getP1type() {
        return p1type;
    }

    public void setP1type(Integer p1type) {
        this.p1type = p1type;
    }

    public Integer getP2type() {
        return p2type;
    }

    public void setP2type(Integer p2type) {
        this.p2type = p2type;
    }

    public Integer getP3type() {
        return p3type;
    }

    public void setP3type(Integer p3type) {
        this.p3type = p3type;
    }

    public Integer getP4type() {
        return p4type;
    }

    public void setP4type(Integer p4type) {
        this.p4type = p4type;
    }

    public Integer getP5type() {
        return p5type;
    }

    public void setP5type(Integer p5type) {
        this.p5type = p5type;
    }

    public BigDecimal getTotal1() {
        if(total1 == null){
            return new BigDecimal(0);
        }
        return new BigDecimal(total1);
    }

    public void setTotal1(BigDecimal total1) {
        this.total1 = total1.toString();
    }

    public BigDecimal getTotal2() {
        if(total2 == null){
            return new BigDecimal(0);
        }
        return new BigDecimal(total2);

    }

    public void setTotal2(BigDecimal total2) {
        this.total2 = total2.toString();
    }

    public BigDecimal getTotal3() {
        if(total3 == null){
            return new BigDecimal(0);
        }
        return new BigDecimal(total3);
    }

    public void setTotal3(BigDecimal total3) {
        this.total3 = total3.toString();
    }

    public BigDecimal getTotal4() {
        if(total4 == null){
            return new BigDecimal(0);
        }
        return new BigDecimal(total4);

    }

    public void setTotal4(BigDecimal total4) {
        this.total4 = total4.toString();
    }

    public BigDecimal getTotal5() {
        if(total5 == null){
            return new BigDecimal(0);
        }
        return new BigDecimal(total5);
    }

    public void setTotal5(BigDecimal total5) {
        this.total5 = total5.toString();
    }

    public String getShortPhone() {
        return shortPhone;
    }

    public void setShortPhone(String shortPhone) {
        this.shortPhone = shortPhone == null ? null : shortPhone.trim();
    }



    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}