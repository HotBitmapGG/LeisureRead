package com.hotbitmapgg.rxzhihu.network;

import com.hotbitmapgg.rxzhihu.base.ZhiHuApp;
import com.hotbitmapgg.rxzhihu.model.DailyDetail;
import com.hotbitmapgg.rxzhihu.model.DailyListBean;
import com.hotbitmapgg.rxzhihu.model.DailyTypeBean;
import com.hotbitmapgg.rxzhihu.model.LuanchImageBean;
import com.hotbitmapgg.rxzhihu.utils.NetWorkUtil;

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
 * Created by 11 on 2016/3/31.
 * <p/>
 * Retrofit管理类
 */
public class RetrofitHelper
{

    public static final String ZHIHU_DAILY_URL = "http://news-at.zhihu.com/api/4/";

    private static OkHttpClient mOkHttpClient;

    private final ZhiHuDailyAPI mZhiHuApi;

    public static final int CACHE_TIME_SHORT = 60;

    public static final int CACHE_TIME_LONG = 60 * 60 * 24 * 7;

    public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";


    public static RetrofitHelper builder()
    {

        return new RetrofitHelper();
    }

    private RetrofitHelper()
    {

        initOkHttpClient();

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(ZHIHU_DAILY_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mZhiHuApi = mRetrofit.create(ZhiHuDailyAPI.class);
    }

    /**
     * 初始化OKHttpClient
     */
    private void initOkHttpClient()
    {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null)
        {
            synchronized (RetrofitHelper.class)
            {
                if (mOkHttpClient == null)
                {
                    //设置Http缓存
                    Cache cache = new Cache(new File(ZhiHuApp.getContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(interceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }

    private Interceptor mRewriteCacheControlInterceptor = new Interceptor()
    {

        @Override
        public Response intercept(Chain chain) throws IOException
        {

            Request request = chain.request();
            if (!NetWorkUtil.isNetworkConnected())
            {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetworkConnected())
            {
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", cacheControl).removeHeader("Pragma").build();
            } else
            {
                return originalResponse.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_TIME_LONG)
                        .removeHeader("Pragma").build();
            }
        }
    };


    public Observable<DailyListBean> getLatestNews()
    {

        return mZhiHuApi.getlatestNews();
    }

    public Observable<DailyListBean> getBeforeNews(String date)
    {

        return mZhiHuApi.getBeforeNews(date);
    }

    public Observable<DailyDetail> getNewsDetails(int id)
    {

        return mZhiHuApi.getNewsDetails(id);
    }

    public Observable<LuanchImageBean> getLuanchImage(String res)
    {

        return mZhiHuApi.getLuanchImage(res);
    }

    public Observable<DailyTypeBean> getDailyType()
    {

        return mZhiHuApi.getDailyType();
    }
}
