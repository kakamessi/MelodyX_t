package com.hzdl.teacher.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Parcel;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.hzdl.teacher.R;
import com.hzdl.teacher.base.Constant;
import com.hzdl.teacher.bean.lesson.CrouseListBean1031;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by DELL on 2017/1/16.
 */

public class Utils {

    public static final String PACK_NAME = "com.hzdl.teacher";

    /**
     * 获取本机ip
     * @param con
     * @return
     */
    public static String getLocalIp(Context con){
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) con.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();

        return intToIp(ipAddress);
    }

    private static String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    public static String getDeviceId(Context context){

        String id;
        //android.telephony.TelephonyManager
        TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephony.getDeviceId() != null) {
            id = mTelephony.getDeviceId();
        } else {
            //android.provider.Settings;
            id = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return id;

    }

    /**
     * 递归删除文件和文件夹
     * @param file    要删除的根目录
     */
    public static void RecursionDeleteFile(File file){
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }

    public static String getVideoPath(){

        return Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.FILE_PATH;
    }

    public static boolean isFileExist(String filename){
        boolean result = false;
        File file = new File(getVideoPath() + filename);
        if(file.exists()){
            result = true;
        }
        return result;
    }

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    public static boolean isTeacherClient(Context context){
        boolean result = false;
        if(getPackageInfo(context).packageName.equals(PACK_NAME)){
            result = true;
        }
        return result;
    }

    /**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void setOnFocusBG(View v, final int onResId, final int offResId){

        final Drawable dd = v.getBackground();
        v.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 获取焦点时操作，常见的有放大、加边框等
                    v.setBackgroundResource(onResId);
                } else {
                    v.setBackground(dd);
                    // 失去焦点时操作，恢复默认状态
                }
            }
        });

    }

    public static void showScreen(Activity context){

        Configuration config = context.getResources().getConfiguration();
        int smallestScreenWidth = config.smallestScreenWidthDp;
        int screenWidth = config.screenWidthDp;

        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
// 屏幕宽度（像素）
        int width = metric.widthPixels;
// 屏幕高度（像素）
        int height = metric.heightPixels;
// 屏幕密度（1.0 / 1.5 / 2.0）
        float density = metric.density;
// 屏幕密度DPI（160 / 240 / 320）
        int densityDpi = metric.densityDpi;
        String info = "机顶盒型号: " + android.os.Build.MODEL
                + ",\nSDK版本:" + android.os.Build.VERSION.SDK
                + ",\n系统版本:" + android.os.Build.VERSION.RELEASE
                + "\n屏幕宽度（像素）: "+width
                + "\n屏幕高度（像素）: " + height
                + "\n屏幕密度:  " +density
                +"\n屏幕密度DPI: "+densityDpi
                + "\n屏幕最小宽度）: " + smallestScreenWidth
        + "\n屏宽度）: " + screenWidth;

        Toast.makeText(context,info,1).show();

    }

    public static String getAppProcessName(Context context) {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return "";
    }

    public static void saveParce(Context con,CrouseListBean1031 bean) {
        FileOutputStream fos;
        try {

            fos = new FileOutputStream(Utils.getVideoPath() + "demox1126");
            //fos = con.getApplicationContext().openFileOutput("", Context.MODE_WORLD_WRITEABLE);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            Parcel parcel = Parcel.obtain();
            parcel.writeParcelable(bean, 0);

            bos.write(parcel.marshall());
            bos.flush();
            bos.close();
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CrouseListBean1031 loadDemoParce(Context con) {

        CrouseListBean1031 data = null;
        InputStream fis;
        try {
            fis = con.getResources().openRawResource(R.raw.demox1126);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            Parcel parcel = Parcel.obtain();
            parcel.unmarshall(bytes, 0, bytes.length);
            parcel.setDataPosition(0);

            data = parcel.readParcelable(CrouseListBean1031.class.getClassLoader());
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

}
