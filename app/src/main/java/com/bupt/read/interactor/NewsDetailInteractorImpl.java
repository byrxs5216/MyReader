package com.bupt.read.interactor;

import android.content.Context;

import com.bupt.read.Config.Constant;
import com.bupt.read.callback.ResponseListener;
import com.bupt.read.manager.RetrofitManager;
import com.bupt.read.model.entity.NewsDetailBody;
import com.bupt.read.model.entity.NewsDetailResponse;
import com.bupt.read.service.NewsDetailService;
import com.bupt.read.utils.LogUtil;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xs on 2016/6/26.
 */
public class NewsDetailInteractorImpl implements NewsDetailInteractor<NewsDetailBody> {
    private Context context;

    public NewsDetailInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public Subscription getNewsDetailInfo(final ResponseListener<NewsDetailBody> callback, String datatype, String showapi_appid,
                                          String showapi_sign, final String url) {
        return RetrofitManager.createNewsDetailServiceIml(context, NewsDetailService.class, Constant.SHOWAPI_JOKE)
                .getNewsDetail(datatype, showapi_appid, showapi_sign, url)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        // 订阅之前回调回去显示加载动画
                        callback.beforeRequest();
                    }
                })
                        //.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.i("错误时处理：" + throwable + " --- " + throwable.getLocalizedMessage());
                        callback.responseError(throwable.getLocalizedMessage());
                    }
                })
                .map(new Func1<NewsDetailResponse, NewsDetailBody>() {
                    @Override
                    public NewsDetailBody call(NewsDetailResponse response) {
                        LogUtil.i("调用call");
                        return response.getShowapi_res_body();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsDetailBody>() {
                    @Override
                    public void onCompleted() {
                        callback.responseComplete();

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.responseError(e.getMessage());

                    }

                    @Override
                    public void onNext(NewsDetailBody newsContent) {
                        callback.responseSuccess(newsContent);
                        LogUtil.i("获取到数据");
                        LogUtil.i(newsContent.toString());


                    }
                });
    }

}
