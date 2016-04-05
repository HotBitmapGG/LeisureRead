package com.hotbitmapgg.rxzhihu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.base.BaseSwipeBackActivity;

import butterknife.Bind;

/**
 * Created by hcc on 16/4/5.
 */
public class FuliFullPicActivity extends BaseSwipeBackActivity
{


    @Bind(R.id.full_pic)
    ImageView mImageView;

    private static final String EXTRA_URL = "extra_url";

    private String url;

    @Override
    public int getLayoutId()
    {

        return R.layout.activity_full_pic;
    }

    @Override
    public void initViews(Bundle savedInstanceState)
    {

        Intent intent = getIntent();
        if (intent != null)
        {
            url = intent.getStringExtra(EXTRA_URL);
        }

        Glide.with(FuliFullPicActivity.this).load(url).into(mImageView);
    }


    public static void LuanchActivity(Activity activity, String url)
    {

        Intent intent = new Intent(activity, FuliFullPicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_URL, url);
        activity.startActivity(intent);
    }
}
