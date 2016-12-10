package com.hotbitmapgg.eyepetizer.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hotbitmapgg.eyepetizer.base.AbsBaseActivity;
import com.hotbitmapgg.rxzhihu.R;

import butterknife.Bind;

/**
 * Created by hcc on 16/4/24 11:10
 * 100332338@qq.com
 * <p/>
 * 关于我
 */
public class HotBitmapGGInfoActivity extends AbsBaseActivity
{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public int getLayoutId()
    {

        return R.layout.activity_hotbitmapgg;
    }

    @Override
    public void initViews(Bundle savedInstanceState)
    {

    }

    @Override
    public void initToolBar()
    {

        mToolbar.setTitleTextColor(getResources().getColor(R.color.black_90));
        mToolbar.setTitle("关于我");
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
