package com.hotbitmapgg.leisureread.mvp.model.entity;

import org.json.JSONObject;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG
 */
public class EditorsInfo {

  private String bio;

  private int id;

  private String name;

  private String url;

  private String avatar;


  public EditorsInfo() {

  }


  public EditorsInfo(JSONObject json) {

    this.bio = json.optString("bio");
    this.id = json.optInt("id");
    this.name = json.optString("name");
    this.url = json.optString("url");
    this.avatar = json.optString("avatar");
  }


  public String getBio() {

    return this.bio;
  }


  public void setBio(String bio) {

    this.bio = bio;
  }


  public int getId() {

    return this.id;
  }


  public void setId(int id) {

    this.id = id;
  }


  public String getName() {

    return this.name;
  }


  public void setName(String name) {

    this.name = name;
  }


  public String getUrl() {

    return this.url;
  }


  public void setUrl(String url) {

    this.url = url;
  }


  public String getAvatar() {

    return this.avatar;
  }


  public void setAvatar(String avatar) {

    this.avatar = avatar;
  }
}
