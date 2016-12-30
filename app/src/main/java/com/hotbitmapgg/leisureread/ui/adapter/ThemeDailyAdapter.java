package com.hotbitmapgg.leisureread.ui.adapter;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.leisureread.mvp.model.entity.ThemeDailyInfo;
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
 * @HotBitmapGG 主题日报列表Adapter
 */
public class ThemeDailyAdapter extends AbsRecyclerViewAdapter<ThemeDailyInfo.OthersBean> {

  public ThemeDailyAdapter(RecyclerView recyclerView) {

    super(recyclerView);
  }


  @Override
  public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    bindContext(parent.getContext());
    return new ItemViewHolder(
        LayoutInflater.from(getContext()).inflate(R.layout.item_themes_daily, parent, false));
  }


  @Override
  public void onBindViewHolder(ClickableViewHolder holder, int position) {

    if (holder instanceof ItemViewHolder) {
      ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
      ThemeDailyInfo.OthersBean othersBean = mDataSources.get(position);

      Glide.with(getContext())
          .load(othersBean.getThumbnail())
          .placeholder(R.drawable.account_avatar)
          .into(itemViewHolder.mImage);

      itemViewHolder.mName.setText(othersBean.getName());
      itemViewHolder.mDes.setText(othersBean.getDescription());
    }

    super.onBindViewHolder(holder, position);
  }


  private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

    TextView mName;

    ImageView mImage;

    TextView mDes;


    ItemViewHolder(View itemView) {

      super(itemView);
      mName = $(R.id.item_type_name);
      mImage = $(R.id.item_type_img);
      mDes = $(R.id.item_type_des);
    }
  }
}
