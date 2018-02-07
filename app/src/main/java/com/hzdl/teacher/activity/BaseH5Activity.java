package com.hzdl.teacher.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.hzdl.mex.socket.teacher.TeacherClient;
import com.hzdl.mex.utils.Log;
import com.hzdl.teacher.R;
import com.hzdl.teacher.base.BaseActivity;
import com.hzdl.teacher.base.BaseMidiActivity;
import com.hzdl.teacher.core.ActionBean;
import com.hzdl.teacher.core.ActionProtocol;
import com.hzdl.teacher.core.ActionResolver;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseH5Activity extends BaseMidiActivity {

    public static final String URL_ROOT = "file:///android_asset/question-for-teacher1/";
    private String questionIndex = "";

    WebView mWebview;
    WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    public void initH5() {

        mWebview = findViewById(R.id.webView);
        mWebSettings = mWebview.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebview.addJavascriptInterface(new AndroidtoJs(), "android");

        mWebview.loadUrl(URL_ROOT + "questionForTeacher.html");
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

    private ActionBean ab;
    /**
     * 收到学生端成绩消息
     * @param msg
     */
    @Override
    protected void handleMsg(Message action) {
        doAction((String) action.obj);
    }
    private void doAction(String str) {
        Log.e("kaka", "----------H5Activity code------- " + str);
        ab = ActionResolver.getInstance().resolve(str);
        if (ab.getCodeByPositon(0) == 2) {
            if(ab.getCodeByPositon(1) == 3){
                questionIndex = ab.getStringByPositon(2);
                nextQuestionImpl();
            }else if(ab.getCodeByPositon(1) == 0){
                finish();
            }else if(ab.getCodeByPositon(1) == 2){
                //教师端收到成绩  发送给h5
                mWebview.loadUrl("javascript:callJS('" + ab.getStringByPositon(2) + "')");
            }
        }
    }

    //---非公共逻辑-----------------------------------------------------------------------------------------------------

    // 继承自Object类
    public class AndroidtoJs extends Object {

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void next(String index) {
            //通知学生下一步
            actionNextQuestion(index);
        }
    }

    //教师端控制下一题
    public void actionNextQuestion(String arg){
        //sendSynAction(ActionProtocol.ACTION_TEST_NUM + "|" + arg);
        TeacherClient.getInstance().sendMsgToAll(ActionProtocol.ACTION_TEST_NUM + "|" + arg);
    }


    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            sendSynAction(ActionProtocol.ACTION_TEST_OFF);
        }else if(keyCode == KeyEvent.KEYCODE_MENU ){
            mWebview.loadUrl("javascript:getQuestionList()");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void nextQuestionImpl(){
        mWebview.loadUrl("javascript:switchQuestion('" + questionIndex +"')");
    }

}
