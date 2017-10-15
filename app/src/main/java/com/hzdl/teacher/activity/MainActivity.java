package com.hzdl.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.hzdl.mex.socket.teacher.TeacherClient;
import com.hzdl.teacher.R;
import com.hzdl.teacher.base.App;
import com.hzdl.teacher.base.BaseMidiActivity;
import com.hzdl.teacher.base.Constant;
import com.hzdl.teacher.bean.ActionBean;
import com.hzdl.teacher.bean.lesson.CrouseListBean;
import com.hzdl.teacher.bean.lesson.LessonInfo;
import com.hzdl.teacher.core.ActionProtocol;
import com.hzdl.teacher.core.ActionResolver;
import com.hzdl.teacher.net.ITeacher;
import com.hzdl.teacher.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    }

    @OnClick({tv_test, R.id.tv_down, R.id.tv_stop,R.id.tv_open})
    public void onClick(View view) {
        switch (view.getId()) {
            case tv_test:
                Intent intent = new Intent(MainActivity.this, CourseChoseActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_down:

                break;
            case R.id.tv_stop:
                Intent in=new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(in);

                break;
            case R.id.tv_open:


                break;
        }
    }

    private void netLessonKit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ITeacher userBiz = retrofit.create(ITeacher.class);
        Call<CrouseListBean> call = null;
        call.enqueue(new Callback<CrouseListBean>()
        {
            @Override
            public void onResponse(Call<CrouseListBean> call, Response<CrouseListBean> response)
            {
                if(response==null || response.body()==null)
                    return;
                if(200==response.body().getCode()){

                    CrouseListBean clb = null;
                    fillData(clb);
                    Intent intent = new Intent(MainActivity.this, CourseChoseActivity.class);
                    startActivity(intent);

                }else{

                }
            }
            @Override
            public void onFailure(Call<CrouseListBean> call, Throwable t)
            {

            }

        });
    }

    private void fillData(CrouseListBean clb) {
        ArrayList<LessonInfo> list = null;

        for(CrouseListBean.DetailLoginBean dlb : clb.getDetail()){
            LessonInfo li = null;


            for(CrouseListBean.DetailLoginBean.ChildrenPartLoginBeanX cpb : dlb.getChildrenPart()){

                for(CrouseListBean.DetailLoginBean.ChildrenPartLoginBeanX.ChildrenPartLoginBean cpbb : cpb.getChildrenPart()){
                }

            }

            list.add(li);
        }

        App.getApplication().setLi(list);
    }


}
