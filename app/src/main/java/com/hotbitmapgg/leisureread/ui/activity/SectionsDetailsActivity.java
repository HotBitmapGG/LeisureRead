package com.hotbitmapgg.leisureread.ui.activity;

import butterknife.Bind;
import com.hotbitmapgg.leisureread.app.AppConstant;
import com.hotbitmapgg.leisureread.mvp.model.entity.SectionsDetailsInfo;
import com.hotbitmapgg.leisureread.network.RetrofitHelper;
import com.hotbitmapgg.leisureread.ui.activity.base.BaseAppCompatActivity;
import com.hotbitmapgg.leisureread.ui.adapter.SectionsDetailsAdapter;
import com.hotbitmapgg.leisureread.widget.recycler.listeners.AutoLoadOnScrollListener;
import com.hotbitmapgg.rxzhihu.R;
import java.util.ArrayList;
import java.util.List;
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

  private List<SectionsDetailsInfo.StoriesBean> sectionsDetailsInfos = new ArrayList<>();

  private LinearLayoutManager mLinearLayoutManager;

  private SectionsDetailsAdapter mAdapter;

  private long timetemp;


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

    mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    mRecyclerView.setHasFixedSize(true);
    mLinearLayoutManager = new LinearLayoutManager(SectionsDetailsActivity.this);
    mRecyclerView.setLayoutManager(mLinearLayoutManager);
    mSwipeRefreshLayout.setOnRefreshListener(this::getSectionsDetails);

    getSectionsDetails();
  }


  private void getSectionsDetails() {

    RetrofitHelper.getLastZhiHuApi().getSectionsDetails(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(sectionsDetails -> {

          if (sectionsDetails != null) {
            mToolbar.setTitle(sectionsDetails.getName());
            timetemp = sectionsDetails.getTimestamp();
            List<SectionsDetailsInfo.StoriesBean> stories = sectionsDetails.getStories();
            if (stories != null && stories.size() > 0) {
              sectionsDetailsInfos.clear();
              sectionsDetailsInfos.addAll(stories);
              finishGetSectionsDetails();
              mSwipeRefreshLayout.setRefreshing(false);
            }
          }
        }, throwable -> {

        });
  }


  private void finishGetSectionsDetails() {

    mAdapter = new SectionsDetailsAdapter(mRecyclerView, sectionsDetailsInfos);
    mRecyclerView.setAdapter(mAdapter);
    mRecyclerView.addOnScrollListener(new AutoLoadOnScrollListener(mLinearLayoutManager) {

      @Override
      public void onLoadMore(int currentPage) {

        loadMore(timetemp);
      }
    });

    mAdapter.setOnItemClickListener((position, holder) -> {

      SectionsDetailsInfo.StoriesBean sectionsDetailsInfo = sectionsDetailsInfos.get(position);
      int id1 = sectionsDetailsInfo.getId();
      DailyDetailsActivity.lanuch(SectionsDetailsActivity.this, id1);
    });
  }


  public void loadMore(long timestamp) {

    RetrofitHelper.getLastZhiHuApi().getBeforeSectionsDetails(id, timestamp)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(sectionsDetails -> {

          if (sectionsDetails != null) {
            List<SectionsDetailsInfo.StoriesBean> stories = sectionsDetails.getStories();
            if (stories != null && stories.size() > 0) {
              int size = stories.size();
              for (int i = 0; i < size; i++) {
                SectionsDetailsInfo.StoriesBean sectionsDetailsInfo = stories.get(i);
                mAdapter.addData(sectionsDetailsInfo);
                mAdapter.notifyDataSetChanged();
              }
            }
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
