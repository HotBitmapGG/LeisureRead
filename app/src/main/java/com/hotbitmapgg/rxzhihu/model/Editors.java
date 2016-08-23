package com.hotbitmapgg.rxzhihu.model;

import org.json.*;


public class Editors
{

    private String bio;

    private int id;

    private String name;

    private String url;

    private String avatar;


    public Editors()
    {

    }

    public Editors(JSONObject json)
    {

        this.bio = json.optString("bio");
        this.id = json.optInt("id");
        this.name = json.optString("name");
        this.url = json.optString("url");
        this.avatar = json.optString("avatar");
    }

    public String getBio()
    {

        return this.bio;
    }

    public void setBio(String bio)
    {

        this.bio = bio;
    }

    public int getId()
    {

        return this.id;
    }

    public void setId(int id)
    {

        this.id = id;
    }

    public String getName()
    {

        return this.name;
    }

    public void setName(String name)
    {

        this.name = name;
    }

    public String getUrl()
    {

        return this.url;
    }

    public void setUrl(String url)
    {

        this.url = url;
    }

    public String getAvatar()
    {

        return this.avatar;
    }

    public void setAvatar(String avatar)
    {

        this.avatar = avatar;
    }
}
