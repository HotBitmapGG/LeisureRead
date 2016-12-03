package com.hotbitmapgg.eyepetizer.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by 11 on 2016/3/31.
 */
public class RxZhihuApp extends Application
{

    public static Context mAppContext;

    public static String BMBO_KEY = "b0d3e44679384c31dd28855c08a7520d";

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
