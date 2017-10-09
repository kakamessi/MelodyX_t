package com.hzdl.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hzdl.mex.socket.teacher.TeacherClient;
import com.hzdl.teacher.R;
import com.hzdl.teacher.base.BaseMidiActivity;
import com.hzdl.teacher.downloadcourse.DownloadActivity;
import com.hzdl.teacher.utils.Utils;

public class MainActivity extends BaseMidiActivity {


    TextView tv_test,tv_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMidi();

        tv_test = (TextView) findViewById(R.id.tv_test);
        tv_down = (TextView) findViewById(R.id.tv_down);

        tv_test.setText("课程选择 " + Utils.getLocalIp(this));
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,CourseChoseActivity.class);
                startActivity(intent);

                TeacherClient.getInstance().sendMsgToAll("3|1|1&".getBytes());
                Toast.makeText(MainActivity.this,TeacherClient.getInstance().tRunner.getSocketList().size()+"",0).show();

            }
        });

        tv_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);

            }
        });

    }

    int i=0;
    @Override
    protected void handleMsg(Message msg) {
        tv_test.setText(i++ + "");
    }



}
