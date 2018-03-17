package com.connxun.elinetv.entity.user;

/**
 * Created by Administrator on 2018\3\15 0015.
 */

public class LabelEntity {

    long userLabelNo;
    long menuNo;
    String name;


    public long getUserLabelNo() {
        return userLabelNo;
    }

    public void setUserLabelNo(long userLabelNo) {
        this.userLabelNo = userLabelNo;
    }

    public long getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(long menuNo) {
        this.menuNo = menuNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "labelEntity{" +
                "userLabelNo=" + userLabelNo +
                ", menuNo=" + menuNo +
                ", name='" + name + '\'' +
                '}';
    }
}
