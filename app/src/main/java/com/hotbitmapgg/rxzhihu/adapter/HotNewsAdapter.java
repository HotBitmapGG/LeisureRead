package com.hotbitmapgg.rxzhihu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.model.HotNews;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcc on 16/4/23 13:49
 * 100332338@qq.com
 */
public class HotNewsAdapter extends AbsRecyclerViewAdapter
{

    private List<HotNews.HotNewsInfo> hotNewsInfos = new ArrayList<>();

    public HotNewsAdapter(RecyclerView recyclerView, List<HotNews.HotNewsInfo> hotNewsInfos)
    {

        super(recyclerView);
        this.hotNewsInfos = hotNewsInfos;
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
            HotNews.HotNewsInfo hotNewsInfo = hotNewsInfos.get(position);
            Glide.with(getContext()).load(hotNewsInfo.thumbnail).placeholder(R.drawable.account_avatar).into(itemViewHolder.mImageView);
            itemViewHolder.mDes.setText(hotNewsInfo.title);
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount()
    {

        return hotNewsInfos.size();
    }

    public class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder
    {

        public ImageView mImageView;

        public TextView mDes;

        public ItemViewHolder(View itemView)
        {

            super(itemView);
            mImageView = $(R.id.item_img);
            mDes = $(R.id.item_des);
        }
    }
}
