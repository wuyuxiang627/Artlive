package com.connxun.elinetv.entity;

/**
 * Created by connxun-16 on 2018/1/23.
 */

public class OpenLive {


    private Live data;
    private String code;
    private String msg;
    private String ext;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getExt() {
        return ext;
    }

    public Live getData() {
        return data;
    }

    public void setData(Live data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OpenLive{" +
                "live=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", ext='" + ext + '\'' +
                '}';
    }
}
