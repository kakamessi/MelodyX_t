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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzdl.mex.utils.Log;
import com.hzdl.teacher.R;
import com.hzdl.teacher.base.Constant;
import com.hzdl.teacher.bean.lesson.LessonInfo;
import com.hzdl.teacher.core.ActionBean;
import com.hzdl.teacher.core.ActionProtocol;
import com.hzdl.teacher.core.ActionResolver;
import com.hzdl.teacher.core.MelodyU;
import com.hzdl.teacher.core.NoteInfo;
import com.hzdl.teacher.interfacex.OnItemClickLitener;
import com.hzdl.teacher.utils.BasePopupWindow;
import com.hzdl.teacher.utils.ButtonUtils;
import com.hzdl.teacher.utils.Utils;
import com.hzdl.teacher.view.JZVideo;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.vov.vitamio.MediaPlayer;
import jp.kshoji.driver.midi.device.MidiInputDevice;
import jp.kshoji.driver.midi.device.MidiOutputDevice;

import static com.hzdl.teacher.core.MelodyU.d_color_1;
import static com.hzdl.teacher.core.MelodyU.d_color_2;
import static com.hzdl.teacher.core.MelodyU.d_color_3;
import static com.hzdl.teacher.core.MelodyU.d_color_4;
import static com.hzdl.teacher.core.MelodyU.d_duringtime_1;
import static com.hzdl.teacher.core.MelodyU.d_duringtime_2;
import static com.hzdl.teacher.core.MelodyU.d_duringtime_3;
import static com.hzdl.teacher.core.MelodyU.d_duringtime_4;
import static com.hzdl.teacher.core.MelodyU.d_note_1;
import static com.hzdl.teacher.core.MelodyU.d_note_2;
import static com.hzdl.teacher.core.MelodyU.d_note_3;
import static com.hzdl.teacher.core.MelodyU.d_note_4;
import static com.hzdl.teacher.core.MelodyU.d_starttime_1;
import static com.hzdl.teacher.core.MelodyU.d_starttime_2;
import static com.hzdl.teacher.core.MelodyU.d_starttime_3;
import static com.hzdl.teacher.core.MelodyU.d_starttime_4;


/**
 * 1， 当前处于哪个小节   cellIndex
 * 只有点击下一步， 选择课程 会改变cellIndex
 * <p>
 * 2， 发送同步消息   发送本地消息
 * <p>
 * <p>
 * 3， 接收端：  界面操作，播放视频，与钢琴通信
 * 发送端：  服务器数据
 * <p>
 * //    loadPlay(Utils.getVideoPath()+"hehe.mp4");
 * //    MidiOutputDevice midiOutputDevice = getMidiOutputDevice();
 * //    midiOutputDevice.sendMidiNoteOn(0, 0x90, 0x40, 0x7f);
 */
public class CourseActivity extends BaseH5Activity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    @BindView(R.id.jzvideo)
    JZVideo mJzvideo;
    @BindView(R.id.rl_video)
    RelativeLayout rlVideo;
    @BindView(R.id.rl_loading)
    RelativeLayout rlLoading;
    @BindView(R.id.fl_one)
    FrameLayout fl_root;
    @BindView(R.id.tv_menu)
    ImageView tvMenu;
    @BindView(R.id.rl_score)
    RelativeLayout rlScore;
    @BindView(R.id.iv_backmain)
    ImageView ivBackmain;
    @BindView(R.id.rl_teacher_screen)
    ImageView rlTeacherScreen;
    @BindView(R.id.webView)
    WebView webView;

    public static int COURSE_TYPE = -1;
    public static final int TYPE_VEDIO = 1;
    public static final int TYPE_PLAY = 2;
    public static final int TYPE_IMG = 3;
    public static final int TYPE_ANSWER = 5;

    public static final int MSG_TYPE_SEDNVIEDO = 100;


    private MidiOutputDevice mOutputDevice;
    //当前消息
    private String actionMsg;
    private boolean allow_btn = false;


    //课程数据
    private LessonInfo les = null;
    //当前小节
    private int cellIndex = -1;

    private Handler courseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_TYPE_SEDNVIEDO:
                    sendVideoActionInner();
                    break;

            }
        }
    };

    private Executor mExecutor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取数据
        les = mBaseApp.getLi().get(mBaseApp.getIndexLessonOn());
        if (les == null) {
            this.finish();
            return;
        }

        setContentView(R.layout.activity_course);
        ButterKnife.bind(this);
        initView();
        initVitamio();
        initMidi();
        initH5();

        mOutputDevice = getMidiOutputDevice();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                action();
                allow_btn = true;

                if (mBaseApp.getDeviceType() == 0) {
                    tvMenu.setVisibility(View.VISIBLE);
                }
            }
        }, 3000);
        setUIType(R.id.rl_loading);

    }

    private void initView() {
        Utils.setOnFocusBG(tvMenu, R.drawable.shape_strock, -1);
        Utils.setOnFocusBG(ivBackmain, R.drawable.shape_strock, -1);
    }

    int testInt = 21;

    private void testX() {
        //initPlaySection();
        //MelodyU.getInstance().setNoteAndKey(this, includeScore, 1, false, 1, false);

        //mOutputDevice.sendMidiSystemExclusive(0,MelodyU.getlightCode(testInt,true,true));
        //testInt++;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JZVideo.releaseAllVideos();
        closeView();
        stopTempleLight();

        new Thread(new Runnable() {
            @Override
            public void run() {
                //Utils.clearSonFile(new File(Utils.getVideoPath()));
            }
        }).start();
    }

    private void closeView() {
        if (popBtns != null) {
            popBtns.dismiss();
        }
        if (popupWindow != null) {
            popupWindow.dismiss();
        }

    }

    /**
     * 视频插件初始化
     */
    private void initVitamio() {

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

    }


    //-----------------------------------------------------------视频相关-----------------------------------------------------------------

    /**
     * 切换资源
     */
    private void swichPlayScr(String name) {
        mJzvideo.setUp(Utils.getVideoPath() + name,name, Jzvd.SCREEN_WINDOW_NORMAL);
        mJzvideo.startPlaying();
    }

    /**
     * 播放/暂停
     */
    private void playOrPause() {
        if (mJzvideo != null){
         mJzvideo.pause();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        //播放完成 跳入答题H5
        //        if(les.getSection(cellIndex).getType() == Constant.SECTION_TYPE_VIDEO_H5){
        //            setUIType(R.id.webView);
        //            webView.setFocusable(true);
        //            webView.requestFocus();
        //            loadH5(les.getSection(cellIndex).getSourceName() + "  id = "+les.getSection(cellIndex).getId());
        //        }

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
    }

    /**
     * 遥控器控制
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (!allow_btn) {
            return false;
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            if (COURSE_TYPE == TYPE_VEDIO) {
                les.getSection(cellIndex).setSyncScreen(1);
                sendVideoAction();
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {

            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            if (COURSE_TYPE == TYPE_VEDIO) {
                sendVideoAction();
            }

            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            action();
            return true;

        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            showCoursePop();
            return true;

        } else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {

            if (COURSE_TYPE == TYPE_VEDIO) {
                if (mJzvideo != null) {
                    if (mJzvideo.isPlaying()) {
                        sendSynAction(ActionProtocol.ACTION_VEDIO_PAUSE);
                    } else {
                        sendSynAction(ActionProtocol.ACTION_VEDIO_ON);
                    }
                }
            }
            return true;

        } else if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            sendSynAction(ActionProtocol.ACTION_COURSE_STOP);
            return true;
        }


        return false;
    }


    //-----------------------------------------------------------视频相关-----------------------------------------------------------------

    private BasePopupWindow popupWindow;

    private BasePopupWindow popBtns;

    public void showButtonPop() {

        View menup = getLayoutInflater().inflate(R.layout.pop_button_menu, null);
        ImageView tv_select = menup.findViewById(R.id.tv_select);
        ImageView tv_next = menup.findViewById(R.id.tv_next);
        ImageView tv_over = menup.findViewById(R.id.tv_over);
        ImageView tv_xueshengping = menup.findViewById(R.id.tv_xueshengping);
        ImageView tv_pause = menup.findViewById(R.id.tv_pause);
        ImageView tv_replay = menup.findViewById(R.id.tv_replay);

        Utils.setOnFocusBG(tv_select, R.drawable.shape_strock, -1);
        Utils.setOnFocusBG(tv_next, R.drawable.shape_strock, -1);
        Utils.setOnFocusBG(tv_over, R.drawable.shape_strock, -1);
        Utils.setOnFocusBG(tv_xueshengping, R.drawable.shape_strock, -1);
        Utils.setOnFocusBG(tv_pause, R.drawable.shape_strock, -1);
        Utils.setOnFocusBG(tv_replay, R.drawable.shape_strock, -1);
        tv_select.requestFocus();

        if (mJzvideo != null) {
            if (mJzvideo.isPlaying()) {
                tv_pause.setImageResource(R.mipmap.btn_pause);
            } else {
                tv_pause.setImageResource(R.mipmap.btn_play);
            }
        }

        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCoursePop();
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

        tv_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //重放
                if (COURSE_TYPE == TYPE_VEDIO) {
                    sendVideoAction();
                    popBtns.dismiss();
                }
            }
        });

        tv_xueshengping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (COURSE_TYPE == TYPE_VEDIO) {
                    les.getSection(cellIndex).setSyncScreen(1);
                    sendVideoAction();
                    popBtns.dismiss();
                }
            }
        });

        tv_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (COURSE_TYPE == TYPE_VEDIO) {
                    if (mJzvideo != null) {
                        if (mJzvideo.isPlaying()) {
                            sendSynAction(ActionProtocol.ACTION_VEDIO_PAUSE, les.getSection(cellIndex));
                        } else {
                            sendSynAction(ActionProtocol.ACTION_VEDIO_ON, les.getSection(cellIndex));
                        }
                    }
                    popBtns.dismiss();
                }
            }
        });

        tv_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSynAction(ActionProtocol.ACTION_COURSE_STOP);
                popBtns.dismiss();
            }
        });

        popBtns = new BasePopupWindow(this);
        popBtns.setContentView(menup);
        popBtns.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popBtns.showAsDropDown(tvMenu, 0, -Utils.dip2px(this, tvMenu.getHeight()));


    }

    public void showCoursePop() {

        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            return;
        }

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wmManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wmManager.getDefaultDisplay().getMetrics(dm);
        int H = dm.heightPixels;
        int W = dm.widthPixels;

        View vv = getLayoutInflater().inflate(R.layout.pop_course, null);
        RecyclerView rcy_course = (RecyclerView) vv.findViewById(R.id.rcy_course);

        //设置布局管理器
        rcy_course.setLayoutManager(new GridLayoutManager(this, 8));
        CCAdapter cc = new CCAdapter();
        cc.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                setCellIndex(position - 1);
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

        CCAdapter2 cc2 = new CCAdapter2();
        cc2.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                int offset = les.getSectionsGSJ().size();
                setCellIndex(position - 1 + offset);
                action();
                popupWindow.dismiss();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        RecyclerView rcy_course2 = (RecyclerView) vv.findViewById(R.id.rcy_course2);
        rcy_course2.setLayoutManager(new GridLayoutManager(this, 8));
        rcy_course2.setAdapter(cc2);
        rcy_course2.setItemAnimator(new DefaultItemAnimator());
        rcy_course2.addItemDecoration(new SpaceItemDecoration(50));

        CCAdapter3 cc3 = new CCAdapter3();
        cc3.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                int offset = les.getSectionsGSJ().size() + les.getSectionsCED().size();
                setCellIndex(position - 1 + offset);
                action();
                popupWindow.dismiss();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        RecyclerView rcy_course3 = (RecyclerView) vv.findViewById(R.id.rcy_course3);
        rcy_course3.setLayoutManager(new GridLayoutManager(this, 8));
        rcy_course3.setAdapter(cc3);
        rcy_course3.setItemAnimator(new DefaultItemAnimator());
        rcy_course3.addItemDecoration(new SpaceItemDecoration(50));

        popupWindow = new BasePopupWindow(this);
        popupWindow.setWidth(W - 150);
        popupWindow.setHeight(H - 150);
        popupWindow.setContentView(vv);
        popupWindow.showAtLocation(fl_root, Gravity.TOP, 0, 75);

    }

    @OnClick({R.id.fl_one, R.id.tv_menu, R.id.iv_backmain})
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
        private int size;

        public CCAdapter() {

        }

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(CourseActivity.this).inflate(R.layout.item_pop_list, parent, false);
            Utils.setOnFocusBG(v, R.drawable.shape_strock, -1);

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
            holder.tv_name.setText(les.getSectionsGSJ().get(position).getGroupName() + "  " + les.getSectionsGSJ().get(position).getShowName());
            //holder.tv.setImageResource(R.mipmap.item_bg_gs);
        }

        @Override
        public int getItemCount() {
            return les.getSectionsGSJ().size();
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

    class CCAdapter2 extends RecyclerView.Adapter<CCAdapter2.MyViewHolder> {
        private OnItemClickLitener mOnItemClickLitener;

        public CCAdapter2() {

        }

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(CourseActivity.this).inflate(R.layout.item_pop_list, parent, false);
            Utils.setOnFocusBG(v, R.drawable.shape_strock, -1);

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
            holder.tv_name.setText(les.getSectionsCED().get(position).getGroupName() + "  " + les.getSectionsCED().get(position).getShowName());
            //holder.tv.setImageResource(R.mipmap.item_bg_ce);
        }

        @Override
        public int getItemCount() {
            return les.getSectionsCED().size();
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

    class CCAdapter3 extends RecyclerView.Adapter<CCAdapter3.MyViewHolder> {
        private OnItemClickLitener mOnItemClickLitener;

        public CCAdapter3() {
        }

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(CourseActivity.this).inflate(R.layout.item_pop_list, parent, false);
            Utils.setOnFocusBG(v, R.drawable.shape_strock, -1);

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
            holder.tv_name.setText(les.getSectionsTZ().get(position).getGroupName() + "  " + les.getSectionsTZ().get(position).getShowName());
            //  holder.tv.setImageResource(R.mipmap.item_bg_tz);
        }

        @Override
        public int getItemCount() {
            return les.getSectionsTZ().size();
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
            outRect.left = 10;
            outRect.right = 10;
            outRect.bottom = 0;
            outRect.top = 0;
        }

        public SpaceItemDecoration(int space) {
            this.mSpace = space;
        }
    }

    public void setCellIndex(int index) {
        this.cellIndex = index;
    }


    //------------教师端课程逻辑----------------------------------------------------------------------------------------------------------------

    /**
     * 切换小节   下一节课  下一节课
     */
    private void action() {

        if (ButtonUtils.isFastDoubleClick(1)) {
            return;
        }

        setCellIndex(++cellIndex);

        if (checkIndexOut()) {
            //课程结束
            setUIType(R.id.include_course_finish);
            mJzvideo.pause();
            --cellIndex;
            ivBackmain.requestFocus();
            return;
        }

        if (les.getSection(cellIndex).getType() == Constant.SECTION_TYPE_VIDEO) {
            //视频
            sendVideoAction();

        } else if (les.getSection(cellIndex).getType() == Constant.SECTION_TYPE_IMG) {
            //图片界面
            sendSynAction(ActionProtocol.ACTION_COURSE_IMG + "|" + les.getSection(cellIndex).getSourceName());

        } else if (les.getSection(cellIndex).getType() == Constant.SECTION_TYPE_NOTEPLAY) {
            //画谱
            sendSynAction(ActionProtocol.ACTION_COURSE_NOTE + "|" + les.getSection(cellIndex).getSourceName());

        } else if (les.getSection(cellIndex).getType() == Constant.SECTION_TYPE_VIDEO_H5) {
            //答题
            sendSynAction(ActionProtocol.ACTION_COURSE_ANSWER + "|" + les.getSection(cellIndex).getId());

        }

    }

    //先解密视频文件，后发送消息
    private void sendVideoAction() {

        //        mExecutor.execute(new Runnable() {
        //            @Override
        //            public void run() {
        //                //解密文件
        //                try {
        //
        //                    File f1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.FILE_PATH_CACHE + les.getSection(cellIndex).getSourceName());
        //                    File f2 = new File(Utils.getVideoPath() + les.getSection(cellIndex).getSourceName());
        //                    Encrypter.decode(f1,f2,"xmelody");
        //
        //                } catch (Exception e) {
        //                    e.printStackTrace();
        //                }
        //
        //                sendMsg(MSG_TYPE_SEDNVIEDO);
        //            }
        //        });

        sendMsg(MSG_TYPE_SEDNVIEDO);
    }

    private void sendVideoActionInner() {

        String code_light = "";
        String code_Screen = "";
        String code_h5 = "";
        if (1 == les.getSection(cellIndex).getLightCode()) {
            code_light = "|1";
        } else {
            code_light = "|0";
        }

        if (1 == les.getSection(cellIndex).getSyncScreen()) {
            code_Screen = "|1";
        } else {
            code_Screen = "|0";
        }

        if (les.getSection(cellIndex).getType() == Constant.SECTION_TYPE_VIDEO_H5) {
            code_h5 = "|1";

        } else {
            code_h5 = "|0";
        }

        String crouseName = "|" + les.getName();

        sendSynAction(ActionProtocol.ACTION_VEDIO_CHANGE + "|" + les.getSection(cellIndex).getSourceName() + code_light + code_Screen + crouseName);

    }

    private void sendMsg(int actionCode) {
        Message msg = Message.obtain();
        msg.what = actionCode;
        courseHandler.sendMessage(msg);
    }

    private boolean checkIndexOut() {
        boolean result = false;
        if (cellIndex + 1 > les.getSectionsList().size()) {
            result = true;
        }
        return result;
    }

    //------------公共课程逻辑start----------------------------------------------------------------------------------------------------------------

    private int currentPlayIndex = 0;

    /******
     * 消息入口
     ******/
    @Override
    protected void handleMsg(Message action) {
        super.handleMsg(action);
        try {
            doAction((String) action.obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /******
     * 1|2|3  消息体封装类
     ******/
    private ActionBean ab;

    private void doAction(String str) {
        /****** 开始新的一节， 重置各种状态 ******/
        resetLight();

        Log.e("kaka", "----------action code------- " + str);
        ab = ActionResolver.getInstance().resolve(str);
        if (ab.getCodeByPositon(0) == 1) {

            if (ab.getCodeByPositon(1) == ActionProtocol.CODE_ACTION_COURSE) {
                if (ab.getCodeByPositon(2) == 0) {
                    CourseActivity.this.finish();
                }
            } else if (ab.getCodeByPositon(1) == ActionProtocol.CODE_ACTION_VEDIO) {
                initVedioSection();

            } else if (ab.getCodeByPositon(1) == ActionProtocol.CODE_ACTION_SCORE) {
                resetVideo();
                initPlaySection();
            } else if (ab.getCodeByPositon(1) == ActionProtocol.CODE_ACTION_IMG) {
                //图片界面
                resetVideo();
                initImgSection();

            } else if (ab.getCodeByPositon(1) == ActionProtocol.CODE_ACTION_ANSWER) {
                //答题界面
                resetVideo();
                initAnswerSection();

            }

        }

    }

    /******
     * 启动视频  是否跟灯
     ******/
    public void initVedioSection() {
        COURSE_TYPE = TYPE_VEDIO;
        setUIType(R.id.rl_video);
        if (ActionProtocol.CODE_VEDIO_ON == ab.getCodeByPositon(2) || ActionProtocol.CODE_VEDIO_OFF == ab.getCodeByPositon(2)) {
            playOrPause();
        } else if (ActionProtocol.CODE_VEDIO_CHANGE == ab.getCodeByPositon(2)) {

            /******  学生端  ******/
/*            //是否投屏
            if(ActionProtocol.CODE_1 == ab.getCodeByPositon(5)) {
                swichPlayScr(ab.getStringByPositon(3));
                //是否亮灯
                if (1 == ab.getCodeByPositon(4)) {
                    startTemple();
                }
            }else{
                resetVideo();
                setUIType(R.id.rl_loading);
            }*/


            /******  教师端  ******/
            swichPlayScr(ab.getStringByPositon(3));
            //是否亮灯
            if (1 == ab.getCodeByPositon(4)) {
                startTemple();
            }

            if ("1-1-3-2.mp4".equals(ab.getStringByPositon(3))) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MelodyU.getInstance().showLight(getMidiOutputDevice());
                    }
                }, 3000);
            }

        }
    }

    /**
     * 增加图片类型
     */
    public void initImgSection() {
        COURSE_TYPE = TYPE_IMG;
        setUIType(R.id.rl_teacher_screen);
        rlTeacherScreen.setImageBitmap(Utils.getBitMap(ab.getStringByPositon(2)));
    }

    private void initAnswerSection() {
        COURSE_TYPE = TYPE_ANSWER;

        setUIType(R.id.webView);
        webView.setFocusable(true);
        webView.requestFocus();
        loadH5(les.getSection(cellIndex).getId() + "");

    }

    /******
     * 显示指定图谱
     ******/
    public void initPlaySection() {

        COURSE_TYPE = TYPE_PLAY;
        currentPlayIndex = 0;

        setUIType(R.id.rl_teacher_screen);
        //rlTeacherScreen.setBackgroundResource(R.mipmap.course_loading);
        rlTeacherScreen.setImageResource(R.mipmap.course_loading);

        //        //教师端独有代码，移植学生端需要手动删除
        //        if (Utils.isTeacherClient(CourseActivity.this) && mBaseApp.isTV()) {
        //            setUIType(R.id.rl_teacher_screen);
        //            rlTeacherScreen.setBackgroundResource(R.mipmap.course_loading);
        //            return;
        //        }
        //
        //        setUIType(R.id.rl_score);
        //        showTopLayout((currentPlayIndex + 1) + "");
        //        initNoteAndLight();
    }

    //初始化音符
    private void initNoteAndLight() {
        NoteInfo nextInfo = null;

        if (ab.getStringByPositon(2).equals(MelodyU.PIC_NAME_1)) {
            nextInfo = new NoteInfo(39, 1, MelodyU.getKeyIndex(39), true);

        } else if (ab.getStringByPositon(2).equals(MelodyU.PIC_NAME_2)) {
            nextInfo = new NoteInfo(39, 1, MelodyU.getKeyIndex(39), true);

        } else if (ab.getStringByPositon(2).equals(MelodyU.PIC_NAME_3)) {
            nextInfo = new NoteInfo(39, 1, MelodyU.getKeyIndex(39), true);

        } else if (ab.getStringByPositon(2).equals(MelodyU.PIC_NAME_4)) {
            nextInfo = new NoteInfo(39, 1, MelodyU.getKeyIndex(39), true);

        } else if (ab.getStringByPositon(2).equals(MelodyU.PIC_NAME_5)) {
            nextInfo = new NoteInfo(46, 1, MelodyU.getKeyIndex(39), true);

        } else if (ab.getStringByPositon(2).equals(MelodyU.PIC_NAME_6)) {
            nextInfo = new NoteInfo(39, 1, MelodyU.getKeyIndex(39), true);

        } else if (ab.getStringByPositon(2).equals(MelodyU.PIC_NAME_7)) {
            nextInfo = new NoteInfo(46, 1, MelodyU.getKeyIndex(39), true);

        } else if (ab.getStringByPositon(2).equals(MelodyU.PIC_NAME_8)) {
            nextInfo = new NoteInfo(39, 1, MelodyU.getKeyIndex(39), true);

        } else if (ab.getStringByPositon(2).equals(MelodyU.PIC_NAME_D1)) {
            nextInfo = new NoteInfo(39, 1, MelodyU.getKeyIndex(39), true);

        }

        MelodyU.getInstance().setNoteAndKey(this, rlScore, nextInfo.getNoteIndex(), nextInfo.isIdNoteRed(), nextInfo.getKeyIndex(), nextInfo.isIdNoteRed());
        //亮灯显示
        doLight(nextInfo);

        preInfo = nextInfo;
    }

    /******
     * 显示指定图谱
     ******/
    private void showTopLayout(String tag) {
        //遍历viewgroup
        LinearLayout vg = null;
        int[] ls = MelodyU.getInstance().getPlayLayouts(ab.getStringByPositon(2));
        for (int i = 0; i < ls.length; i++) {
            vg = (LinearLayout) getLayoutInflater().inflate(ls[i], null);
            ViewGroup vgTop = vg.findViewById(R.id.rl_top);
            for (int n = 0; n < vgTop.getChildCount(); n++) {
                if (tag.equals((String) vgTop.getChildAt(n).getTag())) {
                    replaceLayout(rlScore, ls[i]);
                    return;
                }
            }
        }
    }

    /******
     * 替换布局
     ******/
    private void replaceLayout(ViewGroup fu, int resId) {
        fu.removeAllViews();
        ViewGroup vg = (ViewGroup) LayoutInflater.from(this).inflate(resId, fu);
    }

    //上一个亮灯
    private NoteInfo preInfo = null;

    /******
     * 输入检测  正确则返回下一个音符信息
     ******/
    private void checkInput(int note) {
        NoteInfo nextInfo = null;
        //判断对错
        if ((nextInfo = MelodyU.checkInputX(note, currentPlayIndex, ab.getStringByPositon(2))) != null) {

            /****** 循环判断 ******/
            if (currentPlayIndex == (MelodyU.searchNotes(ab.getStringByPositon(2)).size() - 1)) {
                currentPlayIndex = 0;
            } else {
                currentPlayIndex++;
            }

            //处理多页面 加载正确的页面
            showTopLayout((currentPlayIndex + 1) + "");
            //下一个音符的UI显示
            MelodyU.getInstance().setNoteAndKey(this, rlScore, nextInfo.getNoteIndex(), nextInfo.isIdNoteRed(), nextInfo.getKeyIndex(), nextInfo.isIdNoteRed());

            if (preInfo != null) {
                offLight(preInfo);
            }
            doLight(nextInfo);


            preInfo = nextInfo;
        }
    }

    /******
     * 点亮某一个灯
     ******/
    private void doLight(NoteInfo nextInfo) {
        if (mOutputDevice == null) {
            return;
        }
        MelodyU.getInstance().offAllLight(mOutputDevice);
        mOutputDevice.sendMidiSystemExclusive(0, MelodyU.getlightCode(nextInfo.getNote() + 21, nextInfo.isIdNoteRed(), true));
        if (nextInfo.getInfo() != null) {
            mOutputDevice.sendMidiSystemExclusive(0, MelodyU.getlightCode(nextInfo.getInfo().getNote() + 21, nextInfo.getInfo().isIdNoteRed(), true));
        }
    }

    private void offLight(NoteInfo info) {
        if (mOutputDevice == null) {
            return;
        }
        mOutputDevice.sendMidiSystemExclusive(0, MelodyU.getlightCode(info.getNote() + 21, info.isIdNoteRed(), false));
    }

    private void resetLight() {
        MelodyU.getInstance().offAllLight(mOutputDevice);
        stopTempleLight();

        //这里暂停 会出现异常情况
        //vv.stopPlayback();
    }

    private void resetVideo() {
        if (mJzvideo != null) {
            JzvdStd.releaseAllVideos();
        }
    }

    private void startTemple() {
        if (mOutputDevice == null) {
            return;
        }
        if (tt != null) {
            tt.interrupt();
            tt = null;
        }

        if ("第一课".equals(ab.getStringByPositon(6))) {
            tt = new TempleThread(mOutputDevice, d_starttime_1, d_duringtime_1, d_color_1, d_note_1);
        } else if ("第二课".equals(ab.getStringByPositon(6))) {
            tt = new TempleThread(mOutputDevice, d_starttime_2, d_duringtime_2, d_color_2, d_note_2);
        } else if ("第三课".equals(ab.getStringByPositon(6))) {
            tt = new TempleThread(mOutputDevice, d_starttime_3, d_duringtime_3, d_color_3, d_note_3);
        } else if ("第四课".equals(ab.getStringByPositon(6))) {
            tt = new TempleThread(mOutputDevice, d_starttime_4, d_duringtime_4, d_color_4, d_note_4);
        } else if ("第五课".equals(ab.getStringByPositon(6))) {
            tt = new TempleThread(mOutputDevice, d_starttime_4, d_duringtime_4, d_color_4, d_note_4);
        } else if ("第六课".equals(ab.getStringByPositon(6))) {
            tt = new TempleThread(mOutputDevice, d_starttime_4, d_duringtime_4, d_color_4, d_note_4);
        } else if ("第七课".equals(ab.getStringByPositon(6))) {
            tt = new TempleThread(mOutputDevice, d_starttime_4, d_duringtime_4, d_color_4, d_note_4);
        } else if ("第八课".equals(ab.getStringByPositon(6))) {
            tt = new TempleThread(mOutputDevice, d_starttime_4, d_duringtime_4, d_color_4, d_note_4);
        }

        if (tt != null) {
            flagV = true;
            tt.start();
        }
    }

    //note 21 -108 序号  钢琴按键排序从1开始
    @Override
    public void onMidiNoteOff(@NonNull MidiInputDevice sender, int cable, int channel, final int note, int velocity) {
        super.onMidiNoteOff(sender, cable, channel, note, velocity);
        if (COURSE_TYPE == TYPE_PLAY) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    checkInput(note - 21);
                }
            });

        }
    }

    @Override
    public void onMidiOutputDeviceAttached(@NonNull MidiOutputDevice midiOutputDevice) {
        super.onMidiOutputDeviceAttached(midiOutputDevice);
        Toast.makeText(this, "钢琴已连接", 0).show();
        mOutputDevice = getMidiOutputDevice();
    }

    private TempleThread tt;

    private void stopTempleLight() {
        if (tt != null) {
            tt.interrupt();
            tt = null;
            flagV = false;
        }
    }

    /* 跟灯 */
    private volatile boolean flagV = true;

    class TempleThread extends Thread {
        MidiOutputDevice md;
        long[] delay = null; //时间延迟执行
        long[] dur = null;   //亮灯时间
        int[] color = null;
        int[] index = null;   //亮灯位置

        public TempleThread(MidiOutputDevice mod, long[] mDelays, long[] mdur, int[] mcolor, int[] mindex) {
            md = mod;
            delay = mDelays;
            dur = mdur;
            color = mcolor;
            index = mindex;
        }

        @Override
        public void run() {
            int xunhuan = 0;
            try {
                while (flagV) {
                    if (xunhuan > delay.length - 1) {
                        if (tt != null) {
                            tt = null;
                        }
                        return;
                    }
                    if (mJzvideo != null) {
                        int curTime = (int) mJzvideo.getCurrentPositionWhenPlaying();
                        if (curTime > delay[xunhuan]) {
                            MelodyU.getInstance().lightTempo(md, dur, color, index);
                            xunhuan++;
                        }
                    }
                }
            } catch (InterruptedException e) {
            }
        }
    }


    //------------公共逻辑end----------------------------------------------------------------------------------------------------------------


    //------------父类提供方法end----------------------------------------------------------------------------------------------------------------
    @Override
    public void onNextCourse() {
        action();
    }

    @Override
    public String getID() {
        if (les != null) {
            String id = les.getSection(cellIndex).getId() + "";
            return id;
        }
        return "";
    }

}






