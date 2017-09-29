package com.hzdl.teacher.downloadcourse.okhttp.bean;


import com.hzdl.teacher.downloadcourse.okhttp.HttpInfo;
import com.hzdl.teacher.downloadcourse.okhttp.callback.CallbackOk;

/**
 * 响应回调信息体
 */
public class CallbackMessage extends OkMessage{

    public CallbackOk callback;
    public HttpInfo info;

    public CallbackMessage(int what, CallbackOk callback, HttpInfo info) {
        this.what = what;
        this.callback = callback;
        this.info = info;
    }


}