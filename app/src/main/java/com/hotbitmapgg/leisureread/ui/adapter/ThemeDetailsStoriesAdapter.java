package com.hotbitmapgg.leisureread.ui.adapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hotbitmapgg.leisureread.mvp.model.entity.ThemeDetailsInfo;
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
public class ThemeDetailsStoriesAdapter
    extends AbsRecyclerViewAdapter<ThemeDetailsInfo.StoriesBean> {

  public ThemeDetailsStoriesAdapter(RecyclerView recyclerView) {

    super(recyclerView);
  }


  @Override
  public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    bindContext(parent.getContext());
    return new ItemViewHolder(
        LayoutInflater.from(getContext()).inflate(R.layout.item_themes_stories, parent, false));
  }


  @Override
  public void onBindViewHolder(ClickableViewHolder holder, int position) {

    if (holder instanceof ItemViewHolder) {
      ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
      ThemeDetailsInfo.StoriesBean storiesBean = mDataSources.get(position);

      if (storiesBean.getImages() == null) {
        itemViewHolder.mImg.setVisibility(View.GONE);
      } else {
        Glide.with(getContext())
            .load(storiesBean.getImages().get(0))
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.account_avatar)
            .into(itemViewHolder.mImg);
      }

      itemViewHolder.mTitle.setText(storiesBean.getTitle());
    }
    super.onBindViewHolder(holder, position);
  }


  private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

    ImageView mImg;

    TextView mTitle;


    ItemViewHolder(View itemView) {

      super(itemView);
      mImg = $(R.id.item_image);
      mTitle = $(R.id.item_title);
    }
  }
}
