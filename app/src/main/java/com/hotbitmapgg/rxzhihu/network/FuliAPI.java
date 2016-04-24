package com.hotbitmapgg.rxzhihu.network;

import com.hotbitmapgg.rxzhihu.model.FuliResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hcc on 16/4/5.
 * <p/>
 * 此Api来自代码家的 gank.io~
 */
public interface FuliAPI
{

    /**
     * 查询美图图片福利接口
     *
     * @param number
     * @param page
     * @return
     */
    @GET("data/福利/{number}/{page}")
    Observable<FuliResult> getFulis(@Path("number") int number, @Path("page") int page);
}
