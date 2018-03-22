package com.connxun.elinetv.entity;

/**
 * Created by Administrator on 2018\3\22 0022.
 */

public class Test
{
    /**
     * ext :
     * msg : 操作成功
     * code : 200
     * data : {"hlsPullUrl":"http://pullhls68ef2a85.live.126.net/live/c3d4c4337ee14f03bf10bf9fef1d217d/playlist.m3u8","menuNo":1,"rtmpPullUrl":"rtmp://v68ef2a85.live.126.net/live/c3d4c4337ee14f03bf10bf9fef1d217d","liveNo":"419655041809383424","userNo":"418000012165775360","liveId":10018,"roomid":22163563,"httpPullUrl":"http://flv68ef2a85.live.126.net/live/c3d4c4337ee14f03bf10bf9fef1d217d.flv?netease=flv68ef2a85.live.126.net","pushUrl":"rtmp://p68ef2a85.live.126.net/live/c3d4c4337ee14f03bf10bf9fef1d217d?wsSecret=3558bbca2e73b0c56399209c45b33e36&wsTime=1520095158","name":"ss \\n ","ctime":1520095158906,"position":100000,"cid":"c3d4c4337ee14f03bf10bf9fef1d217d"}
     */

    private String ext;
    private String msg;
    private String code;
    private DataBean data;

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * hlsPullUrl : http://pullhls68ef2a85.live.126.net/live/c3d4c4337ee14f03bf10bf9fef1d217d/playlist.m3u8
         * menuNo : 1
         * rtmpPullUrl : rtmp://v68ef2a85.live.126.net/live/c3d4c4337ee14f03bf10bf9fef1d217d
         * liveNo : 419655041809383424
         * userNo : 418000012165775360
         * liveId : 10018
         * roomid : 22163563
         * httpPullUrl : http://flv68ef2a85.live.126.net/live/c3d4c4337ee14f03bf10bf9fef1d217d.flv?netease=flv68ef2a85.live.126.net
         * pushUrl : rtmp://p68ef2a85.live.126.net/live/c3d4c4337ee14f03bf10bf9fef1d217d?wsSecret=3558bbca2e73b0c56399209c45b33e36&wsTime=1520095158
         * name : ss \n
         * ctime : 1520095158906
         * position : 100000
         * cid : c3d4c4337ee14f03bf10bf9fef1d217d
         */

        private String hlsPullUrl;
        private int menuNo;
        private String rtmpPullUrl;
        private String liveNo;
        private String userNo;
        private int liveId;
        private int roomid;
        private String httpPullUrl;
        private String pushUrl;
        private String name;
        private long ctime;
        private int position;
        private String cid;

        public String getHlsPullUrl() {
            return hlsPullUrl;
        }

        public void setHlsPullUrl(String hlsPullUrl) {
            this.hlsPullUrl = hlsPullUrl;
        }

        public int getMenuNo() {
            return menuNo;
        }

        public void setMenuNo(int menuNo) {
            this.menuNo = menuNo;
        }

        public String getRtmpPullUrl() {
            return rtmpPullUrl;
        }

        public void setRtmpPullUrl(String rtmpPullUrl) {
            this.rtmpPullUrl = rtmpPullUrl;
        }

        public String getLiveNo() {
            return liveNo;
        }

        public void setLiveNo(String liveNo) {
            this.liveNo = liveNo;
        }

        public String getUserNo() {
            return userNo;
        }

        public void setUserNo(String userNo) {
            this.userNo = userNo;
        }

        public int getLiveId() {
            return liveId;
        }

        public void setLiveId(int liveId) {
            this.liveId = liveId;
        }

        public int getRoomid() {
            return roomid;
        }

        public void setRoomid(int roomid) {
            this.roomid = roomid;
        }

        public String getHttpPullUrl() {
            return httpPullUrl;
        }

        public void setHttpPullUrl(String httpPullUrl) {
            this.httpPullUrl = httpPullUrl;
        }

        public String getPushUrl() {
            return pushUrl;
        }

        public void setPushUrl(String pushUrl) {
            this.pushUrl = pushUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getCtime() {
            return ctime;
        }

        public void setCtime(long ctime) {
            this.ctime = ctime;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }
    }
}
