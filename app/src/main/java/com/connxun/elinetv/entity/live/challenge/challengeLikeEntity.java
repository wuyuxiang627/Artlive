package com.connxun.elinetv.entity.live.challenge;

/**
 * Created by Administrator on 2018\3\27 0027.
 */

public class challengeLikeEntity {

    private String msg;
    private String ext;
    private String code;
    private Data data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        private int loveNum;

        public int getLoveNum() {
            return loveNum;
        }

        public void setLoveNum(int loveNum) {
            this.loveNum = loveNum;
        }
    }





}
