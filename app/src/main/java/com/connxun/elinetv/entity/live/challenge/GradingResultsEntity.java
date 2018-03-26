package com.connxun.elinetv.entity.live.challenge;

/**
 * Created by Administrator on 2018\3\24 0024.
 */

/**
 * 评分结果
 */
public class GradingResultsEntity {

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


        public class  Data {
                private String msg;
                private String result;
                private int count;
                private int average;
                private int loveNumScore;
                private int giftNumScore;

                public void setMsg(String msg) {
                    this.msg = msg;
                }

                public String getMsg() {
                    return msg;
                }

                public void setResult(String result) {
                    this.result = result;
                }

                public String getResult() {
                    return result;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public int getCount() {
                    return count;
                }

                public void setAverage(int average) {
                    this.average = average;
                }

                public int getAverage() {
                    return average;
                }

                public void setLoveNumScore(int loveNumScore) {
                    this.loveNumScore = loveNumScore;
                }

                public int getLoveNumScore() {
                    return loveNumScore;
                }

                public void setGiftNumScore(int giftNumScore) {
                    this.giftNumScore = giftNumScore;
                }

                public int getGiftNumScore() {
                    return giftNumScore;
                }

            }


}
