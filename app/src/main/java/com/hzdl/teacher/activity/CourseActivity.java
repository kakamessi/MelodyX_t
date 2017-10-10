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
import com.hzdl.teacher.bean.ActionBean;
import com.hzdl.teacher.core.ActionProtocol;
import com.hzdl.teacher.core.ActionResolver;
import com.hzdl.teacher.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;


/**
 * 上课主界面
 * 1--视频播放逻辑
 */
public class CourseActivity extends BaseMidiActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    @BindView(R.id.vv)
    VideoView vv;
    @BindView(R.id.rl_video)
    RelativeLayout rlVideo;
    @BindView(R.id.rl_loading)
    RelativeLayout rlLoading;
    @BindView(R.id.fl_one)
    FrameLayout fl_root;


//    loadPlay(Utils.getVideoPath()+"hehe.mp4");
//    MidiOutputDevice midiOutputDevice = getMidiOutputDevice();
//    midiOutputDevice.sendMidiNoteOn(0, 0x90, 0x40, 0x7f);


    //当前消息
    private String actionMsg;
    private ActionBean ab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        ButterKnife.bind(this);
        initVV();
        initMidi();
        setUIType(R.id.rl_loading);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vv.stopPlayback();
    }

    @Override
    protected void handleMsg(Message action) {

    }

    /**
     * 处理消息逻辑 如下课，切换视频等逻辑
     */
    private void doAction(String str) {
        ab = ActionResolver.getInstance().resolve(str);
        int one = Integer.parseInt(ab.getCodes()[0]);

        if (one == ActionProtocol.CODE_ACTION_COURSE) {

        } else if (one == ActionProtocol.CODE_ACTION_VEDIO) {

        } else if (one == ActionProtocol.CODE_ACTION_NOTE) {

        }
    }


    /**
     * 视频插件初始化
     */
    private void initVV() {
        Vitamio.initialize(this);
        MediaController mc = new MediaController(this);
        mc.setVisibility(View.INVISIBLE);
        vv.setMediaController(mc);
        vv.setOnPreparedListener(this);
        vv.setOnErrorListener(this);
        vv.setOnCompletionListener(this);

        //mVV.setVideoURI(Uri.parse("http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv"));
        vv.setVideoURI(Uri.parse(Utils.getVideoPath() + "hehe.mp4"));
    }

    /**
     * 切换视图类型
     * <p>
     * 1，视频类型
     * 2，画谱弹奏类型
     * 3，
     *
     * @param id
     */
    private void setUIType(int resID) {
        for (int i = 0; i < fl_root.getChildCount(); i++) {
            if (fl_root.getChildAt(i).getId() == resID) {
                fl_root.getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                fl_root.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    private void setFullScreen() {
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        vv.setLayoutParams(layoutParams1);
    }


    //-----------------------------------------------------------视频相关-----------------------------------------------------------------

    /**
     * 切换资源
     */
    private void swichPlayScr(String name) {
        vv.setVideoURI(Uri.parse(Utils.getVideoPath() + "hehe.mp4"));
        vv.start();
    }

    /**
     * 播放/暂停
     */
    private void playOrPause() {
        if (vv != null)
            if (vv.isPlaying()) {
                vv.pause();
            } else {
                vv.start();
            }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        playOrPause();
    }

    @OnClick(R.id.fl_one)
    public void onClick() {
    }
    //-----------------------------------------------------------视频相关-----------------------------------------------------------------


}
