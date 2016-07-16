package com.bupt.read.interactor;
import com.bupt.read.callback.ResponseListener;

import rx.Subscription;

/**
 * Created by xs on 2016/6/19.
 */
public interface JokeListInteractor<T> {
    Subscription getAnimaJokesData(ResponseListener<T> callback,String joketype,
                               String showapi_appid,
                              String showapi_sign,
                               int page);

    Subscription getTextJokesData(ResponseListener<T> callback,String joketype,
                                  String showapi_appid,
                                  String showapi_sign,
                                  String time,
                                  int page);
}
