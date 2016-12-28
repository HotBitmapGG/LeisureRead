package com.hotbitmapgg.leisureread.network.api;

import com.hotbitmapgg.leisureread.mvp.model.entity.DailyCommentInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyDetailsInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyExtraMessageInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.SectionsInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.ThemeDailyInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.SectionsDetailsInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.ThemeDetailsInfo;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 知乎日报Api
 */
public interface ApiService {

  /**
   * 获取最新的日报数据
   */
  @GET("stories/latest")
  Observable<DailyInfo> getlatestNews();

  /**
   * 根据时间获取对应的日报数据
   */
  @GET("stories/before/{date}")
  Observable<DailyInfo> getBeforeNews(@Path("date") String date);

  /**
   * 获取日报详情数据
   */
  @GET("story/{id}")
  Observable<DailyDetailsInfo> getNewsDetails(@Path("id") int id);

  /**
   * 获取专题日报
   */
  @GET("themes")
  Observable<ThemeDailyInfo> getDailyType();

  /**
   * 根据id查询主题日报内容
   */
  @GET("theme/{id}")
  Observable<ThemeDetailsInfo> getThemesDetailsById(@Path("id") int id);

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
  Observable<SectionsInfo> getZhiHuSections();

  /**
   * 获取专栏详情数据
   */
  @GET("section/{id}")
  Observable<SectionsDetailsInfo> getSectionsDetails(@Path("id") int id);

  /**
   * 获取专栏的之前消息
   */
  @GET("section/{id}/before/{timestamp}")
  Observable<SectionsDetailsInfo> getBeforeSectionsDetails(
      @Path("id") int id, @Path("timestamp") long timestamp);
}
