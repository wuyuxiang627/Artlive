package com.connxun.elinetv.entity;

/**
 * Created by connxun-16 on 2018/2/8.
 */

/**
 * /**
 *json 数据为data是集合
 * @param <T>
 */
public class Entity<T> {

    private String ext;
    private String msg;
    private String code;
    private Data<T> data;

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Data<T> getData() {
        return data;
    }

    public void setData(Data<T> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "Entity{" +
                "ext='" + ext + '\'' +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
