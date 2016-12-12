package com.hotbitmapgg.eyepetizer.model.entity;

import cn.bmob.v3.BmobObject;

public class FeedBackInfo extends BmobObject
{

    private String content;

    public FeedBackInfo(String content)
    {

        super();
        this.content = content;
    }

    public FeedBackInfo()
    {

        super();
        // TODO Auto-generated constructor stub
    }

    public String getContent()
    {

        return content;
    }

    public void setContent(String content)
    {

        this.content = content;
    }

    @Override
    public String toString()
    {

        return "FeekBckBean [content=" + content + "]";
    }
}
