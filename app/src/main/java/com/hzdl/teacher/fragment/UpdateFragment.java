package com.hzdl.teacher.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.allenliu.versionchecklib.core.AllenChecker;
import com.allenliu.versionchecklib.core.VersionParams;
import com.hzdl.teacher.R;
import com.hzdl.teacher.base.Constant;
import com.hzdl.teacher.service.UpdateService;
import com.hzdl.teacher.utils.Utils;

/**
 */
public class UpdateFragment extends Fragment {

    public static String TABLAYOUT_FRAGMENT = "tab_fragment";
    private TextView tv;
    private int type;
    private Button button;

    public static UpdateFragment newInstance(int type) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABLAYOUT_FRAGMENT, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = (int) getArguments().getSerializable(TABLAYOUT_FRAGMENT);
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_update_layout, container, false);
        initView(view);

        return view;
    }


    protected void initView(View view) {

        tv = (TextView) view.findViewById(R.id.tv);
        button = (Button) view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VersionParams.Builder builder = new VersionParams.Builder()
                        .setRequestUrl(Constant.URL_ROOT + Constant.URL_CHECK_UPDATE)
                        .setService(UpdateService.class);
                AllenChecker.startVersionCheck(getActivity(), builder.build());

            }
        });

        try {
            tv.setText("当前版本：V" + Utils.getVersionName(getActivity()));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
