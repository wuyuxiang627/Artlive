package com.connxun.elinetv.entity;

/**
 * Created by connxun-16 on 2018/1/29.
 */

import java.io.Serializable;

/**
 * 直播参数
 */
public class LiveModel implements Serializable{

    private String birthday;
    private String hlsPullUrl;
    private String rtmpPullUrl;
    private String nickName;
    private String liveNo;
    private String userNo;
    private String sex;
    private String avatar;
    private String liveId;
    private long roomid;
    private String cover;
    private String httpPullUrl;
    private int viewNum;
    private String pushUrl;
    private String name;
    private String position;
    private int menuNo;
    private long ctime;
    private String cid;

    public int getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(int menuNo) {
        this.menuNo = menuNo;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHlsPullUrl() {
        return hlsPullUrl;
    }

    public void setHlsPullUrl(String hlsPullUrl) {
        this.hlsPullUrl = hlsPullUrl;
    }

    public String getRtmpPullUrl() {
        return rtmpPullUrl;
    }

    public void setRtmpPullUrl(String rtmpPullUrl) {
        this.rtmpPullUrl = rtmpPullUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
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

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "LiveModel{" +
                "birthday='" + birthday + '\'' +
                ", hlsPullUrl='" + hlsPullUrl + '\'' +
                ", rtmpPullUrl='" + rtmpPullUrl + '\'' +
                ", nickName='" + nickName + '\'' +
                ", liveNo='" + liveNo + '\'' +
                ", userNo='" + userNo + '\'' +
                ", sex='" + sex + '\'' +
                ", avatar='" + avatar + '\'' +
                ", liveId='" + liveId + '\'' +
                ", roomid=" + roomid +
                ", cover='" + cover + '\'' +
                ", httpPullUrl='" + httpPullUrl + '\'' +
                ", viewNum=" + viewNum +
                ", pushUrl='" + pushUrl + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", menuNo=" + menuNo +
                ", ctime=" + ctime +
                ", cid='" + cid + '\'' +
                '}';
    }
}
