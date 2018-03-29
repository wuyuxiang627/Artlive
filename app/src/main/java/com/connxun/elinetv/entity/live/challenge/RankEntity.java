package com.connxun.elinetv.entity.live.challenge;

import java.util.List;

/**
 * Created by Administrator on 2018\3\24 0024.
 */

/**
 * 挑战排行榜
 */
public class RankEntity {

    private Data data;
    private int msgType;
    private long ts;
    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }
    public int getMsgType() {
        return msgType;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
    public long getTs() {
        return ts;
    }


    public static class  Data {
        int giftTotal;
        int giftNum;
        List<Rank> rank;

        public int getGiftTotal() {
            return giftTotal;
        }

        public void setGiftTotal(int giftTotal) {
            this.giftTotal = giftTotal;
        }

        public int getGiftNum() {
            return giftNum;
        }

        public void setGiftNum(int giftNum) {
            this.giftNum = giftNum;
        }

        public List<Rank> getRank() {
            return rank;
        }

        public void setRank(List<Rank> rank) {
            this.rank = rank;
        }

        public static   class Rank{
            String nickName;
            String userNo;
            String avatar;

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getUserNo() {
                return userNo;
            }

            public void setUserNo(String userNo) {
                this.userNo = userNo;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            @Override
            public String toString() {
                return "Rank{" +
                        "nickName='" + nickName + '\'' +
                        ", userNo='" + userNo + '\'' +
                        ", avatar='" + avatar + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "RankEntity{" +
                    "giftNum=" + giftNum +
                    ", rank=" + rank +
                    '}';
        }


    }

}
