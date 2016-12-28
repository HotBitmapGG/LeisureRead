package com.hotbitmapgg.leisureread.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 日报额外数据模型类
 */
public class DailyExtraMessageInfo {

  public int comments;

  @SerializedName("long_comments")
  public int longComments;

  public int popularity;

  @SerializedName("short_comments")
  public int shortComments;
}
