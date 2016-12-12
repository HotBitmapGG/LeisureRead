package com.hotbitmapgg.eyepetizer.view.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hotbitmapgg.eyepetizer.base.BaseFragment;
import com.hotbitmapgg.eyepetizer.model.entity.HotNews;
import com.hotbitmapgg.eyepetizer.network.RetrofitHelper;
import com.hotbitmapgg.eyepetizer.view.activity.DailyDetailActivity;
import com.hotbitmapgg.eyepetizer.view.adapter.HotNewsAdapter;
import com.hotbitmapgg.rxzhihu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/4/23 13:39
 * 100332338@qq.com
 * <p/>
 * 热门文章推荐 每日20条
 */
public class HotNewsFragment extends BaseFragment
{

    @Bind(R.id.recycle)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<HotNews.HotNewsInfo> hotNewsInfos = new ArrayList<>();

    public static HotNewsFragment newInstance()
    {

        return new HotNewsFragment();
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
        mSwipeRefreshLayout.setOnRefreshListener(this::getHotNews);
        showProgress();
    }

    private void getHotNews()
    {

        RetrofitHelper.getLastZhiHuApi().getHotNews()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(hotNews -> {

                        List<HotNews.HotNewsInfo> recent = hotNews.recent;
                        if (recent != null && recent.size() > 0)
                        {
                            hotNewsInfos.clear();
                            hotNewsInfos.addAll(recent);
                            finishGetHotNews();
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                }, throwable -> {

                    mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(false));

                    Snackbar.make(mRecyclerView, "加载失败,请重新下拉刷新数据.", Snackbar.LENGTH_SHORT).show();
                });
    }

    private void finishGetHotNews()
    {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HotNewsAdapter mAdapter = new HotNewsAdapter(mRecyclerView);
        mAdapter.setDataSources(hotNewsInfos);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((position, holder) -> {

            HotNews.HotNewsInfo hotNewsInfo = hotNewsInfos.get(position);
            DailyDetailActivity.lanuch(getActivity(), hotNewsInfo.newsId);
        });
    }

    public void showProgress()
    {

        mSwipeRefreshLayout.postDelayed(() -> {

            mSwipeRefreshLayout.setRefreshing(true);
            getHotNews();
        }, 500);
    }
}
