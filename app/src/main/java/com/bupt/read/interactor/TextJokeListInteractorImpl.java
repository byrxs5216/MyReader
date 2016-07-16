package com.bupt.read.interactor;

import android.content.Context;

import com.bupt.read.Config.Constant;
import com.bupt.read.callback.ResponseListener;
import com.bupt.read.model.entity.TextJokeItem;
import com.bupt.read.model.entity.TextJokeResponse;
import com.bupt.read.service.TextJokeService;
import com.bupt.read.utils.LogUtil;
import com.bupt.read.manager.RetrofitManager;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xs on 2016/6/21.
 */
public class TextJokeListInteractorImpl implements JokeListInteractor<List<TextJokeItem>> {

    private Context context;

    public TextJokeListInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public Subscription getAnimaJokesData(ResponseListener<List<TextJokeItem>> callback, String joketype, String showapi_appid, String showapi_sign, int page) {
        return null;
    }

    @Override
    public Subscription getTextJokesData(final ResponseListener<List<TextJokeItem>> callback, String joketype, String showapi_appid, String showapi_sign, String time, int page) {
        return RetrofitManager.createTextJokesServiceIml(context, TextJokeService.class, Constant.SHOWAPI_JOKE)
                .getTextJokeData(joketype, showapi_appid, showapi_sign, time, page)
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
                    }
                })
                .map(new Func1<TextJokeResponse, List<TextJokeItem>>() {
                    @Override
                    public List<TextJokeItem> call(TextJokeResponse response) {
                        LogUtil.i("调用call");
                        //LogUtil.i(response.toString());
                        // LogUtil.i(response.getShowapi_res_body());

                        return response.getShowapi_res_body().getContentlist();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TextJokeItem>>() {
                    @Override
                    public void onCompleted() {
                        callback.responseComplete();

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.responseError(e.getMessage());

                    }

                    @Override
                    public void onNext(List<TextJokeItem> beanList) {
                        callback.responseSuccess(beanList);
                        LogUtil.i("获取到数据");
                        LogUtil.i(beanList.toString());


                    }
                });
    }
}
