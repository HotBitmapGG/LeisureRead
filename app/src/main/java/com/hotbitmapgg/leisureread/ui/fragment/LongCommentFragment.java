package com.hotbitmapgg.leisureread.ui.fragment;

import butterknife.Bind;
import com.hotbitmapgg.leisureread.app.AppConstant;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyCommentInfo;
import com.hotbitmapgg.leisureread.network.RetrofitHelper;
import com.hotbitmapgg.leisureread.ui.adapter.CommentAdapter;
import com.hotbitmapgg.leisureread.ui.fragment.base.BaseFragment;
import com.hotbitmapgg.leisureread.widget.EmptyView;
import com.hotbitmapgg.rxzhihu.R;
import java.util.ArrayList;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 日报的长评论界面
 */
public class LongCommentFragment extends BaseFragment {

  @Bind(R.id.recycler_view)
  RecyclerView mRecyclerView;

  @Bind(R.id.empty_view)
  EmptyView mEmptyView;

  private int id;

  private CommentAdapter mAdapter;

  private List<DailyCommentInfo.CommentsBean> mLongComments = new ArrayList<>();


  public static LongCommentFragment newInstance(int id) {

    LongCommentFragment mLongCommentFragment = new LongCommentFragment();
    Bundle mBundle = new Bundle();
    mBundle.putInt(AppConstant.EXTRA_LONG_COMMENT_ID, id);
    mLongCommentFragment.setArguments(mBundle);

    return mLongCommentFragment;
  }


  @Override
  public int getLayoutId() {

    return R.layout.fragment_long_comment;
  }


  @Override
  public void initViews() {

    Bundle bundle = getArguments();
    id = bundle.getInt(AppConstant.EXTRA_LONG_COMMENT_ID);

    initRecyclerView();
  }


  private void initRecyclerView() {
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    mAdapter = new CommentAdapter(mRecyclerView);
    mRecyclerView.setAdapter(mAdapter);
  }


  @Override
  public void initData() {
    RetrofitHelper.builder().getDailyLongCommentById(id)
        .compose(bindToLifecycle())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(dailyComment -> {
          mLongComments.addAll(dailyComment.getComments());
          finishTask();
        }, throwable -> {
          mEmptyView.setVisibility(View.VISIBLE);
        });
  }


  private void finishTask() {

    if (mLongComments.isEmpty()) {
      mEmptyView.setVisibility(View.VISIBLE);
    }
    mAdapter.setDataSources(mLongComments);
    mAdapter.notifyDataSetChanged();
  }
}
