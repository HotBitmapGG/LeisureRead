package com.hotbitmapgg.leisureread.ui.fragment;

import butterknife.Bind;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyInfo;
import com.hotbitmapgg.leisureread.network.RetrofitHelper;
import com.hotbitmapgg.leisureread.ui.adapter.DailyListAdapter;
import com.hotbitmapgg.leisureread.ui.fragment.base.BaseFragment;
import com.hotbitmapgg.leisureread.utils.LogUtil;
import com.hotbitmapgg.leisureread.widget.banner.BannerEntity;
import com.hotbitmapgg.leisureread.widget.banner.BannerView;
import com.hotbitmapgg.leisureread.widget.recycler.helper.HeaderViewRecyclerAdapter;
import com.hotbitmapgg.leisureread.widget.recycler.listeners.AutoLoadOnScrollListener;
import com.hotbitmapgg.rxzhihu.R;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 日报列表界面
 */
public class DailyFragment extends BaseFragment {

  @Bind(R.id.daily_recycle)
  RecyclerView mRecyclerView;

  @Bind(R.id.swipe_refresh)
  SwipeRefreshLayout mSwipeRefreshLayout;

  private List<DailyInfo.StoriesBean> stories = new ArrayList<>();

  private String currentTime = "";

  private DailyListAdapter mAdapter;

  private AutoLoadOnScrollListener mAutoLoadOnScrollListener;

  private LinearLayoutManager mLinearLayoutManager;

  private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;

  private List<BannerEntity> banners = new ArrayList<>();

  private BannerView mBannerView;

  private List<DailyInfo.TopStoriesBean> top_stories;


  public static DailyFragment newInstance() {

    return new DailyFragment();
  }


  @Override
  public int getLayoutId() {

    return R.layout.fragment_daily_list;
  }


  @Override
  public void initViews() {

    initSwipeRefreshLayout();
    initRecyclerView();
  }


  private void initRecyclerView() {
    mAdapter = new DailyListAdapter(getActivity(), stories);
    mLinearLayoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(mLinearLayoutManager);
    mAutoLoadOnScrollListener = new AutoLoadOnScrollListener(mLinearLayoutManager) {

      @Override
      public void onLoadMore(int currentPage) {

        loadMoreDaily(currentTime);
      }


      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        super.onScrolled(recyclerView, dx, dy);

        mSwipeRefreshLayout.setEnabled(
            mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
      }
    };
    mRecyclerView.addOnScrollListener(mAutoLoadOnScrollListener);
    mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
    View headView = LayoutInflater.from(getActivity())
        .inflate(R.layout.recycle_head_layout, mRecyclerView, false);

    mBannerView = (BannerView) headView.findViewById(R.id.banner);

    mHeaderViewRecyclerAdapter.addHeaderView(headView);
  }


  private void initSwipeRefreshLayout() {
    mSwipeRefreshLayout.setColorSchemeResources(R.color.black_90);
    mSwipeRefreshLayout.post(() -> {

      mSwipeRefreshLayout.setRefreshing(true);
      getLatesDailys();
    });
    mSwipeRefreshLayout.setOnRefreshListener(() -> {

      clearData();
      getLatesDailys();
    });
  }


  @Override
  public void initData() {

  }


  private void clearData() {

    banners.clear();
  }


  public void getLatesDailys() {

    RetrofitHelper.builder().getLatestNews()
        .compose(bindToLifecycle())
        .map(this::changeReadState)
        .delay(1000, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(dailyListBean -> {

          LogUtil.all(dailyListBean.getStories().get(0).getTitle());
          //mAdapter.updateData(dailyListBean.getStories());
          currentTime = dailyListBean.getDate();
          if (dailyListBean.getStories().size() < 8) {
            loadMoreDaily(DailyFragment.this.currentTime);
          }

          top_stories = dailyListBean.getTop_stories();
          stories.addAll(dailyListBean.getStories());
          finishGetDaily();
        }, throwable -> {

          LogUtil.all("加载失败" + throwable.getMessage());
        });
  }


  private void finishGetDaily() {

    mSwipeRefreshLayout.setRefreshing(false);

    Observable.from(top_stories)
        .forEach(topDailys -> banners.add(new BannerEntity(topDailys.getId(),
            topDailys.getTitle(), topDailys.getImage())));
    mBannerView.delayTime(5).build(banners);
    mRecyclerView.setAdapter(mHeaderViewRecyclerAdapter);
    mAdapter.notifyDataSetChanged();
  }


  private void loadMoreDaily(final String currentTime) {

    RetrofitHelper.builder().getBeforeNews(currentTime)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(this::changeReadState)
        .subscribe(dailyListBean -> {

          mAutoLoadOnScrollListener.setLoading(false);
          mAdapter.addData(dailyListBean.getStories());
          DailyFragment.this.currentTime = dailyListBean.getDate();
        }, throwable -> {

          mAutoLoadOnScrollListener.setLoading(false);
          LogUtil.all("加载更多数据失败");
        });
  }


  /**
   * 改变点击已阅读状态
   */
  public DailyInfo changeReadState(DailyInfo dailyList) {

    //List<String> allReadId = new DailyDao(getActivity()).getAllReadNew();

    for (DailyInfo.StoriesBean storiesBean : dailyList.getStories()) {
      storiesBean.setDate(dailyList.getDate());
      // for (String readId : allReadId) {
      //   if (readId.equals(String.valueOf(storiesBean.getId()))) {
      //     storiesBean.setRead(true);
      //   }
      // }
    }
    return dailyList;
  }
}
