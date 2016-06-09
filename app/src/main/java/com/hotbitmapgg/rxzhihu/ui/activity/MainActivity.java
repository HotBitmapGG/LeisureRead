package com.hotbitmapgg.rxzhihu.ui.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.base.AbsBaseActivity;
import com.hotbitmapgg.rxzhihu.ui.fragment.DailyListFragment;
import com.hotbitmapgg.rxzhihu.ui.fragment.HotBitmapGGInfoActivity;
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
public class MainActivity extends AbsBaseActivity
{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.bottom_navigation)
    AHBottomNavigation mAhBottomNavigation;

    private List<Fragment> fragments = new ArrayList<>();

    private int currentTabIndex;


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
        fragments.add(HotNewsFragment.newInstance());

        showFragment(fragments.get(0));
        initBottomNav();
    }

    private void initBottomNav()
    {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("日报", R.drawable.ic_profile_answer, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("主题", R.drawable.ic_profile_article, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("专栏", R.drawable.ic_profile_column, R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("文章", R.drawable.ic_profile_favorite, R.color.colorPrimary);

        mAhBottomNavigation.addItem(item1);
        mAhBottomNavigation.addItem(item2);
        mAhBottomNavigation.addItem(item3);
        mAhBottomNavigation.addItem(item4);

        mAhBottomNavigation.setBehaviorTranslationEnabled(true);
        mAhBottomNavigation.setAccentColor(Color.parseColor("#F15D5B"));
        mAhBottomNavigation.setInactiveColor(Color.parseColor("#D3D3D3"));
        mAhBottomNavigation.setCurrentItem(0);

        mAhBottomNavigation.setBehaviorTranslationEnabled(true);


        mAhBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener()
        {

            @Override
            public void onTabSelected(int position, boolean wasSelected)
            {

                if (currentTabIndex != position)
                {
                    FragmentTransaction trx = getFragmentManager().beginTransaction();
                    trx.hide(fragments.get(currentTabIndex));
                    if (!fragments.get(position).isAdded())
                    {
                        trx.add(R.id.content, fragments.get(position));
                    }
                    trx.show(fragments.get(position)).commit();
                }
                currentTabIndex = position;
            }
        });
    }

    @Override
    public void initToolBar()
    {

        mToolbar.setTitle("知了");
        setSupportActionBar(mToolbar);
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

        switch (item.getItemId())
        {
            case R.id.action_message:
                // 意见反馈
                startActivity(new Intent(MainActivity.this, MessageActivity.class));
                return true;

            case R.id.action_mode:
                //切换日夜间模式
                mNightModeHelper.toggle();
                return true;

            case R.id.action_settings:
                //设置
                return true;
            case R.id.action_about:
                //关于知了
                startActivity(new Intent(MainActivity.this, AppAboutActivity.class));
                return true;

            case R.id.action_about_me:
                //关于我
                startActivity(new Intent(MainActivity.this, HotBitmapGGInfoActivity.class));
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

    private void showFragment(Fragment fragment)
    {

        getFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }
}
