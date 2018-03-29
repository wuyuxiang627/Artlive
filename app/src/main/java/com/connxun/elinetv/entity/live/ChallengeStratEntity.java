package com.connxun.elinetv.entity.live;

import com.connxun.elinetv.entity.live.challenge.RankEntity;

import java.util.List;

/**
 * Created by Administrator on 2018\3\27 0027.
 */
//观看挑战
public class ChallengeStratEntity {
    private int giftTotal;
    private String average;
    private int giftNum;
    private long menuNo;
    private List<RankEntity.Data.Rank> rank;
    private String state;
    private int time;
    private int loveNum;
    private int rescueNum;
    private String result;

    public int getGiftTotal() {
        return giftTotal;
    }

    public void setGiftTotal(int giftTotal) {
        this.giftTotal = giftTotal;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }

    public long getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(long menuNo) {
        this.menuNo = menuNo;
    }

    public List<RankEntity.Data.Rank> getRank() {
        return rank;
    }

    public void setRank(List<RankEntity.Data.Rank> rank) {
        this.rank = rank;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLoveNum() {
        return loveNum;
    }

    public void setLoveNum(int loveNum) {
        this.loveNum = loveNum;
    }

    public int getRescueNum() {
        return rescueNum;
    }

    public void setRescueNum(int rescueNum) {
        this.rescueNum = rescueNum;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
