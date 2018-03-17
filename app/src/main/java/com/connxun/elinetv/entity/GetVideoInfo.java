package com.connxun.elinetv.entity;

/**
 * Created by connxun-16 on 2018/2/10.
 */


import java.io.Serializable;

/**
 * 视频详情
 */
public class GetVideoInfo implements Serializable{

    UserEntity user;
    VideoInfoEntity info;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public VideoInfoEntity getInfo() {
        return info;
    }

    public void setInfo(VideoInfoEntity info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "GetVideoInfo{" +
                "user=" + user +
                ", info=" + info +
                '}';
    }
}
