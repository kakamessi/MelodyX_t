package com.hzdl.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hzdl.teacher.R;
import com.hzdl.teacher.base.BaseActivity;
import com.hzdl.teacher.base.Constant;
import com.hzdl.teacher.bean.LoginBean;
import com.hzdl.teacher.net.ITeacher;
import com.hzdl.teacher.utils.Md5Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends BaseActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    private String currentUsername;
    private String currentPassword;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

    }

    private void initView() {

        usernameEditText = (EditText)findViewById(R.id.username);
        passwordEditText = (EditText)findViewById(R.id.password);

        usernameEditText.setText("13700000001");
        passwordEditText.setText("123456");

    }


    @Override
    protected void handleMsg(Message msg) {


    }

    public void login(View v){
        netLogin();
    }

    private void netLogin() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ITeacher userBiz = retrofit.create(ITeacher.class);
        Call<LoginBean> call = userBiz.postLogin(usernameEditText.getText().toString(), Md5Util.md5(passwordEditText.getText().toString()));
        call.enqueue(new Callback<LoginBean>()
        {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response)
            {
                if(response==null || response.body()==null)
                    return;
                if(200==response.body().getCode()){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                    LoginActivity.this.finish();
                }else{
                    Toast.makeText( LoginActivity.this,"请检查输入",0).show();
                }

            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t)
            {
                Toast.makeText( LoginActivity.this,"登录失败",0).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
            }

        });
    }


}
