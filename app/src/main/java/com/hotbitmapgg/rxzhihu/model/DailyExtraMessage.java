package com.hotbitmapgg.rxzhihu.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hcc on 16/4/23 10:17
 * 100332338@qq.com
 * <p/>
 * <p/>
 * long_comments : 长评论总数
 * popularity : 点赞总数
 * short_comments : 短评论总数
 * comments : 评论总数
 */
public class DailyExtraMessage
{


    public int comments;

    @SerializedName("long_comments")
    public int longComments;

    public int popularity;

    @SerializedName("short_comments")
    public int shortComments;
}
