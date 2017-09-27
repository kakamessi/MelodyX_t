package com.hzdl.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import com.hzdl.teacher.R;
import com.hzdl.teacher.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGHT = 3000;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler();
        // 延迟SPLASH_DISPLAY_LENGHT时间然后跳转到MainActivity
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();

            }
        }, SPLASH_DISPLAY_LENGHT);

    }

    @Override
    protected void handleMsg(Message msg) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return  true;
            }
        return  super.onKeyDown(keyCode, event);
    }

}
