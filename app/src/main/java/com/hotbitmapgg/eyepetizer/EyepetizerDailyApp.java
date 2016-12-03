package com.hotbitmapgg.eyepetizer;

import android.app.Application;

import com.hotbitmapgg.rxzhihu.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class EyepetizerDailyApp extends Application
{

    public static EyepetizerDailyApp mAppContext;

    public static String BMBO_KEY = "b0d3e44679384c31dd28855c08a7520d";

    @Override
    public void onCreate()
    {

        super.onCreate();
        mAppContext = this;

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lobster-1.4.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }


    public static EyepetizerDailyApp getAppContext()
    {

        return mAppContext;
    }
}
