package com.bupt.read.service;

import com.bupt.read.model.entity.NewsResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xs on 2016/5/29.
 */
public interface NewsService {
    @GET("/{newstype}/")
    Observable<NewsResponse> getNewsList(@Path("newstype") String newstype, @Query("key") String key,
                                         @Query("num") int num, @Query("page") int page);
}

//    @GET("/{datatype}")
//    Observable<NewsItem> getNewsList(@Path("datatype") String datatype,
//                                         @Query("showapi_appid") String showapi_appid,
//                                         @Query("showapi_sign") String showapi_sign,
//                                         @Query("channelId") String channelId,
//                                         @Query("needHtml") String needHtml,
//                                         @Query("needContent") String needContent ,
//                                         @Query("page") int page);
//}
