package com.hzdl.teacher.core;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hzdl.mex.usb.UsbDeviceInfo;
import com.hzdl.mex.utils.Log;
import com.hzdl.teacher.R;

import java.util.ArrayList;

import jp.kshoji.driver.midi.device.MidiOutputDevice;

/**
 * Created by wangshuai on 2017/10/18.
 */

public class MelodyU {

    //-----数据段start---------------------------------------------------------------------------------------------

    public static int[] note_1 = {39,40,41,42,43,44,45,46,
                                  47 ,48 ,49 ,50 ,51 , 51 , 50 ,49 ,48,
                                  47 ,46 ,45, 44, 43 ,42 ,41 ,40 ,39};
    public static int[] color_1 = {1, 1, 1, 1, 1, 1, 1, 1,
                                   1, 1, 1, 1, 1, 1, 1, 1,1,
                                   1, 1, 1, 1, 1, 1, 1, 1,1};


    public static ArrayList<NoteInfo> course_1 = new ArrayList<>();
    public static ArrayList<NoteInfo> course_2 = new ArrayList<>();

    static {
        course_1.addAll(setNoteList(note_1, color_1));
    }

    //-----数据段end---------------------------------------------------------------------------------------------

    private MidiOutputDevice mOutputDevice;

    /**
     * playIndex      从0开始
     *
     * @param note  音符号
     * @param resId 资源id
     * @return 返回下一个音符信息
     */
    public static NoteInfo checkInputX(int note, int playIndex, int resId) {
        NoteInfo result = null;

        if (resId == -1) {

            playIndex = playIndex % course_1.size();

            //判断正误
            if (note != course_1.get(playIndex).getNote()) {
                return null;
            }

            //返回下一个
            if (playIndex == (course_1.size() - 1)) {
                playIndex = 0;
            } else {
                playIndex++;
            }
            result = course_1.get(playIndex);
        }
        return result;
    }

    /**
     * 根据课程id找资源
     * @param id
     * @return
     */
    public ArrayList<NoteInfo> getCourseById(int id){
        ArrayList<NoteInfo> list = null;
        switch(id) {
            case 1:
                list = course_1;
                break;
            case 2:
                break;
        }
        return list;
    }

    /**
     *      动态操作UI元素
     *
     *      音符部分： 找到对应TAG,进行相关操作
     *      琴键部分：
     *
     */
    private ArrayList<ImageView> noteList = null;

    public void setNoteAndKey(Context context, ViewGroup vg, int noteIndex, boolean isNoteRed, int keyIndex, boolean isKeyRed) {

        getNotes(vg);

        //设置对应音符位置的颜色
        for (ImageView iv : noteList) {
            String index = (String) iv.getTag();
            if (index.equals(noteIndex + "")) {
                if (isNoteRed) {
                    iv.setBackgroundResource(R.mipmap.kc_red_puzi_bg);
                } else {
                    iv.setBackgroundResource(R.mipmap.kc_blue_puzi_bg);
                }
            } else {
                iv.setBackgroundResource(0);
            }

        }

        //改变琴键的颜色
        LinearLayout gg = (LinearLayout) vg.findViewById(R.id.white_keys);
        for (int i = 0; i < gg.getChildCount(); i++) {
            ImageView iv = (ImageView) gg.getChildAt(i);
            if (keyIndex - 1 == i) {
                if (isKeyRed) {
                    iv.setImageDrawable(getTintPic(context, R.mipmap.kc_white_key, 0xFFFB5555));
                } else {
                    iv.setImageDrawable(getTintPic(context, R.mipmap.kc_white_key, 0xFF34B4FF));
                }
            } else {
                iv.setImageDrawable(getTintPic(context, R.mipmap.kc_white_key, Color.WHITE));
            }
        }

        //改变黑键键的颜色
        LinearLayout gg = (LinearLayout) vg.findViewById(R.id.black_keys);
        for (int i = 0; i < gg.getChildCount(); i++) {
            ImageView iv = (ImageView) gg.getChildAt(i);
            if (keyIndex  - 14 - 1 == i) {
                if (isKeyRed) {
                    iv.setImageDrawable(context.getResources().getDrawable(R.mipmap.key_red_right));
                } else {
                    iv.setImageDrawable(context.getResources().getDrawable(R.mipmap.key_blue_left));
                }
            } else {
                iv.setImageDrawable(context.getResources().getDrawable(R.mipmap.kc_black_key));
            }
        }

        noteList = null;
    }

    /**
     * F0 4D 4C 4C 45 15 01 F7    键盘左起第一键对应的红灯亮
     * F0 4D 4C 4C 45 15 00 F7    键盘左起第一键对应的红灯熄灭
     * F0 4D 4C 4C 45 15 11 F7    键盘左起第一键对应的蓝灯亮
     * F0 4D 4C 4C 45 15 10 F7    键盘左起第一键对应的蓝灯熄灭
     *
     * @param note(21 - 108)
     * @param isRed
     * @return
     */
    public static byte[] getlightCode(int note, boolean isRed, boolean isOn) {

        byte mNote = (byte) note;
        byte mOn;
        if (isRed) {
            if (isOn) {
                mOn = 0x01;
            } else {
                mOn = 0x00;
            }
        } else {
            if (isOn) {
                mOn = 0x11;
            } else {
                mOn = 0x10;
            }
        }

        byte[] codes = {(byte) 0xF0, 0x4D, 0x4C, 0x4C, 0x45, mNote, mOn, (byte) 0xF7};

        return codes;
    }

    /***
     * 根据乐谱亮灯
     *
     * @param context
     * @param dur
     * @param color
     * @param index
     * @throws InterruptedException
     */
    public void lightTempo(MidiOutputDevice outPut,float[] dur, int[] color, int[] index) throws InterruptedException {
        this.mOutputDevice = outPut;
        final float[] idur = dur;      //音符间隔       音符个数
        final int[] icolor = color;   //色值判断
        final int[] iindex = index;  //亮灯位置
        for (int n = 0; n < idur.length; n++) {
            Thread.sleep(2);
            if (iindex[n] != -1) {
                //每个音符亮灯时长
                if (icolor[n] == 1) {
                    beat(iindex[n], true, (long) (idur[n] * 1000));// 0 0 4 4
                } else if (icolor[n] == 0) {
                    beat(iindex[n], false, (long) (idur[n] * 1000));
                }
                //和下个音符间隔时长
                Thread.sleep((long) (idur[n] * 1000));
            } else {
                Thread.sleep((long) (idur[n] * 1000));
            }
        }
    }

    //闪烁一次灯
    public void beat(int index, final boolean isRed, final long time) {
        mOutputDevice.sendMidiSystemExclusive(0,MelodyU.getlightCode(index,isRed,true));
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mOutputDevice.sendMidiSystemExclusive(0,MelodyU.getlightCode(index,isRed,false));

    }

    /**
     * 返回对应课程布局
     * @return
     */
    public int[] getPlayLayouts(int id){

        int[] ls = {R.layout.include_score};
        return ls;

    }

    /**
     * 生成 跟奏 需要的乐谱
     *
     * @param color
     * @param note
     * @return
     */
    private static ArrayList<NoteInfo> setNoteList(int[] note, int[] color) {
        ArrayList<NoteInfo> al = new ArrayList<NoteInfo>();
        for (int i = 0, m = 1; i < note.length; i++) {
            if (note[i] == -1) {
                continue;
            }
            NoteInfo ni = new NoteInfo();
            ni.setNote(note[i]);
            ni.setNoteIndex(m);
            ni.setKeyIndex(getKeyIndex(note[i]));
            ni.setIdNoteRed(color[i] == 1 ? true : false);
            al.add(ni);
            m++;
        }
        return al;
    }

    /**
     * 根据音符号 获取琴键 index
     *
     * @param noteIndex
     * @return
     */
    public static int getKeyIndex(int noteNum) {
        int result = -1;
        switch (noteNum) {
            case 27:
                result = 1;
                break;
            case 29:
                result = 2;
                break;
            case 31:
                result = 3;
                break;
            case 32:
                result = 4;
                break;
            case 34:
                result = 5;
                break;
            case 36:
                result = 6;
                break;
            case 38:
                result = 7;
                break;
            case 39:
                result = 8;
                break;
            case 41:
                result = 9;
                break;
            case 43:
                result = 10;
                break;
            case 44:
                result = 11;
                break;
            case 46:
                result = 12;
                break;
            case 48:
                result = 13;
                break;
            case 50:
                result = 14;
                break;



            case 28:
                result = 15;
                break;
            case 30:
                result = 16;
                break;
            case 33:
                result = 17;
                break;
            case 35:
                result = 18;
                break;
            case 37:
                result = 19;
                break;
            case 40:
                result = 20;
                break;
            case 42:
                result = 21;
                break;
            case 45:
                result = 22;
                break;
            case 47:
                result = 23;
                break;
            case 49:
                result = 24;
                break;



        }
        return result;
    }


    private void getNotes(ViewGroup vg) {
        if (noteList == null) {
            noteList = new ArrayList<>();
        }
        for (int i = 0; i < vg.getChildCount(); i++) {
            if (vg.getChildAt(i) instanceof ViewGroup) {
                getNotes((ViewGroup) vg.getChildAt(i));
            } else {
                if (vg.getChildAt(i).getTag() != null && !TextUtils.isEmpty(vg.getChildAt(i).getTag().toString())) {
                    noteList.add((ImageView) vg.getChildAt(i));
                }
            }
        }
    }

    /**
     * 改变图片颜色
     */
    private Drawable getTintPic(Context context, int image, int color) {
        Drawable drawable = ContextCompat.getDrawable(context, image);
        Drawable.ConstantState constantState = drawable.getConstantState();
        Drawable newDrawable = DrawableCompat.wrap(constantState == null ? drawable : constantState.newDrawable()).mutate();
        DrawableCompat.setTint(newDrawable, color);
        return newDrawable;
    }


    private static MelodyU instance;

    // 私有化构造方法，变成单例模式
    private MelodyU() {
    }

    // 对外提供一个该类的实例，考虑多线程问题，进行同步操作
    public static MelodyU getInstance() {
        if (instance == null) {
            synchronized (MelodyU.class) {
                if (instance == null) {
                    instance = new MelodyU();
                }
            }
        }
        return instance;
    }


}













