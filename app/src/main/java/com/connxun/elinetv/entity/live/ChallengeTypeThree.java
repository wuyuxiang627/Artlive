package com.connxun.elinetv.entity.live;

/**
 * Created by Administrator on 2018\3\19 0019.
 */

public class ChallengeTypeThree {
    private String cover;
    private long resourceNo;
    private String author;
    private int showTime;
    private String name;
    private String url;
    private String content;
    private boolean blType = true;


    public boolean isBlType() {
        return blType;
    }

    public void setBlType(boolean blType) {
        this.blType = blType;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public long getResourceNo() {
        return resourceNo;
    }

    public void setResourceNo(long resourceNo) {
        this.resourceNo = resourceNo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getShowTime() {
        return showTime;
    }

    public void setShowTime(int showTime) {
        this.showTime = showTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ChallengeTypeThree{" +
                "cover='" + cover + '\'' +
                ", resourceNo=" + resourceNo +
                ", author='" + author + '\'' +
                ", showTime=" + showTime +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
