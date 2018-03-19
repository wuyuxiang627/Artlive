package com.connxun.elinetv.entity;

import com.connxun.elinetv.entity.Video.ChallengeTypeThreeEntity;

import java.util.List;

/**
 * Created by Administrator on 2018\3\10 0010.
 */

public class ChallengeTypeEntity {
    private long menuNo;
    private String name;
    private int sort;
    private int type;
    private List<ChallengeTypeThreeEntity> two;
    private String content;

    public ChallengeTypeEntity() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(long menuNo) {
        this.menuNo = menuNo;
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

    public List<ChallengeTypeThreeEntity> getTwo() {
        return two;
    }

    public void setTwo(List<ChallengeTypeThreeEntity> two) {
        this.two = two;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ChallengeTypeEntity{" +
                "menuNo=" + menuNo +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                ", type=" + type +
                ", two=" + two +
                ", content='" + content + '\'' +
                '}';
    }
}
