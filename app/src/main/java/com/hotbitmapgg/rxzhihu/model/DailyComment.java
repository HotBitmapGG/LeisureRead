package com.hotbitmapgg.rxzhihu.model;

import java.util.List;

/**
 * Created by hcc on 16/4/23 11:28
 * 100332338@qq.com
 * <p/>
 * 日报新闻长评论 短评论
 * 返回数据结构一样 一致处理
 * <p/>
 * comments : 评论列表，形式为数组（请注意，其长度可能为 0）
 * author : 评论作者
 * id : 评论者的唯一标识符
 * content : 评论的内容
 * likes : 评论所获『赞』的数量
 * time : 评论时间
 * avatar : 用户头像图片的地址
 */
public class DailyComment
{

    public List<CommentInfo> comments;


    public class CommentInfo
    {

        public String author;

        public String avatar;

        public String content;

        public int id;

        public int likes;

        public long time;
    }
}
