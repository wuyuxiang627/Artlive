package com.connxun.elinetv.entity;

/**
 * Created by connxun-16 on 2018/3/3.
 */

public class AttentionEntity {

    int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AttentionEntity{" +
                "status=" + status +
                '}';
    }
}
