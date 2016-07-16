package com.bupt.read.interactor;

import com.bupt.read.callback.ResponseListener;

import rx.Subscription;

/**
 * Created by xs on 2016/6/22.
 */
public interface BeautyPicListInteractor<T> {
    public Subscription getBeautyPicData(ResponseListener<T> callback,String newstype,String key,int num,String word,final int page);
}
