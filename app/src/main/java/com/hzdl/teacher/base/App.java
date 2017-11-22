package com.hzdl.teacher.base;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Environment;

import com.hzdl.mex.utils.MyCrashHandler;
import com.hzdl.teacher.bean.lesson.LessonInfo;
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
import java.util.ArrayList;
import java.util.List;

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

    //公共数据区
    private List<LessonInfo> li = null;
    private int IndexLessonOn = -1;
    private boolean root;

    //0是电视， 1是手机
    private int deviceType = -1;

    private List<Activity> activityStack = new ArrayList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        initService();
        initParam();
        initOkHttp();
        initRetrofit();
        initCrash();
        initDevice();
    }

    /***
     * 确定设备源
     */
    private void initDevice() {
        String bul = android.os.Build.MODEL;
        if(bul.contains("TV") || bul.contains("tv")){
            deviceType = 1;
        }else{
            deviceType = 0;
        }

    }

    public boolean isTV(){
        return deviceType == 1;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    private void initRetrofit() {



    }

    public void addActivity(Activity aty) {
        activityStack.add(aty);
    }

    public void removeActivity(Activity aty) {
        activityStack.remove(aty);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
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

    public List<LessonInfo> getLi() {
        return li;
    }

    public void setLi(List<LessonInfo> li) {
        this.li = li;
    }

    public int getIndexLessonOn() {
        return IndexLessonOn;
    }

    public void setIndexLessonOn(int indexLessonOn) {
        IndexLessonOn = indexLessonOn;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }
}
