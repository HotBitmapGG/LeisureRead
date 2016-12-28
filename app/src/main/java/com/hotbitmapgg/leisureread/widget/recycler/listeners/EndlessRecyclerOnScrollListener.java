package com.hotbitmapgg.leisureread.widget.recycler.listeners;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG RecycleView上拉加载更多滑动监听器
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

  public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

  private int previousTotal = 0;

  private boolean loading = true;

  int lastCompletelyVisiableItemPosition, visibleItemCount, totalItemCount;

  private int currentPage = 1;

  private LinearLayoutManager mLinearLayoutManager;


  public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {

    this.mLinearLayoutManager = linearLayoutManager;
  }


  @Override
  public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

    super.onScrolled(recyclerView, dx, dy);

    visibleItemCount = recyclerView.getChildCount();
    totalItemCount = mLinearLayoutManager.getItemCount();
    lastCompletelyVisiableItemPosition
        = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

    if (loading) {
      if (totalItemCount > previousTotal) {
        loading = false;
        previousTotal = totalItemCount;
      }
    }
    if (!loading
        && (visibleItemCount > 0)
        && (lastCompletelyVisiableItemPosition >= totalItemCount - 1)) {
      currentPage++;
      onLoadMore(currentPage);
      loading = true;
    }
  }


  public abstract void onLoadMore(int currentPage);
}