package com.connxun.elinetv.entity;

/**
 * Created by connxun-16 on 2018/2/10.
 */

import java.io.Serializable;

/**
 * 用户实体类
 */
public class UserEntity implements Serializable{
    String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "state='" + state + '\'' +
                '}';
    }
}
