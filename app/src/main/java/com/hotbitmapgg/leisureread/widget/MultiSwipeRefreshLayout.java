package com.hotbitmapgg.leisureread.widget;

import com.hotbitmapgg.rxzhihu.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 可以设置多个子View的SwipeRefreshLayout
 */
public class MultiSwipeRefreshLayout extends SwipeRefreshLayout {

  private CanChildScrollUpCallback mCanChildScrollUpCallback;

  private Drawable mForegroundDrawable;


  public MultiSwipeRefreshLayout(Context context) {

    this(context, null);
  }


  public MultiSwipeRefreshLayout(Context context, AttributeSet attrs) {

    super(context, attrs);
    final TypedArray a = context.obtainStyledAttributes(attrs,
        R.styleable.MultiSwipeRefreshLayout, 0, 0);

    mForegroundDrawable = a.getDrawable(
        R.styleable.MultiSwipeRefreshLayout_foreground);
    if (mForegroundDrawable != null) {
      mForegroundDrawable.setCallback(this);
      setWillNotDraw(false);
    }
    a.recycle();
  }


  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {

    super.onSizeChanged(w, h, oldw, oldh);
    if (mForegroundDrawable != null) {
      mForegroundDrawable.setBounds(0, 0, w, h);
    }
  }


  public void setCanChildScrollUpCallback(CanChildScrollUpCallback canChildScrollUpCallback) {

    mCanChildScrollUpCallback = canChildScrollUpCallback;
  }


  public interface CanChildScrollUpCallback {

    boolean canSwipeRefreshChildScrollUp();
  }


  @Override
  public boolean canChildScrollUp() {

    if (mCanChildScrollUpCallback != null) {
      return mCanChildScrollUpCallback.canSwipeRefreshChildScrollUp();
    }
    return super.canChildScrollUp();
  }
}
