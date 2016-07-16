package com.bupt.read.interactor;

import android.content.Context;

import com.bupt.read.Config.Constant;
import com.bupt.read.callback.ResponseListener;
import com.bupt.read.model.entity.NewsItem;
import com.bupt.read.model.entity.NewsResponse;
import com.bupt.read.service.NewsService;
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
 * Created by xs on 2016/6/16.
 */
public class NewsListInteractorImpl implements NewsListInteractor<List<NewsItem>>{
    private Context context;

    public NewsListInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public Subscription getNewsData(final ResponseListener callback,String newstype,String key,int num,int page) {
        //checkViewAttached();
       return RetrofitManager.createNewsServiceIml(context, NewsService.class, Constant.BASE_URL).
               getNewsList(newstype, key,num, page)
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
                .map(new Func1<NewsResponse, List<NewsItem>>() {
                    @Override
                    public List<NewsItem> call(NewsResponse response) {
                        LogUtil.i("调用call");
                        return response.getNewslist();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewsItem>>() {
                    @Override
                    public void onCompleted() {
                        callback.responseComplete();

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.responseError(e.getMessage());


                    }

                    @Override
                    public void onNext(List<NewsItem> beanList) {
                        LogUtil.i("onnext..");
                        callback.responseSuccess(beanList);

                        //LogUtil.i(beanList.toString());

                    }
                });

    }
}
