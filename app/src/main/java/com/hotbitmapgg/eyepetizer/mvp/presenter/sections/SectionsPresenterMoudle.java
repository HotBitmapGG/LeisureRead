package com.hotbitmapgg.eyepetizer.mvp.presenter.sections;

import com.hotbitmapgg.eyepetizer.mvp.view.SectionsContract;
import com.hotbitmapgg.eyepetizer.mvp.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hcc on 2016/12/12 13:25
 * 100332338@qq.com
 * EyepetizerDaily
 *
 * @HotBitmapGG
 */

@Module
public class SectionsPresenterMoudle {

  private final SectionsContract.View mView;


  public SectionsPresenterMoudle(SectionsContract.View mView) {

    this.mView = mView;
  }


  @Provides
  @ActivityScope
  SectionsContract.View view() {

    return this.mView;
  }
}
