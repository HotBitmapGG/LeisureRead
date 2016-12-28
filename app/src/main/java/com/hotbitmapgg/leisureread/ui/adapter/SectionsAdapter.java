package com.hotbitmapgg.leisureread.ui.adapter;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.leisureread.mvp.model.entity.SectionsInfo;
import com.hotbitmapgg.leisureread.widget.recycler.base.AbsRecyclerViewAdapter;
import com.hotbitmapgg.rxzhihu.R;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 专栏列表Adapter
 */
public class SectionsAdapter extends AbsRecyclerViewAdapter<SectionsInfo.DataBean> {

  public SectionsAdapter(RecyclerView recyclerView) {

    super(recyclerView);
  }


  @Override
  public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    bindContext(parent.getContext());
    return new ItemViewHolder(
        LayoutInflater.from(getContext()).inflate(R.layout.item_sections, parent, false));
  }


  @Override
  public void onBindViewHolder(ClickableViewHolder holder, int position) {

    if (holder instanceof ItemViewHolder) {
      ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
      SectionsInfo.DataBean dailySectionsInfo = mDataSources.get(position);
      Glide.with(getContext())
          .load(dailySectionsInfo.getThumbnail())
          .placeholder(R.drawable.account_avatar)
          .into(itemViewHolder.mImageView);
      itemViewHolder.mDes.setText(dailySectionsInfo.getDescription());
      itemViewHolder.mName.setText(dailySectionsInfo.getName());
    }
    super.onBindViewHolder(holder, position);
  }


  public class ItemViewHolder extends ClickableViewHolder {

    public ImageView mImageView;

    public TextView mDes;

    public TextView mName;


    public ItemViewHolder(View itemView) {

      super(itemView);
      mImageView = $(R.id.item_img);
      mDes = $(R.id.item_des);
      mName = $(R.id.item_name);
    }
  }
}
