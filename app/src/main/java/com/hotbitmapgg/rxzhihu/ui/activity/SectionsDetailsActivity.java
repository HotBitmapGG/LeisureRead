package com.hotbitmapgg.rxzhihu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.adapter.AbsRecyclerViewAdapter;
import com.hotbitmapgg.rxzhihu.adapter.AutoLoadOnScrollListener;
import com.hotbitmapgg.rxzhihu.adapter.SectionsDetailsAdapter;
import com.hotbitmapgg.rxzhihu.base.AbsBaseActivity;
import com.hotbitmapgg.rxzhihu.model.SectionsDetails;
import com.hotbitmapgg.rxzhihu.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/4/23 15:23
 * 100332338@qq.com
 * <p/>
 * 专栏详情界面
 */
public class SectionsDetailsActivity extends AbsBaseActivity
{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.recycle)
    RecyclerView mRecyclerView;

    private static final String EXTRA_ID = "extra_id";

    private int id;

    private List<SectionsDetails.SectionsDetailsInfo> sectionsDetailsInfos = new ArrayList<>();

    private LinearLayoutManager mLinearLayoutManager;

    private SectionsDetailsAdapter mAdapter;

    private long timetemp;

    @Override
    public int getLayoutId()
    {

        return R.layout.activity_sections_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState)
    {

        Intent intent = getIntent();
        if (intent != null)
        {
            id = intent.getIntExtra(EXTRA_ID, -1);
        }

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(SectionsDetailsActivity.this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {

            @Override
            public void onRefresh()
            {

                getSectionsDetails();
            }
        });

        getSectionsDetails();
    }

    private void getSectionsDetails()
    {

        RetrofitHelper.getLastZhiHuApi().getSectionsDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SectionsDetails>()
                {

                    @Override
                    public void call(SectionsDetails sectionsDetails)
                    {

                        if (sectionsDetails != null)
                        {
                            mToolbar.setTitle(sectionsDetails.name);
                            timetemp = sectionsDetails.timestamp;
                            List<SectionsDetails.SectionsDetailsInfo> stories = sectionsDetails.stories;
                            if (stories != null && stories.size() > 0)
                            {
                                sectionsDetailsInfos.clear();
                                sectionsDetailsInfos.addAll(stories);
                                finishGetSectionsDetails();
                                mSwipeRefreshLayout.setRefreshing(false);
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

    private void finishGetSectionsDetails()
    {

        mAdapter = new SectionsDetailsAdapter(mRecyclerView, sectionsDetailsInfos);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new AutoLoadOnScrollListener(mLinearLayoutManager)
        {

            @Override
            public void onLoadMore(int currentPage)
            {

                loadMore(timetemp);
            }
        });

        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener()
        {

            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder)
            {

                SectionsDetails.SectionsDetailsInfo sectionsDetailsInfo = sectionsDetailsInfos.get(position);
                int id = sectionsDetailsInfo.id;
                DailyDetailActivity.lanuch(SectionsDetailsActivity.this , id);
            }
        });
    }

    public void loadMore(long timestamp)
    {

        RetrofitHelper.getLastZhiHuApi().getBeforeSectionsDetails(id, timestamp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SectionsDetails>()
                {

                    @Override
                    public void call(SectionsDetails sectionsDetails)
                    {

                        if (sectionsDetails != null)
                        {
                            List<SectionsDetails.SectionsDetailsInfo> stories = sectionsDetails.stories;
                            if (stories != null && stories.size() > 0)
                            {
                                int size = stories.size();
                                for (int i = 0; i < size; i++)
                                {
                                    SectionsDetails.SectionsDetailsInfo sectionsDetailsInfo = stories.get(i);
                                    mAdapter.addData(sectionsDetailsInfo);
                                    mAdapter.notifyDataSetChanged();
                                }
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

    @Override
    public void initToolBar()
    {
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void luancher(Activity activity, int id)
    {

        Intent mIntent = new Intent(activity, SectionsDetailsActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_ID, id);
        activity.startActivity(mIntent);
    }
}
