package com.hzdl.teacher.bean.lesson;

import java.util.List;

/**
 * Created by wangshuai on 2017/10/13.
 */

public class LessonInfo {

    private List<SimpleSection> list;

    //获取小节
    public SimpleSection getSection(int position){

        return list.get(position);
    }

}
