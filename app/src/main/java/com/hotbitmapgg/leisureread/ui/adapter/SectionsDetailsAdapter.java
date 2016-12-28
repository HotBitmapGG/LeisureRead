package com.hotbitmapgg.leisureread.ui.adapter;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.leisureread.mvp.model.entity.SectionsDetailsInfo;
import com.hotbitmapgg.leisureread.widget.recycler.base.AbsRecyclerViewAdapter;
import com.hotbitmapgg.rxzhihu.R;
import java.util.List;

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
 * @HotBitmapGG 专栏详情Adapter
 */
public class SectionsDetailsAdapter extends AbsRecyclerViewAdapter {

  private List<SectionsDetailsInfo.StoriesBean> sectionsDetailsInfos;


  public SectionsDetailsAdapter(RecyclerView recyclerView, List<SectionsDetailsInfo.StoriesBean> sectionsDetailsInfos) {

    super(recyclerView);
    this.sectionsDetailsInfos = sectionsDetailsInfos;
  }


  @Override
  public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    bindContext(parent.getContext());
    return new ItemViewHolder(
        LayoutInflater.from(getContext()).inflate(R.layout.item_sections_details, parent, false));
  }


  @Override
  public void onBindViewHolder(ClickableViewHolder holder, int position) {

    if (holder instanceof ItemViewHolder) {
      ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
      SectionsDetailsInfo.StoriesBean sectionsDetailsInfo = sectionsDetailsInfos.get(position);
      Glide.with(getContext())
          .load(sectionsDetailsInfo.getImages().get(0))
          .placeholder(R.drawable.account_avatar)
          .into(itemViewHolder.mImageView);
      itemViewHolder.mTitle.setText(sectionsDetailsInfo.getTitle());
      itemViewHolder.mTime.setText(sectionsDetailsInfo.getDisplay_date());
    }
    super.onBindViewHolder(holder, position);
  }


  @Override
  public int getItemCount() {

    return sectionsDetailsInfos.size();
  }


  public void addData(SectionsDetailsInfo.StoriesBean info) {

    sectionsDetailsInfos.add(info);
    this.notifyDataSetChanged();
  }


  public class ItemViewHolder extends ClickableViewHolder {

    public ImageView mImageView;

    public TextView mTitle;

    public TextView mTime;


    public ItemViewHolder(View itemView) {

      super(itemView);
      mImageView = $(R.id.item_image);
      mTitle = $(R.id.item_title);
      mTime = $(R.id.item_time);
    }
  }
}
