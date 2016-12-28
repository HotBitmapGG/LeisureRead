package com.hotbitmapgg.leisureread.app.setting;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

import android.content.Context;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG Glide图片加载库配置
 */

public class GlideModuleSetting implements GlideModule {

  /**
   * 内存缓存大小
   */
  private static final int memorySize = 1024 * 1024 * 10;

  /**
   * 磁盘缓存大小
   */
  private static final int diskCacheSize = 1024 * 1024 * 30;


  @Override
  public void applyOptions(Context context, GlideBuilder builder) {

    builder.setMemoryCache(new LruResourceCache(memorySize));
  }


  @Override
  public void registerComponents(Context context, Glide glide) {

    //配置OkHttp
    OkHttpClient mOkHttpClient = new OkHttpClient()
        .newBuilder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build();

    //设置Glide请求为Okhttp
    glide.register(GlideUrl.class, InputStream.class,
        new OkHttpUrlLoader.Factory(mOkHttpClient));

    //设置Glide的内存缓存和BitmapPool使用最多他们初始值的最大大小的一半
    glide.setMemoryCategory(MemoryCategory.LOW);
  }
}
