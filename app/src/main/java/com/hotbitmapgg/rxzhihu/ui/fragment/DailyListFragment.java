package com.hotbitmapgg.rxzhihu.ui.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.adapter.AutoLoadOnScrollListener;
import com.hotbitmapgg.rxzhihu.adapter.DailyListAdapter;
import com.hotbitmapgg.rxzhihu.adapter.MainViewPagerAdapter;
import com.hotbitmapgg.rxzhihu.base.LazyFragment;
import com.hotbitmapgg.rxzhihu.db.DailyDao;
import com.hotbitmapgg.rxzhihu.model.DailyBean;
import com.hotbitmapgg.rxzhihu.model.DailyDetail;
import com.hotbitmapgg.rxzhihu.model.DailyListBean;
import com.hotbitmapgg.rxzhihu.model.TopDailys;
import com.hotbitmapgg.rxzhihu.network.RetrofitHelper;
import com.hotbitmapgg.rxzhihu.ui.activity.MainActivity;
import com.hotbitmapgg.rxzhihu.utils.LogUtil;
import com.hotbitmapgg.rxzhihu.utils.NetWorkUtil;
import com.hotbitmapgg.rxzhihu.widget.CircleIndicator;
import com.hotbitmapgg.rxzhihu.widget.CircleProgressView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 11 on 2016/4/1.
 * <p/>
 * 日报列表界面
 */
public class DailyListFragment extends LazyFragment implements Runnable
{

    @Bind(R.id.daily_recycle)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    public static final String TAG = DailyListFragment.class.getSimpleName();

    private List<DailyBean> dailys = new ArrayList<>();

    private String currentTime = "";

    private DailyListAdapter mAdapter;

    private AutoLoadOnScrollListener mAutoLoadOnScrollListener;

    private MainViewPagerAdapter mMainViewPagerAdapter;

    private Handler mHandler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {

            super.handleMessage(msg);
            if (msg.what == 0)
            {
                getLatesDailys(true);
            } else if (msg.what == 1)
            {
                hideProgress();
                mSwipeRefreshLayout.setRefreshing(false);
                finishGetDaily();
            }
        }
    };

    private Toolbar toolBar;

    private ViewPager mViewPager;

    private CircleIndicator mCircleIndicator;

    private int mPagerPosition = 0;

    private boolean mIsUserTouched = false;

    private int size;

    private Timer mTimer;

    private BannerTask mTimerTask;

    private RecyclerViewHeader mRecyclerViewHeader;

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

        toolBar = ((MainActivity) getActivity()).getToolBar();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {

            @Override
            public void onRefresh()
            {

                mHandler.sendEmptyMessageDelayed(0, 2000);
            }
        });


        mAdapter = new DailyListAdapter(getActivity(), dailys);
        final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAutoLoadOnScrollListener = new AutoLoadOnScrollListener(mLinearLayoutManager)
        {

            @Override
            public void onLoadMore(int currentPage)
            {

                loadMoreDaily(currentTime);
                //toolBar.setTitle(DateUtil.formatDate(currentTime) + WeekUtil.getWeek(currentTime));
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {

                super.onScrolled(recyclerView, dx, dy);
                //int firstPos = (recyclerView == null || recyclerView.getChildCount() == 0 ? 0 : recyclerView.getChildAt(0).getTop());

                mSwipeRefreshLayout.setEnabled(mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            }
        };
        mRecyclerView.addOnScrollListener(mAutoLoadOnScrollListener);
        mRecyclerViewHeader = RecyclerViewHeader.fromXml(getActivity(), R.layout.recycle_head_layout);
        mRecyclerViewHeader.attachTo(mRecyclerView);
        mViewPager = (ViewPager) mRecyclerViewHeader.findViewById(R.id.main_view_pager);
        mCircleIndicator = (CircleIndicator) mRecyclerViewHeader.findViewById(R.id.pager_indicator);
        mViewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE)
                {
                    mIsUserTouched = true;
                    mSwipeRefreshLayout.setEnabled(false);
                }
                else if (action == MotionEvent.ACTION_UP)
                {
                    mIsUserTouched = false;
                }
                else if(action == MotionEvent.ACTION_CANCEL)
                {
                    mSwipeRefreshLayout.setEnabled(true);
                }
                return false;
            }
        });
        mRecyclerViewHeader.setVisibility(View.GONE);
        getLatesDailys(false);
    }


    public void getLatesDailys(final boolean isDownRefresh)
    {

        RetrofitHelper.builder().getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0()
                {

                    @Override
                    public void call()
                    {

                        if (!isDownRefresh)
                        {
                            showProgress();
                        }
                    }
                })
                .map(new Func1<DailyListBean,DailyListBean>()
                {

                    @Override
                    public DailyListBean call(DailyListBean dailyListBean)
                    {

                        cacheAllDetail(dailyListBean.getStories());
                        return changeReadState(dailyListBean);
                    }
                })
                .subscribe(new Action1<DailyListBean>()
                {

                    @Override
                    public void call(DailyListBean dailyListBean)
                    {


                        if (dailyListBean.getStories() == null)
                        {
                            hideProgress();
                            mSwipeRefreshLayout.setRefreshing(false);
                            LogUtil.all("加载数据失败");
                        } else
                        {
                            LogUtil.all(dailyListBean.getStories().toString());
                            mAdapter.updateData(dailyListBean.getStories());
                            currentTime = dailyListBean.getDate();
                            if (dailyListBean.getStories().size() < 8)
                            {
                                loadMoreDaily(DailyListFragment.this.currentTime);
                            }
                            List<TopDailys> tops = dailyListBean.getTop_stories();
                            mMainViewPagerAdapter = new MainViewPagerAdapter(getActivity() , tops);
                            mViewPager.setAdapter(mMainViewPagerAdapter);
                            mCircleIndicator.setViewPager(mViewPager);
                            size = tops.size();
                            mHandler.sendEmptyMessageDelayed(1, 2000);
                        }
                    }
                }, new Action1<Throwable>()
                {

                    @Override
                    public void call(Throwable throwable)
                    {

                        hideProgress();
                        LogUtil.all("加载失败" + throwable.getMessage());
                    }
                });
    }

    private void showProgress()
    {

        mCircleProgressView.setVisibility(View.VISIBLE);
        mCircleProgressView.spin();
    }

    public void hideProgress()
    {

        mCircleProgressView.setVisibility(View.GONE);
        mCircleProgressView.stopSpinning();
    }


    private void finishGetDaily()
    {

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerViewHeader.setVisibility(View.VISIBLE);
        startViewPagerRun();
    }

    private void loadMoreDaily(final String currentTime)
    {

        RetrofitHelper.builder().getBeforeNews(currentTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<DailyListBean,DailyListBean>()
                {

                    @Override
                    public DailyListBean call(DailyListBean dailyListBean)
                    {

                        cacheAllDetail(dailyListBean.getStories());
                        return changeReadState(dailyListBean);
                    }
                })
                .subscribe(new Action1<DailyListBean>()
                {

                    @Override
                    public void call(DailyListBean dailyListBean)
                    {

                        mAutoLoadOnScrollListener.setLoading(false);
                        mAdapter.addData(dailyListBean.getStories());
                        DailyListFragment.this.currentTime = dailyListBean.getDate();
                    }
                }, new Action1<Throwable>()
                {

                    @Override
                    public void call(Throwable throwable)
                    {

                        mAutoLoadOnScrollListener.setLoading(false);
                        LogUtil.all("加载更多数据失败");
                    }
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

    /**
     * 缓存数据
     *
     * @param dailys
     */
    private void cacheAllDetail(List<DailyBean> dailys)
    {

        if (NetWorkUtil.isWifiConnected())
        {
            for (DailyBean daily : dailys)
            {
                cacheNewsDetail(daily.getId());
            }
        }
    }

    private void cacheNewsDetail(int newsId)
    {

        RetrofitHelper.builder().getNewsDetails(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Action1<DailyDetail>()
                {

                    @Override
                    public void call(DailyDetail dailyDetail)
                    {

                    }
                });
    }

    public void startViewPagerRun()
    {
        //执行ViewPager进行轮播
        mTimer = new Timer();
        mTimerTask = new BannerTask();
        mTimer.schedule(mTimerTask, 10000, 10000);

    }

    @Override
    public void run()
    {
        if (mPagerPosition == size - 1)
        {
            mViewPager.setCurrentItem(size - 1, false);
        }
        else
        {
            mViewPager.setCurrentItem(mPagerPosition);
        }
    }


    private class BannerTask extends TimerTask
    {

        @Override
        public void run()
        {
            if (!mIsUserTouched)
            {
                mPagerPosition = (mPagerPosition + 1) % size;
                if (getActivity() != null)
                {
                    getActivity().runOnUiThread(DailyListFragment.this);
                }

            }
        }
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        mTimer.cancel();
        mTimer = null;
        mTimerTask.cancel();
        mTimerTask = null;
    }
}
