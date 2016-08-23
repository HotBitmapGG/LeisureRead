package com.hotbitmapgg.rxzhihu.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hcc on 16/4/5.
 * <p/>
 * 自定义Recycle分割间隔类
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration
{

    private int space;

    public SpacesItemDecoration(int space)
    {

        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {

        outRect.left = space;
        outRect.bottom = space;
        outRect.right = space;
        if (parent.getChildAdapterPosition(view) == 0)
        {
            outRect.top = space;
        }

        super.getItemOffsets(outRect, view, parent, state);
    }
}
