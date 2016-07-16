package com.bupt.read.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bupt.read.ui.fragment.NewsFragment;


/**
 * Created by xs on 2016/5/29.
 */
public class TabPageIndicatorAdapter extends FragmentStatePagerAdapter{
    /**
     * Tab标题
     */
    private static final String[] TITLE = new String[] { "体育", "娱乐", "科技",
            "健康", "社会", "苹果" , "国际", "国内"};
    private String[] news_type = {"tiyu","huabian","keji","health","social","apple","world","guonei"};

    public TabPageIndicatorAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return NewsFragment.getInstance(news_type[position]);

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position%TITLE.length];
    }

    @Override
    public int getCount() {
        return TITLE.length;
    }
}
