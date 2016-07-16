package com.bupt.read.model.entity;

import java.io.Serializable;

/**
 * Created by xs on 2016/6/20.
 */
public class BaseShowApiResponse implements Serializable{
    protected int showapi_res_code;
    protected String showapi_res_error;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }
}
