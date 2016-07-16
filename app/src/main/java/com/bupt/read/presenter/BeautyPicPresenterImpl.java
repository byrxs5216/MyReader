package com.bupt.read.presenter;

import android.content.Context;

import com.bupt.read.Config.LoadType;
import com.bupt.read.base.BasePresenter;
import com.bupt.read.interactor.BeautyPicListInteractor;
import com.bupt.read.interactor.BeautyPicListInteractorImpl;
import com.bupt.read.model.entity.BeautyPicItem;
import com.bupt.read.utils.LogUtil;
import com.bupt.read.view.mvpview.BeautyPicListView;

import java.util.List;

/**
 * Created by xs on 2016/6/22.
 */
public class BeautyPicPresenterImpl extends BasePresenter<BeautyPicListView,List<BeautyPicItem>> implements NewsPresenter {

    private BeautyPicListInteractor<List<BeautyPicItem>> mBeautyInteractor;
    private String newsType;
    private String key;
    private int num;
    private int page;
    private String word;

    private boolean mIsRefresh = true;
    private boolean mHasInit;

    public BeautyPicPresenterImpl(Context context,String newsType,String key,int num,String word,int page){
        // mNewsService = RetrofitManager.createServiceIml(context,NewsService.class, Constant.BASE_URL);
        LogUtil.i("请求参数初始化");
        mBeautyInteractor = new BeautyPicListInteractorImpl(context);
        this.newsType = newsType;
        this.key = key;
        this.num = num;
        this.page = page;
        this.word = word;
        //mSubscription = mNewsInteractor.getNewsData(this,newsType,key,num,page);
    }

    @Override
    public void attachView(BeautyPicListView view) {
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
    public void responseSuccess(List<BeautyPicItem> data) {
        LogUtil.i("请求成功");
        mHasInit = true;
        if(data != null){
            page++;
        }
        getMvpView().updateBeautyPicList(data,mIsRefresh ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS);

    }

    @Override
    public void responseError(String msg) {
        super.responseError(msg);
        getMvpView().updateBeautyPicList(null,mIsRefresh ? LoadType.TYPE_REFRESH_FAIL : LoadType.TYPE_LOAD_MORE_FAIL);
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }

    @Override
    public BeautyPicListView getMvpView() {
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
        mSubscription = mBeautyInteractor.getBeautyPicData(this, newsType, key, num,word, page);

    }

    @Override
    public void loadMoreData() {
        LogUtil.i("加载更多");
        mIsRefresh = false;
        mSubscription = mBeautyInteractor.getBeautyPicData(this, newsType, key, num, word, page);

    }
}
