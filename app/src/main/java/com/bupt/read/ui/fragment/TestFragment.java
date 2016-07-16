package com.bupt.read.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.bupt.read.Config.Constant;
import com.bupt.read.Config.LoadType;
import com.bupt.read.R;
import com.bupt.read.base.BaseFragment;
import com.bupt.read.model.entity.NewsDetailBody;
import com.bupt.read.presenter.NewsDetailPresenterImpl;
import com.bupt.read.utils.LogUtil;
import com.bupt.read.view.mvpview.NewsDetailView;

/**
 * Created by xs on 2016/6/26.
 */
public class TestFragment extends BaseFragment implements NewsDetailView{
    private NewsDetailPresenterImpl newsDetailPresenter;
    @Override
    protected void setListener() {

    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        String link = "http://tech.qq.com/a/20160625/020335.htm";
        newsDetailPresenter = new NewsDetailPresenterImpl(context,"883-1", Constant.APP_ID,Constant.APP_SECRET,link);
        newsDetailPresenter.attachView(this);
        newsDetailPresenter.requestDetailInfo();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.test;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void updateNewsDetail(NewsDetailBody body, int load_type) {
        if(load_type == LoadType.TYPE_REQUEST_SUCCESS){
            LogUtil.i(body.getHtml());
        }

    }

    @Override
    public void toast(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
