package com.bupt.read.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bupt.read.R;
import com.bupt.read.adapter.JokeIndicatorAdapter;
import com.bupt.read.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xs on 2016/6/7.
 */
public  class JokeFragment extends BaseFragment{
    private TabLayout tab;
    private FragmentStatePagerAdapter fpAdapter;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setListener() {


    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        tab = (TabLayout) rootView.findViewById(R.id.tab_joke);
        viewPager = (ViewPager) rootView.findViewById(R.id.jokes_viewpager);
        fragmentList = new ArrayList<Fragment>();
        AnimationJokeFragment animationJokeFragment = new AnimationJokeFragment();
        PicJokeFragment picJokeFragment = new PicJokeFragment();
        TextJokeFragment textJokeFragment = new TextJokeFragment();
        fragmentList.add(animationJokeFragment);
        fragmentList.add(picJokeFragment);
        fragmentList.add(textJokeFragment);
        fpAdapter = new JokeIndicatorAdapter(getChildFragmentManager(),fragmentList);
        viewPager.setAdapter(fpAdapter);
        tab.setupWithViewPager(viewPager);
        tab.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setCurrentItem(0);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_jokes;
    }


    @Override
    public void onClick(View v) {

    }



    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //ToastUtil.toast(context,position+"");
            viewPager.setCurrentItem(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

//    private class MyTabSelectedListener implements TabLayout.OnTabSelectedListener {
//        @Override
//        public void onTabSelected(TabLayout.Tab tab) {
//            viewPager.setCurrentItem(tab.getPosition());
//        }
//
//        @Override
//        public void onTabUnselected(TabLayout.Tab tab) {
//
//        }
//
//        @Override
//        public void onTabReselected(TabLayout.Tab tab) {
//
//        }
//    }
}
