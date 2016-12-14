package com.hotbitmapgg.leisureread.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyRecommendInfo;
import com.hotbitmapgg.leisureread.widget.CircleImageView;
import com.hotbitmapgg.leisureread.widget.recycler.base.AbsRecyclerViewAdapter;
import com.hotbitmapgg.rxzhihu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcc on 16/4/24 10:10
 * 100332338@qq.com
 */
public class RecommendEditorAdapter extends AbsRecyclerViewAdapter {

  private List<DailyRecommendInfo.Editor> editors = new ArrayList<>();


  public RecommendEditorAdapter(RecyclerView recyclerView, List<DailyRecommendInfo.Editor> editors) {

    super(recyclerView);
    this.editors = editors;
  }


  @Override
  public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    bindContext(parent.getContext());
    return new ItemViewHolder(
        LayoutInflater.from(getContext()).inflate(R.layout.item_recommend_editors, parent, false));
  }


  @Override
  public void onBindViewHolder(ClickableViewHolder holder, int position) {

    if (holder instanceof ItemViewHolder) {
      ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
      DailyRecommendInfo.Editor editor = editors.get(position);
      Glide.with(getContext())
          .load(editor.avatar)
          .placeholder(R.drawable.account_avatar)
          .into(itemViewHolder.mUserPic);
      itemViewHolder.mUserName.setText(editor.name);
      itemViewHolder.mUserWeiBo.setText(editor.bio);
      itemViewHolder.mUserTitle.setText(editor.title);
    }

    super.onBindViewHolder(holder, position);
  }


  @Override
  public int getItemCount() {

    return editors.size();
  }


  public class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

    public CircleImageView mUserPic;

    public TextView mUserName;

    public TextView mUserWeiBo;

    public TextView mUserTitle;


    public ItemViewHolder(View itemView) {

      super(itemView);
      mUserPic = $(R.id.user_pic);
      mUserName = $(R.id.user_name);
      mUserWeiBo = $(R.id.user_weibo);
      mUserTitle = $(R.id.user_title);
    }
  }
}
