package com.hotbitmapgg.eyepetizer.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.hotbitmapgg.eyepetizer.adapter.AutoLoadOnScrollListener;
import com.hotbitmapgg.eyepetizer.adapter.DailyListAdapter;
import com.hotbitmapgg.eyepetizer.base.LazyFragment;
import com.hotbitmapgg.eyepetizer.db.DailyDao;
import com.hotbitmapgg.eyepetizer.model.DailyBean;
import com.hotbitmapgg.eyepetizer.model.DailyListBean;
import com.hotbitmapgg.eyepetizer.model.TopDailys;
import com.hotbitmapgg.eyepetizer.network.RetrofitHelper;
import com.hotbitmapgg.eyepetizer.utils.LogUtil;
import com.hotbitmapgg.eyepetizer.widget.banner.BannerEntity;
import com.hotbitmapgg.eyepetizer.widget.banner.BannerView;
import com.hotbitmapgg.eyepetizer.widget.refresh.HeaderViewRecyclerAdapter;
import com.hotbitmapgg.rxzhihu.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 11 on 2016/4/1.
 * <p/>
 * 日报列表界面
 */
public class DailyListFragment extends LazyFragment
{

    @Bind(R.id.daily_recycle)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<DailyBean> dailys = new ArrayList<>();

    private String currentTime = "";

    private DailyListAdapter mAdapter;

    private AutoLoadOnScrollListener mAutoLoadOnScrollListener;

    private LinearLayoutManager mLinearLayoutManager;

    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;

    private List<BannerEntity> banners = new ArrayList<>();

    private BannerView mBannerView;

    private View headView;

    private List<TopDailys> tops;

    public static DailyListFragment newInstance()
    {

        return new DailyListFragment();
    }

    @Override
    public int getLayoutId()
    {

        return R.layout.fragment_daily_list;
    }

    @Override
    public void initViews()
    {

        mSwipeRefreshLayout.setColorSchemeResources(R.color.black_90);
        mSwipeRefreshLayout.post(() -> {

            mSwipeRefreshLayout.setRefreshing(true);
            getLatesDailys();
        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {

            clearData();
            getLatesDailys();
        });


        mAdapter = new DailyListAdapter(getActivity(), dailys);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAutoLoadOnScrollListener = new AutoLoadOnScrollListener(mLinearLayoutManager)
        {

            @Override
            public void onLoadMore(int currentPage)
            {

                loadMoreDaily(currentTime);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {

                super.onScrolled(recyclerView, dx, dy);

                mSwipeRefreshLayout.setEnabled(mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            }
        };
        mRecyclerView.addOnScrollListener(mAutoLoadOnScrollListener);
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.recycle_head_layout, mRecyclerView, false);

        mBannerView = (BannerView) headView.findViewById(R.id.banner);

        mHeaderViewRecyclerAdapter.addHeaderView(headView);
    }

    private void clearData()
    {

        banners.clear();
    }


    public void getLatesDailys()
    {

        RetrofitHelper.builder().getLatestNews()
                .compose(bindToLifecycle())
                .map(this::changeReadState)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dailyListBean -> {

                    mAdapter.updateData(dailyListBean.getStories());
                    currentTime = dailyListBean.getDate();
                    if (dailyListBean.getStories().size() < 8)
                        loadMoreDaily(DailyListFragment.this.currentTime);

                    tops = dailyListBean.getTop_stories();
                    finishGetDaily();
                }, throwable -> {


                    LogUtil.all("加载失败" + throwable.getMessage());
                });
    }


    private void finishGetDaily()
    {

        mSwipeRefreshLayout.setRefreshing(false);

        Observable.from(tops)
                .forEach(topDailys -> banners.add(new BannerEntity(topDailys.getGa_prefix(),
                        topDailys.getTitle(), topDailys.getImage())));
        mBannerView.delayTime(5).build(banners);
        mRecyclerView.setAdapter(mHeaderViewRecyclerAdapter);
    }

    private void loadMoreDaily(final String currentTime)
    {

        RetrofitHelper.builder().getBeforeNews(currentTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::changeReadState)
                .subscribe(dailyListBean -> {

                    mAutoLoadOnScrollListener.setLoading(false);
                    mAdapter.addData(dailyListBean.getStories());
                    DailyListFragment.this.currentTime = dailyListBean.getDate();
                }, throwable -> {

                    mAutoLoadOnScrollListener.setLoading(false);
                    LogUtil.all("加载更多数据失败");
                });
    }


    /**
     * 改变点击已阅读状态
     *
     * @param dailyList
     * @return
     */
    public DailyListBean changeReadState(DailyListBean dailyList)
    {

        List<String> allReadId = new DailyDao(getActivity()).getAllReadNew();
        for (DailyBean daily : dailyList.getStories())
        {
            daily.setDate(dailyList.getDate());
            for (String readId : allReadId)
            {
                if (readId.equals(daily.getId() + ""))
                {
                    daily.setRead(true);
                }
            }
        }
        return dailyList;
    }
}
