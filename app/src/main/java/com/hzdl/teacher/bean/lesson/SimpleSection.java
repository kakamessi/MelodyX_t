package com.hzdl.teacher.bean.lesson;

/**
 * Created by wangshuai on 2017/10/13.
 */

public class SimpleSection {

    //所属小节名称
    private String groupName;
    //单元种类
    private int type;
    //视频名称
    private String videoName;




    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
