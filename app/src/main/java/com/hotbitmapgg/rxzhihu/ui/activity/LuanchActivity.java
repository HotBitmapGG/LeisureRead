package com.hotbitmapgg.rxzhihu.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.base.AbsBaseActivity;
import com.hotbitmapgg.rxzhihu.model.LuanchImageBean;
import com.hotbitmapgg.rxzhihu.network.RetrofitHelper;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/4/4.
 */
public class LuanchActivity extends AbsBaseActivity
{

    @Bind(R.id.iv_luanch)
    ImageView mLuanchImage;

    @Bind(R.id.tv_form)
    TextView mFormText;

    private static final String RESOLUTION = "1080*1776";


    private static final int ANIMATION_DURATION = 2000;

    private static final float SCALE_END = 1.13F;

    private Handler mHandler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {

            super.handleMessage(msg);
            if(msg.what == 0)
            {
                animateImage();
            }
        }
    };

    @Override
    public int getLayoutId()
    {

        return R.layout.activity_luanch;
    }

    @Override
    public void initViews(Bundle savedInstanceState)
    {

        getLuanchImage();
    }

    private void getLuanchImage()
    {

        RetrofitHelper.builder().getLuanchImage(RESOLUTION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LuanchImageBean>()
                {

                    @Override
                    public void call(LuanchImageBean luanchImageBean)
                    {

                        if (luanchImageBean != null)
                        {
                            String img = luanchImageBean.getImg();
                            Glide.with(LuanchActivity.this).load(img).error(R.mipmap.default_splash).into(mLuanchImage);
                            mFormText.setText(luanchImageBean.getText());
                            mHandler.sendEmptyMessageDelayed(0 , 1000);
                        }
                    }
                }, new Action1<Throwable>()
                {

                    @Override
                    public void call(Throwable throwable)
                    {
                        Glide.with(LuanchActivity.this).load(R.mipmap.default_splash).into(mLuanchImage);
                        mHandler.sendEmptyMessageDelayed(0 , 1000);
                    }
                });
    }

    private void animateImage()
    {

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mLuanchImage, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mLuanchImage, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter()
        {

            @Override
            public void onAnimationEnd(Animator animation)
            {

                startActivity(new Intent(LuanchActivity.this, MainActivity.class));
                LuanchActivity.this.finish();
            }
        });
    }

    @Override
    public void initToolBar()
    {

    }

    @Override
    protected void onDestroy()
    {

        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
