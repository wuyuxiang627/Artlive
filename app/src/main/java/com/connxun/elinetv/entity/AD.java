package com.connxun.elinetv.entity;

import java.util.List;

/**
 * Created by connxun-16 on 2018/1/23.
 */

public class AD {

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
        return "AD{" +
                "msg='" + msg + '\'' +
                ", ext='" + ext + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data {

        private String msg;
        private String ext;
        private String code;
        private DataD data;
        public void setMsg(String msg) {
            this.msg = msg;
        }
        public String getMsg() {
            return msg;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }
        public String getExt() {
            return ext;
        }

        public void setCode(String code) {
            this.code = code;
        }
        public String getCode() {
            return code;
        }

        public void setData(DataD data) {
            this.data = data;
        }
        public DataD getData() {
            return data;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "msg='" + msg + '\'' +
                    ", ext='" + ext + '\'' +
                    ", code='" + code + '\'' +
                    ", data=" + data +
                    '}';
        }

        public class  DataD{
            private int totalRow;
            private int pageNumber;
            private boolean firstPage;
            private boolean lastPage;
            private int totalPage;
            private int pageSize;
            private List<AdList> list;

            public int getTotalRow() {
                return totalRow;
            }

            public void setTotalRow(int totalRow) {
                this.totalRow = totalRow;
            }

            public int getPageNumber() {
                return pageNumber;
            }

            public void setPageNumber(int pageNumber) {
                this.pageNumber = pageNumber;
            }

            public boolean isFirstPage() {
                return firstPage;
            }

            public void setFirstPage(boolean firstPage) {
                this.firstPage = firstPage;
            }

            public boolean isLastPage() {
                return lastPage;
            }

            public void setLastPage(boolean lastPage) {
                this.lastPage = lastPage;
            }

            public int getTotalPage() {
                return totalPage;
            }

            public void setTotalPage(int totalPage) {
                this.totalPage = totalPage;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public List<AdList> getList() {
                return list;
            }

            public void setList(List<AdList> list) {
                this.list = list;
            }

            @Override
            public String toString() {
                return "DataD{" +
                        "totalRow=" + totalRow +
                        ", pageNumber=" + pageNumber +
                        ", firstPage=" + firstPage +
                        ", lastPage=" + lastPage +
                        ", totalPage=" + totalPage +
                        ", pageSize=" + pageSize +
                        ", list=" + list +
                        '}';
            }
        }




    }


}
