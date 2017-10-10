package com.hzdl.teacher.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import com.hzdl.mex.socket.teacher.TeacherClient;
import com.hzdl.teacher.core.ActionDispatcher;

public abstract class BaseActivity extends FragmentActivity {

    protected String TAG = this.getClass().getName();

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

    //****处理通信命令
    protected abstract void handleMsg(Message msg);


    protected void sendSynAction(String action){
        TeacherClient.getInstance().sendMsgToAll(action);
        ActionDispatcher.getInstance().dispatch(action);
    }





    /**
     * 加载过程中显示旋转圈
     */
    public void showLoadingDialog() {

    }

    /**
     * 停止旋转圈
     */
    public void hideLoadingDialog() {

    }



}
