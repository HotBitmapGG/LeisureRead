package com.hotbitmapgg.rxzhihu.model;

import java.io.Serializable;
import java.util.List;


/**
 * limit : 返回数目之限制（仅为猜测）
 * subscribed : 已订阅条目
 * others : 其他条目
 * color : 颜色，作用未知
 * thumbnail : 供显示的图片地址
 * description : 主题日报的介绍
 * id : 该主题日报的编号
 * name : 供显示的主题日报名称
 */
public class DailyTypeBean implements Serializable
{

    int limit;

    List<String> subscribed;

    List<SubjectDaily> others;

    public static class SubjectDaily implements Serializable
    {

        int color;

        String thumbnail;

        String description;

        int id;

        String name;

        public SubjectDaily(int color, String thumbnail, String description, int id, String name)
        {

            this.color = color;
            this.thumbnail = thumbnail;
            this.description = description;
            this.id = id;
            this.name = name;
        }

        public boolean isAdd()
        {

            return isAdd;
        }

        public void setIsAdd(boolean isAdd)
        {

            this.isAdd = isAdd;
        }

        boolean isAdd = false;


        public int getColor()
        {

            return color;
        }

        public void setColor(int color)
        {

            this.color = color;
        }

        public String getThumbnail()
        {

            return thumbnail;
        }

        public void setThumbnail(String thumbnail)
        {

            this.thumbnail = thumbnail;
        }

        public String getDescription()
        {

            return description;
        }

        public void setDescription(String description)
        {

            this.description = description;
        }

        public int getId()
        {

            return id;
        }

        public void setId(int id)
        {

            this.id = id;
        }

        public String getName()
        {

            return name;
        }

        public void setName(String name)
        {

            this.name = name;
        }
    }

    public int getLimit()
    {

        return limit;
    }

    public void setLimit(int limit)
    {

        this.limit = limit;
    }

    public List<String> getSubscribed()
    {

        return subscribed;
    }

    public void setSubscribed(List<String> subscribed)
    {

        this.subscribed = subscribed;
    }

    public List<SubjectDaily> getOthers()
    {

        return others;
    }

    public void setOthers(List<SubjectDaily> others)
    {

        this.others = others;
    }
}
