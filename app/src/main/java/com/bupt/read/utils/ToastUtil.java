package com.bupt.read.utils;

import android.content.Context;
import android.widget.Toast;

import com.bupt.read.Config.ReadApplication;

/**
 * Created by xs on 2016/5/29.
 */
public class ToastUtil {
    public static void toast(Context context , String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void Short(String msg) {
        Toast.makeText(ReadApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void Long(String msg) {
        Toast.makeText(ReadApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
