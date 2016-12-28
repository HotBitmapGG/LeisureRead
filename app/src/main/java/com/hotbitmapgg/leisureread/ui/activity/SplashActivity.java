package com.hotbitmapgg.leisureread.ui.activity;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.hotbitmapgg.leisureread.app.AppConstant;
import com.hotbitmapgg.rxzhihu.R;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 启动页界面
 */
public class SplashActivity extends Activity {

  @Bind(R.id.iv_splash)
  ImageView mSplashImageView;

  private Subscription subscribe;


  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);
    initSplashImage();
  }


  private void initSplashImage() {

    subscribe = Observable.timer(2000, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(aLong -> {
          animateImage();
        });
  }


  public void animateImage() {

    ObjectAnimator animatorX = ObjectAnimator.ofFloat(mSplashImageView, "scaleX", 1f,
        AppConstant.SCALE_END);
    ObjectAnimator animatorY = ObjectAnimator.ofFloat(mSplashImageView, "scaleY", 1f,
        AppConstant.SCALE_END);

    AnimatorSet set = new AnimatorSet();
    set.setDuration(AppConstant.ANIMATION_DURATION).play(animatorX).with(animatorY);
    set.start();

    set.addListener(new AnimatorListenerAdapter() {

      @Override
      public void onAnimationEnd(Animator animation) {

        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        SplashActivity.this.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
      }
    });
  }


  @Override
  protected void onDestroy() {

    super.onDestroy();
    if (subscribe != null && !subscribe.isUnsubscribed()) {
      subscribe.unsubscribe();
    }
  }
}
