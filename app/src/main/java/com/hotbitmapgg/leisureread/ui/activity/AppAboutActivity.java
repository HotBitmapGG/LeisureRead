package com.hotbitmapgg.leisureread.ui.activity;

import butterknife.Bind;
import com.hotbitmapgg.leisureread.ui.activity.base.BaseAppCompatActivity;
import com.hotbitmapgg.rxzhihu.R;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG App介绍界面
 */
public class AppAboutActivity extends BaseAppCompatActivity {

  @Bind(R.id.toolbar)
  Toolbar mToolbar;

  @Bind(R.id.tv_version)
  TextView mVersionTv;


  @Override
  public int getLayoutId() {

    return R.layout.activity_about;
  }


  @Override
  public void initViews(Bundle savedInstanceState) {

    String version = getVersion();
    mVersionTv.setText("版本号:" + " V" + version);
  }


  @Override
  public void initToolBar() {

    mToolbar.setTitle("关于App");
    mToolbar.setNavigationIcon(R.drawable.ic_action_back);
    mToolbar.setNavigationOnClickListener(view -> onBackPressed());
  }


  private String getVersion() {

    try {
      PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
      return pi.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
      return getString(R.string.about_version);
    }
  }
}
