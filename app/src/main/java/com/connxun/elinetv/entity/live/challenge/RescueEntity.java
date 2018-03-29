package com.connxun.elinetv.entity.live.challenge;

/**
 * Created by Administrator on 2018\3\24 0024.
 */

/**
 * 用户投救援票`
 */
public class RescueEntity {


    private Data data;
    private int msgType;
    private long ts;
    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }
    public int getMsgType() {
        return msgType;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
    public long getTs() {
        return ts;
    }


    public class  Data {


        private long pushId;
        private String msg;
        private int rescueNum;

        public long getPushId() {
            return pushId;
        }

        public void setPushId(long pushId) {
            this.pushId = pushId;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getRescueNum() {
            return rescueNum;
        }

        public void setRescueNum(int rescueNum) {
            this.rescueNum = rescueNum;
        }
    }
}
