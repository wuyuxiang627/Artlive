package com.connxun.elinetv.entity;

/**
 * Created by connxun-16 on 2017/12/21.
 */

/**
 * json解析的顶级弗雷
 */
public class JsonEntity {

    private String data;
    private String code;
    private String msg;
    private String ext;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    @Override
    public String toString() {
        return "JsonEntity{" +
                "data='" + data + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", ext='" + ext + '\'' +
                '}';
    }
}




