package com.hzdl.teacher.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hzdl.teacher.core.ActionDispatcher;

public abstract class BaseActivity extends Activity {

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


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActionDispatcher.getInstance().remove(TAG);

    }

    //****处理通信命令
    protected abstract void handleMsg(Message msg);


}
