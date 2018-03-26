package com.connxun.elinetv.entity.Video;

/**
 * Created by Administrator on 2018\3\17 0017.
 */

public class ChallengeTypeThreeEntity {

    private long menuNo;
    private String name;
    private int sort;
    private int type;
    private String content;
    private String color;
    private long showTime;


    public long getShowTime() {
        return showTime;
    }

    public void setShowTime(long showTime) {
        this.showTime = showTime;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "challengeTypethreeEntity{" +
                "menuNo=" + menuNo +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                ", type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
