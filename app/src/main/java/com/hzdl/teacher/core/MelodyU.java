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

import com.hzdl.teacher.R;

import java.util.ArrayList;

/**
 * Created by wangshuai on 2017/10/18.
 */

public class MelodyU {

    private static MelodyU instance;

    private MelodyU mPopupWindow;

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


    /**
     *      动态操作UI元素
     *
     *      根据跟奏[数据] 设置对应的音符，琴键界面变化
     *      音符位置，音符颜色
     *
     *      音符Tag从左至右升序排列 0 - 10  上下都有的序号相同
     *
     *
     *      音符蓝色背景 R.mipmap.kc_blue_puzi_bg 无背景 0
     *
     *  ((ImageView) whiteKeyLl2.getChildAt(i - 1)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, 0xFFFB5555));
     ((ImageView) whiteKeyLl2.getChildAt(i - 1)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, 0xFF34B4FF));
     ((ImageView) whiteKeyLl2.getChildAt(i - 1)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, Color.WHITE));
     *
     *
     */
    private ArrayList<ImageView> noteList = null;
    public void setNoteAndKey(Context context, ViewGroup vg, int noteIndex, boolean isNoteRed, int keyIndex, boolean isKeyRed){

        getNotes(vg);

        //设置对应音符位置的颜色
        for(ImageView iv : noteList){
            String index = (String) iv.getTag();
            if(index.equals(noteIndex + "")) {
                if(isNoteRed){
                    iv.setBackgroundResource(R.mipmap.kc_red_puzi_bg);
                }else{
                    iv.setBackgroundResource(R.mipmap.kc_blue_puzi_bg);
                }
            }else{
                iv.setBackgroundResource(0);
            }

        }

        //改变琴键的颜色
        LinearLayout gg = (LinearLayout) vg.findViewById(R.id.white_keys);
        for(int i=0;i<gg.getChildCount(); i++){
            ImageView iv = (ImageView) gg.getChildAt(i);
            if(keyIndex - 1 == i){
                if(isKeyRed){
                    iv.setImageDrawable(getTintPic(context, R.mipmap.kc_white_key, 0xFFFB5555));
                }else{
                    iv.setImageDrawable(getTintPic(context, R.mipmap.kc_white_key, 0xFF34B4FF));
                }
            }else{
                iv.setImageDrawable(getTintPic(context, R.mipmap.kc_white_key, Color.WHITE));
            }
        }

        noteList = null;
    }

    private void getNotes(ViewGroup vg){
        if(noteList==null){
            noteList = new ArrayList<>();
        }
        for(int i=0;i<vg.getChildCount();i++){
            if(vg.getChildAt(i) instanceof ViewGroup){
                getNotes((ViewGroup) vg.getChildAt(i));
            }else{
                if(vg.getChildAt(i).getTag()!=null && !TextUtils.isEmpty(vg.getChildAt(i).getTag().toString())){
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

}
