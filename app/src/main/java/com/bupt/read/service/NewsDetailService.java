package com.bupt.read.service;

import com.bupt.read.model.entity.NewsDetailResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xs on 2016/6/26.
 */
public interface NewsDetailService {
        @GET("/{datatype}")
        Observable<NewsDetailResponse> getNewsDetail(@Path("datatype") String datatype,
                                         @Query("showapi_appid") String showapi_appid,
                                         @Query("showapi_sign") String showapi_sign,
                                         @Query("url") String url);
}
