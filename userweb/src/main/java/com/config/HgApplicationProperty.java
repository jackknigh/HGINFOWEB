package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by CuiL on 2019-01-07.
 */
@Component
public class HgApplicationProperty {
    @Value("${sysPropertys.standardAddress}")
    private String standardAddress;

    @Value("${sysPropertys.loginWithAutoCode}")
    private String loginWithAutoCode;

    @Value("${sysPropertys.blockSizeByStr}")
    private String blockSizeByStr;

    @Value("${sysPropertys.blockSizeByNum}")
    private String blockSizeByNum;

    @Value("${sysPropertys.cityCode}")
    private String cityCode;

    @Value("${sysPropertys.weightOfNum1}")
    private String weightOfNum1;

    @Value("${sysPropertys.weightOfNum2}")
    private String weightOfNum2;

    @Value("${sysPropertys.weightOfNum3}")
    private String weightOfNum3;

    @Value("${sysPropertys.weightOfNum4}")
    private String weightOfNum4;

    @Value("${sysPropertys.totalCount}")
    private String totalCount;

    @Value("${sysPropertys.totalProcessCount}")
    public String totalProcessCount;

    @Value("${sysPropertys.count}")
    private String count;

    @Value("${sysPropertys.processCount}")
    private String processCount;

    @Value("${sysPropertys.openOrNot}")
    private String openOrNot;

    @Value("${sysPropertys.startProcessCount}")
    private String startProcessCount;

    @Value("${sysPropertys.startCount}")
    private String startCount;

    @Value("${sysPropertys.insertWeight}")
    private String insertWeight;
    /*
        客户名称脱敏
    */
    @Value("${sysPropertys.chinOrEng}")
    private String chinOrEng;

    @Value("${sysPropertys.insertIndex}")
    private String insertIndex;

    @Value("${sysPropertys.delectTableIndex}")
    private  String delectTableIndex;

    public String getStandardAddress() {
        return standardAddress;
    }

    public void setStandardAddress(String standardAddress) {
        this.standardAddress = standardAddress;
    }

    public String getdelectTableIndex() {
        return delectTableIndex;
    }

    public void setdelectTableIndex(String delectTableIndex) {
        this.delectTableIndex = delectTableIndex;
    }

    public String getChinOrEng() {
        return chinOrEng;
    }

    public void setChinOrEng(String chinOrEng) {
        this.chinOrEng = chinOrEng;
    }

    public String getStartCount() {
        return startCount;
    }

    public void setStartCount(String startCount) {
        this.startCount = startCount;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getBlockSizeByStr() {
        return blockSizeByStr;
    }

    public void setBlockSizeByStr(String blockSizeByStr) {
        this.blockSizeByStr = blockSizeByStr;
    }

    public String getBlockSizeByNum() {
        return blockSizeByNum;
    }

    public void setBlockSizeByNum(String blockSizeByNum) {
        this.blockSizeByNum = blockSizeByNum;
    }

    public String getweightOfNum1() {
        return weightOfNum1;
    }

    public void setweightOfNum1(String weightOfNum1) {
        this.weightOfNum1 = weightOfNum1;
    }

    public String getweightOfNum2() {
        return weightOfNum2;
    }

    public void setweightOfNum2(String weightOfNum2) {
        this.weightOfNum2 = weightOfNum2;
    }

    public String getweightOfNum3() {
        return weightOfNum3;
    }

    public void setweightOfNum3(String weightOfNum3) {
        this.weightOfNum3 = weightOfNum3;
    }

    public String getweightOfNum4() {
        return weightOfNum4;
    }

    public void setweightOfNum4(String weightOfNum4) {
        this.weightOfNum4 = weightOfNum4;
    }

    public String getstartProcessCount() {
        return startProcessCount;
    }

    public void setstartProcessCount(String startProcessCount) {
        this.startProcessCount = startProcessCount;
    }

    public String getOpenOrNot() {
        return openOrNot;
    }

    public void setOpenOrNot(String openOrNot) {
        this.openOrNot = openOrNot;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String gettotalProcessCount() {
        return totalProcessCount;
    }

    public void settotalProcessCount(String totalProcessCount) {
        this.totalProcessCount = totalProcessCount;
    }


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getprocessCount() {
        return processCount;
    }

    public void setprocessCount(String processCount) {
        this.processCount = processCount;
    }

    public String getLoginWithAutoCode() {
        return loginWithAutoCode;
    }

    public void setLoginWithAutoCode(String loginWithAutoCode) {
        this.loginWithAutoCode = loginWithAutoCode;
    }

    public String getInsertWeight() {
        return insertWeight;
    }

    public void setInsertWeight(String insertWeight) {
        this.insertWeight = insertWeight;
    }

    public String getInsertIndex() {
        return insertIndex;
    }

    public void setInsertIndex(String insertIndex) {
        this.insertIndex = insertIndex;
    }
}
