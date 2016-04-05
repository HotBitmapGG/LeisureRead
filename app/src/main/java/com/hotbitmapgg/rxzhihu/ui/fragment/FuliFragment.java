package com.hotbitmapgg.rxzhihu.ui.fragment;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import cn.easydone.swiperefreshendless.EndlessRecyclerOnScrollListener;
import cn.easydone.swiperefreshendless.HeaderViewRecyclerAdapter;
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

                FuliFullPicActivity.LuanchActivity(getActivity(), datas.get(position).imageUrl);
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
