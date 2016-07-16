package com.bupt.read.db;

import android.content.Context;

import com.bupt.greendao.TableTextJokes;
import com.bupt.greendao.TableTextJokesDao;
import com.bupt.read.Config.ReadApplication;
import com.bupt.read.model.entity.TextJokeItem;
import com.bupt.read.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by xs on 2016/6/25.
 */
public class TextJokeDaoUtil extends BaseDaoUtil {
    private static TextJokeDaoUtil instance;
    private static TableTextJokesDao mTextJokesDao;

    private TextJokeDaoUtil(){

    }

    public static TextJokeDaoUtil getInstance(Context context){
        if(instance == null){
            synchronized (BeatyDaoUtil.class){
                if(instance == null){
                    instance = new TextJokeDaoUtil();
                }
            }

            mDaoSession = ReadApplication.getDaoSession(context);
            mTextJokesDao = mDaoSession.getTableTextJokesDao();
        }

        return  instance;

    }
    @Override
    public void clearAllCache() {
        mTextJokesDao.deleteAll();

    }

    @Override
    public ArrayList getCacheByPage(int page) {
        QueryBuilder<TableTextJokes> query = mTextJokesDao.queryBuilder().where(TableTextJokesDao
                .Properties.Page.eq("" + page));

        return query.list().size()>0 ? (ArrayList<TextJokeItem>) JsonUtils.toObject(query.list().get(0).getResult(),
                new TypeToken<ArrayList<TextJokeItem>>() {
                }.getType()) : null;
    }
    //添加缓存
    @Override
    public void addToDb(String result, int page) {
        TableTextJokes tableTextJokes = new TableTextJokes();
        tableTextJokes.setResult(result);
        tableTextJokes.setPage(page);
        tableTextJokes.setTime(System.currentTimeMillis());
        mTextJokesDao.insert(tableTextJokes);

    }

}
