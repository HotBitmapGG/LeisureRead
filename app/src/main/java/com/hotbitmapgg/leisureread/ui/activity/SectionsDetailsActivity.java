package com.hotbitmapgg.leisureread.ui.activity;

import butterknife.Bind;
import com.annimon.stream.Stream;
import com.hotbitmapgg.leisureread.app.AppConstant;
import com.hotbitmapgg.leisureread.mvp.model.entity.SectionsDetailsInfo;
import com.hotbitmapgg.leisureread.network.RetrofitHelper;
import com.hotbitmapgg.leisureread.ui.activity.base.BaseAppCompatActivity;
import com.hotbitmapgg.leisureread.ui.adapter.SectionsDetailsAdapter;
import com.hotbitmapgg.leisureread.widget.recycler.listeners.AutoLoadOnScrollListener;
import com.hotbitmapgg.rxzhihu.R;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 专栏详情界面
 */
public class SectionsDetailsActivity extends BaseAppCompatActivity {

  @Bind(R.id.toolbar)
  Toolbar mToolbar;

  @Bind(R.id.swipe_refresh)
  SwipeRefreshLayout mSwipeRefreshLayout;

  @Bind(R.id.recycle)
  RecyclerView mRecyclerView;

  private int id;

  private long timetemp;

  private SectionsDetailsAdapter mAdapter;

  private LinearLayoutManager mLinearLayoutManager;

  private List<SectionsDetailsInfo.StoriesBean> sectionsDetailsInfos = new ArrayList<>();


  @Override
  public int getLayoutId() {

    return R.layout.activity_sections_details;
  }


  @Override
  public void initViews(Bundle savedInstanceState) {

    Intent intent = getIntent();
    if (intent != null) {
      id = intent.getIntExtra(AppConstant.EXTRA_ID, -1);
    }

    initSwipeRefreshLayout();
    initRecyclerView();
  }


  private void initSwipeRefreshLayout() {
    mSwipeRefreshLayout.setColorSchemeResources(R.color.black_90);
    mSwipeRefreshLayout.setOnRefreshListener(this::loadData);
    mSwipeRefreshLayout.post(() -> {
      mSwipeRefreshLayout.setRefreshing(true);
      loadData();
    });
  }


  private void initRecyclerView() {
    mRecyclerView.setHasFixedSize(true);
    mLinearLayoutManager = new LinearLayoutManager(SectionsDetailsActivity.this);
    mRecyclerView.setLayoutManager(mLinearLayoutManager);
  }


  private void loadData() {

    RetrofitHelper.getLastZhiHuApi().getSectionsDetails(id)
        .compose(bindToLifecycle())
        .delay(1000, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(sectionsDetails -> {

          mToolbar.setTitle(sectionsDetails.getName());
          timetemp = sectionsDetails.getTimestamp();
          List<SectionsDetailsInfo.StoriesBean> stories = sectionsDetails.getStories();
          if (stories != null && stories.size() > 0) {
            sectionsDetailsInfos.clear();
            sectionsDetailsInfos.addAll(stories);
            finishTask();
          }
        }, throwable -> {
          mSwipeRefreshLayout.setRefreshing(false);
        });
  }


  private void finishTask() {

    mSwipeRefreshLayout.setRefreshing(false);
    mAdapter = new SectionsDetailsAdapter(mRecyclerView, sectionsDetailsInfos);
    mRecyclerView.setAdapter(mAdapter);
    mRecyclerView.addOnScrollListener(new AutoLoadOnScrollListener(mLinearLayoutManager) {

      @Override
      public void onLoadMore(int currentPage) {

        loadMore(timetemp);
      }
    });

    mAdapter.setOnItemClickListener(
        (position, holder) -> DailyDetailsActivity.lanuch(SectionsDetailsActivity.this,
            sectionsDetailsInfos.get(position).getId()));
  }


  public void loadMore(long timestamp) {

    RetrofitHelper.getLastZhiHuApi().getBeforeSectionsDetails(id, timestamp)
        .compose(bindToLifecycle())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(sectionsDetails -> {

          List<SectionsDetailsInfo.StoriesBean> stories = sectionsDetails.getStories();
          if (stories != null && stories.size() > 0) {
            Stream.of(stories)
                .forEach(storiesBean -> mAdapter.addData(storiesBean));
          }
        }, throwable -> {

        });
  }


  @Override
  public void initToolBar() {

    mToolbar.setNavigationIcon(R.drawable.ic_action_back);
    mToolbar.setTitleTextColor(getResources().getColor(R.color.black_90));
    mToolbar.setNavigationOnClickListener(view -> onBackPressed());
  }


  public static void launch(Activity activity, int id) {

    Intent mIntent = new Intent(activity, SectionsDetailsActivity.class);
    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    mIntent.putExtra(AppConstant.EXTRA_ID, id);
    activity.startActivity(mIntent);
  }
}
