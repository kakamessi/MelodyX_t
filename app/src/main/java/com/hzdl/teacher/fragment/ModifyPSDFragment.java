package com.hzdl.teacher.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hzdl.teacher.R;
import com.hzdl.teacher.activity.LoginActivity;
import com.hzdl.teacher.activity.MainActivity;
import com.hzdl.teacher.base.Constant;
import com.hzdl.teacher.bean.LoginBean;
import com.hzdl.teacher.bean.ModifyPswBean;
import com.hzdl.teacher.net.ITeacher;
import com.hzdl.teacher.utils.Md5Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.os.Build.VERSION_CODES.M;


public class ModifyPSDFragment extends Fragment {

    private EditText et_old,et_new,et_confirm;
    private Button button2;

    private String pswOld,pswNew,pswConfirm;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ModifyPSDFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModifyPSDFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModifyPSDFragment newInstance(String param1, String param2) {
        ModifyPSDFragment fragment = new ModifyPSDFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_modify_psd, container, false);

        et_old = v.findViewById(R.id.et_old);
        et_new = v.findViewById(R.id.et_new);
        et_confirm = v.findViewById(R.id.et_confirm);
        button2 = v.findViewById(R.id.button2);



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pswOld = et_old.getText().toString();
                pswNew = et_new.getText().toString();
                pswConfirm = et_confirm.getText().toString();

                if(!pswOld.equals("") && !pswNew.equals("") && !pswConfirm.equals("") ){

                    check();
                }else{
                    Toast.makeText(getActivity(),"在检查一下哦",0).show();
                }

            }

        });

        return v;
    }

    private void check() {

        if(!pswNew.equals(pswConfirm)){
            Toast.makeText(getActivity(),"两次输入密码不一致",0).show();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ITeacher userBiz = retrofit.create(ITeacher.class);
        Call<ModifyPswBean> call = userBiz.postModifyPsw("",Md5Util.md5(pswOld),Md5Util.md5(pswNew),Md5Util.md5(pswConfirm));
        call.enqueue(new Callback<ModifyPswBean>()
        {
            @Override
            public void onResponse(Call<ModifyPswBean> call, Response<ModifyPswBean> response)
            {
                if(response==null || response.body()==null)
                    return;
                if(200==response.body().getCode()){
                    Toast.makeText(getActivity(),"修改成功",0).show();
                }else{
                    Toast.makeText(getActivity(),"修改失败",0).show();
                }

            }

            @Override
            public void onFailure(Call<ModifyPswBean> call, Throwable t)
            {
                Toast.makeText(getActivity(),"网络出了点问题",0).show();

            }

        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
