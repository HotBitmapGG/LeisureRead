package com.hotbitmapgg.leisureread.app;

import com.hotbitmapgg.leisureread.LeisureReadApp;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

import android.content.Context;

/**
 * Created by hcc on 2016/12/11 14:46
 * 100332338@qq.com
 * EyepetizerDaily
 *
 * @HotBitmapGG
 */
@Module
public class AppModule {

  private final LeisureReadApp mApplication;


  public AppModule(LeisureReadApp mApplication) {

    this.mApplication = mApplication;
  }


  @Provides
  @Singleton
  Context provideApplicationContext() {

    return mApplication;
  }
}
