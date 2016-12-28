package com.hotbitmapgg.leisureread.ui.fragment;

import butterknife.Bind;
import com.hotbitmapgg.leisureread.ui.fragment.base.BaseFragment;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyTypeInfo;
import com.hotbitmapgg.leisureread.network.RetrofitHelper;
import com.hotbitmapgg.leisureread.ui.activity.ThemesDailyDetailsActivity;
import com.hotbitmapgg.leisureread.ui.adapter.DailyTypeRecycleAdapter;
import com.hotbitmapgg.leisureread.widget.CircleProgressView;
import com.hotbitmapgg.rxzhihu.R;
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


  public static ThemesDailyFragment newInstance() {

    return new ThemesDailyFragment();
  }


  @Override
  public int getLayoutId() {

    return R.layout.fragment_themes_daily;
  }


  @Override
  public void initViews() {

    showProgress();
    getDailyTypeData();
  }


  @Override
  public void initData() {

  }


  public void showProgress() {

    mCircleProgressView.setVisibility(View.VISIBLE);
    mCircleProgressView.spin();
  }


  public void hideProgress() {

    mCircleProgressView.setVisibility(View.GONE);
    mCircleProgressView.stopSpinning();
  }


  public void getDailyTypeData() {

    RetrofitHelper.builder().getDailyType()
        .compose(bindToLifecycle())
        .delay(1000, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(dailyTypeBean -> {

          if (dailyTypeBean != null) {
            List<DailyTypeInfo.SubjectDaily> others = dailyTypeBean.getOthers();
            finishGetDailyType(others);
          }
        }, throwable -> {

        });
  }


  private void finishGetDailyType(final List<DailyTypeInfo.SubjectDaily> others) {
    hideProgress();
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    DailyTypeRecycleAdapter mAdapter = new DailyTypeRecycleAdapter(mRecyclerView, others);
    mRecyclerView.setAdapter(mAdapter);
    mAdapter.setOnItemClickListener((position, holder) -> {

      DailyTypeInfo.SubjectDaily subjectDaily = others.get(position);
      ThemesDailyDetailsActivity.launch(getActivity(), subjectDaily.getId());

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
