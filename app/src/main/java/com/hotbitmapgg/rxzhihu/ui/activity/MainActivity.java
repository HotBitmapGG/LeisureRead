package com.hotbitmapgg.rxzhihu.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.adapter.AbsRecyclerViewAdapter;
import com.hotbitmapgg.rxzhihu.adapter.DailyTypeRecycleAdapter;
import com.hotbitmapgg.rxzhihu.base.AbsBaseActivity;
import com.hotbitmapgg.rxzhihu.model.DailyTypeBean;
import com.hotbitmapgg.rxzhihu.network.RetrofitHelper;
import com.hotbitmapgg.rxzhihu.ui.fragment.DailyListFragment;
import com.hotbitmapgg.rxzhihu.ui.fragment.FuliFragment;
import com.hotbitmapgg.rxzhihu.ui.fragment.TypeDailyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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

    @Bind(R.id.nav_recycle)
    RecyclerView mRecyclerView;

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

        fragments.add(new DailyListFragment());
        setShowingFragment(fragments.get(0));
        getDailyTypeData();

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

                setShowingFragment(FuliFragment.newInstance());
                mToolbar.setTitle("隐藏福利");
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

    public void getDailyTypeData()
    {

        RetrofitHelper.builder().getDailyType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DailyTypeBean>()
                {

                    @Override
                    public void call(DailyTypeBean dailyTypeBean)
                    {

                        if (dailyTypeBean != null)
                        {
                            List<DailyTypeBean.SubjectDaily> others = dailyTypeBean.getOthers();
                            finishGetDailyType(others);
                        }
                    }
                }, new Action1<Throwable>()
                {

                    @Override
                    public void call(Throwable throwable)
                    {

                    }
                });
    }

    private void finishGetDailyType(final List<DailyTypeBean.SubjectDaily> others)
    {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        DailyTypeRecycleAdapter mAdapter = new DailyTypeRecycleAdapter(mRecyclerView, others);
        mRecyclerView.setAdapter(mAdapter);
        for (int i = 0 ; i < others.size(); i++)
        {
            fragments.add(TypeDailyFragment.newInstance(others.get(i)));
        }
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener()
        {

            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder)
            {

                DailyTypeBean.SubjectDaily subjectDaily = others.get(position);
                //fragments.add(TypeDailyFragment.newInstance(subjectDaily));
                setShowingFragment(fragments.get(position + 1));
                mDrawerLayout.closeDrawers();
                mToolbar.setTitle(subjectDaily.getName());

            }
        });
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
                mToolbar.setTitle("知乎日报");
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
