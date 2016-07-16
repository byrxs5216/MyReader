package com.bupt.read.db;

import android.content.Context;

import com.bupt.greendao.TableAnimaJokes;
import com.bupt.greendao.TableAnimaJokesDao;
import com.bupt.read.Config.ReadApplication;
import com.bupt.read.model.entity.AnimationJokeItem;
import com.bupt.read.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by xs on 2016/6/25.
 */
public class AnimaJokeDaoUtil extends BaseDaoUtil {
    private static AnimaJokeDaoUtil instance;
    private static TableAnimaJokesDao mAnimaJokeDao;

    private AnimaJokeDaoUtil(){

    }

    public static AnimaJokeDaoUtil getInstance(Context context){
        if(instance == null){
            synchronized (BeatyDaoUtil.class){
                if(instance == null){
                    instance = new AnimaJokeDaoUtil();
                }
            }

            mDaoSession = ReadApplication.getDaoSession(context);
            mAnimaJokeDao = mDaoSession.getTableAnimaJokesDao();
        }

        return  instance;

    }
    @Override
    public void clearAllCache() {
        mAnimaJokeDao.deleteAll();

    }

    @Override
    public ArrayList getCacheByPage(int page) {
        QueryBuilder<TableAnimaJokes> query = mAnimaJokeDao.queryBuilder().where(TableAnimaJokesDao
                .Properties.Page.eq("" + page));

        return query.list().size()>0 ? (ArrayList<AnimationJokeItem>) JsonUtils.toObject(query.list().get(0).getResult(),
                new TypeToken<ArrayList<AnimationJokeItem>>() {
                }.getType()) : null;
    }
    //添加缓存
    @Override
    public void addToDb(String result, int page) {
        TableAnimaJokes tableAnimaJokes = new TableAnimaJokes();
        tableAnimaJokes.setResult(result);
        tableAnimaJokes.setPage(page);
        tableAnimaJokes.setTime(System.currentTimeMillis());
        mAnimaJokeDao.insert(tableAnimaJokes);

    }

}
