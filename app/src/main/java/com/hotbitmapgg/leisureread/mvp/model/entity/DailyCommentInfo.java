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

  private List<CommentsBean> comments;


  public List<CommentsBean> getComments() { return comments;}


  public void setComments(List<CommentsBean> comments) { this.comments = comments;}


  public static class CommentsBean {
    /**
     * author : vivian-小扣
     * content : 只看过三部他的电影，《梦旅人》，《岸边之旅》，和最近的《罗曼蒂克消亡史》，每一部都很出彩。
     * avatar : http://pic4.zhimg.com/6c4ab083a109ff176ff563cc24972cfb_im.jpg
     * time : 1482911777
     * id : 27655915
     * likes : 0
     */

    private String author;
    private String content;
    private String avatar;
    private int time;
    private int id;
    private int likes;


    public String getAuthor() { return author;}


    public void setAuthor(String author) { this.author = author;}


    public String getContent() { return content;}


    public void setContent(String content) { this.content = content;}


    public String getAvatar() { return avatar;}


    public void setAvatar(String avatar) { this.avatar = avatar;}


    public int getTime() { return time;}


    public void setTime(int time) { this.time = time;}


    public int getId() { return id;}


    public void setId(int id) { this.id = id;}


    public int getLikes() { return likes;}


    public void setLikes(int likes) { this.likes = likes;}
  }
}
