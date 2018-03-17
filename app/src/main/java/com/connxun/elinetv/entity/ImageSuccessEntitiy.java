package com.connxun.elinetv.entity;

/**
 * Created by connxun-16 on 2018/2/6.
 */

public class ImageSuccessEntitiy {
    private String requestID;
    private String offset;


    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "ImageSuccessEntitiy{" +
                "requestID='" + requestID + '\'' +
                ", offset='" + offset + '\'' +
                '}';
    }
}
