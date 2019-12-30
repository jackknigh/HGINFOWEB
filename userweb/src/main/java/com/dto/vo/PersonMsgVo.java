package com.dto.vo;

import com.dao.entity.lwaddress.Base_addr;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "人员信息", description = "人员信息")
public class PersonMsgVo{
    @ApiModelProperty(value = "主键id", name = "id")
    private String id;
    @ApiModelProperty(value = "身份证号", name = "idCard")
    private String idCard;
    @ApiModelProperty(value = "姓名", name = "name")
    private String name;
    @ApiModelProperty(value = "电话号码", name = "telNumber")
    private String telNumber;
    @ApiModelProperty(value = "地址", name = "address")
    private String address;
    @ApiModelProperty(value = "短地址", name = "shortAddress")
    private String shortAddress;
    @ApiModelProperty(value = "同地址人数", name = "peopleNum")
    private String peopleNum;
    @ApiModelProperty(value = "同地址人数详情", name = "peopleList")
    private List<Base_addr> peopleList;
    @ApiModelProperty(value = "标准地址详情", name = "basiscMsg")
    private Base_addr basiscMsg;
    @ApiModelProperty(value = "合并地址数量", name = "addressNum")
    private int addressNum;
    @ApiModelProperty(value = "关联id", name = "contrastId")
    private String contrastId;
    @ApiModelProperty(value = "省", name = "province")
    private String province;
    @ApiModelProperty(value = "市", name = "city")
    private String city;
    @ApiModelProperty(value = "区", name = "area")
    private String area;
    @ApiModelProperty(value = "街道", name = "street")
    private String street;
    @ApiModelProperty(value = "图表数据", name = "chartData")
    private List<ChartData> chartData;

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

    public Base_addr getBasiscMsg() {
        return basiscMsg;
    }

    public void setBasiscMsg(Base_addr basiscMsg) {
        this.basiscMsg = basiscMsg;
    }

    public List<Base_addr> getPeopleList() {
        return peopleList;
    }

    public void setPeopleList(List<Base_addr> peopleList) {
        this.peopleList = peopleList;
    }

    public List<ChartData> getChartData() {
        return chartData;
    }

    public void setChartData(List<ChartData> chartData) {
        this.chartData = chartData;
    }

    public String getContrastId() {
        return contrastId;
    }

    public void setContrastId(String contrastId) {
        this.contrastId = contrastId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(String shortAddress) {
        this.shortAddress = shortAddress;
    }

    public String getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(String peopleNum) {
        this.peopleNum = peopleNum;
    }

    public int getAddressNum() {
        return addressNum;
    }

    public void setAddressNum(int addressNum) {
        this.addressNum = addressNum;
    }
}
