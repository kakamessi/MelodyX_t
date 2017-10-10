package com.hzdl.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.hzdl.mex.socket.teacher.TeacherClient;
import com.hzdl.teacher.R;
import com.hzdl.teacher.base.BaseMidiActivity;
import com.hzdl.teacher.bean.ActionBean;
import com.hzdl.teacher.core.ActionProtocol;
import com.hzdl.teacher.core.ActionResolver;
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


            }
        });

        tv_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
//                startActivity(intent);

                sendSynAction(ActionProtocol.ACTION_COURSE_START);

            }
        });

    }

    int i=0;
    @Override
    protected void handleMsg(Message msg) {
        ActionBean ab = ActionResolver.getInstance().resolve((String) msg.obj);
        int c1 = Integer.parseInt(ab.getCodes()[0]);
        if(c1== ActionProtocol.CODE_ACTION_CONNECTED){
            tv_down.setText("当前连入学生：" + TeacherClient.getInstance().tRunner.getSocketList().size());
            return;
        }

        doAction((String)msg.obj);
    }

    private ActionBean ab;
    private void doAction(String str) {
        ab = ActionResolver.getInstance().resolve(str);

        int c2 = Integer.parseInt(ab.getCodes()[1]);
        int c3 = Integer.parseInt(ab.getCodes()[2]);

        if(c2== ActionProtocol.CODE_ACTION_COURSE){
            if(c3==1){
                Intent intent=new Intent(MainActivity.this,CourseActivity.class);
                startActivity(intent);
            }
        }
    }

}
