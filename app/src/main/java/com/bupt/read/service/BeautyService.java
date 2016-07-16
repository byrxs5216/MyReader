package com.bupt.read.service;

import com.bupt.read.model.entity.BeatyPicResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xs on 2016/6/22.
 */
public interface BeautyService {
    @GET("/{meinv}/")
    Observable<BeatyPicResponse> getBeautyPicList(@Path("meinv") String newstype,@Query("key") String key,
                                         @Query("num") int num,@Query("word") String word,@Query("page") int page);
}
