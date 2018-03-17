package com.connxun.elinetv.entity;

/**
 * Created by connxun-16 on 2018/2/8.
 */

import java.io.Serializable;

/**
 * 视频列表参数
 */
public class VideoEtivity implements Serializable{

        private long videoNo;
        private String city;
        private String nickName;
        private String userNo;
        private int topicNo;
        private String avatar;
        private String title;
        private int loveNum;
        private int type;
        private String cover;
        private int viewNum;
        private String width;
        private String topic;
        private String height;

    public long getVideoNo() {
        return videoNo;
    }

    public void setVideoNo(long videoNo) {
        this.videoNo = videoNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public int getTopicNo() {
        return topicNo;
    }

    public void setTopicNo(int topicNo) {
        this.topicNo = topicNo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLoveNum() {
        return loveNum;
    }

    public void setLoveNum(int loveNum) {
        this.loveNum = loveNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "VideoEtivity{" +
                "videoNo=" + videoNo +
                ", city=" + city +
                ", nickName='" + nickName + '\'' +
                ", userNo='" + userNo + '\'' +
                ", topicNo=" + topicNo +
                ", avatar='" + avatar + '\'' +
                ", title='" + title + '\'' +
                ", loveNum=" + loveNum +
                ", type=" + type +
                ", cover='" + cover + '\'' +
                ", viewNum=" + viewNum +
                ", width='" + width + '\'' +
                ", topic='" + topic + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
