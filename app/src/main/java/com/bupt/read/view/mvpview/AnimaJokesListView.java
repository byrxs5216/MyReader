package com.bupt.read.view.mvpview;

import com.bupt.read.model.entity.AnimationJokeItem;

import java.util.List;

/**
 * Created by xs on 2016/6/7.
 */
public interface AnimaJokesListView extends MvpView{
    void updateAnimaJokeDatas(List<AnimationJokeItem> jokedata,int load_status);
}
