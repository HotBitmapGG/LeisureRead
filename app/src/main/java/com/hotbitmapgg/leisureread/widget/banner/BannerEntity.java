package com.hotbitmapgg.leisureread.widget.banner;

/**
 * Created by hcc on 16/8/24 21:37
 * 100332338@qq.com
 * <p>
 * Banner模型类
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
