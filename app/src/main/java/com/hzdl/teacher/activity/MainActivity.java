package com.hzdl.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.hzdl.teacher.R;
import com.hzdl.teacher.base.App;
import com.hzdl.teacher.base.BaseMidiActivity;
import com.hzdl.teacher.base.Constant;
import com.hzdl.teacher.bean.lesson.CrouseListBean1031;
import com.hzdl.teacher.bean.lesson.LessonInfo;
import com.hzdl.teacher.bean.lesson.SimpleGroup;
import com.hzdl.teacher.bean.lesson.SimpleSection;
import com.hzdl.teacher.core.ActionBean;
import com.hzdl.teacher.core.ActionProtocol;
import com.hzdl.teacher.core.ActionResolver;
import com.hzdl.teacher.downloadcourse.DownloadActivity;
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

public class MainActivity extends BaseMidiActivity {


    @BindView(R.id.iv_start)
    ImageView ivStart;
    @BindView(R.id.iv_exit)
    ImageView ivExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initMidi();
        initView();

    }

    private void initView() {

        Utils.setOnFocusBG(ivStart, R.drawable.shape_strock, -1);
        Utils.setOnFocusBG(ivExit, R.drawable.shape_strock, -1);

    }

    int i = 0;

    @Override
    protected void handleMsg(Message msg) {
        ActionBean ab = ActionResolver.getInstance().resolve((String) msg.obj);
        int c1 = Integer.parseInt(ab.getCodes()[0]);
        if (c1 == ActionProtocol.CODE_ACTION_CONNECTED) {

            return;
        }

    }

    private void netLessonKit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ITeacher userBiz = retrofit.create(ITeacher.class);
        Call<CrouseListBean1031> call = userBiz.getCrouseList();
        call.enqueue(new Callback<CrouseListBean1031>() {
            @Override
            public void onResponse(Call<CrouseListBean1031> call, Response<CrouseListBean1031> response) {
                if (response == null || response.body() == null)
                    return;
                if (200 == response.body().getCode()) {

                    CrouseListBean1031 clb = response.body();

                    ArrayList<LessonInfo> data = new ArrayList<LessonInfo>();
                    fillData(data, clb);
                    App.getApplication().setLi(data);

                    Intent intent = new Intent(MainActivity.this, CourseChoseActivity.class);
                    startActivity(intent);

                } else {

                }

                hideLoadingDialog();
            }

            @Override
            public void onFailure(Call<CrouseListBean1031> call, Throwable t) {
                hideLoadingDialog();
            }

        });
    }

    private void fillData(ArrayList<LessonInfo> data, CrouseListBean1031 clb) {

        ArrayList<LessonInfo> list = data;

        for (CrouseListBean1031.DetailLoginBean dlb : clb.getDetail()) {
            LessonInfo li = new LessonInfo();

            if (dlb.getChildrenPart() != null) {
                ArrayList<SimpleGroup> listSG = new ArrayList<SimpleGroup>();

                //小节
                for (CrouseListBean1031.DetailLoginBean.ChildrenPartLoginBeanX cpb : dlb.getChildrenPart()) {

                    SimpleGroup sg = new SimpleGroup();
                    sg.setName(cpb.getName());

                    if (cpb.getChildrenPart() != null) {

                        //单元
                        ArrayList<SimpleSection> listSS = new ArrayList<SimpleSection>();
                        for (CrouseListBean1031.DetailLoginBean.ChildrenPartLoginBeanX.ChildrenPartLoginBean cpbb : cpb.getChildrenPart()) {
                            SimpleSection ss = new SimpleSection();
                            ss.setType(cpbb.getType());
                            ss.setShowName(cpbb.getName());
                            ss.setSourceName(cpbb.getSourceName());
                            ss.setSyncScreen(cpbb.getIs_screen());
                            ss.setLightCode(cpbb.getIs_light());
                            listSS.add(ss);
                        }
                        sg.setList(listSS);
                    }

                    listSG.add(sg);
                }
                li.setGroupList(listSG);
                li.setName(dlb.getName());
            }

            list.add(li);
        }
    }

    @OnClick({R.id.iv_start, R.id.iv_exit, R.id.iv_my,R.id.iv_down})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_start:

                showLoadingDialog();
                netLessonKit();

                break;

            case R.id.iv_exit:
                this.finish();

                break;

            case R.id.iv_down:
                Intent in = new Intent(MainActivity.this, DownloadActivity.class);
                startActivity(in);

                break;

            case R.id.iv_my:
                Intent inp = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(inp);
                break;

        }
    }

}
