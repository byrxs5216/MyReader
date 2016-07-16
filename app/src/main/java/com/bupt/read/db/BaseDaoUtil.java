package com.bupt.read.db;

import com.bupt.greendao.DaoSession;

import java.util.ArrayList;

/**
 * Created by xs on 2016/6/25.
 */
public abstract class BaseDaoUtil<T> {

    protected static DaoSession mDaoSession; //全局共用一个

    public abstract void clearAllCache();

    public abstract ArrayList<T> getCacheByPage(int page);

    public abstract void addToDb(String result, int page);


}
