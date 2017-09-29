package com.hzdl.teacher.base;

import android.app.Application;
import android.content.Intent;
import android.os.Environment;

import com.hzdl.mex.utils.MyCrashHandler;
import com.hzdl.teacher.downloadcourse.okhttp.HttpInterceptor;
import com.hzdl.teacher.downloadcourse.okhttp.OkHttpUtil;
import com.hzdl.teacher.downloadcourse.okhttp.annotation.CacheLevel;
import com.hzdl.teacher.downloadcourse.okhttp.annotation.CacheType;
import com.hzdl.teacher.downloadcourse.okhttp.cookie.PersistentCookieJar;
import com.hzdl.teacher.downloadcourse.okhttp.cookie.cache.SetCookieCache;
import com.hzdl.teacher.downloadcourse.okhttp.cookie.persistence.SharedPrefsCookiePersistor;
import com.hzdl.teacher.service.Mservice;
import com.hzdl.teacher.utils.Utils;

import java.io.File;

/**
 * Created by wangshuai on 2017/9/15.
 */

public class App extends Application {

    //http请求类
    public static OkHttpUtil.Builder init;

    //单例
    private static App myApplication = null;
    public static App getApplication(){
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        initService();
        initParam();
        initOkHttp();
        //initCrash();
    }

    //初始化网络框架
    private void initOkHttp() {
        init = OkHttpUtil.init(this);
        init.setConnectTimeout(30)//连接超时时间
                .setWriteTimeout(30)//写超时时间
                .setReadTimeout(30)//读超时时间
                .setMaxCacheSize(10 * 1024 * 1024)//缓存空间大小
                .setCacheLevel(CacheLevel.FIRST_LEVEL)//缓存等级
                .setCacheType(CacheType.NETWORK_THEN_CACHE)//缓存类型
                .setShowHttpLog(true)//显示请求日志
                .setShowLifecycleLog(true)//显示Activity销毁日志
                .setRetryOnConnectionFailure(false)//失败后不自动重连
                .setDownloadFileDir(Utils.getVideoPath())//文件下载保存目录(根据实际需求设置App.init.set...)
                .addResultInterceptor(HttpInterceptor.ResultInterceptor)//请求结果拦截器
                .addExceptionInterceptor(HttpInterceptor.ExceptionInterceptor)//请求链路异常拦截器
                .setCookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this)))//持久化cookie
                .build();
    }

    private void initParam() {
        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.FILE_PATH;
        File filePath = new File(sdDir);
        if (!filePath.exists()) {
            filePath.mkdir();
        }
    }

    private void initService() {
        Intent startIntent = new Intent(this, Mservice.class);
        startService(startIntent);
    }

    private void initCrash() {
        // 初始化捕捉异常的类
        MyCrashHandler handler = MyCrashHandler.getInstance();
        handler.init(getApplicationContext());
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }


}
