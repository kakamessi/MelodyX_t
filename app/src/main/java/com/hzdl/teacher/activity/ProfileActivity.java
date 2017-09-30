package com.hzdl.teacher.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.hzdl.teacher.R;
import com.hzdl.teacher.base.BaseActivity;
import com.hzdl.teacher.fragment.FeedbackFragment;
import com.hzdl.teacher.fragment.ModifyPSDFragment;
import com.hzdl.teacher.fragment.UpdateFragment;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity {

    private TabLayout tab;
    private ViewPager viewpager;
    private TabAdapter adapter;
    public static final String[] tabTitle = new String[]{"问题反馈", "修改密码", "版本更新"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initviews();

    }

    private void initviews() {
        tab = (TabLayout) findViewById(R.id.tab);
        viewpager = (ViewPager) findViewById(R.id.viewpager);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(FeedbackFragment.newInstance("1","2"));
        fragments.add(ModifyPSDFragment.newInstance("1","2"));
        fragments.add(UpdateFragment.newInstance(1));

        adapter = new TabAdapter(getSupportFragmentManager(), fragments);
        //给ViewPager设置适配器
        viewpager.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来。
        tab.setupWithViewPager(viewpager);
        //设置可以滑动
        //tab.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    public class TabAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;


        public TabAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        //设置tablayout标题
        @Override
        public CharSequence getPageTitle(int position) {

            return ProfileActivity.tabTitle[position];


        }

    }

    @Override
    protected void handleMsg(Message msg) {


    }




}
