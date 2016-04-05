package com.hotbitmapgg.rxzhihu.network;

import com.hotbitmapgg.rxzhihu.model.DailyDetail;
import com.hotbitmapgg.rxzhihu.model.DailyListBean;
import com.hotbitmapgg.rxzhihu.model.DailyTypeBean;
import com.hotbitmapgg.rxzhihu.model.LuanchImageBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 11 on 2016/3/31.
 * <p/>
 * Retrofit请求配置接口
 */
public interface ZhiHuDailyAPI
{

    /**
     * 获取最新的日报数据
     *
     * @return
     */
    @GET("stories/latest")
    Observable<DailyListBean> getlatestNews();


    /**
     * 根据时间获取对应的日报数据
     *
     * @param date
     * @return
     */
    @GET("stories/before/{date}")
    Observable<DailyListBean> getBeforeNews(@Path("date") String date);

    /**
     * 获取日报详情数据
     *
     * @param id
     * @return
     */
    @GET("story/{id}")
    Observable<DailyDetail> getNewsDetails(@Path("id") int id);

    /**
     * 根据分辨率获取启动界面图片
     *
     * @param res
     * @return
     */
    @GET("start-image/{res}")
    Observable<LuanchImageBean> getLuanchImage(@Path("res") String res);

    /**
     * 获取专题日报
     *
     * @return
     */
    @GET("themes")
    Observable<DailyTypeBean> getDailyType();
}
