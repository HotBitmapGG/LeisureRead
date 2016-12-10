package com.hotbitmapgg.eyepetizer.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.hotbitmapgg.eyepetizer.base.BaseActivity;
import com.hotbitmapgg.eyepetizer.view.fragment.DailyListFragment;
import com.hotbitmapgg.eyepetizer.view.fragment.SectionsFragment;
import com.hotbitmapgg.eyepetizer.view.fragment.ThemesDailyFragment;
import com.hotbitmapgg.eyepetizer.view.fragment.UserInfoFragment;
import com.hotbitmapgg.rxzhihu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by hcc on 2016/12/10 11:40
 * 100332338@qq.com
 * EyepetizerDaily
 *
 * @HotBitmapGG 开眼日报主界面
 */
public class MainActivity extends BaseActivity
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
        fragments.add(UserInfoFragment.newInstance());

        showFragment(fragments.get(0));
        initBottomNav();
    }

    @Override
    protected void attachBaseContext(Context newBase)
    {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void initBottomNav()
    {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("精选", R.drawable.ic_tab_strip_icon_feed_selected);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("发现", R.drawable.ic_tab_strip_icon_category_selected);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("专栏", R.drawable.ic_tab_strip_icon_follow_selected);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("我的", R.drawable.ic_tab_strip_icon_profile_selected);


        mAhBottomNavigation.addItem(item1);
        mAhBottomNavigation.addItem(item2);
        mAhBottomNavigation.addItem(item3);
        mAhBottomNavigation.addItem(item4);

        mAhBottomNavigation.setColored(false);
        mAhBottomNavigation.setForceTint(false);
        mAhBottomNavigation.setBehaviorTranslationEnabled(true);
        mAhBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        mAhBottomNavigation.setAccentColor(getResources().getColor(R.color.black_90));
        mAhBottomNavigation.setInactiveColor(getResources().getColor(R.color.nav_text_color_mormal));
        mAhBottomNavigation.setCurrentItem(0);
        mAhBottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.bottom_tab_bar_color));


        mAhBottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {

            if (currentTabIndex != position)
            {
                FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                trx.hide(fragments.get(currentTabIndex));
                if (!fragments.get(position).isAdded())
                {
                    trx.add(R.id.content, fragments.get(position));
                }
                trx.show(fragments.get(position)).commit();
            }
            currentTabIndex = position;

            return true;
        });
    }

    @Override
    public void initToolBar()
    {

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }


    private void showFragment(Fragment fragment)
    {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }
}
