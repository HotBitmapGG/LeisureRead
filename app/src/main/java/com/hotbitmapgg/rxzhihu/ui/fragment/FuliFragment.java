package com.hotbitmapgg.rxzhihu.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.adapter.AbsRecyclerViewAdapter;
import com.hotbitmapgg.rxzhihu.adapter.FuliRecycleAdapter;
import com.hotbitmapgg.rxzhihu.base.LazyFragment;
import com.hotbitmapgg.rxzhihu.model.FuliItem;
import com.hotbitmapgg.rxzhihu.model.FuliResult;
import com.hotbitmapgg.rxzhihu.network.RetrofitHelper;
import com.hotbitmapgg.rxzhihu.ui.activity.FuliFullPicActivity;
import com.hotbitmapgg.rxzhihu.utils.LogUtil;
import com.hotbitmapgg.rxzhihu.widget.refresh.EndlessRecyclerOnScrollListener;
import com.hotbitmapgg.rxzhihu.widget.refresh.HeaderViewRecyclerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/4/5.
 * <p/>
 * 妹子福利界面
 */
public class FuliFragment extends LazyFragment
{


    @Bind(R.id.recylce)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private int pageNum = 1;

    private List<FuliItem> datas = new ArrayList<>();

    private HeaderViewRecyclerAdapter mRecyclerAdapter;

    private FuliRecycleAdapter mAdapter;

    private View footLayout;

    public static FuliFragment newInstance()
    {

        return new FuliFragment();
    }

    @Override
    public int getLayoutId()
    {

        return R.layout.fragment_fuli;
    }

    @Override
    public void initViews()
    {

        showProgress();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {

            @Override
            public void onRefresh()
            {

                getBeautys();
                new Handler().postDelayed(new Runnable()
                {

                    @Override
                    public void run()
                    {

                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    private void getBeautys()
    {

        RetrofitHelper.getFuliApi().getFulis(20, pageNum)
                .map(new Func1<FuliResult,List<FuliItem>>()
                {

                    @Override
                    public List<FuliItem> call(FuliResult fuliResult)
                    {

                        List<FuliResult.FuliBean> fulis = fuliResult.fulis;
                        List<FuliItem> items = new ArrayList<FuliItem>(fulis.size());
                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
                        SimpleDateFormat outputFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
                        int size = fulis.size();
                        FuliItem fuliItem = null;
                        for (int i = 0; i < size; i++)
                        {
                            try
                            {
                                fuliItem = new FuliItem();
                                Date date = inputFormat.parse(fulis.get(i).createdAt);
                                fuliItem.description = outputFormat.format(date);
                                fuliItem.imageUrl = fulis.get(i).url;
                            } catch (ParseException e)
                            {
                                e.printStackTrace();
                            }
                            items.add(fuliItem);
                        }
                        return items;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<FuliItem>>()
                {

                    @Override
                    public void call(List<FuliItem> fuliItems)
                    {

                        if (fuliItems != null && fuliItems.size() > 0)
                        {
                            datas.addAll(fuliItems);
                            finishGetFulis();
                        } else
                        {
                            hideProgress();
                        }
                    }
                }, new Action1<Throwable>()
                {

                    @Override
                    public void call(Throwable throwable)
                    {

                        hideProgress();
                        LogUtil.all("数据加载失败" + throwable.getMessage());
                    }
                });
    }

    private void finishGetFulis()
    {

        hideProgress();
        mRecyclerView.setHasFixedSize(true);
        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FuliRecycleAdapter(mRecyclerView, datas);
        mRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        createFootLayout();
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
        {

            @Override
            public int getSpanSize(int position)
            {

                return ((mRecyclerAdapter.getItemCount() - 1) == position) ? mLayoutManager.getSpanCount() : 1;
            }
        });
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager)
        {

            @Override
            public void onLoadMore(int i)
            {

                pageNum++;
                loadMoreFulis();
            }
        });

        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener()
        {

            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder)
            {

                //Activity跳转动画 界面共享元素的使用
                Intent intent = FuliFullPicActivity.LuanchActivity(getActivity(), datas.get(position).imageUrl, datas.get(position).description);
                ActivityOptionsCompat mActivityOptionsCompat;
                if (Build.VERSION.SDK_INT >= 21)
                {
                    mActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            getActivity(), holder.getParentView().findViewById(R.id.item_img), FuliFullPicActivity.TRANSIT_PIC);
                } else
                {
                    mActivityOptionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(
                            holder.getParentView().findViewById(R.id.item_img), 0, 0,
                            holder.getParentView().findViewById(R.id.item_img).getWidth(),
                            holder.getParentView().findViewById(R.id.item_img).getHeight());
                }

                startActivity(intent, mActivityOptionsCompat.toBundle());
            }
        });
    }

    private void createFootLayout()
    {

        footLayout = LayoutInflater.from(getActivity()).inflate(R.layout.load_more_foot_layout, mRecyclerView, false);
        mRecyclerAdapter.addFooterView(footLayout);
    }

    private void loadMoreFulis()
    {

        RetrofitHelper.getFuliApi().getFulis(20, pageNum)
                .map(new Func1<FuliResult,List<FuliItem>>()
                {

                    @Override
                    public List<FuliItem> call(FuliResult fuliResult)
                    {

                        List<FuliResult.FuliBean> fulis = fuliResult.fulis;
                        List<FuliItem> items = new ArrayList<FuliItem>(fulis.size());
                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
                        SimpleDateFormat outputFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
                        int size = fulis.size();
                        FuliItem fuliItem = null;
                        for (int i = 0; i < size; i++)
                        {
                            try
                            {
                                fuliItem = new FuliItem();
                                Date date = inputFormat.parse(fulis.get(i).createdAt);
                                fuliItem.description = outputFormat.format(date);
                                fuliItem.imageUrl = fulis.get(i).url;
                            } catch (ParseException e)
                            {
                                e.printStackTrace();
                            }
                            items.add(fuliItem);
                        }
                        return items;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<FuliItem>>()
                {

                    @Override
                    public void call(List<FuliItem> fuliItems)
                    {

                        if (fuliItems != null && fuliItems.size() > 0)
                        {
                            int size = fuliItems.size();
                            if (size < 20)
                            {
                                footLayout.setVisibility(View.GONE);
                            }
                            for (int i = 0; i < size; i++)
                            {
                                mAdapter.addData(fuliItems.get(i));
                                mRecyclerAdapter.notifyDataSetChanged();
                            }
                        } else
                        {
                            mRecyclerAdapter.notifyDataSetChanged();
                            footLayout.setVisibility(View.GONE);
                        }
                    }
                }, new Action1<Throwable>()
                {

                    @Override
                    public void call(Throwable throwable)
                    {

                        mRecyclerAdapter.notifyDataSetChanged();
                        footLayout.setVisibility(View.GONE);
                        LogUtil.all("数据加载失败" + throwable.getMessage());
                    }
                });
    }

    public void showProgress()
    {

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.post(new Runnable()
        {

            @Override
            public void run()
            {

                mSwipeRefreshLayout.setRefreshing(true);
                getBeautys();
            }
        });
    }

    public void hideProgress()
    {

        mSwipeRefreshLayout.post(new Runnable()
        {

            @Override
            public void run()
            {

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
