package com.hotbitmapgg.leisureread.ui.adapter;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyCommentInfo;
import com.hotbitmapgg.leisureread.utils.DateUtil;
import com.hotbitmapgg.leisureread.widget.CircleImageView;
import com.hotbitmapgg.leisureread.widget.recycler.base.AbsRecyclerViewAdapter;
import com.hotbitmapgg.rxzhihu.R;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 日报评论Adapter
 */
public class CommentAdapter extends AbsRecyclerViewAdapter<DailyCommentInfo.CommentsBean> {

  public CommentAdapter(RecyclerView recyclerView) {

    super(recyclerView);
  }


  @Override
  public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    bindContext(parent.getContext());
    return new ItemViewHolder(
        LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false));
  }


  @Override
  public void onBindViewHolder(ClickableViewHolder holder, int position) {

    if (holder instanceof ItemViewHolder) {
      ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
      DailyCommentInfo.CommentsBean commentsBean = mDataSources.get(position);

      Glide.with(getContext())
          .load(commentsBean.getAvatar())
          .dontAnimate()
          .placeholder(R.drawable.account_avatar)
          .into(itemViewHolder.mUserPic);

      itemViewHolder.mUserName.setText(commentsBean.getAuthor());
      itemViewHolder.mUserPariseNum.setText(String.valueOf(commentsBean.getLikes()));
      itemViewHolder.mUserContent.setText(commentsBean.getContent());
      itemViewHolder.mUserTime.setText(DateUtil.getTime(commentsBean.getTime()));
    }

    super.onBindViewHolder(holder, position);
  }


  private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

    CircleImageView mUserPic;

    TextView mUserName;

    TextView mUserPariseNum;

    TextView mUserContent;

    TextView mUserTime;


    ItemViewHolder(View itemView) {

      super(itemView);
      mUserPic = $(R.id.user_pic);
      mUserName = $(R.id.user_name);
      mUserPariseNum = $(R.id.user_parise_num);
      mUserContent = $(R.id.user_content);
      mUserTime = $(R.id.user_time);
    }
  }
}
