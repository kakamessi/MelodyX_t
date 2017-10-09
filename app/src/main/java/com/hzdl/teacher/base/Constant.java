package com.hzdl.teacher.base;

/**
 * Created by wangshuai on 2017/9/14.
 */

public class Constant {

    /**
     * 参数相关
     */
    //视频文件存储路径
    public static String FILE_PATH = "/xmelody/";


    //----课程下载相关-------------------------------------------------------------------------------------------
    public static final String DOWNLOAD_ROOT = "http://newapp.tianshiyinyue.cn";
    public static final String DOWNLOAD_PATH = "/client/course/findCourseOnlyPath.json";


    public static final String DOWNLOAD_APK = "/client/course/findCourseOnlyPath.json";


    //----网络相关-------------------------------------------------------------------------------------------

    public static final String URL_ROOT = "http://10.0.0.3:8080/";
    public static final String URL_LOGIN = "client/teacherLogin.json";
    public static final String URL_COURSE_DOWNLOAD = "http://video.angelmusic360.com";
    public static final String URL_CHECK_UPDATE = "/client/version/versionInfo.json?type=1";


    //----持久化相关-------------------------------------------------------------------------------------------

    public static final String KEY_LOGIN_PHONE = "KEY_LOGIN_PHONE";
    public static final String KEY_SCHOOL_ID = "KEY_SCHOOL_ID";


    /**
     * ----教师学生端命令相关-------------------------------------------------------------------------------------------
     */
    //--准备上课
    public static final int ACTION_COURSE_START = 101;
    //--播放视频
    public static final int ACTION_VEDIO_ON = 102;
    //--暂停视频
    public static final int ACTION_VEDIO_PAUSE = 103;
    //--画谱界面
    public static final int ACTION_COURSE_NOTE = 104;
    //--下课
    public static final int ACTION_COURSE_STOP = 105;





}
