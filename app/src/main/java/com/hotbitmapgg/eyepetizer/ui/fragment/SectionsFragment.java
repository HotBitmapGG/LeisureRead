package com.hotbitmapgg.eyepetizer.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hotbitmapgg.eyepetizer.adapter.SectionsAdapter;
import com.hotbitmapgg.eyepetizer.base.LazyFragment;
import com.hotbitmapgg.eyepetizer.model.DailySections;
import com.hotbitmapgg.eyepetizer.network.RetrofitHelper;
import com.hotbitmapgg.eyepetizer.ui.activity.SectionsDetailsActivity;
import com.hotbitmapgg.eyepetizer.widget.CircleProgressView;
import com.hotbitmapgg.rxzhihu.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/4/23 14:09
 * 100332338@qq.com
 * <p/>
 * 知乎专栏列表
 */
public class SectionsFragment extends LazyFragment
{

    @Bind(R.id.recycle)
    RecyclerView mRecyclerView;

    @Bind(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    private List<DailySections.DailySectionsInfo> sectionsInfos = new ArrayList<>();

    public static SectionsFragment newInstance()
    {

        return new SectionsFragment();
    }


    @Override
    public int getLayoutId()
    {

        return R.layout.fragment_sections;
    }

    @Override
    public void initViews()
    {

        getSections();
    }

    private void getSections()
    {

        RetrofitHelper.getLastZhiHuApi().getZhiHuSections()
                .compose(bindToLifecycle())
                .doOnSubscribe(this::showProgress)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dailySections -> {

                    if (dailySections != null)
                    {
                        List<DailySections.DailySectionsInfo> data = dailySections.data;
                        if (data != null && data.size() > 0)
                        {
                            sectionsInfos.clear();
                            sectionsInfos.addAll(data);
                            finishGetSections();
                        }
                    }
                }, throwable -> {

                    hideProgress();
                });
    }

    private void finishGetSections()
    {

        hideProgress();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SectionsAdapter mAdapter = new SectionsAdapter(mRecyclerView, sectionsInfos);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((position, holder) -> {

            DailySections.DailySectionsInfo dailySectionsInfo = sectionsInfos.get(position);
            SectionsDetailsActivity.luancher(getActivity(), dailySectionsInfo.id);
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
}
