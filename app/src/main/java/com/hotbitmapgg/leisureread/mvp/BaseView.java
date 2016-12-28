package com.hotbitmapgg.leisureread.mvp;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG MVP架构->View层基类
 */

public interface BaseView<T> {

  void setPresenter(T presenter);
}
