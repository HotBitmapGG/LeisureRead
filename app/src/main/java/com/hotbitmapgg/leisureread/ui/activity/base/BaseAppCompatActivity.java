package com.hotbitmapgg.leisureread.ui.activity.base;

import butterknife.ButterKnife;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import android.os.Bundle;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG BaseAppCompatActivity基类
 */
public abstract class BaseAppCompatActivity extends RxAppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    //设置布局内容
    setContentView(getLayoutId());
    //初始化黄油刀控件绑定框架
    ButterKnife.bind(this);
    //初始化控件
    initViews(savedInstanceState);
    //初始化ToolBar
    initToolBar();
  }


  @Override
  protected void onDestroy() {

    super.onDestroy();
    ButterKnife.unbind(this);
  }


  public abstract int getLayoutId();

  public abstract void initViews(Bundle savedInstanceState);

  public abstract void initToolBar();
}
