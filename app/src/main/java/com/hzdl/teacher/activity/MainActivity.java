package com.hzdl.teacher.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hzdl.mex.socket.teacher.TeacherClient;
import com.hzdl.teacher.R;
import com.hzdl.teacher.activity.net.ITeacher;
import com.hzdl.teacher.activity.net.NetConstant;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv_test = (TextView) findViewById(R.id.tv_test);
        tv_test.setText(Utils.getLocalIp(this));

        //testRetrofit();

        final TeacherClient client = new TeacherClient();
        client.start(null);

        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.sendMsgToAll("0|0|0".getBytes());
            }
        });

    }

    private void testRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetConstant.URL_TEST_QQ)
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
