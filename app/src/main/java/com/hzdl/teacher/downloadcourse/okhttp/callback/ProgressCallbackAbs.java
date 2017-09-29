package com.hzdl.teacher.downloadcourse.okhttp.callback;


import com.hzdl.teacher.downloadcourse.okhttp.HttpInfo;

/**
 * 进度回调抽象类
 */
public abstract class ProgressCallbackAbs {

    public abstract void onResponseMain(String filePath, HttpInfo info);

    public abstract void onResponseSync(String filePath, HttpInfo info);

    public abstract void onProgressAsync(int percent, long bytesWritten, long contentLength, boolean done);

    public abstract void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done);

}
