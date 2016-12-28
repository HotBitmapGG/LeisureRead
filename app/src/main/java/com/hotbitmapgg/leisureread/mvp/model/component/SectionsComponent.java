package com.hotbitmapgg.leisureread.mvp.model.component;

import com.hotbitmapgg.leisureread.app.AppComponent;
import com.hotbitmapgg.leisureread.mvp.presenter.sections.SectionsPresenterMoudle;
import com.hotbitmapgg.leisureread.mvp.scope.ActivityScope;
import com.hotbitmapgg.leisureread.ui.activity.MainActivity;

import dagger.Component;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = { SectionsPresenterMoudle.class })
public interface SectionsComponent {

  void inject(MainActivity mainActivity);
}
