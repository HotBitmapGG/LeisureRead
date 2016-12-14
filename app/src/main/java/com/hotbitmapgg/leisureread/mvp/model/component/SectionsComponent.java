package com.hotbitmapgg.leisureread.mvp.model.component;

import com.hotbitmapgg.leisureread.app.AppComponent;
import com.hotbitmapgg.leisureread.mvp.presenter.sections.SectionsPresenterMoudle;
import com.hotbitmapgg.leisureread.mvp.scope.ActivityScope;
import com.hotbitmapgg.leisureread.ui.activity.MainActivity;

import dagger.Component;

/**
 * Created by hcc on 2016/12/10 16:16
 * 100332338@qq.com
 * EyepetizerDaily
 *
 * @HotBitmapGG
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = { SectionsPresenterMoudle.class })
public interface SectionsComponent {

  void inject(MainActivity mainActivity);
}
