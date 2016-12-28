package com.hotbitmapgg.leisureread.ui.activity;

import butterknife.Bind;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.hotbitmapgg.leisureread.app.AppConstant;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyDetailsInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyExtraMessageInfo;
import com.hotbitmapgg.leisureread.network.RetrofitHelper;
import com.hotbitmapgg.leisureread.ui.activity.base.BaseSwipeBackActivity;
import com.hotbitmapgg.leisureread.utils.HtmlUtil;
import com.hotbitmapgg.leisureread.widget.CircleProgressView;
import com.hotbitmapgg.rxzhihu.R;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 日报详情界面
 */
public class DailyDetailsActivity extends BaseSwipeBackActivity {

  @Bind(R.id.coll_toolbar_layout)
  CollapsingToolbarLayout mCollapsingToolbarLayout;

  @Bind(R.id.detail_image)
  ImageView mDetailImage;

  @Bind(R.id.detail_title)
  TextView mDetailTitle;

  @Bind(R.id.detail_source)
  TextView mDetailSource;

  @Bind(R.id.detail_web_view)
  WebView mWebView;

  @Bind(R.id.circle_progress)
  CircleProgressView mCircleProgressView;

  @Bind(R.id.bottom_love_tv)
  TextView mBottomLoveTv;

  @Bind(R.id.bottom_comment_tv)
  TextView mBottomCommentTv;

  private int id;

  private DailyDetailsInfo mDailyDetailsInfo;

  private DailyExtraMessageInfo mDailyExtraMessageInfo;


  @Override
  public int getLayoutId() {

    return R.layout.activity_daily_dateil;
  }


  @Override
  public void initViews(Bundle savedInstanceState) {

    Intent intent = getIntent();
    if (intent != null) {
      id = intent.getIntExtra(AppConstant.EXTRA_ID, -1);
    }

    //设置侧滑返回触发范围
    mSwipeBackLayout.setEdgeSize(120);
    mCollapsingToolbarLayout.setTitleEnabled(true);
    loadData();
  }


  private void loadData() {

    RetrofitHelper.builder().getNewsDetails(id)
        .doOnSubscribe(this::showProgress)
        .flatMap(new Func1<DailyDetailsInfo, Observable<DailyExtraMessageInfo>>() {
          @Override
          public Observable<DailyExtraMessageInfo> call(DailyDetailsInfo dailyDetailsInfo) {

            mDailyDetailsInfo = dailyDetailsInfo;
            return RetrofitHelper.builder().getDailyExtraMessageById(id);
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(dailyExtraMessageInfo -> {

          mDailyExtraMessageInfo = dailyExtraMessageInfo;
          finishTask();
        }, throwable -> {
          hideProgress();
        });
  }


  private void finishTask() {

    //设置封面图片
    Glide.with(DailyDetailsActivity.this)
        .load(mDailyDetailsInfo.getImage())
        .placeholder(R.drawable.account_avatar)
        .into(mDetailImage);

    //设置日报标题
    mDetailTitle.setText(mDailyDetailsInfo.getTitle());
    //设置图片来源
    mDetailSource.setText(mDailyDetailsInfo.getImage_source());
    //设置web内容加载
    String htmlData = HtmlUtil.createHtmlData(mDailyDetailsInfo);
    mWebView.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    //设置日报评论和点赞信息
    mBottomCommentTv.setText(mDailyExtraMessageInfo.comments >= 99
                             ? "99+"
                             : String.valueOf(mDailyExtraMessageInfo.comments));
    mBottomLoveTv.setText(mDailyExtraMessageInfo.popularity >= 99
                          ? "99+"
                          : String.valueOf(mDailyExtraMessageInfo.popularity));

    DailyDetailsActivity.this.getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
    hideProgress();
  }


  public void showProgress() {

    mCircleProgressView.setVisibility(View.VISIBLE);
    mCircleProgressView.spin();
  }


  public void hideProgress() {

    mCircleProgressView.setVisibility(View.GONE);
    mCircleProgressView.stopSpinning();
  }


  public static void lanuch(Context context, int id) {

    Intent mIntent = new Intent(context, DailyDetailsActivity.class);
    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    mIntent.putExtra(AppConstant.EXTRA_ID, id);
    context.startActivity(mIntent);
  }


  @OnClick(R.id.bottom_back)
  void back() {

    onBackPressed();
  }


  @OnClick(R.id.bottom_next)
  void next() {

  }


  @OnClick(R.id.bottom_share)
  void share() {

    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share));
    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_from) +
        mDailyDetailsInfo.getTitle() + "，http://daily.zhihu.com/story/" +
        mDailyDetailsInfo.getId());
    startActivity(Intent.createChooser(intent, mDailyDetailsInfo.getTitle()));
  }


  @OnClick(R.id.bottom_love)
  void love() {
  }


  @OnClick(R.id.bottom_comment)
  void comment() {

    DailyCommentActivity.launch(DailyDetailsActivity.this, id,
        mDailyExtraMessageInfo.comments,
        mDailyExtraMessageInfo.longComments,
        mDailyExtraMessageInfo.shortComments);
  }
}
