package com.hotbitmapgg.eyepetizer.view.fragments;

import butterknife.Bind;
import com.hotbitmapgg.eyepetizer.base.BaseFragment;
import com.hotbitmapgg.eyepetizer.model.entity.DailySections;
import com.hotbitmapgg.eyepetizer.presenter.contracts.SectionsContract;
import com.hotbitmapgg.eyepetizer.view.activitys.SectionsDetailsActivity;
import com.hotbitmapgg.eyepetizer.view.adapters.SectionsAdapter;
import com.hotbitmapgg.eyepetizer.widget.CircleProgressView;
import com.hotbitmapgg.rxzhihu.R;
import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hcc on 16/4/23 14:09
 * 100332338@qq.com
 * <p/>
 * 知乎专栏列表
 */
public class SectionsFragment extends BaseFragment implements SectionsContract.View {

  @Bind(R.id.recycle)
  RecyclerView mRecyclerView;

  @Bind(R.id.circle_progress)
  CircleProgressView mCircleProgressView;

  private SectionsContract.Presenter mPresenter;

  private List<DailySections.DailySectionsInfo> sectionsInfos = new ArrayList<>();
  private SectionsAdapter mAdapter;


  public static SectionsFragment newInstance() {

    return new SectionsFragment();
  }


  @Override
  public int getLayoutId() {

    return R.layout.fragment_sections;
  }


  @Override
  public void initViews() {

    showProgress();

    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    mAdapter = new SectionsAdapter(mRecyclerView);
    mRecyclerView.setAdapter(mAdapter);
    mAdapter.setOnItemClickListener((position, holder) -> {

      DailySections.DailySectionsInfo dailySectionsInfo = sectionsInfos.get(position);
      SectionsDetailsActivity.luancher(getActivity(), dailySectionsInfo.id);
    });
  }


  @Override
  public void initData() {

    mPresenter.start();
  }


  private void showProgress() {

    mCircleProgressView.setVisibility(View.VISIBLE);
    mCircleProgressView.spin();
  }


  public void hideProgress() {

    mCircleProgressView.setVisibility(View.GONE);
    mCircleProgressView.stopSpinning();
  }


  @Override
  public void showError() {

    hideProgress();
  }


  @Override
  public void showData(List<DailySections.DailySectionsInfo> sectionsDetailsInfos) {

    hideProgress();
    mAdapter.setDataSources(sectionsDetailsInfos);
    mAdapter.notifyDataSetChanged();
  }


  @Override
  public void setPresenter(SectionsContract.Presenter presenter) {

    mPresenter = presenter;
  }
}
