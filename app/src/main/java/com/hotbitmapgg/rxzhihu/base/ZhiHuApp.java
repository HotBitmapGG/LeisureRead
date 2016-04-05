package com.hotbitmapgg.rxzhihu.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by 11 on 2016/3/31.
 */
public class ZhiHuApp extends Application
{
    public static Context mAppContext;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mAppContext = this;
    }


    public static Context getContext()
    {
        return mAppContext;
    }

}
