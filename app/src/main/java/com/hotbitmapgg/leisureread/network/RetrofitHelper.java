package com.hotbitmapgg.leisureread.network;

import com.hotbitmapgg.leisureread.LeisureReadApp;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyCommentInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyDetailsInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyExtraMessageInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.ThemeDailyInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.ThemeDetailsInfo;
import com.hotbitmapgg.leisureread.network.api.ApiService;
import com.hotbitmapgg.leisureread.utils.NetWorkUtil;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG Retrofit帮助类
 */
public class RetrofitHelper {

  private static final String ZHIHU_DAILY_URL = "http://news-at.zhihu.com/api/4/";

  private static final String ZHIHU_LAST_URL = "http://news-at.zhihu.com/api/3/";

  private static OkHttpClient mOkHttpClient;

  private final ApiService mZhiHuApiService;


  static {

    initOkHttpClient();
  }


  public static RetrofitHelper builder() {

    return new RetrofitHelper();
  }


  private RetrofitHelper() {

    Retrofit mRetrofit = new Retrofit.Builder()
        .baseUrl(ZHIHU_DAILY_URL)
        .client(mOkHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    mZhiHuApiService = mRetrofit.create(ApiService.class);
  }


  public static ApiService getLastZhiHuApi() {

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(ZHIHU_LAST_URL)
        .client(mOkHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    return retrofit.create(ApiService.class);
  }


  /**
   * 初始化OKHttpClient
   */
  private static void initOkHttpClient() {

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    if (mOkHttpClient == null) {
      synchronized (RetrofitHelper.class) {
        if (mOkHttpClient == null) {
          //设置Http缓存
          Cache cache = new Cache(
              new File(LeisureReadApp.getAppContext().getCacheDir(), "HttpCache"),
              1024 * 1024 * 100);

          mOkHttpClient = new OkHttpClient.Builder()
              .cache(cache)
              .addNetworkInterceptor(new CacheInterceptor())
              .addInterceptor(interceptor)
              .retryOnConnectionFailure(true)
              .connectTimeout(15, TimeUnit.SECONDS)
              .build();
        }
      }
    }
  }


  /**
   * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
   */
  private static class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

      // 有网络时 设置缓存超时时间1个小时
      int maxAge = 60 * 60;
      // 无网络时，设置超时为1天
      int maxStale = 60 * 60 * 24;
      Request request = chain.request();
      if (NetWorkUtil.isNetworkConnected()) {
        //有网络时只从网络获取
        request = request.newBuilder()
            .cacheControl(CacheControl.FORCE_NETWORK)
            .build();
      } else {
        //无网络时只从缓存中读取
        request = request.newBuilder()
            .cacheControl(CacheControl.FORCE_CACHE)
            .build();
      }
      Response response = chain.proceed(request);
      if (NetWorkUtil.isNetworkConnected()) {
        response = response.newBuilder()
            .removeHeader("Pragma")
            .header("Cache-Control", "public, max-age=" + maxAge)
            .build();
      } else {
        response = response.newBuilder()
            .removeHeader("Pragma")
            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
            .build();
      }
      return response;
    }
  }


  public Observable<DailyInfo> getLatestNews() {

    return mZhiHuApiService.getlatestNews();
  }


  public Observable<DailyInfo> getBeforeNews(String date) {

    return mZhiHuApiService.getBeforeNews(date);
  }


  public Observable<DailyDetailsInfo> getNewsDetails(int id) {

    return mZhiHuApiService.getNewsDetails(id);
  }


  public Observable<ThemeDailyInfo> getDailyType() {

    return mZhiHuApiService.getDailyType();
  }


  public Observable<ThemeDetailsInfo> getThemesDetailsById(int id) {

    return mZhiHuApiService.getThemesDetailsById(id);
  }


  public Observable<DailyExtraMessageInfo> getDailyExtraMessageById(int id) {

    return mZhiHuApiService.getDailyExtraMessageById(id);
  }


  public Observable<DailyCommentInfo> getDailyLongCommentById(int id) {

    return mZhiHuApiService.getDailyLongComment(id);
  }


  public Observable<DailyCommentInfo> getDailyShortCommentById(int id) {

    return mZhiHuApiService.getDailyShortComment(id);
  }
}
