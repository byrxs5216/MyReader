package com.bupt.read.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by xs on 2016/6/16.
 */
public class JokeIndicatorAdapter extends FragmentStatePagerAdapter {
    private static final String[] TITLE = new String[] { "动态搞笑", "图片笑话", "文本笑话"
             };
    private List<Fragment> list;
    public JokeIndicatorAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {


        return list.get(position);
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
