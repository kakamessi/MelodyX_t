package com.hzdl.teacher.activity;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzdl.teacher.R;
import com.hzdl.teacher.base.BaseMidiActivity;
import com.hzdl.teacher.base.Constant;
import com.hzdl.teacher.bean.ActionBean;
import com.hzdl.teacher.bean.lesson.LessonInfo;
import com.hzdl.teacher.core.ActionProtocol;
import com.hzdl.teacher.core.ActionResolver;
import com.hzdl.teacher.interfacex.OnItemClickLitener;
import com.hzdl.teacher.utils.BasePopupWindow;
import com.hzdl.teacher.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import jp.kshoji.driver.midi.device.MidiInputDevice;
import jp.kshoji.driver.midi.device.MidiOutputDevice;


/**
 * 上课主界面
 * 1--视频播放逻辑
 * <p>
 * <p>
 * 操作： 1，随机选择单元
 * 2，下一步单元
 * 3，投学生屏幕
 * <p>
 * <p>
 * //    loadPlay(Utils.getVideoPath()+"hehe.mp4");
 * //    MidiOutputDevice midiOutputDevice = getMidiOutputDevice();
 * //    midiOutputDevice.sendMidiNoteOn(0, 0x90, 0x40, 0x7f);
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
    @BindView(R.id.tv_menu)
    TextView tvMenu;


    public static int COURSE_TYPE = -1;
    public static final int TYPE_VEDIO = 1;
    public static final int TYPE_PLAY = 2;
    public static final int TYPE_MUSIC = 3;


    private LessonInfo les = null;
    private int cellIndex = 0;

    private MidiOutputDevice mOutputDevice;
    //当前消息
    private String actionMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        ButterKnife.bind(this);
        initVitamio();
        initMidi();
        mOutputDevice = getMidiOutputDevice();

        les = mBaseApp.getLi().get(mBaseApp.getIndexLessonOn());
        if (les == null) {
            this.finish();
            return;
        }

        setUIType(R.id.rl_loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                action();
            }
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vv.stopPlayback();
        closeView();

    }

    private void closeView(){
        if(popBtns!=null){
            popBtns.dismiss();
        }
        if(popupWindow!=null){
            popupWindow.dismiss();
        }

    }

    /**
     * 视频插件初始化
     */
    private void initVitamio() {
        Vitamio.initialize(this);
        MediaController mc = new MediaController(this);
        mc.setVisibility(View.INVISIBLE);
        vv.setMediaController(mc);
        vv.setOnPreparedListener(this);
        vv.setOnErrorListener(this);
        vv.setOnCompletionListener(this);

        //mVV.setVideoURI(Uri.parse("http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv"));
        //vv.setVideoURI(Uri.parse(Utils.getVideoPath() + "hehe.mp4"));
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
        vv.setVideoURI(Uri.parse(Utils.getVideoPath() + name));
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
    }

    //-----------------------------------------------------------视频相关-----------------------------------------------------------------


    //------------课程逻辑----------------------------------------------------------------------------------------------------------------

    /**
     * 开始上课
     */
    private void action() {

        if (cellIndex + 1 > les.getSectionsList().size()) {
            //课程结束
            setUIType(R.id.include_course_finish);
            return;
        }

        if (les.getSection(cellIndex).getType() == Constant.SECTION_TYPE_VIDEO) {
            //视频
            sendSynAction(ActionProtocol.ACTION_VEDIO_CHANGE + "|" + les.getSection(cellIndex).getSourceName());

        } else if (les.getSection(cellIndex).getType() == Constant.SECTION_TYPE_MUSIC) {
            //音乐

        } else if (les.getSection(cellIndex).getType() == Constant.SECTION_TYPE_XX) {
            //画谱

        }

        setCellIndex(++cellIndex);
    }

    /**
     * 消息入口
     *
     * @param action
     */
    @Override
    protected void handleMsg(Message action) {
        doAction((String) action.obj);
    }

    /**
     * 处理消息逻辑 如下课，切换视频等逻辑
     */
    private ActionBean ab;

    private void doAction(String str) {
        ab = ActionResolver.getInstance().resolve(str);
        if (ab.getCodeByPositon(1) == ActionProtocol.CODE_ACTION_COURSE) {
            if (ab.getCodeByPositon(2) == 0) {
                CourseActivity.this.finish();
            }
        } else if (ab.getCodeByPositon(1) == ActionProtocol.CODE_ACTION_VEDIO) {
            initVedioSection();
        } else if (ab.getCodeByPositon(1) == ActionProtocol.CODE_ACTION_SCORE) {

        }
    }

    /***
     * 播放视频
     */
    public void initVedioSection() {
        COURSE_TYPE = TYPE_VEDIO;
        setUIType(R.id.rl_video);
        if (ActionProtocol.CODE_VEDIO_ON == ab.getCodeByPositon(2) || ActionProtocol.CODE_VEDIO_OFF == ab.getCodeByPositon(2)) {
            playOrPause();
        } else if (ActionProtocol.CODE_VEDIO_CHANGE == ab.getCodeByPositon(2)) {
            swichPlayScr(ab.getStringByPositon(3));
        }
    }

    /**
     * 准备曲谱， 判断钢琴输入对错
     */
    public void initPlaySection() {
        COURSE_TYPE = TYPE_PLAY;

    }

    /**
     * 增加伴奏音乐
     */
    public void initMusicSection() {
        COURSE_TYPE = TYPE_MUSIC;

    }

    //------------课程逻辑----------------------------------------------------------------------------------------------------------------


    //note 21 -108
    @Override
    public void onMidiNoteOff(@NonNull MidiInputDevice sender, int cable, int channel, int note, int velocity) {
        super.onMidiNoteOff(sender, cable, channel, note, velocity);


    }

    @Override
    public void onMidiOutputDeviceAttached(@NonNull MidiOutputDevice midiOutputDevice) {
        super.onMidiOutputDeviceAttached(midiOutputDevice);
        mOutputDevice = getMidiOutputDevice();
    }

    private BasePopupWindow popupWindow;

    private BasePopupWindow popBtns;

    public void showButtonPop() {

        View vv = getLayoutInflater().inflate(R.layout.pop_button_menu, null);
        TextView tv1 = vv.findViewById(R.id.tv_select);
        TextView tv_next = vv.findViewById(R.id.tv_next);
        TextView tv3 = vv.findViewById(R.id.tv_over);
        TextView tv_pause = vv.findViewById(R.id.tv_pause);
        TextView tv_play = vv.findViewById(R.id.tv_play);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop();
                popBtns.dismiss();
            }
        });

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action();
                popBtns.dismiss();
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSynAction(ActionProtocol.ACTION_COURSE_STOP);
                popBtns.dismiss();
            }
        });

        tv_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (COURSE_TYPE == TYPE_VEDIO) {
                    playOrPause();
                }
                popBtns.dismiss();
            }
        });

        tv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (COURSE_TYPE == TYPE_VEDIO) {
                    playOrPause();
                }
                popBtns.dismiss();
            }
        });

        popBtns = new BasePopupWindow(this);
        popBtns.setContentView(vv);
        popBtns.showAsDropDown(tvMenu, 0, 0);


    }

    public void showPop() {

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wmManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wmManager.getDefaultDisplay().getMetrics(dm);
        int H = dm.heightPixels;
        int W = dm.widthPixels;

        View vv = getLayoutInflater().inflate(R.layout.pop_course, null);
        RecyclerView rcy_course = (RecyclerView) vv.findViewById(R.id.rcy_course);
        //设置布局管理器
        rcy_course.setLayoutManager(new GridLayoutManager(this, 5));
        CCAdapter cc = new CCAdapter();
        cc.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                setCellIndex(position);
                action();
                popupWindow.dismiss();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        rcy_course.setAdapter(cc);
        rcy_course.setItemAnimator(new DefaultItemAnimator());
        rcy_course.addItemDecoration(new SpaceItemDecoration(50));


        popupWindow = new BasePopupWindow(this);
        popupWindow.setWidth(W - 150);
        popupWindow.setHeight(H - 150);
        popupWindow.setContentView(vv);
        popupWindow.showAtLocation(fl_root, Gravity.TOP, 0, 75);

    }

    @OnClick({R.id.fl_one, R.id.tv_menu,R.id.iv_backmain})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_one:

                break;
            case R.id.tv_menu:
                showButtonPop();
                break;

            case R.id.iv_backmain:
                sendSynAction(ActionProtocol.ACTION_COURSE_STOP);
                break;
        }
    }


    class CCAdapter extends RecyclerView.Adapter<CCAdapter.MyViewHolder> {
        private OnItemClickLitener mOnItemClickLitener;

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(CourseActivity.this).inflate(R.layout.item_pop_list, parent, false);
            final MyViewHolder holder = new MyViewHolder(v);
            if (mOnItemClickLitener != null) {
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(holder.itemView, pos);
                    }
                });
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv_name.setText(les.getSection(position).getGroupName() + "  " + les.getSection(position).getShowName());
        }

        @Override
        public int getItemCount() {
            return les.getSectionsList().size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView tv;
            TextView tv_name;

            public MyViewHolder(View view) {
                super(view);
                tv = (ImageView) view.findViewById(R.id.imageView);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
            }
        }
    }

    class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        int mSpace;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.bottom = mSpace;
            outRect.top = 10;
        }

        public SpaceItemDecoration(int space) {
            this.mSpace = space;
        }
    }

    public void setCellIndex(int index) {
        this.cellIndex = index;
    }
}






