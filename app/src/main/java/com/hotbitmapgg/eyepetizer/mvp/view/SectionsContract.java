package com.hotbitmapgg.eyepetizer.mvp.view;

import com.hotbitmapgg.eyepetizer.mvp.BasePresenter;
import com.hotbitmapgg.eyepetizer.mvp.BaseView;
import com.hotbitmapgg.eyepetizer.mvp.model.entity.DailySections;

import java.util.List;

/**
 * Created by hcc on 2016/12/12 13:26
 * 100332338@qq.com
 * EyepetizerDaily
 *
 * @HotBitmapGG
 */

public interface SectionsContract {

  interface View extends BaseView<Presenter> {

    void showError();

    void showData(List<DailySections.DailySectionsInfo> sectionsDetailsInfos);
  }

  interface Presenter extends BasePresenter {

    void loadData();
  }
}
