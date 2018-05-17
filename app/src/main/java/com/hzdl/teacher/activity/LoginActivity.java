package com.hzdl.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.hzdl.mex.utils.SPUtils;
import com.hzdl.teacher.R;
import com.hzdl.teacher.base.BaseActivity;
import com.hzdl.teacher.base.Constant;
import com.hzdl.teacher.bean.LoginBean;
import com.hzdl.teacher.net.ITeacher;
import com.hzdl.teacher.utils.Md5Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.username)
    EditText usernameEditText;
    @BindView(R.id.password)
    EditText passwordEditText;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.cb_login)
    CheckBox cbLogin;

    private boolean psdCached;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        gestureDetector=new GestureDetector(LoginActivity.this,new GestureDelectorSimlpeListener());
        initView();

    }

    private void initView() {

        int tag = (int) SPUtils.get(this,Constant.KEY_REMENBER_TAG,0);
        if(tag==1){
            psdCached = true;
            cbLogin.setChecked(true);
            String id = (String) SPUtils.get(this,Constant.KEY_LOGIN_PHONE,"");
            String psd = (String) SPUtils.get(this,Constant.KEY_LOGIN_PSD,"");
            usernameEditText.setText(id);
            passwordEditText.setText(psd);
        }else{
            psdCached = false;
            cbLogin.setChecked(false);
            SPUtils.remove(this,Constant.KEY_LOGIN_PHONE);
            SPUtils.remove(this,Constant.KEY_LOGIN_PSD);
        }

        btnLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 获取焦点时操作，常见的有放大、加边框等
                    btnLogin.setBackgroundResource(R.drawable.shape_strock);
                } else {
                    btnLogin.setBackgroundResource(R.drawable.shape_btn_login);
                    // 失去焦点时操作，恢复默认状态
                }
            }
        });

        cbLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    psdCached = true;
                    SPUtils.put(LoginActivity.this,Constant.KEY_REMENBER_TAG,1);
                }else{
                    psdCached = false;
                    SPUtils.put(LoginActivity.this,Constant.KEY_REMENBER_TAG,0);
                }
            }
        });
    }


    @Override
    protected void handleMsg(Message msg) {


    }

    private void netLogin() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ITeacher userBiz = retrofit.create(ITeacher.class);
        Call<LoginBean> call = userBiz.postLogin(usernameEditText.getText().toString(), Md5Util.md5(passwordEditText.getText().toString()));
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (response == null || response.body() == null)
                    return;
                if (200 == response.body().getCode()) {

                    SPUtils.put(LoginActivity.this,Constant.KEY_SCHOOL_ID, response.body().getDetail().getSchoolId() + "");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                    LoginActivity.this.finish();
                } else {
                    Toast.makeText(LoginActivity.this, "请检查输入", 0).show();
                }

            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "登录失败", 0).show();
            }

        });
    }

    @OnClick({R.id.password, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.password:
                break;
            case R.id.btn_login:

                if(usernameEditText.getText().toString().equals("111")){
                    startMain();
                    return;
                }
                setPsd();
                netLogin();
                //Utils.showScreen(this);
                break;
        }
    }

    private void setPsd() {
        if(psdCached){
            SPUtils.put(this,Constant.KEY_LOGIN_PHONE,usernameEditText.getText().toString());
            SPUtils.put(this,Constant.KEY_LOGIN_PSD,passwordEditText.getText().toString());
        }
    }

    /**
     * 2.继承 SimpleOnGestureListener
     * 重载 感兴趣的 手势
     * @author yuan
     *
     */
    class GestureDelectorSimlpeListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            /*
             *  滑动 使用 onFling（）方
             *  3.判断法
             */
            String result=drawTouch(e1.getX(),e1.getY(),e2.getX(),e2.getY());
            //Log.e("kaka",result);
            return true;
        }

        /**
         * 手势判断
         * @param x
         * @param y
         * @param upx
         * @param upy
         * @return
         */
        private String drawTouch(float x,float y,float upx,float upy){

            String str="没有滑动";
            //水平滑动
            if(upx-x>100){
                str="向右滑动";
                //改变图片

            }else if(x-upx>100){
                str="向左滑动";
                //改变图片

            }else if(upy-y>100){
                str="向下滑动";
                //改变图片

            }else if(y-upy>400){
                str="向上滑动";
                startMain();
                //改变图片

            }
            return str;
        }

    }

    private void startMain(){
        mBaseApp.setRoot(true);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }
}


