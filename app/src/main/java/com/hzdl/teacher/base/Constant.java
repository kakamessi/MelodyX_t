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

    public static int SECTION_TYPE_VIDEO = 102;
    public static int SECTION_TYPE_MUSIC = 102;
    public static int SECTION_TYPE_NOTEPLAY = 103;


    //----课程下载相关-------------------------------------------------------------------------------------------
    public static final String DOWNLOAD_ROOT = "http://newapp.tianshiyinyue.cn";
    public static final String DOWNLOAD_PATH = "/client/course/findCourseOnlyPath.json";


    public static final String DOWNLOAD_APK = "/client/course/findCourseOnlyPath.json";


    //----网络相关-------------------------------------------------------------------------------------------

    //发布环境
    public static final String URL_ROOT = "http://47.104.96.204:8080/";
    //测试环境
    //public static final String URL_ROOT = "http://app.w3cstudy.cc";

    public static final String URL_LOGIN = "client/teacherLogin.json";
    public static final String URL_MODIFY_PSW = "/client/resetPass.json";
    public static final String URL_COURSE_DOWNLOAD = "http://video.w3cstudy.cc";
    //public static final String URL_COURSE_DOWNLOAD = "http://video.angelmusic360.com";
    public static final String URL_CHECK_UPDATE = "/client/version/versionInfo.json?type=1";
    public static final String URL_COURSE_LIST = "client/course/findCoursePart.json";

    //----持久化相关-------------------------------------------------------------------------------------------

    public static final String KEY_REMENBER_TAG = "KEY_REMENBER_TAG";
    public static final String KEY_LOGIN_PHONE = "KEY_LOGIN_PHONE";
    public static final String KEY_LOGIN_PSD = "KEY_LOGIN_PSD";
    public static final String KEY_SCHOOL_ID = "KEY_SCHOOL_ID";



}
