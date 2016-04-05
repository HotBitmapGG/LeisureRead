package com.hotbitmapgg.rxzhihu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.model.DailyTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcc on 16/4/4.
 */
public class DailyTypeRecycleAdapter extends AbsRecyclerViewAdapter
{

    private List<DailyTypeBean.SubjectDaily> others = new ArrayList<>();

    public DailyTypeRecycleAdapter(RecyclerView recyclerView, List<DailyTypeBean.SubjectDaily> others)
    {

        super(recyclerView);
        this.others = others;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_nav, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position)
    {

        super.onBindViewHolder(holder, position);

        if (holder instanceof ItemViewHolder)
        {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mName.setText(others.get(position).getName());
        }
    }

    @Override
    public int getItemCount()
    {

        return others.size();
    }

    public class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder
    {

        public TextView mName;

        public ImageView mArrow;

        public ItemViewHolder(View itemView)
        {

            super(itemView);
            mName = $(R.id.item_type_name);
            mArrow = $(R.id.item_type_arrow);
        }
    }
}
