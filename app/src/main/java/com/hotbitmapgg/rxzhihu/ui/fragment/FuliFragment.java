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
 * 隐藏美女福利界面
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

        getBeautys();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
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
                        }
                    }
                }, new Action1<Throwable>()
                {

                    @Override
                    public void call(Throwable throwable)
                    {

                        LogUtil.all("数据加载失败" + throwable.getMessage());
                    }
                });
    }

    private void finishGetFulis()
    {

        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FuliRecycleAdapter(mRecyclerView, datas);
        mRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        createFootLayout();
        mRecyclerView.setAdapter(mRecyclerAdapter);
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
                            getActivity(), holder.itemView, FuliFullPicActivity.TRANSIT_PIC);
                } else
                {
                    mActivityOptionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(
                            holder.itemView, 0, 0, holder.itemView.getWidth(), holder.itemView.getHeight());
                }

                startActivity(intent, mActivityOptionsCompat.toBundle());


            }
        });
    }

    private void createFootLayout()
    {

        View footLayout = LayoutInflater.from(getActivity()).inflate(R.layout.load_more_foot_layout, mRecyclerView, false);
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
                            for (int i = 0; i < size; i++)
                            {
                                mAdapter.addData(fuliItems.get(i));
                                mRecyclerAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }, new Action1<Throwable>()
                {

                    @Override
                    public void call(Throwable throwable)
                    {

                        LogUtil.all("数据加载失败" + throwable.getMessage());
                    }
                });
    }
}
