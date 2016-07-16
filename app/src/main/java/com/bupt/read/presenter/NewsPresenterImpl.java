package com.bupt.read.presenter;

import android.content.Context;

import com.bupt.read.Config.LoadType;
import com.bupt.read.base.BasePresenter;
import com.bupt.read.interactor.NewsListInteractor;
import com.bupt.read.interactor.NewsListInteractorImpl;
import com.bupt.read.model.entity.NewsItem;
import com.bupt.read.utils.LogUtil;
import com.bupt.read.view.mvpview.NewsListView;

import java.util.List;

/**
 * Created by xs on 2016/5/29.
 */
public class NewsPresenterImpl extends BasePresenter<NewsListView,List<NewsItem>> implements NewsPresenter{
    private NewsListInteractor<List<NewsItem>> mNewsInteractor;
    private String newstype;
    private String key;
    private int num;
    private int page;
    private boolean mIsRefresh = true;
    private boolean mHasInit;

    public NewsPresenterImpl(Context context,String newstype,String key,int num,int page){
        LogUtil.i("请求参数初始化");
        mNewsInteractor = new NewsListInteractorImpl(context);
        this.key = key;
        this.newstype = newstype;
        this.num = num;
        this.page = page;
    }

    @Override
    public void attachView(NewsListView view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        if (mSubscription != null)
            mSubscription.unsubscribe();
    }

    @Override
    public void beforeRequest() {
        if (!mHasInit) {
            getMvpView().showLoading();
        }
    }

    @Override
    public void responseSuccess(List<NewsItem> data) {
        LogUtil.i("响应成功回调..");
        mHasInit = true;
        if(data != null){
            LogUtil.i("请求数据不为空..");
            LogUtil.i(data.toString());
            //page++;
        }
        getMvpView().updateNewsList(data,mIsRefresh ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS);

    }

    @Override
    public void responseError(String msg) {
        super.responseError(msg);
        getMvpView().updateNewsList(null, mIsRefresh ? LoadType.TYPE_REFRESH_FAIL : LoadType.TYPE_LOAD_MORE_FAIL);
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }

    @Override
    public NewsListView getMvpView() {
        return super.getMvpView();
    }

    @Override
    public void checkViewAttached() {
        super.checkViewAttached();
    }


    @Override
    public void refreshData() {
        LogUtil.i("refresh Data....");
        page = 1;
        mIsRefresh = true;
        mSubscription = mNewsInteractor.getNewsData(this,newstype,key,num,page);

    }

    @Override
    public void loadMoreData() {
        page++;
        mIsRefresh = false;
        mSubscription = mNewsInteractor.getNewsData(this,newstype,key,num,page);

    }
}
