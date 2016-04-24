package com.hotbitmapgg.rxzhihu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hcc on 16/4/23 13:32
 * 100332338@qq.com
 * <p/>
 * 热门消息
 * <p/>
 * 大体同前面介绍的 API 类似，唯一需要注意的是：欲获得图片地址，不再使用 image 而是 thumbnail 属性
 * url 属性可直接使用。请注意，url 中的 api 属性为 2，是较老版本。
 */
public class HotNews
{

    public List<HotNewsInfo> recent;

    public class HotNewsInfo
    {

        @SerializedName("news_id")
        public int newsId;

        public String thumbnail;

        public String title;

        public String url;
    }
}
