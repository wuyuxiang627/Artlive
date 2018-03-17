package com.connxun.elinetv.entity.order;

/**
 * Created by Administrator on 2018\3\9 0009.
 */

public class WithdrawalsList {

    private String alipay;
    private int cashCoin;
    private int money;
    private String name;
    private long beforeBalance;
    private int state;
    private long afterBalance;
    private String createDate;


    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public int getCashCoin() {
        return cashCoin;
    }

    public void setCashCoin(int cashCoin) {
        this.cashCoin = cashCoin;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(long beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(long afterBalance) {
        this.afterBalance = afterBalance;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "WithdrawalsList{" +
                "alipay='" + alipay + '\'' +
                ", cashCoin=" + cashCoin +
                ", money=" + money +
                ", name='" + name + '\'' +
                ", beforeBalance=" + beforeBalance +
                ", state=" + state +
                ", afterBalance=" + afterBalance +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
