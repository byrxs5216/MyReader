package com.bupt.read.view.mvpview;

import com.bupt.read.model.entity.NewsDetailBody;

/**
 * Created by xs on 2016/6/26.
 */
public interface NewsDetailView extends MvpView {
    void updateNewsDetail(NewsDetailBody body,int load_type);
}
