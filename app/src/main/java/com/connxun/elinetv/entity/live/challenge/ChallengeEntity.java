package com.connxun.elinetv.entity.live.challenge;

/**
 * Created by Administrator on 2018\3\24 0024.
 */

public class ChallengeEntity<T> {
    String msgType;
    int ts;
    T data;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ChallengeModelEntity{" +
                "msgType='" + msgType + '\'' +
                ", ts=" + ts +
                ", data=" + data +
                '}';
    }
}
