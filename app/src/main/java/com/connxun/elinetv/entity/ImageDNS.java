package com.connxun.elinetv.entity;

import java.util.List;

/**
 * Created by connxun-16 on 2018/2/6.
 */

public class ImageDNS {

    //{"lbs":"http://106.2.45.249/lbs","upload":["http://106.2.45.251"]}

    private String  lbs;
    private List<String> upload;

    public String getLbs() {
        return lbs;
    }

    public void setLbs(String lbs) {
        this.lbs = lbs;
    }

    public List<String> getUpload() {
        return upload;
    }

    public void setUpload(List<String> upload) {
        this.upload = upload;
    }

    @Override
    public String toString() {
        return "ImageDNS{" +
                "lbs='" + lbs + '\'' +
                ", upload=" + upload +
                '}';
    }
}
