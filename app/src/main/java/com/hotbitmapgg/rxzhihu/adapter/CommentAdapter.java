package com.hotbitmapgg.rxzhihu.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.model.DailyComment;
import com.hotbitmapgg.rxzhihu.utils.DateUtil;
import com.hotbitmapgg.rxzhihu.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcc on 16/4/23 12:36
 * 100332338@qq.com
 */
public class CommentAdapter extends AbsRecyclerViewAdapter
{

    private List<DailyComment.CommentInfo> commentInfos = new ArrayList<>();

    public CommentAdapter(RecyclerView recyclerView, List<DailyComment.CommentInfo> commentInfos)
    {

        super(recyclerView);
        this.commentInfos = commentInfos;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false));
    }


    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position)
    {

        if (holder instanceof ItemViewHolder)
        {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            DailyComment.CommentInfo commentInfo = commentInfos.get(position);
            Glide.with(getContext())
                    .load(commentInfo.avatar)
                    .dontAnimate()
                    .placeholder(R.drawable.account_avatar)
                    .into(itemViewHolder.mUserPic);
            itemViewHolder.mUserName.setText(commentInfo.author);
            itemViewHolder.mUserPariseNum.setText(commentInfo.likes + "");
            itemViewHolder.mUserContent.setText(commentInfo.content);
            itemViewHolder.mUserTime.setText(DateUtil.getTime(commentInfo.time));
        }

        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount()
    {

        return commentInfos.size();
    }

    public class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder
    {

        public CircleImageView mUserPic;

        public TextView mUserName;

        public TextView mUserPariseNum;

        public TextView mUserContent;

        public TextView mUserTime;

        public ItemViewHolder(View itemView)
        {

            super(itemView);
            mUserPic = $(R.id.user_pic);
            mUserName = $(R.id.user_name);
            mUserPariseNum = $(R.id.user_parise_num);
            mUserContent = $(R.id.user_content);
            mUserTime = $(R.id.user_time);
        }
    }
}
