package com.hzdl.teacher.bean;

/**
 * Created by wangshuai on 2017/9/30.
 */

public class UpdateInfoBean {


    /**
     * code : 200
     * description : 请求成功
     * detail : {"code":"0.0.2","createDate":"2017-02-20 12:02:18","id":5,"info":"走过路过看Yi看fgfdgfdgfgfg","isforced":1,"remark":"哈哈啊","state":1,"type":1,"url":"/apk/2017-02-16/teacher_v2.0.2.apk"}
     */

    private int code;
    private String description;
    private DetailLoginBean detail;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DetailLoginBean getDetail() {
        return detail;
    }

    public void setDetail(DetailLoginBean detail) {
        this.detail = detail;
    }

    public static class DetailLoginBean {
        /**
         * code : 0.0.2
         * createDate : 2017-02-20 12:02:18
         * id : 5
         * info : 走过路过看Yi看fgfdgfdgfgfg
         * isforced : 1
         * remark : 哈哈啊
         * state : 1
         * type : 1
         * url : /apk/2017-02-16/teacher_v2.0.2.apk
         */

        private String code;
        private String createDate;
        private int id;
        private String info;
        private int isforced;
        private String remark;
        private int state;
        private int type;
        private String url;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getIsforced() {
            return isforced;
        }

        public void setIsforced(int isforced) {
            this.isforced = isforced;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
