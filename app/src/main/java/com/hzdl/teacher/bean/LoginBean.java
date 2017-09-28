package com.hzdl.teacher.bean;

/**
 * Created by wangshuai on 2017/9/28.
 */

public class LoginBean {

    /**
     * code : 200
     * description : 请求成功
     * detail : {"teacherId":2,"phone":"13700000001","schoolId":2,"nickname":"程序猿甲","schoolName":"小学学校"}
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
         * teacherId : 2
         * phone : 13700000001
         * schoolId : 2
         * nickname : 程序猿甲
         * schoolName : 小学学校
         */

        private int teacherId;
        private String phone;
        private int schoolId;
        private String nickname;
        private String schoolName;

        public int getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(int schoolId) {
            this.schoolId = schoolId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }
    }
}
