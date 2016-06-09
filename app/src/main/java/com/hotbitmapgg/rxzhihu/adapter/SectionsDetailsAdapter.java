package com.hotbitmapgg.rxzhihu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.model.SectionsDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcc on 16/4/23 13:49
 * 100332338@qq.com
 */
public class SectionsDetailsAdapter extends AbsRecyclerViewAdapter
{

    private List<SectionsDetails.SectionsDetailsInfo> sectionsDetailsInfos = new ArrayList<>();

    public SectionsDetailsAdapter(RecyclerView recyclerView, List<SectionsDetails.SectionsDetailsInfo> sectionsDetailsInfos)
    {

        super(recyclerView);
        this.sectionsDetailsInfos = sectionsDetailsInfos;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_sections_details, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position)
    {

        if (holder instanceof ItemViewHolder)
        {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            SectionsDetails.SectionsDetailsInfo sectionsDetailsInfo = sectionsDetailsInfos.get(position);
            Glide.with(getContext()).load(sectionsDetailsInfo.images.get(0)).placeholder(R.drawable.account_avatar).into(itemViewHolder.mImageView);
            itemViewHolder.mTitle.setText(sectionsDetailsInfo.title);
            itemViewHolder.mTime.setText(sectionsDetailsInfo.displayDate);
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount()
    {

        return sectionsDetailsInfos.size();
    }

    public void addData(SectionsDetails.SectionsDetailsInfo info)
    {

        sectionsDetailsInfos.add(info);
    }

    public class ItemViewHolder extends ClickableViewHolder
    {

        public ImageView mImageView;

        public TextView mTitle;

        public TextView mTime;

        public ItemViewHolder(View itemView)
        {

            super(itemView);
            mImageView = $(R.id.item_image);
            mTitle = $(R.id.item_title);
            mTime = $(R.id.item_time);
        }
    }
}
