package com.hotbitmapgg.rxzhihu.model;

import java.util.List;

/**
 * Created by hcc on 16/4/23 14:06
 * 100332338@qq.com
 * <p/>
 * 知乎专栏列表
 */
public class DailySections
{

    public List<DailySectionsInfo> data;

    public class DailySectionsInfo
    {

        public String description;

        public int id;

        public String name;

        public String thumbnail;
    }
}
