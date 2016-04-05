package com.hotbitmapgg.rxzhihu.model;

/**
 * Created by hcc on 16/4/4.
 */
public class LuanchImageBean
{

    private String text;

    private String img;

    public String getImg()
    {

        return img;
    }

    public void setImg(String img)
    {

        this.img = img;
    }

    public String getText()
    {

        return text;
    }

    public void setText(String text)
    {

        this.text = text;
    }

    @Override
    public String toString()
    {

        return "LuanchImageBean{" +
                "text='" + text + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
