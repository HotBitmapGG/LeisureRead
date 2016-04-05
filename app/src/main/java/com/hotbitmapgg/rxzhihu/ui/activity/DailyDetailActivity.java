package com.hotbitmapgg.rxzhihu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.base.BaseSwipeBackActivity;
import com.hotbitmapgg.rxzhihu.model.DailyBean;
import com.hotbitmapgg.rxzhihu.model.DailyDetail;
import com.hotbitmapgg.rxzhihu.network.RetrofitHelper;
import com.hotbitmapgg.rxzhihu.utils.HtmlUtil;
import com.hotbitmapgg.rxzhihu.utils.LogUtil;
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
        if(mActionBar != null)
        {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        mCollapsingToolbarLayout.setTitleEnabled(true);
        mActionBar.setTitle("");
        mToolbar.setNavigationIcon(R.mipmap.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                finish();
            }
        });


        startGetDailyDetail(mDaily == null ? id : mDaily.getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.menu_action_share:
                share();
                return true;

            case R.id.menu_action_fav:
                LogUtil.all("收藏");
                return true;

            case R.id.menu_action_comment:
                LogUtil.all("评论");
                return true;

            case R.id.menu_action_parise:
                LogUtil.all("点赞");

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
                            Glide.with(DailyDetailActivity.this).load(dailyDetail.getImage()).placeholder(R.mipmap.account_avatar).into(mDetailImage);
                            //设置标题
                            mDetailTitle.setText(dailyDetail.getTitle());
                            //设置图片来源
                            mDetailSource.setText(dailyDetail.getImage_source());
                            //设置web内容加载
                            String htmlData = HtmlUtil.createHtmlData(dailyDetail);
                            mWebView.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
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
