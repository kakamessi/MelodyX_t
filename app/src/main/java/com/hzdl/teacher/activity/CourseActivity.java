package com.hzdl.teacher.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hzdl.teacher.R;
import com.hzdl.teacher.base.BaseMidiActivity;

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
 *
 *
 */
public class CourseActivity extends BaseMidiActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{


//    loadPlay(Utils.getVideoPath()+"hehe.mp4");
//    MidiOutputDevice midiOutputDevice = getMidiOutputDevice();
//    midiOutputDevice.sendMidiNoteOn(0, 0x90, 0x40, 0x7f);





    private FrameLayout fl_one;
    private RelativeLayout rl_video,rl_piano;
    private VideoView mVV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        initView();
        initVV();
        initMidi();
        setUIType(R.id.rl_piano);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVV.stopPlayback();
    }

    @Override
    protected void handleMsg(Message msg) {

    }

    private void setUIType(int id){
        for(int i=0; i<fl_one.getChildCount(); i++){
            if(fl_one.getChildAt(i).getId()==id){
                fl_one.getChildAt(i).setVisibility(View.VISIBLE);
            }else{
                fl_one.getChildAt(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    private void initView() {

        mVV = (VideoView)findViewById(R.id.vv);

        rl_video = (RelativeLayout) findViewById(R.id.rl_video);
        rl_piano = (RelativeLayout) findViewById(R.id.rl_piano);
        fl_one = (FrameLayout) findViewById(R.id.fl_one);

    }

    /**
     * 视频插件初始化
     */
    private void initVV() {
        Vitamio.initialize(this);
        MediaController mc = new MediaController(this);
        mc.setVisibility(View.INVISIBLE);
        mVV.setMediaController(mc);
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
