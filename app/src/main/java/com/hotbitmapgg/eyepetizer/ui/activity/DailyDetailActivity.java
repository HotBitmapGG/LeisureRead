package com.hotbitmapgg.eyepetizer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.eyepetizer.base.BaseSwipeBackActivity;
import com.hotbitmapgg.eyepetizer.model.DailyBean;
import com.hotbitmapgg.eyepetizer.model.DailyExtraMessage;
import com.hotbitmapgg.eyepetizer.network.RetrofitHelper;
import com.hotbitmapgg.eyepetizer.utils.HtmlUtil;
import com.hotbitmapgg.eyepetizer.utils.LogUtil;
import com.hotbitmapgg.eyepetizer.utils.PreferenceUtils;
import com.hotbitmapgg.eyepetizer.widget.CircleProgressView;
import com.hotbitmapgg.rxzhihu.R;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/4/4.
 */
public class DailyDetailActivity extends BaseSwipeBackActivity
{

    @Bind(R.id.coll_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

//    @Bind(R.id.toolbar)
//    Toolbar mToolbar;

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

    @Bind(R.id.bottom_back)
    ImageView mBottomBack;

    @Bind(R.id.bottom_next)
    ImageView mBottomNext;

    @Bind(R.id.bottom_love_tv)
    TextView mBottomLoveTv;

    @Bind(R.id.bottom_love)
    FrameLayout mBottomLove;

    @Bind(R.id.bottom_share)
    ImageView mBottomShare;

    @Bind(R.id.bottom_comment_tv)
    TextView mBottomCommentTv;

    @Bind(R.id.bottom_comment)
    FrameLayout mBottomComment;

    private DailyBean mDaily;

    //private ActionBar mActionBar;

    private static final String EXTRA_DETAIL = "extra_detail";

    private static final String EXTRA_ID = "extra_id";

    private int id;

    private DailyExtraMessage mDailyExtraMessage;

    private boolean isShowSnack = false;

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

        //第一次进入提示用户可以左滑返回
        showSnackBarHint();
    }

    private void showSnackBarHint()
    {

        boolean isShow = PreferenceUtils.getBoolean("isShowSnack", false);
        if (!isShow)
        {
            Snackbar.make(mCollapsingToolbarLayout, "左滑可以返回主页哦~", Snackbar.LENGTH_LONG).show();
            this.isShowSnack = true;
            PreferenceUtils.putBoolean("isShowSnack", true);
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//
//        switch (item.getItemId())
//        {
//            case R.id.menu_action_share:
//                //分享新闻
//                share();
//                return true;
//
//            case R.id.menu_action_fav:
//                //查看新闻推荐者
//                DailyRecommendEditorsActivity.luancher(DailyDetailActivity.this, mDaily == null ? id : mDaily.getId());
//                return true;
//
//            case R.id.menu_action_comment:
//                // 查看新闻评论
//                DailyCommentActivity.luancher(DailyDetailActivity.this, mDaily == null ? id : mDaily.getId(), mDailyExtraMessage.comments, mDailyExtraMessage.longComments, mDailyExtraMessage.shortComments);
//                return true;
//
//            case R.id.menu_action_parise:
//                //执行点赞动画
//                AnimationUtils.loadAnimation(DailyDetailActivity.this, R.anim.anim_small);
//                itemParise.setIcon(R.drawable.praised);
//                Toast.makeText(DailyDetailActivity.this, "点赞数:" + popularity, Toast.LENGTH_SHORT).show();
//                return true;
//
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

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


    private void share()
    {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_from) + mDaily.getTitle() + "，http://daily.zhihu.com/story/" + mDaily.getId());
        startActivity(Intent.createChooser(intent, mDaily.getTitle()));
    }
}
