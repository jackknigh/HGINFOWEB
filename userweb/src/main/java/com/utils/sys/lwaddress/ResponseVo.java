package com.utils.sys.lwaddress;

import java.util.List;

public class ResponseVo<T> {

    private int code;

    private String msg;

    private List<T> data;

    private String src;

    private long count;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public static ResponseVo OK() {

            ResponseVo vo = new ResponseVo();

            vo.setMsg("success");
            vo.setCode(0);
        return vo;
    }

    public static ResponseVo error() {

        ResponseVo vo = new ResponseVo();

        vo.setMsg("error");
        vo.setCode(2000);
        return vo;
    }

}
