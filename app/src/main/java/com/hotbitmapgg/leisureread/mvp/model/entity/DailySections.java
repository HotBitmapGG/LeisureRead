package com.hotbitmapgg.leisureread.mvp.model.entity;

import java.util.List;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 专栏列表模型类
 */
public class DailySections {

  public List<DailySectionsInfo> data;

  public class DailySectionsInfo {

    public String description;

    public int id;

    public String name;

    public String thumbnail;
  }
}
