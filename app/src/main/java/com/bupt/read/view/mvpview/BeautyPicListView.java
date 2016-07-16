package com.bupt.read.view.mvpview;

import com.bupt.read.model.entity.BeautyPicItem;

import java.util.List;

/**
 * Created by xs on 2016/6/22.
 */
public interface BeautyPicListView extends MvpView {
    void updateBeautyPicList(List<BeautyPicItem> beautyPics,int load_status);
}
