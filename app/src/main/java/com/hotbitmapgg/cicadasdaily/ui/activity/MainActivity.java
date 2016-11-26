package com.hotbitmapgg.cicadasdaily.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.hotbitmapgg.cicadasdaily.ui.fragment.SectionsFragment;
import com.hotbitmapgg.cicadasdaily.utils.StatusBarUtil;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.cicadasdaily.base.AbsBaseActivity;
import com.hotbitmapgg.cicadasdaily.ui.fragment.DailyListFragment;
import com.hotbitmapgg.cicadasdaily.ui.fragment.HotNewsFragment;
import com.hotbitmapgg.cicadasdaily.ui.fragment.ThemesDailyFragment;

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

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("日报", R.drawable.ic_profile_answer, R.color.black_90);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("主题", R.drawable.ic_profile_article, R.color.black_90);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("专栏", R.drawable.ic_profile_column, R.color.black_90);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("文章", R.drawable.ic_profile_favorite, R.color.black_90);

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
        mAhBottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.bg_color));


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

        mToolbar.setTitle("知了");
        setSupportActionBar(mToolbar);

        //设置6.0以上StatusBar字体颜色
        StatusBarUtil.from(this)
                .setLightStatusBar(true)
                .process();
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
//            case R.id.action_mode:
//                //切换日夜间模式
//                mNightModeHelper.toggle();
//                return true;

            case R.id.action_settings:
                //设置
                startActivity(new Intent(MainActivity.this, MoreActivity.class));
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

        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }
}
