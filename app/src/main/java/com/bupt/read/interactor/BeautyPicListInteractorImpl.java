package com.bupt.read.interactor;

import android.content.Context;

import com.bupt.read.Config.Constant;
import com.bupt.read.callback.ResponseListener;
import com.bupt.read.manager.RetrofitManager;
import com.bupt.read.model.entity.BeatyPicResponse;
import com.bupt.read.model.entity.BeautyPicItem;
import com.bupt.read.service.BeautyService;
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
 * Created by xs on 2016/6/22.
 */
public class BeautyPicListInteractorImpl implements BeautyPicListInteractor<List<BeautyPicItem>> {

    private Context context;

    public BeautyPicListInteractorImpl(Context context) {
        this.context = context;
    }
    @Override
    public Subscription getBeautyPicData(final ResponseListener<List<BeautyPicItem>> callback, String newstype, String key, int num,String word, int page) {
        //checkViewAttached();
        return RetrofitManager.createBeautyPicServiceIml(context, BeautyService.class, Constant.BASE_URL).getBeautyPicList(newstype, key, num, word, page)
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
                .map(new Func1<BeatyPicResponse, List<BeautyPicItem>>() {
                    @Override
                    public List<BeautyPicItem> call(BeatyPicResponse response) {
                        LogUtil.i("调用call");
                        return response.getNewslist();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BeautyPicItem>>() {
                    @Override
                    public void onCompleted() {
                        callback.responseComplete();

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.responseError(e.getMessage());


                    }

                    @Override
                    public void onNext(List<BeautyPicItem> beanList) {
                        callback.responseSuccess(beanList);
                        LogUtil.i("获取到数据");
                        LogUtil.i(beanList.toString());


                    }
                });

    }

}
