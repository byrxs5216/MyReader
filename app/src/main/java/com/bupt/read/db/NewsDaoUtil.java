package com.bupt.read.db;

import android.content.Context;

import com.bupt.greendao.TableNews;
import com.bupt.greendao.TableNewsDao;
import com.bupt.read.Config.ReadApplication;
import com.bupt.read.model.entity.NewsItem;
import com.bupt.read.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by xs on 2016/6/25.
 */
public class NewsDaoUtil extends BaseDaoUtil {
    private static NewsDaoUtil instance;
    private static TableNewsDao mNewsDao;

    private NewsDaoUtil(){

    }

    public static NewsDaoUtil getInstance(Context context){
        if(instance == null){
            synchronized (BeatyDaoUtil.class){
                if(instance == null){
                    instance = new NewsDaoUtil();
                }
            }

            mDaoSession = ReadApplication.getDaoSession(context);
            mNewsDao = mDaoSession.getTableNewsDao();
        }

        return  instance;

    }
    @Override
    public void clearAllCache() {
        mNewsDao.deleteAll();

    }

    @Override
    public ArrayList getCacheByPage(int page) {
        QueryBuilder<TableNews> query = mNewsDao.queryBuilder().where(TableNewsDao
                .Properties.Page.eq("" + page));

        return query.list().size()>0 ? (ArrayList<NewsItem>) JsonUtils.toObject(query.list().get(0).getResult(),
                new TypeToken<ArrayList<NewsItem>>() {
                }.getType()) : null;
    }
    //添加缓存
    @Override
    public void addToDb(String result, int page) {
        TableNews tableNews = new TableNews();
        tableNews.setResult(result);
        tableNews.setPage(page);
        tableNews.setTime(System.currentTimeMillis());
        mNewsDao.insert(tableNews);

    }

}
