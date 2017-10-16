package com.hzdl.teacher.bean.lesson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshuai on 2017/10/13.
 */

public class LessonInfo {

    private List<SimpleGroup> groupList;
    private int lessonId;
    private List<SimpleSection> sectionsList;


    public List<SimpleGroup> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<SimpleGroup> groupList) {
        this.groupList = groupList;

        if(sectionsList==null){
            sectionsList = new ArrayList<SimpleSection>();
        }

        for(SimpleGroup sg : groupList){
            if(sg.getList()!=null) {
                for (SimpleSection ss : sg.getList()) {
                    sectionsList.add(ss);
                }
            }
        }

    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public List<SimpleSection> getSectionsList() {
        return sectionsList;
    }

}
