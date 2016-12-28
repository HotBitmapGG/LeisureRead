package com.hotbitmapgg.leisureread.mvp.presenter;

import com.hotbitmapgg.leisureread.network.RetrofitHelper;
import com.hotbitmapgg.leisureread.mvp.view.SectionsContract;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG
 */

public class SectionsPresenter implements SectionsContract.Presenter {

  private final SectionsContract.View mView;


  @Inject
  public SectionsPresenter(SectionsContract.View mView) {

    this.mView = mView;
    mView.setPresenter(this);
  }


  @Override
  public void start() {

    loadData();
  }


  @Override
  public void loadData() {

    RetrofitHelper.getLastZhiHuApi().getZhiHuSections()
        .delay(1000, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(dailySections -> {
          mView.showData(dailySections.data);
        }, throwable -> {
          mView.showError();
        });
  }
}
