package com.bupt.read.presenter;

import android.content.Context;

import com.bupt.read.Config.LoadType;
import com.bupt.read.base.BasePresenter;
import com.bupt.read.interactor.JokeListInteractor;
import com.bupt.read.interactor.TextJokeListInteractorImpl;
import com.bupt.read.model.entity.TextJokeItem;
import com.bupt.read.utils.LogUtil;
import com.bupt.read.view.mvpview.TextJokesListView;

import java.util.List;

/**
 * Created by xs on 2016/6/21.
 */
public class TextJokePresenterImpl extends BasePresenter<TextJokesListView,List<TextJokeItem>> implements JokesPresenter {

    private JokeListInteractor<List<TextJokeItem>> mJokesInteractor;
    private String joketype;
    private String showapi_appid;
    private String showapi_sign;
    private String time;
    private int page;

    private boolean mIsRefresh = true;
    private boolean mHasInit;

    public TextJokePresenterImpl(Context context, String joketype, String showapi_appid, String showapi_sign, String time,int page){

        LogUtil.i("请求参数初始化");
        mJokesInteractor =  new TextJokeListInteractorImpl(context);
        this.joketype = joketype;
        this.showapi_appid = showapi_appid;
        this.showapi_sign = showapi_sign;
        this.time = time;
        this.page = page;

    }

    @Override
    public void attachView(TextJokesListView view) {
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
    public void responseSuccess(List<TextJokeItem> data) {
        LogUtil.i("请求成功");
        mHasInit = true;
        if(data != null){
            page += 1;
        }
        getMvpView().updateTextJokeDatas(data, mIsRefresh ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS);
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }

    @Override
    public TextJokesListView getMvpView() {
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
        mSubscription = mJokesInteractor.getTextJokesData(this, joketype, showapi_appid, showapi_sign,time, page);

    }

    @Override
    public void responseError(String msg) {
        super.responseError(msg);
        getMvpView().updateTextJokeDatas(null,mIsRefresh ? LoadType.TYPE_REFRESH_FAIL : LoadType.TYPE_LOAD_MORE_FAIL);
    }

    @Override
    public void loadMoreData() {
        LogUtil.i("加载更多");
        mIsRefresh = false;
        LogUtil.i("page: "+page);
        mSubscription = mJokesInteractor.getTextJokesData(this, joketype, showapi_appid, showapi_sign, time, page);

    }


}
