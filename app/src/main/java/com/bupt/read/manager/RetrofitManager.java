package com.bupt.read.manager;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xs on 2016/5/28.
 */
public class RetrofitManager {
    private static Retrofit singleton;
    private static Retrofit singleton1;
    private static Retrofit singleton2;
    private static Retrofit singleton3;
    private static Retrofit singleton4;

    public static <T> T createNewsServiceIml(Context context, Class<T> clazz,String url) {
        if (singleton == null) {
            synchronized (RetrofitManager.class) {
                if (singleton == null) {
                    Retrofit.Builder builder = new Retrofit.Builder();
                    builder.baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())//设置远程地址
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
                    singleton = builder.build();
                }
            }
        }
        return singleton.create(clazz);
    }

    public static <T> T createJokesServiceIml(Context context, Class<T> clazz,String url) {
        if (singleton1 == null) {
            synchronized (RetrofitManager.class) {
                if (singleton1 == null) {
                    Retrofit.Builder builder = new Retrofit.Builder();
                    builder.baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())//设置远程地址
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
                    singleton1 = builder.build();
                }
            }
        }
        return singleton1.create(clazz);
    }

    public static <T> T createTextJokesServiceIml(Context context, Class<T> clazz,String url) {
        if (singleton2 == null) {
            synchronized (RetrofitManager.class) {
                if (singleton2 == null) {
                    Retrofit.Builder builder = new Retrofit.Builder();
                    builder.baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())//设置远程地址
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
                    singleton2 = builder.build();
                }
            }
        }
        return singleton2.create(clazz);
    }

    public static <T> T createBeautyPicServiceIml(Context context, Class<T> clazz,String url) {
        if (singleton3 == null) {
            synchronized (RetrofitManager.class) {
                if (singleton3 == null) {
                    Retrofit.Builder builder = new Retrofit.Builder();
                    builder.baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())//设置远程地址
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
                    singleton3 = builder.build();
                }
            }
        }
        return singleton3.create(clazz);
    }

    public static <T> T createNewsDetailServiceIml(Context context, Class<T> clazz,String url) {
        if (singleton4 == null) {
            synchronized (RetrofitManager.class) {
                if (singleton4 == null) {
                    Retrofit.Builder builder = new Retrofit.Builder();
                    builder.baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())//设置远程地址
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
                    singleton4 = builder.build();
                }
            }
        }
        return singleton4.create(clazz);
    }






}
