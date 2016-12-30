package com.hotbitmapgg.leisureread.ui.adapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hotbitmapgg.leisureread.mvp.model.entity.ThemeDetailsInfo;
import com.hotbitmapgg.leisureread.widget.CircleImageView;
import com.hotbitmapgg.leisureread.widget.recycler.base.AbsRecyclerViewAdapter;
import com.hotbitmapgg.rxzhihu.R;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 主题日报编辑Adapter
 */
public class ThemeDetailsEditorsAdapter
    extends AbsRecyclerViewAdapter<ThemeDetailsInfo.EditorsBean> {

  public ThemeDetailsEditorsAdapter(RecyclerView recyclerView) {

    super(recyclerView);
  }


  @Override
  public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    bindContext(parent.getContext());
    return new ItemViewHolder(LayoutInflater.from(getContext())
        .inflate(R.layout.item_themes_details_editors, parent, false));
  }


  @Override
  public void onBindViewHolder(ClickableViewHolder holder, int position) {

    if (holder instanceof ItemViewHolder) {
      ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
      ThemeDetailsInfo.EditorsBean editorsBean = mDataSources.get(position);
      Glide.with(getContext()).load(editorsBean.getAvatar())
          .centerCrop()
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .placeholder(R.drawable.account_avatar)
          .into(itemViewHolder.mPic);
    }

    super.onBindViewHolder(holder, position);
  }


  private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

    CircleImageView mPic;


    ItemViewHolder(View itemView) {

      super(itemView);
      mPic = $(R.id.editor_pic);
    }
  }
}
