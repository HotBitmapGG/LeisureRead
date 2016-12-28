package com.hotbitmapgg.leisureread.ui.adapter;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyCommentInfo;
import com.hotbitmapgg.leisureread.utils.DateUtil;
import com.hotbitmapgg.leisureread.widget.CircleImageView;
import com.hotbitmapgg.leisureread.widget.recycler.base.AbsRecyclerViewAdapter;
import com.hotbitmapgg.rxzhihu.R;
import java.util.ArrayList;
import java.util.List;

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
public class CommentAdapter extends AbsRecyclerViewAdapter {

  private List<DailyCommentInfo.CommentsBean> commentInfos = new ArrayList<>();


  public CommentAdapter(RecyclerView recyclerView, List<DailyCommentInfo.CommentsBean> commentInfos) {

    super(recyclerView);
    this.commentInfos = commentInfos;
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
      DailyCommentInfo.CommentsBean commentInfo = commentInfos.get(position);
      Glide.with(getContext())
          .load(commentInfo.getAvatar())
          .dontAnimate()
          .placeholder(R.drawable.account_avatar)
          .into(itemViewHolder.mUserPic);
      itemViewHolder.mUserName.setText(commentInfo.getAuthor());
      itemViewHolder.mUserPariseNum.setText(String.valueOf(commentInfo.getLikes()));
      itemViewHolder.mUserContent.setText(commentInfo.getContent());
      itemViewHolder.mUserTime.setText(DateUtil.getTime(commentInfo.getTime()));
    }

    super.onBindViewHolder(holder, position);
  }


  @Override
  public int getItemCount() {

    return commentInfos.size();
  }


  public class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

    public CircleImageView mUserPic;

    public TextView mUserName;

    public TextView mUserPariseNum;

    public TextView mUserContent;

    public TextView mUserTime;


    public ItemViewHolder(View itemView) {

      super(itemView);
      mUserPic = $(R.id.user_pic);
      mUserName = $(R.id.user_name);
      mUserPariseNum = $(R.id.user_parise_num);
      mUserContent = $(R.id.user_content);
      mUserTime = $(R.id.user_time);
    }
  }
}
