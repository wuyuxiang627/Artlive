package com.connxun.elinetv.entity.Video;

/**
 * Created by connxun-16 on 2018/3/2.
 */


public class Talk {

    private String nickName;
    private long contentNo;
    private long userNo;
    private String avatar;
    private long extendNo;
    private int loveNum;
    private String content;
    private int loveState;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getContentNo() {
        return contentNo;
    }

    public void setContentNo(long contentNo) {
        this.contentNo = contentNo;
    }

    public long getUserNo() {
        return userNo;
    }

    public void setUserNo(long userNo) {
        this.userNo = userNo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getExtendNo() {
        return extendNo;
    }

    public void setExtendNo(long extendNo) {
        this.extendNo = extendNo;
    }

    public int getLoveNum() {
        return loveNum;
    }

    public void setLoveNum(int loveNum) {
        this.loveNum = loveNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLoveState() {
        return loveState;
    }

    public void setLoveState(int loveState) {
        this.loveState = loveState;
    }

    @Override
    public String toString() {
        return "Talk{" +
                "nickName='" + nickName + '\'' +
                ", contentNo=" + contentNo +
                ", userNo=" + userNo +
                ", avatar='" + avatar + '\'' +
                ", extendNo=" + extendNo +
                ", loveNum=" + loveNum +
                ", content='" + content + '\'' +
                ", loveState=" + loveState +
                '}';
    }
}
