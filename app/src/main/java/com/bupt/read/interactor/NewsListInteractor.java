package com.bupt.read.interactor;

import com.bupt.read.callback.ResponseListener;

import rx.Subscription;

/**
 * Created by xs on 2016/6/16.
 */
public interface NewsListInteractor<T> {
    public Subscription getNewsData(ResponseListener<T> callback,String newstype,String key,int num,int page);
}
