package com.hotbitmapgg.leisureread.widget.banner;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG Banner模型类
 */
public class BannerEntity {

  public BannerEntity(int id, String title, String img) {

    this.id = id;
    this.title = title;
    this.img = img;
  }


  public String title;

  public String img;

  public int id;
}
