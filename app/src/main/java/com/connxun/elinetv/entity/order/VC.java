package com.connxun.elinetv.entity.order;

/**
 * Created by connxun-16 on 2018/3/2.
 */

import java.io.Serializable;

/**
 * 金币
 */
public class VC implements Serializable{

    private int price;
    private int vcNo;
    private int id;
    private int vc;
    private int giveVc;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getVcNo() {
        return vcNo;
    }

    public void setVcNo(int vcNo) {
        this.vcNo = vcNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVc() {
        return vc;
    }

    public void setVc(int vc) {
        this.vc = vc;
    }

    public int getGiveVc() {
        return giveVc;
    }

    public void setGiveVc(int giveVc) {
        this.giveVc = giveVc;

    }

    @Override
    public String toString() {
        return "VC{" +
                "price=" + price +
                ", vcNo=" + vcNo +
                ", id=" + id +
                ", vc=" + vc +
                ", giveVc=" + giveVc +
                '}';
    }
}



