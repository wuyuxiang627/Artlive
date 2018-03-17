package com.connxun.elinetv.entity;

/**
 * Created by connxun-16 on 2018/1/12.
 */

public class BeautyType {
    int img;
    String text;
    //是否选中
    boolean checked;


    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
