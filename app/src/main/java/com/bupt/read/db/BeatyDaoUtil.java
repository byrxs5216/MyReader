package com.bupt.read.db;

import android.content.Context;

import com.bupt.greendao.TableBeautyPics;
import com.bupt.greendao.TableBeautyPicsDao;
import com.bupt.read.Config.ReadApplication;
import com.bupt.read.model.entity.BeautyPicItem;
import com.bupt.read.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by xs on 2016/6/25.
 */
public class BeatyDaoUtil extends BaseDaoUtil {
    private static BeatyDaoUtil instance;
    private static TableBeautyPicsDao mBeautyPicsDao;

    private BeatyDaoUtil(){

    }

    public static BeatyDaoUtil getInstance(Context context){
        if(instance == null){
            synchronized (BeatyDaoUtil.class){
                if(instance == null){
                    instance = new BeatyDaoUtil();
                }
            }

            mDaoSession = ReadApplication.getDaoSession(context);
            mBeautyPicsDao = mDaoSession.getTableBeautyPicsDao();
        }

        return  instance;

    }
    @Override
    public void clearAllCache() {
        mBeautyPicsDao.deleteAll();

    }

    @Override
    public ArrayList getCacheByPage(int page) {
        QueryBuilder<com.bupt.greendao.TableBeautyPics> query = mBeautyPicsDao.queryBuilder().where(TableBeautyPicsDao
                .Properties.Page.eq("" + page));

        return query.list().size()>0 ? (ArrayList<BeautyPicItem>) JsonUtils.toObject(query.list().get(0).getResult(),
                new TypeToken<ArrayList<BeautyPicItem>>() {
                }.getType()) : null;
    }
//添加缓存
    @Override
    public void addToDb(String result, int page) {
        TableBeautyPics tableBeautyPics = new TableBeautyPics();
        tableBeautyPics.setResult(result);
        tableBeautyPics.setPage(page);
        tableBeautyPics.setTime(System.currentTimeMillis());
        mBeautyPicsDao.insert(tableBeautyPics);

    }

}
