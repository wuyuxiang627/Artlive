package com.connxun.elinetv.entity;

/**
 * Created by connxun-16 on 2018/3/1.
 */

//礼物
public class Gift {
    private String giftPic;
    private String id;
    private int charmValue;
    private int giftPrice;
    private String giftName;
    private boolean isSelected;//

    public String getGiftPic() {
        return giftPic;
    }

    public void setGiftPic(String giftPic) {
        this.giftPic = giftPic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCharmValue() {
        return charmValue;
    }

    public void setCharmValue(int charmValue) {
        this.charmValue = charmValue;
    }

    public int getGiftPrice() {
        return giftPrice;
    }

    public void setGiftPrice(int giftPrice) {
        this.giftPrice = giftPrice;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Gift{" +
                "giftPic='" + giftPic + '\'' +
                ", id='" + id + '\'' +
                ", charmValue=" + charmValue +
                ", giftPrice=" + giftPrice +
                ", giftName='" + giftName + '\'' +
                '}';
    }
}
