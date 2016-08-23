package com.hotbitmapgg.rxzhihu.ui.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.base.AbsBaseActivity;

import butterknife.Bind;

/**
 * Created by hcc on 16/4/24 13:18
 * 100332338@qq.com
 * <p/>
 * App知了介绍
 */
public class AppAboutActivity extends AbsBaseActivity
{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

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

    }

    @Override
    public void initToolBar()
    {

        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbarLayout.setTitle("关于知了");
        String version = getVersion();
        mVersionTv.setText("版本号:" + " V" + version);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
