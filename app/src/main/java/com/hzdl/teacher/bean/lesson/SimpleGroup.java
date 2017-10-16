package com.hzdl.teacher.bean.lesson;

import java.util.List;

/**
 * Created by wangshuai on 2017/10/16.
 */

public class SimpleGroup {

    private List<SimpleSection> list;
    private String name;

    public List<SimpleSection> getList() {
        return list;
    }

    public void setList(List<SimpleSection> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
