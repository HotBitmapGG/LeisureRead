package com.hotbitmapgg.rxzhihu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.base.BaseSwipeBackActivity;
import com.hotbitmapgg.rxzhihu.model.DailyBean;
import com.hotbitmapgg.rxzhihu.model.DailyDetail;
import com.hotbitmapgg.rxzhihu.model.DailyExtraMessage;
import com.hotbitmapgg.rxzhihu.network.RetrofitHelper;
import com.hotbitmapgg.rxzhihu.utils.HtmlUtil;
import com.hotbitmapgg.rxzhihu.utils.LogUtil;
import com.hotbitmapgg.rxzhihu.utils.PreferenceUtils;
import com.hotbitmapgg.rxzhihu.widget.CircleProgressView;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/4/4.
 */
public class DailyDetailActivity extends BaseSwipeBackActivity
{

    @Bind(R.id.coll_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

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

    private DailyBean mDaily;

    private ActionBar mActionBar;

    private static final String EXTRA_DETAIL = "extra_detail";

    private static final String EXTRA_ID = "extra_id";

    private int id;

    private MenuItem itemCommentNum;

    private MenuItem itemPariseNum;

    private DailyExtraMessage mDailyExtraMessage;

    private MenuItem itemParise;

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
        // 初始化ToolBar
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null)
        {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        mCollapsingToolbarLayout.setTitleEnabled(true);
        mActionBar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                finish();
            }
        });


        startGetDailyDetail(mDaily == null ? id : mDaily.getId());

        //第一次进入提示用户可以左滑返回
        showSnackBarHint();
    }

    private void showSnackBarHint()
    {

        boolean isShow = PreferenceUtils.getBoolean("isShowSnack", false);
        if (!isShow)
        {
            Snackbar.make(mToolbar, "左滑可以返回主页哦~", Snackbar.LENGTH_LONG).show();
            this.isShowSnack = true;
            PreferenceUtils.putBoolean("isShowSnack", this.isShowSnack);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_detail, menu);
        itemCommentNum = menu.findItem(R.id.menu_action_comment_num);
        itemPariseNum = menu.findItem(R.id.menu_action_parise_num);
        itemParise = menu.findItem(R.id.menu_action_parise);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.menu_action_share:
                //分享新闻
                share();
                return true;

            case R.id.menu_action_fav:
                //查看新闻推荐者
                DailyRecommendEditorsActivity.luancher(DailyDetailActivity.this, mDaily == null ? id : mDaily.getId());
                return true;

            case R.id.menu_action_comment:
                // 查看新闻评论
                DailyCommentActivity.luancher(DailyDetailActivity.this, mDaily == null ? id : mDaily.getId(), mDailyExtraMessage.comments, mDailyExtraMessage.longComments, mDailyExtraMessage.shortComments);
                return true;

            case R.id.menu_action_parise:
                //执行点赞动画
                AnimationUtils.loadAnimation(DailyDetailActivity.this, R.anim.anim_small);
                itemParise.setIcon(R.drawable.praised);
                Toast.makeText(DailyDetailActivity.this , "点赞数:" + popularity ,Toast.LENGTH_SHORT).show();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startGetDailyDetail(int id)
    {

        RetrofitHelper.builder().getNewsDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0()
                {

                    @Override
                    public void call()
                    {

                        showProgress();
                    }
                })
                .subscribe(new Action1<DailyDetail>()
                {

                    @Override
                    public void call(DailyDetail dailyDetail)
                    {

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
                    }
                }, new Action1<Throwable>()
                {

                    @Override
                    public void call(Throwable throwable)
                    {

                        hideProgress();
                        LogUtil.all("数据加载失败");
                    }
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
                .subscribe(new Action1<DailyExtraMessage>()
                {

                    @Override
                    public void call(DailyExtraMessage dailyExtraMessage)
                    {

                        if (dailyExtraMessage != null)
                        {
                            mDailyExtraMessage = dailyExtraMessage;

                            comments = dailyExtraMessage.comments;
                            popularity = dailyExtraMessage.popularity;

                            itemCommentNum.setTitle(comments + "");
                            itemPariseNum.setTitle(popularity + "");
                            DailyDetailActivity.this.getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                        }
                    }
                }, new Action1<Throwable>()
                {

                    @Override
                    public void call(Throwable throwable)
                    {

                    }
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
