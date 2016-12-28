package com.hotbitmapgg.leisureread.widget.banner;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hotbitmapgg.leisureread.ui.activity.DailyDetailsActivity;
import com.hotbitmapgg.leisureread.utils.DisplayUtil;
import com.hotbitmapgg.leisureread.utils.LogUtil;
import com.hotbitmapgg.rxzhihu.R;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 自定义Banner无限轮播控件
 */
public class BannerView extends RelativeLayout
    implements BannerAdapter.ViewPagerOnItemClickListener {

  @Bind(R.id.layout_banner_viewpager)
  ViewPager viewPager;

  @Bind(R.id.layout_banner_points_group)
  LinearLayout points;

  @Bind(R.id.layout_banner_title)
  TextView mTitle;

  private CompositeSubscription compositeSubscription;

  //默认轮播时间，10s
  private int delayTime = 10;

  private List<ImageView> imageViewList;

  private List<BannerEntity> bannerList;

  //选中显示Indicator
  private int selectRes = R.drawable.shape_dots_select;

  //非选中显示Indicator
  private int unSelcetRes = R.drawable.shape_dots_default;

  //当前页的下标
  private int currrentPos;


  public BannerView(Context context) {

    this(context, null);
  }


  public BannerView(Context context, AttributeSet attrs) {

    this(context, attrs, 0);
  }


  public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {

    super(context, attrs, defStyleAttr);
    LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_banner, this, true);
    ButterKnife.bind(this);

    imageViewList = new ArrayList<>();
  }


  /**
   * 设置轮播间隔时间
   *
   * @param time 轮播间隔时间，单位秒
   */
  public BannerView delayTime(int time) {

    this.delayTime = time;
    return this;
  }


  /**
   * 设置Points资源 Res
   *
   * @param selectRes 选中状态
   * @param unselcetRes 非选中状态
   */
  public void setPointsRes(int selectRes, int unselcetRes) {

    this.selectRes = selectRes;
    this.unSelcetRes = unselcetRes;
  }


  /**
   * 图片轮播需要传入参数
   */
  public void build(List<BannerEntity> list) {

    destory();

    if (list.size() == 0) {
      this.setVisibility(GONE);
      return;
    }

    bannerList = new ArrayList<>();
    bannerList.addAll(list);
    final int pointSize;
    pointSize = bannerList.size();

    if (pointSize == 2) {
      bannerList.addAll(list);
    }
    //判断是否清空 指示器点
    if (points.getChildCount() != 0) {
      points.removeAllViewsInLayout();
    }

    //初始化与个数相同的指示器点
    for (int i = 0; i < pointSize; i++) {
      View dot = new View(getContext());
      dot.setBackgroundResource(unSelcetRes);
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
          DisplayUtil.dp2px(getContext(), 5),
          DisplayUtil.dp2px(getContext(), 5));
      params.leftMargin = 10;
      dot.setLayoutParams(params);
      dot.setEnabled(false);
      points.addView(dot);
    }

    points.getChildAt(0).setBackgroundResource(selectRes);
    mTitle.setText(bannerList.get(0).title);

    for (int i = 0; i < bannerList.size(); i++) {
      ImageView mImageView = new ImageView(getContext());

      Glide.with(getContext())
          .load(bannerList.get(i).img)
          .centerCrop()
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .dontAnimate()
          .into(mImageView);
      imageViewList.add(mImageView);
    }

    //监听图片轮播，改变指示器状态
    viewPager.clearOnPageChangeListeners();
    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }


      @Override
      public void onPageSelected(int pos) {

        pos = pos % pointSize;
        currrentPos = pos;
        for (int i = 0; i < points.getChildCount(); i++) {
          points.getChildAt(i).setBackgroundResource(unSelcetRes);
        }
        points.getChildAt(pos).setBackgroundResource(selectRes);
        mTitle.setText(bannerList.get(pos).title);
      }


      @Override
      public void onPageScrollStateChanged(int state) {

        switch (state) {
          case ViewPager.SCROLL_STATE_IDLE:
            if (isStopScroll) {
              startScroll();
            }
            break;
          case ViewPager.SCROLL_STATE_DRAGGING:
            stopScroll();
            compositeSubscription.unsubscribe();
            break;
        }
      }
    });

    BannerAdapter bannerAdapter = new BannerAdapter(imageViewList);
    viewPager.setAdapter(bannerAdapter);
    bannerAdapter.notifyDataSetChanged();
    bannerAdapter.setmViewPagerOnItemClickListener(this);

    //图片开始轮播
    startScroll();
  }


  private boolean isStopScroll = false;


  /**
   * 图片开始轮播
   */
  private void startScroll() {

    compositeSubscription = new CompositeSubscription();
    isStopScroll = false;
    Subscription subscription = Observable.timer(delayTime, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(aLong -> {

          if (isStopScroll) {
            return;
          }

          isStopScroll = true;
          viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        });
    compositeSubscription.add(subscription);
  }


  /**
   * 图片停止轮播
   */
  private void stopScroll() {

    isStopScroll = true;
  }


  public void destory() {

    if (compositeSubscription != null) {
      compositeSubscription.unsubscribe();
    }
  }


  /**
   * 设置ViewPager的Item点击回调事件
   */
  @Override
  public void onItemClick() {
    LogUtil.all("点击事件响应");
    DailyDetailsActivity.lanuch(getContext(), bannerList.get(currrentPos).id);
  }
}
