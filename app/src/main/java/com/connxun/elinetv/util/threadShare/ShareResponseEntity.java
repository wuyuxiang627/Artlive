package com.connxun.elinetv.util.threadShare;

/**
 * Created by Administrator on 2017/5/11.
 */

public class ShareResponseEntity {
    private String userID;
    private String userName;
    private String userIcon;
    private int userSex;
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }


    @Override
    public String toString() {
        return "ShareResponseEntity{" +
                "userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", userSex=" + userSex +
                '}';
    }
}
