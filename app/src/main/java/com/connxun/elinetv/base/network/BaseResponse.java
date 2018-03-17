package com.connxun.elinetv.base.network;

/**
 * Created by connxun-16 on 2018/1/30.
 */

public class BaseResponse<T> {

    private int code;
    private String msg;
    private String ext;
    private T data;

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk() {
        return code == 200;
    }
}
