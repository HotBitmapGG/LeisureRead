package com.hotbitmapgg.leisureread.widget;

import com.hotbitmapgg.rxzhihu.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG emptyView
 */
public class EmptyView extends LinearLayout {

  public EmptyView(Context context) {

    this(context, null);
  }


  public EmptyView(Context context, AttributeSet attrs) {

    super(context, attrs);
    init();
  }


  private void init() {

    View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty_comment, null);
    addView(view);
  }
}
