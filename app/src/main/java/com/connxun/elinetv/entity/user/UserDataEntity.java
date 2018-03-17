package com.connxun.elinetv.entity.user;

/**
 * Created by connxun-16 on 2018/3/9.
 */

public class UserDataEntity {

    private String nickName;//昵称
    private String birthday;//生日
    private String city;//定位
    private String recommender; //推荐人
    private String userNo;//用户id
    private String tel;//电话
    private String avatar;//头像

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UserDataEntity{" +
                "nickName='" + nickName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", city='" + city + '\'' +
                ", recommender='" + recommender + '\'' +
                ", userNo='" + userNo + '\'' +
                ", tel='" + tel + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
