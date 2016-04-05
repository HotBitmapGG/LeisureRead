package com.hotbitmapgg.rxzhihu.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.model.TopDailys;
import com.hotbitmapgg.rxzhihu.ui.activity.DailyDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcc on 16/4/4.
 */
public class MainViewPagerAdapter extends PagerAdapter
{

    private List<TopDailys> tops = new ArrayList<>();

    private Context mContext;


    public MainViewPagerAdapter(Context context, List<TopDailys> tops)
    {

        this.tops = tops;
        this.mContext = context;
    }

    @Override
    public int getCount()
    {

        return tops.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {

        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {

        View view = LayoutInflater.from(mContext).inflate(R.layout.pager_item, container, false);
        ImageView mImg = (ImageView) view.findViewById(R.id.pager_img);
        TextView mTitle = (TextView) view.findViewById(R.id.pager_title);
        TopDailys mTopDaily = tops.get(position);
        Glide.with(mContext).load(mTopDaily.getImage()).into(mImg);
        mTitle.setText(mTopDaily.getTitle());
        final int id = mTopDaily.getId();
        view.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                DailyDetailActivity.lanuch(mContext, id);
            }
        });
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {

        container.removeView((View) object);
    }
}
