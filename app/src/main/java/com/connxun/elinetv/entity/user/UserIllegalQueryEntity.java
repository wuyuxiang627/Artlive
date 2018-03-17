package com.connxun.elinetv.entity.user;

/**
 * Created by connxun-16 on 2018/3/8.
 */

public class UserIllegalQueryEntity {
    private String cause;
    private String createDate;
    private int state;
    private long illegalno;

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getIllegalno() {
        return illegalno;
    }

    public void setIllegalno(long illegalno) {
        this.illegalno = illegalno;
    }

    @Override
    public String toString() {
        return "UserIllegalQueryEntity{" +
                "cause='" + cause + '\'' +
                ", createDate='" + createDate + '\'' +
                ", state=" + state +
                ", illegalno=" + illegalno +
                '}';
    }
}
