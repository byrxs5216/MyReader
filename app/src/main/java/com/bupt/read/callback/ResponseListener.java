package com.bupt.read.callback;

/**
 * Created by xs on 2016/6/7.
 */
public interface ResponseListener<T> {
    void beforeRequest();
    void responseError(String msg);
    void responseComplete();
    void responseSuccess(T data);
}
