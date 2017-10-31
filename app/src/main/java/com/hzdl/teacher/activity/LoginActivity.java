package com.hzdl.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hzdl.teacher.R;
import com.hzdl.teacher.base.BaseActivity;
import com.hzdl.teacher.base.Constant;
import com.hzdl.teacher.bean.LoginBean;
import com.hzdl.teacher.net.ITeacher;
import com.hzdl.teacher.utils.Md5Util;
import com.hzdl.teacher.utils.Utils;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {

        usernameEditText.setText("13700000001");
        passwordEditText.setText("123456");
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
                netLogin();
                //Utils.showScreen(this);
                break;
        }
    }
}


