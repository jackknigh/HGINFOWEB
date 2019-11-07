package com.dto.pojo.stata;

import com.utils.sys.DateUtil;
import com.utils.sys.TextUtils;

import java.io.Serializable;

public class StatJcjDTO implements Serializable, Comparable {

    private String depCode;
    private String depName;
    private int jqzs;
    private String date;
    private int yxjq;
    private String lxdm;
    private String lxmz;
    private String lbdm;
    private String lbmz;

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getYxjq() {
        return yxjq;
    }

    public void setYxjq(int yxjq) {
        this.yxjq = yxjq;
    }

    public int getJqzs() {
        return jqzs;
    }

    public void setJqzs(int jqzs) {
        this.jqzs = jqzs;
    }

    public String getLxdm() {
        return lxdm;
    }

    public void setLxdm(String lxdm) {
        this.lxdm = lxdm;
    }

    public String getLxmz() {
        return lxmz;
    }

    public void setLxmz(String lxmz) {
        this.lxmz = lxmz;
    }

    public String getLbdm() {
        return lbdm;
    }

    public void setLbdm(String lbdm) {
        this.lbdm = lbdm;
    }

    public String getLbmz() {
        return lbmz;
    }

    public void setLbmz(String lbmz) {
        this.lbmz = lbmz;
    }

    @Override
    public int compareTo(Object o) {
        StatJcjDTO dto = (StatJcjDTO) o;
        if (!TextUtils.isEmpty(this.date)) {
            if (DateUtil.stringToDate(this.date, DateUtil.DATE_FORMAT_PATTERN).after(DateUtil.stringToDate(dto.date, DateUtil.DATE_FORMAT_PATTERN))) {
                return 1;
            } else if (DateUtil.stringToDate(this.date, DateUtil.DATE_FORMAT_PATTERN).before(DateUtil.stringToDate(dto.date, DateUtil.DATE_FORMAT_PATTERN))) {
                return -1;
            } else {
                if (this.jqzs > dto.jqzs) {
                    return -1;
                } else if (this.jqzs < dto.jqzs) {
                    return 1;
                }
            }
        } else {
            if (this.jqzs > dto.jqzs) {
                return -1;
            } else if (this.jqzs < dto.jqzs) {
                return 1;
            }
        }

        return 0;
    }
}
