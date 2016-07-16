package com.bupt.read.model.entity;

import java.util.List;

/**
 * Created by xs on 2016/6/21.
 */
public class TextBody extends BaseBody {
    private int ret_code;
    private List<TextJokeItem> contentlist;

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public List<TextJokeItem> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<TextJokeItem> contentlist) {
        this.contentlist = contentlist;
    }
}
