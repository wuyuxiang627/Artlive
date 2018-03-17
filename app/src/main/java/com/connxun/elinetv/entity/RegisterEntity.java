package com.connxun.elinetv.entity;

/**
 * Created by connxun-16 on 2017/12/22.
 */

public class RegisterEntity {

    private Data data;
    private String code;
    private String msg;
    private String ext;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getExt() {
        return ext;
    }


    public class Data {
        private String birthday;
        private int userCharm;
        private int followerSize;
        private String city;
        private String nickName;
        private String userNo;
        private int sex;
        private String avatar;
        private int liveId;
        private int usableBalance;
        private String token;
        private String cover;
        private int character;
        private int followNum;
        private int userLever;
        private int id;
        private int state;
        private int userBalance;
        private int status;


        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getUserCharm() {
            return userCharm;
        }

        public void setUserCharm(int userCharm) {
            this.userCharm = userCharm;
        }

        public int getFollowerSize() {
            return followerSize;
        }

        public void setFollowerSize(int followerSize) {
            this.followerSize = followerSize;
        }

        public int getLiveId() {
            return liveId;
        }

        public void setLiveId(int liveId) {
            this.liveId = liveId;
        }

        public int getUsableBalance() {
            return usableBalance;
        }

        public void setUsableBalance(int usableBalance) {
            this.usableBalance = usableBalance;
        }

        public int getCharacter() {
            return character;
        }

        public void setCharacter(int character) {
            this.character = character;
        }

        public int getFollowNum() {
            return followNum;
        }

        public void setFollowNum(int followNum) {
            this.followNum = followNum;
        }

        public int getUserLever() {
            return userLever;
        }

        public void setUserLever(int userLever) {
            this.userLever = userLever;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserBalance() {
            return userBalance;
        }

        public void setUserBalance(int userBalance) {
            this.userBalance = userBalance;
        }


        @Override
        public String toString() {
            return "Data{" +
                    "birthday='" + birthday + '\'' +
                    ", userCharm=" + userCharm +
                    ", followerSize=" + followerSize +
                    ", city='" + city + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", userNo='" + userNo + '\'' +
                    ", sex=" + sex +
                    ", avatar='" + avatar + '\'' +
                    ", liveId=" + liveId +
                    ", usableBalance=" + usableBalance +
                    ", token='" + token + '\'' +
                    ", cover='" + cover + '\'' +
                    ", character=" + character +
                    ", followNum=" + followNum +
                    ", userLever=" + userLever +
                    ", id=" + id +
                    ", state=" + state +
                    ", userBalance=" + userBalance +
                    ", status=" + status +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RegisterEntity{" +
                "data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", ext='" + ext + '\'' +
                '}';
    }
}
