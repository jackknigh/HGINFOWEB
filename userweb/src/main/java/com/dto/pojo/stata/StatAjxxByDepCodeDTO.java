package com.dto.pojo.stata;

import com.utils.sys.DateUtil;
import com.utils.sys.TextUtils;

import java.io.Serializable;

public class StatAjxxByDepCodeDTO implements Serializable, Comparable {

    private String name;
    private String depName;
    private String depCode;
    private int statCount;
    private String code;
    private String date;
    private int householdCode;
    private String household;

    public int getHouseholdCode() {
        return householdCode;
    }

    public void setHouseholdCode(int householdCode) {
        this.householdCode = householdCode;
    }

    public String getHousehold() {
        return household;
    }

    public void setHousehold(String household) {
        this.household = household;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public int getStatCount() {
        return statCount;
    }

    public void setStatCount(int statCount) {
        this.statCount = statCount;
    }

    @Override
    public int compareTo(Object o) {
        StatAjxxByDepCodeDTO dto = (StatAjxxByDepCodeDTO) o;
        if (this.householdCode != 0) {
            if (this.householdCode > dto.householdCode) {
                return 1;
            } else if (this.householdCode < dto.householdCode) {
                return -1;
            } else {
                if (Long.parseLong(this.depCode) > Long.parseLong(dto.depCode)) {
                    return 1;
                } else if (Long.parseLong(this.depCode) < Long.parseLong(dto.depCode)) {
                    return -1;
                } else {
                    if (this.statCount > dto.statCount) {
                        return -1;
                    } else if (this.statCount < dto.statCount) {
                        return 1;
                    }
                }
            }
        } else if (!TextUtils.isEmpty(this.date) && !TextUtils.isEmpty(this.depCode)) {
            if (DateUtil.stringToDate(this.date, DateUtil.DATE_FORMAT_PATTERN).after(DateUtil.stringToDate(dto.date, DateUtil.DATE_FORMAT_PATTERN))) {
                return 1;
            } else if (DateUtil.stringToDate(this.date, DateUtil.DATE_FORMAT_PATTERN).before(DateUtil.stringToDate(dto.date, DateUtil.DATE_FORMAT_PATTERN))) {
                return -1;
            } else {
                if (Long.parseLong(this.depCode) > Long.parseLong(dto.depCode)) {
                    return 1;
                } else if (Long.parseLong(this.depCode) < Long.parseLong(dto.depCode)) {
                    return -1;
                } else {
                    if (this.statCount > dto.statCount) {
                        return -1;
                    } else if (this.statCount < dto.statCount) {
                        return 1;
                    }
                }
            }
        } else if (!TextUtils.isEmpty(this.depCode)) {
            if (Long.parseLong(this.depCode) > Long.parseLong(dto.depCode)) {
                return 1;
            } else if (Long.parseLong(this.depCode) < Long.parseLong(dto.depCode)) {
                return -1;
            } else {
                if (this.statCount > dto.statCount) {
                    return -1;
                } else if (this.statCount < dto.statCount) {
                    return 1;
                }
            }
        } else if (!TextUtils.isEmpty(this.date)) {
            if (Long.parseLong(this.date) > Long.parseLong(dto.date)) {
                return 1;
            } else if (Long.parseLong(this.date) < Long.parseLong(dto.date)) {
                return -1;
            } else {
                if (this.statCount > dto.statCount) {
                    return -1;
                } else if (this.statCount < dto.statCount) {
                    return 1;
                }
            }
        } else {
            if (this.statCount > dto.statCount) {
                return -1;
            } else if (this.statCount < dto.statCount) {
                return 1;
            }
        }
        return 0;
    }
}
