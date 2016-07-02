package com.hotbitmapgg.rxzhihu.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.model.LuanchImageBean;
import com.hotbitmapgg.rxzhihu.network.RetrofitHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/4/4.
 * <p/>
 * Tips:App启动页面 该页面不要继承AppCompatActivity
 * 会导致界面启动卡顿 加载主题的原因.
 */
public class EntryActivity extends Activity
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
            if (msg.what == 0)
            {
                animateImage();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luanch);
        ButterKnife.bind(this);
    }


    @Override
    protected void onResume()
    {

        getLuanchImage();
        super.onResume();
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
                            Glide.with(EntryActivity.this).load(img).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.default_splash).into(mLuanchImage);
                            mFormText.setText(luanchImageBean.getText());
                            mHandler.sendEmptyMessageDelayed(0, 1000);
                        }
                    }
                }, new Action1<Throwable>()
                {

                    @Override
                    public void call(Throwable throwable)
                    {

                        Glide.with(EntryActivity.this).load(R.drawable.default_splash).into(mLuanchImage);
                        mHandler.sendEmptyMessageDelayed(0, 1000);
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

                startActivity(new Intent(EntryActivity.this, MainActivity.class));
                EntryActivity.this.finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    protected void onDestroy()
    {

        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
