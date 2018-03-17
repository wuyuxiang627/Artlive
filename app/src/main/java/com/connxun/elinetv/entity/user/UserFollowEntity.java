package com.connxun.elinetv.entity.user;

/**
 * Created by Administrator on 2018\3\6 0006.
 */

public class UserFollowEntity {

    private String nickName;
    private String userNo;
    private String avatart;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public String getAvatart() {
        return avatart;
    }

    public void setAvatart(String avatart) {
        this.avatart = avatart;
    }


    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Override
    public String toString() {
        return "UserFollowEntity{" +
                "nickName='" + nickName + '\'' +
                ", userNo='" + userNo + '\'' +
                ", avatart='" + avatart + '\'' +
                '}';
    }
}
