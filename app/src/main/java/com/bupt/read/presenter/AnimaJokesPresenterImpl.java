package com.bupt.read.presenter;

import android.content.Context;

import com.bupt.read.Config.LoadType;
import com.bupt.read.base.BasePresenter;
import com.bupt.read.interactor.AnimaJokeListInteractorImpl;
import com.bupt.read.interactor.JokeListInteractor;
import com.bupt.read.model.entity.AnimationJokeItem;
import com.bupt.read.utils.LogUtil;
import com.bupt.read.view.mvpview.AnimaJokesListView;

import java.util.List;

/**
 * Created by xs on 2016/6/7.
 */
public class AnimaJokesPresenterImpl extends BasePresenter<AnimaJokesListView,List<AnimationJokeItem>> implements JokesPresenter {

    private JokeListInteractor<List<AnimationJokeItem>> mJokesInteractor;
    private String joketype;
    private String showapi_appid;
    private String showapi_sign;
    private int page;

    private boolean mIsRefresh = true;
    private boolean mHasInit;

    public AnimaJokesPresenterImpl(Context context, String joketype, String showapi_appid, String showapi_sign, int page){

        LogUtil.i("请求参数初始化");
        mJokesInteractor =  new AnimaJokeListInteractorImpl(context);
        this.joketype = joketype;
        this.showapi_appid = showapi_appid;
        this.showapi_sign = showapi_sign;
        this.page = page;

    }

    @Override
    public void attachView(AnimaJokesListView view) {
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
    public void responseSuccess(List<AnimationJokeItem> data) {
        LogUtil.i("请求成功");
        mHasInit = true;
        if(data != null){
            page += 1;
        }
        LogUtil.i("page: "+page);
        getMvpView().updateAnimaJokeDatas(data,mIsRefresh ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS);

    }

    @Override
    public void responseError(String msg) {
        super.responseError(msg);
        getMvpView().updateAnimaJokeDatas(null,mIsRefresh ? LoadType.TYPE_REFRESH_FAIL : LoadType.TYPE_LOAD_MORE_FAIL);
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }

    @Override
    public AnimaJokesListView getMvpView() {
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
        mSubscription = mJokesInteractor.getAnimaJokesData(this, joketype, showapi_appid, showapi_sign, page);

    }

    @Override
    public void loadMoreData() {
        LogUtil.i("加载更多");
        mIsRefresh = false;
        page++;
        LogUtil.i("page: "+page);
        mSubscription = mJokesInteractor.getAnimaJokesData(this, joketype, showapi_appid, showapi_sign, page);

    }



}
