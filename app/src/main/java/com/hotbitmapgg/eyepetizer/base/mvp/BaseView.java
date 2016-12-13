package com.hotbitmapgg.eyepetizer.base.mvp;

/**
 * Created by hcc on 2016/12/10 16:00
 * 100332338@qq.com
 * EyepetizerDaily
 *
 * @HotBitmapGG MVP架构->View层基类
 */

public interface BaseView<T> {

  void setPresenter(T presenter);
}
