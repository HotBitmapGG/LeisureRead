package com.hotbitmapgg.leisureread.ui.activity;

import butterknife.Bind;
import com.hotbitmapgg.leisureread.app.AppConstant;
import com.hotbitmapgg.leisureread.ui.activity.base.BaseAppCompatActivity;
import com.hotbitmapgg.leisureread.ui.fragment.LongCommentFragment;
import com.hotbitmapgg.leisureread.ui.fragment.ShortCommentFragment;
import com.hotbitmapgg.rxzhihu.R;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 日报评论界面
 */
public class DailyCommentActivity extends BaseAppCompatActivity {

  @Bind(R.id.sliding_tabs)
  TabLayout mSlidingTabLayout;

  @Bind(R.id.view_pager)
  ViewPager mViewPager;

  @Bind(R.id.toolbar)
  Toolbar mToolbar;

  private List<String> titles = new ArrayList<>();

  private List<Fragment> fragmentList = new ArrayList<>();

  private int id;

  private int commentNum;

  private int longCommentNum;

  private int shortCommentNum;


  @Override
  public int getLayoutId() {

    return R.layout.activity_daily_comment;
  }


  @Override
  public void initViews(Bundle savedInstanceState) {

    Intent intent = getIntent();
    if (intent != null) {
      id = intent.getIntExtra(AppConstant.EXTRA_COMMENT_ID, -1);
      commentNum = intent.getIntExtra(AppConstant.EXTRA_COMMENT_NUM, 0);
      longCommentNum = intent.getIntExtra(AppConstant.EXTRA_LONG_COMMENT_NUM, 0);
      shortCommentNum = intent.getIntExtra(AppConstant.EXTRA_SHORT_COMMENT_NUM, 0);
    }

    titles.add("长评论" + " (" + longCommentNum + ")");
    titles.add("短评论" + " (" + shortCommentNum + ")");
    LongCommentFragment longCommentFragment = LongCommentFragment.newInstance(id);
    ShortCommentFragment shortCommentFragment = ShortCommentFragment.newInstance(id);
    fragmentList.add(longCommentFragment);
    fragmentList.add(shortCommentFragment);

    CommentPagerAdapter mAdapter = new CommentPagerAdapter(getSupportFragmentManager(),
        fragmentList, titles);
    mViewPager.setAdapter(mAdapter);
    mSlidingTabLayout.setupWithViewPager(mViewPager);
  }


  @Override
  public void initToolBar() {

    mToolbar.setTitleTextColor(getResources().getColor(R.color.black_90));
    mToolbar.setTitle(commentNum + "条点评");
    mToolbar.setNavigationIcon(R.drawable.ic_action_back);
    mToolbar.setNavigationOnClickListener(view -> onBackPressed());
  }


  public static void launch(Activity activity, int id, int num, int longCommentNum, int shortCommentNum) {

    Intent mIntent = new Intent(activity, DailyCommentActivity.class);
    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    mIntent.putExtra(AppConstant.EXTRA_COMMENT_ID, id);
    mIntent.putExtra(AppConstant.EXTRA_COMMENT_NUM, num);
    mIntent.putExtra(AppConstant.EXTRA_LONG_COMMENT_NUM, longCommentNum);
    mIntent.putExtra(AppConstant.EXTRA_SHORT_COMMENT_NUM, shortCommentNum);
    activity.startActivity(mIntent);
  }


  public static class CommentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;

    private List<String> titles;


    CommentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titles) {

      super(fm);
      this.fragmentList = fragmentList;
      this.titles = titles;
    }


    @Override
    public Fragment getItem(int position) {

      return fragmentList.get(position);
    }


    @Override
    public CharSequence getPageTitle(int position) {

      return titles.get(position);
    }


    @Override
    public int getCount() {

      return titles.size();
    }
  }
}
