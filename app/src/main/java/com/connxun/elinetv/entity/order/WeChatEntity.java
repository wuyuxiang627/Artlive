package com.connxun.elinetv.entity.order;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018\3\7 0007.
 */

public class WeChatEntity {

    @SerializedName("package")
    String weChatPackage;
    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getWeChatPackage() {
        return weChatPackage;
    }

    public void setWeChatPackage(String weChatPackage) {
        this.weChatPackage = weChatPackage;
    }

    @Override
    public String toString() {
        return "WeChatEntity{" +
                "weChatPackage='" + weChatPackage + '\'' +
                ", appid='" + appid + '\'' +
                ", sign='" + sign + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
