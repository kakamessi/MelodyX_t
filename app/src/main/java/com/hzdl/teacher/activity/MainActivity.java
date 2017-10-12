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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hzdl.teacher.R.id.tv_test;

public class MainActivity extends BaseMidiActivity {


    @BindView(tv_test)
    TextView tvTest;
    @BindView(R.id.tv_down)
    TextView tvDown;
    @BindView(R.id.tv_stop)
    TextView tvStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initMidi();

        tvTest.setText("课程选择 " + Utils.getLocalIp(this));
    }

    int i = 0;

    @Override
    protected void handleMsg(Message msg) {
        ActionBean ab = ActionResolver.getInstance().resolve((String) msg.obj);
        int c1 = Integer.parseInt(ab.getCodes()[0]);
        if (c1 == ActionProtocol.CODE_ACTION_CONNECTED) {
            tvDown.setText("当前连入学生：" + TeacherClient.getInstance().tRunner.getSocketList().size());
            return;
        }

        doAction((String) msg.obj);
    }

    private ActionBean ab;

    private void doAction(String str) {
        ab = ActionResolver.getInstance().resolve(str);

        int c2 = Integer.parseInt(ab.getCodes()[1]);
        int c3 = Integer.parseInt(ab.getCodes()[2]);

        if (c2 == ActionProtocol.CODE_ACTION_COURSE) {
            if (c3 == 1) {
                Intent intent = new Intent(MainActivity.this, CourseActivity.class);
                startActivity(intent);
            }
        }
    }

    @OnClick({tv_test, R.id.tv_down, R.id.tv_stop,R.id.tv_open})
    public void onClick(View view) {
        switch (view.getId()) {
            case tv_test:
                Intent intent = new Intent(MainActivity.this, CourseChoseActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_down:

                sendSynAction(ActionProtocol.ACTION_COURSE_START);

                break;
            case R.id.tv_stop:
                Intent in=new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(in);

                break;
            case R.id.tv_open:


                break;
        }
    }


}
