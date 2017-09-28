package com.hzdl.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.hzdl.teacher.R;
import com.hzdl.teacher.base.BaseActivity;

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


    }


    @Override
    protected void handleMsg(Message msg) {


    }

    public void login(View v){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();

    }


}
