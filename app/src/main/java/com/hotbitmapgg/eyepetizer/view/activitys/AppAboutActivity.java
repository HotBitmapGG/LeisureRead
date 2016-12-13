package com.hotbitmapgg.eyepetizer.view.activitys;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.hotbitmapgg.eyepetizer.base.BaseActivity;
import com.hotbitmapgg.rxzhihu.R;

import butterknife.Bind;

/**
 * Created by hcc on 16/4/24 13:18
 * 100332338@qq.com
 * <p/>
 * App知了介绍
 */
public class AppAboutActivity extends BaseActivity
{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.tv_version)
    TextView mVersionTv;


    @Override
    public int getLayoutId()
    {

        return R.layout.activity_about;
    }

    @Override
    public void initViews(Bundle savedInstanceState)
    {

        String version = getVersion();
        mVersionTv.setText("版本号:" + " V" + version);
    }

    @Override
    public void initToolBar()
    {

        mToolbar.setTitle("关于App");
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(view -> onBackPressed());
    }


    private String getVersion()
    {

        try
        {
            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return getString(R.string.about_version);
        }
    }
}
