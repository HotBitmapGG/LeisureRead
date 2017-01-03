package com.hotbitmapgg.leisureread.ui.fragment;

import butterknife.Bind;
import com.hotbitmapgg.leisureread.app.AppConstant;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyCommentInfo;
import com.hotbitmapgg.leisureread.network.RetrofitHelper;
import com.hotbitmapgg.leisureread.rx.Rxutils;
import com.hotbitmapgg.leisureread.ui.adapter.CommentAdapter;
import com.hotbitmapgg.leisureread.ui.fragment.base.BaseFragment;
import com.hotbitmapgg.rxzhihu.R;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 短评论界面
 */
public class ShortCommentFragment extends BaseFragment {

  @Bind(R.id.recycler_view)
  RecyclerView mRecyclerView;

  private int id;

  private CommentAdapter mAdapter;

  private List<DailyCommentInfo.CommentsBean> mShortComments = new ArrayList<>();


  public static ShortCommentFragment newInstance(int id) {

    ShortCommentFragment mShortCommentFragment = new ShortCommentFragment();
    Bundle mBundle = new Bundle();
    mBundle.putInt(AppConstant.EXTRA_SHORT_COMMENT_ID, id);
    mShortCommentFragment.setArguments(mBundle);

    return mShortCommentFragment;
  }


  @Override
  public int getLayoutId() {

    return R.layout.fragment_short_comment;
  }


  @Override
  public void initViews() {

    Bundle bundle = getArguments();
    id = bundle.getInt(AppConstant.EXTRA_SHORT_COMMENT_ID);

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
    RetrofitHelper.builder().getDailyShortCommentById(id)
        .compose(Rxutils.normalSchedulers())
        .subscribe(dailyComment -> {

          mShortComments.addAll(dailyComment.getComments());
          finishTask();
        }, throwable -> {

        });
  }


  private void finishTask() {
    mAdapter.setDataSources(mShortComments);
    mAdapter.notifyDataSetChanged();
  }
}
