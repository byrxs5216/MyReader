package com.bupt.read.service;

import com.bupt.read.model.entity.TextJokeResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xs on 2016/6/21.
 */
public interface TextJokeService {
    @GET("/{joketype}")
    Observable<TextJokeResponse> getTextJokeData(@Path("joketype") String joketype,
                                                 @Query("showapi_appid") String showapi_appid,
                                                 @Query("showapi_sign") String showapi_sign,
                                                 @Query("time") String time,
                                                 @Query("page") int page);
}
