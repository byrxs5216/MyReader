package com.bupt.read.model.entity;

/**
 * Created by xs on 2016/6/20.
 */
public class AnimaJokeResponse extends BaseShowApiResponse {
    private AnimaBody showapi_res_body;

    @Override
    public String toString() {
        return "AnimaJokeResponse{" +
                "showapi_res_body=" + showapi_res_body +
                '}';
    }

    public AnimaBody getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(AnimaBody showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }
}
