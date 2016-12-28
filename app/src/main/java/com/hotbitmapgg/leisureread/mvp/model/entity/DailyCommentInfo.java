package com.hotbitmapgg.leisureread.mvp.model.entity;

import java.util.List;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 日报评论模型类
 */
public class DailyCommentInfo {

  public List<CommentInfo> comments;

  public class CommentInfo {

    public String author;

    public String avatar;

    public String content;

    public int id;

    public int likes;

    public long time;
  }
}
