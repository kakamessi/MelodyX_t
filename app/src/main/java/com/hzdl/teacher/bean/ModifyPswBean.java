package com.hzdl.teacher.bean;

/**
 * Created by wangshuai on 2017/10/11.
 */

public class ModifyPswBean {


    /**
     * code : 200
     * description : 请求成功
     * detail : {}
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
    }
}
