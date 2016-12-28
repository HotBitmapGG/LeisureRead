package com.hotbitmapgg.leisureread.app;

import com.hotbitmapgg.leisureread.LeisureReadApp;
import dagger.Component;
import javax.inject.Singleton;

import android.content.Context;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG AppComponent
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

  Context context();

  void inject(LeisureReadApp mApplication);
}
