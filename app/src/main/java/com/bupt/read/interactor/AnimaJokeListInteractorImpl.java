package com.bupt.read.interactor;

import android.content.Context;

import com.bupt.read.Config.Constant;
import com.bupt.read.callback.ResponseListener;
import com.bupt.read.model.entity.AnimaJokeResponse;
import com.bupt.read.model.entity.AnimationJokeItem;
import com.bupt.read.service.AnimationJokeService;
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
 * Created by xs on 2016/6/19.
 */
public class AnimaJokeListInteractorImpl implements JokeListInteractor<List<AnimationJokeItem>> {

    private Context context;

    public AnimaJokeListInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public Subscription getAnimaJokesData(final ResponseListener<List<AnimationJokeItem>> callback, String joketype,
                                     String showapi_appid, String showapi_sign, int page) {
        return RetrofitManager.createJokesServiceIml(context, AnimationJokeService.class, Constant.SHOWAPI_JOKE)
                .getAnimaJokeData(joketype, showapi_appid, showapi_sign,page)
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
                .map(new Func1<AnimaJokeResponse, List<AnimationJokeItem>>() {
                    @Override
                    public List<AnimationJokeItem> call(AnimaJokeResponse response) {
                        LogUtil.i("调用call");
                        //LogUtil.i(response.toString());
                       // LogUtil.i(response.getShowapi_res_body());

                        return response.getShowapi_res_body().getContentlist();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AnimationJokeItem>>() {
                    @Override
                    public void onCompleted() {
                        callback.responseComplete();

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.responseError(e.getMessage());

                    }

                    @Override
                    public void onNext(List<AnimationJokeItem> beanList) {
                        callback.responseSuccess(beanList);
                        LogUtil.i("获取到数据");
                        LogUtil.i(beanList.toString());


                    }
                });

    }

    @Override
    public Subscription getTextJokesData(ResponseListener<List<AnimationJokeItem>> callback, String joketype, String showapi_appid, String showapi_sign, String time, int page) {
        return null;
    }


}
