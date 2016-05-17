package com.hotbitmapgg.rxzhihu.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hcc on 16/5/15 13:18
 * 100332338@qq.com
 */
public interface DoubanMeizhiApi
{

    /**
     * 根据cid查询不同类型的妹子图片
     * @param cid
     * @param pageNum
     * @return
     */
    @GET("show.htm")
    Call<ResponseBody> getDoubanMeizi(@Query("cid") int cid, @Query("pager_offset") int pageNum);
}
