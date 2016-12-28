package com.hotbitmapgg.leisureread.ui.adapter;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.leisureread.mvp.model.entity.ThemeDetailsInfo;
import com.hotbitmapgg.leisureread.widget.CircleImageView;
import com.hotbitmapgg.leisureread.widget.recycler.base.AbsRecyclerViewAdapter;
import com.hotbitmapgg.rxzhihu.R;
import java.util.List;

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
public class ThemeDetailsHeadAdapter extends AbsRecyclerViewAdapter {

  private List<ThemeDetailsInfo.EditorsBean> editors;


  public ThemeDetailsHeadAdapter(RecyclerView recyclerView, List<ThemeDetailsInfo.EditorsBean> editors) {

    super(recyclerView);
    this.editors = editors;
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
      Glide.with(getContext()).load(editors.get(position).getAvatar()).into(itemViewHolder.mPic);
    }

    super.onBindViewHolder(holder, position);
  }


  @Override
  public int getItemCount() {

    return editors.size();
  }


  public class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

    public CircleImageView mPic;


    public ItemViewHolder(View itemView) {

      super(itemView);
      mPic = $(R.id.editor_pic);
    }
  }
}
