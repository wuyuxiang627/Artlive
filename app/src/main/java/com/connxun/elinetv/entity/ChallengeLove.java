package com.connxun.elinetv.entity;

/**
 * Created by Administrator on 2018\3\28 0028.
 */

public class ChallengeLove {
    private Data data;
    private int msgType;
    private long ts;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public class Data{
        private int loveNum;
        private long PushId;

        public int getLoveNum() {
            return loveNum;
        }

        public void setLoveNum(int loveNum) {
            this.loveNum = loveNum;
        }

        public long getPushId() {
            return PushId;
        }

        public void setPushId(long pushId) {
            PushId = pushId;
        }
    }


}
