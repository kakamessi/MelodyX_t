package com.hzdl.teacher.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hzdl.mex.socket.teacher.TeacherClient;
import com.hzdl.teacher.R;
import com.hzdl.teacher.base.BaseMidiActivity;
import com.hzdl.teacher.base.Constant;
import com.hzdl.teacher.net.ITeacher;
import com.hzdl.teacher.utils.Utils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseMidiActivity {


    TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMidi();

        tv_test = (TextView) findViewById(R.id.tv_test);
        tv_test.setText(Utils.getLocalIp(this));
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,CourseChoseActivity.class);
                startActivity(intent);

                TeacherClient.getInstance().sendMsgToAll("3|1|1&".getBytes());
                Toast.makeText(MainActivity.this,TeacherClient.getInstance().tRunner.getSocketList().size()+"",0).show();

            }
        });

    }

    int i=0;
    @Override
    protected void handleMsg(Message msg) {
        tv_test.setText(i++ + "");
    }


    private void testRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_TEST_QQ)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ITeacher userBiz = retrofit.create(ITeacher.class);
        Call<ResponseBody> call = userBiz.getTestInfos("125921384","1","1916754934");
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                try {
                    tv_test.setText(new String(response.body().bytes(),"gbk"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                tv_test.setText(t.toString());
            }
        });
    }


}
