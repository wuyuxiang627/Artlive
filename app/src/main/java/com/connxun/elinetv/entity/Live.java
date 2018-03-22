package com.connxun.elinetv.entity;

/**
 * Created by connxun-16 on 2018/1/23.
 */

import java.io.Serializable;

/**
 * 直播列表参数
 */
public class Live implements Serializable{

    private String hlsPullUrl;
    private int menuNo;
    private String rtmpPullUrl;
    private String liveNo;
    private String userNo;
    private long roomid;
    private String cover;
    private String httpPullUrl;
    private String pushUrl;
    private String name;
    private long ctime;
    private String position;
    private String cid;
    private String liveId;

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getHlsPullUrl() {
        return hlsPullUrl;
    }

    public void setHlsPullUrl(String hlsPullUrl) {
        this.hlsPullUrl = hlsPullUrl;
    }

    public int getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(int menuNo) {
        this.menuNo = menuNo;
    }

    public String getRtmpPullUrl() {
        return rtmpPullUrl;
    }

    public void setRtmpPullUrl(String rtmpPullUrl) {
        this.rtmpPullUrl = rtmpPullUrl;
    }

    public String getLiveNo() {
        return liveNo;
    }

    public void setLiveNo(String liveNo) {
        this.liveNo = liveNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public long getRoomid() {
        return roomid;
    }

    public void setRoomid(long roomid) {
        this.roomid = roomid;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getHttpPullUrl() {
        return httpPullUrl;
    }

    public void setHttpPullUrl(String httpPullUrl) {
        this.httpPullUrl = httpPullUrl;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "Live{" +
                "hlsPullUrl='" + hlsPullUrl + '\'' +
                ", menuNo=" + menuNo +
                ", rtmpPullUrl='" + rtmpPullUrl + '\'' +
                ", liveNo='" + liveNo + '\'' +
                ", userNo='" + userNo + '\'' +
                ", roomid=" + roomid +
                ", cover='" + cover + '\'' +
                ", httpPullUrl='" + httpPullUrl + '\'' +
                ", pushUrl='" + pushUrl + '\'' +
                ", name='" + name + '\'' +
                ", ctime=" + ctime +
                ", position='" + position + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }
}
