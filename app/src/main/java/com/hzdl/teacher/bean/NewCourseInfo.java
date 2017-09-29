package com.hzdl.teacher.bean;

import java.util.List;

/**
 * Created by DELL on 2017/5/17.
 */

public class NewCourseInfo {

    private int code;
    private String description;
    private List<CourseBean> detail;

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

    public List<CourseBean> getDetail() {
        return detail;
    }

    public void setDetail(List<CourseBean> detail) {
        this.detail = detail;
    }
}
