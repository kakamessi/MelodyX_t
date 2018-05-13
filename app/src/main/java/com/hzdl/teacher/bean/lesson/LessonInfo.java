package com.hzdl.teacher.bean.lesson;

import com.hzdl.mex.utils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshuai on 2017/10/13.
 */

public class LessonInfo {

    private List<SimpleGroup> groupList;
    private int lessonId;
    private String name;

    //业务逻辑集合 所有小节列表
    private List<SimpleSection> sectionsList;
    //分类宫商角徵羽
    private List<SimpleSection> sectionsGSJ = new ArrayList<SimpleSection>();
    //分类 从耳德与歌
    private List<SimpleSection> sectionsCED = new ArrayList<SimpleSection>();
    //分类 弹奏
    private List<SimpleSection> sectionsTZ = new ArrayList<SimpleSection>();

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
                    ss.setGroupName(sg.getName());

                    if("宫商角徴羽".equals(ss.getGroupName())){
                        sectionsGSJ.add(ss);
                    }else if(ss.getGroupName().equals("从耳德，与歌") || ss.getGroupName().equals("从耳德与歌")){
                        sectionsCED.add(ss);
                    }else if(ss.getGroupName().equals("弹奏")){
                        sectionsTZ.add(ss);
                    }

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

    public SimpleSection getSection(int i) {
        return sectionsList.get(i);
    }

    public List<SimpleSection> getSectionsList() {
        return sectionsList;
    }

    public void setSectionsList(List<SimpleSection> sectionsList) {
        this.sectionsList = sectionsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SimpleSection> getSectionsGSJ() {
        return sectionsGSJ;
    }

    public List<SimpleSection> getSectionsCED() {
        return sectionsCED;
    }

    public List<SimpleSection> getSectionsTZ() {
        return sectionsTZ;
    }
}
