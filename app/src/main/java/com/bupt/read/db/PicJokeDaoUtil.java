package com.bupt.read.db;

import android.content.Context;

import com.bupt.greendao.TablePicJokes;
import com.bupt.greendao.TablePicJokesDao;
import com.bupt.read.Config.ReadApplication;
import com.bupt.read.model.entity.AnimationJokeItem;
import com.bupt.read.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by xs on 2016/6/25.
 */
public class PicJokeDaoUtil extends BaseDaoUtil {
    private static PicJokeDaoUtil instance;
    private static TablePicJokesDao mPicJokesDao;

    private PicJokeDaoUtil(){

    }

    public static PicJokeDaoUtil getInstance(Context context){
        if(instance == null){
            synchronized (BeatyDaoUtil.class){
                if(instance == null){
                    instance = new PicJokeDaoUtil();
                }
            }

            mDaoSession = ReadApplication.getDaoSession(context);
            mPicJokesDao = mDaoSession.getTablePicJokesDao();
        }

        return  instance;

    }
    @Override
    public void clearAllCache() {
        mPicJokesDao.deleteAll();

    }

    @Override
    public ArrayList getCacheByPage(int page) {
        QueryBuilder<TablePicJokes> query = mPicJokesDao.queryBuilder().where(TablePicJokesDao
                .Properties.Page.eq("" + page));

        return query.list().size()>0 ? (ArrayList<AnimationJokeItem>) JsonUtils.toObject(query.list().get(0).getResult(),
                new TypeToken<ArrayList<AnimationJokeItem>>() {
                }.getType()) : null;
    }
    //添加缓存
    @Override
    public void addToDb(String result, int page) {
        TablePicJokes tablePicJokes = new TablePicJokes();
        tablePicJokes.setResult(result);
        tablePicJokes.setPage(page);
        tablePicJokes.setTime(System.currentTimeMillis());
        mPicJokesDao.insert(tablePicJokes);

    }

}
