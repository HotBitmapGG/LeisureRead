package com.hotbitmapgg.rxzhihu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hcc on 16/4/5.
 */
public class FuliResult
{

    public boolean error;

    @SerializedName("results")
    public List<FuliBean> fulis;


    public class FuliBean
    {

        public String createdAt;

        public String url;
    }
}
