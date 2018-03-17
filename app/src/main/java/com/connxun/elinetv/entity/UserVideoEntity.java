package com.connxun.elinetv.entity;

/**
 * Created by connxun-16 on 2018/1/29.
 */

public class UserVideoEntity {
    private String msg;
    private String ext;
    private String code;
    private Data data;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserVideoEntity{" +
                "msg='" + msg + '\'' +
                ", ext='" + ext + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data {

        private VideoToken videoToken;

        public VideoToken getVideoToken() {
            return videoToken;
        }

        public void setVideoToken(VideoToken videoToken) {
            this.videoToken = videoToken;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "videoToken=" + videoToken +
                    '}';
        }

        public class VideoToken{
            private String name;
            private String accid;
            private String props;
            private String token;


            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAccid() {
                return accid;
            }

            public void setAccid(String accid) {
                this.accid = accid;
            }

            public String getProps() {
                return props;
            }

            public void setProps(String props) {
                this.props = props;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
            @Override
            public String toString() {
                return "Data{" +
                        "name='" + name + '\'' +
                        ", accid='" + accid + '\'' +
                        ", props='" + props + '\'' +
                        ", token='" + token + '\'' +
                        '}';
            }
        }


    }
}
