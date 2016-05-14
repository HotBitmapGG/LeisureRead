package com.hotbitmapgg.rxzhihu.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.base.AbsBaseActivity;
import com.hotbitmapgg.rxzhihu.utils.ImageUtil;

import java.io.File;

import butterknife.Bind;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by hcc on 16/4/5.
 * <p/>
 * 福利大图浏览界面
 */
public class FuliFullPicActivity extends AbsBaseActivity
{


    @Bind(R.id.full_pic)
    ImageView mImageView;

    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @Bind(R.id.toolbar)
    Toolbar mToolBar;

    private static final String EXTRA_URL = "extra_url";

    private static final String EXTRA_TITLE = "extra_title";

    public static final String TRANSIT_PIC = "picture";

    private String url;

    private PhotoViewAttacher mPhotoViewAttacher;

    private String title;

    private CompositeSubscription mCompositeSubscription;

    private boolean isHide = false;

    @Override
    public int getLayoutId()
    {

        return R.layout.activity_full_pic;
    }

    @Override
    public void initViews(Bundle savedInstanceState)
    {

        mCompositeSubscription = new CompositeSubscription();

        Intent intent = getIntent();
        if (intent != null)
        {
            url = intent.getStringExtra(EXTRA_URL);
            title = intent.getStringExtra(EXTRA_TITLE);
        }

        ViewCompat.setTransitionName(mImageView, TRANSIT_PIC);
        Glide.with(FuliFullPicActivity.this).load(url).into(mImageView);

        setUpPhotoAttacher();
    }

    @Override
    public void initToolBar()
    {

        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(title);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                onBackPressed();
            }
        });
        if (supportActionBar != null)
        {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        mAppBarLayout.setAlpha(0.3f);
        mToolBar.setBackgroundResource(R.color.black_90);
        mAppBarLayout.setBackgroundResource(R.color.black_90);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_fuli, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int itemId = item.getItemId();
        switch (itemId)
        {
            case R.id.action_fuli_share:
                // 分享
                ImageUtil.saveImageAndGetPathObservable(FuliFullPicActivity.this, url, title)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Uri>()
                        {

                            @Override
                            public void call(Uri uri)
                            {

                                share(uri);
                            }
                        }, new Action1<Throwable>()
                        {

                            @Override
                            public void call(Throwable throwable)
                            {

                                Toast.makeText(FuliFullPicActivity.this, "保存失败,请重试", Toast.LENGTH_SHORT).show();
                            }
                        });
                return true;

            case R.id.action_fuli_save:
                //保存
                saveImageToGallery();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public static Intent LuanchActivity(Activity activity, String url, String title)
    {

        Intent intent = new Intent(activity, FuliFullPicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_TITLE, title);
        return intent;
    }

    private void setUpPhotoAttacher()
    {

        mPhotoViewAttacher = new PhotoViewAttacher(mImageView);
        mPhotoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener()
        {

            @Override
            public void onViewTap(View view, float v, float v1)
            {

                //隐藏ToolBar
                hideOrShowToolbar();
            }
        });
        mPhotoViewAttacher.setOnLongClickListener(new View.OnLongClickListener()
        {

            @Override
            public boolean onLongClick(View v)
            {

                new AlertDialog.Builder(FuliFullPicActivity.this)
                        .setMessage("是否保存到本地?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                                saveImageToGallery();
                                dialog.dismiss();
                            }
                        })
                        .show();


                return true;
            }
        });
    }


    private void saveImageToGallery()
    {

        Subscription s = ImageUtil.saveImageAndGetPathObservable(this, url, title)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Uri>()
                {

                    @Override
                    public void call(Uri uri)
                    {

                        File appDir = new File(Environment.getExternalStorageDirectory(), "rx_zhihu");
                        String msg = String.format("图片已保存至 %s 文件夹", appDir.getAbsolutePath());
                        Toast.makeText(FuliFullPicActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }, new Action1<Throwable>()
                {

                    @Override
                    public void call(Throwable throwable)
                    {

                        Toast.makeText(FuliFullPicActivity.this, "保存失败,请重试", Toast.LENGTH_SHORT).show();
                    }
                });

        mCompositeSubscription.add(s);
    }

    /**
     * 分享图片
     *
     * @param uri
     */
    private void share(Uri uri)
    {

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, title));
    }

    protected void hideOrShowToolbar()
    {

        mAppBarLayout.animate()
                .translationY(isHide ? 0 : -mAppBarLayout.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();

        isHide = !isHide;
    }

    @Override
    protected void onDestroy()
    {

        mPhotoViewAttacher.cleanup();
        if(mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed())
        {
            mCompositeSubscription.unsubscribe();
        }
        super.onDestroy();
    }
}
