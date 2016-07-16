package com.bupt.read.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xs on 2016/5/29.
 */
public class NewsResponse implements Serializable {
    private int code; //返回码
    private String msg; //状态
    private List<NewsItem> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewsItem> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewsItem> newslist) {
        this.newslist = newslist;
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", newslist=" + newslist +
                '}';
    }
}
