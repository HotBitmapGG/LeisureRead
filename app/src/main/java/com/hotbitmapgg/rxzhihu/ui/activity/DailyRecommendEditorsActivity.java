package com.hotbitmapgg.rxzhihu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.adapter.AbsRecyclerViewAdapter;
import com.hotbitmapgg.rxzhihu.adapter.RecommendEditorAdapter;
import com.hotbitmapgg.rxzhihu.base.AbsBaseActivity;
import com.hotbitmapgg.rxzhihu.model.DailyRecommend;
import com.hotbitmapgg.rxzhihu.network.RetrofitHelper;
import com.hotbitmapgg.rxzhihu.utils.LogUtil;
import com.hotbitmapgg.rxzhihu.widget.CircleProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/4/24 09:55
 * 100332338@qq.com
 * <p/>
 * 查看日报推荐者界面
 */
public class DailyRecommendEditorsActivity extends AbsBaseActivity
{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.recycle)
    RecyclerView mRecyclerView;

    @Bind(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    @Bind(R.id.empty_tv)
    TextView mTextView;

    private static final String EXTRA_ID = "extra_id";

    private int id;

    private List<DailyRecommend.Editor> editorList = new ArrayList<>();

    @Override
    public int getLayoutId()
    {

        return R.layout.activity_daily_recommend_editors;
    }

    @Override
    public void initViews(Bundle savedInstanceState)
    {

        Intent intent = getIntent();
        if (intent != null)
        {
            id = intent.getIntExtra(EXTRA_ID, -1);
            LogUtil.all(id + "");
        }
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(DailyRecommendEditorsActivity.this));

        startGetEditors();
    }

    private void startGetEditors()
    {

        mCircleProgressView.setVisibility(View.VISIBLE);
        mCircleProgressView.spin();

        getEditors();
    }

    private void getEditors()
    {

        RetrofitHelper.builder().getDailyRecommendEditors(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DailyRecommend>()
                {

                    @Override
                    public void call(DailyRecommend dailyRecommend)
                    {

                        if (dailyRecommend != null)
                        {
                            LogUtil.all(dailyRecommend.toString());
                            List<DailyRecommend.Editor> editors = dailyRecommend.editors;
                            if (editors != null && editors.size() > 0)
                            {
                                editorList.addAll(editors);
                                finishGetEditors();
                            } else
                            {
                                hideProgress();
                            }
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

                    }
                });
    }

    private void finishGetEditors()
    {

        RecommendEditorAdapter mAdapter = new RecommendEditorAdapter(mRecyclerView, editorList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener()
        {

            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder)
            {

                DailyRecommend.Editor editor = editorList.get(position);
                int id = editor.id;
                String name = editor.name;
                EditorInfoActivity.luancher(DailyRecommendEditorsActivity.this, id, name);
            }
        });


        hideProgress();
    }

    public void hideProgress()
    {

        mCircleProgressView.setVisibility(View.GONE);
        mCircleProgressView.stopSpinning();

        mTextView.setVisibility(View.VISIBLE);
    }


    @Override
    public void initToolBar()
    {
        mToolbar.setTitle("日报推荐者");
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

        Intent mIntent = new Intent(activity, DailyRecommendEditorsActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_ID, id);
        activity.startActivity(mIntent);
    }
}
