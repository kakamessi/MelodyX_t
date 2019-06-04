package com.hzdl.teacher.view;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.hzdl.teacher.R;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUserAction;
import cn.jzvd.JZUtils;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class JZVideo extends JzvdStd {


    public JZVideo(Context context) {
        super(context);
        init();
    }

    private void init() {
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;//全屏方向
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;//普通方向
        Jzvd.SAVE_PROGRESS = false;
    }

    public JZVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void pause(){

        if (jzDataSource == null || jzDataSource.urlsMap.isEmpty() || jzDataSource.getCurrentUrl() == null) {
            Toast.makeText(getContext(), getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
            return;
        }
        if (currentState == CURRENT_STATE_NORMAL) {
            if (!jzDataSource.getCurrentUrl().toString().startsWith("file") && !
                    jzDataSource.getCurrentUrl().toString().startsWith("/") &&
                    !JZUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {
                showWifiDialog();
                return;
            }
            startVideo();
            onEvent(JZUserAction.ON_CLICK_START_ICON);//开始的事件应该在播放之后，此处特殊
        } else if (currentState == CURRENT_STATE_PLAYING) {
            onEvent(JZUserAction.ON_CLICK_PAUSE);
            Log.d(TAG, "pauseVideo [" + this.hashCode() + "] ");
            JZMediaManager.pause();
            onStatePause();
        } else if (currentState == CURRENT_STATE_PAUSE) {
            onEvent(JZUserAction.ON_CLICK_RESUME);
            JZMediaManager.start();
            onStatePlaying();
        } else if (currentState == CURRENT_STATE_AUTO_COMPLETE) {
            onEvent(JZUserAction.ON_CLICK_START_AUTO_COMPLETE);
            startVideo();
        }

    }

    public boolean isPlaying() {
        boolean result = false;
        if(currentState == CURRENT_STATE_PLAYING){
            result = true;
        }
        return result;
    }

    public void startPlaying(){
        onEvent(JZUserAction.ON_CLICK_START_AUTO_COMPLETE);
        startVideo();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onStateAutoComplete() {

    }




}






