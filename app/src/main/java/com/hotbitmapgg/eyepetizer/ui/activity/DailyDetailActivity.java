package com.hotbitmapgg.eyepetizer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.eyepetizer.base.BaseSwipeBackActivity;
import com.hotbitmapgg.eyepetizer.model.DailyBean;
import com.hotbitmapgg.eyepetizer.model.DailyExtraMessage;
import com.hotbitmapgg.eyepetizer.network.RetrofitHelper;
import com.hotbitmapgg.eyepetizer.utils.HtmlUtil;
import com.hotbitmapgg.eyepetizer.utils.LogUtil;
import com.hotbitmapgg.eyepetizer.widget.CircleProgressView;
import com.hotbitmapgg.rxzhihu.R;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/4/4 12:03
 * 100332338@qq.com
 * EyepetizerDaily
 *
 * @HotBitmapGG 日报详情界面
 */
public class DailyDetailActivity extends BaseSwipeBackActivity
{

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

    private DailyBean mDaily;

    private static final String EXTRA_DETAIL = "extra_detail";

    private static final String EXTRA_ID = "extra_id";

    private int id;

    private DailyExtraMessage mDailyExtraMessage;

    private int comments;

    private int popularity;


    @Override
    public int getLayoutId()
    {

        return R.layout.activity_daily_dateil;
    }

    @Override
    public void initViews(Bundle savedInstanceState)
    {

        Intent intent = getIntent();
        if (intent != null)
        {
            mDaily = intent.getParcelableExtra(EXTRA_DETAIL);
            id = intent.getIntExtra(EXTRA_ID, -1);
        }

        //设置侧滑返回触发范围
        mSwipeBackLayout.setEdgeDp(120);
        mCollapsingToolbarLayout.setTitleEnabled(true);
        startGetDailyDetail(mDaily == null ? id : mDaily.getId());
    }

    private void startGetDailyDetail(int id)
    {

        RetrofitHelper.builder().getNewsDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgress)
                .subscribe(dailyDetail -> {

                    hideProgress();
                    if (dailyDetail != null)
                    {
                        //设置图片
                        Glide.with(DailyDetailActivity.this).load(dailyDetail.getImage()).placeholder(R.drawable.account_avatar).into(mDetailImage);
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
                    LogUtil.all("数据加载失败");
                });
    }

    /**
     * 设置日报的评论数跟点赞数
     *
     * @param id
     */
    private void getDailyMessage(int id)
    {

        RetrofitHelper.builder().getDailyExtraMessageById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dailyExtraMessage -> {

                    if (dailyExtraMessage != null)
                    {
                        mDailyExtraMessage = dailyExtraMessage;

                        comments = dailyExtraMessage.comments;
                        popularity = dailyExtraMessage.popularity;

                        mBottomCommentTv.setText(String.valueOf(comments));
                        mBottomLoveTv.setText(String.valueOf(popularity));
                        DailyDetailActivity.this.getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                    }
                }, throwable -> {

                });
    }


    public void showProgress()
    {

        mCircleProgressView.setVisibility(View.VISIBLE);
        mCircleProgressView.spin();
    }

    public void hideProgress()
    {

        mCircleProgressView.setVisibility(View.GONE);
        mCircleProgressView.stopSpinning();
    }

    public static void lanuch(Context context, DailyBean dailyBean)
    {

        Intent mIntent = new Intent(context, DailyDetailActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_DETAIL, dailyBean);
        context.startActivity(mIntent);
    }

    public static void lanuch(Context context, int id)
    {

        Intent mIntent = new Intent(context, DailyDetailActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_ID, id);
        context.startActivity(mIntent);
    }


    @OnClick(R.id.bottom_back)
    void back()
    {

        onBackPressed();
    }

    @OnClick(R.id.bottom_next)
    void next()
    {

    }

    @OnClick(R.id.bottom_share)
    void share()
    {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_from) +
                mDaily.getTitle() + "，http://daily.zhihu.com/story/" + mDaily.getId());
        startActivity(Intent.createChooser(intent, mDaily.getTitle()));
    }

    @OnClick(R.id.bottom_love)
    void love()
    {

        DailyRecommendEditorsActivity.luancher(DailyDetailActivity.this, mDaily == null ? id : mDaily.getId());
    }

    @OnClick(R.id.bottom_comment)
    void comment()
    {

        DailyCommentActivity.luancher(DailyDetailActivity.this, mDaily == null ? id : mDaily.getId(), mDailyExtraMessage.comments,
                mDailyExtraMessage.longComments, mDailyExtraMessage.shortComments);
    }
}
