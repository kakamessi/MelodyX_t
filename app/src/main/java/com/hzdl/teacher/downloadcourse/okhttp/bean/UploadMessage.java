package com.hzdl.teacher.downloadcourse.okhttp.bean;

import com.hzdl.teacher.downloadcourse.okhttp.HttpInfo;
import com.hzdl.teacher.downloadcourse.okhttp.callback.ProgressCallback;

/**
 * 上传响应回调信息体
 */
public class UploadMessage extends OkMessage {

    public String filePath;
    public HttpInfo info;
    public ProgressCallback progressCallback;

    public UploadMessage(int what, String filePath, HttpInfo info, ProgressCallback progressCallback) {
        this.what = what;
        this.filePath = filePath;
        this.info = info;
        this.progressCallback = progressCallback;
    }
}