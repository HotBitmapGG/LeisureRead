package com.hotbitmapgg.leisureread.ui.fragment;

import butterknife.Bind;
import com.hotbitmapgg.leisureread.mvp.model.entity.SectionsInfo;
import com.hotbitmapgg.leisureread.mvp.view.SectionsContract;
import com.hotbitmapgg.leisureread.ui.activity.SectionsDetailsActivity;
import com.hotbitmapgg.leisureread.ui.adapter.SectionsAdapter;
import com.hotbitmapgg.leisureread.ui.fragment.base.BaseFragment;
import com.hotbitmapgg.leisureread.widget.CircleProgressView;
import com.hotbitmapgg.rxzhihu.R;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 专栏列表界面
 */
public class SectionsFragment extends BaseFragment implements SectionsContract.View {

  @Bind(R.id.recycler_view)
  RecyclerView mRecyclerView;

  @Bind(R.id.circle_progress)
  CircleProgressView mCircleProgressView;

  private SectionsContract.Presenter mPresenter;

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
  public void showData(List<SectionsInfo.DataBean> sectionsDetailsInfos) {

    hideProgress();
    mAdapter.setDataSources(sectionsDetailsInfos);
    mAdapter.notifyDataSetChanged();
    mAdapter.setOnItemClickListener((position, holder) -> {

      SectionsInfo.DataBean dailySectionsInfo = sectionsDetailsInfos.get(position);
      SectionsDetailsActivity.launch(getActivity(), dailySectionsInfo.getId());
    });
  }


  @Override
  public void setPresenter(SectionsContract.Presenter presenter) {

    mPresenter = presenter;
  }
}
