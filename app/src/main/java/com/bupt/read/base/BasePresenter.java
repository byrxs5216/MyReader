package com.bupt.read.base;

import com.bupt.read.callback.ResponseListener;
import com.bupt.read.presenter.Presenter;
import com.bupt.read.utils.LogUtil;
import com.bupt.read.view.mvpview.MvpView;

import rx.Subscription;

/**
 * Created by xs on 2016/5/28.
 */
public class BasePresenter<T extends MvpView,V> implements Presenter<T>, ResponseListener<V> {
    protected Subscription mSubscription;
    private T mvpView;
    @Override
    public void attachView(T view) {
        mvpView = view;

    }


    @Override
    public void detachView() {
        mvpView = null;

    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

    public T getMvpView() {
        return mvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    @Override
    public void beforeRequest() {
        getMvpView().showLoading();

    }

    @Override
    public void responseError(String msg) {
        LogUtil.i(msg);
        getMvpView().toast(msg);
        getMvpView().hideLoading();

    }

    @Override
    public void responseComplete() {
        getMvpView().hideLoading();

    }

    @Override
    public void responseSuccess(V data) {

    }


    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
