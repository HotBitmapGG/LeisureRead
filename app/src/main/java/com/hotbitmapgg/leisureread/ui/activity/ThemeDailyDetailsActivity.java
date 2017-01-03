package com.hotbitmapgg.leisureread.ui.activity;

import butterknife.Bind;
import com.bumptech.glide.Glide;
import com.hotbitmapgg.leisureread.app.AppConstant;
import com.hotbitmapgg.leisureread.mvp.model.entity.ThemeDetailsInfo;
import com.hotbitmapgg.leisureread.network.RetrofitHelper;
import com.hotbitmapgg.leisureread.rx.Rxutils;
import com.hotbitmapgg.leisureread.ui.activity.base.BaseAppCompatActivity;
import com.hotbitmapgg.leisureread.ui.adapter.ThemeDetailsEditorsAdapter;
import com.hotbitmapgg.leisureread.ui.adapter.ThemeDetailsStoriesAdapter;
import com.hotbitmapgg.leisureread.widget.CircleProgressView;
import com.hotbitmapgg.leisureread.widget.recycler.helper.HeaderViewRecyclerAdapter;
import com.hotbitmapgg.rxzhihu.R;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

  @Bind(R.id.recycler_view)
  RecyclerView mRecyclerView;

  private int id;

  private ThemeDetailsInfo mThemeDetailsInfo;

  private ThemeDetailsStoriesAdapter mAdapter;

  private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;

  //主题日报故事列表
  private List<ThemeDetailsInfo.StoriesBean> stories = new ArrayList<>();

  //主题日报主编列表
  private List<ThemeDetailsInfo.EditorsBean> editors = new ArrayList<>();


  @Override
  public int getLayoutId() {

    return R.layout.activity_theme_daily_details;
  }


  @Override
  public void initViews(Bundle savedInstanceState) {
    Intent intent = getIntent();
    if (intent != null) {
      id = intent.getIntExtra(AppConstant.EXTRA_TYPE, -1);
    }

    initRecyclerView();
    loadData();
  }


  private void initRecyclerView() {
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(ThemeDailyDetailsActivity.this));
    mAdapter = new ThemeDetailsStoriesAdapter(mRecyclerView);
    mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
    mRecyclerView.setAdapter(mHeaderViewRecyclerAdapter);
    mAdapter.setOnItemClickListener((position, holder) -> {

      ThemeDetailsInfo.StoriesBean stories1 = ThemeDailyDetailsActivity.this.stories.get(position);
      DailyDetailsActivity.lanuch(ThemeDailyDetailsActivity.this, stories1.getId());
    });
  }


  private void createHeadView() {
    View headView = LayoutInflater.from(ThemeDailyDetailsActivity.this)
        .inflate(R.layout.layout_themes_details_head, mRecyclerView, false);

    ImageView mThemesBg = (ImageView) headView.findViewById(R.id.type_image);
    TextView mThemesTitle = (TextView) headView.findViewById(R.id.type_title);

    //设置主题日报背景图片
    Glide.with(ThemeDailyDetailsActivity.this)
        .load(mThemeDetailsInfo.getBackground())
        .placeholder(R.drawable.account_avatar)
        .into(mThemesBg);

    //设置主题日报标题
    mThemesTitle.setText(mThemeDetailsInfo.getDescription());

    //设置主题日报编辑头像列表
    RecyclerView mEditorsRecyclerView = (RecyclerView) headView.findViewById(R.id.head_recycle);
    mEditorsRecyclerView.setHasFixedSize(true);
    mEditorsRecyclerView.setLayoutManager(
        new LinearLayoutManager(ThemeDailyDetailsActivity.this, LinearLayoutManager.HORIZONTAL,
            false));
    ThemeDetailsEditorsAdapter mEditorsAdapter = new ThemeDetailsEditorsAdapter(mEditorsRecyclerView);
    mEditorsRecyclerView.setAdapter(mEditorsAdapter);
    mEditorsAdapter.setOnItemClickListener((position, holder) ->
        EditorInfoActivity.launch(ThemeDailyDetailsActivity.this,
            editors.get(position).getId(),
            editors.get(position).getName()));

    //设置主题日报数据源
    mAdapter.setDataSources(stories);
    mAdapter.notifyDataSetChanged();
    //设置主题日报编辑数据源
    mEditorsAdapter.setDataSources(editors);
    mEditorsAdapter.notifyDataSetChanged();
    //刷新数据源
    mHeaderViewRecyclerAdapter.addHeaderView(headView);
    mHeaderViewRecyclerAdapter.notifyDataSetChanged();
  }


  private void loadData() {
    RetrofitHelper.builder().getThemesDetailsById(id)
        .compose(bindToLifecycle())
        .doOnSubscribe(this::showProgress)
        .delay(3000, TimeUnit.MILLISECONDS)
        .compose(Rxutils.normalSchedulers())
        .subscribe(themeDetailsInfo -> {
          mThemeDetailsInfo = themeDetailsInfo;
          finishTask();
        }, throwable -> {
          hideProgress();
        });
  }


  private void finishTask() {

    mToolbar.setTitle(mThemeDetailsInfo.getName());
    stories.addAll(mThemeDetailsInfo.getStories());
    editors.addAll(mThemeDetailsInfo.getEditors());
    hideProgress();
    createHeadView();
  }


  @Override
  public void initToolBar() {
    mToolbar.setNavigationIcon(R.drawable.ic_action_back);
    mToolbar.setNavigationOnClickListener(view -> onBackPressed());
    mToolbar.setTitleTextColor(getResources().getColor(R.color.black_90));
  }


  public void showProgress() {
    mCircleProgressView.setVisibility(View.VISIBLE);
    mCircleProgressView.spin();
    mRecyclerView.setVisibility(View.GONE);
  }


  public void hideProgress() {
    mCircleProgressView.setVisibility(View.GONE);
    mCircleProgressView.stopSpinning();
    mRecyclerView.setVisibility(View.VISIBLE);
  }


  public static void launch(Activity activity, int id) {

    Intent mIntent = new Intent(activity, ThemeDailyDetailsActivity.class);
    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    mIntent.putExtra(AppConstant.EXTRA_TYPE, id);
    activity.startActivity(mIntent);
  }
}
