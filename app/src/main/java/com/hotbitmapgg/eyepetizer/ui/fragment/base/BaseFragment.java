package com.hotbitmapgg.eyepetizer.ui.fragment.base;

import butterknife.ButterKnife;
import com.trello.rxlifecycle.components.support.RxFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 11 on 2016/4/1.
 * <p/>
 * Fragment基类
 */
public abstract class BaseFragment extends RxFragment {

  private View rootView;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    rootView = inflater.inflate(getLayoutId(), container, false);
    return rootView;
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
