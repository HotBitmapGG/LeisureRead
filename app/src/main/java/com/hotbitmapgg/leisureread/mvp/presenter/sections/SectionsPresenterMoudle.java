package com.hotbitmapgg.leisureread.mvp.presenter.sections;

import com.hotbitmapgg.leisureread.mvp.view.SectionsContract;
import com.hotbitmapgg.leisureread.mvp.scope.ActivityScope;

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
