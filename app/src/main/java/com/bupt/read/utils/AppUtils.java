package com.bupt.read.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xs on 2016/5/29.
 */
public class AppUtils {
    /**
     * 根据新闻类型获取上次更新的时间
     *
     * @param newType
     * @return
     */
    public static String getRefreashTime(Context context, int newType)
    {
        String timeStr = (String) SPUtils.get(context, "NEWS_" + newType, "refresh_time");
        if (TextUtils.isEmpty(timeStr))
        {
            return "我好笨，忘记了...";
        }
        return timeStr;
    }


    /**
     * 根据新闻类型设置上次更新的时间
     *
     * @param newType
     * @return
     */
    public static void setRefreashTime(Context context, int newType)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SPUtils.put(context, "NEWS_" + newType, df.format(new Date()));
    }

    public static int getScreenWidth(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getHeight();
    }
}
