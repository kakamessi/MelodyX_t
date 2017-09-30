package com.hzdl.teacher.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzdl.teacher.R;

/**
 */
public class UpdateFragment extends Fragment {

    public static String TABLAYOUT_FRAGMENT = "tab_fragment";
    private TextView tv_feedback;
    private int type;

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

        tv_feedback = (TextView) view.findViewById(R.id.tv_feedback);




    }

}
