package com.hzdl.teacher.activity;

import android.nfc.cardemulation.HostNfcFService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.hzdl.mex.utils.Log;
import com.hzdl.teacher.R;
import com.hzdl.teacher.base.BaseMidiActivity;
import com.hzdl.teacher.core.MelodyU;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.kshoji.driver.midi.device.MidiInputDevice;
import jp.kshoji.driver.midi.device.MidiOutputDevice;


public class H5MusicActivity extends BaseMidiActivity {

    @BindView(R.id.webView1)
    WebView mWebview;

    WebSettings mWebSettings;

    private MidiOutputDevice mOutputDevice;

    int mNote;
    Handler hand = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch(msg.what)
            {
                case 1:
                    mWebview.loadUrl( "javascript:backStatusJS('" + mNote + "')" );;

                case 0:
                    mWebview.loadUrl( "javascript:backKeyUpJs('" + mNote + "')" );

            }

            Log.e("kaka",mNote + "----------------");

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5_music);
        ButterKnife.bind(this);
        initMidi();
        init();
    }

    private void init() {
        mWebSettings = mWebview.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebview.addJavascriptInterface(new H5MusicActivity.AndroidForJs(), "android");

        mWebview.loadUrl("http://10.0.0.6:8091");
        //mWebview.loadUrl("http://192.168.1.135:8091");
        //mWebview.loadUrl("http://q.w3cstudy.cc/t/questionForTeacher.html");

        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {
            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
            }
            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
                }
            }
        });

    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();

            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }


    @Override
    protected void handleMsg(Message msg) {




    }


    // mWebview.loadUrl( "javascript:callJS('" + ab.getStringByPositon(2) + "')" );
    //JS调用Android
    public class AndroidForJs extends Object {

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void setLedStatus(String index, boolean isRed, boolean isOn) {
            Toast.makeText(H5MusicActivity.this,":" + index,0).show();
            mOutputDevice.sendMidiSystemExclusive(0, MelodyU.getlightCode(Integer.parseInt(index), isRed, isOn));
        }

    }

    //note 21 -108 序号  钢琴按键排序从1开始
    @Override
    public void onMidiNoteOff(@NonNull MidiInputDevice sender, int cable, int channel, final int note, int velocity) {
        super.onMidiNoteOff(sender, cable, channel, note, velocity);

        mNote = note;
        hand.sendEmptyMessage(0);

    }

    @Override
    public void onMidiNoteOn(@NonNull MidiInputDevice sender, int cable, int channel, final int note, int velocity) {
        super.onMidiNoteOn(sender, cable, channel, note, velocity);

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mWebview.loadUrl( "javascript:backStatusJS('" + note + "')" );
//                Toast.makeText(H5MusicActivity.this,":" + note,0).show();
//            }
//        });

//        mNote = note;
//        hand.sendEmptyMessage(1);

    }

    @Override
    public void onMidiOutputDeviceAttached(@NonNull MidiOutputDevice midiOutputDevice) {
        super.onMidiOutputDeviceAttached(midiOutputDevice);
        Toast.makeText(this, "钢琴已连接", 0).show();
        mOutputDevice = getMidiOutputDevice();
        MelodyU.getInstance().startBeatThread(midiOutputDevice);
    }

}


