package com.hotbitmapgg.leisureread.ui.adapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.hotbitmapgg.leisureread.mvp.model.entity.DailyListBean;
import com.hotbitmapgg.leisureread.ui.activity.DailyDetailActivity;
import com.hotbitmapgg.leisureread.utils.DateUtil;
import com.hotbitmapgg.leisureread.utils.LogUtil;
import com.hotbitmapgg.leisureread.utils.WeekUtil;
import com.hotbitmapgg.rxzhihu.R;
import java.util.List;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
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
 * @HotBitmapGG 日报列表Adapter
 */
public class DailyListAdapter extends RecyclerView.Adapter<DailyListAdapter.ItemContentViewHolder> {

  private static final int ITEM_CONTENT = 0;

  private static final int ITEM_TIME = 1;

  private List<DailyListBean.StoriesBean> stories;

  private Context mContext;


  public DailyListAdapter(Context context, List<DailyListBean.StoriesBean> stories) {

    this.stories = stories;
    this.mContext = context;
  }


  @Override
  public int getItemViewType(int position) {

    if (position == 0) {
      return ITEM_TIME;
    }
    String time = stories.get(position).getDate();
    int index = position - 1;
    boolean isDifferent = !stories.get(index).getDate().equals(time);

    return isDifferent ? ITEM_TIME : ITEM_CONTENT;
  }


  @Override
  public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    if (viewType == ITEM_TIME) {
      return new ItemTimeViewHolder(LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_daily_list_time, parent, false));
    } else {
      return new ItemContentViewHolder(LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_daily_list, parent, false));
    }
  }


  @Override
  public void onBindViewHolder(ItemContentViewHolder holder, int position) {

    DailyListBean.StoriesBean storiesBean = stories.get(position);
    if (storiesBean == null) {
      return;
    }

    if (holder instanceof ItemTimeViewHolder) {
      setDailyDate(holder, storiesBean);
      ItemTimeViewHolder itemTimeViewHolder = (ItemTimeViewHolder) holder;
      String timeStr;
      if (position == 0) {
        timeStr = "今日热闻";
      } else {
        timeStr = DateUtil.formatDate(storiesBean.getDate()) + "  " +
            WeekUtil.getWeek(storiesBean.getDate());
      }
      itemTimeViewHolder.mTime.setText(timeStr);
    } else {
      setDailyDate(holder, storiesBean);
    }
  }


  /**
   * 设置数据给普通内容Item
   */
  private void setDailyDate(final ItemContentViewHolder holder, final DailyListBean.StoriesBean storiesBean) {

    holder.mTitle.setText(storiesBean.getTitle());
    List<String> images = storiesBean.getImages();
    if (images != null && images.size() > 0) {
      Glide.with(mContext)
          .load(images.get(0))
          .placeholder(R.drawable.account_avatar)
          .into(holder.mPic);
    }
    boolean multipic = storiesBean.isMultipic();
    if (multipic) {

      holder.mMorePic.setVisibility(View.VISIBLE);
    } else {
      holder.mMorePic.setVisibility(View.GONE);
    }
    if (!storiesBean.isRead()) {
      holder.mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_unread));
    } else {
      holder.mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_read));
    }
    holder.mLayout.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {

        LogUtil.all("点击");
        if (!storiesBean.isRead()) {
          storiesBean.setRead(true);
          holder.mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_read));
          new Thread(new Runnable() {

            @Override
            public void run() {

              //mDailyDao.insertReadNew(storiesBean.getId() + "");
            }
          }).start();
        }
        //跳转到详情界面
        DailyDetailActivity.lanuch(mContext, storiesBean);
      }
    });
  }


  public void updateData(List<DailyListBean.StoriesBean> stories) {

    this.stories = stories;
    notifyDataSetChanged();
  }


  public void addData(List<DailyListBean.StoriesBean> stories) {

    if (this.stories == null) {
      updateData(stories);
    } else {
      this.stories.addAll(stories);
      notifyDataSetChanged();
    }
  }


  @Override
  public int getItemCount() {

    return stories.size() == 0 ? 0 : stories.size();
  }


  public List<DailyListBean.StoriesBean> getmDailyList() {

    return stories;
  }


  public class ItemTimeViewHolder extends ItemContentViewHolder {

    @Bind(R.id.item_time)
    TextView mTime;


    public ItemTimeViewHolder(View itemView) {

      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public class ItemContentViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.card_view)
    CardView mLayout;

    @Bind(R.id.item_image)
    ImageView mPic;

    @Bind(R.id.item_title)
    TextView mTitle;

    @Bind(R.id.item_more_pic)
    ImageView mMorePic;


    public ItemContentViewHolder(View itemView) {

      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
