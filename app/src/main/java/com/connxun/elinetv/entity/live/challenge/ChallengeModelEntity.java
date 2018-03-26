package com.connxun.elinetv.entity.live.challenge;

/**
 * Created by Administrator on 2018\3\24 0024.
 */

/**
 * 创建挑战
 */
public class ChallengeModelEntity {
        /**
         * hlsPullUrl : http://pullhls68ef2a85.live.126.net/live/527b4110d3ac4ef7a7335cf08997ac9b/playlist.m3u8
         * rtmpPullUrl : rtmp://v68ef2a85.live.126.net/live/527b4110d3ac4ef7a7335cf08997ac9b
         * liveNo : 425281440813416448
         * userNo : 418449240532975616
         * challengeNo : 425281452385501200
         * liveId : 10023
         * roomid : 22482088
         * cover : http://art.nos-eastchina1.126.net/cms1521112115736.jpg
         * httpPullUrl : http://flv68ef2a85.live.126.net/live/527b4110d3ac4ef7a7335cf08997ac9b.flv?netease=flv68ef2a85.live.126.net
         * pushUrl : rtmp://p68ef2a85.live.126.net/live/527b4110d3ac4ef7a7335cf08997ac9b?wsSecret=47285deb786f779998a9f16f0b9fe0da&wsTime=1521436598
         * name : 123挑战赛
         * ctime : 1521436598538
         * position : 11000
         * cid : 527b4110d3ac4ef7a7335cf08997ac9b
         */

        private String hlsPullUrl;
        private String rtmpPullUrl;
        private String liveNo;
        private String userNo;
        private long challengeNo;
        private int liveId;
        private int roomid;
        private String cover;
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

        public long getChallengeNo() {
            return challengeNo;
        }

        public void setChallengeNo(long challengeNo) {
            this.challengeNo = challengeNo;
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

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
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

    @Override
    public String toString() {
        return "ChallengeModelEntity{" +
                "hlsPullUrl='" + hlsPullUrl + '\'' +
                ", rtmpPullUrl='" + rtmpPullUrl + '\'' +
                ", liveNo='" + liveNo + '\'' +
                ", userNo='" + userNo + '\'' +
                ", challengeNo=" + challengeNo +
                ", liveId=" + liveId +
                ", roomid=" + roomid +
                ", cover='" + cover + '\'' +
                ", httpPullUrl='" + httpPullUrl + '\'' +
                ", pushUrl='" + pushUrl + '\'' +
                ", name='" + name + '\'' +
                ", ctime=" + ctime +
                ", position=" + position +
                ", cid='" + cid + '\'' +
                '}';
    }
}
