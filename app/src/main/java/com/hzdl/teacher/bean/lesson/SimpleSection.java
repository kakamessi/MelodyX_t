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
    private String showName;
    //资源名称
    private String sourceName;
    //亮灯信号
    private int lightCode;
    //是否投学生屏幕
    private int syncScreen;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public int getLightCode() {
        return lightCode;
    }

    public void setLightCode(int lightCode) {
        this.lightCode = lightCode;
    }

    public int getSyncScreen() {
        return syncScreen;
    }

    public void setSyncScreen(int syncScreen) {
        this.syncScreen = syncScreen;
    }
}
