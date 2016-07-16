package com.bupt.read.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xs on 2016/6/22.
 */
public class BeatyPicResponse implements Serializable {
    private int code; //返回码
    private String msg; //状态
    private List<BeautyPicItem> newslist;

    @Override
    public String toString() {
        return "BeatyPicResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", newslist=" + newslist +
                '}';
    }

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

    public List<BeautyPicItem> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<BeautyPicItem> newslist) {
        this.newslist = newslist;
    }
}
