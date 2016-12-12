package com.hotbitmapgg.eyepetizer.app;

import android.content.Context;

import com.hotbitmapgg.eyepetizer.EyepetizerDailyApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hcc on 2016/12/11 14:46
 * 100332338@qq.com
 * EyepetizerDaily
 *
 * @HotBitmapGG
 */
@Module
public class AppModule
{

    private final EyepetizerDailyApp mApplication;

    public AppModule(EyepetizerDailyApp mApplication)
    {

        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    Context provideApplicationContext()
    {

        return mApplication;
    }
}
