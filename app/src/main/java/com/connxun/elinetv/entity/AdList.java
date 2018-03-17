package com.connxun.elinetv.entity;

/**
 * Created by connxun-16 on 2018/1/23.
 */

/**
 * 首页轮播
 */
public class AdList {
    private String adName;
    private String adPic;
    private int adType;
    private String adUrl;
    private String id;

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getAdPic() {
        return adPic;
    }

    public void setAdPic(String adPic) {
        this.adPic = adPic;
    }

    public int getAdType() {
        return adType;
    }

    public void setAdType(int adType) {
        this.adType = adType;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AdList{" +
                "adName='" + adName + '\'' +
                ", adPic='" + adPic + '\'' +
                ", adType=" + adType +
                ", adUrl='" + adUrl + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
