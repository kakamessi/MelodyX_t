package com.hzdl.teacher.downloadcourse.okhttp.progress;


import android.os.Message;
import android.util.Log;


import com.hzdl.teacher.downloadcourse.okhttp.bean.DownloadFileInfo;
import com.hzdl.teacher.downloadcourse.okhttp.bean.ProgressMessage;
import com.hzdl.teacher.downloadcourse.okhttp.callback.ProgressCallback;
import com.hzdl.teacher.downloadcourse.okhttp.handler.OkMainHandler;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 进度响应体
 */
public class ProgressResponseBody extends ResponseBody{

    private final ResponseBody originalResponseBody;
    private BufferedSource bufferedSink;
    private DownloadFileInfo downloadFileInfo;

    public ProgressResponseBody(ResponseBody originalResponseBody, DownloadFileInfo downloadFileInfo) {
        this.originalResponseBody = originalResponseBody;
        this.downloadFileInfo = downloadFileInfo;
    }

    @Override
    public long contentLength() {
        return originalResponseBody.contentLength();
    }

    @Override
    public MediaType contentType() {
        return originalResponseBody.contentType();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSink == null) {
            bufferedSink = Okio.buffer(source(originalResponseBody.source()));
        }
        return bufferedSink;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;
            long contentLength = 0L;
            ProgressCallback progressCallback;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                if(totalBytesRead == 0){
                    totalBytesRead = downloadFileInfo.getCompletedSize();
                    Log.d("OkHttpUtil","从节点["+totalBytesRead+"]开始下载"
                            +downloadFileInfo.getSaveFileNameWithExtension());
                }
                if (contentLength == 0) {
                    //文件总长度=当前需要下载长度+已完成长度
                    contentLength = contentLength() + totalBytesRead;
                }
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if(null == progressCallback)
                    progressCallback = downloadFileInfo.getProgressCallback();
                if(null != progressCallback){
                    int percent = (int) ((100 * totalBytesRead) / contentLength);
                    progressCallback.onProgressAsync(percent, totalBytesRead,contentLength,totalBytesRead == -1);
                    //主线程回调
                    Message msg = new ProgressMessage(OkMainHandler.PROGRESS_CALLBACK,
                            progressCallback,
                            percent,
                            totalBytesRead,
                            contentLength,
                            bytesRead == -1)
                            .build();
                    OkMainHandler.getInstance().sendMessage(msg);
                }
                return bytesRead;
            }
        };
    }


}
