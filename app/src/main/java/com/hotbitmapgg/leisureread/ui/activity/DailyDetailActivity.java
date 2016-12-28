package com.hotbitmapgg.leisureread.ui.activity;

import butterknife.Bind;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.hotbitmapgg.leisureread.app.AppConstant;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyExtraMessageInfo;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyListBean;
import com.hotbitmapgg.leisureread.network.RetrofitHelper;
import com.hotbitmapgg.leisureread.ui.activity.base.BaseSwipeBackActivity;
import com.hotbitmapgg.leisureread.utils.HtmlUtil;
import com.hotbitmapgg.leisureread.widget.CircleProgressView;
import com.hotbitmapgg.rxzhihu.R;
import rx.android.schedulers.AndroidSchedulers;
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
public class DailyDetailActivity extends BaseSwipeBackActivity {

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

  private int comments;

  private int popularity;

  private DailyListBean.StoriesBean mDaily;

  private DailyExtraMessageInfo mDailyExtraMessageInfo;


  @Override
  public int getLayoutId() {

    return R.layout.activity_daily_dateil;
  }


  @Override
  public void initViews(Bundle savedInstanceState) {

    Intent intent = getIntent();
    if (intent != null) {
      mDaily = intent.getParcelableExtra(AppConstant.EXTRA_DETAIL);
      id = intent.getIntExtra(AppConstant.EXTRA_ID, -1);
    }

    //设置侧滑返回触发范围
    mSwipeBackLayout.setEdgeSize(120);
    mCollapsingToolbarLayout.setTitleEnabled(true);
    loadData(mDaily == null ? id : mDaily.getId());
  }


  private void loadData(int id) {

    RetrofitHelper.builder().getNewsDetails(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(this::showProgress)
        .subscribe(dailyDetail -> {

          hideProgress();
          if (dailyDetail != null) {
            //设置图片
            Glide.with(DailyDetailActivity.this)
                .load(dailyDetail.getImage())
                .placeholder(R.drawable.account_avatar)
                .into(mDetailImage);
            //设置标题
            mDetailTitle.setText(dailyDetail.getTitle());
            //设置图片来源
            mDetailSource.setText(dailyDetail.getImage_source());
            //设置web内容加载
            String htmlData = HtmlUtil.createHtmlData(dailyDetail);
            mWebView.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);

            getDailyMessage(dailyDetail.getId());
          }
        }, throwable -> {

          hideProgress();
        });
  }


  private void getDailyMessage(int id) {

    RetrofitHelper.builder().getDailyExtraMessageById(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(dailyExtraMessage -> {

          if (dailyExtraMessage != null) {
            mDailyExtraMessageInfo = dailyExtraMessage;

            comments = dailyExtraMessage.comments;
            popularity = dailyExtraMessage.popularity;

            mBottomCommentTv.setText(comments >= 99 ? "99+" : String.valueOf(comments));
            mBottomLoveTv.setText(popularity >= 99 ? "99+" : String.valueOf(popularity));
            DailyDetailActivity.this.getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
          }
        }, throwable -> {

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


  public static void lanuch(Context context, DailyListBean.StoriesBean storiesBean) {

    Intent mIntent = new Intent(context, DailyDetailActivity.class);
    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    mIntent.putExtra(AppConstant.EXTRA_DETAIL, storiesBean);
    context.startActivity(mIntent);
  }


  public static void lanuch(Context context, int id) {

    Intent mIntent = new Intent(context, DailyDetailActivity.class);
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
        mDaily.getTitle() + "，http://daily.zhihu.com/story/" + mDaily.getId());
    startActivity(Intent.createChooser(intent, mDaily.getTitle()));
  }


  @OnClick(R.id.bottom_love)
  void love() {
  }


  @OnClick(R.id.bottom_comment)
  void comment() {

    DailyCommentActivity.launch(DailyDetailActivity.this, mDaily == null ? id : mDaily.getId(),
        mDailyExtraMessageInfo.comments,
        mDailyExtraMessageInfo.longComments, mDailyExtraMessageInfo.shortComments);
  }
}
