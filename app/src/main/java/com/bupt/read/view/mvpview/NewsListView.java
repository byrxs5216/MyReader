package com.bupt.read.view.mvpview;

import com.bupt.read.model.entity.NewsItem;

import java.util.List;

/**
 * Created by xs on 2016/5/29.
 */
public interface NewsListView extends MvpView {
    void updateNewsList(List<NewsItem> newsdata,int load_type);
}
