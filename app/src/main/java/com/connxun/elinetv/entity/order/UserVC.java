package com.connxun.elinetv.entity.order;

/**
 * Created by connxun-16 on 2018/3/1.
 */

/**
 * 用户相关财富
 */
public class UserVC {
    private long userCharm;
    private int character;
    private long userBalance;
    private long usableBalance;


    public long getUserCharm() {
        return userCharm;
    }

    public void setUserCharm(long userCharm) {
        this.userCharm = userCharm;
    }

    public int getCharacter() {
        return character;
    }

    public void setCharacter(int character) {
        this.character = character;
    }

    public long getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(long userBalance) {
        this.userBalance = userBalance;
    }

    public long getUsableBalance() {
        return usableBalance;
    }

    public void setUsableBalance(long usableBalance) {
        this.usableBalance = usableBalance;
    }

    @Override
    public String toString() {
        return "UserVC{" +
                "userCharm=" + userCharm +
                ", character=" + character +
                ", userBalance=" + userBalance +
                ", usableBalance=" + usableBalance +
                '}';
    }
}
