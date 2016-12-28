package com.hotbitmapgg.leisureread.mvp.model.entity;

import java.util.List;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 专栏列表模型类
 */
public class SectionsInfo {

  private List<DataBean> data;

  public List<DataBean> getData() { return data;}


  public void setData(List<DataBean> data) { this.data = data;}


  public static class DataBean {
    /**
     * description : 看别人的经历，理解自己的生活
     * id : 1
     * name : 深夜惊奇
     * thumbnail : http://pic3.zhimg.com/91125c9aebcab1c84f58ce4f8779551e.jpg
     */

    private String description;
    private int id;
    private String name;
    private String thumbnail;


    public String getDescription() { return description;}


    public void setDescription(String description) { this.description = description;}


    public int getId() { return id;}


    public void setId(int id) { this.id = id;}


    public String getName() { return name;}


    public void setName(String name) { this.name = name;}


    public String getThumbnail() { return thumbnail;}


    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail;}
  }
}
