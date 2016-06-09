package com.hotbitmapgg.rxzhihu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.model.DailySections;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcc on 16/4/23 13:49
 * 100332338@qq.com
 */
public class SectionsAdapter extends AbsRecyclerViewAdapter
{

    private List<DailySections.DailySectionsInfo> sectionsInfos = new ArrayList<>();

    public SectionsAdapter(RecyclerView recyclerView, List<DailySections.DailySectionsInfo> sectionsInfos)
    {

        super(recyclerView);
        this.sectionsInfos = sectionsInfos;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_sections, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position)
    {

        if (holder instanceof ItemViewHolder)
        {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            DailySections.DailySectionsInfo dailySectionsInfo = sectionsInfos.get(position);
            Glide.with(getContext()).load(dailySectionsInfo.thumbnail).placeholder(R.drawable.account_avatar).into(itemViewHolder.mImageView);
            itemViewHolder.mDes.setText(dailySectionsInfo.description);
            itemViewHolder.mName.setText(dailySectionsInfo.name);
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount()
    {

        return sectionsInfos.size();
    }

    public class ItemViewHolder extends ClickableViewHolder
    {

        public ImageView mImageView;

        public TextView mDes;

        public TextView mName;

        public ItemViewHolder(View itemView)
        {

            super(itemView);
            mImageView = $(R.id.item_img);
            mDes = $(R.id.item_des);
            mName = $(R.id.item_name);
        }
    }
}
