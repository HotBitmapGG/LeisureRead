package com.hotbitmapgg.rxzhihu.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.adapter.CommentAdapter;
import com.hotbitmapgg.rxzhihu.base.LazyFragment;
import com.hotbitmapgg.rxzhihu.model.DailyComment;
import com.hotbitmapgg.rxzhihu.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/4/23 12:18
 * 100332338@qq.com
 * <p/>
 * 日报对应短评论
 */
public class ShortCommentFragment extends LazyFragment
{

    @Bind(R.id.recycle)
    RecyclerView mRecyclerView;

    private static final String EXTRA_ID = "short_comment_id";

    private int id;

    private List<DailyComment.CommentInfo> shortCommentInfos = new ArrayList<>();

    public static ShortCommentFragment newInstance(int id)
    {

        ShortCommentFragment mShortCommentFragment = new ShortCommentFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt(EXTRA_ID, id);
        mShortCommentFragment.setArguments(mBundle);

        return mShortCommentFragment;
    }

    @Override
    public int getLayoutId()
    {

        return R.layout.fragment_short_comment;
    }

    @Override
    public void initViews()
    {

        Bundle bundle = getArguments();
        id = bundle.getInt(EXTRA_ID);

        getShortComment();
    }

    private void getShortComment()
    {

        RetrofitHelper.builder().getDailyShortCommentById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DailyComment>()
                {

                    @Override
                    public void call(DailyComment dailyComment)
                    {

                        if (dailyComment != null)
                        {
                            List<DailyComment.CommentInfo> comments = dailyComment.comments;
                            if (comments != null && comments.size() > 0)
                            {
                                shortCommentInfos.addAll(comments);
                                finishGetShortComment();
                            }
                        }
                    }
                }, new Action1<Throwable>()
                {

                    @Override
                    public void call(Throwable throwable)
                    {

                    }
                });
    }

    private void finishGetShortComment()
    {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CommentAdapter mAdapter = new CommentAdapter(mRecyclerView, shortCommentInfos);
        mRecyclerView.setAdapter(mAdapter);
    }
}
