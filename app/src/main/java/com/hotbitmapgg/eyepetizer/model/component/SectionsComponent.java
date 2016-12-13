package com.hotbitmapgg.eyepetizer.model.component;

import com.hotbitmapgg.eyepetizer.app.AppComponent;
import com.hotbitmapgg.eyepetizer.presenter.sections.SectionsPresenterMoudle;
import com.hotbitmapgg.eyepetizer.scope.ActivityScope;
import com.hotbitmapgg.eyepetizer.view.activitys.MainActivity;

import dagger.Component;

/**
 * Created by hcc on 2016/12/10 16:16
 * 100332338@qq.com
 * EyepetizerDaily
 *
 * @HotBitmapGG
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {SectionsPresenterMoudle.class})
public interface SectionsComponent
{

    void inject(MainActivity mainActivity);
}
