package com.hotbitmapgg.leisureread.ui.fragment;

import butterknife.Bind;
import com.hotbitmapgg.leisureread.mvp.model.entity.ThemeDailyInfo;
import com.hotbitmapgg.leisureread.network.RetrofitHelper;
import com.hotbitmapgg.leisureread.ui.activity.ThemeDailyDetailsActivity;
import com.hotbitmapgg.leisureread.ui.adapter.DailyTypeRecycleAdapter;
import com.hotbitmapgg.leisureread.ui.fragment.base.BaseFragment;
import com.hotbitmapgg.leisureread.widget.CircleProgressView;
import com.hotbitmapgg.rxzhihu.R;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 主题日报界面
 */
public class ThemesDailyFragment extends BaseFragment {

  @Bind(R.id.circle_progress)
  CircleProgressView mCircleProgressView;

  @Bind(R.id.themes_recycle)
  RecyclerView mRecyclerView;

  private List<ThemeDailyInfo.OthersBean> others = new ArrayList<>();
  private DailyTypeRecycleAdapter mAdapter;


  public static ThemesDailyFragment newInstance() {

    return new ThemesDailyFragment();
  }


  @Override
  public int getLayoutId() {

    return R.layout.fragment_themes_daily;
  }


  @Override
  public void initViews() {

    initRecyclerView();
    initData();
  }


  private void initRecyclerView() {
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    mAdapter = new DailyTypeRecycleAdapter(mRecyclerView, others);
    mRecyclerView.setAdapter(mAdapter);
    mAdapter.setOnItemClickListener((position, holder) -> {

      ThemeDailyInfo.OthersBean subjectDaily = others.get(position);
      ThemeDailyDetailsActivity.launch(getActivity(), subjectDaily.getId());
    });
  }


  @Override
  public void initData() {
    RetrofitHelper.builder().getDailyType()
        .compose(bindToLifecycle())
        .doOnSubscribe(this::showProgress)
        .delay(1000, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(dailyTypeBean -> {

          others.addAll(dailyTypeBean.getOthers());
          finishTask();
        }, throwable -> {
          hideProgress();
        });
  }


  public void showProgress() {

    mCircleProgressView.setVisibility(View.VISIBLE);
    mCircleProgressView.spin();
  }


  public void hideProgress() {

    mCircleProgressView.setVisibility(View.GONE);
    mCircleProgressView.stopSpinning();
  }


  private void finishTask() {
    hideProgress();
    mAdapter.notifyDataSetChanged();
  }
}
