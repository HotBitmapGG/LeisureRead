package com.hotbitmapgg.eyepetizer.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.eyepetizer.model.entity.HotNews;
import com.hotbitmapgg.eyepetizer.widget.recycler.base.AbsRecyclerViewAdapter;
import com.hotbitmapgg.rxzhihu.R;

/**
 * Created by hcc on 16/4/23 13:49
 * 100332338@qq.com
 */
public class HotNewsAdapter extends AbsRecyclerViewAdapter<HotNews.HotNewsInfo>
{

    public HotNewsAdapter(RecyclerView recyclerView)
    {

        super(recyclerView);
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_hot_news, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position)
    {

        if (holder instanceof ItemViewHolder)
        {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            HotNews.HotNewsInfo hotNewsInfo = mDataSources.get(position);
            Glide.with(getContext())
                    .load(hotNewsInfo.thumbnail)
                    .placeholder(R.drawable.account_avatar)
                    .into(itemViewHolder.mImageView);
            itemViewHolder.mDes.setText(hotNewsInfo.title);
        }
        super.onBindViewHolder(holder, position);
    }


    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder
    {

        ImageView mImageView;

        TextView mDes;

        ItemViewHolder(View itemView)
        {

            super(itemView);
            mImageView = $(R.id.item_img);
            mDes = $(R.id.item_des);
        }
    }
}
