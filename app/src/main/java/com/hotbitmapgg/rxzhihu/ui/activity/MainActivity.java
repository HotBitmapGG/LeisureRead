package com.hotbitmapgg.rxzhihu.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.base.AbsBaseActivity;
import com.hotbitmapgg.rxzhihu.ui.fragment.DailyListFragment;
import com.hotbitmapgg.rxzhihu.ui.fragment.FuliFragment;
import com.hotbitmapgg.rxzhihu.ui.fragment.HotBitmapGGInfoFragment;
import com.hotbitmapgg.rxzhihu.ui.fragment.HotNewsFragment;
import com.hotbitmapgg.rxzhihu.ui.fragment.SectionsFragment;
import com.hotbitmapgg.rxzhihu.ui.fragment.ThemesDailyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 知乎日报主界面
 *
 * @HotBitmapgg
 */
public class MainActivity extends AbsBaseActivity implements NavigationView.OnNavigationItemSelectedListener
{

    @Bind(R.id.toolbar)
    android.support.v7.widget.Toolbar mToolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.nav_view)
    NavigationView mNavigationView;

    private List<Fragment> fragments = new ArrayList<>();

    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    public int getLayoutId()
    {

        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState)
    {


        fragments.add(DailyListFragment.newInstance());
        fragments.add(ThemesDailyFragment.newInstance());
        fragments.add(SectionsFragment.newInstance());
        fragments.add(FuliFragment.newInstance());
        fragments.add(HotNewsFragment.newInstance());
        fragments.add(HotBitmapGGInfoFragment.newInstance());

        setShowingFragment(fragments.get(0));
    }

    @Override
    public void initToolBar()
    {

        setSupportActionBar(mToolbar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
        {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        mDrawerLayout.addDrawerListener(new DrawerListener());
        mNavigationView.setNavigationItemSelectedListener(this);

        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        switch (item.getItemId())
        {
            case R.id.action_message:

                return true;

            case R.id.action_mode:
                //切换日夜间模式
                mNightModeHelper.toggle();
                return true;

            case R.id.action_settings:

                return true;
            case R.id.action_about:
                //关于我
                mToolbar.setTitle("关于我");
                setShowingFragment(fragments.get(5));
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {

        return super.onPrepareOptionsMenu(menu);
    }

    private void setShowingFragment(Fragment fragment)
    {

        getFragmentManager().beginTransaction().replace(R.id.conotent, fragment).commit();
    }

    public Toolbar getToolBar()
    {

        return mToolbar;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {

        mDrawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId())
        {
            case R.id.nav_home:
                setShowingFragment(fragments.get(0));
                item.setCheckable(true);
                mToolbar.setTitle("主页");
                return true;

            case R.id.nav_type:
                setShowingFragment(fragments.get(1));
                item.setCheckable(true);
                mToolbar.setTitle("主题日报");
                return true;

            case R.id.nav_zhuanglan:
                setShowingFragment(fragments.get(2));
                item.setCheckable(true);
                mToolbar.setTitle("知了专栏");
                return true;

            case R.id.nav_fuli:
                setShowingFragment(fragments.get(3));
                item.setCheckable(true);
                mToolbar.setTitle("美女福利");
                return true;

            case R.id.nav_article:
                setShowingFragment(fragments.get(4));
                item.setCheckable(true);
                mToolbar.setTitle("热门文章");

                return true;

            case R.id.nav_about:
                //关于知了
                startActivity(new Intent(MainActivity.this, AppAboutActivity.class));
                return true;
        }

        return false;
    }


    private class DrawerListener implements DrawerLayout.DrawerListener
    {

        @Override
        public void onDrawerOpened(View drawerView)
        {

            if (mDrawerToggle != null)
            {
                mDrawerToggle.onDrawerOpened(drawerView);
            }
        }

        @Override
        public void onDrawerClosed(View drawerView)
        {

            if (mDrawerToggle != null)
            {
                mDrawerToggle.onDrawerClosed(drawerView);
            }
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset)
        {

            if (mDrawerToggle != null)
            {
                mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
            }
        }

        @Override
        public void onDrawerStateChanged(int newState)
        {

            if (mDrawerToggle != null)
            {
                mDrawerToggle.onDrawerStateChanged(newState);
            }
        }
    }

    private class ActionBarDrawerToggle extends android.support.v7.app.ActionBarDrawerToggle
    {

        public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar,
                                     int openDrawerContentDescRes, int closeDrawerContentDescRes)
        {

            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerClosed(View drawerView)
        {

            super.onDrawerClosed(drawerView);
            invalidateOptionsMenu();
        }

        @Override
        public void onDrawerOpened(View drawerView)
        {

            super.onDrawerOpened(drawerView);
            invalidateOptionsMenu();
        }
    }
}
