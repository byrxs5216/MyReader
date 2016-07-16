package com.bupt.read.presenter;

import com.bupt.read.view.mvpview.MvpView;

/**
 * Created by xs on 2016/5/28.
 */
public interface Presenter <V extends MvpView>{
    void attachView(V view);
    void detachView();
}
