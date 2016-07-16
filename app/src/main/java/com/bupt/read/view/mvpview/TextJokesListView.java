package com.bupt.read.view.mvpview;

import com.bupt.read.model.entity.TextJokeItem;

import java.util.List;

/**
 * Created by xs on 2016/6/21.
 */
public interface TextJokesListView extends MvpView{
    void updateTextJokeDatas(List<TextJokeItem> jokedata,int load_status);
}
