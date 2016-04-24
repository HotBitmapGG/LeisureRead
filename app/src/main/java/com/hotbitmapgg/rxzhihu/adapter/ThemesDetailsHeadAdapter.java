package com.hotbitmapgg.rxzhihu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.model.Editors;
import com.hotbitmapgg.rxzhihu.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcc on 16/4/18 21:03
 * 100332338@qq.com
 */
public class ThemesDetailsHeadAdapter extends AbsRecyclerViewAdapter
{

    private List<Editors> editors = new ArrayList<>();

    public ThemesDetailsHeadAdapter(RecyclerView recyclerView, List<Editors> editors)
    {

        super(recyclerView);
        this.editors = editors;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_themes_details_editors, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position)
    {


        if (holder instanceof ItemViewHolder)
        {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            Glide.with(getContext()).load(editors.get(position).getAvatar()).into(itemViewHolder.mPic);
        }

        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount()
    {

        return editors.size();
    }

    public class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder
    {

        public CircleImageView mPic;

        public ItemViewHolder(View itemView)
        {

            super(itemView);
            mPic = $(R.id.editor_pic);
        }
    }
}
