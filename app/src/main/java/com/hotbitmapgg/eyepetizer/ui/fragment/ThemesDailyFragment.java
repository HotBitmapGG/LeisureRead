package com.hotbitmapgg.eyepetizer.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hotbitmapgg.eyepetizer.adapter.DailyTypeRecycleAdapter;
import com.hotbitmapgg.eyepetizer.base.LazyFragment;
import com.hotbitmapgg.eyepetizer.model.DailyTypeBean;
import com.hotbitmapgg.eyepetizer.network.RetrofitHelper;
import com.hotbitmapgg.eyepetizer.ui.activity.ThemesDailyDetailsActivity;
import com.hotbitmapgg.eyepetizer.widget.CircleProgressView;
import com.hotbitmapgg.rxzhihu.R;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
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

        return new ThemesDailyFragment();
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
                .subscribe(dailyTypeBean -> {

                    if (dailyTypeBean != null)
                    {
                        List<DailyTypeBean.SubjectDaily> others = dailyTypeBean.getOthers();
                        finishGetDailyType(others);
                    }
                }, throwable -> {

                });
    }

    private void finishGetDailyType(final List<DailyTypeBean.SubjectDaily> others)
    {

        hideProgress();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DailyTypeRecycleAdapter mAdapter = new DailyTypeRecycleAdapter(mRecyclerView, others);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((position, holder) -> {

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

        });
    }
}
