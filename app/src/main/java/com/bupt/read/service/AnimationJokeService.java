package com.bupt.read.service;

import com.bupt.read.model.entity.AnimaJokeResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xs on 2016/6/7.
 */
public interface AnimationJokeService {

    /**
     * http://route.showapi.com/341-3?showapi_appid=20017&showapi_sign=0d7e782620224968b3261e57bb9e2a24&page=1
     * @param joketype
     * @param showapi_appid
     * @param showapi_sign
     * @param page
     * @return
     */

    @GET("/{joketype}")
    Observable<AnimaJokeResponse> getAnimaJokeData(@Path("joketype") String joketype,
                                                                         @Query("showapi_appid") String showapi_appid,
                                                                         @Query("showapi_sign") String showapi_sign,
                                                                         @Query("page") int page
                                                                         );

}
