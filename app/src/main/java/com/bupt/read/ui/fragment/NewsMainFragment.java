package com.bupt.read.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bupt.read.R;
import com.bupt.read.adapter.TabPageIndicatorAdapter;
import com.bupt.read.base.BaseFragment;
import com.bupt.read.utils.LogUtil;
import com.bupt.read.view.SlidingTabLayout;

/**
 * Created by xs on 2016/5/29.
 */
public class NewsMainFragment extends BaseFragment{
    private SlidingTabLayout tab;
    private FragmentStatePagerAdapter fpAdapter;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.i("onCreateView---NewsMainFragment");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setListener() {
        tab.setOnPageChangeListener(new PageChangeListener());

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        LogUtil.i("init NewsMain View");
        tab = (SlidingTabLayout) rootView.findViewById(R.id.tab);
        viewPager = (ViewPager) rootView.findViewById(R.id.news_viewpager);
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        fpAdapter = new TabPageIndicatorAdapter(getChildFragmentManager());
        viewPager.setAdapter(fpAdapter);
        tab.setViewPager(viewPager);
        viewPager.setCurrentItem(0);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
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
}
