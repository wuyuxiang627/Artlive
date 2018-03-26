package com.connxun.elinetv.entity.live.challenge;

/**
 * Created by Administrator on 2018\3\24 0024.
 */

/**
 * 救援票结果
 */
public class RescueResultsEntity {

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


        int rescueNum;
        private String msg;
        private String result;

        public int getRescueNum() {
            return rescueNum;
        }

        public void setRescueNum(int rescueNum) {
            this.rescueNum = rescueNum;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }


}
