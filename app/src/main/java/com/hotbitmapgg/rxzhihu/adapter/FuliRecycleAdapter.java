package com.hotbitmapgg.rxzhihu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.model.FuliItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcc on 16/4/5.
 */
public class FuliRecycleAdapter extends AbsRecyclerViewAdapter
{

    private List<FuliItem> items = new ArrayList<>();


    public FuliRecycleAdapter(RecyclerView recyclerView, List<FuliItem> items)
    {

        super(recyclerView);
        this.items = items;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.card_item_fuli, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position)
    {

        if (holder instanceof ItemViewHolder)
        {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            Glide.with(getContext()).
                    load(items.get(position).imageUrl).
                    centerCrop().
                    diskCacheStrategy(DiskCacheStrategy.ALL).
                    placeholder(R.mipmap.account_avatar).
                    into(itemViewHolder.mImg);
            itemViewHolder.mText.setText(items.get(position).description);
        }


        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount()
    {

        return items == null ? 0 : items.size();
    }

    public void addData(FuliItem item)
    {

        items.add(item);
    }

    public class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder
    {

        public ImageView mImg;

        public TextView mText;

        public ItemViewHolder(View itemView)
        {

            super(itemView);
            mImg = $(R.id.item_img);
            mText = $(R.id.item_text);
        }
    }
}
