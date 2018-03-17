package com.connxun.elinetv.entity.order;

/**
 * Created by connxun-16 on 2018/3/2.
 */

/**
 *订单
 */
public class Order {
    private long orderNo;
    private int finalAmount;
    private long userNo;
    private int extendNo;
    private int type;

    public long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
    }

    public int getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(int finalAmount) {
        this.finalAmount = finalAmount;
    }

    public long getUserNo() {
        return userNo;
    }

    public void setUserNo(long userNo) {
        this.userNo = userNo;
    }

    public int getExtendNo() {
        return extendNo;
    }

    public void setExtendNo(int extendNo) {
        this.extendNo = extendNo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNo=" + orderNo +
                ", finalAmount=" + finalAmount +
                ", userNo=" + userNo +
                ", extendNo=" + extendNo +
                ", type=" + type +
                '}';
    }
}
