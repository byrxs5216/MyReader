package com.bupt.read.model.entity;

import java.util.List;

/**
 * Created by xs on 2016/6/20.
 */
public class AnimaBody extends BaseBody {
    private List<AnimationJokeItem> contentlist;

    @Override
    public String toString() {
        return "AnimaBody{" +
                "contentlist=" + contentlist +
                '}';
    }

    public List<AnimationJokeItem> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<AnimationJokeItem> contentlist) {
        this.contentlist = contentlist;
    }
}
