package com.bupt.read.presenter;

import android.content.Context;

import com.bupt.read.Config.LoadType;
import com.bupt.read.base.BasePresenter;
import com.bupt.read.interactor.NewsDetailInteractor;
import com.bupt.read.interactor.NewsDetailInteractorImpl;
import com.bupt.read.model.entity.NewsDetailBody;
import com.bupt.read.utils.LogUtil;
import com.bupt.read.view.mvpview.NewsDetailView;

import java.util.List;

/**
 * Created by xs on 2016/6/26.
 */
public class NewsDetailPresenterImpl extends BasePresenter<NewsDetailView,NewsDetailBody> implements NewsDetailPresenter{
    private NewsDetailInteractor<NewsDetailBody> mNewsInteractor;
    private String datatype;
    private String showapi_appid;
    private String showapi_sign;
    private String url;

    public NewsDetailPresenterImpl(Context context, String datatype, String showapi_appid, String showapi_sign, String url){
        LogUtil.i("请求参数初始化");
        mNewsInteractor = new NewsDetailInteractorImpl(context);
        this.datatype = datatype;
        this.showapi_appid = showapi_appid;
        this.showapi_sign = showapi_sign;
        this.url = url;
    }

    @Override
    public void attachView(NewsDetailView view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        if (mSubscription != null)
            mSubscription.unsubscribe();
    }

    @Override
    public void beforeRequest() {

            getMvpView().showLoading();

    }

    @Override
    public void responseSuccess(NewsDetailBody body) {
        LogUtil.i("响应成功回调..");
        if(body != null){
            LogUtil.i("请求数据不为空..");
            LogUtil.i(body.toString());

        }
        getMvpView().updateNewsDetail(body, LoadType.TYPE_REQUEST_SUCCESS);

    }

    @Override
    public void responseError(String msg) {
        super.responseError(msg);
        getMvpView().updateNewsDetail(null, LoadType.TYPE_REQUEST_FAILED);
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }

    @Override
    public NewsDetailView getMvpView() {
        return super.getMvpView();
    }

    @Override
    public void checkViewAttached() {
        super.checkViewAttached();
    }


    @Override
    public void requestDetailInfo() {
        mSubscription = mNewsInteractor.getNewsDetailInfo(this,datatype,showapi_appid,showapi_sign,url);
    }
}
