package com.hotbitmapgg.leisureread.ui.activity;

import butterknife.Bind;
import com.bumptech.glide.Glide;
import com.hotbitmapgg.leisureread.app.AppConstant;
import com.hotbitmapgg.leisureread.mvp.model.entity.ThemeDetailsInfo;
import com.hotbitmapgg.leisureread.network.RetrofitHelper;
import com.hotbitmapgg.leisureread.ui.activity.base.BaseAppCompatActivity;
import com.hotbitmapgg.leisureread.ui.adapter.ThemeDetailsHeadAdapter;
import com.hotbitmapgg.leisureread.ui.adapter.ThemeDetailsStoriesAdapter;
import com.hotbitmapgg.leisureread.utils.LogUtil;
import com.hotbitmapgg.leisureread.widget.CircleProgressView;
import com.hotbitmapgg.leisureread.widget.recycler.helper.HeaderViewRecyclerAdapter;
import com.hotbitmapgg.rxzhihu.R;
import java.util.ArrayList;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 主题日报界面
 */
public class ThemeDailyDetailsActivity extends BaseAppCompatActivity {

  @Bind(R.id.toolbar)
  Toolbar mToolbar;

  @Bind(R.id.circle_progress)
  CircleProgressView mCircleProgressView;

  @Bind(R.id.recycle)
  RecyclerView mRecyclerView;

  @Bind(R.id.swipe_refresh)
  SwipeRefreshLayout mSwipeRefreshLayout;

  //主题日报故事列表
  private List<ThemeDetailsInfo.StoriesBean> stories = new ArrayList<>();

  //主题日报主编列表
  private List<ThemeDetailsInfo.EditorsBean> editors = new ArrayList<>();

  private int id;

  private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;


  @Override
  public int getLayoutId() {

    return R.layout.activity_type_daily;
  }


  @Override
  public void initViews(Bundle savedInstanceState) {
    Intent intent = getIntent();
    if (intent != null) {
      id = intent.getIntExtra(AppConstant.EXTRA_TYPE, -1);
    }

    startGetThemesDetails();
  }


  private void startGetThemesDetails() {

    mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
    mSwipeRefreshLayout.setOnRefreshListener(() -> mSwipeRefreshLayout.setRefreshing(false));
    mCircleProgressView.setVisibility(View.VISIBLE);
    mCircleProgressView.spin();
    mRecyclerView.setVisibility(View.GONE);
    getThemesDetails();
  }


  private void getThemesDetails() {

    RetrofitHelper.builder().getThemesDetailsById(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(themesDetails -> {

          if (themesDetails != null) {
            finishGetThemesDetails(themesDetails);
          }
        }, throwable -> {

          LogUtil.all("加载数据失败");
        });
  }


  private void finishGetThemesDetails(ThemeDetailsInfo themeDetailsInfo) {

    stories.addAll(themeDetailsInfo.getStories());
    editors.addAll(themeDetailsInfo.getEditors());

    mToolbar.setTitle(themeDetailsInfo.getName());

    mRecyclerView.setHasFixedSize(true);
    LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(
        ThemeDailyDetailsActivity.this);
    mRecyclerView.setLayoutManager(mLinearLayoutManager);
    ThemeDetailsStoriesAdapter mAdapter = new ThemeDetailsStoriesAdapter(mRecyclerView, stories);
    mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
    addHeadView(themeDetailsInfo);
    mRecyclerView.setAdapter(mHeaderViewRecyclerAdapter);
    mAdapter.setOnItemClickListener((position, holder) -> {

      ThemeDetailsInfo.StoriesBean stories1 = ThemeDailyDetailsActivity.this.stories.get(position);
      DailyDetailActivity.lanuch(ThemeDailyDetailsActivity.this, stories1.getId());
    });

    new Handler().postDelayed(() -> {

      mCircleProgressView.setVisibility(View.GONE);
      mCircleProgressView.stopSpinning();
      mRecyclerView.setVisibility(View.VISIBLE);
    }, 3000);
  }


  private void addHeadView(ThemeDetailsInfo themeDetailsInfo) {

    View headView = LayoutInflater.from(ThemeDailyDetailsActivity.this)
        .inflate(R.layout.layout_themes_details_head, mRecyclerView, false);
    ImageView mThemesBg = (ImageView) headView.findViewById(R.id.type_image);
    TextView mThemesTitle = (TextView) headView.findViewById(R.id.type_title);
    Glide.with(ThemeDailyDetailsActivity.this)
        .load(themeDetailsInfo.getBackground())
        .placeholder(R.drawable.account_avatar)
        .into(mThemesBg);
    mThemesTitle.setText(themeDetailsInfo.getDescription());
    View editorsHeadView = LayoutInflater.from(ThemeDailyDetailsActivity.this)
        .inflate(R.layout.layout_themes_details_head_2, mRecyclerView, false);
    RecyclerView mHeadRecycle = (RecyclerView) editorsHeadView.findViewById(R.id.head_recycle);
    mHeadRecycle.setHasFixedSize(true);
    LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(
        ThemeDailyDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
    mHeadRecycle.setLayoutManager(mLinearLayoutManager);
    ThemeDetailsHeadAdapter mHeadAdapter = new ThemeDetailsHeadAdapter(mHeadRecycle, editors);
    mHeadRecycle.setAdapter(mHeadAdapter);
    mHeadAdapter.setOnItemClickListener((position, holder) -> {

      ThemeDetailsInfo.EditorsBean editor = ThemeDailyDetailsActivity.this.editors.get(position);
      int id1 = editor.getId();
      String name = editor.getName();
      EditorInfoActivity.launch(ThemeDailyDetailsActivity.this, id1, name);
    });
    mHeaderViewRecyclerAdapter.addHeaderView(headView);
    mHeaderViewRecyclerAdapter.addHeaderView(editorsHeadView);
    mHeaderViewRecyclerAdapter.notifyDataSetChanged();
    mHeadAdapter.notifyDataSetChanged();
  }


  @Override
  public void initToolBar() {
    mToolbar.setNavigationIcon(R.drawable.ic_action_back);
    mToolbar.setNavigationOnClickListener(view -> onBackPressed());
    mToolbar.setTitleTextColor(getResources().getColor(R.color.black_90));
  }


  public static void launch(Activity activity, int id) {

    Intent mIntent = new Intent(activity, ThemeDailyDetailsActivity.class);
    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    mIntent.putExtra(AppConstant.EXTRA_TYPE, id);
    activity.startActivity(mIntent);
  }
}
