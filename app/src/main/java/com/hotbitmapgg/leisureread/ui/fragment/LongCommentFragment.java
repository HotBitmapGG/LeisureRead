package com.hotbitmapgg.leisureread.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hotbitmapgg.leisureread.ui.fragment.base.BaseFragment;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyCommentInfo;
import com.hotbitmapgg.leisureread.network.RetrofitHelper;
import com.hotbitmapgg.leisureread.ui.adapter.CommentAdapter;
import com.hotbitmapgg.leisureread.widget.EmptyView;
import com.hotbitmapgg.rxzhihu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/4/23 12:18
 * 100332338@qq.com
 * <p/>
 * 日报对应长评论
 */
public class LongCommentFragment extends BaseFragment {

  @Bind(R.id.recycle)
  RecyclerView mRecyclerView;

  @Bind(R.id.empty)
  EmptyView mEmptyView;

  private static final String EXTRA_ID = "long_comment_id";

  private int id;

  private List<DailyCommentInfo.CommentInfo> longCommentinfos = new ArrayList<>();


  public static LongCommentFragment newInstance(int id) {

    LongCommentFragment mLongCommentFragment = new LongCommentFragment();
    Bundle mBundle = new Bundle();
    mBundle.putInt(EXTRA_ID, id);
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
    id = bundle.getInt(EXTRA_ID);

    getLongComment();
  }


  @Override
  public void initData() {

  }


  private void getLongComment() {

    RetrofitHelper.builder().getDailyLongCommentById(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(dailyComment -> {

          if (dailyComment != null) {
            List<DailyCommentInfo.CommentInfo> comments = dailyComment.comments;
            if (comments != null && comments.size() > 0) {
              longCommentinfos.addAll(comments);
              finishGetLongComment();
            } else {
              mEmptyView.setVisibility(View.VISIBLE);
            }
          }
        }, throwable -> {
          mEmptyView.setVisibility(View.VISIBLE);
        });
  }


  private void finishGetLongComment() {

    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    CommentAdapter mAdapter = new CommentAdapter(mRecyclerView, longCommentinfos);
    mRecyclerView.setAdapter(mAdapter);
  }
}
