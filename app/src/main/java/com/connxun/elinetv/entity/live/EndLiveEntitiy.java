package com.connxun.elinetv.entity.live;

/**
 * Created by connxun-16 on 2018/3/3.
 */

public class EndLiveEntitiy {
    private int liveSize;
    private int liveDate;
    private int liveVc;
    private int liveViewNum;

    public int getLiveSize() {
        return liveSize;
    }

    public void setLiveSize(int liveSize) {
        this.liveSize = liveSize;
    }

    public int getLiveDate() {
        return liveDate;
    }

    public void setLiveDate(int liveDate) {
        this.liveDate = liveDate;
    }

    public int getLiveVc() {
        return liveVc;
    }

    public void setLiveVc(int liveVc) {
        this.liveVc = liveVc;
    }

    public int getLiveViewNum() {
        return liveViewNum;
    }

    public void setLiveViewNum(int liveViewNum) {
        this.liveViewNum = liveViewNum;
    }

    @Override
    public String toString() {
        return "EndLiveEntitiy{" +
                "liveSize=" + liveSize +
                ", liveDate=" + liveDate +
                ", liveVc=" + liveVc +
                ", liveViewNum=" + liveViewNum +
                '}';
    }
}
