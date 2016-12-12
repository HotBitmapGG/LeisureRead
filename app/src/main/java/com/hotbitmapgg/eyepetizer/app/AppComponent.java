package com.hotbitmapgg.eyepetizer.app;

import android.content.Context;

import com.hotbitmapgg.eyepetizer.EyepetizerDailyApp;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hcc on 2016/12/11 14:56
 * 100332338@qq.com
 * EyepetizerDaily
 *
 * @HotBitmapGG
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent
{

    Context context();

    void inject(EyepetizerDailyApp mApplication);
}
