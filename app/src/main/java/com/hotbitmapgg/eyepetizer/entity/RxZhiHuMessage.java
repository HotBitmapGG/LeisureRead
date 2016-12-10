package com.hotbitmapgg.eyepetizer.entity;

import cn.bmob.v3.BmobObject;

public class RxZhiHuMessage extends BmobObject
{

    private String content;

    public RxZhiHuMessage(String content)
    {

        super();
        this.content = content;
    }

    public RxZhiHuMessage()
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
