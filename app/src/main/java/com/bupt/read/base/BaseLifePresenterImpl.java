package com.bupt.read.base;

import com.bupt.read.callback.ResponseListener;
import com.bupt.read.utils.LogUtil;

import rx.Subscription;

/**
 * Created by xs on 2016/6/7.
 */
public class BaseLifePresenterImpl<T extends BaseView,V> implements BaseLifePresenter,ResponseListener<V>{
    protected Subscription mSubscription;
    protected T mView;

    public BaseLifePresenterImpl(T view){
        mView = view;
    }
    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        if(mSubscription != null && !mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }
        mView = null;
    }

    @Override
    public void beforeRequest() {
        mView.showLoadHint();

    }

    @Override
    public void responseError(String msg) {
        LogUtil.i(msg);
        mView.toast(msg);
        mView.hideLoadHint();

    }

    @Override
    public void responseComplete() {
        mView.hideLoadHint();

    }

    @Override
    public void responseSuccess(V data) {


    }
}
