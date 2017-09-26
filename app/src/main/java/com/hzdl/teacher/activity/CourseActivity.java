package com.hzdl.teacher.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hzdl.teacher.R;
import com.hzdl.teacher.base.BaseActivity;
import com.hzdl.teacher.utils.Utils;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;


/**
 *
 *   上课主界面
 *   1--视频播放逻辑
 *
 *
 *
 *
 */
public class CourseActivity extends BaseActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{

    private VideoView mVV;

    TextView tv_play;
    TextView tv_stop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        initView();
        initVV();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVV.stopPlayback();
    }

    @Override
    protected void handleMsg(Message msg) {

    }

    private void initView() {
        mVV = (VideoView)findViewById(R.id.vv);

        tv_play = (TextView) findViewById(R.id.tv_play);
        tv_stop = (TextView) findViewById(R.id.tv_stop);

        tv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadPlay(Utils.getVideoPath()+"hehe.mp4");
            }
        });
        tv_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playOrPause();
            }
        });
    }

    /**
     * 视频插件初始化
     */
    private void initVV() {
        Vitamio.initialize(this);
        mVV.setMediaController(new MediaController(this));

        mVV.setOnPreparedListener(this);
        mVV.setOnErrorListener(this);
        mVV.setOnCompletionListener(this);
    }

    /**
     * 装载播放
     */
    private void loadPlay(String path){
        mVV.setVideoURI(Uri.parse(path));
        mVV.start();
    }

    /**
     * 播放/暂停
     */
    private void playOrPause() {
        if (mVV != null)
            if (mVV.isPlaying()) {
                mVV.pause();
            } else {
                mVV.start();
            }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Toast.makeText(this,"准备好了", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }


}
