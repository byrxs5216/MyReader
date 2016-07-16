package com.bupt.read.interactor;

import com.bupt.read.callback.ResponseListener;

import rx.Subscription;

/**
 * Created by xs on 2016/6/26.
 */
public interface NewsDetailInteractor<T> {
    public Subscription getNewsDetailInfo(ResponseListener<T> callback, String datatype,
                                          String showapi_appid,
                                          String showapi_sign,
                                          String url);
}
