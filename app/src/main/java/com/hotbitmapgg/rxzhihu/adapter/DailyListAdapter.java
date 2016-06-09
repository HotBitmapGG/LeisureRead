package com.hotbitmapgg.rxzhihu.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.db.DailyDao;
import com.hotbitmapgg.rxzhihu.model.DailyBean;
import com.hotbitmapgg.rxzhihu.ui.activity.DailyDetailActivity;
import com.hotbitmapgg.rxzhihu.utils.DateUtil;
import com.hotbitmapgg.rxzhihu.utils.LogUtil;
import com.hotbitmapgg.rxzhihu.utils.WeekUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hcc on 16/4/2.
 */
public class DailyListAdapter extends RecyclerView.Adapter<DailyListAdapter.ItemContentViewHolder>
{

    private static final int ITEM_CONTENT = 0;

    private static final int ITEM_TIME = 1;

    private List<DailyBean> dailys = new ArrayList<>();

    private DailyDao mDailyDao;

    private Context mContext;


    public DailyListAdapter(Context context, List<DailyBean> dailys)
    {

        this.dailys = dailys;
        this.mContext = context;
        this.mDailyDao = new DailyDao(context);
    }

    @Override
    public int getItemViewType(int position)
    {

        if (position == 0)
        {
            return ITEM_TIME;
        }
        String time = dailys.get(position).getDate();
        int index = position - 1;
        boolean isDifferent = !dailys.get(index).getDate().equals(time);
        int pos = isDifferent ? ITEM_TIME : ITEM_CONTENT;

        return pos;
    }

    @Override
    public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        if (viewType == ITEM_TIME)
        {
            return new ItemTimeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_list_time, parent, false));
        } else
        {
            return new ItemContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_list, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(ItemContentViewHolder holder, int position)
    {

        DailyBean dailyBean = dailys.get(position);
        if (dailyBean == null)
        {
            return;
        }

        if (holder instanceof ItemTimeViewHolder)
        {
            setDailyDate(holder, dailyBean);
            ItemTimeViewHolder itemTimeViewHolder = (ItemTimeViewHolder) holder;
            String timeStr = "";
            if (position == 0)
            {
                timeStr = "今日热闻";
            } else
            {
                timeStr = DateUtil.formatDate(dailyBean.getDate()) + "  " + WeekUtil.getWeek(dailyBean.getDate());
            }
            itemTimeViewHolder.mTime.setText(timeStr);
        } else
        {
            setDailyDate(holder, dailyBean);
        }
    }


    /**
     * 设置数据给普通内容Item
     *
     * @param holder
     * @param dailyBean
     */
    private void setDailyDate(final ItemContentViewHolder holder, final DailyBean dailyBean)
    {

        holder.mTitle.setText(dailyBean.getTitle());
        List<String> images = dailyBean.getImages();
        if (images != null && images.size() > 0)
        {
            Glide.with(mContext).load(images.get(0)).placeholder(R.drawable.account_avatar).into(holder.mPic);
        }
        boolean multipic = dailyBean.isMultipic();
        if (multipic)
        {

            holder.mMorePic.setVisibility(View.VISIBLE);
        } else
        {
            holder.mMorePic.setVisibility(View.GONE);
        }
        if (!dailyBean.isRead())
        {
            holder.mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_unread));
        } else
        {
            holder.mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_read));
        }
        holder.mLayout.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                LogUtil.all("点击");
                if (!dailyBean.isRead())
                {
                    dailyBean.setRead(true);
                    holder.mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_read));
                    new Thread(new Runnable()
                    {

                        @Override
                        public void run()
                        {

                            mDailyDao.insertReadNew(dailyBean.getId() + "");
                        }
                    }).start();
                }
                //跳转到详情界面
                DailyDetailActivity.lanuch(mContext, dailyBean);
            }
        });
    }


    public void updateData(List<DailyBean> dailys)
    {

        this.dailys = dailys;
        notifyDataSetChanged();
    }


    public void addData(List<DailyBean> dailys)
    {

        if (this.dailys == null)
        {
            updateData(dailys);
        } else
        {
            this.dailys.addAll(dailys);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount()
    {

        return dailys.size() == 0 ? 0 : dailys.size();
    }

    public List<DailyBean> getmDailyList()
    {

        return dailys;
    }


    public class ItemTimeViewHolder extends ItemContentViewHolder
    {

        @Bind(R.id.item_time)
        TextView mTime;

        public ItemTimeViewHolder(View itemView)
        {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public class ItemContentViewHolder extends RecyclerView.ViewHolder
    {


        @Bind(R.id.card_view)
        CardView mLayout;

        @Bind(R.id.item_image)
        ImageView mPic;

        @Bind(R.id.item_title)
        TextView mTitle;

        @Bind(R.id.item_more_pic)
        ImageView mMorePic;


        public ItemContentViewHolder(View itemView)
        {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
