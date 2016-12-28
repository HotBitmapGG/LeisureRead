package com.hotbitmapgg.leisureread.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 专栏详情模型类
 */
public class SectionsDetails {

  public String name;

  public long timestamp;

  public List<SectionsDetailsInfo> stories;

  public class SectionsDetailsInfo {

    public String date;

    @SerializedName("display_date")
    public String displayDate;

    public int id;

    public List<String> images;

    public String title;
  }
}
