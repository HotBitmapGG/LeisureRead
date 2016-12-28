package com.hotbitmapgg.leisureread.ui.activity;

import butterknife.Bind;
import com.hotbitmapgg.leisureread.ui.activity.base.BaseAppCompatActivity;
import com.hotbitmapgg.rxzhihu.R;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 关于我界面
 */
public class HotBitmapGGInfoActivity extends BaseAppCompatActivity {

  @Bind(R.id.toolbar)
  Toolbar mToolbar;


  @Override
  public int getLayoutId() {

    return R.layout.activity_hotbitmapgg;
  }


  @Override
  public void initViews(Bundle savedInstanceState) {

  }


  @Override
  public void initToolBar() {

    mToolbar.setTitleTextColor(getResources().getColor(R.color.black_90));
    mToolbar.setTitle("关于我");
    mToolbar.setNavigationIcon(R.drawable.ic_action_back);
    mToolbar.setNavigationOnClickListener(view -> onBackPressed());
  }
}
