package com.hotbitmapgg.rxzhihu.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.adapter.AbsRecyclerViewAdapter;
import com.hotbitmapgg.rxzhihu.adapter.DailyTypeRecycleAdapter;
import com.hotbitmapgg.rxzhihu.base.LazyFragment;
import com.hotbitmapgg.rxzhihu.model.DailyTypeBean;
import com.hotbitmapgg.rxzhihu.network.RetrofitHelper;
import com.hotbitmapgg.rxzhihu.ui.activity.ThemesDailyDetailsActivity;
import com.hotbitmapgg.rxzhihu.widget.CircleProgressView;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/4/17 19:03
 * 100332338@qq.com
 * <p/>
 * 主题日报列表
 */
public class ThemesDailyFragment extends LazyFragment
{

    @Bind(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    @Bind(R.id.themes_recycle)
    RecyclerView mRecyclerView;

    public static ThemesDailyFragment newInstance()
    {

        ThemesDailyFragment mThemesDailyFragment = new ThemesDailyFragment();
        return mThemesDailyFragment;
    }

    @Override
    public int getLayoutId()
    {

        return R.layout.fragment_themes_daily;
    }

    @Override
    public void initViews()
    {

        showProgress();
        getDailyTypeData();
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
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void getDailyTypeData()
    {

        RetrofitHelper.builder().getDailyType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DailyTypeBean>()
                {

                    @Override
                    public void call(DailyTypeBean dailyTypeBean)
                    {

                        if (dailyTypeBean != null)
                        {
                            List<DailyTypeBean.SubjectDaily> others = dailyTypeBean.getOthers();
                            finishGetDailyType(others);
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

    private void finishGetDailyType(final List<DailyTypeBean.SubjectDaily> others)
    {

        hideProgress();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DailyTypeRecycleAdapter mAdapter = new DailyTypeRecycleAdapter(mRecyclerView, others);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener()
        {

            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder)
            {

                DailyTypeBean.SubjectDaily subjectDaily = others.get(position);
                ThemesDailyDetailsActivity.Luanch(getActivity(), subjectDaily.getId());

//                DailyTypeBean.SubjectDaily subjectDaily = others.get(position);
//                Intent mIntent = new Intent(getActivity() , ThemesDailyDetailsActivity.class);
//                mIntent.putExtra("extra_type" , subjectDaily);
//                ActivityOptionsCompat mActivityOptionsCompat;
//                if (Build.VERSION.SDK_INT >= 21)
//                {
//                    mActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                            getActivity(), holder.itemView, ThemesDailyDetailsActivity.PIC);
//                } else
//                {
//                    mActivityOptionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(
//                            holder.itemView, 0, 0, holder.itemView.getWidth(), holder.itemView.getHeight());
//                }
//
//                startActivity(mIntent, mActivityOptionsCompat.toBundle());

            }
        });
    }
}
