package com.hotbitmapgg.rxzhihu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hcc on 16/4/24 09:40
 * 100332338@qq.com
 * <p/>
 * 查看日报推荐者信息
 * <p/>
 * "items":[] 该参数没有使用 目前还不知道是什么用处
 */
public class DailyRecommend
{

    @SerializedName("item_count")
    public int itemCount;

    public List<Editor> editors;

    @Override
    public String toString()
    {

        return "DailyRecommend{" +
                "itemCount=" + itemCount +
                ", editors=" + editors +
                '}';
    }

    public class Editor
    {

        public String avatar;

        public String bio;

        public int id;

        public String name;

        public String title;

        @Override
        public String toString()
        {

            return "Editor{" +
                    "avatar='" + avatar + '\'' +
                    ", bio='" + bio + '\'' +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
