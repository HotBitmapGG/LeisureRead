package com.hotbitmapgg.leisureread.network.api;

import com.hotbitmapgg.leisureread.mvp.model.entity.DailyCommentInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyDetailsInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyExtraMessageInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyListBean;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyRecommendInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailySections;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyTypeInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.SectionsDetails;
import com.hotbitmapgg.leisureread.mvp.model.entity.ThemesDetails;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 11 on 2016/3/31.
 * <p/>
 * Retrofit请求配置接口  By:知乎日报
 *
 * @HotBitmapGG
 */
public interface ApiService {

  /**
   * 获取最新的日报数据
   */
  @GET("stories/latest")
  Observable<DailyListBean> getlatestNews();

  /**
   * 根据时间获取对应的日报数据
   */
  @GET("stories/before/{date}")
  Observable<DailyListBean> getBeforeNews(@Path("date") String date);

  /**
   * 获取日报详情数据
   */
  @GET("story/{id}")
  Observable<DailyDetailsInfo> getNewsDetails(@Path("id") int id);


  /**
   * 获取专题日报
   */
  @GET("themes")
  Observable<DailyTypeInfo> getDailyType();

  /**
   * 根据id查询主题日报内容
   */
  @GET("theme/{id}")
  Observable<ThemesDetails> getThemesDetailsById(@Path("id") int id);

  /**
   * 根据id查询日报的额外信息
   */
  @GET("story-extra/{id}")
  Observable<DailyExtraMessageInfo> getDailyExtraMessageById(@Path("id") int id);

  /**
   * 根据id查询日报的长评论
   */
  @GET("story/{id}/long-comments")
  Observable<DailyCommentInfo> getDailyLongComment(@Path("id") int id);

  /**
   * 根据id查询日报的短评论
   */
  @GET("story/{id}/short-comments")
  Observable<DailyCommentInfo> getDailyShortComment(@Path("id") int id);



  /**
   * 获取知乎专栏数据
   */
  @GET("sections")
  Observable<DailySections> getZhiHuSections();

  /**
   * 获取专栏详情数据
   */
  @GET("section/{id}")
  Observable<SectionsDetails> getSectionsDetails(@Path("id") int id);

  /**
   * 获取专栏的之前消息
   */
  @GET("section/{id}/before/{timestamp}")
  Observable<SectionsDetails> getBeforeSectionsDetails(
      @Path("id") int id, @Path("timestamp") long timestamp);

  /**
   * 根据日报id查询该日报的推荐者信息
   */
  @GET("story/{id}/recommenders")
  Observable<DailyRecommendInfo> getDailyRecommendEditors(@Path("id") int id);
}
