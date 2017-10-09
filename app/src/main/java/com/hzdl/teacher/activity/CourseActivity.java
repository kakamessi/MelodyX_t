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
import com.hzdl.teacher.base.Constant;
import com.hzdl.teacher.bean.ActionBean;
import com.hzdl.teacher.core.ActionResolver;
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
 *
 *
 */
public class CourseActivity extends BaseMidiActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{


//    loadPlay(Utils.getVideoPath()+"hehe.mp4");
//    MidiOutputDevice midiOutputDevice = getMidiOutputDevice();
//    midiOutputDevice.sendMidiNoteOn(0, 0x90, 0x40, 0x7f);


    //当前消息
    private String actionMsg;
    private ActionBean ab;

    private FrameLayout fl_root;
    private RelativeLayout rl_video,rl_piano;
    private VideoView mVV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        initView();
        initVV();
        initMidi();
        setUIType(R.id.rl_video);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVV.stopPlayback();
    }

    @Override
    protected void handleMsg(Message action) {

    }

    /**
     *
     * 处理消息逻辑 如下课，切换视频等逻辑
     */
    private void doAction(String str) {
        ab = ActionResolver.getInstance().resolve(str);
        int one = Integer.parseInt(ab.getCodes()[0]);

        if(one== Constant.ACTION_VEDIO_ON){

        }else if(one== Constant.ACTION_VEDIO_PAUSE){

        }else if(one== Constant.ACTION_COURSE_NOTE){

        }else if(one== Constant.ACTION_COURSE_STOP){

        }
    }

    private void initView() {

        mVV = (VideoView)findViewById(R.id.vv);

        rl_video = (RelativeLayout) findViewById(R.id.rl_video);
        rl_piano = (RelativeLayout) findViewById(R.id.rl_piano);
        fl_root = (FrameLayout) findViewById(R.id.fl_one);

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

        //mVV.setVideoURI(Uri.parse("http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv"));
        mVV.setVideoURI(Uri.parse(Utils.getVideoPath()+"hehe.mp4"));
    }

    /**
     * 切换视图类型
     *
     * 1，视频类型
     * 2，画谱弹奏类型
     * 3，
     *
     *
     * @param id
     */
    private void setUIType(int resID){
        for(int i = 0; i< fl_root.getChildCount(); i++){
            if(fl_root.getChildAt(i).getId()==resID){
                fl_root.getChildAt(i).setVisibility(View.VISIBLE);
            }else{
                fl_root.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    private void setFullScreen()
    {
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        mVV.setLayoutParams(layoutParams1);
    }


    //-----------------------------------------------------------视频相关-----------------------------------------------------------------
    /**
     * 切换资源
     */
    private void swichPlayScr(String name){
        mVV.setVideoURI(Uri.parse(Utils.getVideoPath()+"hehe.mp4"));
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
    }
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(this,"error", Toast.LENGTH_LONG).show();
        return false;
    }
    @Override
    public void onPrepared(MediaPlayer mp) {
        playOrPause();
    }
    //-----------------------------------------------------------视频相关-----------------------------------------------------------------




}
