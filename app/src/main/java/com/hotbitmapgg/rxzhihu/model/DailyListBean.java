package com.hotbitmapgg.rxzhihu.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class DailyListBean implements Parcelable
{

    private String date;

    private List<DailyBean> stories;

    private List<TopDailys> top_stories;

    public List<TopDailys> getTop_stories()
    {

        return top_stories;
    }

    public void setTop_stories(List<TopDailys> top_stories)
    {

        this.top_stories = top_stories;
    }

    public String getDate()
    {

        return date;
    }

    public void setDate(String date)
    {

        this.date = date;
    }

    public List<DailyBean> getStories()
    {

        return stories;
    }

    public void setStories(List<DailyBean> stories)
    {

        this.stories = stories;
    }

    @Override
    public int describeContents()
    {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

        dest.writeString(this.date);
        dest.writeTypedList(stories);
        dest.writeTypedList(top_stories);
    }

    public DailyListBean()
    {

    }

    protected DailyListBean(Parcel in)
    {

        this.date = in.readString();
        this.stories = in.createTypedArrayList(DailyBean.CREATOR);
        this.top_stories = in.createTypedArrayList(TopDailys.CREATOR);
    }

    public static final Creator<DailyListBean> CREATOR = new Creator<DailyListBean>()
    {

        public DailyListBean createFromParcel(Parcel source)
        {

            return new DailyListBean(source);
        }

        public DailyListBean[] newArray(int size)
        {

            return new DailyListBean[size];
        }
    };
}
