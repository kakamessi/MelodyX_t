package com.hzdl.teacher.base;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;

import com.hzdl.mex.socket.teacher.TeacherClient;
import com.hzdl.teacher.R;
import com.hzdl.teacher.bean.lesson.SimpleSection;
import com.hzdl.teacher.core.ActionDispatcher;
import com.hzdl.teacher.view.UniversalLoadingView;

public abstract class BaseActivity extends FragmentActivity {

    protected String TAG = this.getClass().getName();
    protected View loadingView;
    protected App mBaseApp = null;

    private Handler actionHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handleMsg(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideBottomUIMenu();

        mBaseApp = App.getApplication();
        ActionDispatcher.getInstance().register(TAG, actionHandler);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActionDispatcher.getInstance().remove(TAG);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    //****处理通信命令
    protected abstract void handleMsg(Message msg);

    protected void sendSynAction(String action, SimpleSection ss){
        if(ss.getSyncScreen()==1){
            TeacherClient.getInstance().sendMsgToAll(action);
            ActionDispatcher.getInstance().dispatch(action);
        }else {
            ActionDispatcher.getInstance().dispatch(action);
        }
    }

    protected void sendSynAction(String action){
        TeacherClient.getInstance().sendMsgToAll(action);
        ActionDispatcher.getInstance().dispatch(action);
    }

    protected void sendTeacherAction(String action){
        ActionDispatcher.getInstance().dispatch(action);
    }


    private UniversalLoadingView uv;
    /**
     * 加载过程中显示旋转圈
     */
    public void showLoadingDialog() {
        if(loadingView==null) {
            loadingView = findViewById(R.id.view_loading);
        }
        loadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 停止旋转圈
     */
    public void hideLoadingDialog() {
        if(loadingView!=null)
            loadingView.setVisibility(View.GONE);
    }


    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


}
