package com.hotbitmapgg.leisureread.ui.fragment.base;

import butterknife.ButterKnife;
import com.trello.rxlifecycle.components.support.RxFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG BaseFragment基类
 */
public abstract class BaseFragment extends RxFragment {

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    return inflater.inflate(getLayoutId(), container, false);
  }


  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
  }


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {

    initViews();
    initData();
    super.onActivityCreated(savedInstanceState);
  }


  @Override
  public void onDetach() {

    super.onDetach();
  }


  public abstract int getLayoutId();

  public abstract void initViews();

  public abstract void initData();
}
