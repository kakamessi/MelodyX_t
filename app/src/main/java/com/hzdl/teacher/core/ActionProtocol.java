package com.hzdl.teacher.core;

/**
 * Created by wangshuai on 2017/10/9.
 */

public class ActionProtocol {

    //- 功能性协议 --------------------------------------------------------------------------------------
    //学生端在线人数
    public static final String ACTION_STATU_ALIVENUM = "0|1|1";


    //-公开课环节--------------------------------------------------------------------------------------

    //--准备上课
    public static final String ACTION_COURSE_START = "1|1|1";
    //--下课
    public static final String ACTION_COURSE_STOP = "1|1|0";

    //--播放视频
    public static final String ACTION_VEDIO_ON = "1|2|1";
    //--暂停视频
    public static final String ACTION_VEDIO_PAUSE = "1|2|0";

    // 1| 2视频类型| 3切换视频 |④ "haha".mp4 视频名称 |⑤ 是否亮灯 |⑥ 是否投屏 |⑦ 第几节课 |⑧  是否进入答题h5
    //--切换视频
    public static final String ACTION_VEDIO_CHANGE = "1|2|3";

    //--画谱界面
    public static final String ACTION_COURSE_NOTE = "1|3";

    //--图片界面 1|4|.png
    public static final String ACTION_COURSE_IMG = "1|4";

    //-测试题环节--------------------------------------------------------------------------------------

    //开始测试
    public static final String ACTION_TEST_ON = "2|1|-10|-10";
    //上传成绩  "2|2|小明=C"
    public static final String ACTION_TEST_QUESTION = "2|2";
    //下一题  "2|3|题号"
    public static final String ACTION_TEST_NUM = "2|3";
    //结束测试
    public static final String ACTION_TEST_OFF = "2|0|-10|-10";






    //--准备上课
    public static final int CODE_ACTION_COURSE = 1;
    //--播放视频
    public static final int CODE_ACTION_VEDIO = 2;
    public static final int CODE_VEDIO_ON = 1;
    public static final int CODE_VEDIO_OFF = 0;
    public static final int CODE_VEDIO_CHANGE = 3;
    // 0 - 1
    public static final int CODE_1 = 1;
    public static final int CODE_0 = 0;

    //--画谱界面
    public static final int CODE_ACTION_SCORE = 3;
    //--图片界面
    public static final int CODE_ACTION_IMG = 4;

    //--学生连接通知
    public static final int CODE_ACTION_CONNECTED = 401;
    public static final int CODE_ACTION_UDP_ON = 402;
    public static final int CODE_ACTION_UDP_OFF = 403;
    public static final int CODE_ACTION_UPDATE = 404;

    public static String getActionCode(int code){
        StringBuffer sb = new StringBuffer();
        sb.append(code+"");
        for(int i=0; i<5; i++){
            sb.append("|0");
        }
        return sb.toString();
    }

}
