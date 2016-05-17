package com.hotbitmapgg.rxzhihu.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.base.AbsBaseActivity;
import com.hotbitmapgg.rxzhihu.ui.fragment.SimpleMeiziFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * Created by hcc on 16/5/14 22:30
 * 100332338@qq.com
 */
public class DoubanMeiziActivity extends AbsBaseActivity
{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabLayout;

    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    private List<String> titles = Arrays.asList("大胸妹", "小翘臀", "黑丝袜", "美图控", "高颜值");

    private List<Integer> cids = Arrays.asList(2, 6, 7, 3, 4);


    @Override
    public int getLayoutId()
    {

        return R.layout.activity_douban_meizi;
    }

    @Override
    public void initViews(Bundle savedInstanceState)
    {

        initFragments();
    }

    private void initFragments()
    {

        mViewPager.setAdapter(new DoubanMeiziPageAdapter(getFragmentManager()));
        mViewPager.setOffscreenPageLimit(1);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void initToolBar()
    {

        mToolbar.setTitle("Douban妹子");
        mToolbar.setNavigationIcon(R.mipmap.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                finish();
            }
        });
    }

    private class DoubanMeiziPageAdapter extends FragmentPagerAdapter
    {

        public DoubanMeiziPageAdapter(FragmentManager fm)
        {

            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {

            return SimpleMeiziFragment.newInstance(cids.get(position),position);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {

            return titles.get(position);
        }

        @Override
        public int getCount()
        {

            return titles.size();
        }
    }
}
