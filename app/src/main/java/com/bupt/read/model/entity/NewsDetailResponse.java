package com.bupt.read.model.entity;

import java.io.Serializable;

/**
 * Created by xs on 2016/6/26.
 */
public class NewsDetailResponse extends BaseShowApiResponse implements Serializable {
   private NewsDetailBody showapi_res_body;

    @Override
    public String toString() {
        return "NewsDetailResponse{" +
                "showapi_res_body=" + showapi_res_body +
                '}';
    }

    public NewsDetailBody getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(NewsDetailBody showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }
}
