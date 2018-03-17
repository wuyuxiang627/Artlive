package com.connxun.elinetv.entity;

/**
 * Created by connxun-16 on 2018/2/10.
 */

import java.io.Serializable;

/**
 *json 数据为data是对象
 * @param <T> 具体对象
 */
public class EntityObject<T> implements Serializable{

    private String ext;
    private String msg;
    private String code;
    private T data;


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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EntityObject{" +
                "ext='" + ext + '\'' +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
