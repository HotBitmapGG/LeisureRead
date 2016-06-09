package com.hotbitmapgg.rxzhihu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.adapter.AbsRecyclerViewAdapter;
import com.hotbitmapgg.rxzhihu.adapter.ThemesDetailsHeadAdapter;
import com.hotbitmapgg.rxzhihu.adapter.ThemesDetailsStoriesAdapter;
import com.hotbitmapgg.rxzhihu.base.AbsBaseActivity;
import com.hotbitmapgg.rxzhihu.model.Editors;
import com.hotbitmapgg.rxzhihu.model.Stories;
import com.hotbitmapgg.rxzhihu.model.ThemesDetails;
import com.hotbitmapgg.rxzhihu.network.RetrofitHelper;
import com.hotbitmapgg.rxzhihu.utils.LogUtil;
import com.hotbitmapgg.rxzhihu.widget.CircleProgressView;
import com.hotbitmapgg.rxzhihu.widget.refresh.HeaderViewRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/4/4.
 */
public class ThemesDailyDetailsActivity extends AbsBaseActivity
{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    @Bind(R.id.recycle)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    //主题日报故事列表
    private List<Stories> stories = new ArrayList<>();

    //主题日报主编列表
    private List<Editors> editors = new ArrayList<>();

    private static final String EXTRA_TYPE = "extra_type";

    private int id;

    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;


    @Override
    public int getLayoutId()
    {

        return R.layout.activity_type_daily;
    }

    @Override
    public void initViews(Bundle savedInstanceState)
    {
        Intent intent = getIntent();
        if (intent != null)
        {
            id = intent.getIntExtra(EXTRA_TYPE, -1);
        }


        startGetThemesDetails();
    }

    private void startGetThemesDetails()
    {

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {

            @Override
            public void onRefresh()
            {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mCircleProgressView.setVisibility(View.VISIBLE);
        mCircleProgressView.spin();
        mRecyclerView.setVisibility(View.GONE);
        getThemesDetails();
    }

    private void getThemesDetails()
    {

        RetrofitHelper.builder().getThemesDetailsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ThemesDetails>()
                {

                    @Override
                    public void call(ThemesDetails themesDetails)
                    {

                        if (themesDetails != null)
                        {
                            finishGetThemesDetails(themesDetails);
                        }
                    }
                }, new Action1<Throwable>()
                {

                    @Override
                    public void call(Throwable throwable)
                    {

                        LogUtil.all("加载数据失败");
                    }
                });
    }

    private void finishGetThemesDetails(ThemesDetails themesDetails)
    {

        stories.addAll(themesDetails.getStories());
        editors.addAll(themesDetails.getEditors());

        mToolbar.setTitle(themesDetails.getName());

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(ThemesDailyDetailsActivity.this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        ThemesDetailsStoriesAdapter mAdapter = new ThemesDetailsStoriesAdapter(mRecyclerView, stories);
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        addHeadView(themesDetails);
        mRecyclerView.setAdapter(mHeaderViewRecyclerAdapter);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener()
        {

            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder)
            {

                Stories stories = ThemesDailyDetailsActivity.this.stories.get(position);
                DailyDetailActivity.lanuch(ThemesDailyDetailsActivity.this, stories.getId());
            }
        });

        new Handler().postDelayed(new Runnable()
        {

            @Override
            public void run()
            {

                mCircleProgressView.setVisibility(View.GONE);
                mCircleProgressView.stopSpinning();
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }, 3000);
    }

    private void addHeadView(ThemesDetails themesDetails)
    {

        View headView = LayoutInflater.from(ThemesDailyDetailsActivity.this).inflate(R.layout.layout_themes_details_head, mRecyclerView, false);
        ImageView mThemesBg = (ImageView) headView.findViewById(R.id.type_image);
        TextView mThemesTitle = (TextView) headView.findViewById(R.id.type_title);
        Glide.with(ThemesDailyDetailsActivity.this).load(themesDetails.getBackground()).placeholder(R.drawable.account_avatar).into(mThemesBg);
        mThemesTitle.setText(themesDetails.getDescription());
        View editorsHeadView = LayoutInflater.from(ThemesDailyDetailsActivity.this).inflate(R.layout.layout_themes_details_head_2, mRecyclerView, false);
        RecyclerView mHeadRecycle = (RecyclerView) editorsHeadView.findViewById(R.id.head_recycle);
        mHeadRecycle.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(ThemesDailyDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mHeadRecycle.setLayoutManager(mLinearLayoutManager);
        ThemesDetailsHeadAdapter mHeadAdapter = new ThemesDetailsHeadAdapter(mHeadRecycle, editors);
        mHeadRecycle.setAdapter(mHeadAdapter);
        mHeadAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener()
        {

            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder)
            {

                Editors editor = ThemesDailyDetailsActivity.this.editors.get(position);
                int id = editor.getId();
                String name = editor.getName();
                EditorInfoActivity.luancher(ThemesDailyDetailsActivity.this, id, name);
            }
        });
        mHeaderViewRecyclerAdapter.addHeaderView(headView);
        mHeaderViewRecyclerAdapter.addHeaderView(editorsHeadView);
        mHeaderViewRecyclerAdapter.notifyDataSetChanged();
        mHeadAdapter.notifyDataSetChanged();
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


    public static void Luanch(Activity activity, int id)
    {

        Intent mIntent = new Intent(activity, ThemesDailyDetailsActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_TYPE, id);
        activity.startActivity(mIntent);
    }
}
