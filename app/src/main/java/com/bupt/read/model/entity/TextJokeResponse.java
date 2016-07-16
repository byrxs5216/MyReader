package com.bupt.read.model.entity;

/**
 * Created by xs on 2016/6/21.
 */
public class TextJokeResponse extends BaseShowApiResponse {
    private TextBody showapi_res_body;

    @Override
    public String toString() {
        return "TextJokeResponse{" +
                "showapi_res_body=" + showapi_res_body +
                '}';
    }

    public TextBody getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(TextBody showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }
}
