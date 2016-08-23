package com.hotbitmapgg.rxzhihu.ui.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.adapter.AbsRecyclerViewAdapter;
import com.hotbitmapgg.rxzhihu.adapter.HotNewsAdapter;
import com.hotbitmapgg.rxzhihu.base.LazyFragment;
import com.hotbitmapgg.rxzhihu.model.HotNews;
import com.hotbitmapgg.rxzhihu.network.RetrofitHelper;
import com.hotbitmapgg.rxzhihu.ui.activity.DailyDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/4/23 13:39
 * 100332338@qq.com
 * <p/>
 * 热门文章推荐 每日20条
 */
public class HotNewsFragment extends LazyFragment
{

    @Bind(R.id.recycle)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<HotNews.HotNewsInfo> hotNewsInfos = new ArrayList<>();

    public static HotNewsFragment newInstance()
    {

        HotNewsFragment mHotNewsFragment = new HotNewsFragment();
        return mHotNewsFragment;
    }


    @Override
    public int getLayoutId()
    {

        return R.layout.fragment_hot_news;
    }

    @Override
    public void initViews()
    {

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {

            @Override
            public void onRefresh()
            {

                getHotNews();
            }
        });
        showProgress();
    }

    private void getHotNews()
    {

        RetrofitHelper.getLastZhiHuApi().getHotNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HotNews>()
                {

                    @Override
                    public void call(HotNews hotNews)
                    {

                        if (hotNews != null)
                        {
                            List<HotNews.HotNewsInfo> recent = hotNews.recent;
                            if (recent != null && recent.size() > 0)
                            {
                                hotNewsInfos.clear();
                                hotNewsInfos.addAll(recent);
                                finishGetHotNews();
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }
                }, new Action1<Throwable>()
                {

                    @Override
                    public void call(Throwable throwable)
                    {

                        mSwipeRefreshLayout.post(new Runnable()
                        {

                            @Override
                            public void run()
                            {

                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });

                        Snackbar.make(mRecyclerView, "加载失败,请重新下拉刷新数据.", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void finishGetHotNews()
    {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HotNewsAdapter mAdapter = new HotNewsAdapter(mRecyclerView, hotNewsInfos);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener()
        {

            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder)
            {

                HotNews.HotNewsInfo hotNewsInfo = hotNewsInfos.get(position);
                DailyDetailActivity.lanuch(getActivity(), hotNewsInfo.newsId);
            }
        });
    }

    public void showProgress()
    {

        mSwipeRefreshLayout.postDelayed(new Runnable()
        {

            @Override
            public void run()
            {

                mSwipeRefreshLayout.setRefreshing(true);
                getHotNews();
            }
        }, 500);
    }
}
