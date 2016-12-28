package com.hotbitmapgg.leisureread.ui.fragment;

import butterknife.Bind;
import com.hotbitmapgg.leisureread.app.AppConstant;
import com.hotbitmapgg.leisureread.ui.fragment.base.BaseFragment;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyCommentInfo;
import com.hotbitmapgg.leisureread.network.RetrofitHelper;
import com.hotbitmapgg.leisureread.ui.adapter.CommentAdapter;
import com.hotbitmapgg.rxzhihu.R;
import java.util.ArrayList;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

  @Bind(R.id.recycle)
  RecyclerView mRecyclerView;

  private int id;

  private List<DailyCommentInfo.CommentsBean> shortCommentInfos = new ArrayList<>();


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

    getShortComment();
  }


  @Override
  public void initData() {

  }


  private void getShortComment() {

    RetrofitHelper.builder().getDailyShortCommentById(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(dailyComment -> {

          if (dailyComment != null) {
            List<DailyCommentInfo.CommentsBean> comments = dailyComment.getComments();
            if (comments != null && comments.size() > 0) {
              shortCommentInfos.addAll(comments);
              finishGetShortComment();
            }
          }
        }, throwable -> {

        });
  }


  private void finishGetShortComment() {

    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    CommentAdapter mAdapter = new CommentAdapter(mRecyclerView, shortCommentInfos);
    mRecyclerView.setAdapter(mAdapter);
  }
}
